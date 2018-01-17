package com.joey.ice.chat.client;


import Glacier2.SessionHelper;
import Glacier2.SessionNotExistException;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;
import com.joey.ice.chat.slice.common.InvalidMessageException;
import com.joey.ice.chat.slice.push.*;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * <p>Coordinator.java</p>
 * <p/>
 * <p>Description:</p>
 * <p/>
 * <p>Copyright (c) 2017. juzhen.io. All rights reserved.</p>
 * <p/>
 *
 * @version 1.0.0
 * @author: lvxiaoyi
 * <p/>
 * Revision History:
 * 2018/1/5, lvxiaoyi, Initial Version.
 */
public class Coordinator {
    public enum ClientState {Disconnected, Connecting, Connected, Disconnecting}

    public Coordinator(MainView mainView, LoginView loginView, ChatView chatView, DefaultListModel users, String[] args) {
        _args = args;
        _mainView = mainView;
        _loginView = loginView;
        _chatView = chatView;
        _users = users;

        Ice.InitializationData initData = new Ice.InitializationData();
        initData.properties = Ice.Util.createProperties(new Ice.StringSeqHolder(_args));
        //initData.properties.setProperty("Ice.Plugin.IceSSL", "IceSSL.PluginFactory");

        //
        // Set Ice.Default.Router if not set.
        //
        if (initData.properties.getProperty("Ice.Default.Router").isEmpty()) {
            initData.properties.setProperty("IceSSL.UsePlatformCAs", "1");
            initData.properties.setProperty("IceSSL.CheckCertName", "1");
            initData.properties.setProperty("Ice.Default.Router", "Glacier2/router:wss -p 443 -h zeroc.com -r /demo-proxy/chat/glacier2");
        }

        initData.dispatcher = new Ice.Dispatcher() {
            public void dispatch(Runnable runnable, Ice.Connection connection) {
                if (_exit)  // The GUI is being destroyed, don't use the GUI thread any more
                {
                    runnable.run();
                } else {
                    SwingUtilities.invokeLater(runnable);
                }
            }
        };

        final Coordinator coordinator = this;
        _factory = new Glacier2.SessionFactoryHelper(initData, new Glacier2.SessionCallback() {
            public void connected(final SessionHelper session) throws SessionNotExistException {
                //
                // Ignore callbacks during shutdown.
                //
                if (_exit) {
                    return;
                }

                //
                // If the session has been reassigned avoid the spurious callback.
                //

                if (session != _session) {
                    return;
                }

                ChatRoomCallbackPrx callback = ChatRoomCallbackPrxHelper.uncheckedCast(_session/**/.addWithUUID(new ChatRoomCallbackI(coordinator)));

                _chat = ChatSessionPrxHelper.uncheckedCast(_session.session());
                try {
                    _chat.begin_setCallback(callback, new Callback_ChatSession_setCallback() {
                        @Override
                        public void
                        response() {
                            _info.save();
                            _username = ChatUtils.formatUsername(_info.username);
                            setState(ClientState.Connected);
                        }

                        @Override
                        public void
                        exception(Ice.LocalException ex) {
                            destroySession();
                        }
                    });
                } catch (Ice.CommunicatorDestroyedException ex) {
                    //Ignore client session was destroyed.
                }
            }

            public void disconnected(SessionHelper session) {
                //
                // Ignore callbacks during shutdown.
                //
                if (_exit) {
                    return;
                }

                _username = "";
                if (_state == ClientState.Disconnecting) // Connection closed by user logout/exit
                {
                    setState(ClientState.Disconnected);
                } else if (_state == ClientState.Connected) // Connection lost while user was chatting
                {
                    setError("<system-message> - The connection with the server was unexpectedly lost.\n" +
                            "You can try to login again from the File menu.");
                } else // Connection lost while user was connecting
                {
                    setError("<system-message> - The connection with the server was unexpectedly lost.\n" +
                            "Try again.");
                }
            }

            public void connectFailed(SessionHelper session, Throwable exception) {
                //
                // Ignore callbacks during shutdown.
                //
                if (_exit) {
                    return;
                }

                try {
                    throw exception;
                } catch (final Glacier2.CannotCreateSessionException ex) {
                    setError("Login failed (Glacier2.CannotCreateSessionException):\n" + ex.reason);
                } catch (final Glacier2.PermissionDeniedException ex) {
                    setError("Login failed (Glacier2.PermissionDeniedException):\n" + ex.reason);
                } catch (Ice.Exception ex) {
                    setError("Login failed (" + ex.ice_name() + ").\n" +
                            "Please check your configuration.");
                } catch (final Throwable ex) {
                    setError("Login failed:\n" + ChatUtils.stack2string(ex));
                }

            }

            public void createdCommunicator(SessionHelper session) {
            }
        });
    }

    public void login(LoginInfo info) {
        setState(ClientState.Connecting);
        _info = info;
        _session = _factory.connect(info.username, info.password);
    }

    public void logout() {
        setState(ClientState.Disconnecting);
        destroySession();
    }

    public void exit() {
        _exit = true;
        Ice.Communicator communicator = _session == null ? null : _session.communicator();
        destroySession();
        _mainView.dispose();
        if (communicator != null) {
            communicator.waitForShutdown();
        }
        System.exit(0);
    }

    @SuppressWarnings("unchecked")
    public void initEvent(final String[] users) {
        for (int cont = 0; cont < users.length; ++cont) {
            _users.addElement(users[cont]);
        }
    }

    @SuppressWarnings("unchecked")
    public void userJoinEvent(final long timestamp, final String name) {
        _users.addElement(name);
        _chatView.appendMessage(ChatUtils.formatTimestamp(timestamp) + " - <system-message> - " + name +
                " joined.");
    }

    public void userLeaveEvent(final long timestamp, final String name) {
        int index = _users.indexOf(name);
        if (index != -1) {
            _users.remove(index);
            _chatView.appendMessage(ChatUtils.formatTimestamp(timestamp) + " - " + "<system-message> - " +
                    name + " left.");
        }
    }

    public void userSayEvent(final long timestamp, final String name, final String message) {
        _chatView.appendMessage(ChatUtils.formatTimestamp(timestamp) + " - <" + name + "> " +
                ChatUtils.unstripHtml(message));
    }

    public void sendMessage(String message) {

        if (_chat != null) {
            if (message.length() > _maxMessageSize) {
                _chatView.appendMessage("<system-message> - Message length exceeded, maximum length is " +
                        _maxMessageSize + " characters.");
            } else {
                try {
                    _chat.begin_send(message, new AMI_ChatSession_sendI(_username, message));
                } catch (Ice.CommunicatorDestroyedException ex) {
                    //Ignore client session was destroyed.
                }
            }
        }
    }

    public void setState(ClientState state) {
        _state = state;
        if (state == ClientState.Disconnected) {
            _loginView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            _chatView.setConnected(false);
            _loginView.setEnabled(true);
            _loginView.setConnected(false);
            _mainView.setEnabled(false);
            _mainView.setConnected(false);
            _users.clear();
        } else if (state == ClientState.Connecting) {
            _loginView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            _loginView.setEnabled(false);
            _mainView.setEnabled(false);
        } else if (state == ClientState.Connected) {
            _chatView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            _loginView.setConnected(true);
            _chatView.setConnected(true);
            _mainView.setEnabled(true);
            _mainView.setConnected(true);
        } else if (state == ClientState.Disconnecting) {
            _chatView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            _mainView.setEnabled(false);
        }
    }

    public void setError(final String message) {
        //
        // Don't display errors at that point GUI is being destroyed.
        //
        if (_exit == true) {
            return;
        }
        if (_state != ClientState.Connected) {
            setState(ClientState.Disconnected);
            ErrorView errorView = new ErrorView(_mainView, message);
            errorView.setSize(430, 200);
            errorView.setMinimumSize(new Dimension(430, 200));
            errorView.setMaximumSize(new Dimension(430, 200));
            errorView.setResizable(false);
            ChatDemoGUIFactory.centerComponent(errorView, _mainView);
            errorView.setVisible(true);
        } else {
            _mainView.setEnabled(false);
            _mainView.setConnected(false);
            _chatView.appendError(message);
        }
    }

    public String getUsername() {
        return _username;
    }

    //
    // Callback for the send async operation.
    //
    public class AMI_ChatSession_sendI extends Callback_ChatSession_send {
        public AMI_ChatSession_sendI(String name, String message) {
            _name = name;
            _message = message;
        }

        public void response(long timestamp) {
            userSayEvent(timestamp, _name, _message);
        }

        public void exception(Ice.LocalException ex) {
            destroySession();
        }

        public void exception(Ice.UserException ex) {
            if (ex instanceof InvalidMessageException) {
                InvalidMessageException e = (InvalidMessageException) ex;
                _chatView.appendMessage("<system-message> - " + e.reason);
            }
        }

        private final String _name;
        private final String _message;
    }

    public class ErrorView extends JDialog {
        private JButton bttClose = new JButton("Close");

        ErrorView(Frame frame, String message) {
            super(frame, true);
            setTitle("Error - Chat Demo");
            ActionListener closeListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            };
            bttClose.addActionListener(closeListener);

            final JTextArea txtErrorMessage = new JTextArea(message, 5, 30);
            txtErrorMessage.setLineWrap(true);
            JPanel errorPanel = null; // Build the error panel.
            {
                FormLayout layout = new FormLayout("center:pref:grow", "center:pref:grow");
                DefaultFormBuilder builder = new DefaultFormBuilder(layout);
                builder.border(Borders.DIALOG);
                ImageIcon icon = MainView.getIcon("/icons/32x32/error.png");
                if (icon != null) {
                    builder.append(new JLabel(icon));
                    builder.nextLine();
                }
                txtErrorMessage.setEditable(false);
                builder.append(ChatDemoGUIFactory.createStrippedScrollPane(txtErrorMessage));
                errorPanel = builder.getPanel();
            }

            JPanel actionsPanel = null; // Build a panel for put actions.
            {
                FormLayout layout = new FormLayout("center:3dlu:grow", "pref");
                DefaultFormBuilder builder = new DefaultFormBuilder(layout);
                builder.append(bttClose);
                actionsPanel = builder.getPanel();
                getRootPane().setDefaultButton(bttClose);
            }

            FormLayout layout = new FormLayout("fill:pref:grow", "pref");
            DefaultFormBuilder builder = new DefaultFormBuilder(layout);
            builder.append(errorPanel);
            builder.nextLine();
            builder.append(actionsPanel);

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setContentPane(builder.getPanel());

            final JPopupMenu textMenu = new JPopupMenu();
            textMenu.add(new DefaultEditorKit.CopyAction());
            textMenu.pack();


            txtErrorMessage.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger()) // BUTTON3 is the right mouse button
                    {
                        textMenu.show(txtErrorMessage, e.getX(), e.getY());
                    }
                }
            });
        }
    }

    protected void
    destroySession() {
        final Glacier2.SessionHelper s = _session;
        _session = null;
        _chat = null;

        if (s != null) {
            s.destroy();
        }
    }

    private final Glacier2.SessionFactoryHelper _factory;
    private ClientState _state;
    private final String[] _args;
    private final MainView _mainView;
    private final ChatView _chatView;
    private final LoginView _loginView;
    private final DefaultListModel _users;
    private LoginInfo _info = new LoginInfo();
    private Glacier2.SessionHelper _session = null;
    private Object _sessionMonitor = new Object();
    private ChatSessionPrx _chat = null;
    private String _username = "";
    private static final int _maxMessageSize = 1024;
    private boolean _exit = false;
}

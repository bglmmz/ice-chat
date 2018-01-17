package com.joey.ice.chat.client;


import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

/**
 * <p>LoginView.java</p>
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
public class LoginView extends JPanel {
    LoginView(MainView mainview, Preferences connectionPrefs) {
        _mainView = mainview;
        _info = new LoginInfo(connectionPrefs);

        ActionListener loginListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        };
        _bttLogin.addActionListener(loginListener);

        {   // Build the basic login panel.
            FormLayout layout = new FormLayout("center:pref:grow", "pref:grow");
            DefaultFormBuilder builder = new DefaultFormBuilder(layout);
            builder.border(Borders.DIALOG);
            builder.append(new JLabel("Chat Demo"));
            builder.nextLine();
            builder.append(new JLabel("Welcome! You can login with any username / password."));
            builder.append(new JLabel("Username"));
            builder.nextLine();
            builder.append(_txtUsername);
            builder.nextLine();
            builder.append(new JLabel("Password"));
            builder.nextLine();
            builder.append(_txtPassword);
            _directPanel = builder.getPanel();
        }

        JPanel actionsPanel = null; // Build a panel for put actions.
        {
            FormLayout layout = new FormLayout("center:3dlu:grow", "pref");
            DefaultFormBuilder builder = new DefaultFormBuilder(layout);
            builder.append(_bttLogin);
            actionsPanel = builder.getPanel();
            _mainView.getRootPane().setDefaultButton(_bttLogin);
        }

        FormLayout layout = new FormLayout("fill:pref:grow", "pref");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.append(_directPanel);
        builder.nextLine();
        builder.append(actionsPanel);

        _txtUsername.setText(_info.username);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(builder.getPanel());
    }

    public void setCoordinator(Coordinator coordinator) {
        _coordinator = coordinator;
    }

    public void setEnabled(boolean enabled) {
        _txtUsername.setEnabled(enabled);
        _txtPassword.setEnabled(enabled);
        _bttLogin.setEnabled(enabled);
    }

    private void login() {
        _coordinator.setState(Coordinator.ClientState.Connecting);
        _info.username = _txtUsername.getText();
        if (_txtPassword.getPassword() != null) {
            _info.password = new String(_txtPassword.getPassword());
        }
        _coordinator.login(_info);
    }

    public void setConnected(boolean connected) {
        setVisible(!connected);
        if (connected) {
            _mainView.getRootPane().setDefaultButton(null);
        } else {
            _mainView.getRootPane().setDefaultButton(_bttLogin);
        }
    }

    private final JPanel _directPanel;
    private final JTextField _txtUsername = new JTextField(20);
    private final JPasswordField _txtPassword = new JPasswordField(20);
    private final JButton _bttLogin = new JButton("Login");
    private final LoginInfo _info;
    private Coordinator _coordinator = null;
    private final MainView _mainView;
}
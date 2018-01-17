package com.joey.ice.chat.server;

import Ice.Current;
import Ice.ObjectNotExistException;
import com.joey.ice.chat.slice.common.InvalidMessageException;
import com.joey.ice.chat.slice.polling.ChatRoomEvent;
import com.joey.ice.chat.slice.polling._PollingChatSessionDisp;
import com.joey.ice.chat.adapter.PollCallbackAdapter;

import java.util.Date;

/**
 * <p>PollingChatSessionI.java</p>
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
 * 2017/12/29, lvxiaoyi, Initial Version.
 */
public class PollingChatSessionI extends _PollingChatSessionDisp {

    private PollCallbackAdapter callback;

    private ChatRoom chatRoom;
    private String name;
    private Ice.Logger logger;
    private boolean destroy;
    private boolean trace;
    private long timestamp;

    public PollingChatSessionI() {
    }

    public PollingChatSessionI(ChatRoom chatRoom, String name, boolean trace, Ice.Logger logger) {
        this.chatRoom = chatRoom;
        this.name = name;
        this.trace = true;
        this.logger = logger;
        this.callback = new PollCallbackAdapter();
        this.timestamp = System.currentTimeMillis();
        this.destroy = false;

        chatRoom.join(name, this.callback);
    }

    @Override
    public String[] getInitialUsers(Current __current) {
        if(destroy) {
            if(trace) {
                logger.trace("info", "User '" + name +  "' requested initial users list but the session is already destroyed.");
            }
            throw new ObjectNotExistException();
        }

        return callback.getInitialUsers();
        /*
        logger = __current.adapter.getCommunicator().getLogger();

        ConcurrentMap<String, ChatRoomCallbackAdapter> members =  this.chatRoom.getMembers();
        return members.keySet().toArray(new String[]{});
        */
    }

    @Override
    public ChatRoomEvent[] getUpdates(Current __current) {
        if(destroy) {
            if(trace) {
                logger.trace("info", "User '" + name +  "' requested session updates list but the session is already destroyed.");
            }
            throw new ObjectNotExistException();
        }
        return callback.getUpdates().toArray(new ChatRoomEvent[]{});
    }

    @Override
    public long send(String message, Current __current) throws InvalidMessageException {
        if(destroy) {
            if(trace) {
                logger.trace("info", "User '" + name +  "' tried to send a message but the session is already destroyed.");
            }
            throw new ObjectNotExistException();
        }

        if(trace) {
            logger.trace("info", "User '" + name +  "' sent a message.");
        }

        return chatRoom.send(name, message);
    }

    @Override
    public void destroy(Current __current) {
        if(destroy) {
            if(trace) {
                logger.trace("info", "User '" + name +  "' tried to destroy the session but the session is already destroyed.");
            }
            throw new ObjectNotExistException();
        }

        __current.adapter.remove(__current.id);
        chatRoom.leave(name);

        if(trace) {
            logger.trace("info", "Poll session for user '" + name +  "' destroyed.");
        }
        destroy = true;
    }


    public long timestamp(){
        if(destroy) {
            if(trace) {
                logger.trace("info", "User '" + name +  "' requested the session timestamp but the session is already destroyed.");
            }
            throw new ObjectNotExistException();
        }
        return timestamp;
    }
}

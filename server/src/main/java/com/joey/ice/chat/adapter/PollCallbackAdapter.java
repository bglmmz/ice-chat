package com.joey.ice.chat.adapter;

import com.joey.ice.chat.slice.polling.ChatRoomEvent;
import com.joey.ice.chat.slice.polling.MessageEvent;
import com.joey.ice.chat.slice.polling.UserJoinedEvent;
import com.joey.ice.chat.slice.polling.UserLeftEvent;
import com.joey.ice.chat.server.ChatRoom;

import java.util.Date;
import java.util.List;

/**
 * <p>PollCallbackAdapter.java</p>
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
public class PollCallbackAdapter implements ChatRoomCallbackAdapter {

    String[] users;
    List<ChatRoomEvent> updates;

    ChatRoom chatRoom;
    String name;
    Date timestamp; // The last time the session was refreshed.
    boolean destroy;
    boolean trace;
    Ice.Logger logger;

    @Override
    public void init(String[] users) {
        this.users = users;
    }

    @Override
    public void join(UserJoinedEvent event) {
        updates.add(event);
    }

    @Override
    public void leave(UserLeftEvent event) {
        updates.add(event);
    }

    @Override
    public void send(MessageEvent event) {
        updates.add(event);
    }

    public String[] getInitialUsers() {

        return users;
    }

    public List<ChatRoomEvent> getUpdates() {
        return updates;
    }
}

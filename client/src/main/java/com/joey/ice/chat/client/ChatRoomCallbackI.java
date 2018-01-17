package com.joey.ice.chat.client;

import com.joey.ice.chat.slice.push._ChatRoomCallbackDisp;

/**
 * <p>ChatRoomCallbackI.java</p>
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
 * 2017/8/30, lvxiaoyi, Initial Version.
 */
public class ChatRoomCallbackI extends _ChatRoomCallbackDisp {

    public ChatRoomCallbackI(Coordinator coordinator) {
        _coordinator = coordinator;
    }

    public void init(String[] users, Ice.Current currrent) {
        _coordinator.initEvent(users);
    }

    public void send(long timestamp, String name, String message, Ice.Current currrent) {
        if (name.compareToIgnoreCase(_coordinator.getUsername()) != 0) {
            _coordinator.userSayEvent(timestamp, name, message);
        }
    }

    public void join(long timestamp, String name, Ice.Current currrent) {
        _coordinator.userJoinEvent(timestamp, name);
    }

    public void leave(long timestamp, String name, Ice.Current currrent) {
        _coordinator.userLeaveEvent(timestamp, name);
    }

    private final Coordinator _coordinator;
}

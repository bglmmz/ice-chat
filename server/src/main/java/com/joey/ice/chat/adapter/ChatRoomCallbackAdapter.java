package com.joey.ice.chat.adapter;


import com.joey.ice.chat.slice.polling.MessageEvent;
import com.joey.ice.chat.slice.polling.UserJoinedEvent;
import com.joey.ice.chat.slice.polling.UserLeftEvent;

/**
 * <p>ChatRoomCallbackAdapter.java</p>
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
 * 2017/8/29, lvxiaoyi, Initial Version.
 */
public interface ChatRoomCallbackAdapter {
    void init(String[] users);
    void join(UserJoinedEvent event);
    void leave(UserLeftEvent event);
    void send(MessageEvent event);
}

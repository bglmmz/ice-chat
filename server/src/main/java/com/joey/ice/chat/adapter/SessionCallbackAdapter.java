package com.joey.ice.chat.adapter;


import Ice.LocalException;
import com.joey.ice.chat.server.ChatRoom;
import com.joey.ice.chat.slice.polling.MessageEvent;
import com.joey.ice.chat.slice.polling.UserJoinedEvent;
import com.joey.ice.chat.slice.polling.UserLeftEvent;
import com.joey.ice.chat.slice.push.*;

/**
 * <p>SessionCallbackAdapter.java</p>
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
public class SessionCallbackAdapter implements ChatRoomCallbackAdapter {

    private ChatRoom chatRoom;
    private ChatRoomCallbackPrx callback;
    private ChatSessionPrx session;
    private boolean trace;
    private Ice.Logger logger;
    private String name;


    public SessionCallbackAdapter(ChatRoom chatRoom, ChatRoomCallbackPrx callback, ChatSessionPrx session, boolean trace, Ice.Logger logger, String name ) {
        this.chatRoom = chatRoom;
        this.callback = callback;
        this.session = session;
        this.trace = trace;
        this.logger = logger;
        this.name = name;
    }

    @Override
    public void init(String[] users) {
        //callback.begin_init(users);

        System.out.println("用户欢迎页面:");

        //用异步方式回调客户端
        callback.begin_init(users, new Callback_ChatRoomCallback_init() {
            @Override
            public void response() {
                success();
            }

            @Override
            public void exception(LocalException ex) {
                failure();
            }
        });

    }

    @Override
    public void join(UserJoinedEvent event) {
        //callback.join(event.timestamp, event.name);

        System.out.println("用户加入:"+ event.name);

        //用异步方式回调客户端
        callback.begin_join(event.timestamp, event.name, new Callback_ChatRoomCallback_join() {
            @Override
            public void response() {
                success();
            }

            @Override
            public void exception(LocalException ex) {
                failure(event.name);
            }
        });
    }

    @Override
    public void leave(UserLeftEvent event) {
        //callback.leave(event.timestamp, event.name);
        System.out.println("用户离开:"+ event.name);

        //用异步方式回调客户端
        callback.begin_leave(event.timestamp, event.name, new Callback_ChatRoomCallback_leave() {
            @Override
            public void response() {
                success();
            }

            @Override
            public void exception(LocalException ex) {
                failure(event.name);
            }
        });
    }

    @Override
    public void send(MessageEvent event) {
        //callback.send(event.timestamp, event.name, event.message);

        System.out.println("用户发送消息:"+ event.message);

        //用异步方式回调客户端
        callback.begin_send(event.timestamp, event.name, event.message, new Callback_ChatRoomCallback_send() {
            @Override
            public void response() {
                success();
            }

            @Override
            public void exception(LocalException ex) {
                failure(event.name);
            }
        });
    }

    private void success() {

    };

    private void failure() {
        if(trace){
            logger.trace("info", "Error sending request to user " + name + ". The user's session will be destroyed." );
        }
        try{
            session.destroy();
            chatRoom.leave(name);
            // Collocated call.
        }catch(Ice.LocalException ex) {

        }
    }

    private void failure(String name) {
        if(trace){
            logger.trace("info", "Error sending request to user " + name + ". The user's session will be destroyed." );
        }
        try{
            session.destroy();
            chatRoom.leave(name);
            // Collocated call.
        }catch(Ice.LocalException ex) {

        }
    }
}

package com.joey.ice.chat.server;

import Ice.Current;
import Ice.ObjectAdapterDeactivatedException;
import Ice.ObjectNotExistException;
import com.joey.ice.chat.slice.common.InvalidMessageException;
import com.joey.ice.chat.slice.push.ChatRoomCallbackPrx;
import com.joey.ice.chat.slice.push.ChatSessionPrx;
import com.joey.ice.chat.slice.push.ChatSessionPrxHelper;
import com.joey.ice.chat.slice.push._ChatSessionDisp;
import com.joey.ice.chat.adapter.ChatRoomCallbackAdapter;
import com.joey.ice.chat.adapter.SessionCallbackAdapter;

/**
 * <p>ChatSessionI.java</p>
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
public class ChatSessionI extends _ChatSessionDisp {
    private ChatRoomCallbackPrx chatRoomCallbackPrx;
    private ChatRoom chatRoom;
    private String name;
    private Ice.Logger logger;
    private boolean destroy;
    private boolean trace;

    public ChatSessionI(ChatRoom chatRoom, String name, boolean trace, Ice.Logger logger) {
        this.chatRoom = chatRoom;
        this.name = name;
        this.trace = trace;
        this.logger = logger;
    }

    @Override
    public void setCallback(ChatRoomCallbackPrx cb, Current current) {

        System.out.println("User '" + name + "' tried to set a ChatRoomCallbackPrx:\n" + cb);

        if(destroy){
            throw new ObjectNotExistException();
        }
        if(cb==null){
            throw new ObjectNotExistException();
        }

        this.chatRoomCallbackPrx = cb;

        ChatSessionPrx sessionPrx = ChatSessionPrxHelper.uncheckedCast(current.adapter.createProxy(current.id));
        logger = current.adapter.getCommunicator().getLogger();

        ChatRoomCallbackAdapter chatRoomCallbackAdapter = new SessionCallbackAdapter(chatRoom, cb, sessionPrx, true, logger, name);

        // 注意，在使用join传递代理之前，向客户代理添加了一个值为 "o" 的_fwd上下文。
        // 它提示Glacier使用单向调用来转发客户回调。这样比双向调用更加有效。
        // 因为所有的回调操作均为void返回值，所以可以单向调用。
        current.ctx.put("_fwd", "o");

        // 一旦客户调用了setCallback，表示加入了聊天室，可以接收聊天室的各种行为通知
        chatRoom.join(name, chatRoomCallbackAdapter);

    }

    @Override
    public long send(String message, Current __current) throws InvalidMessageException {
        if(destroy){
            if(trace) {
                logger.trace("info", "User '" + name + "' tried to send a message but the session is already destroyed.");
            }
            throw new ObjectNotExistException();
        }
        if(chatRoomCallbackPrx==null){
            if(trace) {
                logger.trace("info", "User '" + name + "' tried to send a message without setting the callback.");
            }
            throw new InvalidMessageException("You cannot send messages until you joined the chat.");
        }
        if(trace) {
            logger.trace("info", "User '" + name + "' tried to send a message:\n" + message);
        }

        //todo: 检查用户发送的信息是否合法
        return chatRoom.send(name, message);
    }

    @Override
    public void destroy(Current current) {
        if(destroy){
            if(trace) {
                logger.trace("info", "Trying to destroy the session for user '" + name + "' but the session is already destroyed.");
            }
            throw new ObjectNotExistException();
        }
        try {
            current.adapter.remove(current.id);

            chatRoom.unreserve(name);
            chatRoom.leave(name);
        } catch(ObjectAdapterDeactivatedException e) {
            // No need to clean up, the server is shutting down.
        }
        destroy = true;
    }
}

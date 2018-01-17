package com.joey.ice.chat.server;

import Ice.Logger;
import com.joey.ice.chat.slice.polling.MessageEvent;
import com.joey.ice.chat.slice.polling.UserJoinedEvent;
import com.joey.ice.chat.slice.polling.UserLeftEvent;
import com.joey.ice.chat.adapter.ChatRoomCallbackAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>ChatRoom.java</p>
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
public class ChatRoom {

    private Ice.Logger logger;
    private boolean trace;

    private ReentrantLock lock = new ReentrantLock();

    //存储已经建立回话，但是还没有加入聊天室的客户名
    private List<String> reserved = new ArrayList<>();

    //存储当前聊天室的所有用户（已经调用过join函数的用户）
    private ConcurrentMap<String, ChatRoomCallbackAdapter> members = new ConcurrentHashMap<>();


    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public ChatRoom(boolean trace, Ice.Logger logger){
        this.trace = trace;
        this.logger = logger;
    }

    public List<String> getReserved() {
        return reserved;
    }

    public void setReserved(List<String> reserved) {
        this.reserved = reserved;
    }

    public ConcurrentMap<String, ChatRoomCallbackAdapter> getMembers() {
        return members;
    }

    public void setMembers(ConcurrentMap<String, ChatRoomCallbackAdapter> members) {
        this.members = members;
    }

    public void reserve(String name) {
        lock.lock();
        try {
            if (reserved.contains(name)) {
                throw new RuntimeException("The name " + name + " is already in use.");
            }
            reserved.add(name);
        }finally {
            lock.unlock();
        }
    }
    public void unreserve(String name){
        lock.lock();
        try {
            reserved.remove(name);
        }finally {
            lock.unlock();
        }
    }
    public void join(String name, ChatRoomCallbackAdapter adapter){

        System.out.println("User '" + name + "' tried to join the chat room.");


        lock.lock();
        try {
           long timestamp = System.currentTimeMillis();
            reserved.remove(name);
            List<String> userList = new ArrayList<>();
            for(String key : members.keySet()){
                userList.add(key);
            }

            adapter.init(userList.toArray(new String[]{}));

            //保存每个用户的session（连接），adapter是封装后的session(连接)
            members.put(name, adapter);

            UserJoinedEvent joinedEvent = new UserJoinedEvent(timestamp, name);

            for(ChatRoomCallbackAdapter eachAdapter : members.values()) {
                eachAdapter.join(joinedEvent);
            }

            if(trace){
                 logger.trace("info", "User " + name + " joined the chat room.");
            }

        }finally {
            lock.unlock();
        }
    }


    public void leave(String name){
        System.out.println("User '" + name + "' tried to leave.");

        lock.lock();
        try {
            long timestamp = System.currentTimeMillis();
            members.remove(name);
            UserLeftEvent leftEvent = new UserLeftEvent(timestamp, name);

            for(ChatRoomCallbackAdapter eachAdapter : members.values()) {
                eachAdapter.leave(leftEvent);
            }

            if(trace){
                logger.trace("info", "User " + name + " left the chat room.");
            }


        }finally {
            lock.unlock();
        }
    }

    public long send(String name, String msg){

        System.out.println("User '" + name + "' tried to send a message:\n" + msg);

        lock.lock();
        try {
            long timestamp = System.currentTimeMillis();

            MessageEvent messageEvent = new MessageEvent(timestamp, name, msg);
            for(ChatRoomCallbackAdapter eachAdapter : members.values()) {
                eachAdapter.send(messageEvent);
            }

            if(trace){
                logger.trace("info", "User " + name + " sent a message to the chat room.");
            }

            return timestamp;

        }finally {
            lock.unlock();
        }
    }

}

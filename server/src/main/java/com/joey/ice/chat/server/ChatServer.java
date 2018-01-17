package com.joey.ice.chat.server;

import Ice.Application;
import Ice.ObjectAdapter;
import IceInternal.Ex;

import java.util.Timer;


/**
 * <p>ChatServer.java</p>
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
public class ChatServer extends Ice.Application {

    private ObjectAdapter objectAdapter;
    private Timer timer = new Timer();;

    @Override
    public int run(String[] args) {
        //超时时间设置, 单位：秒
        int timeout = communicator().getProperties().getPropertyAsIntWithDefault("ReaperTimeout", 10);
        boolean traceEnabled = communicator().getProperties().getPropertyAsIntWithDefault("Server.Trace", 0) != 0;
        Ice.Logger logger = communicator().getLogger();
        ReaperTask reaper = new ReaperTask(timeout, traceEnabled, logger);
        timer.schedule(reaper, timeout*1000);

        try {
            objectAdapter = communicator().createObjectAdapter("ChatServer");

            ChatRoom chatRoom = new ChatRoom(traceEnabled, logger);
            if (traceEnabled) {
                logger.trace("info", "Chat room created ok.");
            }

            objectAdapter.add(new ChatSessionManagerI(chatRoom, traceEnabled, logger), communicator().stringToIdentity("ChatSessionManager"));
            if (traceEnabled) {
                logger.trace("info", "Chat session manager created ok.");
            }

            objectAdapter.add(new PollingChatSessionFactoryI(chatRoom, reaper, traceEnabled, logger), communicator().stringToIdentity("PollingChatSessionFactory"));
            if (traceEnabled) {
                logger.trace("info", "Polling chat session factory created ok.");
            }

            objectAdapter.add(new DummyPermissionsVerifier(), communicator().stringToIdentity("DummyPermissionsVerifier"));
            if (traceEnabled) {
                logger.trace("info", "Dummy permission verifier created ok.");
            }

            objectAdapter.activate();

            if (traceEnabled) {
                logger.trace("info", "Chat server started ok.");
            }

            communicator().waitForShutdown();
        }catch (Ice.LocalException e){
            timer.cancel();
            throw e;
        }


        return 0;
    }



    // main
    public static void main(String[] args) {
        System.out.println("**********Chat server starting**********");
        ChatServer chatServer = new ChatServer();
        //String conf = "D:\\workspace\\prev\\ice-chat\\src\\main\\resources\\config.chatserver";
        //int status = chatServer.main("ChatServer", args, conf);
        int status = chatServer.main("ChatServer", args);
        System.exit(status);
    }

}

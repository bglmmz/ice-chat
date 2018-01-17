package com.joey.ice.chat.server;

import Glacier2.SessionPrx;
import Glacier2.SessionPrxHelper;
import Ice.*;
import Ice.Exception;
import com.joey.ice.chat.server.ChatRoom;
import com.joey.ice.chat.server.PollingChatSessionI;
import com.joey.ice.chat.slice.common.CannotCreateSessionException;
import com.joey.ice.chat.slice.polling.PollingChatSession;
import com.joey.ice.chat.slice.polling.PollingChatSessionPrx;
import com.joey.ice.chat.slice.polling.PollingChatSessionPrxHelper;
import com.joey.ice.chat.slice.polling._PollingChatSessionFactoryDisp;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * <p>PollingChatSessionFactoryI.java</p>
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
public class PollingChatSessionFactoryI extends _PollingChatSessionFactoryDisp {

    private ChatRoom chatRoom;
    private boolean trace;
    private Ice.Logger logger;
    private ReaperTask reaper;

    public PollingChatSessionFactoryI(ChatRoom  chatRoom, ReaperTask reaper, boolean trace, Ice.Logger logger){
        this.chatRoom = chatRoom;
        this.reaper=reaper;
        this.trace =trace;
        this.logger=logger;
    }


    @Override
    public PollingChatSessionPrx create(String name, String password, Current current) throws CannotCreateSessionException {
        if(StringUtils.isBlank(name)){
            chatRoom.getLogger().trace("error", "User name is blank.");
            throw new CannotCreateSessionException("User name  blank.");
        }

        chatRoom.reserve(name);

        PollingChatSessionPrx proxy = null;
        try {


            PollingChatSessionI session = new PollingChatSessionI(chatRoom, name, trace, logger);

            /*Ice.Identity identity = new Ice.Identity();
            identity.name = name;
            identity.category = "pollingChatSession";
            proxy = PollingChatSessionPrxHelper.uncheckedCast(current.adapter.add(session, identity));*/

            proxy = PollingChatSessionPrxHelper.uncheckedCast(current.adapter.addWithUUID(session));

            reaper.add(proxy, session);
        }catch (Exception e){
            logger.trace("info", "Cannot create poll session for user '" + name + "', ChatServer adapter is deactivated.");
            throw new CannotCreateSessionException("Internal server error");
        }

        if(trace){
            logger.trace("info", "Poll session created for user '"  + name + "'.");
        }
        return proxy;
    }


    class ConnectionCallbackI implements  ConnectionCallback{
        private boolean trace;
        private Ice.Logger logger;
        private PollingChatSessionPrx proxy;

        public ConnectionCallbackI(PollingChatSessionPrx proxy, boolean trace, Logger logger) {
            this.proxy = proxy;
            this.trace = trace;
            this.logger = logger;
        }

        @Override
        public void heartbeat(Connection con) {

        }

        @Override
        public void closed(Connection con) {
            proxy.destroy();
            if(trace){
                logger.trace("info", "Session: " + proxy + " reaped.");
            }
        }
    }
}

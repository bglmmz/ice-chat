package com.joey.ice.chat.server;

import Glacier2.*;
import Ice.Current;
import Ice.Exception;
import org.apache.commons.lang3.StringUtils;


/**
 * <p>ChatSessionManagerI.java</p>
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
public class ChatSessionManagerI extends _SessionManagerDisp {
    private ChatRoom chatRoom;
    private boolean trace;
    private Ice.Logger logger;

    public ChatSessionManagerI() {
    }

    public ChatSessionManagerI(ChatRoom chatRoom, boolean trace, Ice.Logger logger) {
        this.chatRoom = chatRoom;
        this.trace = trace;
        this.logger = logger;
    }

    @Override
    public SessionPrx create(String name, SessionControlPrx sessionControlPrx, Current current) throws CannotCreateSessionException {

        System.out.println("User '" + name + "' tried to create SessionPrx:\n" + sessionControlPrx);

        if(StringUtils.isBlank(name)){
            if(trace){
                logger.trace("info", "Cannot create push session:\n" + "user name is blank.");
            }
            throw new Glacier2.CannotCreateSessionException("user name is blank.");
        }

        chatRoom.reserve(name);

        SessionPrx proxy = null;
        try {


            final ChatSessionI session = new ChatSessionI(chatRoom, name, trace, logger);

            /*Ice.Identity ident = new Ice.Identity();
            ident.name = name;
            ident.category = "chatSession";
            proxy = SessionPrxHelper.uncheckedCast(current.adapter.add(session, ident));
            sessionControlPrx.identities().add(new Ice.Identity[]{ident});*/

            proxy = SessionPrxHelper.uncheckedCast(current.adapter.addWithUUID(session));
            sessionControlPrx.identities().add(new Ice.Identity[]{proxy.ice_getIdentity()});

        }catch (Exception e){
            chatRoom.getLogger().trace("error", e.getMessage());

            if(proxy!=null){
                proxy.destroy();
            }
            throw new CannotCreateSessionException("Internal server error");
        }
        return proxy;
    }
}

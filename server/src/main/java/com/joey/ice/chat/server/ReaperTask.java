package com.joey.ice.chat.server;

import Ice.Logger;
import com.joey.ice.chat.slice.polling.PollingChatSessionPrx;

import java.util.*;

/**
 * <p>ReaperTask.java</p>
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
public class ReaperTask extends TimerTask {
    private long timeout;
    private boolean trace;
    private Ice.Logger logger;

    List<AbstractMap.SimpleEntry> reapables = new ArrayList<>();

    public ReaperTask(int timeout, boolean trace, Logger logger) {
        this.timeout = timeout;
        this.trace = trace;
        this.logger = logger;
    }


    void add(PollingChatSessionPrx proxy, PollingChatSessionI session){
        reapables.add(new AbstractMap.SimpleEntry(proxy, session));
    }

    @Override
    public void run() {
        Iterator it = reapables.iterator();
        while(it.hasNext()) {
            AbstractMap.SimpleEntry entry = (AbstractMap.SimpleEntry)it.next();

            PollingChatSessionPrx proxy = (PollingChatSessionPrx)entry.getKey();
            PollingChatSessionI session = (PollingChatSessionI)entry.getValue();
            try{
                //session超时
                if(System.currentTimeMillis()-session.timestamp()> timeout){
                    if(trace) {
                        logger.trace("info", "Session: " + proxy + " reaped.");
                    }

                    proxy.destroy();
                    reapables.remove(entry);
                }
            } catch (Ice.LocalException e) {
                reapables.remove(entry);
            }
        }
    }
}

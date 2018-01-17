package com.joey.ice.chat.client;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>ChatUtils.java</p>
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
public class ChatUtils {
    //
    // This function unescapes HTML entities in the data string
    // and return the unescaped string.
    //
    public static String unstripHtml(String data) {
        data = data.replace("&quot;", "\"");
        data = data.replace("&#39;", "'");
        data = data.replace("&lt;", "<");
        data = data.replace("&gt;", ">");
        data = data.replace("&amp;", "&");
        return data;
    }

    public static String formatTimestamp(long timestamp) {
        DateFormat dtf = new SimpleDateFormat("HH:mm:ss");
        dtf.setTimeZone(TimeZone.getDefault());
        return dtf.format(new Date(timestamp));
    }

    public static String formatUsername(String in) {
        try {
            in = in.substring(0, 1).toUpperCase() + in.substring(1, in.length()).toLowerCase();
        } catch (IndexOutOfBoundsException ex) {
        }
        return in;
    }

    public static String stack2string(Throwable e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e2) {
            return e.toString();
        }
    }
}

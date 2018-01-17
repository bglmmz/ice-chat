package com.joey.ice.chat.client;


import java.util.prefs.Preferences;

/**
 * <p>LoginInfo.java</p>
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
public class LoginInfo {
    public LoginInfo() {
    }

    public LoginInfo(Preferences connectionPrefs) {
        _connectionPrefs = connectionPrefs;
        load();
    }

    public void load() {
        username = _connectionPrefs.get("chatdemo.username", username);
    }

    public void save() {
        _connectionPrefs.put("chatdemo.username", username);
    }

    public String username = System.getProperty("user.name");
    public String password = "";
    private Preferences _connectionPrefs;
}

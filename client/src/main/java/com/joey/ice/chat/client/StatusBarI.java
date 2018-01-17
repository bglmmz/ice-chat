package com.joey.ice.chat.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * <p>StatusBarI.java</p>
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
public class StatusBarI extends JPanel {
    StatusBarI() {
        super(new BorderLayout());
        setBorder(new EmptyBorder(7, 7, 7, 7));
        _connectedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(_connectedLabel, BorderLayout.LINE_START);
    }

    public void setConnected(boolean connected) {
        if (connected) {
            _connectedLabel.setText("Online");
        } else {
            _connectedLabel.setText("Offline");
        }
    }

    private final JLabel _connectedLabel = new JLabel("Offline");
}
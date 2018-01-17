package com.joey.ice.chat.client;


import javax.swing.*;
import java.awt.*;

/**
 * <p>ChatDemoGUIFactory.java</p>
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
public class ChatDemoGUIFactory {
    public static void locateOnScreen(Component component) {
        Dimension paneSize = component.getSize();
        Dimension screenSize = component.getToolkit().getScreenSize();
        component.setLocation((screenSize.width - paneSize.width) / 2, (screenSize.height - paneSize.height) / 2);
    }

    public static void centerComponent(Component component, Component base) {
        Dimension baseSize = base.getSize();
        Point basePoint = base.getLocation();
        Dimension componentSize = component.getSize();
        component.setLocation(basePoint.x + (baseSize.width - componentSize.width) / 2,
                basePoint.y + (baseSize.height - componentSize.height) / 2);
    }

    public static JScrollPane createStrippedScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        return scrollPane;
    }
}

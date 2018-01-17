package com.joey.ice.chat.server;

import Glacier2.PermissionDeniedException;
import Glacier2.SSLInfo;
import Ice.Current;
import Ice.StringHolder;

/**
 * <p>DummySSLPermissionsVerifier.java</p>
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
 * 2018/1/9, lvxiaoyi, Initial Version.
 */
public class DummySSLPermissionsVerifier extends Glacier2._SSLPermissionsVerifierDisp {
    @Override
    public boolean authorize(SSLInfo info, StringHolder reason, Current __current) throws PermissionDeniedException {

        return false;
    }
}

package com.joey.ice.chat.server;

import Glacier2.PermissionDeniedException;
import Ice.Current;
import Ice.StringHolder;

import static java.lang.System.out;

/**
 * <p>DummyPermissionsVerifier.java</p>
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
public class DummyPermissionsVerifier extends Glacier2._PermissionsVerifierDisp {
    @Override
    public boolean checkPermissions(String userId, String password, StringHolder reason, Current __current) throws PermissionDeniedException {
        out.println("verified user '" + userId + "' with password '" + password + "'");

        //todo: verify the user/password from DB realm.
        reason = new StringHolder("Ok");
        return true;
    }
}

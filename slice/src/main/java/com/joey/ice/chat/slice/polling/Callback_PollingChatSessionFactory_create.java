// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.3
//
// <auto-generated>
//
// Generated from file `PollingChat.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.joey.ice.chat.slice.polling;

public abstract class Callback_PollingChatSessionFactory_create
    extends IceInternal.TwowayCallback implements Ice.TwowayCallbackArg1UE<com.joey.ice.chat.slice.polling.PollingChatSessionPrx>
{
    public final void __completed(Ice.AsyncResult __result)
    {
        PollingChatSessionFactoryPrxHelper.__create_completed(this, __result);
    }
}

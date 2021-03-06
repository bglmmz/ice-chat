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

public interface PollingChatSessionFactoryPrx extends Ice.ObjectPrx
{
    public PollingChatSessionPrx create(String name, String password)
        throws com.joey.ice.chat.slice.common.CannotCreateSessionException;

    public PollingChatSessionPrx create(String name, String password, java.util.Map<String, String> __ctx)
        throws com.joey.ice.chat.slice.common.CannotCreateSessionException;

    public Ice.AsyncResult begin_create(String name, String password);

    public Ice.AsyncResult begin_create(String name, String password, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_create(String name, String password, Ice.Callback __cb);

    public Ice.AsyncResult begin_create(String name, String password, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_create(String name, String password, Callback_PollingChatSessionFactory_create __cb);

    public Ice.AsyncResult begin_create(String name, String password, java.util.Map<String, String> __ctx, Callback_PollingChatSessionFactory_create __cb);

    public Ice.AsyncResult begin_create(String name, 
                                        String password, 
                                        IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_create(String name, 
                                        String password, 
                                        IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                        IceInternal.Functional_BoolCallback __sentCb);

    public Ice.AsyncResult begin_create(String name, 
                                        String password, 
                                        java.util.Map<String, String> __ctx, 
                                        IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_create(String name, 
                                        String password, 
                                        java.util.Map<String, String> __ctx, 
                                        IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                        IceInternal.Functional_BoolCallback __sentCb);

    public PollingChatSessionPrx end_create(Ice.AsyncResult __result)
        throws com.joey.ice.chat.slice.common.CannotCreateSessionException;
}

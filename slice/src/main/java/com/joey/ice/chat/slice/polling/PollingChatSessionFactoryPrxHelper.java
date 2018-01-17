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

/**
 * Provides type-specific helper functions.
 **/
public final class PollingChatSessionFactoryPrxHelper extends Ice.ObjectPrxHelperBase implements PollingChatSessionFactoryPrx
{
    private static final String __create_name = "create";

    public PollingChatSessionPrx create(String name, String password)
        throws com.joey.ice.chat.slice.common.CannotCreateSessionException
    {
        return create(name, password, null, false);
    }

    public PollingChatSessionPrx create(String name, String password, java.util.Map<String, String> __ctx)
        throws com.joey.ice.chat.slice.common.CannotCreateSessionException
    {
        return create(name, password, __ctx, true);
    }

    private PollingChatSessionPrx create(String name, String password, java.util.Map<String, String> __ctx, boolean __explicitCtx)
        throws com.joey.ice.chat.slice.common.CannotCreateSessionException
    {
        __checkTwowayOnly(__create_name);
        return end_create(begin_create(name, password, __ctx, __explicitCtx, true, null));
    }

    public Ice.AsyncResult begin_create(String name, String password)
    {
        return begin_create(name, password, null, false, false, null);
    }

    public Ice.AsyncResult begin_create(String name, String password, java.util.Map<String, String> __ctx)
    {
        return begin_create(name, password, __ctx, true, false, null);
    }

    public Ice.AsyncResult begin_create(String name, String password, Ice.Callback __cb)
    {
        return begin_create(name, password, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_create(String name, String password, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_create(name, password, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_create(String name, String password, Callback_PollingChatSessionFactory_create __cb)
    {
        return begin_create(name, password, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_create(String name, String password, java.util.Map<String, String> __ctx, Callback_PollingChatSessionFactory_create __cb)
    {
        return begin_create(name, password, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_create(String name, 
                                        String password, 
                                        IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_create(name, password, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_create(String name, 
                                        String password, 
                                        IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                        IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_create(name, password, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    public Ice.AsyncResult begin_create(String name, 
                                        String password, 
                                        java.util.Map<String, String> __ctx, 
                                        IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_create(name, password, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_create(String name, 
                                        String password, 
                                        java.util.Map<String, String> __ctx, 
                                        IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                        IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_create(name, password, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    private Ice.AsyncResult begin_create(String name, 
                                         String password, 
                                         java.util.Map<String, String> __ctx, 
                                         boolean __explicitCtx, 
                                         boolean __synchronous, 
                                         IceInternal.Functional_GenericCallback1<PollingChatSessionPrx> __responseCb, 
                                         IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                         IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_create(name, password, __ctx, __explicitCtx, __synchronous, 
                            new IceInternal.Functional_TwowayCallbackArg1UE<com.joey.ice.chat.slice.polling.PollingChatSessionPrx>(__responseCb, __userExceptionCb, __exceptionCb, __sentCb)
                                {
                                    public final void __completed(Ice.AsyncResult __result)
                                    {
                                        PollingChatSessionFactoryPrxHelper.__create_completed(this, __result);
                                    }
                                });
    }

    private Ice.AsyncResult begin_create(String name, 
                                         String password, 
                                         java.util.Map<String, String> __ctx, 
                                         boolean __explicitCtx, 
                                         boolean __synchronous, 
                                         IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__create_name);
        IceInternal.OutgoingAsync __result = getOutgoingAsync(__create_name, __cb);
        try
        {
            __result.prepare(__create_name, Ice.OperationMode.Normal, __ctx, __explicitCtx, __synchronous);
            IceInternal.BasicStream __os = __result.startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(name);
            __os.writeString(password);
            __result.endWriteParams();
            __result.invoke();
        }
        catch(Ice.Exception __ex)
        {
            __result.abort(__ex);
        }
        return __result;
    }

    public PollingChatSessionPrx end_create(Ice.AsyncResult __iresult)
        throws com.joey.ice.chat.slice.common.CannotCreateSessionException
    {
        IceInternal.OutgoingAsync __result = IceInternal.OutgoingAsync.check(__iresult, this, __create_name);
        try
        {
            if(!__result.__wait())
            {
                try
                {
                    __result.throwUserException();
                }
                catch(com.joey.ice.chat.slice.common.CannotCreateSessionException __ex)
                {
                    throw __ex;
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.startReadParams();
            PollingChatSessionPrx __ret;
            __ret = PollingChatSessionPrxHelper.__read(__is);
            __result.endReadParams();
            return __ret;
        }
        finally
        {
            if(__result != null)
            {
                __result.cacheMessageBuffers();
            }
        }
    }

    static public void __create_completed(Ice.TwowayCallbackArg1UE<PollingChatSessionPrx> __cb, Ice.AsyncResult __result)
    {
        com.joey.ice.chat.slice.polling.PollingChatSessionFactoryPrx __proxy = (com.joey.ice.chat.slice.polling.PollingChatSessionFactoryPrx)__result.getProxy();
        PollingChatSessionPrx __ret = null;
        try
        {
            __ret = __proxy.end_create(__result);
        }
        catch(Ice.UserException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.LocalException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.SystemException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        __cb.response(__ret);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param __obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    public static PollingChatSessionFactoryPrx checkedCast(Ice.ObjectPrx __obj)
    {
        return checkedCastImpl(__obj, ice_staticId(), PollingChatSessionFactoryPrx.class, PollingChatSessionFactoryPrxHelper.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param __obj The untyped proxy.
     * @param __ctx The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    public static PollingChatSessionFactoryPrx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        return checkedCastImpl(__obj, __ctx, ice_staticId(), PollingChatSessionFactoryPrx.class, PollingChatSessionFactoryPrxHelper.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param __obj The untyped proxy.
     * @param __facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    public static PollingChatSessionFactoryPrx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        return checkedCastImpl(__obj, __facet, ice_staticId(), PollingChatSessionFactoryPrx.class, PollingChatSessionFactoryPrxHelper.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param __obj The untyped proxy.
     * @param __facet The name of the desired facet.
     * @param __ctx The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    public static PollingChatSessionFactoryPrx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        return checkedCastImpl(__obj, __facet, __ctx, ice_staticId(), PollingChatSessionFactoryPrx.class, PollingChatSessionFactoryPrxHelper.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param __obj The untyped proxy.
     * @return A proxy for this type.
     **/
    public static PollingChatSessionFactoryPrx uncheckedCast(Ice.ObjectPrx __obj)
    {
        return uncheckedCastImpl(__obj, PollingChatSessionFactoryPrx.class, PollingChatSessionFactoryPrxHelper.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param __obj The untyped proxy.
     * @param __facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    public static PollingChatSessionFactoryPrx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        return uncheckedCastImpl(__obj, __facet, PollingChatSessionFactoryPrx.class, PollingChatSessionFactoryPrxHelper.class);
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::polling::PollingChatSessionFactory"
    };

    /**
     * Provides the Slice type ID of this type.
     * @return The Slice type ID.
     **/
    public static String ice_staticId()
    {
        return __ids[1];
    }

    public static void __write(IceInternal.BasicStream __os, PollingChatSessionFactoryPrx v)
    {
        __os.writeProxy(v);
    }

    public static PollingChatSessionFactoryPrx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            PollingChatSessionFactoryPrxHelper result = new PollingChatSessionFactoryPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}
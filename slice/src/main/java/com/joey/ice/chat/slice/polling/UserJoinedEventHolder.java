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

public final class UserJoinedEventHolder extends Ice.ObjectHolderBase<UserJoinedEvent>
{
    public
    UserJoinedEventHolder()
    {
    }

    public
    UserJoinedEventHolder(UserJoinedEvent value)
    {
        this.value = value;
    }

    public void
    patch(Ice.Object v)
    {
        if(v == null || v instanceof UserJoinedEvent)
        {
            value = (UserJoinedEvent)v;
        }
        else
        {
            IceInternal.Ex.throwUOE(type(), v);
        }
    }

    public String
    type()
    {
        return UserJoinedEvent.ice_staticId();
    }
}

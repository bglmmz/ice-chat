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
// Generated from file `PushChat.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.joey.ice.chat.slice.push;

public interface _ChatRoomCallbackOperationsNC
{
    void init(String[] users);

    void join(long timestamp, String name);

    void leave(long timestamp, String name);

    void send(long timestamp, String name, String message);
}

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

public class ChatRoomEvent extends Ice.ObjectImpl
{
    public ChatRoomEvent()
    {
        name = "";
    }

    public ChatRoomEvent(long timestamp, String name)
    {
        this.timestamp = timestamp;
        this.name = name;
    }

    private static class __F implements Ice.ObjectFactory
    {
        public Ice.Object create(String type)
        {
            assert(type.equals(ice_staticId()));
            return new ChatRoomEvent();
        }

        public void destroy()
        {
        }
    }
    private static Ice.ObjectFactory _factory = new __F();

    public static Ice.ObjectFactory
    ice_factory()
    {
        return _factory;
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::polling::ChatRoomEvent"
    };

    public boolean ice_isA(String s)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public boolean ice_isA(String s, Ice.Current __current)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public String[] ice_ids()
    {
        return __ids;
    }

    public String[] ice_ids(Ice.Current __current)
    {
        return __ids;
    }

    public String ice_id()
    {
        return __ids[1];
    }

    public String ice_id(Ice.Current __current)
    {
        return __ids[1];
    }

    public static String ice_staticId()
    {
        return __ids[1];
    }

    protected void __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice(ice_staticId(), -1, true);
        __os.writeLong(timestamp);
        __os.writeString(name);
        __os.endWriteSlice();
    }

    protected void __readImpl(IceInternal.BasicStream __is)
    {
        __is.startReadSlice();
        timestamp = __is.readLong();
        name = __is.readString();
        __is.endReadSlice();
    }

    public long timestamp;

    public String name;

    public ChatRoomEvent
    clone()
    {
        return (ChatRoomEvent)super.clone();
    }

    public static final long serialVersionUID = 1868204234L;
}

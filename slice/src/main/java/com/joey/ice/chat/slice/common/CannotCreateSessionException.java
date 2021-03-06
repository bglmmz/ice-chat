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
// Generated from file `Common.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.joey.ice.chat.slice.common;

public class CannotCreateSessionException extends Ice.UserException
{
    public CannotCreateSessionException()
    {
        reason = "";
    }

    public CannotCreateSessionException(Throwable __cause)
    {
        super(__cause);
        reason = "";
    }

    public CannotCreateSessionException(String reason)
    {
        this.reason = reason;
    }

    public CannotCreateSessionException(String reason, Throwable __cause)
    {
        super(__cause);
        this.reason = reason;
    }

    public String
    ice_name()
    {
        return "common::CannotCreateSessionException";
    }

    public String reason;

    protected void
    __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice("::common::CannotCreateSessionException", -1, true);
        __os.writeString(reason);
        __os.endWriteSlice();
    }

    protected void
    __readImpl(IceInternal.BasicStream __is)
    {
        __is.startReadSlice();
        reason = __is.readString();
        __is.endReadSlice();
    }

    public static final long serialVersionUID = 1692097937L;
}

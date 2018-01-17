#include "Glacier2/Session.ice"
#include "Common.ice"

[["java:package:com.joey.ice.chat.slice"]]

// Slice
module push {
    // Implemented by clients
    interface ChatRoomCallback {
        void init(Ice::StringSeq users);
        void join(long timestamp, string name);
        void leave(long timestamp, string name);
        void send(long timestamp, string name, string message);
    };


    // Implemented by server
    interface ChatSession extends Glacier2::Session {
        void setCallback(ChatRoomCallback* cb);
        long send(string message) throws common::InvalidMessageException;
    };
};
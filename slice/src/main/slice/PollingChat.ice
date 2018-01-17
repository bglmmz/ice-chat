#include "Glacier2/Session.ice"
#include "Common.ice"

[["java:package:com.joey.ice.chat.slice"]]

// Slice
module polling {
    class ChatRoomEvent {
        long timestamp;
        string name;
    };


    class UserJoinedEvent extends ChatRoomEvent{ };
    class UserLeftEvent extends ChatRoomEvent { };
    class MessageEvent extends ChatRoomEvent { string message; };

    sequence<ChatRoomEvent> ChatRoomEventSeq;

    //server
    interface PollingChatSession {
        Ice::StringSeq getInitialUsers();
        ChatRoomEventSeq getUpdates();
        long send(string message) throws common::InvalidMessageException;
        void destroy();
    };

    interface PollingChatSessionFactory {
        PollingChatSession* create(string name, string password) throws common::CannotCreateSessionException;
    };
};
#include "Glacier2/Session.ice"

[["java:package:com.joey.ice.chat.slice"]]

// Slice
module common {
    exception InvalidMessageException {
        string reason;
    };

    exception CannotCreateSessionException {
        string reason;
    };
};
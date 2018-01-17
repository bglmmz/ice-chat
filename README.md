To run the server:

gradelw installAll

To run the demo:

Start the server:

java -jar ice-chat-server --Ice.Config=config/config.chatserver

In a separate window, start the Glacier2 router:

glacier2router --Ice.Config=config/config.glacier2

In a separate window, start the client:

java -jar ice-chat-client --Ice.Config=config/config.client

package com.hackathon.broadcastserver.shell;

import com.hackathon.broadcastserver.handler.WebSocketMessageHandler;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@ShellComponent
public class ShellHandler {

    private final WebSocketMessageHandler webSocketMessageHandler;

    public ShellHandler(WebSocketMessageHandler webSocketMessageHandler) {
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    @ShellMethod(key = "broadcast-server start", value = "Start the broadcast server")
    public String startServer() {
        return "Broadcast server started on ws://localhost:8080/connect.";
    }

    @ShellMethod(key = "broadcast-server connect", value = "Connect as client to the broadcast server. \nConnect by entering ws://localhost:8080/connect?name=<your-name>")
    public String connectClient() {
        return "Broadcast server connected on ws://localhost:8080/connect?name=<your-name>";
    }

    @ShellMethod(key = "broadcast-server stats", value = "Show the number of active clients and their IPs")
    public String showStats() {
        Map<WebSocketSession, String> activeUsers = webSocketMessageHandler.getActiveUsersWithNames();
        int activeUsersCount = activeUsers.size();

        StringBuilder result = new StringBuilder("Active Users: " + activeUsersCount + "\n");
        activeUsers.forEach((session, ip) -> {
                result.append("User: ").
                        append(session.getId()).
                        append(", IP: ").append(ip).
                        append("\n");
        });

        return result.toString();
    }

    @ShellMethod(key = "broadcast-server stop", value = "Stop the broadcast server")
    public String stopServer() throws IOException {
        webSocketMessageHandler.stopServer();
        return "Broadcast server stopped";
    }
}

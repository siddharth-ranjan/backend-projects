package com.hackathon.realtimecollaborativetextapp.controller;

import com.hackathon.realtimecollaborativetextapp.model.DocumentModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class DocumentController {

    private final AtomicReference<DocumentModel> documentModel =
            new AtomicReference<>(new DocumentModel(0, "Initial content."));

    private final SimpMessagingTemplate messagingTemplate;

    public DocumentController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/document/edit")
    @SendTo("/topic/document/updates")
    public DocumentModel handleEdit(DocumentModel incomingChange, SimpMessageHeaderAccessor headerAccessor) {
        DocumentModel currentState = documentModel.get();

        if(incomingChange.getVersion() != currentState.getVersion()) {
            System.err.println("Conflict detected! Server version: " + currentState.getVersion() +
                    ", Client version: " + incomingChange.getVersion());

            String sessionId = headerAccessor.getSessionId();

            // send back an error message to the specific user who sent the conflicting change
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/errors",
                    "Your version is outdated. Please refresh your document.");


            return null; // Do not broadcast anything
        }

        DocumentModel newState = new DocumentModel(
                incomingChange.getVersion() + 1,
                incomingChange.getContent()
        );

        documentModel.set(newState);
        System.out.println("Update successful. New version: " + newState.getVersion());
        return newState; // Broadcast the new state to all subscribed clients
    }

    @MessageMapping("/document/get")
    @SendTo("/topic/document/updates")
    public DocumentModel getDocumentState() {
        return documentModel.get(); // Return the current document state to the client
    }
}

package com.hackathon.realtimelearderboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class LoggingTestController {

    @GetMapping("/logging")
    public ResponseEntity<Map<String, String>> testLogging(
            @RequestParam(defaultValue = "INFO") String level) {
        
        // Set MDC context for structured logging
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        MDC.put("endpoint", "/api/test/logging");
        
        try {
            log.info("Testing logging endpoint with level: {}", level);
            
            switch (level.toUpperCase()) {
                case "DEBUG":
                    log.debug("This is a DEBUG level message for request: {}", requestId);
                    break;
                case "INFO":
                    log.info("This is an INFO level message for request: {}", requestId);
                    break;
                case "WARN":
                    log.warn("This is a WARN level message for request: {}", requestId);
                    break;
                case "ERROR":
                    log.error("This is an ERROR level message for request: {}", requestId);
                    break;
                default:
                    log.info("Default INFO level message for request: {}", requestId);
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logging test completed successfully");
            response.put("level", level);
            response.put("requestId", requestId);
            response.put("note", "Check console and log files for output");
            
            log.info("Returning response for request: {}", requestId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error occurred during logging test for request: {}", requestId, e);
            throw e;
        } finally {
            // Clean up MDC
            MDC.clear();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        log.debug("Health check endpoint called");
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Real-time leaderboard is running");
        
        return ResponseEntity.ok(response);
    }
}
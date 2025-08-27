package com.hackathon.realtimelearderboard.controller;

import com.hackathon.realtimelearderboard.dto.LeaderboardEntry;
import com.hackathon.realtimelearderboard.dto.ScoreSubmissionRequest;
import com.hackathon.realtimelearderboard.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
@Slf4j
public class LeaderboardController {

    private final LeaderboardService leaderboardService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/scores")
    public ResponseEntity<Void> submitScore(@RequestBody ScoreSubmissionRequest request, Authentication authentication) {
        String username = authentication.getName();
        leaderboardService.submitScore(username, request.getScore());

        broadcastLeaderboardUpdate();

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard() {
        return ResponseEntity.ok(leaderboardService.getTopN(10));
    }

    @GetMapping("/my-rank")
    public ResponseEntity<LeaderboardEntry> getMyRank(Authentication authentication) {
        String username = authentication.getName();
        LeaderboardEntry rank = leaderboardService.getUserRank(username);
        return ResponseEntity.ok(rank);
    }

    private void broadcastLeaderboardUpdate() {
        log.info("Broadcasting leaderboard update to WebSocket clients");
        List<LeaderboardEntry> top10 = leaderboardService.getTopN(10);

        messagingTemplate.convertAndSend("/topic/leaderboard", top10);
    }
}
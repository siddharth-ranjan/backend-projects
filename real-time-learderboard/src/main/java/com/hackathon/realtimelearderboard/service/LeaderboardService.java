package com.hackathon.realtimelearderboard.service;

import com.hackathon.realtimelearderboard.dto.LeaderboardEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaderboardService {

    private static final String LEADERBOARD_KEY = "global_leaderboard";
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Submits a score for a user. If the user already has a score, it will be updated.
     * @param username The user's username.
     * @param score The score to submit.
     */
    public void submitScore(String username, double score) {
        log.info("Submitting score {} for user '{}'", score, username);
        redisTemplate.opsForZSet().add(LEADERBOARD_KEY, username, score);
    }

    /**
     * Retrieves the top N entries from the leaderboard.
     * @param topN The number of top entries to retrieve.
     * @return A list of LeaderboardEntry objects.
     */
    public List<LeaderboardEntry> getTopN(int topN) {
        log.debug("Fetching top {} players from the leaderboard", topN);
        // ZREVRANGE fetches a range of members from a sorted set, ordered from highest to lowest score.
        Set<TypedTuple<String>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(LEADERBOARD_KEY, 0, topN - 1);

        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        if (tuples != null) {
            long rank = 1;
            for (TypedTuple<String> tuple : tuples) {
                leaderboard.add(new LeaderboardEntry(rank++, tuple.getValue(), tuple.getScore()));
            }
        }
        return leaderboard;
    }

    /**
     * Gets the rank of a specific user.
     * @param username The username to find the rank for.
     * @return A LeaderboardEntry containing the user's rank and score, or null if not found.
     */
    public LeaderboardEntry getUserRank(String username) {
        log.debug("Fetching rank for user '{}'", username);
        // ZREVRANK gets the 0-based rank of a member, ordered from highest to lowest score.
        Long rank = redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, username);
        if (rank == null) {
            return null; // User is not on the leaderboard
        }

        Double score = redisTemplate.opsForZSet().score(LEADERBOARD_KEY, username);
        return new LeaderboardEntry(rank + 1, username, score);
    }
}
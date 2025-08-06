package com.hackathon.githubuseractivity.runner;

import com.hackathon.githubuseractivity.record.GithubEvent;
import com.hackathon.githubuseractivity.service.GithubService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class AppRunner implements CommandLineRunner {

    private final GithubService githubService;

    public AppRunner(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            System.out.println("Please provide a GitHub username.");
            return;
        }

        String username = args[0];
        System.out.println("Fetching activity for user: " + username + "\n");

        githubService.getUserActivity(username)
                .doOnNext(event -> {
                    String action = formatEvent(event);
                    System.out.println("• " + action);
                })
                .doOnTerminate(() -> {
                    System.out.println("\nFinished fetching activity.");
                    System.exit(0);
                })
                .subscribe();
    }

    private String formatEvent(GithubEvent event) {
        String action;
        Map<String, Object> payload = event.payload();

        action = switch (event.type()) {
            case "PushEvent" -> {
                int commitCount = ((List<?>) payload.get("commits")).size();
                yield String.format("Pushed %d commit(s) to %s", commitCount, event.repo().name());
            }
            case "IssuesEvent" -> {
                String issueAction = (String) payload.get("action");
                yield String.format("%s an issue in %s", capitalize(issueAction), event.repo().name());
            }
            case "WatchEvent" -> String.format("Starred %s", event.repo().name());
            case "ForkEvent" -> String.format("Forked %s", event.repo().name());
            case "CreateEvent" -> {
                String refType = (String) payload.get("ref_type");
                yield String.format("Created a %s in %s", refType, event.repo().name());
            }
            case "PullRequestEvent" -> {
                String prAction = (String) payload.get("action"); // e.g., "opened", "closed"
                yield String.format("%s a pull request in %s", capitalize(prAction), event.repo().name());
            }
            default -> String.format("%s at %s", event.type().replace("Event", ""), event.repo().name());
        };
        return action;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
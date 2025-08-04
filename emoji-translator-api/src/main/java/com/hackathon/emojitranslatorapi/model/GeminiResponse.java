package com.hackathon.emojitranslatorapi.model;

public class GeminiResponse {
    private Candidate[] candidates;

    public Candidate[] getCandidates() {
        return candidates;
    }

    public static class Candidate {
        private Content content;

        public Content getContent() {
            return content;
        }
    }

    public static class Content {
        private GeminiRequest.Part[] parts;

        public GeminiRequest.Part[] getParts() {
            return parts;
        }
    }
}

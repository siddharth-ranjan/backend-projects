package com.hackathon.emojitranslatorapi.model;

public class GeminiRequest {

    public Body[] contents;

    public GeminiRequest(Body[] contents) {
        this.contents = contents;
    }

    public static class Body {
        public Part[] parts;

        public Body(Part[] parts) {
            this.parts = parts;
        }
    }

    public static class Part {
        public String text;

        public Part(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
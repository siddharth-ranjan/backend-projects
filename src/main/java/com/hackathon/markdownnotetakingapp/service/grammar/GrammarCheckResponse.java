package com.hackathon.markdownnotetakingapp.service.grammar;

import java.util.List;

public class GrammarCheckResponse {
    private List<String> response;
    private boolean success;

    public GrammarCheckResponse(List<String> response, boolean success) {
        this.response = response;
        this.success = success;
    }

    public GrammarCheckResponse() {
    }

    public List<String> getResponse() {
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "GrammarCheckResponse{" +
                "response='" + response + '\'' +
                ", success=" + success +
                '}';
    }
}

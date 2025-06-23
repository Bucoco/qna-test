package com.lg2.qna_service.ai.dto;

import lombok.Data;

import java.util.List;

@Data
public class AIResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}

package com.lg2.qna_service.domain.dto.csAnswer;

import com.lg2.qna_service.global.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class CSAnswerResponse {

    @Data
    @Builder
    public static class CSAnswerListResponse {
        private String user_nickname;
        private Long csquestion_id;
        private Category csquestion_category;
        private String csquestion_content;
        private Long csanswer_id;
        private String csanswer_content;
        private LocalDateTime csanswer_created_at;
    }

    @Data
    @Builder
    public static class CSAnswerDetailResponse {
        private Long csquestion_id;
        private Category csquestion_category;
        private String csquestion_content;
        private Long csanswer_id;
        private String csanswer_content;
        private LocalDateTime csanswer_created_at;
        private String csanswer_feedback;
    }

    @Data
    @Builder
    public static class AIFeedbackResponse {
        private Long csanswer_id;
        private String csanswer_feedback;
    }
}
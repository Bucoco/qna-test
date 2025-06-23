package com.lg2.qna_service.domain.dto.csQuestion;

import java.time.LocalDateTime;

import com.lg2.qna_service.global.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSQuestionResponse {

    private Long id;
    private Category category;
    private LocalDateTime createdAt;
    private String content;
    private boolean isSubmitted;

    @Data
    @Builder
    public static class AIQuestionResponse {
        private Category category;
        private LocalDateTime createdAt;
        private String content;
        private String keyword;
    }
}
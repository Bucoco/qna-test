package com.lg2.qna_service.controller.answer;

import com.lg2.qna_service.domain.dto.csAnswer.CSAnswerResponse.AIFeedbackResponse;
import com.lg2.qna_service.global.response.CustomResponse;
import com.lg2.qna_service.service.answer.AIFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIFeedbackController {

    @Autowired
    private AIFeedbackService aiFeedbackService;

    @PostMapping("/api/answers/{answerId}")
    public ResponseEntity<CustomResponse<AIFeedbackResponse>> generateAIFeedback(
            @PathVariable Long answerId) {

        AIFeedbackResponse response = aiFeedbackService.createAIFeedback(answerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomResponse.created(response));
    }
}

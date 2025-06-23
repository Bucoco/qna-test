package com.lg2.qna_service.controller.question;

import com.lg2.qna_service.domain.dto.csQuestion.CSQuestionResponse;
import com.lg2.qna_service.global.response.CustomResponse;
import com.lg2.qna_service.service.question.AIQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lg2.qna_service.domain.dto.csQuestion.CSQuestionResponse.*;


@RestController
public class AIQuestionController {

    @Autowired
    private AIQuestionService aiQuestionService;

    @PostMapping("/api/questions")
    public ResponseEntity<CustomResponse<AIQuestionResponse>> generateAIQuestion() {
        AIQuestionResponse response = aiQuestionService.createAIQuestion();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomResponse.created(response));
    }
}

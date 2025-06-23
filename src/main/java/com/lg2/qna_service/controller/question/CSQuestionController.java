package com.lg2.qna_service.controller.question;

import com.lg2.qna_service.domain.dto.csQuestion.CSQuestionResponse;
import com.lg2.qna_service.global.code.GeneralSuccessCode;
import com.lg2.qna_service.global.response.CustomResponse;
import com.lg2.qna_service.service.question.CSQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class CSQuestionController {

    @Autowired
    CSQuestionService csQuestionService;

    @GetMapping
    public ResponseEntity<?> getCSQuestionList(@RequestParam(required = false) String category,
                                               @RequestParam(defaultValue = "1") int page) {
        Order order = Order.desc("id");
        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(page - 1, 10, sort);

        Page<CSQuestionResponse> list = csQuestionService.getCSQuestionList(category, pageable);
        return ResponseEntity.ok(CustomResponse.ok(list));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getCSQuestion(@PathVariable Long questionId) {
        CSQuestionResponse question = csQuestionService.getCSQuestion(questionId);

        return ResponseEntity.ok(CustomResponse.ok(question));
    }

    @GetMapping("/today")
    public ResponseEntity<?> getTodayCSQuestion() {
        CSQuestionResponse question = csQuestionService.getTodayCSQuestion();

        return ResponseEntity.ok(CustomResponse.ok(question));
    }

    @PostMapping("/{questionId}/delete")
    public ResponseEntity<?> deleteCSQuestion(@PathVariable Long questionId) {
        csQuestionService.deleteQuestion(questionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(CustomResponse.success(GeneralSuccessCode._DELETED, null));
    }
}

package com.lg2.qna_service.service.question;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import com.lg2.qna_service.domain.CSAnswer;
import com.lg2.qna_service.domain.CSQuestion;
import com.lg2.qna_service.domain.dto.csQuestion.CSQuestionResponse;
import com.lg2.qna_service.global.domain.Category;
import com.lg2.qna_service.remote.RemoteUserService;
import com.lg2.qna_service.remote.UserDto;
import com.lg2.qna_service.repository.CSAnswerRepository;
import com.lg2.qna_service.repository.CSQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CSQuestionService {
    @Autowired
    CSQuestionRepository csQuestionRepository;

    @Autowired
    CSAnswerRepository csAnswerRepository;

    @Autowired
    RemoteUserService userService;

    public Page<CSQuestionResponse> getCSQuestionList(String categoryName, Pageable pageable, UserDetails userDetails) {
        Page<CSQuestion> questions;
        Set<Long> submittedQuestionsIds = new HashSet<>();
        UserDto userDto = (userDetails != null) ? getUserFromDetails(userDetails) : null;

        if (categoryName == null) {
            questions = csQuestionRepository.findAll(pageable);

        } else {
            try {
                Category category = Category.valueOf(categoryName);
                questions = csQuestionRepository.findByCategory(category, pageable);
            } catch (IllegalArgumentException e) {
                questions = csQuestionRepository.findAll(pageable);
            }
        }

        if (userDto != null) {
            List<CSAnswer> answers = csAnswerRepository.findAllById(userDto.getId());
            submittedQuestionsIds
                    .addAll(answers.stream().map((a) -> a.getCsQuestion().getId()).collect(Collectors.toSet()));
        }

        return questions.map(q -> new CSQuestionResponse(q.getId(), q.getCategory(), q.getCreatedAt(), q.getContent(),
                userDto != null && submittedQuestionsIds.contains(q.getId())));
    }

    public CSQuestionResponse getCSQuestion(Long id) {
        CSQuestion q = csQuestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));

        CSQuestionResponse res = new CSQuestionResponse(q.getId(), q.getCategory(), q.getCreatedAt(), q.getContent(),
                false);

        return res;
    }

    public CSQuestionResponse getTodayCSQuestion() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        CSQuestion q = csQuestionRepository.findFirstByCreatedAtBetweenOrderByCreatedAtDesc(startOfDay, endOfDay);

        if (q == null) {
            throw new NoSuchElementException("오늘의 질문이 아직 등록되지 않았습니다.");
        }

        CSQuestionResponse res = new CSQuestionResponse(q.getId(), q.getCategory(), q.getCreatedAt(), q.getContent(),
                false);

        return res;
    }

    public void deleteQuestion(Long id) {
        CSQuestion q = csQuestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));

        csQuestionRepository.delete(q);
    }

    private UserDto getUserFromDetails(UserDetails userDetails) {
        return userService.getUser("Bearer " + userDetails.getUsername());
    }

}

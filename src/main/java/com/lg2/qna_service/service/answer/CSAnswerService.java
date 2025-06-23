package com.lg2.qna_service.service.answer;

import com.lg2.qna_service.common.web.context.GatewayRequestHeaderUtils;
import com.lg2.qna_service.domain.CSAnswer;
import com.lg2.qna_service.domain.CSQuestion;
import com.lg2.qna_service.domain.dto.csAnswer.CSAnswerRequest;
import com.lg2.qna_service.domain.dto.csAnswer.CSAnswerResponse;
import com.lg2.qna_service.repository.CSAnswerRepository;
import com.lg2.qna_service.repository.CSQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CSAnswerService {

    @Autowired
    private CSAnswerRepository csAnswerRepository;

    @Autowired
    private CSQuestionRepository csQuestionRepository;

    // 답변 작성
    public CSAnswerResponse.CSAnswerDetailResponse createAnswer(CSAnswerRequest.CSAnswerCreateRequest request) {
        Long userId = Long.valueOf(GatewayRequestHeaderUtils.getUserIdOrThrowException());

        CSQuestion question = csQuestionRepository.findById(request.getCsquestion_id()) // 400
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));

        CSAnswer answer = new CSAnswer();
        answer.setContent(request.getCsanswer_content());
        answer.setCreatedAt(LocalDateTime.now());
        answer.setFeedback("아직 피드백 없음");
        answer.setCsQuestion(question);
        answer.setUserId(userId);

        csAnswerRepository.save(answer);

        return CSAnswerResponse.CSAnswerDetailResponse.builder()
                .csquestion_id(question.getId())
                .csquestion_category(question.getCategory())
                .csquestion_content(question.getContent())
                .csanswer_id(answer.getId())
                .csanswer_content(answer.getContent())
                .csanswer_created_at(answer.getCreatedAt())
                .csanswer_feedback(answer.getFeedback())
                .build();
    }

    // 답변 리스트 조회 (페이지, 특정 질문)
    public Page<CSAnswerResponse.CSAnswerListResponse> getAnswerList(Pageable pageable, Long questionId) {
        Long userId = Long.valueOf(GatewayRequestHeaderUtils.getUserIdOrThrowException());

        Page<CSAnswer> answers;
        if (questionId == null) {
            answers = csAnswerRepository.findAllById(userId, pageable);
        } else {
            CSQuestion tquestion = csQuestionRepository.findById(questionId) // 400
                    .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));
            answers = csAnswerRepository.findAllByUserIdAndCsQuestion(userId, tquestion, pageable);
        }

        return answers.map(answer -> {
            CSQuestion question = answer.getCsQuestion();
            return CSAnswerResponse.CSAnswerListResponse.builder()
                    .user_nickname(answer.getUserNickname())
                    .csquestion_id(question.getId())
                    .csquestion_category(question.getCategory())
                    .csquestion_content(question.getContent())
                    .csanswer_id(answer.getId())
                    .csanswer_content(answer.getContent())
                    .csanswer_created_at(answer.getCreatedAt())
                    .build();
        });
    }

    // 특정 답변 조회
    public CSAnswerResponse.CSAnswerDetailResponse getAnswerDetail(Long answerId) {
        Long userId = Long.valueOf(GatewayRequestHeaderUtils.getUserIdOrThrowException());

        CSAnswer answer = csAnswerRepository.findById(answerId) // 400
                .orElseThrow(() -> new IllegalArgumentException("답변이 존재하지 않습니다."));

        CSQuestion question = answer.getCsQuestion();

        if (!answer.getUserId().equals(userId)) { // 500
            throw new RuntimeException("자신의 답변만 조회할 수 있습니다.");
        }

        return CSAnswerResponse.CSAnswerDetailResponse.builder()
                .csquestion_id(question.getId())
                .csquestion_category(question.getCategory())
                .csquestion_content(question.getContent())
                .csanswer_id(answer.getId())
                .csanswer_content(answer.getContent())
                .csanswer_created_at(answer.getCreatedAt())
                .csanswer_feedback(answer.getFeedback())
                .build();
    }

    // 답변 수정
    public CSAnswerResponse.CSAnswerDetailResponse updateAnswer(Long answerId, CSAnswerRequest.CSAnswerUpdate request) {
        Long userId = Long.valueOf(GatewayRequestHeaderUtils.getUserIdOrThrowException());

        CSAnswer answer = csAnswerRepository.findById(answerId) // 400
                .orElseThrow(() -> new IllegalArgumentException("답변이 존재하지 않습니다."));

        CSQuestion question = answer.getCsQuestion();

        if (!answer.getUserId().equals(userId)) { // 500
            throw new RuntimeException("자신의 답변만 수정할 수 있습니다.");
        }

        answer.setContent(request.getCsanswer_content());
        answer.setFeedback("아직 피드백 없음");
        csAnswerRepository.save(answer);

        return CSAnswerResponse.CSAnswerDetailResponse.builder()
                .csquestion_id(question.getId())
                .csquestion_category(question.getCategory())
                .csquestion_content(question.getContent())
                .csanswer_id(answer.getId())
                .csanswer_content(answer.getContent())
                .csanswer_created_at(answer.getCreatedAt())
                .csanswer_feedback(answer.getFeedback())
                .build();
    }

    // 답변 삭제
    public void deleteAnswer(Long answerId) {
        Long userId = Long.valueOf(GatewayRequestHeaderUtils.getUserIdOrThrowException());

        CSAnswer answer = csAnswerRepository.findById(answerId) // 400
                .orElseThrow(() -> new IllegalArgumentException("답변이 존재하지 않습니다."));

        if (!answer.getUserId().equals(userId)) { // 500
            throw new RuntimeException("자신의 답변만 삭제할 수 있습니다.");
        }
        csAnswerRepository.deleteById(answerId);
    }
}

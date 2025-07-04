package com.lg2.qna_service.repository;

import com.lg2.qna_service.domain.CSQuestion;
import com.lg2.qna_service.global.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CSQuestionRepository extends JpaRepository<CSQuestion, Long> {

    Page<CSQuestion> findByCategory(Category category, Pageable pageable);

    CSQuestion findFirstByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);
}
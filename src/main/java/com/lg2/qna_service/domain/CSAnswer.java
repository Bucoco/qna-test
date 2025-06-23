package com.lg2.qna_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CSAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Lob
    @Column(nullable = false)
    private String feedback;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private CSQuestion csQuestion;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userNickname;
}

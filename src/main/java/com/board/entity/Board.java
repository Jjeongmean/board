package com.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime regDate;

    @Column(nullable = false, length = 50) //not null지정, 크기 최대 50
    private String title;
    private LocalDateTime updateDate;

    private Member member;


}

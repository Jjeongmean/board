package com.board.entity;

import com.board.dto.BoardFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //Board가 Member를 참조

    //board 엔티티 수정
    public void updateBoard(BoardFormDto boardFormDto) {
        this.content = boardFormDto.getContent();
        this.regDate = boardFormDto.getRegDate();
        this.title = boardFormDto.getTitle();
        this.updateDate = boardFormDto.getUpdateDate();
    }

}

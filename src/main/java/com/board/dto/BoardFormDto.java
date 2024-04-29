package com.board.dto;

import com.board.entity.Board;
import com.board.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardFormDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    private Member member;

    //model mapper 사용
    private static ModelMapper modelMapper = new ModelMapper();

    // dto ➡ entity
    public Board createBoard() {
        return modelMapper.map(this, Board.class); //Board 엔티티 객체 리턴한다
    }

    // entity ➡ dto
    public static BoardFormDto of (Board board) {
        return modelMapper.map(board,BoardFormDto.class); //BoardFormDto 객체 리턴
    }

}

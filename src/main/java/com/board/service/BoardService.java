package com.board.service;

import com.board.dto.BoardFormDto;
import com.board.dto.BoardSearchDto;
import com.board.entity.Board;
import com.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
   private final BoardRepository boardRepository;

   // 글 등록하기
    public Long saveBoard(BoardFormDto boardFormDto) throws Exception {
        // 1. BoardFormDto를 Board 엔티티로 변환
        Board board = boardFormDto.createBoard();

        // 2. BoardRepository를 사용하여 엔티티 저장
        boardRepository.save(board);
        // 3. 저장된 엔티티의 ID를 반환
        return board.getId();
    }

    //불러오기
    @Transactional(readOnly = true)
    public BoardFormDto getBoardDtl(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);

        BoardFormDto boardFormDto = BoardFormDto.of(board);

        return boardFormDto;
    }

    //글 수정하기
    public Long updateBoard(BoardFormDto boardFormDto) throws Exception {
        // board 엔티티 수정
        Board board = boardRepository.findById(boardFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        board.updateBoard(boardFormDto);

        return board.getId();
    }


    @Transactional(readOnly = true)
    public Page<Board> getListPage(BoardSearchDto boardSearchDto, Pageable pageable) {
        Page<Board> boardPage = boardRepository.getBoardPage(boardSearchDto, pageable);
        return boardPage;
    }

}

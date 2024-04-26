package com.board.controller;

import com.board.dto.BoardFormDto;
import com.board.dto.BoardSearchDto;
import com.board.entity.Board;
import com.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@RequestBody BoardFormDto boardFormDto) {
        // BoardFormDto는 게시물 정보를 포함하는 DTO 객체입니다.
        try {
            // BoardService를 통해 게시물을 저장합니다.
            Board newBoard = boardService.createBoard(boardFormDto);
            return ResponseEntity.ok("Board created with ID: " + newBoard.getId());
        } catch (Exception e) {
            // 오류가 발생하면 400 상태 코드와 함께 에러 메시지를 반환합니다.
            return ResponseEntity.badRequest().body("Error creating board: " + e.getMessage());
        }
    }

    @GetMapping(value = {"/list", "/list/{page}"})
    public String boardList(Model model, BoardSearchDto boardSearchDto,
                            @PathVariable(value = "page") Optional<Integer> page) {
        // 페이징 객체 생성
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 6);

        Page<Board> boards = boardService.getListPage(boardSearchDto, pageable);

        // 모델에 페이징 결과를 추가
        model.addAttribute("boards", boards);
        model.addAttribute("boardSearchDto", boardSearchDto);
        model.addAttribute("maxPage", 5);

        return "post/list"; // list.html로 반환
    }
}

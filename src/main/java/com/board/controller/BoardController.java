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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public String boardNew (@RequestBody BoardFormDto boardFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) return "post/list";

        try {
           boardService.saveBoard(boardFormDto);
        } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("errorMessage",
                   "에러가 발생했습니다.");
           return "post/list";
        }
        return "/";
    }


    //게시물 리스트
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

    //게시물 상세 페이지
    @GetMapping("/detail/{boardId}")
    public String boardDtl(Model model, @PathVariable("boardId") Long boardId) {
        BoardService boardFormDto  = boardService;
        model.addAttribute("board", boardFormDto);
        return "post/detail";

    }
}

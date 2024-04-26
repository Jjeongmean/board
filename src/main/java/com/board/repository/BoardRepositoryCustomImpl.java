package com.board.repository;

import com.board.dto.BoardFormDto;
import com.board.dto.BoardSearchDto;
import com.board.entity.Board;
import com.board.entity.QBoard;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable) {
        /*
        * select * from
        * where title like %검색어%
        * order by board_id desc;
         */

        List<Board> content = queryFactory
                .selectFrom(QBoard.board)
                .where(QBoard.board.title.like("%" + boardSearchDto.getSearchQuery() + "%"))
                .orderBy(QBoard.board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QBoard.board)
                .where(QBoard.board.title.like("%" + boardSearchDto.getSearchQuery() + "%"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);

    }
}

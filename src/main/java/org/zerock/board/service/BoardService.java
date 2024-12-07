package org.zerock.board.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;

public interface BoardService {

    Long register(BoardDTO dto);
    
    boolean delete(long bno);

    //void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) //int로 처리하도록
                .build();

        return boardDTO;

    }

    Board findByBno(Long bno); // ID로 게시글 조회

}

package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.BoardRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

	private final BoardRepository repository;

	@Override
	public Long register(BoardDTO dto) {

		log.info(dto);

		Board board = dtoToEntity(dto);

		repository.save(board);

		return board.getBno();
	}

	
	  @Transactional public Board update(Long boardId, String title, String content) {
		  
		 Board dto = repository.findByBoardBno(boardId);
	  
	  if(dto.getTitle() != null) { dto.setTitle(dto.getTitle()); }
	  if(dto.getContent() != null) { dto.setContent(dto.getContent()); }
	  
	return dto;
	  
	 }
	 
	  @Override
	    public boolean delete(long bno) {
	        // Check if the board exists
	        if (repository.existsById(bno)) {
	        	repository.deleteById(bno);  // Delete the board by its ID
	            return true;
	        }
	        return false;  // Return false if board does not exist
	    }
	  
	@Override
	public Board findByBno(Long boardId) {
		// ID로 게시글을 조회하고 DTO로 변환
		Board board = repository.findById(boardId)
				.orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
		return board;
	}

	// 엔티티를 DTO로 변환하는 유틸리티 메서드
	private BoardDTO convertToDTO(Board board) {
		return new BoardDTO(board.getBno(), board.getTitle(), board.getContent(), null, null, null, null, 0);
	}

//	    @Override
//	    @Transactional
//	    public boolean delete(long bno) {
//	        Optional<Board> boardOptional = repository.findById(bno);
//	        if (boardOptional.isPresent()) {
//	        	repository.delete(boardOptional.get());
//	            return true;
//	        }
//	        return false;
//	    }

};

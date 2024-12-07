package org.zerock.board.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@EnableJpaAuditing
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private MemberRepository memberRepository;

	
    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // Member 객체를 데이터베이스에 저장
            Member member = Member.builder().email("user" + i + "@bbb.com").build();
            memberRepository.save(member);

            // Board 객체 생성 및 저장
            Board board = Board.builder()
                .title("Title..." + i)
                .content("Content...." + i)
                .writer(member)  // 저장된 Member를 참조
                .build();

            boardRepository.save(board);
        });
    }

	 @Test
	 public void deleteBoard() {
		 IntStream.rangeClosed(20, 30).forEach(i -> {

			 
			 
	            // Board 객체 생성 및 저장
			 	Board board = Board.builder()
			 			.bno((long) i)
		                .build();
			 	
			 	Board bon = boardRepository.findByBoardBno(board.getBno());
			 	
			 	if(bon != null ) {
			 		boardRepository.deleteById(board.getBno());
			 	}else {
			 		System.out.println("해당 게시글이 없습니다.");
			 	}
			 	
	        });
		 
	 }


    @Transactional
    @Test
    public void testRead1() {
    	//조인명령을 활용한다.
        Optional<Board> result = boardRepository.findById(101L); //데이터베이스에 존재하는 번호

        Board board = result.get();
        
        System.out.println("testRead1()..............");
        System.out.println(board);
        System.out.println(board.getWriter());

    }



    
}

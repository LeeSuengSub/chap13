package org.zerock.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.service.BoardService;
import org.springframework.web.bind.annotation.RequestParam;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

   @GetMapping("/list")
   public void list(Model model){

   }
   
    @GetMapping("/register")
    public void register(){
        log.info("regiser get...");
    }
    
    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);
        //새로 추가된 엔티티의 번호
        dto.setWriterEmail("1111");
        dto.setWriterName("2222");
        Long bno = boardService.register(dto);

        log.info("BNO: " + bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping("/delete/{bno}")
    public String deleteBoardPage(@PathVariable Long bno, Model model) {
        Board board = boardService.findByBno(bno);
        model.addAttribute("board", board);
        return "board/delete"; // Returns the delete page
    }
    
    
    @DeleteMapping("/delete/{bno}")
    public String deleteBoard(@PathVariable Long bno) {
        boardService.delete(bno);
        return "redirect:/board/list"; // Redirect to the board list page after deletion
    }
    
}

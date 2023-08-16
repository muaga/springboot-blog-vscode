package shop.mtcoding.blogstudy01.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.blogstudy01.board.BoardRequest.BoardDetailDTO;
import shop.mtcoding.blogstudy01.board.BoardRequest.WriteDTO;
import shop.mtcoding.blogstudy01.user.User;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpSession session;

    // 메인페이지 겸 글 전체 목록
    @GetMapping({ "/", "/board" })
    public String index(HttpServletRequest request) {
        List<Board> boardList = boardRepository.findByAll();
        request.setAttribute("boardList", boardList);
        return "index";
    }

    // 글쓰기 페이지
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    // 글쓰기 기능
    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("sessionUSer : " + sessionUser);
        boardRepository.save(writeDTO, sessionUser.getId());
        return "redirect:/";
    }

    // 글 상세보기 페이지
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request){
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);
        return "board/detailForm";
    }
    
    // 글 수정 페이지
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);
        // select한 결과를 mustache에게 보내주지 않으면, mustache는 데이터를 찾을 수 없다.
        return "board/updateForm";
    }

    // 글 수정 기능
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO updateDTO){
        boardRepository.update(updateDTO, id);
        return "redirect:/";
    }

    // 글 삭제 기능
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
        boardRepository.delete(id);
        return "redirect:/";
    }










    // 글 상세보기 페이지 with 댓글
    // @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<BoardDetailDTO> boardDetailDTOList = null;
        if (sessionUser == null) {
            boardDetailDTOList = boardRepository.findByIdJoinReply(id, null);
        } else {
            boardDetailDTOList = boardRepository.findByIdJoinReply(id, sessionUser.getId());
        }
        request.setAttribute("boardDetailDTOList", boardDetailDTOList);
        return "board/detailForm";
    }

}

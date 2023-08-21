package shop.mtcoding.blogstudy01.reply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogstudy01._core.util.Script;
import shop.mtcoding.blogstudy01.user.User;

@Controller
public class ReplyController {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private HttpSession session;
    
    // 댓글 등록 기능
    @PostMapping("/reply/save")
    public @ResponseBody String save(ReplyRequest.SaveDTO saveDTO, Integer sessionUserId){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser != null){
            replyRepository.save(saveDTO, sessionUser.getId());
            return Script.href("redirect:/board/" + saveDTO.getBoardId());
        }else{
            return Script.href("/loginForm", "로그인 후 댓글 작성바랍니다.");
        }
    }

    // 댓글 삭제 기능
    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, Integer boardId){
        replyRepository.delete(id);
        return "redirect:/board/" + boardId; 
    }

}

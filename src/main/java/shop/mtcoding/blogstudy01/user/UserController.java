package shop.mtcoding.blogstudy01.user;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blogstudy01.user.UserRequest.JoinDTO;
import shop.mtcoding.blogstudy01.user.UserRequest.LoginDTO;
import shop.mtcoding.blogstudy01.user.UserRequest.UserUpadateDTO;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    // 회원가입 페이지
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // 회원가입 기능
    @PostMapping("/join")
    public String save(UserRequest.JoinDTO joinDTO) {
        userRepository.save(joinDTO);
        return "redirect:/loginForm";
    }

    // 로그인 페이지
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // 로그인 기능
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        // 최초 로그인 시, session에 등록
        User sessionUser = userRepository.findByUsernameAndPassword(loginDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    // 회원수정 페이지
    @GetMapping("/updateForm")
    public String updateForm(HttpServletRequest request) {
        // session에 등록된 유저인지 체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        // session에 등록된 유저의 id로 기존의 회원인지 확인
        User user = userRepository.findById(sessionUser.getId());
        // 해당 id의 유저의 정보를 view
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    // 회원수정 기능
    @PostMapping("/user/update")
    public String update(UserRequest.UserUpadateDTO userUpadateDTO, Integer id) {

        // session에 등록된 유저인지 체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("test : " + sessionUser);

        // session에 등록된 유저의 id로 기존의 회원인지 확인
        User user = userRepository.findById(sessionUser.getId());
        System.out.println("test : " + user);

        // 해당 id의 유정의 정보를 수정
        userRepository.update(userUpadateDTO, sessionUser.getId());
        System.out.println("test : 업데이트 쿼리로 이동");
        return "redirect:/";
    }

    // 로그아웃 기능
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

}

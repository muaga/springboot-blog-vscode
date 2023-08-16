package shop.mtcoding.blogstudy01.user;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogstudy01._core.util.Script;
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
    public @ResponseBody String save(UserRequest.JoinDTO joinDTO) {
        // 부가로직
        // 1. 공백 또는 null 유효성
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return Script.back("유저네임을 입력해주세요.");
        }
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return Script.back("패스워드를 입력해주세요.");
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return Script.back("이메일을 입력해주세요.");
        }

        // 2. 중복값 확인
        // 핵심로직
        // try-catch가 아닌 if로 예외처리 미리 하기
        User user = userRepository.findByUsername(joinDTO.getUsername());
        if (user == null) {
            userRepository.save(joinDTO);
            return Script.href("/loginForm", "회원가입이 완료되었습니다.");
        }        
        return Script.back("유저네임이 중복되었습니다. 다른 유저네임을 입력해주세요.");
    }

    // 중복체크 버튼
    @GetMapping("/check")
    public ResponseEntity<String> check(String name){
        // ResponseEntity : String 타입의 Body을 응답한다.

        User user = userRepository.findByUsername(name);
        if(user == null){
            return new ResponseEntity<String>("유저네임을 사용할 수 있습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<String>("유저네임이 중복되었습니다.", HttpStatus.BAD_REQUEST);
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

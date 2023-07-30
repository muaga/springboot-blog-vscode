package shop.mtcoding.blogstudy01.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import shop.mtcoding.blogstudy01.repository.UserRepository;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }

}

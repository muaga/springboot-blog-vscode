package shop.mtcoding.blogstudy01.user;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    // 회원가입 DTO
    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
    }

    // 로그인 DTO
    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
        private String password;
    }

    // 회원정보수정 DTO
    @Getter
    @Setter
    public static class UserUpadateDTO {
        private String password;
    }
    

}

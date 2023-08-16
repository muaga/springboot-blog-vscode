package shop.mtcoding.blogstudy01.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import shop.mtcoding.blogstudy01.user.User;
import shop.mtcoding.blogstudy01.user.UserRepository;

@SpringBootTest
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ParameterizedTest    
    public void findByUsername_test(String username) {
        username = "ssar";
        User user = userRepository.findByUsername(username);
        System.out.println(user.getEmail());
    }
}
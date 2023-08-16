package shop.mtcoding.blogstudy01.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "user_tb")
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;
    
    @Column(nullable = false, length = 60)
    private String password;
    
    @Column(nullable = false, length = 20)
    private String email;
    
    private Timestamp createdAt;
}

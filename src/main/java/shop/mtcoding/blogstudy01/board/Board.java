package shop.mtcoding.blogstudy01.board;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blogstudy01.user.User;

@Setter
@Getter
@Table(name = "board_tb")
@Entity
public class Board {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String title;
    private String content;
    private Timestamp createdAt;

    @ManyToOne
    private User user;
}

package shop.mtcoding.blogstudy01.reply;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blogstudy01.board.Board;
import shop.mtcoding.blogstudy01.user.User;

@Setter
@Getter
@Table(name = "reply_tb")
@Entity
public class Reply {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String comment;
    private Timestamp createdAt;
    
    @ManyToOne
    private User user;
    @ManyToOne
    private Board board;
}

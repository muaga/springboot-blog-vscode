package shop.mtcoding.blogstudy01.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogstudy01.user.UserRequest.JoinDTO;
import shop.mtcoding.blogstudy01.user.UserRequest.LoginDTO;
import shop.mtcoding.blogstudy01.user.UserRequest.UserUpadateDTO;

@Repository
public class UserRepository {

    @Autowired
    private EntityManager em;

    // 회원가입
    @Transactional
    public void save(JoinDTO entity) {
        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email, created_at) values(:username, :password, :email, now())");
        query.setParameter("username", entity.getUsername());
        query.setParameter("password", entity.getPassword());
        query.setParameter("email", entity.getEmail());
        query.executeUpdate();
    }

    // 삭제
    @Transactional
    public void delete(Integer id) {
        Query query = em.createNativeQuery(
                "delete from user_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // 회원정보수정 - password
    @Transactional
    public void update(UserUpadateDTO entity, Integer id) {
        Query query = em.createNativeQuery(
                "update user_tb set password = :password where id = :id");
        System.out.println("test : 1");

        query.setParameter("password", entity.getPassword());
        query.setParameter("id", id);
        System.out.println("test : 2");

        query.executeUpdate();
        System.out.println("test : 3");

    }

    // findById
    public User findById(Integer id) {
        Query query = em.createNativeQuery(
                "select * from user_tb where id = :id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    // findByUsernameAndPassword - 로그인
    public User findByUsernameAndPassword(LoginDTO entity) {
        Query query = em.createNativeQuery(
                "select * from user_tb where username = :username and password = :password", User.class);
        query.setParameter("username", entity.getUsername());
        query.setParameter("password", entity.getPassword());
        return (User) query.getSingleResult();
    }

    // findByAll
    public List<User> findByAll() {
        Query query = em.createNativeQuery(
                "select * from user_tb", User.class);
        return query.getResultList();
    }
}

package shop.mtcoding.blogstudy01.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogstudy01.dto.JoinDTO;

@Repository
public class UserRepository {
    

    @Autowired
    private EntityManager em;

    @Transactional
    public void save(JoinDTO joinDTO){
        Query Query = em.createNamedQuery("insert into user_tb(username, password, email) values(:username, :password, :email)");
        Query.setParameter("usename", joinDTO.getUsername());
        Query.setParameter("password", joinDTO.getPassword());
        Query.setParameter("email", joinDTO.getEmail());
        Query.executeUpdate();
    }
}

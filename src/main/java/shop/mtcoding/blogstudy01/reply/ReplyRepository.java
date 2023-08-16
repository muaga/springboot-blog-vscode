package shop.mtcoding.blogstudy01.reply;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogstudy01.board.Board;

@Repository
public class ReplyRepository {

    @Autowired
    private EntityManager em;

    // 등록
    @Transactional
    public void save(Reply entity) {
        Query query = em.createNativeQuery(
                "insert into reply_tb(comment, created_at, user_id, board_id) values(:comment, now(), :userId, :boardId)");
        query.setParameter("comment", entity.getComment());
        query.setParameter("userId", entity.getUser().getId());
        query.setParameter("boardId", entity.getBoard().getId());
        query.executeUpdate();
    }

    // 삭제
    @Transactional
    public void delete(Integer userId) {
        Query query = em.createNativeQuery(
                "delete from reply_tb where user_id = :userId");
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    // 수정
    @Transactional
    public void update(Board entity) {
        Query query = em.createNativeQuery(
                "update reply_tb set comment = :comment where user_id = :userId");
        query.setParameter("comment", entity.getContent());
        query.setParameter("userId", entity.getUser().getId());
        query.executeUpdate();
    }

    // findById
    public Reply findById(Integer userId) {
        Query query = em.createNativeQuery(
                "select * from reply_tb where user_id = :userId", Reply.class);
        query.setParameter("userId", userId);
        return (Reply) query.getSingleResult();
    }

    // findByAll
    public List<Reply> findByAll() {
        Query query = em.createNativeQuery(
                "select * from reply_tb", Reply.class);
        return query.getResultList();
    }
}

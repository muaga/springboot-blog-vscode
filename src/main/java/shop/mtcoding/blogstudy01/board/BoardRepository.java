package shop.mtcoding.blogstudy01.board;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogstudy01.board.BoardRequest.BoardDetailDTO;
import shop.mtcoding.blogstudy01.board.BoardRequest.UpdateDTO;
import shop.mtcoding.blogstudy01.board.BoardRequest.WriteDTO;
import shop.mtcoding.blogstudy01.user.User;

@Repository
public class BoardRepository {

    @Autowired
    private EntityManager em;

    // 등록
    @Transactional
    public void save(WriteDTO entity, Integer userId) {
        Query query = em.createNativeQuery(
                "insert into board_tb(title, content, user_id, created_at) values(:title, :content, :userId, now())");
        query.setParameter("title", entity.getTitle());
        query.setParameter("content", entity.getContent());
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    // 삭제
    @Transactional
    public void delete(Integer id) {
        Query query = em.createNativeQuery(
                "delete from board_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // 수정
    @Transactional
    public void update(UpdateDTO entity, Integer id) {
        Query query = em.createNativeQuery(
                "update board_tb set title = :title, content = :content where id = :id");
        query.setParameter("title", entity.getTitle());
        query.setParameter("content", entity.getContent());
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // findById
    public Board findById(Integer id) {
        Query query = em.createNativeQuery(
                "select * from board_tb where id = :id", Board.class);
        query.setParameter("id", id);
        return (Board) query.getSingleResult();
    }

    // findByAll
    public List<Board> findByAll() {
        Query query = em.createNativeQuery(
                "select * from board_tb", Board.class);
        return query.getResultList();
    }

    // 글 상세보기 - 댓글주인, 게시물 주인 확인
    public List<BoardDetailDTO> findByIdJoinReply(Integer boardId, Integer sessionUserId) {
        // 동적쿼리 -> DB에서 다 처리하고 가지고 오기
        String sql = "select ";
        sql += "b.id board_id, ";
        sql += "b.content board_content, ";
        sql += "b.title board_title, ";
        sql += "b.user_id board_user_id, ";
        sql += "r.id reply_id, ";
        sql += "r.comment reply_comment, ";
        sql += "r.user_id reply_user_id, ";
        sql += "ru.username reply_user_username, ";
        if (sessionUserId == null) {
            sql += "false reply_owner ";
        } else {
            sql += "case when r.user_id = :sessionUserId then true else false end reply_owner ";
        }
        // 현재 로그인이 되어 있는 유저 중 댓글 유저 id와 로그인 중 id가 일치하면 댓글을 작성한 유저이다.
        sql += "from board_tb b left outer join reply_tb r ";
        sql += "on b.id = r.board_id ";
        sql += "left outer join user_tb ru ";
        sql += "on r.user_id = ru.id ";
        sql += "where b.id = :boardId ";
        sql += "order by r.id desc";

        Query query = em.createNativeQuery(sql);
        // QLRM
        // model에 받지 않고, qlrm을 통해서 DTO에 데이터를 받아야 하는 경우
        // 매핑 클래스를 createNativeQuery에 작성하지 않는다.

        query.setParameter("boardId", boardId);
        // :boardId = name:"boardId"

        // sessionUser가 있다면 받는 데이터
        if (sessionUserId != null) {
            query.setParameter("sessionUserId", sessionUserId);
        }

        // 직접 오브젝트 매핑하기
        JpaResultMapper mapper = new JpaResultMapper();
        List<BoardDetailDTO> dtos = mapper.list(query, BoardDetailDTO.class);
        // 1 row = uniqueResult
        // 여러 row = list

        return dtos;
    }
}
package shop.mtcoding.blogstudy01.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BoardRequest {

    // 글쓰기 DTO
    @Setter
    @Getter
    public static class WriteDTO {
        private String title;
        private String content;
    }

    // 글수정 DTO
    @Setter
    @Getter
    public static class UpdateDTO {
        private String title;
        private String content;
    }

    // 글상세보기 DTO - 수정예정
    @NoArgsConstructor
    @Getter
    @Setter
    public static class BoardDetailDTO {
        private Integer boardId;
        private String boardContent;
        private String boardTitle;
        private Integer boardUserId;

        private Integer replyId;
        private String replyComment;
        private Integer replyUserId;
        private String replyUserUsername;
        private boolean replyOwner;

        // QLRM은 생성자와 @NoArgsConstructor가 있어야 데이터를 받아올 수 있다.
        public BoardDetailDTO(Integer boardId, String boardContent, String boardTitle, Integer boardUserId,
                Integer replyId, String replyComment, Integer replyUserId, String replyUserUsername, boolean replyOwner) {
            this.boardId = boardId;
            this.boardContent = boardContent;
            this.boardTitle = boardTitle;
            this.boardUserId = boardUserId;
            this.replyId = replyId;
            this.replyComment = replyComment;
            this.replyUserId = replyUserId;
            this.replyUserUsername = replyUserUsername;
            this.replyOwner = replyOwner;
        }
    }

    
}

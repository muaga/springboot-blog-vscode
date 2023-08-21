package shop.mtcoding.blogstudy01.reply;

import lombok.Getter;
import lombok.Setter;

public class ReplyRequest {
    
    // 댓글등록 DTO
    @Setter
    @Getter
    public static class SaveDTO{
        private Integer boardId;
        private String comment;
    }

}

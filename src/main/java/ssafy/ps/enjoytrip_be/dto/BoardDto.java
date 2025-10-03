package ssafy.ps.enjoytrip_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private int articleNo;
    private String userId; // 작성자 ID
    private String userName; // DTO에서는 작성자 이름도 같이 보내주면 좋음
    private String subject;
    private String content;
    private String category;
    private int hit;
    private String registerTime;
}
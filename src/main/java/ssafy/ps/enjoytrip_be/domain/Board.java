package ssafy.ps.enjoytrip_be.domain;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Board {
    private int articleNo;
    private String userId;
    private String subject;
    private String content;
    private int hit;
    private String registerTime;

    // 기본 생성자
    public Board() {}

}
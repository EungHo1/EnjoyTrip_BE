package ssafy.ps.enjoytrip_be.domain;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Board {
    private int articleNo;
    private long userNo;
    private String userName;
    private String subject;
    private String content;
    private String category;
    private int hit;
    private String registerTime;

}
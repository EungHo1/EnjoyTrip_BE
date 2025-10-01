package ssafy.ps.enjoytrip_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private long userNo;
    private String userId;
    private String userName;
    private String userPassword;

    public UserRequestDto() {
    }

}
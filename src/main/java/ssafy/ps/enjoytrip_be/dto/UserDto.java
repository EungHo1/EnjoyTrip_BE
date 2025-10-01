package ssafy.ps.enjoytrip_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private long userNo;
    private String userId;
    private String userName;
    private String joinDate;

    public UserDto(String userId, String userName, String joinDate) {
        this.userId = userId;
        this.userName = userName;
        this.joinDate = joinDate;
    }

    public UserDto() {
    }
}
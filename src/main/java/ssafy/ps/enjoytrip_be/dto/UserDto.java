package ssafy.ps.enjoytrip_be.dto;

public class UserDto {
    private String userId;
    private String userName;
    private String joinDate;

    // domain 객체에는 userPassword가 있지만, DTO에는 없음
    // 이런 식으로 비밀번호 같은 민감한 정보는 서비스 계층 밖으로 나가지 않도록 설계

    public UserDto(String userId, String userName, String joinDate) {
        this.userId = userId;
        this.userName = userName;
        this.joinDate = joinDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

// Getters and Setters ... (생략)
}
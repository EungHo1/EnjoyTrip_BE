package ssafy.ps.enjoytrip_be.dto;

public class UserRegisterRequestDto {
    private String userId;
    private String userName;
    private String userPassword;

    // Getters and Setters ...
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
package ssafy.ps.enjoytrip_be.domain;

public class User {
    private String userId;
    private String userName;
    private String userPassword;
    private String joinDate;

    // 기본 생성자
    public User() {}

    // Getters and Setters
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

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                // 비밀번호는 toString에서 제외하는 것이 보안상 좋음
                ", joinDate='" + joinDate + '\'' +
                '}';
    }
}
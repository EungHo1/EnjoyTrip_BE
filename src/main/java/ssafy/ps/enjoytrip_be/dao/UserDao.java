package ssafy.ps.enjoytrip_be.dao;

import ssafy.ps.enjoytrip_be.domain.User;

public interface UserDao {
    void createUser(User user);
    User findByUserId(String userId);
    void updateUser(User user);
    void deleteUser(String userId);
}
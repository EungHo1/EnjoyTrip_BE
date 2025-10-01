package ssafy.ps.enjoytrip_be.dao;

import ssafy.ps.enjoytrip_be.domain.User;

public interface UserDao {
    User createUser(User user);
    User findByUserId(String userId);
    User findByUserNo(long userNo);
    void updateUser(User user);
    void deleteUser(long userNo);
}
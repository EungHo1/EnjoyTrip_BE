package ssafy.ps.enjoytrip_be.dao.impl;

import ssafy.ps.enjoytrip_be.dao.UserDao;
import ssafy.ps.enjoytrip_be.domain.User;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private final DBUtil dbUtil = DBUtil.getInstance(); // DBUtil 인스턴스

    @Override
    public void createUser(User user) {

    }

    @Override
    public User findByUserId(String userId) {
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. DB 연결
            conn = dbUtil.getConnection();

            // 2. SQL 준비 (PreparedStatement 사용)
            String sql = "SELECT user_id, user_name, join_date FROM user WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);

            // 3. SQL의 '?' 부분에 값 채우기 (SQL Injection 공격 방지)
            pstmt.setString(1, userId);

            // 4. SQL 실행 및 결과 받기
            rs = pstmt.executeQuery();

            // 5. 결과를 User 객체에 매핑
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setJoinDate(rs.getString("join_date"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. 사용한 자원 반납 (매우 중요!)
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String userId) {

    }

    // ... 다른 메소드 구현
}
package ssafy.ps.enjoytrip_be.dao.impl;

import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dao.UserDao;
import ssafy.ps.enjoytrip_be.domain.User;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.sql.*;


@Slf4j
public class UserDaoImpl implements UserDao {
    private final DBUtil dbUtil = DBUtil.getInstance(); // DBUtil 인스턴스
    private UserDaoImpl(){}

    private final static UserDaoImpl instance = new UserDaoImpl();

    public static UserDaoImpl getInstance(){
        return instance;
    }
    // UserDaoImpl.java

    @Override
    public User createUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; // 생성된 키를 받기 위한 ResultSet

        try {
            conn = dbUtil.getConnection();
            String sql = "INSERT INTO user (user_id, user_name, user_password) VALUES (?, ?, ?)";

            // 1. PreparedStatement를 생성할 때, 생성된 키를 반환해달라는 옵션을 추가!
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getUserPassword());

            // 2. INSERT 실행
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // 3. INSERT가 성공했으면, 생성된 키(들)를 ResultSet으로 받아옴
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    // 4. ResultSet에서 생성된 user_no 값을 꺼내서
                    long generatedKey = rs.getLong(1);
                    // 5. 파라미터로 받았던 원본 user 객체의 userNo 필드에 채우기
                    user.setUserNo(generatedKey);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return user;
    }
    @Override
    public User findByUserId(String userId) {
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbUtil.getConnection();

            String sql = "SELECT user_no, user_id, user_name, user_password, join_date FROM user WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserNo(rs.getLong("user_no"));
                user.setUserId(rs.getString("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPassword(rs.getString("user_password"));
                user.setJoinDate(rs.getString("join_date"));
            }

        } catch (SQLException e) {
            log.error("해당 아이디의 유저를 찾을 수 없습니다. userId : {}", userId, e);
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }

        log.info("DAO가 DB에서 찾은 user 정보: {}", user); // user 객체가 null인지, 값이 제대로 채워졌는지 확인

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
package ssafy.ps.enjoytrip_be.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class DBUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // URL의 데이터베이스 이름만 ssafy_trip으로 변경
    private static final String URL = "jdbc:mysql://localhost:3306/ssafy_trip?serverTimezone=UTC";
    private static final String USER = "SSAFY";
    private static final String PASSWORD = "SSAFY";

    // 1. 자기 자신의 인스턴스를 static으로 가지고 있음
    @Getter
    private static DBUtil instance = new DBUtil();

    // 2. 생성자를 private으로 막아서 외부에서 new로 객체 생성을 못하게 함
    private DBUtil() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("드라이버 로드 실패!!");
        }
    }

    // 4. 커넥션을 반환하는 메소드
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            log.error("자원 반납 실패!!" + e);
        }
    }

    public void close(PreparedStatement pstmt, Connection conn) {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            log.error("자원 반납 실패!!" + e);
        }
    }

    public void close(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            log.error("자원 반납 실패!!" + e);
        }
    }
}
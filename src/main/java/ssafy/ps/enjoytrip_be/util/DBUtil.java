package ssafy.ps.enjoytrip_be.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:enjoytripdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    // 1. 자기 자신의 인스턴스를 static으로 가지고 있음
    private static DBUtil instance = new DBUtil();

    // 2. 생성자를 private으로 막아서 외부에서 new로 객체 생성을 못하게 함
    private DBUtil() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 3. 외부에서 이 클래스의 인스턴스를 필요로 할 때 호출하는 메소드
    public static DBUtil getInstance() {
        return instance;
    }

    // 4. 커넥션을 반환하는 메소드
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
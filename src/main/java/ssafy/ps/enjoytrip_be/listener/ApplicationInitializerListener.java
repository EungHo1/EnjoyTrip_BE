package ssafy.ps.enjoytrip_be.listener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.util.DBUtil; // 네 DBUtil 경로에 맞게 수정

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@WebListener
@Slf4j
public class ApplicationInitializerListener implements ServletContextListener {

    private final DBUtil dbUtil = DBUtil.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("웹 애플리케이션 시작! 초기 데이터 확인/적재를 시작합니다.");

        try (Connection conn = dbUtil.getConnection(); Statement stmt = conn.createStatement()) {

            // --- 1. 필수 User 데이터 확인 및 생성 ---
            String checkUserSql = "SELECT COUNT(*) FROM user WHERE user_id = 'unknown'";
            ResultSet rs = stmt.executeQuery(checkUserSql);
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO `user` (user_id, user_name, user_password) VALUES ('unknown', '탈퇴한 사용자', '1234')");
                stmt.executeUpdate("INSERT INTO `user` (user_id, user_name, user_password) VALUES ('admin', '관리자', '1234')");
                stmt.executeUpdate("INSERT INTO `user` (user_id, user_name, user_password) VALUES ('ssafy', '김싸피', '1234')");
            }
            rs.close();

            // --- 2. 게시판 더미 데이터 확인 및 생성 ---
            String checkBoardSql = "SELECT COUNT(*) FROM board";
            rs = stmt.executeQuery(checkBoardSql);
            if (rs.next() && rs.getInt(1) == 0) {
                log.info("게시판 더미 데이터를 생성합니다.");
                stmt.executeUpdate("INSERT INTO `board` (user_no, subject, content) VALUES (3, '첫 번째 글입니다.', '안녕하세요.')");
                stmt.executeUpdate("INSERT INTO `board` (user_no, subject, content) VALUES (2, '공지사항입니다.', '규칙을 지켜주세요.')");
            }
            rs.close();


        } catch (Exception e) {
            log.info("초기 데이터 확인/적재 중 오류 발생!");
            e.printStackTrace();
        }
    }

}
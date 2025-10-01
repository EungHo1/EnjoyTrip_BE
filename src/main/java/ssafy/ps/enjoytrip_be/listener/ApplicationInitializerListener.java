package ssafy.ps.enjoytrip_be.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

@WebListener // 이 어노테이션으로 서블릿 컨테이너가 리스너임을 인지함
public class ApplicationInitializerListener implements ServletContextListener {

    private final DBUtil dbUtil = DBUtil.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("웹 애플리케이션 시작! DB 초기화를 시작");

        try (Connection conn = dbUtil.getConnection(); Statement stmt = conn.createStatement()) {
            // 1. src/main/resources 폴더에 있는 schema.sql 파일을 읽어옴
            InputStream is = getClass().getClassLoader().getResourceAsStream("schema.sql");
            if (is == null) {
                System.err.println("schema.sql 파일을 찾을 수 없습니다!");
                return;
            }
            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));

            // 2. SQL 스크립트를 세미콜론(;)을 기준으로 각 문장으로 분리
            String[] sqlStatements = sql.split(";");

            // 3. 각 SQL 문장을 순서대로 실행
            for (String statement : sqlStatements) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement);
                }
            }
            System.out.println("DB 초기화 성공!");

        } catch (SQLException e) {
            System.err.println("DB 초기화 중 오류 발생!");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("웹 애플리케이션 종료.");
    }
}
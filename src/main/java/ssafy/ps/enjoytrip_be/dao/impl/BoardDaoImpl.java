package ssafy.ps.enjoytrip_be.dao.impl;

import lombok.Getter;
import ssafy.ps.enjoytrip_be.dao.BoardDao;
import ssafy.ps.enjoytrip_be.domain.Board;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements BoardDao {
    DBUtil dbUtil = DBUtil.getInstance();
    private final static long DEACTIVATED_USER_NO = 1; // '탈퇴한 회원'의 user_no 값
    @Getter
    private final static BoardDaoImpl instance = new BoardDaoImpl();

    private BoardDaoImpl(){}

    @Override
    public void createArticle(Board board) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbUtil.getConnection();
            String sql = "INSERT INTO board (user_no, subject, content, hit, category) VALUES (?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, board.getUserNo());
            pstmt.setString(2, board.getSubject());
            pstmt.setString(3, board.getContent());
            pstmt.setInt(4, board.getHit());
            pstmt.setString(5, board.getCategory());

            // 1. INSERT 실행
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(pstmt, conn);
        }
    }

    @Override
    public List<Board> selectAllBoards(String category) {
        List<Board> list = new ArrayList<>();
        // board와 user 테이블을 JOIN해서, user_name까지 한번에 가져온다.
        String sql = "SELECT b.article_no, b.user_no, u.user_name, b.subject, b.hit, b.register_time "
                + "FROM board b JOIN user u ON b.user_no = u.user_no "
                + "WHERE b.category = ? " // category 조건 추가!
                + "ORDER BY b.register_time DESC LIMIT 0, 10";

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category); // category 값 설정


            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Board board = new Board();
                    board.setArticleNo(rs.getInt("article_no"));
                    board.setUserNo(rs.getLong("user_no"));
                    board.setUserName(rs.getString("user_name")); // JOIN으로 가져온 작성자 이름 추가!
                    board.setSubject(rs.getString("subject"));
                    board.setHit(rs.getInt("hit"));
                    board.setRegisterTime(rs.getTimestamp("register_time").toString());
                    list.add(board);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Board findByArticleNo(int articleNo) {
        String sql = "SELECT article_no, user_no, subject, content, hit, register_time "
                + "FROM board WHERE article_no = ?";

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, articleNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Board board = new Board();
                    board.setArticleNo(rs.getInt("article_no"));
                    board.setUserNo(rs.getInt("user_no"));
                    board.setSubject(rs.getString("subject"));
                    board.setContent(rs.getString("content"));
                    board.setHit(rs.getInt("hit"));
                    board.setRegisterTime(rs.getTimestamp("register_time").toString());
                    return board;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("글 상세 조회 중 DB 오류", e);
        }

        return null; // 글이 없으면 null 반환
    }

    @Override
    public void updateArticle(Board board) {
        String sql = "UPDATE board SET subject = ?, content = ? WHERE article_no = ?";

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, board.getSubject());
            pstmt.setString(2, board.getContent());
            pstmt.setInt(3, board.getArticleNo());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("글을 수정할 수 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("게시글 수정 중 DB 오류", e);
        }
    }

    @Override
    public void deleteArticle(int articleNo) {
        String sql = "DELETE FROM board WHERE article_no = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, articleNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게시글 삭제 중 DB 오류", e);
        }
    }

    @Override
    public void updateHit(int articleNo) {
        String sql = "UPDATE board SET hit = hit + 1 WHERE article_no = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, articleNo);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("조회수 증가 중 DB 오류", e);
        }
    }

    @Override
    public int reassignPostsToUnknown(long userNo) throws SQLException {
        int affectedRows = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        // user_no를 '탈퇴한 회원'의 번호로 UPDATE한다.
        String sql = "UPDATE board SET user_no = ? WHERE user_no = ?";

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            // SQL의 '?' 자리에 값을 채워넣는다.
            pstmt.setLong(1, DEACTIVATED_USER_NO); // 첫 번째 ?: 탈퇴한 회원의 번호
            pstmt.setLong(2, userNo);              // 두 번째 ?: 지금 탈퇴하는 회원의 번호

            affectedRows = pstmt.executeUpdate();

        } finally {
            dbUtil.close(pstmt, conn);
        }

        return affectedRows;
    }

}

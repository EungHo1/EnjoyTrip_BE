package ssafy.ps.enjoytrip_be.dao.impl;

import lombok.Getter;
import ssafy.ps.enjoytrip_be.dao.BoardDao;
import ssafy.ps.enjoytrip_be.domain.Board;
import ssafy.ps.enjoytrip_be.dto.BoardDto;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BoardDaoImpl implements BoardDao {
    DBUtil dbUtil = DBUtil.getInstance();
    private final static long DEACTIVATED_USER_NO = 1; // '탈퇴한 회원'의 user_no 값
    @Getter
    private final static BoardDaoImpl instance = new BoardDaoImpl();

    private BoardDaoImpl(){}

    @Override
    public void createArticle(Board board) {
    }

    @Override
    public List<BoardDto> selectAllBoards() {
        return List.of();
    }

    @Override
    public BoardDto findById(int articleNo) {
        return null;
    }

    @Override
    public void updateArticle(Board board) {

    }

    @Override
    public void deleteArticle(int articleNo) {

    }

    @Override
    public void updateHit(int articleNo) {

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

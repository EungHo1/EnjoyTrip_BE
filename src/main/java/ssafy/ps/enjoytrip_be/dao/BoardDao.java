package ssafy.ps.enjoytrip_be.dao;

import ssafy.ps.enjoytrip_be.domain.Board;

import java.sql.SQLException;
import java.util.List;

public interface BoardDao {
    void createArticle(Board board);
    List<Board> selectAllBoards(String category);
    Board findByArticleNo(int articleNo);
    void updateArticle(Board board);
    void deleteArticle(int articleNo);
    void updateHit(int articleNo);
    int reassignPostsToUnknown(long userNo) throws SQLException;
}
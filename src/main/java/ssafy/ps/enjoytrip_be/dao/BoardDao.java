package ssafy.ps.enjoytrip_be.dao;

import ssafy.ps.enjoytrip_be.domain.Board;
import ssafy.ps.enjoytrip_be.dto.BoardDto;

import java.sql.SQLException;
import java.util.List;

public interface BoardDao {
    void createArticle(Board board);
    List<BoardDto> selectAllBoards();
    BoardDto findById(int articleNo);
    void updateArticle(Board board);
    void deleteArticle(int articleNo);
    void updateHit(int articleNo);
    int reassignPostsToUnknown(long userNo) throws SQLException;
}
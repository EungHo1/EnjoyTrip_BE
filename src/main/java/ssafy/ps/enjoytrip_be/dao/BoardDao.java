package ssafy.ps.enjoytrip_be.dao;

import ssafy.ps.enjoytrip_be.domain.Board;
import java.util.List;

public interface BoardDao {
    void createArticle(Board board);
    List<Board> findAll();
    Board findById(int articleNo);
    void updateArticle(Board board);
    void deleteArticle(int articleNo);
    void updateHit(int articleNo);
}
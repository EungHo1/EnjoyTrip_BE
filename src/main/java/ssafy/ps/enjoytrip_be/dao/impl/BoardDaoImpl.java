package ssafy.ps.enjoytrip_be.dao.impl;

import ssafy.ps.enjoytrip_be.dao.BoardDao;
import ssafy.ps.enjoytrip_be.domain.Board;

import java.util.List;

public class BoardDaoImpl implements BoardDao {
    @Override
    public void createArticle(Board board) {

    }

    @Override
    public List<Board> findAll() {
        return List.of();
    }

    @Override
    public Board findById(int articleNo) {
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
}

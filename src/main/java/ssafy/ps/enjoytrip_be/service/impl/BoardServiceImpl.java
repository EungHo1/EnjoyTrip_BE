package ssafy.ps.enjoytrip_be.service.impl;

import ssafy.ps.enjoytrip_be.dto.BoardDto;
import ssafy.ps.enjoytrip_be.service.BoardService;

import java.util.List;

public class BoardServiceImpl implements BoardService {
    @Override
    public void writeArticle(BoardDto boardDto) {

    }

    @Override
    public List<BoardDto> listArticles() {
        return List.of();
    }

    @Override
    public BoardDto getArticle(int articleNo) {
        return null;
    }

    @Override
    public void modifyArticle(BoardDto boardDto) {

    }

    @Override
    public void deleteArticle(int articleNo) {

    }
}

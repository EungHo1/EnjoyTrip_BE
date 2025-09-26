package ssafy.ps.enjoytrip_be.service;

import ssafy.ps.enjoytrip_be.dto.BoardDto;
import java.util.List;

public interface BoardService {
    void writeArticle(BoardDto boardDto);
    List<BoardDto> listArticles();
    BoardDto getArticle(int articleNo);
    void modifyArticle(BoardDto boardDto);
    void deleteArticle(int articleNo);
}
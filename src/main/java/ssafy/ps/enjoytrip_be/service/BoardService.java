package ssafy.ps.enjoytrip_be.service;

import ssafy.ps.enjoytrip_be.dto.BoardDto;
import ssafy.ps.enjoytrip_be.dto.UserDto;

import java.util.List;

public interface BoardService {
    void writeArticle(BoardDto boardDto);
    List<BoardDto> listArticles(String category);
    BoardDto getArticle(int articleNo);
    void modifyArticle(BoardDto boardDto, UserDto loginUser);
    void deleteArticle(int articleNo, UserDto loginUser);
}
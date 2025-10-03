package ssafy.ps.enjoytrip_be.service.impl;

import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dao.BoardDao;
import ssafy.ps.enjoytrip_be.dao.UserDao;
import ssafy.ps.enjoytrip_be.dao.impl.BoardDaoImpl;
import ssafy.ps.enjoytrip_be.dao.impl.UserDaoImpl;
import ssafy.ps.enjoytrip_be.domain.Board;
import ssafy.ps.enjoytrip_be.domain.User;
import ssafy.ps.enjoytrip_be.dto.BoardDto;
import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.service.BoardService;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final static BoardDao boardDao = BoardDaoImpl.getInstance();
    private UserDao userDao = UserDaoImpl.getInstance();

    @Override
    public void writeArticle(BoardDto boardDto) {
        Board board = new Board();

        // user_id -> user_no 변환
        User user = userDao.findByUserId(boardDto.getUserId());

        if (user != null) {
            log.debug("조회된 사용자 - userNo: {}, userId: {}, userName: {}",
                    user.getUserNo(), user.getUserId(), user.getUserName());
        } else {
            log.debug("해당 userNo={} 에 대한 사용자를 찾을 수 없습니다.", boardDto.getUserId());
        }

        board.setUserNo(user.getUserNo());
        board.setSubject(boardDto.getSubject());
        board.setContent(boardDto.getContent());
        board.setHit(boardDto.getHit());
        board.setCategory(boardDto.getCategory());

        boardDao.createArticle(board); // Dao로 전달
    }

    @Override
    public List<BoardDto> listArticles(String category) {
        List<Board> boards = boardDao.selectAllBoards(category);

        List<BoardDto> dtos = new ArrayList<>();
        for (Board board : boards) {
            dtos.add(boardToBoardDto(board));
        }

        return dtos;
    }

    @Override
    public BoardDto getArticle(int articleNo) {
        // 1. 조회수 증가
        boardDao.updateHit(articleNo);

        // 2. 글 정보 조회
        Board board = boardDao.findByArticleNo(articleNo);
        if (board == null) {
            return null; // 또는 예외 처리
        }
        return boardToBoardDto(board);
    }

    @Override
    public void modifyArticle(BoardDto boardDto, UserDto loginUser) {
        // 글 정보 조회
        Board board = boardDao.findByArticleNo(boardDto.getArticleNo());
        if (board.getUserNo() != loginUser.getUserNo()) {
            throw new RuntimeException("수정 권한이 없습니다."); // 예외를 던져서 막아버린다.
        }

        // 글 수정
        board.setSubject(boardDto.getSubject());
        board.setContent(boardDto.getContent());
        boardDao.updateArticle(board);
    }

    @Override
    public void deleteArticle(int articleNo, UserDto loginUser) {
        Board board = boardDao.findByArticleNo(articleNo);
        if (board.getUserNo() != loginUser.getUserNo()) {
            throw new RuntimeException("수정 권한이 없습니다."); // 예외를 던져서 막아버린다.
        }

        boardDao.deleteArticle(articleNo);
    }

    // Board -> BoardDto 변환
    private BoardDto boardToBoardDto(Board board) {
        BoardDto dto = new BoardDto();

        dto.setArticleNo(board.getArticleNo());

        // user_no → user_id 조회
        User user = userDao.findByUserNo(board.getUserNo());
        dto.setUserId(user.getUserId());

        dto.setSubject(board.getSubject());
        dto.setContent(board.getContent());
        dto.setHit(board.getHit());
        dto.setRegisterTime(board.getRegisterTime());

        return dto;
    }
}

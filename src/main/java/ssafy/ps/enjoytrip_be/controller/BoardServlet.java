package ssafy.ps.enjoytrip_be.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dao.UserDao;
import ssafy.ps.enjoytrip_be.dao.impl.UserDaoImpl;
import ssafy.ps.enjoytrip_be.dto.BoardDto;
import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.service.BoardService;
import ssafy.ps.enjoytrip_be.service.impl.BoardServiceImpl;

import java.io.IOException;
import java.util.List;

@Slf4j
// "/" 주소로 오는 GET 요청을 이 서블릿이 처리하도록 매핑
@WebServlet("/board")
public class BoardServlet extends HttpServlet implements ControllerHelper {
    private  static final long serialVersionUID = 1L;
    private final UserDao userDao =  UserDaoImpl.getInstance();
    private static final BoardService boardService = new BoardServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getActionParameter(request, response);

        switch (action) {
            case "mvform" -> moveForm(request, response);
            case "list" -> getBoardList(request, response);
            case "detail" -> getDetail(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getActionParameter(request, response);

        switch (action) {
            case "create" -> create(request, response);
            case "update" -> update(request, response);
            case "delete" -> delete(request, response);
        }
    }

    private void moveForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String articleNoParam = request.getParameter("articleNo");
        if(articleNoParam != null) { // 수정 폼일 때
            int articleNo = Integer.parseInt(articleNoParam);
            BoardDto article = boardService.getArticle(articleNo);
            request.setAttribute("article", article);
        }
        forward(request, response, "/WEB-INF/views/board/form.jsp");
    }

    private void getBoardList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BoardDto> boardDtoList = boardService.listArticles();
        request.setAttribute("articleList", boardDtoList);
        forward(request, response, "/WEB-INF/views/board/list.jsp");
    }

    private void getDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int articleId = Integer.parseInt(request.getParameter("articleNo"));

        // 1. 글 조회
        BoardDto article = boardService.getArticle(articleId);
        request.setAttribute("article", article);

        // 2. 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = request.getSession(false);
        UserDto loginUser = null;
        if (session != null) {
            loginUser = (UserDto) session.getAttribute("userInfo");
        }
        request.setAttribute("loginUser", loginUser); // JSP에서 비교용으로 전달

        // 3. JSP 포워드
        forward(request, response, "/WEB-INF/views/board/detail.jsp");
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // 기존 세션만 가져옴

        // 1. 세션에서 사용자 정보 꺼내기
        String userId = null;
        if (session != null && session.getAttribute("userInfo") != null) {
            UserDto loginUser = (UserDto) session.getAttribute("userInfo");
            userId = loginUser.getUserId();
            log.info("userId: {}", userId);
        } else {
            log.info("세션이 존재하지 않습니다. (로그인 필요)");
            redirect(request, response, "/board?action=login");
        }
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        // 조회수(hit)는 글 작성 시 보통 0으로 초기화
        int hit = 0;

        BoardDto boardDto = new BoardDto();
        boardDto.setUserId(userId);
        boardDto.setSubject(subject);
        boardDto.setContent(content);
        boardDto.setHit(hit);

        try {
            boardService.writeArticle(boardDto);
            redirect(request, response, "/board?action=list");
        } catch (Exception e) { // Service에서 던진 예외를 잡음
            log.error("게시판 글 작성 중 오류 발생", e); // 서버 로그에는 에러를 자세히 남기고
            request.setAttribute("errorMsg", "해당 글을 작성할 수 없습니다."); // 사용자에게 보여줄 메시지 설정
            forward(request, response, "/WEB-INF/views/board/form.jsp"); // 다시 글 작성 폼으로 포워드
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int articleNo = Integer.parseInt(request.getParameter("articleNo"));
        log.info("업데이트할 글 번호: {}", articleNo);

        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        BoardDto boardDto = new BoardDto();
        boardDto.setArticleNo(articleNo);
        boardDto.setSubject(subject);
        boardDto.setContent(content);

        try {
            boardService.modifyArticle(boardDto); // 로그인 사용자 ID 전달
            redirect(request, response, "/board?action=detail&articleNo=" + articleNo);
        } catch (RuntimeException e) {
            log.error("게시판 글 수정 중 오류 발생", e);
            forward(request, response, "/WEB-INF/views/board/form.jsp");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int articleNo = Integer.parseInt(request.getParameter("articleNo"));
        log.info("삭제할 글 번호: {}", request.getParameter("articleNo"));

        boardService.deleteArticle(articleNo);
        redirect(request, response, "/board?action=list");
    }
}

package ssafy.ps.enjoytrip_be.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.dto.UserRegisterRequestDto;
import ssafy.ps.enjoytrip_be.service.UserService;
import ssafy.ps.enjoytrip_be.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.Serial;

// "/" 주소로 오는 GET 요청을 이 서블릿이 처리하도록 매핑
@Slf4j
@WebServlet("/user")
public class UserServlet extends HttpServlet implements ControllerHelper {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getActionParameter(request, response);

        switch (action) {
            case "register-form" -> getRegisterForm(request, response);
            case "login-form" -> getLoginForm(request, response);
            case "logout" -> {
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getActionParameter(request, response);

        switch (action) {
            case "join" -> join(request, response);
            case "login" -> login(request, response);
        }
    }


    private void getLoginForm(HttpServletRequest request, HttpServletResponse response) {
        try {
            forward(request, response, "/WEB-INF/views/user/login.jsp");
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getRegisterForm(HttpServletRequest request, HttpServletResponse response) {
        try {
            forward(request, response, "/WEB-INF/views/user/register.jsp");
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String userPassword = request.getParameter("userPassword");

        UserDto login = userService.login(userId, userPassword);
        if (login != null) {
            request.getSession().setAttribute("userInfo", login);
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("errorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            forward(request, response, "/WEB-INF/views/user/login.jsp");
        }
    }


    private void join(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");

        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setUserName(userName);
        userRegisterRequestDto.setUserPassword(userPassword);
        userRegisterRequestDto.setUserId(userId);

        try {
            userService.join(userRegisterRequestDto);
            request.getSession().setAttribute("successMsg", "회원가입을 축하합니다! 로그인해주세요.");

            response.sendRedirect(request.getContextPath() + "/user?action=login-form");
        } catch (Exception e) { // Service에서 던진 예외를 잡음
            log.error("회원가입 중 오류 발생", e); // 서버 로그에는 에러를 자세히 남기고
            request.setAttribute("errorMsg", "이미 사용 중인 아이디입니다."); // 사용자에게 보여줄 메시지 설정
            forward(request, response, "/WEB-INF/views/user/register.jsp"); // 다시 회원가입 폼으로 포워드
        }
    }
}

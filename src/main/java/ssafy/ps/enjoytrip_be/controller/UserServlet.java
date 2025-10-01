package ssafy.ps.enjoytrip_be.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.dto.UserRequestDto;
import ssafy.ps.enjoytrip_be.service.UserService;
import ssafy.ps.enjoytrip_be.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.Serial;

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
            case "mypage" -> mypage(request, response);
            case "logout" -> {
                request.getSession().invalidate();
                redirect(request, response, "/");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getActionParameter(request, response);

        switch (action) {
            case "join" -> join(request, response);
            case "login" -> login(request, response);
            case "update" -> update(request, response);
            case "delete" -> delete(request, response);
        }
    }

    private void mypage(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userInfo") == null) {
                response.sendRedirect(request.getContextPath() + "/main?action=login");
                return;
            }

            UserDto loginUser = (UserDto) session.getAttribute("userInfo");
            long userNo = loginUser.getUserNo();

            UserDto freshUserInfo = userService.getUserByNo(userNo);

            request.setAttribute("userInfo", freshUserInfo);
            forward(request, response, "/WEB-INF/views/user/mypage.jsp");
        } catch (ServletException | IOException ex) {
            log.error("마이페이지 로드 중 오류 발생", ex);
            throw new RuntimeException(ex);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        // 1. 세션에서 사용자 정보 꺼내기
        if (session != null && session.getAttribute("userInfo") != null) {
            UserDto loginUser = (UserDto) session.getAttribute("userInfo");
            try {
                userService.deleteUser(loginUser.getUserNo());
                session.invalidate();
                redirect(request, response, "/");
            } catch (Exception e) {
                log.error("회원 탈퇴 중 오류 발생", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }

    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        // 1. 세션에서 '누구'를 수정할지 결정한다.
        if (session == null || session.getAttribute("userInfo") == null) {
            redirect(request, response, "/user?action=login");
            return;
        }
        UserDto loginUser = (UserDto) session.getAttribute("userInfo");

        try {
            // 2. 파라미터에서 '무엇으로' 수정할지 가져온다.
            String newName = request.getParameter("userName");
            String newPassword = request.getParameter("userPassword");

            // 3. DTO를 만든다. 식별자(userNo)는 '세션'에서, 변경할 내용은 '파라미터'에서 가져온다.
            UserRequestDto userDto = new UserRequestDto();
            userDto.setUserNo(loginUser.getUserNo());
            userDto.setUserName(newName);
            userDto.setUserPassword(newPassword);

            // 4. DB 업데이트
            userService.updateUser(userDto);

            // 5. 세션에 저장된 사용자 정보도 새로운 정보로 업데이트
            loginUser.setUserName(newName);
            session.setAttribute("userInfo", loginUser); // 갱신된 객체로 세션을 덮어쓴다.

            session.setAttribute("successMsg", "회원정보가 성공적으로 수정되었습니다.");
            redirect(request, response, "/user?action=mypage");

        } catch (Exception e) {
            log.error("회원정보 수정 중 오류 발생", e);
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

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserName(userName);
        userRequestDto.setUserPassword(userPassword);
        userRequestDto.setUserId(userId);

        try {
            userService.join(userRequestDto);
            request.getSession().setAttribute("successMsg", "회원가입을 축하합니다! 로그인해주세요.");

            response.sendRedirect(request.getContextPath() + "/user?action=login-form");
        } catch (Exception e) { // Service에서 던진 예외를 잡음
            log.error("회원가입 중 오류 발생", e); // 서버 로그에는 에러를 자세히 남기고
            request.setAttribute("errorMsg", "이미 사용 중인 아이디입니다."); // 사용자에게 보여줄 메시지 설정
            forward(request, response, "/WEB-INF/views/user/register.jsp"); // 다시 회원가입 폼으로 포워드
        }
    }
}

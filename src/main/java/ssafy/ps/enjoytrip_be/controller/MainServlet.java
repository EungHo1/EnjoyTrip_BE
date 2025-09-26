package ssafy.ps.enjoytrip_be.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// "/" 주소로 오는 GET 요청을 이 서블릿이 처리하도록 매핑
@WebServlet("/")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청을 index.jsp 파일로 전달(forward)
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
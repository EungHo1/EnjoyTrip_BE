package ssafy.ps.enjoytrip_be.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dto.ContentTypeDto;
import ssafy.ps.enjoytrip_be.dto.GugunDto;
import ssafy.ps.enjoytrip_be.dto.SidoDto;
import ssafy.ps.enjoytrip_be.service.AttractionService;
import ssafy.ps.enjoytrip_be.service.impl.AttractionServiceImpl;


import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("/attraction")
public class AttractionServlet extends HttpServlet implements ControllerHelper {
    private static final long serialVersionUID = 1L;
    AttractionService attractionService = AttractionServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getActionParameter(request, response);
            switch (action) {
                case "list" -> listSidos(request, response);
                case "gugunList" -> listGuguns(request, response);
            }


    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void listSidos(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<SidoDto> sidos = attractionService.listSidos();
            List<ContentTypeDto> contentTypes = attractionService.listContentTypes();
            request.setAttribute("sidoList", sidos);
            request.setAttribute("contentTypeList", contentTypes);

            forward(request, response, "/WEB-INF/views/attraction/list.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 구/군 목록을 JSON으로 응답하는 헬퍼 메소드
    private void listGuguns(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 1. 요청 파라미터에서 sidoCode를 꺼낸다.
            int sidoCode = Integer.parseInt(request.getParameter("sidoCode"));
            // 2. 서비스를 호출해 구/군 목록을 가져온다.
            List<GugunDto> guguns = attractionService.listGuguns(sidoCode);

            // 3. Gson 라이브러리로 List 객체를 JSON 문자열로 변환한다.
            Gson gson = new Gson();
            String json = gson.toJson(guguns);

            // 4. 응답의 타입을 JSON으로 설정하고, JSON 문자열을 응답 바디에 쓴다.
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

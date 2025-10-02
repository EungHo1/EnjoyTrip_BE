package ssafy.ps.enjoytrip_be.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ssafy.ps.enjoytrip_be.dto.AttractionInfoDto;
import ssafy.ps.enjoytrip_be.dto.PlanDto;
import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.service.AttractionService;
import ssafy.ps.enjoytrip_be.service.PlanService;
import ssafy.ps.enjoytrip_be.service.impl.AttractionServiceImpl;
import ssafy.ps.enjoytrip_be.service.impl.PlanServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/plan")
public class PlanServlet extends HttpServlet implements ControllerHelper {
    private AttractionService attractionService = AttractionServiceImpl.getInstance();
    private PlanService planService = PlanServiceImpl.getInstance(); // 새로 만들어야 함

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "view":
                    viewPlan(request, response);
                    break;
                case "add":
                    addSpotToCart(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("save".equals(action)) {
                savePlan(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리
        }
    }

    private void viewPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        UserDto loginUser = (UserDto) session.getAttribute("userInfo");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user?action=login-form");
            return;
        }

        // 1. Service를 통해 해당 유저가 저장한 모든 plan을 DB에서 가져온다.
        List<PlanDto> savedPlans = planService.listPlans(loginUser.getUserNo());

        // 2. request에 "savedPlans"라는 이름으로 데이터를 담는다.
        request.setAttribute("savedPlans", savedPlans);

        // 3. plan.jsp로 forward한다.
        forward(request, response, "/WEB-INF/views/plan/plan.jsp");
    }

    private void addSpotToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int contentId = Integer.parseInt(request.getParameter("contentId"));
        HttpSession session = request.getSession();

        // 세션에서 장바구니 가져오기, 없으면 새로 만들기
        List<AttractionInfoDto> cart = (List<AttractionInfoDto>) session.getAttribute("planCart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // DB에서 관광지 정보 조회
        AttractionInfoDto spot = attractionService.getAttraction(contentId); // 이 메소드 DAO, Service에 추가 필요
        if (spot != null) {
            cart.add(spot);
        }

        // 장바구니를 세션에 다시 저장
        session.setAttribute("planCart", cart);

        // 원래 있던 검색 페이지로 리다이렉트
        redirect(request, response, "/attraction?action=list");
    }

    private void savePlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("userInfo");
        List<AttractionInfoDto> cart = (List<AttractionInfoDto>) session.getAttribute("planCart");
        String planTitle = request.getParameter("planTitle");

        if (loginUser == null || cart == null || cart.isEmpty()) {
            // 비정상적인 접근 처리
            redirect(request, response, "/");
            return;
        }

        PlanDto planDto = new PlanDto();
        planDto.setUserNo(loginUser.getUserNo());
        planDto.setTitle(planTitle);
        planDto.setAttractions(cart);

        planService.savePlan(planDto); // 이 메소드 Service, DAO에 추가 필요 (트랜잭션 처리!)

        // 저장 후 장바구니 비우기
        session.removeAttribute("planCart");

        // 나의 여행 계획 목록 페이지 같은 곳으로 리다이렉트 (일단 메인으로)
        response.sendRedirect(request.getContextPath() + "/");
    }
}
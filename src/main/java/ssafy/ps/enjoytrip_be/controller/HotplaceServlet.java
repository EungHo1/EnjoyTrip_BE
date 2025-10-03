package ssafy.ps.enjoytrip_be.controller;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dto.HotplaceDto;
import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.service.HotplaceService;
import ssafy.ps.enjoytrip_be.service.impl.HotplaceServiceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@WebServlet("/hotplace")
@MultipartConfig
public class HotplaceServlet extends HttpServlet implements ControllerHelper {
    private static final long serialVersionUID = 1L;
    private static final HotplaceService hotplaceService = HotplaceServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "mvform" -> moveForm(request, response);
                case "mvlist" -> moveList(request, response);
                case "list" -> listHotplaces(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // '목록 페이지'로 포워딩하는 메소드를 새로 만들어.
    private void moveList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, "/WEB-INF/views/hotplace/list.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            createHotplace(request, response);
        }
    }


    private void moveForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, "/WEB-INF/views/hotplace/form.jsp");
    }


    private void listHotplaces(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Service -> DAO 거쳐서 모든 핫플레이스 목록 가져오기
        List<HotplaceDto> list = hotplaceService.findAll();
        // JSON으로 변환해서 응답 (attraction 검색이랑 똑같음)
        Gson gson = new Gson();
        String json = gson.toJson(list);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private void createHotplace(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        UserDto loginUser = (UserDto) (session != null ? session.getAttribute("userInfo") : null);
        if (loginUser == null) {
            redirect(request, response, "/user?action=login-form");
            return;
        }

        try {
            // 1. 텍스트 파라미터 추출
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));

            // 2. 파일 파트(Part) 추출
            Part filePart = request.getPart("image");
            String imageUrl = null;

            if (filePart != null && filePart.getSize() > 0) {
                // 3. 파일 저장
                String uploadPath = getServletContext().getRealPath("/assets/img/hotplace");
                log.info("파일이 실제로 저장되는 물리 경로: {}", uploadPath);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                // 중복되지 않는 파일 이름 생성
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID() + extension;

                filePart.write(uploadPath + File.separator + savedFileName);
                imageUrl = request.getContextPath() + "/assets/img/hotplace/" + savedFileName;
            }

            // 4. DTO 생성 및 서비스 호출
            HotplaceDto hotplaceDto = new HotplaceDto();
            hotplaceDto.setUserNo(loginUser.getUserNo());
            hotplaceDto.setTitle(title);
            hotplaceDto.setDescription(description);
            hotplaceDto.setLatitude(latitude);
            hotplaceDto.setLongitude(longitude);
            hotplaceDto.setImageUrl(imageUrl);

            hotplaceService.create(hotplaceDto);

            redirect(request, response, "/hotplace?action=mvlist");
        } catch (Exception e) {
                e.printStackTrace(); // 에러 처리
        }
    }
}

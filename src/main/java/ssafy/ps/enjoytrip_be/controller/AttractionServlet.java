package ssafy.ps.enjoytrip_be.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ssafy.ps.enjoytrip_be.service.AttractionService;
import ssafy.ps.enjoytrip_be.service.impl.AttractionServiceImpl;

import java.io.IOException;

public class AttractionServlet extends HttpServlet implements ControllerHelper {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getActionParameter(request, response);
        switch (action) {
            case "list":
//                listAttractions(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

package ssafy.ps.enjoytrip_be.fillter;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@WebFilter("/*")
@Slf4j
public class LoggingFilter extends HttpFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 요청 정보 로깅
        log.info("요청 파라미터 분석");
        request.getParameterMap().forEach((k, v) -> {
            log.info("name: {}, value: {}", k, Arrays.toString(v));
        });
        log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        chain.doFilter(request, response);
    }
}

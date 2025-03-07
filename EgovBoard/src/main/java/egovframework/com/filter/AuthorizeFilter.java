package egovframework.com.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthorizeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String secretCodeId = request.getHeader("X-CODE-ID");

        String SECRET_CODE = "-WzAnecnlNewSEQwDgJ2BQ";
        if (ObjectUtils.isEmpty(secretCodeId) || !SECRET_CODE.equals(secretCodeId)) {
            log.warn("##### Access Denied: Unauthorized Access Attempt");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String ERROR_PAGE = "/error/403.html";
            response.sendRedirect(request.getContextPath() + ERROR_PAGE);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.matches(".*\\.(css|js|png|jpg|jpeg|gif|svg|woff|woff2|ttf|otf|eot|ico|html)$");
    }

}

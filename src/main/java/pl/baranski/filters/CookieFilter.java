package pl.baranski.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "CookieFilter", urlPatterns = {"/*"})
public class CookieFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        Cookie[] cookies = httpReq.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                
                if (!"JSESSIONID".equals(cookie.getName())) {
                    
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0); 
                    httpRes.addCookie(cookie);
                } else {
                    
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);   // przy HTTPS
                    cookie.setPath("/");
                    httpRes.addCookie(cookie);
                }
            }
        }

        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
}

package jh.hw.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final String authTokenName;

    private final JWTUtil jwtUtil;

    public AuthenticationFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager, String authTokenName, JWTUtil jwtUtil) {
        super(requestMatcher);
        this.authTokenName = authTokenName;
        this.jwtUtil = jwtUtil;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        try {
            String token = getCookieValue(httpServletRequest, authTokenName);
            String user = jwtUtil.validateAndRetrieveSubject(token, "User Details", "user");
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(authTokenName, user));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    private static String getCookieValue(HttpServletRequest httpServletRequest, String cookieName) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null || cookies.length < 1) {
            return null;
        }
        Cookie cookie = Arrays.stream(cookies).filter(c -> cookieName.equals(c.getName())).findFirst().orElse(null);
        return cookie == null ? null : cookie.getValue();
    }
}
package com.study.realworld.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.auth.dto.UserLoginDto;
import com.study.realworld.auth.token.AjaxAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String x_requested_with = "X-Requested-With";
    private static final String xmlHttpRequest = "XMLHttpRequest";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AjaxAuthenticationProcessingFilter(String url) {
        super(new AntPathRequestMatcher(url));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isAjax(request)) {
            throw new IllegalStateException("ajax 요청이 아닙니다.");
        }

        UserLoginDto dto = objectMapper.readValue(request.getReader(), UserLoginDto.class);

        if (dto == null || dto.getUsername() == null || dto.getPassword() == null) {
            throw new BadCredentialsException("there is no username or password");
        }

        AjaxAuthenticationToken token = new AjaxAuthenticationToken(dto.getUsername(), dto.getPassword());
        return getAuthenticationManager().authenticate(token);
    }

    private boolean isAjax(HttpServletRequest request) {
        return request.getHeader(x_requested_with).equals(xmlHttpRequest);
    }
}

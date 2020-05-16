package com.yrx.springsecurity.ssoclient.sso;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by r.x on 2020/5/14.
 */
public class SsoAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public SsoAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter("code");
        SsoAuthenticationToken authenticationToken = new SsoAuthenticationToken(code);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}

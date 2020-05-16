package com.yrx.springsecurity.ssoclient.api;

import com.alibaba.fastjson.JSON;
import com.yrx.springsecurity.ssoclient.sso.SsoAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by r.x on 2020/5/14.
 */
@RestController
@RequestMapping("/private")
public class PrivateApi {

    @GetMapping("/source")
    public String source() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof SsoAuthenticationToken) {
            SsoAuthenticationToken ssoAuthenticationToken = (SsoAuthenticationToken) authentication;
            return "login user: " + JSON.toJSONString(ssoAuthenticationToken);
        }
        return "this is private source and login authentication is unknown";
    }

    @GetMapping("/afterLogin")
    public String afterLogin() {
        return "this is private source after login";
    }

}

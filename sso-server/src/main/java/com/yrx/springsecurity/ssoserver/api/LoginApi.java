package com.yrx.springsecurity.ssoserver.api;

import com.yrx.springsecurity.ssoserver.model.UserEnum;
import com.yrx.springsecurity.ssoserver.model.UserSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by r.x on 2020/5/16.
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginApi {
    static Map<String, UserEnum> userCache = new HashMap<>();

    @PostMapping("/authenticate")
    public void authenticate(HttpServletRequest request, HttpServletResponse response, UserSummary userSummary) throws IOException {
        log.info("authenticate user: {}", userSummary);
        UserEnum userEnum = UserEnum.authenticate(userSummary.getName(), userSummary.getIdCard());
        String code = UUID.randomUUID().toString().replace("-", "");
        if (userEnum != null) {
            userCache.put(code, userEnum);
        }
        response.sendRedirect("http://localhost:9090/client/sso/callback?code=" + code);
    }
}


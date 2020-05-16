package com.yrx.springsecurity.ssoserver.api;

import com.yrx.springsecurity.ssoserver.model.UserEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by r.x on 2020/5/16.
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationApi {

    @GetMapping("/userSummary")
    public String getByCode(@RequestParam String code) {
        UserEnum userEnum = LoginApi.userCache.get(code);
        if (userEnum != null) {
            return userEnum.toString();
        }
        return "";
    }
}

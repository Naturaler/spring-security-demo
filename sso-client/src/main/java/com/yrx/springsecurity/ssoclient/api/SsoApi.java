package com.yrx.springsecurity.ssoclient.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by r.x on 2020/5/16.
 */
@RestController
@RequestMapping("sso")
@Slf4j
public class SsoApi {

    @GetMapping("/callback")
    public String callback(@RequestParam String code) {
        log.info("callback from server, code: {}", code);
        return code;
    }
}

package com.yrx.springsecurity.ssoclient.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by r.x on 2020/5/14.
 */
@RestController
@RequestMapping("/public")
public class PublicApi {

    @GetMapping("/source")
    public String source() {
        return "this is public source";
    }
}

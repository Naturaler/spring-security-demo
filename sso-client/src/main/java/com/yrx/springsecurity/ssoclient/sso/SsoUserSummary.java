package com.yrx.springsecurity.ssoclient.sso;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by r.x on 2020/5/14.
 */
@Data
@Builder
public class SsoUserSummary {
    private String name;
    private String idCard;
    private List<String> roles;

}

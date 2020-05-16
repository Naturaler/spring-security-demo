package com.yrx.springsecurity.ssoclient.sso;

import com.alibaba.fastjson.JSON;
import com.yrx.springsecurity.ssoclient.helper.ApplicationContextHelper;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by r.x on 2020/5/14.
 */
public class SsoProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof SsoAuthenticationToken) {
            SsoAuthenticationToken ssoAuthenticationToken = (SsoAuthenticationToken) authentication;
            String code = ssoAuthenticationToken.getCode();
            SsoUserSummary userSummary = getUserInfoByCode(code);
            return new SsoAuthenticationToken(userSummary.getName(), userSummary.getIdCard(), roleToAuthority(userSummary.getRoles()));
        }
        throw new AuthenticationCredentialsNotFoundException("error authentication token");
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (SsoAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private SsoUserSummary getUserInfoByCode(String code) {
        RestTemplate restTemplate = ApplicationContextHelper.getBean(RestTemplate.class);
        String url = "http://localhost:9091/server/authentication/userSummary?code="+code;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return parseResponse(responseEntity.getBody());
    }

    private SsoUserSummary parseResponse(String body) {
        if (StringUtils.isEmpty(body)) {
            throw new AuthenticationCredentialsNotFoundException("invalid authenticated!");
        }
        SsoUserDto ssoUserDto = JSON.parseObject(body, SsoUserDto.class);
        return userDtoToUserSummary(ssoUserDto);
    }

    private SsoUserSummary userDtoToUserSummary(SsoUserDto ssoUserDto) {
        return SsoUserSummary.builder()
                .name(ssoUserDto.getName())
                .idCard(ssoUserDto.getIdCard())
                .roles(Collections.singletonList(ssoUserDto.getRole()))
                .build();
    }

    private Collection<? extends GrantedAuthority> roleToAuthority(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Data
    private static class SsoUserDto{
        private String name;
        private String idCard;
        private String role;
    }

}

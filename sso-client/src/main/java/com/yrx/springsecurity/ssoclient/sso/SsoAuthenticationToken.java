package com.yrx.springsecurity.ssoclient.sso;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by r.x on 2020/5/14.
 */
@Getter
public class SsoAuthenticationToken extends AbstractAuthenticationToken {
    private String code;
    private String name;
    private String idCard;

    public SsoAuthenticationToken(String code) {
        super(null);
        this.code = code;
        super.setAuthenticated(false);
    }

    public SsoAuthenticationToken(String name, String idCard, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.name = name;
        this.idCard = idCard;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.idCard;
    }

    @Override
    public Object getPrincipal() {
        return this.name;
    }
}

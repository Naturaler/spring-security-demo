/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yrx.springsecurity.ssoclient.config;

import com.yrx.springsecurity.ssoclient.sso.SsoAuthenticationProcessingFilter;
import com.yrx.springsecurity.ssoclient.sso.SsoLoginAuthenticationEntryPoint;
import com.yrx.springsecurity.ssoclient.sso.SsoProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(ssoProcessingFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/private/**").authenticated();

        http.exceptionHandling().authenticationEntryPoint(loginEntryPoint());
    }

    @Bean
    public AuthenticationEntryPoint loginEntryPoint() {
        return new SsoLoginAuthenticationEntryPoint();
    }

    @Bean
    public SsoProvider ssoProvider() {
        return new SsoProvider();
    }

    private SsoAuthenticationProcessingFilter ssoProcessingFilter(AuthenticationManager authenticationManager) {
        SsoAuthenticationProcessingFilter processingFilter = new SsoAuthenticationProcessingFilter(
                (request -> {
                    String uri = request.getRequestURI();
                    String code = request.getParameter("code");
                    return uri.equals("/client/sso/callback") && StringUtils.hasText(code);
                }));
        processingFilter.setAuthenticationManager(authenticationManager);
        return processingFilter;
    }
}

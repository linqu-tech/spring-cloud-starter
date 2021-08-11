/*
 * Copyright (c) 2020 linqu.tech, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.linqu.spring.cloud.starter.auth.config;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import tech.linqu.spring.cloud.starter.auth.config.handler.RedirectAuthenticationFailureHandler;
import tech.linqu.spring.cloud.starter.auth.config.handler.RedirectAuthenticationSuccessHandler;
import tech.linqu.spring.cloud.starter.auth.config.handler.RedirectLogoutSuccessHandler;

/**
 * Configuration for {@link HttpSecurity}.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final MysqlUserDetailsService userDetailsService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
        throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
            new OAuth2AuthorizationServerConfigurer<>();
        authorizationServerConfigurer.authorizationEndpoint(
            authorizationEndpoint -> authorizationEndpoint.consentPage("/gateway/consent.html"));
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
            .requestMatcher(endpointsMatcher)
            .authorizeRequests(authorizeRequests ->
                authorizeRequests.anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .apply(authorizationServerConfigurer);

        applyHttpSecurityConfig(http);
        return http.build();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/signup").permitAll()
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        applyHttpSecurityConfig(http);
        return http.build();
    }

    private void applyHttpSecurityConfig(HttpSecurity http) throws Exception {
        http
            .formLogin(configurer -> configurer
                .successHandler(new RedirectAuthenticationSuccessHandler())
                .failureHandler(
                    new RedirectAuthenticationFailureHandler("/gateway/login.html?error=true"))
                .loginPage("/gateway/login.html")
                .loginProcessingUrl("/login")
            )
            .logout(configurer -> configurer
                .logoutSuccessHandler(new RedirectLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            );
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        BCryptPasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
        encoders.put(encodingId, defaultEncoder);
        encoders.put("plain", new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        });
        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder(encodingId, encoders) {

            private final Pattern pattern = Pattern.compile("\\{(?<encId>\\w+)}(?<pass>.+)");

            @Override
            public String encode(CharSequence rawPassword) {
                Matcher matcher = pattern.matcher(rawPassword);
                if (matcher.find()) {
                    String encId = matcher.group("encId");
                    PasswordEncoder passwordEncoder = encoders.get(encId);
                    if (!encodingId.equals(encId) && passwordEncoder != null) {
                        return "{" + encId + "}" + passwordEncoder.encode(matcher.group("pass"));
                    } else {
                        return defaultEncoder.encode(matcher.group("pass"));
                    }
                }
                return defaultEncoder.encode(rawPassword);
            }
        };
        encoder.setDefaultPasswordEncoderForMatches(defaultEncoder);
        return encoder;
    }
}

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

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import tech.linqu.spring.cloud.starter.proto.mapstruct.GeneralProtoMapper;
import tech.linqu.spring.cloud.starter.utilities.mapstruct.GeneralMapper;
import tech.linqu.spring.cloud.starter.utilities.uid.SnowflakeGenerator;
import tech.linqu.spring.cloud.starter.utilities.uid.UidGenerator;
import tech.linqu.webpb.runtime.mvc.WebpbRequestBodyAdvice;

/**
 * Configuration for beans.
 */
@Configuration
@EnableConfigurationProperties(SpringDataWebProperties.class)
@RequiredArgsConstructor
public class BeansConfiguration {

    private final SpringDataWebProperties dataWebProperties;

    @Bean
    GeneralMapper generalMapper() {
        return new GeneralMapper();
    }

    @Bean
    GeneralProtoMapper generalProtoMapper() {
        return new GeneralProtoMapper(dataWebProperties);
    }

    @Bean
    UidGenerator uidGenerator() {
        return new SnowflakeGenerator();
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }

    @Bean
    WebpbRequestBodyAdvice requestBodyAdvice() {
        return new WebpbRequestBodyAdvice();
    }
}

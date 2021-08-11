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

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.linqu.spring.cloud.starter.utilities.mvc.PageableMethodArgumentResolver;
import tech.linqu.webpb.runtime.mvc.WebpbHandlerMethodArgumentResolver;

/**
 * Configuration for {@link WebMvcConfigurer}.
 */
@Configuration
@EnableConfigurationProperties(SpringDataWebProperties.class)
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final SpringDataWebProperties properties;

    @Value("${core.force-pageable:true}")
    private boolean forcePageable;

    @Bean
    PageableMethodArgumentResolver pageableMethodArgumentResolver(
        @Value("${core.force-pageable:true}") boolean forcePageable) {
        PageableMethodArgumentResolver pageableResolver =
            new PageableMethodArgumentResolver(forcePageable);
        pageableCustomizer().customize(pageableResolver);
        return pageableResolver;
    }

    @Bean
    SortHandlerMethodArgumentResolver defaultSortHandlerMethodArgumentResolver() {
        SortHandlerMethodArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver();
        sortCustomizer().customize(sortResolver);
        return sortResolver;
    }

    @Bean
    @ConditionalOnMissingBean
    PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer() {
        return (resolver) -> {
            SpringDataWebProperties.Pageable pageable = this.properties.getPageable();
            resolver.setPageParameterName(pageable.getPageParameter());
            resolver.setSizeParameterName(pageable.getSizeParameter());
            resolver.setOneIndexedParameters(pageable.isOneIndexedParameters());
            resolver.setPrefix(pageable.getPrefix());
            resolver.setQualifierDelimiter(pageable.getQualifierDelimiter());
            resolver.setFallbackPageable(PageRequest.of(0, pageable.getDefaultPageSize()));
            resolver.setMaxPageSize(pageable.getMaxPageSize());
        };
    }

    @Bean
    @ConditionalOnMissingBean
    SortHandlerMethodArgumentResolverCustomizer sortCustomizer() {
        return resolver -> resolver.setSortParameter(this.properties.getSort().getSortParameter());
    }

    /**
     * Add argument resolvers.
     *
     * @param resolvers list of {@link HandlerMethodArgumentResolver}.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new WebpbHandlerMethodArgumentResolver());
        resolvers.add(pageableMethodArgumentResolver(forcePageable));
        resolvers.add(defaultSortHandlerMethodArgumentResolver());
    }
}

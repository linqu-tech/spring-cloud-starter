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

package tech.linqu.spring.cloud.starter.gateway.filter;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Replace location response header.
 */
@Component
public class ReplaceLocationResponseHeaderGatewayFilterFactory
    extends AbstractGatewayFilterFactory<ReplaceLocationResponseHeaderGatewayFilterFactory.Config> {

    private static final String LOCATION_HEADER_NAME_KEY = "locationHeaderName";

    private static final String MATCH_REGEX = "matchRegex";

    private static final String REPLACEMENT = "replacement";

    /**
     * Constructor.
     */
    public ReplaceLocationResponseHeaderGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * Shortcut field order.
     *
     * @return list of {@link String}
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(LOCATION_HEADER_NAME_KEY, MATCH_REGEX, REPLACEMENT);
    }

    /**
     * Apply {@link Config}.
     *
     * @param config {@link Config}
     * @return {@link GatewayFilter}
     */
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> replaceLocation(exchange, config)));
            }

            @Override
            public String toString() {
                return filterToStringCreator(ReplaceLocationResponseHeaderGatewayFilterFactory.this)
                    .append("locationHeaderName", config.locationHeaderName)
                    .append("matchRegex", config.matchRegex)
                    .append("replacement", config.replacement)
                    .toString();
            }
        };
    }

    void replaceLocation(ServerWebExchange exchange, Config config) {
        final String location =
            exchange.getResponse().getHeaders().getFirst(config.getLocationHeaderName());
        if (location != null) {
            final String matchRegex = config.getMatchRegex();
            final String replacement = config.getReplacement();
            Pattern matchPattern = Pattern.compile(matchRegex);
            final String fixedLocation = matchPattern.matcher(location).replaceFirst(replacement);
            exchange.getResponse().getHeaders().set(config.getLocationHeaderName(), fixedLocation);
        }
    }

    /**
     * Config for {@link GatewayFilter}.
     */
    public static class Config {

        private String locationHeaderName = HttpHeaders.LOCATION;

        private String matchRegex = "";

        private String replacement = "";

        String getLocationHeaderName() {
            return locationHeaderName;
        }

        void setLocationHeaderName(String locationHeaderName) {
            this.locationHeaderName = locationHeaderName;
        }

        String getMatchRegex() {
            return matchRegex;
        }

        void setMatchRegex(String matchRegex) {
            this.matchRegex = matchRegex;
        }

        String getReplacement() {
            return replacement;
        }

        void setReplacement(String replacement) {
            this.replacement = replacement;
        }
    }
}

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

package tech.linqu.spring.cloud.starter.account.client;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;
import static tech.linqu.spring.cloud.starter.utilities.utils.Utils.uncheckedCall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.linqu.spring.cloud.starter.proto.exception.ApiErrorCode;
import tech.linqu.spring.cloud.starter.proto.exception.ApiException;
import tech.linqu.spring.cloud.starter.proto.external.error.ErrorCode;
import tech.linqu.spring.cloud.starter.proto.imports.error.ErrorMessage;
import tech.linqu.spring.cloud.starter.proto.internal.error.RpcErrorCode;
import tech.linqu.webpb.runtime.reactive.WebpbClient;

/**
 * Http client to request auth service.
 */
@Component
public class AuthServiceClient extends WebpbClient {

    /**
     * Construct from {@link WebClient.Builder}.
     *
     * @param factory {@link WebClient.Builder}
     */
    public AuthServiceClient(WebClient.Builder factory,
                             @Value("${core.url.auth-service}") String baseUrl) {
        super(webClient(factory, baseUrl), clientRegistrationId("account-service"));
    }

    private static WebClient webClient(WebClient.Builder builder, String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }

    @Override
    protected Mono<? extends Throwable> createException(ClientResponse clientResponse) {
        return clientResponse
            .bodyToMono(byte[].class)
            .map(data -> {
                ErrorMessage message
                    = uncheckedCall(() -> transportMapper.readValue(data, ErrorMessage.class));
                ApiErrorCode errorCode = RpcErrorCode.fromValue(message.getCode());
                if (errorCode == null) {
                    errorCode = ErrorCode.fromValue(message.getCode());
                }
                return ApiException.of(clientResponse.statusCode(), errorCode);
            });
    }
}

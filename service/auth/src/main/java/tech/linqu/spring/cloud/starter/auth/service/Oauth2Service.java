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

package tech.linqu.spring.cloud.starter.auth.service;

import static org.springframework.util.StringUtils.delimitedListToStringArray;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import tech.linqu.spring.cloud.starter.proto.exception.ApiException;
import tech.linqu.spring.cloud.starter.proto.external.error.ErrorCode;
import tech.linqu.spring.cloud.starter.proto.external.oauth2.Oauth2ConsentRequest;
import tech.linqu.spring.cloud.starter.proto.external.oauth2.Oauth2ConsentResponse;

/**
 * Oauth2 service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final OAuth2AuthorizationConsentService authorizationConsentService;

    private final RegisteredClientRepository registeredClientRepository;

    /**
     * Get consent.
     *
     * @param principal {@link Principal}
     * @param request   {@link Oauth2ConsentRequest}
     * @return {@link Oauth2ConsentResponse}
     */
    public Oauth2ConsentResponse getConsent(Principal principal, Oauth2ConsentRequest request) {
        Set<String> scopes = new HashSet<>();
        Set<String> approvedScopes = new HashSet<>();
        RegisteredClient client = registeredClientRepository.findByClientId(request.getClientId());
        if (client == null) {
            throw ApiException.of(ErrorCode.ERROR);
        }
        OAuth2AuthorizationConsent consent =
            authorizationConsentService.findById(client.getId(), principal.getName());
        Set<String> authorizedScopes;
        if (consent != null) {
            authorizedScopes = consent.getScopes();
        } else {
            authorizedScopes = Collections.emptySet();
        }
        for (String scope : delimitedListToStringArray(request.getScope(), " ")) {
            if (authorizedScopes.contains(scope)) {
                approvedScopes.add(scope);
            } else {
                scopes.add(scope);
            }
        }

        return new Oauth2ConsentResponse()
            .setClientId(request.getClientId())
            .setPrincipalName(principal.getName())
            .setState(request.getState())
            .setScopes(new ArrayList<>(scopes))
            .setApprovedScopes(new ArrayList<>(approvedScopes));
    }
}

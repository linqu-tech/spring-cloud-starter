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

package tech.linqu.spring.cloud.starter.utilities.mvc;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolve {@link Pageable} from query.
 */
public class PageableMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private final boolean forcePageable;

    /**
     * Construct a resolver.
     *
     * @param forcePageable force paging ignore pagination parameter
     */
    public PageableMethodArgumentResolver(
        @Value("${core.force-pageable:true}") boolean forcePageable) {
        this.forcePageable = forcePageable;
    }

    /**
     * Resolve argument.
     *
     * @param methodParameter {@link MethodParameter}
     * @param mavContainer    {@link ModelAndViewContainer}
     * @param webRequest      {@link NativeWebRequest}
     * @param binderFactory   {@link WebDataBinderFactory}
     * @return {@link Pageable}
     */
    @Override
    public Pageable resolveArgument(MethodParameter methodParameter,
                                    ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory) {
        if (forcePageable) {
            return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        }
        Object pageable = webRequest.getParameter("pagination");
        if (Objects.equals(pageable, Boolean.FALSE.toString())) {
            return Pageable.unpaged();
        }
        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }
}

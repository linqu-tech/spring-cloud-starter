/*
 * Copyright 2013-2020 the original author or authors.
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

package tech.linqu.spring.cloud.starter.gateway.filter;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class HttpBinCompatibleController {

    @RequestMapping(path = "/no/location")
    ResponseEntity<?> noLocation() {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/replace")
    ResponseEntity<?> direct() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/replaced/test"));
        return ResponseEntity.ok().headers(httpHeaders).build();
    }

    @RequestMapping(path = "/preserve")
    ResponseEntity<?> redirect() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/preserved/test"));
        return ResponseEntity.ok().headers(httpHeaders).build();
    }
}

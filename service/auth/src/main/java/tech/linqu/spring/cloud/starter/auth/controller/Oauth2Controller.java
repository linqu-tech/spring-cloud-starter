package tech.linqu.spring.cloud.starter.auth.controller;

import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import tech.linqu.spring.cloud.starter.auth.service.Oauth2Service;
import tech.linqu.spring.cloud.starter.proto.external.oauth2.Oauth2ConsentRequest;
import tech.linqu.spring.cloud.starter.proto.external.oauth2.Oauth2ConsentResponse;
import tech.linqu.webpb.runtime.mvc.WebpbRequestMapping;

/**
 * Oauth2 controller.
 */
@RestController
@RequiredArgsConstructor
public class Oauth2Controller {

    private final Oauth2Service oauth2Service;

    @WebpbRequestMapping
    public Oauth2ConsentResponse consent(Principal principal, @Valid Oauth2ConsentRequest request) {
        return oauth2Service.getConsent(principal, request);
    }
}

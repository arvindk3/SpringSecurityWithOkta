package com.arv.quiz.quizservice.config.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    private OAuth2AuthorizedClientManager manager;

    public FeignClientInterceptor(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token=manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build()).getAccessToken().getTokenValue();
        requestTemplate.header("Authorization","Bearer "+token);

    }
}

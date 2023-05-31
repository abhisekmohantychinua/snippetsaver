package dev.abhisek.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate template;

    public AuthFilter() {
        super(Config.class);

    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {

            if (routeValidator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange
                            .getResponse()
                            .setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange
                            .getResponse()
                            .setComplete();
                }
                String authHeader = exchange
                        .getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    try {
                        template
                                .getForObject("http://localhost:8085/auth/validate/" + authHeader, Boolean.class);
                    } catch (RestClientException e) {
                        System.out.println(e.getMessage());
                        exchange
                                .getResponse()
                                .setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange
                                .getResponse()
                                .setComplete();
                    }
                }

            }
            return chain.filter(exchange);
        });
    }


    public static class Config {
    }
}

package dev.abhisek.apigateway.filter;

import dev.abhisek.apigateway.exception.InvalidTokenException;
import dev.abhisek.apigateway.util.JwtUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final RouteValidator routeValidator;
    private final JwtUtil jwtUtil;
    private final WebClient webClient;


    public AuthFilter(RouteValidator routeValidator, JwtUtil jwtUtil, WebClient webClient) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtUtil = jwtUtil;
        this.webClient = webClient;
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

                try {
                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        throw new InvalidTokenException();
                    }
                    String token = authHeader.substring(7);
                    return webClient
                            .get()
                            .uri("http://localhost:8085/auth/validate/{token}", token)
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .flatMap(isValid -> {
                                if (isValid == null || !isValid) {


                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "text/plain");
                                    return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                                            .bufferFactory().wrap(new InvalidTokenException().getLocalizedMessage().getBytes())));

                                }
                                return chain.filter(exchange);
                            })
                            .onErrorResume(WebClientResponseException.Forbidden.class, e -> {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                return exchange.getResponse().setComplete();
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    exchange
                            .getResponse()
                            .setStatusCode(HttpStatus.UNAUTHORIZED);
                    exchange
                            .getResponse()
                            .getHeaders()
                            .add(HttpHeaders.CONTENT_TYPE, "text/plain");
                    return exchange
                            .getResponse()
                            .writeWith(Mono
                                    .just(exchange
                                            .getResponse()
                                            .bufferFactory()
                                            .wrap(e
                                                    .getLocalizedMessage()
                                                    .getBytes())));

                }

            }
            return chain.filter(exchange);
        });
    }


    public static class Config {
    }
}

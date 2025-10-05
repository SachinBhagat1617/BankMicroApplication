package com.ofss.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account_service", r -> r
                        .path("/api/v1/accounts/**","/api/v1/transactions/**")
                        .uri("lb://AccountMS"))
                .route("customer_service",r->r
                        .path("/api/v1/customers/**")
                        .uri("lb://CustomerMS")
                )
                .route("Bank_service",r->r
                        .path("/api/v1/banks/**")
                        .uri("lb://BankMS")
                )
                .build();
    }
}

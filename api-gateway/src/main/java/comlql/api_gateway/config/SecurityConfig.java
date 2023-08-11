package comlql.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                    .csrf(csrfSpec -> csrfSpec.disable())
                    .authorizeExchange(exchange -> exchange
                                             .pathMatchers("/eureka/**").permitAll()
                                             .anyExchange().authenticated())
                    .oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()))
                    .build();

    }
}
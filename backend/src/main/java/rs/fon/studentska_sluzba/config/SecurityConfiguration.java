package rs.fon.studentska_sluzba.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/gradovi/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/gradovi/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/obavestenja/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/obavestenja/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/molbe/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/studenti/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/prijave/admin**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

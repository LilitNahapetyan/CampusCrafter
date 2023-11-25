package academy.campuscrafter.config;

import academy.campuscrafter.filter.JWTAuthenticationTokenFilter;
import academy.campuscrafter.sequrity.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.mapstruct.SubclassMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    @SuppressWarnings("All")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//aranc sessioni lini
                .and().authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/user/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/courses/**").authenticated()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()).httpBasic();
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}

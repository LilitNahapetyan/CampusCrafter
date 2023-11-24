package academy.campuscrafter.config;

import academy.campuscrafter.filter.JWTAuthenticationTokenFilter;
import academy.campuscrafter.sequrity.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.mapstruct.SubclassMapping;
import org.springframework.context.annotation.Configuration;
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
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    @SuppressWarnings("All")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                //.requestMatchers("/swagger-ui/**").permitAll()
                //.requestMatchers("/v3/api-docs/**").permitAll()
                //.requestMatchers("/countries").permitAll()
                //.requestMatchers("/books/getImage/**").permitAll()
                //.requestMatchers(HttpMethod.POST, "/user/auth").permitAll()
                //.requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                //.requestMatchers(HttpMethod.GET, "/author/**").authenticated()
                //.requestMatchers(HttpMethod.POST, "/author/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated();
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}


package com.warehousemanagement.config;

import com.warehousemanagement.repository.UserRepository;
import com.warehousemanagement.security.JwtAuthenticationFilter;
import com.warehousemanagement.security.OAuth2SuccessHandler;
import com.warehousemanagement.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserRepository userRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      AuthenticationProvider authenticationProvider,
      JwtAuthenticationFilter jwtAuthenticationFilter,
      CustomOAuth2UserService customOAuth2UserService,
      OAuth2SuccessHandler oAuth2SuccessHandler
  ) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint((request, response, authException) -> {
              response.setStatus(401);
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);
              response.getWriter().write("{\"status\":401,\"message\":\"Unauthorized\"}");
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
              response.setStatus(403);
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);
              response.getWriter().write("{\"status\":403,\"message\":\"Forbidden\"}");
            })
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/oauth2/**",
                "/login/oauth2/**"
            ).permitAll()
            .requestMatchers(HttpMethod.POST, "/v1/auth/**").permitAll()

            .requestMatchers(HttpMethod.POST, "/v1/categories").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/v1/categories").hasAnyRole("ADMIN", "WAREHOUSE")

            .requestMatchers(HttpMethod.POST, "/v1/products").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/v1/products/*").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/v1/products", "/v1/products/low-stock")
            .hasAnyRole("ADMIN", "WAREHOUSE")

            .requestMatchers(HttpMethod.POST, "/v1/stock/in").hasRole("WAREHOUSE")
            .requestMatchers(HttpMethod.POST, "/v1/sales").hasRole("WAREHOUSE")

            .anyRequest().authenticated()
        )
        .authenticationProvider(authenticationProvider)
        .oauth2Login(oauth -> oauth
            .userInfoEndpoint(userInfo ->
                userInfo.userService(customOAuth2UserService)
            )
            .successHandler(oAuth2SuccessHandler)
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Bean
  public AuthenticationProvider authenticationProvider(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder
  ) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration configuration
  ) {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

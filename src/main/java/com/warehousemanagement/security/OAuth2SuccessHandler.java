package com.warehousemanagement.security;

import com.warehousemanagement.entity.UserEntity;
import com.warehousemanagement.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final UserRepository userRepository;
  private final JwtService jwtService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication
  ) throws IOException, ServletException {

    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    String email = oAuth2User.getAttribute("email");

    if(email == null && oAuth2User.getAttribute("login") != null) {
      email = oAuth2User.getAttribute("login") + "@github.local";
    }

    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    String token = jwtService.generateToken(user);

    response.setContentType("application/json");
    response.getWriter().write(
        "{\"token\":\"" + token + "\"}"
    );
  }
}

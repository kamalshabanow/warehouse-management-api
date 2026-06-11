package com.warehousemanagement.service;

import com.warehousemanagement.entity.UserEntity;
import com.warehousemanagement.enums.UserRole;
import com.warehousemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest)
      throws OAuth2AuthenticationException {

    OAuth2User oauth2User = super.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    String email = oauth2User.getAttribute("email");
    String name = oauth2User.getAttribute("name");

    if ("github".equals(registrationId)) {
      Object githubIdObj = oauth2User.getAttribute("id");
      String githubId = githubIdObj != null ? githubIdObj.toString() : null;

      if (name == null) {
        name = oauth2User.getAttribute("login");
      }

      if (email == null) {
        email = oauth2User.getAttribute("login") + "@github.local";
      }

      String finalEmail = email;
      String finalName = name;

      userRepository.findByEmail(finalEmail)
          .orElseGet(() -> {
            UserEntity newUser = UserEntity.builder()
                .username(finalEmail)
                .email(finalEmail)
                .githubId(githubId)
                .fullName(finalName)
                .role(UserRole.WAREHOUSE)
                .build();

            return userRepository.save(newUser);

          });
    } else if ("google".equals(registrationId)) {

      String googleId = oauth2User.getAttribute("sub");

      String finalEmail = email;
      String finalName = name;
      userRepository.findByEmail(email)
          .orElseGet(() -> {
            UserEntity newUser = UserEntity.builder()
                .username(finalEmail)
                .email(finalEmail)
                .googleId(googleId)
                .fullName(finalName)
                .role(UserRole.WAREHOUSE)
                .build();

            return userRepository.save(newUser);

          });

    }

    return oauth2User;
  }
}
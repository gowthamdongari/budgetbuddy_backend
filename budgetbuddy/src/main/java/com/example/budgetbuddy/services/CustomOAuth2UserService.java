package com.example.budgetbuddy.services;

import com.example.budgetbuddy.dao.UsersRepository;
import com.example.budgetbuddy.modal.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

public class CustomOAuth2UserService  extends DefaultOAuth2UserService {

      @Autowired
      private UsersRepository usersRepository;

      @Override
      public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
          OAuth2User oAuth2User = super.loadUser(userRequest);

          String email = oAuth2User.getAttribute("email");
          Optional<Users> usersOptional = usersRepository.findByEmail(email);

          Users users;
          if(usersOptional.isPresent()){
              users = usersOptional.get();
          } else{
               users = new Users();
               users.setEmail(email);
              users.setName(oAuth2User.getAttribute("name"));
              users.setProvider(userRequest.getClientRegistration().getRegistrationId());
              users.setProviderId(oAuth2User.getName());
              usersRepository.save(users);
          }
          return  users;
      }


}

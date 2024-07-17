package com.example.budgetbuddy.services;

import com.example.budgetbuddy.dao.UsersRepository;
import com.example.budgetbuddy.dto.AuthResponse;
import com.example.budgetbuddy.dto.LoginRequest;
import com.example.budgetbuddy.dto.RegisterRequest;
import com.example.budgetbuddy.modal.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private  final  JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public LoginService(UsersRepository usersRepository, JwtService jwtService, @Lazy PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public  boolean registration(RegisterRequest registerRequest){
          if(usersRepository.existsByEmail(registerRequest.email())){
              return  false;
          }
        Users users = new Users();
        users.setEmail(registerRequest.email());
        users.setName(registerRequest.name());
        users.setPasswordHash(passwordEncoder.encode(registerRequest.password()));
        usersRepository.save(users);
        return  true; 
    }

    public AuthResponse login(LoginRequest loginRequest){
        Optional<Users> usersOptional = usersRepository.findByEmail(loginRequest.email());
        if(usersOptional.isPresent()){
            Users users = usersOptional.get();
            if(passwordEncoder.matches(loginRequest.password(),users.getPassword())){
                 String token = jwtService.generateToken(users);
                 return  new AuthResponse(token);
            }
        }
        return  null;
    }


    public String passwordHashing(String password){
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password,salt);
    }
    public boolean checkPassword(String password, String hashedPassword){
          return  BCrypt.checkpw(password,hashedPassword);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  usersRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email"+email));
    }
}

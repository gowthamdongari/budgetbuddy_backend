package com.example.budgetbuddy.services;

import com.example.budgetbuddy.dao.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private  final  JwtService jwtService;

    public LoginService(UsersRepository usersRepository, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
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

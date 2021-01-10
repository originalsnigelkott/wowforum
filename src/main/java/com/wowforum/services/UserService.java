package com.wowforum.services;

import com.wowforum.configs.MyUserDetailsService;
import com.wowforum.dtos.UserCreateDto;
import com.wowforum.entities.User;
import com.wowforum.exceptions.BadRequestException;
import com.wowforum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MyUserDetailsService userDetailsService;

  public User createUser(UserCreateDto userCreateDto) {
    if (userCreateDto.getUsername().equals("anonymousUser") || userRepository.existsUserByUsername(userCreateDto.getUsername())) {
      throw new BadRequestException("Username is taken.");
    }
    var user = new User(userCreateDto);
    user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
    if (currentUserIsAdmin() && user.getRoles() != null && !user.getRoles().isBlank()) {
      user.setRoles(userCreateDto.getRoles());
    } else {
      user.setRoles("USER");
    }
    return userRepository.save(user);
  }

  public List<User> getUsers(String username) {
    System.out.println(username);
    if(username == null || username.isBlank()) {
      return userRepository.findAll();
    }
    return userRepository.findByUsernameContainingIgnoreCase(username);
  }

  private boolean currentUserIsAdmin() {
    var user = userDetailsService.getCurrentUser();
    return user != null && user.getRoles().contains("ADMIN");
  }
}

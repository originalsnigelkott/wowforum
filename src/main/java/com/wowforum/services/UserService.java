package com.wowforum.services;

import com.wowforum.configs.MyUserDetailsService;
import com.wowforum.dtos.UserCreateDto;
import com.wowforum.entities.User;
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
    var user = new User(userCreateDto);
    user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
    if (currentUserIsAdmin()) {
      user.setRoles(userCreateDto.getRoles());
    } else {
      user.setRoles("USER");
    }
    return userRepository.save(user);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  private boolean currentUserIsAdmin() {
    var user = userDetailsService.getCurrentUser();
    return user != null && user.getRoles().contains("ADMIN");
  }
}

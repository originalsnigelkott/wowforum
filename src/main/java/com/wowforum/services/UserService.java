package com.wowforum.services;

import com.wowforum.configs.MyUserDetailsService;
import com.wowforum.dtos.UserCreateDto;
import com.wowforum.dtos.UserUpdateDto;
import com.wowforum.entities.User;
import com.wowforum.exceptions.BadRequestException;
import com.wowforum.exceptions.EntityNotFoundException;
import com.wowforum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MyUserDetailsService userDetailsService;

  public User createUser(UserCreateDto userCreateDto) {
    if (userCreateDto.getUsername().equals("anonymousUser") || userRepository.existsUserByUsernameIgnoreCase(userCreateDto.getUsername())) {
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
    if(username == null || username.isBlank()) {
      return userRepository.findAll();
    }
    return userRepository.findByUsernameContainingIgnoreCase(username);
  }

  public void updateUser(UUID id, UserUpdateDto dto) {
    var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user", "id"));
    user.setRoles(dto.getRoles().toUpperCase());
    userRepository.save(user);
  }

  private boolean currentUserIsAdmin() {
    var user = userDetailsService.getCurrentUser();
    return user != null && user.getRoles().contains("ADMIN");
  }

  public void deleteUser(UUID id) {
    if(!userRepository.existsById(id)) {
      throw new EntityNotFoundException("user", "id");
    }
    userRepository.deleteById(id);
  }
}

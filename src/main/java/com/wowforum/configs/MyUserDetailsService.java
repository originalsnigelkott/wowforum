package com.wowforum.configs;

import com.wowforum.entities.User;
import com.wowforum.repositories.UserRepository;
import com.wowforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class MyUserDetailsService implements UserDetailsService {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @PostConstruct
  private void createDefaultUser() {
    if(!userRepository.existsUserByUsernameIgnoreCase("admin")) {
      var user = new User();
      user.setUsername("admin");
      user.setFirstName("Admin");
      user.setLastName("McAdminFace");
      user.setPassword(passwordEncoder.encode("password"));
      user.setRoles("USER,ADMIN");
      userRepository.save(user);
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s not found.", username)));
    return toUserDetails(user);
  }

  private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
    return Arrays.stream(user.getRoles().split(","))
      .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
      .collect(Collectors.toSet());
  }

  private UserDetails toUserDetails(User user) {
    return org.springframework.security.core.userdetails.User
      .withUsername(user.getUsername())
      .password(user.getPassword())
      .authorities(getGrantedAuthorities(user))
      .build();
  }

  public User getCurrentUser() {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (username == "anonymousUser") {
      return null;
    }
    var user = userRepository.findByUsername(username).orElse(null);
    return user;
  }
}

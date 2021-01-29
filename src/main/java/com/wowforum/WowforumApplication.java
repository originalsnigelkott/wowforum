package com.wowforum;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
  servers = {
    @Server(
      description = "Live url",
      url = "https://wow-en.hakkerbojz.tech"
    ),
    @Server(
      description = "Dev url",
      url = "http://localhost:3000"
    )
  }
)
@SpringBootApplication
public class WowforumApplication {
  public static void main(String[] args) {
    SpringApplication.run(WowforumApplication.class, args);
  }
}

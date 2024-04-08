package com.oauth.oauth_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.oauth.oauth_example.service", "com.oauth.oauth_example.handler", "com.oauth.oauth_example.config" })
public class OauthExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(OauthExampleApplication.class, args);
	}
}

package com.utfpr.delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BCryptPasswordEncoderConfig {

	@Bean
	public PasswordEncoder passwordEconder() {
		return new BCryptPasswordEncoder();
	}
	
}
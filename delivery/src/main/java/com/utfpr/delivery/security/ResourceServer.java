package com.utfpr.delivery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ResourceServer extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * http.csrf().disable().httpBasic() .and() .authorizeRequests() .anyRequest()
		 * .authenticated()
		 * .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
		 * STATELESS);
		 */

		http.authorizeRequests().antMatchers("/hello/**").permitAll().anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.oauth2ResourceServer().opaqueToken();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		String encodedPassword = passwordEncoder.encode("123");

		auth.inMemoryAuthentication().withUser("alexandre").password(encodedPassword).roles("ADMIN");
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}

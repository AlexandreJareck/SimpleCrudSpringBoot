package com.utfpr.delivery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEconder;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		String encodedPswd = passwordEconder.encode("123");
		
		clients.
		inMemory()
			.withClient("alexandre")
			.secret(encodedPswd)
			.authorizedGrantTypes("password")
			.scopes("write", "read")
			.accessTokenValiditySeconds(60 * 60 * 24 * 10)
			.and()
				.withClient("checktoken")
				.secret(encodedPswd);
	}	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		JwtAccessTokenConverter jwt = jwtAccessTokenConverter();

		endpoints.authenticationManager(authenticationManager)
			.accessTokenConverter(jwt);

	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security.checkTokenAccess("permitAll()");

	}
	
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		
		JwtAccessTokenConverter jwt = new JwtAccessTokenConverter();
		jwt.setSigningKey("40bd001563085fc35165329ea1ff5c5ecbdbbeef");
		
		return jwt;		
	}
		
}

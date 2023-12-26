package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(11);
//	}
	
	private static String[] WHITE_LIST = {"/register","/login"};
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		http
        .csrf((csrf) -> csrf.disable())
////		.csrf()
//		.disable()
		.cors(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(req->
			req.requestMatchers(WHITE_LIST)
		.permitAll()
		.anyRequest()
		.authenticated())
//		.and()
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		http.authenticationProvider(authenticationProvider);
//		.authenticationProvider(authenticationProvider)
		http.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}

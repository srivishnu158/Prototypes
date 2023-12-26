package com.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.repository.UserRepository;
import com.service.ServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	
//	private ServiceImpl serviceImpl;
	@Autowired
private UserRepository userRepository;
	@Autowired
private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		final String reqHeader = request.getHeader("Authorization");
		String jwt;
		if(reqHeader == null || !reqHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request,response);
			return;
		}
		jwt = reqHeader.substring(7);
		String useremail = jwtService.extractUsername(jwt);
		System.out.println(useremail);
		if(useremail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(useremail);
			if(jwtService.isTokenNotExpaired(jwt)) {
				UsernamePasswordAuthenticationToken authToken = new
						UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				System.out.println("reached "+authToken);
				SecurityContextHolder.getContext().setAuthentication(authToken);
				System.out.println("got it");
			}
		}
		else {
			System.out.println("got it");
		}
		
		filterChain.doFilter(request, response);
		
		
	}
	
}

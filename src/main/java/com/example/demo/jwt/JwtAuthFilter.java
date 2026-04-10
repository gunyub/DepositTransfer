package com.example.demo.jwt;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	private final JwtUtil jwtUtil;
	
	private JwtAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 	
									FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("----------------jwtFilter----------");
		String uri = request.getRequestURI();
		System.out.println("------------------------uri :" + uri);
		
		// /api/** 경로만 토큰 검사
		if(!uri.startsWith("/member")) {
			filterChain.doFilter(request, response);
			return ;
		}
		
		// Authorization 헤더에서 토큰 추출 Authorization
		String header = request.getHeader("Authorization");
		System.out.println(header);
		if(header == null || !header.startsWith("Bearer ") || header.equals("null")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String token = header.substring(7);
		System.out.println("필터에서확인 --" + token);
		// 토큰 유효성 검사
		if(!jwtUtil.isValid(token)) {
			response.sendRedirect("/loginForm");
			//filterChain.doFilter(request, response);
			return ;
		}
		
		filterChain.doFilter(request, response);
	}
	

	
}

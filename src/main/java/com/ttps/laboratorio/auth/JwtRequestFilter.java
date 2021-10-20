package com.ttps.laboratorio.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ttps.laboratorio.auth.config.WebSecurityConfig;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private static Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		try {
			String jwt = this.resolveToken(request);
			if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
				Authentication authentication = this.tokenProvider.getAuthentication(jwt);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			chain.doFilter(request, response);

			this.resetAuthenticationAfterRequest();
		} catch (ExpiredJwtException eje) {
			LOGGER.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			LOGGER.debug("Exception " + eje.getMessage(), eje);
		}
	}

	private void resetAuthenticationAfterRequest() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	private String resolveToken(HttpServletRequest request) {

		String bearerToken = request.getHeader(WebSecurityConfig.AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
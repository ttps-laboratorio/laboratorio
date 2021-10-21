package com.ttps.laboratorio.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.repository.IUserRepository;

@Component(value = "customAuthentication")
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = userRepository.findByUsername(authentication.getPrincipal().toString())
				.orElseThrow(() -> new UsernameNotFoundException(authentication.getPrincipal().toString()));
		if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))
			throw new UsernameNotFoundException(authentication.getPrincipal().toString());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
		return new UsernamePasswordAuthenticationToken(user.getUsername(), "", authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}

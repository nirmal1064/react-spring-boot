package com.mattermost.springauth.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mattermost.springauth.dao.AppUserDao;
import com.mattermost.springauth.model.AppUser;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AppUserDao appUserDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException, BadCredentialsException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		try {
			AppUser appUser = appUserDao.findByUserName(username);
			boolean matches = passwordEncoder.matches(password, appUser.getPassword());
			if (matches) {
				UserDetails userDetails = new User(username, password, new ArrayList<>());
				return new UsernamePasswordAuthenticationToken(userDetails, password, new ArrayList<>());
			} else {
				throw new BadCredentialsException("Username or Password is wrong");
			}
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("Username or Password is wrong");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

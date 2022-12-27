package com.mattermost.springauth.controller;

import static com.mattermost.springauth.utils.Constants.FAILURE;
import static com.mattermost.springauth.utils.Constants.SUCCESS;
import static com.mattermost.springauth.utils.Constants.TOKEN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mattermost.springauth.model.AppUser;
import com.mattermost.springauth.repository.AppUserRepository;
import com.mattermost.springauth.utils.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(value = "/api/auth")
@RestController
public class AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping(value = "/register")
	public ResponseEntity<AppUser> register(@RequestBody AppUser appUser) {
		try {
			AppUser appUserToSave = new AppUser();
			appUserToSave.setUsername(appUser.getUsername());
			appUserToSave.setPassword(passwordEncoder.encode(appUser.getPassword()));
			appUserToSave.setCountry(appUser.getCountry());
			appUserToSave.setName(appUser.getName());
			AppUser savedUser = appUserRepository.save(appUserToSave);
			LOGGER.info("User Saved");
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody AppUser appUser, HttpServletRequest request, HttpServletResponse response) {
		try {
			final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword());
			final Authentication auth = authenticationManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
			LOGGER.info("Name {}", auth.getName());
			LOGGER.info("Principal {}", auth.getPrincipal());
			String token = jwtUtils.generateToken(appUser.getUsername());
			Cookie cookie = new Cookie(TOKEN, token);
			cookie.setMaxAge(60*60*24);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);
 			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(FAILURE, HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(value = "/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(TOKEN, null);
		cookie.setMaxAge(0);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}

}

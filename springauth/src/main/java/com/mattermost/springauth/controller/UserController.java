package com.mattermost.springauth.controller;

import static com.mattermost.springauth.utils.Constants.USER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mattermost.springauth.model.AppUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "/home")
	public ResponseEntity<String> userHome(HttpServletRequest request, HttpServletResponse response) {
		try {
			AppUser appUser = (AppUser) request.getAttribute(USER);
			String greeting = "Welcome " + appUser.getUsername();
			return new ResponseEntity<>(greeting, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/details")
	public ResponseEntity<AppUser> getDetails(HttpServletRequest request, HttpServletResponse response) {
		try {
			AppUser appUser = (AppUser) request.getAttribute(USER);
			return new ResponseEntity<>(appUser, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

}

package com.mattermost.springauth.security;

import static com.mattermost.springauth.utils.Constants.TOKEN;
import static com.mattermost.springauth.utils.Constants.USER;
import static com.mattermost.springauth.utils.Constants.USERNAME;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.mattermost.springauth.dao.AppUserDao;
import com.mattermost.springauth.model.AppUser;
import com.mattermost.springauth.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AppUserDao appUserDao;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String username = getUserNameFromToken(request);
		if (username != null) {
			request.setAttribute(USERNAME, username);
			AppUser appUser = appUserDao.findByUserName(username);
			request.setAttribute(USER, appUser);
			UserDetails userDetails = new User(username, appUser.getPassword(), new ArrayList<>());
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		filterChain.doFilter(request, response);
	}

	private String getUserNameFromToken(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, TOKEN);
		if (cookie != null) {
			String token = cookie.getValue();
			return jwtUtils.validateAndGetUsernameFromToken(token);
		}
		return null;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURI().contains("/api/auth");
	}

}

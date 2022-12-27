package com.mattermost.springauth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mattermost.springauth.model.AppUser;
import com.mattermost.springauth.repository.AppUserRepository;

@Service
public class AppUserDao {

	@Autowired
	private AppUserRepository appUserRepository;

	public AppUser findByUserName(String username) throws UsernameNotFoundException {
		return appUserRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
	}

}

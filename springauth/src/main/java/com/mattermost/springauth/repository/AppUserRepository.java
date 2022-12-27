package com.mattermost.springauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mattermost.springauth.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	public Optional<AppUser> findByUsername(String username);

}

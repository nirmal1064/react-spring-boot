package com.mattermost.springauth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class AppUser {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = Access.WRITE_ONLY)
	private long id;

	@Column(unique = true)
	private String username;

	@Column
	private String name;

	@Column
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Column
	private String country;

}

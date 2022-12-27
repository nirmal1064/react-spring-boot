package com.mattermost.springauth.utils;

import static com.mattermost.springauth.utils.Constants.USERNAME;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(String username) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		Instant expiresAt = Instant.now().plus(1, ChronoUnit.DAYS);
		return JWT.create().withClaim(USERNAME, username).withExpiresAt(expiresAt).sign(algorithm);
	}

	public String validateAndGetUsernameFromToken(String token) throws JWTVerificationException {
		LOGGER.info("validating token {}", token);
		Algorithm algorithm = Algorithm.HMAC256(secret);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		String username = decodedJWT.getClaim(USERNAME).asString();
		return username;
	}

}

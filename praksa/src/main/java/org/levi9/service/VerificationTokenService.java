package org.levi9.service;

public interface VerificationTokenService {

	Boolean activateUser(Long userId, String verificationTokenValue);

}

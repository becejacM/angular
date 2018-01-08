package org.levi9.service.impl;

import org.levi9.model.User;
import org.levi9.model.VerificationToken;
import org.levi9.repository.UserRepository;
import org.levi9.repository.VerificationTokenRepository;
import org.levi9.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;

	@Autowired
	public VerificationTokenServiceImpl(UserRepository userRepository,
			VerificationTokenRepository verificationTokenRepository) {
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
	}

	@Override
	public Boolean activateUser(Long userId, String verificationTokenValue) {
		User user = (User) userRepository.findOne(userId);
		VerificationToken token = verificationTokenRepository.findByToken(verificationTokenValue);

		if (user == null || token == null)
			return false;
		if (!token.isOwner(userId))
			return false;

		user.setVerified(true);
		verificationTokenRepository.delete(token.getId());

		return true;
	}

	public String getTokenByUserIdAndPurpose(Long userId, String purpose) {
		return verificationTokenRepository.findTokenByUserIdAndPurpose(userId, purpose);
	}
}
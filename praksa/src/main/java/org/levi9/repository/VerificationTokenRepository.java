package org.levi9.repository;

import org.levi9.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String verificationTokenValue);

	String findTokenByUserIdAndPurpose(Long userId, String purpose);

	VerificationToken findByUserId(Long id);

}

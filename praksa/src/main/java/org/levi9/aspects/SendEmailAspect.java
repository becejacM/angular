package org.levi9.aspects;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.levi9.model.User;
import org.levi9.model.VerificationToken;
import org.levi9.repository.UserRepository;
import org.levi9.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author m.becejac
 *
 */
@Component
@Aspect
public class SendEmailAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailAspect.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VerificationTokenRepository verificationTokenRepository;

	@Value(value = "${spring.mail.username}")
	private String username;

	/**
	 * Function sends email to user who wants to register on site
	 * 
	 * @param joinPoint
	 *            - JoinPoint joinPoint
	 * @param user
	 *            - user who wants to register on site
	 * @throws Throwable
	 */
	@After("execution(* org.levi9.service.UserService.create(..)) && args(user,..)")
	@Async
	public void sendConfirmationEmail(JoinPoint joinPoint, User user) throws Throwable {
		LOGGER.info("@After: Before call - " + joinPoint.getTarget().getClass().getName() + " - " + new Date());

		VerificationToken vt = this.verificationTokenRepository.findByUserId(user.getId());

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(username);
		mail.setSubject("Registration confirm ");

		mail.setText("Hello " + user.getName()
				+ "\n Click and verify your email : http://localhost:8080/api/users/confirm/" + vt.getToken());
		javaMailSender.send(mail);

		LOGGER.info("@After: After call - " + joinPoint.getTarget().getClass().getName() + " - " + new Date());
	}

}
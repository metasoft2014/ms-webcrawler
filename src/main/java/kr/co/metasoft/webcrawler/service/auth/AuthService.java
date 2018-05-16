package kr.co.metasoft.webcrawler.service.auth;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import kr.co.metasoft.webcrawler.bean.auth.AuthBean;
import kr.co.metasoft.webcrawler.bean.auth.RegisterMessageBean;
import kr.co.metasoft.webcrawler.bean.auth.annotation.RegisterMessageCode;
import kr.co.metasoft.webcrawler.config.websecurity.AuthPasswordEncoder;
import kr.co.metasoft.webcrawler.config.websecurity.AuthUserDetails;
import kr.co.metasoft.webcrawler.entity.user.UserEntity;
import kr.co.metasoft.webcrawler.repository.user.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthPasswordEncoder authPasswordEncoder;

	public RegisterMessageBean register(AuthBean authBean) {
		RegisterMessageBean registerMessageBean = new RegisterMessageBean();
		String userName = authBean.getUserName();
		String email = authBean.getEmail();
		String password = authBean.getPassword();
		String confirmPassword = authBean.getConfirmPassword();
		String firstName = authBean.getFirstName();
		String lastName = authBean.getLastName();

		if (userRepository.existsByUserName(userName)) {
			registerMessageBean.setRegisterMessageCode(RegisterMessageCode.ERROR_EXISTS_USER_NAME);
			registerMessageBean.setMessage("User Name already exists!");
			return registerMessageBean;
		}

		if (userRepository.existsByEmail(email)) {
			registerMessageBean.setRegisterMessageCode(RegisterMessageCode.ERROR_EXISTS_EMAIL);
			registerMessageBean.setMessage("Email already exists!");
			return registerMessageBean;
		}

		if (!password.equals(confirmPassword)) {
			registerMessageBean.setRegisterMessageCode(RegisterMessageCode.ERROR_MISMATCH_CONFIRM_PASSWORD);
			registerMessageBean.setMessage("Confirm Password mismatch!");
			return registerMessageBean;
		}

		String encodedPassword = authPasswordEncoder.encode(password);
		UserEntity userEntity = new UserEntity();
		Timestamp now = Timestamp.from(Instant.now());
		userEntity.setUserName(userName);
		userEntity.setEmail(email);
		userEntity.setPassword(encodedPassword);
		userEntity.setFirstName(firstName);
		userEntity.setLastName(lastName);
		userEntity.setCreatedDate(now);
		userEntity.setUpdatedDate(now);
		userRepository.save(userEntity);
		registerMessageBean.setRegisterMessageCode(RegisterMessageCode.SUCCESS);
		registerMessageBean.setMessage("Register successful!");

		return registerMessageBean;
	}

	public UserEntity myInfo(Authentication authentication) {
		AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
		String username = authUserDetails.getUsername();
		UserEntity exampleUserEntity = new UserEntity();
		exampleUserEntity.setUserName(username);
		Example<UserEntity> example = Example.of(exampleUserEntity);
		UserEntity userEntity = userRepository.findOne(example).get();
		userEntity.setPassword("");
		return userEntity;
	}

	public RegisterMessageBean myInfoUpdate(Authentication authentication, AuthBean authBean) {
		UserEntity userEntity = myInfo(authentication);
		RegisterMessageBean registerMessageBean = new RegisterMessageBean();
		String userName = authBean.getUserName();
		String email = authBean.getEmail();
		String password = authBean.getPassword();
		String confirmPassword = authBean.getConfirmPassword();
		String firstName = authBean.getFirstName();
		String lastName = authBean.getLastName();

		if (!userName.equals(userEntity.getUserName())) {
			if (userRepository.existsByUserName(userName)) {
				registerMessageBean.setRegisterMessageCode(RegisterMessageCode.ERROR_EXISTS_USER_NAME);
				registerMessageBean.setMessage("User Name already exists!");
				return registerMessageBean;
			}
		}

		if (!email.equals(userEntity.getEmail())) {
			if (userRepository.existsByEmail(email)) {
				registerMessageBean.setRegisterMessageCode(RegisterMessageCode.ERROR_EXISTS_EMAIL);
				registerMessageBean.setMessage("Email already exists!");
				return registerMessageBean;
			}
		}

		if (!password.equals(confirmPassword)) {
			registerMessageBean.setRegisterMessageCode(RegisterMessageCode.ERROR_MISMATCH_CONFIRM_PASSWORD);
			registerMessageBean.setMessage("Confirm Password mismatch!");
			return registerMessageBean;
		}

		String encodedPassword = authPasswordEncoder.encode(password);
		Timestamp now = Timestamp.from(Instant.now());
		userEntity.setUserName(userName);
		userEntity.setEmail(email);
		userEntity.setPassword(encodedPassword);
		userEntity.setFirstName(firstName);
		userEntity.setLastName(lastName);
		userEntity.setUpdatedDate(now);
		userRepository.save(userEntity);
		registerMessageBean.setRegisterMessageCode(RegisterMessageCode.SUCCESS);
		registerMessageBean.setMessage("Update successful!");

		return registerMessageBean;
	}

	public void myInfoDelete(Authentication authentication) {
		UserEntity userEntity = myInfo(authentication);
		userRepository.delete(userEntity);
	}

}
package kr.co.metasoft.webcrawler.config.websecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import kr.co.metasoft.webcrawler.entity.user.UserEntity;
import kr.co.metasoft.webcrawler.service.user.UserService;

@Component
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity exampleUserEntity = new UserEntity();
		exampleUserEntity.setUserName(username);
		Example<UserEntity> example = Example.of(exampleUserEntity);
		UserEntity userEntity = userService.userSelectOne(example).get();
		return new AuthUserDetails(userEntity);
	}

}
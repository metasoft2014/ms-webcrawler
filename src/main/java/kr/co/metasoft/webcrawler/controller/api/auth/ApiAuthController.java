package kr.co.metasoft.webcrawler.controller.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.webcrawler.bean.auth.AuthBean;
import kr.co.metasoft.webcrawler.bean.auth.RegisterMessageBean;
import kr.co.metasoft.webcrawler.entity.user.UserEntity;
import kr.co.metasoft.webcrawler.service.auth.AuthService;

@RestController
@RequestMapping (path = "/api/auth")
public class ApiAuthController {

	@Autowired
	private AuthService authService;

	@RequestMapping (path = "/register", method = RequestMethod.POST)
	public RegisterMessageBean register(AuthBean authBean) {
		return authService.register(authBean);
	}

	@RequestMapping (path = "/my-info", method = RequestMethod.GET)
	public UserEntity myInfo(Authentication authentication) {
		return authService.myInfo(authentication);
	}

	@RequestMapping (path = "/my-info/update", method = RequestMethod.PUT)
	public RegisterMessageBean myInfoUpdate(Authentication authentication, AuthBean authBean) {
		return authService.myInfoUpdate(authentication, authBean);
	}

	@RequestMapping (path = "/my-info/delete", method = RequestMethod.DELETE)
	public void myInfoDelete(Authentication authentication) {
		authService.myInfoDelete(authentication);
	}

}
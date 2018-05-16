package kr.co.metasoft.webcrawler.controller.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.webcrawler.entity.user.UserEntity;
import kr.co.metasoft.webcrawler.service.user.UserService;

@RestController
@RequestMapping (path = "/api/users")
public class ApiUserController {

	@Autowired
	private UserService userService;

	@RequestMapping (path = "", method = RequestMethod.GET)
	public Page<UserEntity> userSelectList(
			UserEntity userEntity,
			Pageable pageable) {
		return userService.userSelectList(userEntity, pageable);
	}

	@RequestMapping (path = "/{userId}", method = RequestMethod.GET)
	public UserEntity userSelectOneById(
			@PathVariable (name = "userId", required = true) String userId) {
		return userService.userSelectOneById(userId).get();
	}

	@RequestMapping (path = "", method = RequestMethod.POST)
	public UserEntity userInsert(UserEntity userEntity) {
		return userService.userInsert(userEntity);
	}

	@RequestMapping (path = "/{userId}", method = RequestMethod.PUT)
	public UserEntity userUpdate(UserEntity userEntity) {
		return userService.userUpdate(userEntity);
	}

	@RequestMapping (path = "/{userId}", method = RequestMethod.DELETE)
	public void userDeleteById(
			@PathVariable (name = "userId", required = true) String userId) {
		userService.userDeleteById(userId);
	}

}
package kr.co.metasoft.webcrawler.service.user;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.metasoft.webcrawler.entity.user.UserEntity;
import kr.co.metasoft.webcrawler.repository.user.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Page<UserEntity> userSelectList(UserEntity userEntity, Pageable pageable) {
		Example<UserEntity> example = Example.of(userEntity);
		return userRepository.findAll(example, pageable);
	}

	public Optional<UserEntity> userSelectOneById(String userId) {
		return userRepository.findById(userId);
	}

	public Optional<UserEntity> userSelectOne(Example<UserEntity> example) {
		return userRepository.findOne(example);
	}

	public UserEntity userInsert(UserEntity userEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		userEntity.setCreatedDate(now);
		userEntity.setUpdatedDate(now);
		return userRepository.save(userEntity);
	}

	public UserEntity userUpdate(UserEntity userEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		userEntity.setUpdatedDate(now);
		return userRepository.save(userEntity);
	}

	public void userDeleteById(String userId) {
		userRepository.deleteById(userId);
	}

}
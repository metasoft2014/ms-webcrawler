package kr.co.metasoft.webcrawler.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.webcrawler.entity.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	boolean existsByUserName(String userName);

	boolean existsByEmail(String email);

}
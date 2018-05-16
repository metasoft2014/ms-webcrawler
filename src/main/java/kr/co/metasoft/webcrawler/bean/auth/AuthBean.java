package kr.co.metasoft.webcrawler.bean.auth;

import lombok.Data;

@Data
public class AuthBean {

	private String userName;

	private String email;

	private String password;

	private String confirmPassword;

	private String firstName;

	private String lastName;

}
package kr.co.metasoft.webcrawler.bean.auth.annotation;

public enum RegisterMessageCode {
	SUCCESS,
	ERROR_EXISTS_USER_NAME,
	ERROR_EXISTS_EMAIL,
	ERROR_MISMATCH_CONFIRM_PASSWORD;
}
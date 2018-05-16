package kr.co.metasoft.webcrawler.bean.auth;

import kr.co.metasoft.webcrawler.bean.auth.annotation.LoginMessageCode;
import lombok.Data;

@Data
public class LoginMessageBean {

	private LoginMessageCode loginMessageCode;

	private String message;

}
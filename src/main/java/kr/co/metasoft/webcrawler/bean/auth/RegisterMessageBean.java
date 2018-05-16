package kr.co.metasoft.webcrawler.bean.auth;

import kr.co.metasoft.webcrawler.bean.auth.annotation.RegisterMessageCode;
import lombok.Data;

@Data
public class RegisterMessageBean {

	private RegisterMessageCode registerMessageCode;

	private String message;

}
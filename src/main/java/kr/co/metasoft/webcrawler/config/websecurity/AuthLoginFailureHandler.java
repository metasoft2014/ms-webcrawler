package kr.co.metasoft.webcrawler.config.websecurity;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import kr.co.metasoft.webcrawler.bean.auth.LoginMessageBean;
import kr.co.metasoft.webcrawler.bean.auth.annotation.LoginMessageCode;

@Component
public class AuthLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException auth)
			throws IOException, ServletException {
		response.setHeader("Content-Type", "application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		LoginMessageBean loginMessageBean = new LoginMessageBean();
		loginMessageBean.setLoginMessageCode(LoginMessageCode.ERROR);
		loginMessageBean.setMessage("Login failed!");
		out.print(gson.toJson(loginMessageBean));
		out.flush();
	}

}
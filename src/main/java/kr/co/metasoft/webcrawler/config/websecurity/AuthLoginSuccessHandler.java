package kr.co.metasoft.webcrawler.config.websecurity;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import kr.co.metasoft.webcrawler.bean.auth.LoginMessageBean;
import kr.co.metasoft.webcrawler.bean.auth.annotation.LoginMessageCode;

@Component
public class AuthLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		AuthUserDetails authUserDetails = (AuthUserDetails) auth.getPrincipal();
		String username = authUserDetails.getUsername();
		simpMessagingTemplate.convertAndSend("/queue/users/" + username + "/session-expired", "SESSION_EXPIRED");

		response.setHeader("Content-Type", "application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		LoginMessageBean loginMessageBean = new LoginMessageBean();
		loginMessageBean.setLoginMessageCode(LoginMessageCode.SUCCESS);
		loginMessageBean.setMessage("Login successful!");
		out.print(gson.toJson(loginMessageBean));
		out.flush();
	}

}
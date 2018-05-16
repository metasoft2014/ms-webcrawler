package kr.co.metasoft.webcrawler.config.websecurity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import kr.co.metasoft.webcrawler.bean.crawler.annotation.CrawlerStatusCode;

@Component
public class AuthSessionDestroyedListener implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		List<SecurityContext> securityContextList = event.getSecurityContexts();
		securityContextList.forEach((securityContext) -> {
			AuthUserDetails authUserDetails = (AuthUserDetails) securityContext.getAuthentication().getPrincipal();
			String username = authUserDetails.getUsername();
			authUserDetails.getCrawlerWorker().setCrawlerStatusCode(CrawlerStatusCode.STOPPED);
			simpMessagingTemplate.convertAndSend("/queue/users/" + username + "/session-expired", "SESSION_EXPIRED");
		});
	}

}
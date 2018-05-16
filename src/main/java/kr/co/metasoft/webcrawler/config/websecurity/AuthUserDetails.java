package kr.co.metasoft.webcrawler.config.websecurity;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import kr.co.metasoft.webcrawler.bean.crawler.CrawlerBean;
import kr.co.metasoft.webcrawler.bean.crawler.annotation.CrawlerStatusCode;
import kr.co.metasoft.webcrawler.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

public class AuthUserDetails extends User {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private CrawlerBean crawlerWorker;

	{
		crawlerWorker = new CrawlerBean();
		crawlerWorker.setCrawlerStatusCode(CrawlerStatusCode.STOPPED);
	}

	public AuthUserDetails(UserEntity userEntity) {
		super(userEntity.getUserName(), userEntity.getPassword(), AuthorityUtils.createAuthorityList("USER"));
	}

}
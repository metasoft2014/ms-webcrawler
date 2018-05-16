package kr.co.metasoft.webcrawler.controller.api.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.webcrawler.bean.crawler.CrawlerBean;
import kr.co.metasoft.webcrawler.bean.crawler.CrawlerMessageBean;
import kr.co.metasoft.webcrawler.entity.crawler.CrawlerEntity;
import kr.co.metasoft.webcrawler.service.crawler.CrawlerService;

@RestController
@RequestMapping (path = "/api/crawlers")
public class ApiCrawlerController {

	@Autowired
	private CrawlerService crawlerService;

	@RequestMapping (path = "", method = RequestMethod.GET)
	public Page<CrawlerEntity> crawlerSelectList(
			CrawlerEntity crawlerEntity,
			Pageable pageable) {
		return crawlerService.crawlerSelectList(crawlerEntity, pageable);
	}

	@RequestMapping (path = "/{crawlerId}", method = RequestMethod.GET)
	public CrawlerEntity crawlerSelectOneById(
			@PathVariable (name = "crawlerId", required = true) Long crawlerId) {
		return crawlerService.crawlerSelectOneById(crawlerId).get();
	}

	@RequestMapping (path = "", method = RequestMethod.POST)
	public CrawlerEntity crawlerInsert(CrawlerEntity crawlerEntity) {
		return crawlerService.crawlerInsert(crawlerEntity);
	}

	@RequestMapping (path = "/{crawlerId}", method = RequestMethod.PUT)
	public CrawlerEntity crawlerUpdate(CrawlerEntity crawlerEntity) {
		return crawlerService.crawlerUpdate(crawlerEntity);
	}

	@RequestMapping (path = "/{crawlerId}", method = RequestMethod.DELETE)
	public void crawlerDeleteById(
			@PathVariable (name = "crawlerId", required = true) Long crawlerId) {
		crawlerService.crawlerDeleteById(crawlerId);
	}

	@RequestMapping (path = "/{crawlerId}/start", method = RequestMethod.POST)
	public CrawlerMessageBean crawlerStart(Authentication authentication, CrawlerBean crawlerBean) {
		return crawlerService.crawlerStart(authentication, crawlerBean);
	}

	@RequestMapping (path = "/{crawlerId}/stop", method = RequestMethod.POST)
	public void crawlerStop(Authentication authentication) {
		crawlerService.crawlerStop(authentication);
	}

}
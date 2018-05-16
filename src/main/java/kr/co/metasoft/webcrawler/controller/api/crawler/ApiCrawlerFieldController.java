package kr.co.metasoft.webcrawler.controller.api.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.webcrawler.entity.crawler.CrawlerFieldEntity;
import kr.co.metasoft.webcrawler.service.crawler.CrawlerFieldService;

@RestController
@RequestMapping (path = "/api/crawlers/{crawlerId}/fields")
public class ApiCrawlerFieldController {

	@Autowired
	private CrawlerFieldService crawlerFieldService;

	@RequestMapping (path = "", method = RequestMethod.GET)
	public Page<CrawlerFieldEntity> crawlerFieldSelectList(
			CrawlerFieldEntity crawlerFieldEntity,
			@PathVariable (name = "crawlerId", required = true) Long crawlerId,
			Pageable pageable) {
		crawlerFieldEntity.setCrawlerId(crawlerId);
		return crawlerFieldService.crawlerFieldSelectList(crawlerFieldEntity, pageable);
	}

	@RequestMapping (path = "/{crawlerFieldId}", method = RequestMethod.GET)
	public CrawlerFieldEntity crawlerFieldSelectOneById(
			@PathVariable (name = "crawlerFieldId", required = true) Long crawlerFieldId) {
		return crawlerFieldService.crawlerFieldSelectOneById(crawlerFieldId).get();
	}

	@RequestMapping (path = "", method = RequestMethod.POST)
	public CrawlerFieldEntity crawlerFieldInsert(CrawlerFieldEntity crawlerFieldEntity) {
		return crawlerFieldService.crawlerFieldInsert(crawlerFieldEntity);
	}

	@RequestMapping (path = "/{crawlerFieldId}", method = RequestMethod.PUT)
	public CrawlerFieldEntity crawlerFieldUpdate(CrawlerFieldEntity crawlerFieldEntity) {
		return crawlerFieldService.crawlerFieldUpdate(crawlerFieldEntity);
	}

	@RequestMapping (path = "/{crawlerFieldId}", method = RequestMethod.DELETE)
	public void crawlerFieldDeleteById(
			@PathVariable (name = "crawlerFieldId", required = true) Long crawlerFieldId) {
		crawlerFieldService.crawlerFieldDeleteById(crawlerFieldId);
	}

}
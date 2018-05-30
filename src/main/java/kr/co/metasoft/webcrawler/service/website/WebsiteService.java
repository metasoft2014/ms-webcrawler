package kr.co.metasoft.webcrawler.service.website;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.webcrawler.entity.crawler.CrawlerEntity;
import kr.co.metasoft.webcrawler.entity.crawler.CrawlerFieldEntity;
import kr.co.metasoft.webcrawler.entity.website.WebsiteEntity;
import kr.co.metasoft.webcrawler.repository.crawler.CrawlerFieldRepository;
import kr.co.metasoft.webcrawler.repository.crawler.CrawlerRepository;
import kr.co.metasoft.webcrawler.repository.website.WebsiteRepository;

@Service
public class WebsiteService {

	@Autowired
	private WebsiteRepository websiteRepository;

	@Autowired
	private CrawlerRepository crawlerRepository;

	@Autowired
	private CrawlerFieldRepository crawlerFieldRepository;

	public Page<WebsiteEntity> websiteSelectList(WebsiteEntity websiteEntity, Pageable pageable) {
		Example<WebsiteEntity> example = Example.of(websiteEntity);
		return websiteRepository.findAll(example, pageable);
	}

	public Optional<WebsiteEntity> websiteSelectOneById(Long websiteId) {
		return websiteRepository.findById(websiteId);
	}

	public WebsiteEntity websiteInsert(WebsiteEntity websiteEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		websiteEntity.setCreatedDate(now);
		websiteEntity.setUpdatedDate(now);
		return websiteRepository.save(websiteEntity);
	}

	public WebsiteEntity websiteUpdate(WebsiteEntity websiteEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		websiteEntity.setUpdatedDate(now);
		return websiteRepository.save(websiteEntity);
	}

	@Transactional
	public void websiteDeleteById(Long websiteId) {
		CrawlerEntity exampleCrawlerEntity = new CrawlerEntity();
		exampleCrawlerEntity.setWebsiteId(websiteId);
		List<CrawlerEntity> crawlerEntityList = crawlerRepository.findAll(Example.of(exampleCrawlerEntity));
		crawlerEntityList.forEach((crawlerEntity) -> {
			CrawlerFieldEntity exampleCrawlerFieldEntity = new CrawlerFieldEntity();
			exampleCrawlerFieldEntity.setCrawlerId(crawlerEntity.getCrawlerId());
			List<CrawlerFieldEntity> crawlerFieldEntityList = crawlerFieldRepository.findAll(Example.of(exampleCrawlerFieldEntity));
			crawlerFieldEntityList.forEach((crawlerFieldEntity) -> {
				crawlerFieldRepository.delete(crawlerFieldEntity);
			});
			crawlerRepository.delete(crawlerEntity);
		});
		websiteRepository.deleteById(websiteId);
	}

}
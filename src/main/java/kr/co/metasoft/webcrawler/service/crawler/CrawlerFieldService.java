package kr.co.metasoft.webcrawler.service.crawler;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.metasoft.webcrawler.entity.crawler.CrawlerFieldEntity;
import kr.co.metasoft.webcrawler.repository.crawler.CrawlerFieldRepository;

@Service
public class CrawlerFieldService {

	@Autowired
	private CrawlerFieldRepository crawlerFieldRepository;

	public Page<CrawlerFieldEntity> crawlerFieldSelectList(CrawlerFieldEntity crawlerFieldEntity, Pageable pageable) {
		Example<CrawlerFieldEntity> example = Example.of(crawlerFieldEntity);
		return crawlerFieldRepository.findAll(example, pageable);
	}

	public Optional<CrawlerFieldEntity> crawlerFieldSelectOneById(Long crawlerFieldId) {
		return crawlerFieldRepository.findById(crawlerFieldId);
	}

	public CrawlerFieldEntity crawlerFieldInsert(CrawlerFieldEntity crawlerFieldEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		crawlerFieldEntity.setCreatedDate(now);
		crawlerFieldEntity.setUpdatedDate(now);
		return crawlerFieldRepository.save(crawlerFieldEntity);
	}

	public CrawlerFieldEntity crawlerFieldUpdate(CrawlerFieldEntity crawlerFieldEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		crawlerFieldEntity.setUpdatedDate(now);
		return crawlerFieldRepository.save(crawlerFieldEntity);
	}

	public void crawlerFieldDeleteById(Long crawlerFieldId) {
		crawlerFieldRepository.deleteById(crawlerFieldId);
	}

}
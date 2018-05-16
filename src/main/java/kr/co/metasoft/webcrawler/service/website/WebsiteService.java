package kr.co.metasoft.webcrawler.service.website;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.metasoft.webcrawler.entity.website.WebsiteEntity;
import kr.co.metasoft.webcrawler.repository.website.WebsiteRepository;

@Service
public class WebsiteService {

	@Autowired
	private WebsiteRepository websiteRepository;

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

	public void websiteDeleteById(Long websiteId) {
		websiteRepository.deleteById(websiteId);
	}

}
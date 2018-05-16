package kr.co.metasoft.webcrawler.repository.crawler;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.webcrawler.entity.crawler.CrawlerFieldEntity;

@Repository
public interface CrawlerFieldRepository extends JpaRepository<CrawlerFieldEntity, Long> {

	Long deleteByCrawlerId(Long crawlerId);

	List<CrawlerFieldEntity> removeByCrawlerId(Long crawlerId);

}
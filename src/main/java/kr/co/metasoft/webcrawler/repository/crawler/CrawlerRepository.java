package kr.co.metasoft.webcrawler.repository.crawler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.webcrawler.entity.crawler.CrawlerEntity;

@Repository
public interface CrawlerRepository extends JpaRepository<CrawlerEntity, Long> {

}
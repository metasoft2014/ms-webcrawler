package kr.co.metasoft.webcrawler.repository.website;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.webcrawler.entity.website.WebsiteEntity;

@Repository
public interface WebsiteRepository extends JpaRepository<WebsiteEntity, Long> {

}
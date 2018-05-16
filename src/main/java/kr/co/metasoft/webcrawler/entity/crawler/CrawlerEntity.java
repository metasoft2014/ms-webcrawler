package kr.co.metasoft.webcrawler.entity.crawler;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity (name = "CRAWLER")
@Table (name = "MS_CRAWLER")
public class CrawlerEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.TABLE, generator = "MS_CRAWLER_SEQ_GENERATOR")
	@SequenceGenerator (name = "MS_CRAWLER_SEQ_GENERATOR", sequenceName = "MS_CRAWLER_SEQ", initialValue = 1, allocationSize = 1)
	@Column (name = "CRAWLER_ID")
	private Long crawlerId;

	@Column (name = "CRAWLER_NAME", nullable = false, unique = true)
	private String crawlerName;

	@Column (name = "TARGET_URL")
	private String targetUrl;

	@Column (name = "CREATED_DATE", updatable = false)
	private Timestamp createdDate;

	@Column (name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column (name = "WEBSITE_ID", nullable = false)
	private Long websiteId;

}
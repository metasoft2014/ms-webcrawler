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
@Entity (name = "CRAWLER_FIELD")
@Table (name = "MS_CRAWLER_FIELD")
public class CrawlerFieldEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.TABLE, generator = "MS_CRAWLER_FIELD_SEQ_GENERATOR")
	@SequenceGenerator (name = "MS_CRAWLER_FIELD_SEQ_GENERATOR", sequenceName = "MS_CRAWLER_FIELD_SEQ", initialValue = 1, allocationSize = 1)
	@Column (name = "CRAWLER_FIELD_ID")
	private Long crawlerFieldId;

	@Column (name = "CRAWLER_FIELD_NAME")
	private String crawlerFieldName;

	@Column (name = "CRAWLER_FIELD_ORDER")
	private Long crawlerFieldOrder;

	@Column (name = "CRAWLER_FIELD_TYPE")
	private String crawlerFieldType;

	@Column (name = "USE_STATUS")
	private String useStatus;

	@Column (name = "SELECTOR", length = 500)
	private String selector;

	@Column (name = "ATTRIBUTE_NAME")
	private String attributeName;

	@Column (name = "SCRIPT", length = 2000)
	private String script;

	@Column (name = "CREATED_DATE", updatable = false)
	private Timestamp createdDate;

	@Column (name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column (name = "CRAWLER_ID")
	private Long crawlerId;

}
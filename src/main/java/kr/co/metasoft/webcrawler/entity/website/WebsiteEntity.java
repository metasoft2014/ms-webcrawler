package kr.co.metasoft.webcrawler.entity.website;

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
@Entity (name = "WEBSITE")
@Table (name = "MS_WEBSITE")
public class WebsiteEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.TABLE, generator = "MS_WEBSITE_SEQ_GENERATOR")
	@SequenceGenerator (name = "MS_WEBSITE_SEQ_GENERATOR", sequenceName = "MS_WEBSITE_SEQ", initialValue = 1, allocationSize = 1)
	@Column (name = "WEBSITE_ID")
	private Long websiteId;

	@Column (name = "WEBSITE_NAME", nullable = false, unique = true)
	private String websiteName;

	@Column (name = "WEBSITE_DOMAIN_NAME")
	private String websiteDomainName;

	@Column (name = "CREATED_DATE", updatable = false)
	private Timestamp createdDate;

	@Column (name = "UPDATED_DATE")
	private Timestamp updatedDate;

}
package kr.co.metasoft.webcrawler.entity.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class CollectedDataEntityId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column (name = "CRAWLER_ID")
	private Long crawlerId;

	@Column (name = "DATA_KEY")
	private String dataKey;

}
package kr.co.metasoft.webcrawler.entity.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity (name = "ANALYZED_DATA")
@Table (name = "MS_ANALYZED_DATA")
public class AnalyzedDataEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.TABLE, generator = "MS_ANALYZED_DATA_SEQ_GENERATOR")
	@SequenceGenerator (name = "MS_ANALYZED_DATA_SEQ_GENERATOR", sequenceName = "MS_ANALYZED_DATA_SEQ", initialValue = 1, allocationSize = 1)
	@Column (name = "ANALYZED_DATA_ID")
	private Long analyzedDataId;

}
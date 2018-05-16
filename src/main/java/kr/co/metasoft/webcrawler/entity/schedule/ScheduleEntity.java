package kr.co.metasoft.webcrawler.entity.schedule;

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
@Entity (name = "SCHEDULE")
@Table (name = "MS_SCHEDULE")
public class ScheduleEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.TABLE, generator = "MS_SCHEDULE_SEQ_GENERATOR")
	@SequenceGenerator (name = "MS_SCHEDULE_SEQ_GENERATOR", sequenceName = "MS_SCHEDULE_SEQ", initialValue = 1, allocationSize = 1)
	@Column (name = "SCHEDULE_ID")
	private Long scheduleId;

	@Column (name = "SCHEDULE_NAME", nullable = false, unique = true)
	private String scheduleName;

	@Column (name = "SCHEDULE_CRON")
	private String scheduleCron;

	@Column (name = "CREATED_DATE", updatable = false)
	private Timestamp createdDate;

	@Column (name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column (name = "CRAWLER_ID")
	private Long crawlerId;

}
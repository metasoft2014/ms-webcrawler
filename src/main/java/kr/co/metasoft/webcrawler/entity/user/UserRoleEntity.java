package kr.co.metasoft.webcrawler.entity.user;

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
@Entity (name = "USER_ROLE")
@Table (name = "MS_USER_ROLE")
public class UserRoleEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.TABLE, generator = "MS_USER_ROLE_SEQ_GENERATOR")
	@SequenceGenerator (name = "MS_USER_ROLE_SEQ_GENERATOR", sequenceName = "MS_USER_ROLE_SEQ", initialValue = 1, allocationSize = 1)
	@Column (name = "USER_ROLE_ID")
	private Long userRoleId;

	@Column (name = "ROLE")
	private String role;

	@Column (name = "USER_ID")
	private Long userId;

	@Column (name = "CREATED_DATE", updatable = false)
	private Timestamp createdDate;

	@Column (name = "UPDATED_DATE")
	private Timestamp updatedDate;

}
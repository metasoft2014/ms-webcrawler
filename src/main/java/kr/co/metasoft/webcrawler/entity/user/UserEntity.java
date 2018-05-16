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
@Entity (name = "USER")
@Table (name = "MS_USER")
public class UserEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.TABLE, generator = "MS_USER_SEQ_GENERATOR")
	@SequenceGenerator (name = "MS_USER_SEQ_GENERATOR", sequenceName = "MS_USER_SEQ", initialValue = 1, allocationSize = 1)
	@Column (name = "USER_ID")
	private Long userId;

	@Column (name = "USER_NAME", nullable = false, unique = true)
	private String userName;

	@Column (name = "EMAIL", nullable = false, unique = true)
	private String email;

	@Column (name = "PASSWORD", nullable = false)
	private String password;

	@Column (name = "FIRST_NAME")
	private String firstName;

	@Column (name = "LAST_NAME")
	private String lastName;

	@Column (name = "CREATED_DATE", updatable = false)
	private Timestamp createdDate;

	@Column (name = "UPDATED_DATE")
	private Timestamp updatedDate;

}
package kr.co.metasoft.webcrawler.entity.data;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity (name = "COLLECTED_DATA")
@Table (name = "MS_COLLECTED_DATA")
public class CollectedDataEntity {

    @EmbeddedId
    private CollectedDataEntityId collectedDataEntityId;

    @Column (name = "KEYWORD")
    private String keyword;

    @Column (name = "TITLE", length = 500)
    private String title;

    @Column (name = "CONTENT", length = 15000)
    private String content;

    @Column (name = "WRITER")
    private String writer;

    @Column (name = "WRITTEN_DATE")
    private Timestamp writtenDate;

    @Column (name = "CREATED_DATE")
    private Timestamp createdDate;

}
package kr.co.metasoft.webcrawler.bean.crawler;

import java.sql.Timestamp;

import kr.co.metasoft.webcrawler.bean.crawler.annotation.CrawlerStatusCode;
import lombok.Data;

@Data
public class CrawlerMessageBean {

	private Long totalDataCount;

	private Long processedDataCount;

	private String dataKey;

	private String title;

	private String content;

	private String writer;

	private Timestamp writtenDate;

	private CrawlerStatusCode crawlerStatusCode;

}
package kr.co.metasoft.webcrawler.bean.crawler;

import kr.co.metasoft.webcrawler.bean.crawler.annotation.CrawlerStatusCode;
import lombok.Data;

@Data
public class CrawlerBean {

	private Long crawlerId;

	private String keyword;

	private Long dataSize;

	private CrawlerStatusCode crawlerStatusCode;

}
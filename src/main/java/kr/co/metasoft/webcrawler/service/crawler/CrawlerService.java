package kr.co.metasoft.webcrawler.service.crawler;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kr.co.metasoft.webcrawler.bean.crawler.CrawlerBean;
import kr.co.metasoft.webcrawler.bean.crawler.CrawlerMessageBean;
import kr.co.metasoft.webcrawler.bean.crawler.annotation.CrawlerStatusCode;
import kr.co.metasoft.webcrawler.config.websecurity.AuthUserDetails;
import kr.co.metasoft.webcrawler.entity.crawler.CrawlerEntity;
import kr.co.metasoft.webcrawler.entity.crawler.CrawlerFieldEntity;
import kr.co.metasoft.webcrawler.entity.data.CollectedDataEntity;
import kr.co.metasoft.webcrawler.entity.data.CollectedDataEntityId;
import kr.co.metasoft.webcrawler.repository.crawler.CrawlerFieldRepository;
import kr.co.metasoft.webcrawler.repository.crawler.CrawlerRepository;
import kr.co.metasoft.webcrawler.repository.data.CollectedDataRepository;

@Service
public class CrawlerService {

	@Autowired
	private CrawlerRepository crawlerRepository;

	@Autowired
	private CrawlerFieldRepository crawlerFieldRepository;

	@Autowired
	private CollectedDataRepository collectedDataRepository;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	public Page<CrawlerEntity> crawlerSelectList(CrawlerEntity crawlerEntity, Pageable pageable) {
		Example<CrawlerEntity> example = Example.of(crawlerEntity);
		return crawlerRepository.findAll(example, pageable);
	}

	public Optional<CrawlerEntity> crawlerSelectOneById(Long crawlerId) {
		return crawlerRepository.findById(crawlerId);
	}

	public CrawlerEntity crawlerInsert(CrawlerEntity crawlerEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		crawlerEntity.setCreatedDate(now);
		crawlerEntity.setUpdatedDate(now);
		return crawlerRepository.save(crawlerEntity);
	}

	public CrawlerEntity crawlerUpdate(CrawlerEntity crawlerEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		crawlerEntity.setUpdatedDate(now);
		return crawlerRepository.save(crawlerEntity);
	}

	@Transactional
	public void crawlerDeleteById(Long crawlerId) {
		crawlerRepository.deleteById(crawlerId);
		crawlerFieldRepository.removeByCrawlerId(crawlerId);
	}

	public CrawlerMessageBean crawlerStart(Authentication authentication, CrawlerBean crawlerBean) {
		AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
		String username = authUserDetails.getUsername();
		CrawlerBean crawlerWorker = authUserDetails.getCrawlerWorker();
		CrawlerMessageBean crawlerMessageBean = new CrawlerMessageBean();
		CrawlerStatusCode crawlerStatusCode = crawlerWorker.getCrawlerStatusCode();

		if (crawlerStatusCode.equals(CrawlerStatusCode.STOPPED)) {
			crawlerWorker.setCrawlerStatusCode(CrawlerStatusCode.RUNNING);

			Long crawlerId = crawlerBean.getCrawlerId();
			String keyword = crawlerBean.getKeyword();
			Long dataSize = crawlerBean.getDataSize();

			CrawlerFieldEntity exampleCrawlerFieldEntity = new CrawlerFieldEntity();
			exampleCrawlerFieldEntity.setCrawlerId(crawlerId);
			exampleCrawlerFieldEntity.setUseStatus("Y");
			Example<CrawlerFieldEntity> example = Example.of(exampleCrawlerFieldEntity);
			List<CrawlerFieldEntity> crawlerFieldEntityList = crawlerFieldRepository.findAll(example, PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "crawlerFieldOrder"))).getContent();
			CrawlerEntity crawlerEntity = crawlerRepository.findById(crawlerId).get();
			String targetUrl = crawlerEntity.getTargetUrl();
			targetUrl = targetUrl.replaceAll("#keyword#", keyword);

			WebDriver webDriver = new ChromeDriver();
			webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			webDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
			webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			webDriver.get(targetUrl);
			WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
			JavascriptExecutor je = (JavascriptExecutor) webDriver;

			Long processedDataCount = 0L;
			while (true) {
				crawlerStatusCode = crawlerWorker.getCrawlerStatusCode();

				if (processedDataCount >= dataSize
						|| crawlerStatusCode.equals(CrawlerStatusCode.STOPPED)) {
					break;
				}

				String dataKey = null;
				String title = null;
				String content = null;
				String writer = null;
				Timestamp writtenDate = null;

				try {
					System.out.println("------------------------------");
					System.out.println("Processed Data Count : " + (processedDataCount + 1));
					for (int i = 0; i < crawlerFieldEntityList.size(); i++) {
						CrawlerFieldEntity crawlerFieldEntity = crawlerFieldEntityList.get(i);
						String crawlerFieldName = crawlerFieldEntity.getCrawlerFieldName();
						String crawlerFieldType = crawlerFieldEntity.getCrawlerFieldType();
						String selector = crawlerFieldEntity.getSelector();
						String attributeName = crawlerFieldEntity.getAttributeName();
						String script = crawlerFieldEntity.getScript();
						String data = "";

						// Selector, Attribute Name
						if (StringUtils.hasText(selector)) {
							WebElement webElement = webDriverWait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.cssSelector(selector))));
							if (StringUtils.hasText(attributeName)) {
								data = webElement.getAttribute(attributeName);
							} else {
								data = webElement.getText();
							}
							if (StringUtils.hasText(crawlerFieldType)) {
								if ("DATA_KEY".equals(crawlerFieldType)) {
									dataKey = data;
								} else if ("TITLE".equals(crawlerFieldType)) {
									title = data;
								} else if ("CONTENT".equals(crawlerFieldType)) {
									content = data;
								} else if ("WRITER".equals(crawlerFieldType)) {
									writer = data;
								} else if ("WRITTEN_DATE".equals(crawlerFieldType)) {
									writtenDate = Timestamp.from(new Date(Long.parseLong(data)).toInstant());
								}
								if (!"ACTION".equals(crawlerFieldType)) {
									System.out.println(crawlerFieldName + " : " + data);
								}
							}
						}
						// Script
						if (StringUtils.hasText(script)) {
							je.executeScript(script);
						}
					}
					System.out.println("------------------------------");
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}

				CollectedDataEntity collectedDataEntity = new CollectedDataEntity();
				CollectedDataEntityId collectedDataEntityId = new CollectedDataEntityId();
				collectedDataEntityId.setCrawlerId(crawlerId);
				collectedDataEntityId.setDataKey(dataKey);
				collectedDataEntity.setCollectedDataEntityId(collectedDataEntityId);
				collectedDataEntity.setKeyword(keyword);
				collectedDataEntity.setTitle(title);
				collectedDataEntity.setContent(content);
				collectedDataEntity.setWriter(writer);
				collectedDataEntity.setWrittenDate(writtenDate);
				collectedDataEntity.setCreatedDate(Timestamp.from(Instant.now()));
				collectedDataRepository.save(collectedDataEntity);
				processedDataCount++;
				crawlerMessageBean.setTotalDataCount(dataSize);
				crawlerMessageBean.setProcessedDataCount(processedDataCount);
				crawlerMessageBean.setDataKey(dataKey);
				crawlerMessageBean.setTitle(title);
				crawlerMessageBean.setContent(content);
				crawlerMessageBean.setWriter(writer);
				crawlerMessageBean.setWrittenDate(writtenDate);
				crawlerMessageBean.setCrawlerStatusCode(crawlerStatusCode);
				simpMessagingTemplate.convertAndSend("/queue/crawler-status/" + username, crawlerMessageBean);
			}
			webDriver.quit();
			crawlerStatusCode = CrawlerStatusCode.STOPPED;
			crawlerWorker.setCrawlerStatusCode(crawlerStatusCode);
			crawlerMessageBean.setCrawlerStatusCode(crawlerStatusCode);
			simpMessagingTemplate.convertAndSend("/queue/crawler-status/" + username, crawlerMessageBean);
		}
		return crawlerMessageBean;
	}

	public void crawlerStop(Authentication authentication) {
		AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
		CrawlerBean crawlerWorker = authUserDetails.getCrawlerWorker();
		crawlerWorker.setCrawlerStatusCode(CrawlerStatusCode.STOPPED);
	}

}
package kr.co.metasoft.webcrawler.config.initializer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializerConfig {

	@Value (value = "${webdriver.chrome.driver}")
	private String webdriverChromeDriver;

	@PostConstruct
	public void initSystemProperties() {
		System.setProperty("webdriver.chrome.driver", webdriverChromeDriver);
	}

}
package kr.co.metasoft.webcrawler.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/register").setViewName("register");
		registry.addViewController("/forgot-password").setViewName("forgot-password");
		registry.addViewController("/session-expired").setViewName("session-expired");
		registry.addViewController("/dashboard").setViewName("dashboard");
		registry.addViewController("/my-info").setViewName("my-info");
		registry.addViewController("/website/website-list").setViewName("website/website-list");
		registry.addViewController("/website/website-form").setViewName("website/website-form");
		registry.addViewController("/crawler/crawler-list").setViewName("crawler/crawler-list");
		registry.addViewController("/crawler/crawler-form").setViewName("crawler/crawler-form");
		registry.addViewController("/schedule/schedule-list").setViewName("schedule/schedule-list");
		registry.addViewController("/schedule/schedule-form").setViewName("schedule/schedule-form");
		registry.addViewController("/analyzer/analyzer-list").setViewName("analyzer/analyzer-list");
		registry.addViewController("/analyzer/analyzer-form").setViewName("analyzer/analyzer-form");
	}

}
package kr.co.metasoft.webcrawler.controller.api.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.webcrawler.entity.website.WebsiteEntity;
import kr.co.metasoft.webcrawler.service.website.WebsiteService;

@RestController
@RequestMapping (path = "/api/websites")
public class ApiWebsiteController {

	@Autowired
	private WebsiteService websiteService;

	@RequestMapping (path = "", method = RequestMethod.GET)
	public Page<WebsiteEntity> websiteSelectList(
			WebsiteEntity websiteEntity,
			Pageable pageable) {
		return websiteService.websiteSelectList(websiteEntity, pageable);
	}

	@RequestMapping (path = "/{websiteId}", method = RequestMethod.GET)
	public WebsiteEntity websiteSelectOneById(
			@PathVariable (name = "websiteId", required = true) Long websiteId) {
		return websiteService.websiteSelectOneById(websiteId).get();
	}

	@RequestMapping (path = "", method = RequestMethod.POST)
	public WebsiteEntity websiteInsert(WebsiteEntity websiteEntity) {
		return websiteService.websiteInsert(websiteEntity);
	}

	@RequestMapping (path = "/{websiteId}", method = RequestMethod.PUT)
	public WebsiteEntity websiteUpdate(WebsiteEntity websiteEntity) {
		return websiteService.websiteUpdate(websiteEntity);
	}

	@RequestMapping (path = "/{websiteId}", method = RequestMethod.DELETE)
	public void websiteDeleteById(
			@PathVariable (name = "websiteId", required = true) Long websiteId) {
		websiteService.websiteDeleteById(websiteId);
	}

}
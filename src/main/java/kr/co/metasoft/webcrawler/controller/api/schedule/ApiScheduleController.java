package kr.co.metasoft.webcrawler.controller.api.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.metasoft.webcrawler.entity.schedule.ScheduleEntity;
import kr.co.metasoft.webcrawler.service.schedule.ScheduleService;

@RestController
@RequestMapping (path = "/api/schedules")
public class ApiScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@RequestMapping (path = "", method = RequestMethod.GET)
	public Page<ScheduleEntity> scheduleSelectList(
			ScheduleEntity scheduleEntity,
			Pageable pageable) {
		return scheduleService.scheduleSelectList(scheduleEntity, pageable);
	}

	@RequestMapping (path = "/{scheduleId}", method = RequestMethod.GET)
	public ScheduleEntity scheduleSelectOneById(
			@PathVariable (name = "scheduleId", required = true) Long scheduleId) {
		return scheduleService.scheduleSelectOneById(scheduleId).get();
	}

	@RequestMapping (path = "", method = RequestMethod.POST)
	public ScheduleEntity scheduleInsert(ScheduleEntity scheduleEntity) {
		return scheduleService.scheduleInsert(scheduleEntity);
	}

	@RequestMapping (path = "/{scheduleId}", method = RequestMethod.PUT)
	public ScheduleEntity scheduleUpdate(ScheduleEntity scheduleEntity) {
		return scheduleService.scheduleUpdate(scheduleEntity);
	}

	@RequestMapping (path = "/{scheduleId}", method = RequestMethod.DELETE)
	public void scheduleDeleteById(
			@PathVariable (name = "scheduleId", required = true) Long scheduleId) {
		scheduleService.scheduleDeleteById(scheduleId);
	}

}
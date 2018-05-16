package kr.co.metasoft.webcrawler.service.schedule;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.metasoft.webcrawler.entity.schedule.ScheduleEntity;
import kr.co.metasoft.webcrawler.repository.schedule.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	public Page<ScheduleEntity> scheduleSelectList(ScheduleEntity scheduleEntity, Pageable pageable) {
		Example<ScheduleEntity> example = Example.of(scheduleEntity);
		return scheduleRepository.findAll(example, pageable);
	}

	public Optional<ScheduleEntity> scheduleSelectOneById(Long scheduleId) {
		return scheduleRepository.findById(scheduleId);
	}

	public ScheduleEntity scheduleInsert(ScheduleEntity scheduleEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		scheduleEntity.setCreatedDate(now);
		scheduleEntity.setUpdatedDate(now);
		return scheduleRepository.save(scheduleEntity);
	}

	public ScheduleEntity scheduleUpdate(ScheduleEntity scheduleEntity) {
		Timestamp now = Timestamp.from(Instant.now());
		scheduleEntity.setUpdatedDate(now);
		return scheduleRepository.save(scheduleEntity);
	}

	public void scheduleDeleteById(Long scheduleId) {
		scheduleRepository.deleteById(scheduleId);
	}

}
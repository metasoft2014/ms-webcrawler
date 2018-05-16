package kr.co.metasoft.webcrawler.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.webcrawler.entity.data.CollectedDataEntity;

@Repository
public interface CollectedDataRepository extends JpaRepository<CollectedDataEntity, Long> {

}
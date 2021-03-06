package com.example.springcourse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springcourse.domain.RequestStage;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long> {
	
	public Optional<List<RequestStage>> findAllByRequestId(Long id);
	
	public Page<RequestStage> findAllByRequestId(Long id, Pageable pageable);

}

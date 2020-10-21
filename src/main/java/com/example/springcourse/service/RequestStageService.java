package com.example.springcourse.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.enums.RequestState;
import com.example.springcourse.repository.RequestRepository;
import com.example.springcourse.repository.RequestStageRepository;

@Service
public class RequestStageService {
	
	@Autowired
	private RequestStageRepository requestStageRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	public RequestStage createRequestStage(RequestStage stage) {
		
		stage.setRealizationDate(new Date());
		RequestStage createdStage = requestStageRepository.save(stage);
		
		Long requestId = stage.getRequest().getId();
		RequestState state = stage.getState();
		
		requestRepository.updateRequest(requestId, state);
		
		return createdStage;
	}
	
	public RequestStage getRequestStageById(Long id) {
		Optional<RequestStage> result = requestStageRepository.findById(id);
		
		return result.get();
	}
	
	public List<RequestStage> getAllRequestStageByRequestId(Long id) {
		Optional<List<RequestStage>> result = requestStageRepository.findAllByRequestId(id);
		
		return result.get();
	}

}

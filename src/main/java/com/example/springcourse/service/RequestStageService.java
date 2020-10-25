package com.example.springcourse.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.enums.RequestState;
import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
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

		return result.orElseThrow(() -> new NotFoundException("There are no request stage with id " + id));
	}

	public List<RequestStage> getAllRequestStageByRequestId(Long id) {
		Optional<List<RequestStage>> result = requestStageRepository.findAllByRequestId(id);

		return result.orElseThrow(() -> new NotFoundException("There are no request stage to request id " + id));
	}

	public PageModel<RequestStage> getAllRequestStageByRequestIdOnLazyMode(Long id, PageRequestModel pr) {
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<RequestStage> pageRequestStage = requestStageRepository.findAllByRequestId(id, pageable);
		PageModel<RequestStage> requestStages = new PageModel<>((int) pageRequestStage.getTotalElements(),
				pageRequestStage.getSize(), pageRequestStage.getTotalPages(), pageRequestStage.getContent());
		
		return requestStages;
	}

}

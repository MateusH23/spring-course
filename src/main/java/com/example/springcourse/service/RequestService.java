package com.example.springcourse.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.enums.RequestState;
import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.repository.RequestRepository;

@Service
public class RequestService {

	@Autowired
	private RequestRepository requestRepository;

	public Request createRequest(Request request) {

		request.setState(RequestState.OPEN);
		request.setCreationDate(new Date());

		return requestRepository.save(request);
	}

	public Request updateRequest(Request request) {
		return requestRepository.save(request);
	}

	public Request getRequestById(Long id) {
		Optional<Request> result = requestRepository.findById(id);

		return result.orElseThrow(() -> new NotFoundException("There are no request with id " + id));
	}

	public List<Request> getAllRequest() {
		return requestRepository.findAll();
	}

	public List<Request> getAllRequestByOwnerId(Long ownerId) {
		Optional<List<Request>> result = requestRepository.findAllByOwnerId(ownerId);

		return result.orElseThrow(() -> new NotFoundException("There are no request to owner id " + ownerId));
	}

	public PageModel<Request> getAllRequestOnLazyMode(PageRequestModel pr) {
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<Request> pageRequest = requestRepository.findAll(pageable);
		PageModel<Request> requests = new PageModel<>((int) pageRequest.getTotalElements(), pageRequest.getSize(),
				pageRequest.getTotalPages(), pageRequest.getContent());
		
		return requests;
	}

	public PageModel<Request> getAllRequestByOwnerIdOnLazyMode(Long ownerId, PageRequestModel pr) {
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<Request> pageRequest = requestRepository.findAllByOwnerId(ownerId, pageable);
		PageModel<Request> requests = new PageModel<>((int) pageRequest.getTotalElements(), pageRequest.getSize(),
				pageRequest.getTotalPages(), pageRequest.getContent());

		return requests;
	}

}

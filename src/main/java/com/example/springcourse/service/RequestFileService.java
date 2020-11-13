package com.example.springcourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.RequestFile;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.repository.RequestFileRepository;

@Service
public class RequestFileService {
	
	@Autowired
	private RequestFileRepository fileRepository;
	
	// upload
	
	public PageModel<RequestFile> findAllByRequestId(Long id, PageRequestModel prm) {
		Pageable pageable = PageRequest.of(prm.getPage(), prm.getSize());
		Page<RequestFile> page = fileRepository.findAllByRequestId(id, pageable);
		
		PageModel<RequestFile> pageModel = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		
		return pageModel;
	}

}

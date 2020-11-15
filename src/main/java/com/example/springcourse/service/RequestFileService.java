package com.example.springcourse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestFile;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.model.UploadedFileModel;
import com.example.springcourse.repository.RequestFileRepository;
import com.example.springcourse.service.s3.S3Service;

@Service
public class RequestFileService {
	
	@Autowired
	private RequestFileRepository fileRepository;
	
	@Autowired
	private S3Service s3Service;
	
	public List<RequestFile> upload(Long id, MultipartFile[] files) {
		List<UploadedFileModel> uploadedFiles = s3Service.upload(files);
		List<RequestFile> requestFiles = new ArrayList<>();
		
		Request request = new Request();
		request.setId(id);
		
		uploadedFiles.forEach(uploadedFile -> {
			RequestFile requestFile = new RequestFile(null, uploadedFile.getName(), uploadedFile.getLocation(), request);
			requestFiles.add(requestFile);
		});
		
		return fileRepository.saveAll(requestFiles);
	}
	
	public PageModel<RequestFile> findAllByRequestId(Long id, PageRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();
		Page<RequestFile> page = fileRepository.findAllByRequestId(id, pageable);
		
		PageModel<RequestFile> pageModel = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		
		return pageModel;
	}

}

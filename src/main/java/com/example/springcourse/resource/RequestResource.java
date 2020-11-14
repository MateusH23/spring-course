package com.example.springcourse.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestFile;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.dto.RequestSaveDTO;
import com.example.springcourse.dto.RequestUpdateDTO;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.service.RequestFileService;
import com.example.springcourse.service.RequestService;
import com.example.springcourse.service.RequestStageService;

@RestController
@RequestMapping("/requests")
public class RequestResource {

	@Autowired
	private RequestService requestService;

	@Autowired
	private RequestStageService stageService;

	@Autowired
	private RequestFileService fileService;

	@PostMapping
	public ResponseEntity<Request> create(@RequestBody @Valid RequestSaveDTO requestDTO) {
		Request request = requestDTO.transformToRequest();
		Request createdRequest = requestService.createRequest(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}

	@PreAuthorize("@accessManager.isRequestOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable("id") Long id,
			@RequestBody @Valid RequestUpdateDTO requestDTO) {
		Request request = requestDTO.transformToRequest();
		request.setId(id);
		Request updatedRequest = requestService.updateRequest(request);
		return ResponseEntity.ok(updatedRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Request> getById(@PathVariable("id") Long id) {
		Request request = requestService.getRequestById(id);
		return ResponseEntity.ok(request);
	}

//	@GetMapping
//	public ResponseEntity<List<Request>> listAll() {
//		List<Request> requests = requestService.getAllRequest();
//		return ResponseEntity.ok(requests);
//	}

//	@GetMapping("/{id}/stages")
//	public ResponseEntity<List<RequestStage>> getAllStageById(@PathVariable("id") Long id) {
//		List<RequestStage> stages = stageService.getAllRequestStageByRequestId(id);
//		return ResponseEntity.ok(stages);
//	}

	@GetMapping
	public ResponseEntity<PageModel<Request>> listAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<Request> requests = requestService.getAllRequestOnLazyMode(pr);

		return ResponseEntity.ok(requests);
	}

	@GetMapping("/{id}/stages")
	public ResponseEntity<PageModel<RequestStage>> getAllStageById(@PathVariable("id") Long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<RequestStage> pageRequest = stageService.getAllRequestStageByRequestIdOnLazyMode(id, pr);

		return ResponseEntity.ok(pageRequest);
	}

	@GetMapping("/{id}/files")
	public ResponseEntity<PageModel<RequestFile>> getAllFilesByRequestId(@PathVariable("id") Long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		PageRequestModel pm = new PageRequestModel(page, size);

		PageModel<RequestFile> pageModel = fileService.findAllByRequestId(id, pm);

		return ResponseEntity.ok(pageModel);
	}

	@PostMapping("/{id}/files")
	public ResponseEntity<List<RequestFile>> upload(@PathVariable("id") Long id,
			@RequestParam("files") MultipartFile[] files) {

		List<RequestFile> requestFiles = fileService.upload(id, files);

		return ResponseEntity.status(HttpStatus.CREATED).body(requestFiles);
	}

}

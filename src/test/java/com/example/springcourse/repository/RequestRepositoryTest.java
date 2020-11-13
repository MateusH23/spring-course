package com.example.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.RequestState;

@SpringBootTest
public class RequestRepositoryTest {
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Test
	public void aCreateRequestTest() {
		User owner = new User();
		owner.setId(1L);
		Request request = new Request(null, "Acer Predator Helios 300", "Comprando um notebook gamer", new Date(), RequestState.OPEN, owner, null, null);
		
		Request createdRequest = requestRepository.save(request);
		
		assertThat(createdRequest.getId()).isEqualTo(1L);
	}
	
	@Test
	public void updateRequestTest() {
		User owner = new User();
		owner.setId(1L);
		Request request = new Request(1L, "Acer Predator Helios 300 Ci7 16 GB GTX 1660TI", "Comprando um notebook gamer", null, RequestState.OPEN, owner, null, null);
		
		Request updatedRequest = requestRepository.save(request);
		
		assertThat(updatedRequest.getSubject()).isEqualTo("Acer Predator Helios 300 Ci7 16 GB GTX 1660TI");
	}
	
	@Test
	public void getRequestByIdTest() {
		Optional<Request> result = requestRepository.findById(1L);
		
		if(result.isPresent()) {
			Request request = result.get();
			
			assertThat(request.getDescription()).isEqualTo("Comprando um notebook gamer");
		}
	}
	
	@Test
	public void listAllRequestTest() {
		List<Request> requests = requestRepository.findAll();
		
		assertThat(requests.size()).isEqualTo(1);
	}
	
	@Test
	public void getAllRequestByOwnerIdTest() {
		Optional<List<Request>> result = requestRepository.findAllByOwnerId(1L);
		
		if(result.isPresent()) {
			List<Request> requestsOwner = result.get();
			
			assertThat(requestsOwner.size()).isEqualTo(1);
		}
	}
	
	@Test
	public void updateRequestStateTest() {
		int affectedRows = requestRepository.updateRequest(1L, RequestState.IN_PROGRESS);
		
		assertThat(affectedRows).isEqualTo(1);
	}

}

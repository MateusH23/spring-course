package com.example.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.RequestState;

@SpringBootTest
public class RequestStageRepositoryTest {
	
	@Autowired
	private RequestStageRepository requestStageRepository;
	
	@Test
	public void saveRequestStageTest() {
		User owner = new User();
		owner.setId(1L);
		
		Request request = new Request();
		request.setId(1L);
		
		RequestStage createdRequest = requestStageRepository.save(new RequestStage(null, new Date(), "Compra de novo notebook gamer efetuada.", RequestState.CLOSED, owner, request));
		
		assertThat(createdRequest.getId()).isEqualTo(1L);
	}
	
	@Test
	public void getByRequestStageIdTest() {
		Optional<RequestStage> result = requestStageRepository.findById(1L);
		
		RequestStage requestStage = result.get();
		
		assertThat(requestStage.getDescription()).isEqualTo("Compra de novo notebook gamer efetuada.");
	}
	
	@Test
	public void findAllByRequestIdTest() {
		Optional<List<RequestStage>> requestStages = requestStageRepository.findAllByRequestId(1L);
		
		assertThat(requestStages.get().size()).isEqualTo(1);
	}

}

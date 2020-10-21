package com.example.springcourse.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HashUtilTest {
	
	@Test
	public void getSecureHashTest() {
		String hash = HashUtil.getSecureHash("123");
		System.out.println(hash);
		assertThat(hash.length()).isEqualTo(64);
	}

}

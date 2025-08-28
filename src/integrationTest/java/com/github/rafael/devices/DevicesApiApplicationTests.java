package com.github.rafael.devices;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DevicesApiApplicationTests {

	@Test
	void contextLoads() {

		assertThat(200).isEqualTo(200);
	}

}

package com.github.rafael.devices;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mockStatic;

class DevicesApiApplicationTest {

    @Test
    void main_shouldInvokeSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(DevicesApiApplication.class, new String[]{}))
                    .thenReturn(null);

            assertThatCode(() -> DevicesApiApplication.main(new String[]{}))
                    .doesNotThrowAnyException();

            mocked.verify(() -> SpringApplication.run(DevicesApiApplication.class, new String[]{}));
        }
    }
}
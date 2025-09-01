package com.github.rafael.devices.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.assertj.core.api.Assertions.assertThat;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void notFound_shouldReturnProblemDetailWith404AndMessage() {
        IllegalArgumentException ex = new IllegalArgumentException("Device not found");

        ProblemDetail pd = handler.notFound(ex);

        assertThat(pd).isNotNull();
        assertThat(pd.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(pd.getDetail()).isEqualTo("Device not found");
    }

    @Test
    void conflict_shouldReturnProblemDetailWith409AndMessage() {
        IllegalStateException ex = new IllegalStateException("Invalid state");

        ProblemDetail pd = handler.conflict(ex);

        assertThat(pd).isNotNull();
        assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getDetail()).isEqualTo("Invalid state");
    }
}

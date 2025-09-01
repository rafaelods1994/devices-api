package com.github.rafael.devices.it;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

class DevicesApiErrorTest extends BaseApiTest {

    @Test
    void createShouldReturn400WhenNameMissing() {
        String body = """
            {"brand":"Nest","state":"AVAILABLE"}
        """;
        ResponseEntity<String> resp = rest.postForEntity(baseUrl(), new HttpEntity<>(body, jsonHeaders()), String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createShouldReturn400WhenStateInvalid() {
        String body = """
            {"name":"Thermostat","brand":"Nest","state":"INVALID"}
        """;
        ResponseEntity<String> resp = rest.postForEntity(baseUrl(), new HttpEntity<>(body, jsonHeaders()), String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getNonExistentShouldReturn404() {
        ResponseEntity<String> resp = rest.getForEntity(baseUrl() + "/999999", String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteNonExistentShouldReturn404() {
        ResponseEntity<Void> resp = rest.exchange(baseUrl() + "/999999", HttpMethod.DELETE, null, Void.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

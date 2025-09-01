package com.github.rafael.devices.it;

import com.github.rafael.devices.it.dto.DeviceDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

class DevicesApiDeleteEdgeTest extends BaseApiTest {

    @Test
    void deleteAlreadyDeletedShouldReturn404() {
        DeviceDto created = createDevice("Router","TP-Link","AVAILABLE");

        ResponseEntity<Void> first = rest.exchange(
                baseUrl() + "/" + created.id(), HttpMethod.DELETE, null, Void.class);
        assertThat(first.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Void> second = rest.exchange(
                baseUrl() + "/" + created.id(), HttpMethod.DELETE, null, Void.class);
        assertThat(second.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteInvalidIdShouldReturn404() {
        ResponseEntity<Void> resp = rest.exchange(
                baseUrl() + "/-1", HttpMethod.DELETE, null, Void.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private DeviceDto createDevice(String name, String brand, String state) {
        String body = """
            {"name":"%s","brand":"%s","state":"%s"}
            """.formatted(name, brand, state);

        return rest.exchange(
                baseUrl(),
                HttpMethod.POST,
                new HttpEntity<>(body, jsonHeaders()),
                DeviceDto.class
        ).getBody();
    }
}

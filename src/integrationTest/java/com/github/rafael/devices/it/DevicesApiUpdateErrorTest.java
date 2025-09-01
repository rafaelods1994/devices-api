package com.github.rafael.devices.it;

import com.github.rafael.devices.it.dto.DeviceDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

class DevicesApiUpdateErrorTest extends BaseApiTest {

    @Test
    void updateNonExistentShouldReturn404() {
        String body = """
            {"name":"Ghost","brand":"Phantom","state":"AVAILABLE"}
        """;

        ResponseEntity<String> resp = rest.exchange(
                baseUrl() + "/999999",
                HttpMethod.PUT,
                new HttpEntity<>(body, jsonHeaders()),
                String.class
        );

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateShouldReturn400WhenMissingName() {
        DeviceDto created = create("Lamp", "Philips", "AVAILABLE");

        String badUpdate = """
            {"name":"","brand":"Philips","state":"AVAILABLE"}
        """;

        ResponseEntity<String> resp = rest.exchange(
                baseUrl() + "/" + created.id(),
                HttpMethod.PUT,
                new HttpEntity<>(badUpdate, jsonHeaders()),
                String.class
        );

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateShouldReturn400WhenInvalidState() {
        DeviceDto created = create("Fan", "Dyson", "AVAILABLE");

        String badUpdate = """
            {"name":"Fan","brand":"Dyson","state":"INVALID"}
        """;

        ResponseEntity<String> resp = rest.exchange(
                baseUrl() + "/" + created.id(),
                HttpMethod.PUT,
                new HttpEntity<>(badUpdate, jsonHeaders()),
                String.class
        );

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private DeviceDto create(String name, String brand, String state) {
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

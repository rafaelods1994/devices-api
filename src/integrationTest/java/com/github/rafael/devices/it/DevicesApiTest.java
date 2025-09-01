package com.github.rafael.devices.it;

import com.github.rafael.devices.it.dto.DeviceDto;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class DevicesApiTest extends BaseApiTest {

    record PageResponse<T>(List<T> content) {}

    @Test
    void createAndFetchDevice() {
        String body = """
            {"name":"Thermostat","brand":"Nest","state":"AVAILABLE"}
        """;

        ResponseEntity<DeviceDto> created = rest.exchange(
                baseUrl(), HttpMethod.POST, new HttpEntity<>(body, jsonHeaders()), DeviceDto.class
        );

        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(created.getBody()).isNotNull();

        Long id = created.getBody().id();

        ResponseEntity<DeviceDto> fetched = rest.getForEntity(
                baseUrl() + "/" + id, DeviceDto.class
        );

        assertThat(fetched.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetched.getBody()).isNotNull();
        assertThat(fetched.getBody().id()).isEqualTo(created.getBody().id());
        assertThat(fetched.getBody().name()).isEqualTo(created.getBody().name());
        assertThat(fetched.getBody().brand()).isEqualTo(created.getBody().brand());
        assertThat(fetched.getBody().state()).isEqualTo(created.getBody().state());
        assertThat(fetched.getBody().createdAt())
                .isCloseTo(created.getBody().createdAt(), within(1, ChronoUnit.MICROS));
    }

    @Test
    void listDevices() {
        ResponseEntity<PageResponse<DeviceDto>> resp = rest.exchange(
                baseUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageResponse<DeviceDto>>() {}
        );

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().content()).isNotNull();
    }

    @Test
    void updateDevice() {
        DeviceDto created = create("Lamp", "Philips", "AVAILABLE");

        String update = """
            {"name":"Lamp","brand":"Philips","state":"IN_USE"}
        """;

        ResponseEntity<DeviceDto> updated = rest.exchange(
                baseUrl() + "/" + created.id(),
                HttpMethod.PUT,
                new HttpEntity<>(update, jsonHeaders()),
                DeviceDto.class
        );

        assertThat(updated.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updated.getBody()).isNotNull();
        assertThat(updated.getBody().state()).isEqualTo("IN_USE");
    }

    @Test
    void deleteDevice() {
        DeviceDto created = create("Camera", "Sony", "AVAILABLE");

        ResponseEntity<Void> del = rest.exchange(
                baseUrl() + "/" + created.id(), HttpMethod.DELETE, null, Void.class
        );
        assertThat(del.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> after = rest.getForEntity(baseUrl() + "/" + created.id(), String.class);
        assertThat(after.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private DeviceDto create(String name, String brand, String state) {
        String body = """
            {"name":"%s","brand":"%s","state":"%s"}
        """.formatted(name, brand, state);
        return rest.exchange(
                baseUrl(), HttpMethod.POST, new HttpEntity<>(body, jsonHeaders()), DeviceDto.class
        ).getBody();
    }
}

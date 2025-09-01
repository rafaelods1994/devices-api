package com.github.rafael.devices.it;

import com.github.rafael.devices.domain.DeviceState;
import com.github.rafael.devices.it.dto.DeviceDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

class DevicesApiParameterizedIT extends BaseApiTest {

    @ParameterizedTest
    @EnumSource(DeviceState.class)
    void createWithState(DeviceState state) {
        String body = """
            {"name":"ParamDevice","brand":"BrandX","state":"%s"}
        """.formatted(state.name());

        ResponseEntity<DeviceDto> resp = rest.exchange(
                baseUrl(), HttpMethod.POST, new HttpEntity<>(body, jsonHeaders()), DeviceDto.class
        );

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getBody().state()).isEqualTo(state.name());
    }
}

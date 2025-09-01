package com.github.rafael.devices.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rafael.devices.domain.DeviceState;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceResponseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void equalsHashCodeAndToString_shouldWork() {
        Instant now = Instant.now();
        DeviceResponse r1 = new DeviceResponse(1L, "Lamp", "Philips", DeviceState.AVAILABLE, now.toString());
        DeviceResponse r2 = new DeviceResponse(1L, "Lamp", "Philips", DeviceState.AVAILABLE, now.toString());

        assertThat(r1).isEqualTo(r2);
        assertThat(r1).hasSameHashCodeAs(r2);
        assertThat(r1.toString()).contains("Lamp", "Philips", "AVAILABLE");
    }

    @Test
    void jsonSerializationAndDeserialization_shouldWork() throws JsonProcessingException {
        Instant now = Instant.now();
        DeviceResponse resp = new DeviceResponse(1L, "Lamp", "Philips", DeviceState.AVAILABLE, now.toString());

        String json = mapper.writeValueAsString(resp);
        DeviceResponse fromJson = mapper.readValue(json, DeviceResponse.class);

        assertThat(fromJson).isEqualTo(resp);
    }
}

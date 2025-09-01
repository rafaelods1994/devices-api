package com.github.rafael.devices.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rafael.devices.domain.DeviceState;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceCreateRequestTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void equalsHashCodeAndToString_shouldWork() {
        DeviceCreateRequest req1 = new DeviceCreateRequest("Lamp", "Philips", DeviceState.AVAILABLE);
        DeviceCreateRequest req2 = new DeviceCreateRequest("Lamp", "Philips", DeviceState.AVAILABLE);

        assertThat(req1).isEqualTo(req2);
        assertThat(req1).hasSameHashCodeAs(req2);
        assertThat(req1.toString()).contains("Lamp", "Philips", "AVAILABLE");
    }

    @Test
    void jsonSerializationAndDeserialization_shouldWork() throws JsonProcessingException {
        DeviceCreateRequest req = new DeviceCreateRequest("Lamp", "Philips", DeviceState.AVAILABLE);

        String json = mapper.writeValueAsString(req);
        DeviceCreateRequest fromJson = mapper.readValue(json, DeviceCreateRequest.class);

        assertThat(fromJson).isEqualTo(req);
    }
}

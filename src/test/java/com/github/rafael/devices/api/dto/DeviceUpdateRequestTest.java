package com.github.rafael.devices.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rafael.devices.domain.DeviceState;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceUpdateRequestTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void equalsHashCodeAndToString_shouldWork() {
        DeviceUpdateRequest req1 = new DeviceUpdateRequest("Lamp", "Philips", DeviceState.AVAILABLE);
        DeviceUpdateRequest req2 = new DeviceUpdateRequest("Lamp", "Philips", DeviceState.AVAILABLE);

        assertThat(req1).isEqualTo(req2);
        assertThat(req1).hasSameHashCodeAs(req2);
        assertThat(req1.toString()).contains("Lamp", "Philips", "AVAILABLE");
    }

    @Test
    void jsonSerializationAndDeserialization_shouldWork() throws JsonProcessingException {
        DeviceUpdateRequest req = new DeviceUpdateRequest("Lamp", "Philips", DeviceState.AVAILABLE);

        String json = mapper.writeValueAsString(req);
        DeviceUpdateRequest fromJson = mapper.readValue(json, DeviceUpdateRequest.class);

        assertThat(fromJson).isEqualTo(req);
    }
}

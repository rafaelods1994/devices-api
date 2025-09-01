package com.github.rafael.devices.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rafael.devices.domain.DeviceState;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DevicePatchRequestTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void equalsHashCodeAndToString_shouldWork() {
        DevicePatchRequest req1 = new DevicePatchRequest("Lamp", null, DeviceState.AVAILABLE);
        DevicePatchRequest req2 = new DevicePatchRequest("Lamp", null, DeviceState.AVAILABLE);

        assertThat(req1).isEqualTo(req2);
        assertThat(req1).hasSameHashCodeAs(req2);
        assertThat(req1.toString()).contains("Lamp", "AVAILABLE");
    }

    @Test
    void jsonSerializationAndDeserialization_shouldWork() throws JsonProcessingException {
        DevicePatchRequest req = new DevicePatchRequest("Lamp", null, DeviceState.AVAILABLE);

        String json = mapper.writeValueAsString(req);
        DevicePatchRequest fromJson = mapper.readValue(json, DevicePatchRequest.class);

        assertThat(fromJson).isEqualTo(req);
    }
}

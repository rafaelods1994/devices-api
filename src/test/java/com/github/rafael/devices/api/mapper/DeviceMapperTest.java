package com.github.rafael.devices.api.mapper;

import com.github.rafael.devices.api.dto.DeviceResponse;
import com.github.rafael.devices.domain.Device;
import com.github.rafael.devices.domain.DeviceState;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeviceMapperTest {

    @Test
    void toResponse_shouldMapAllFields_whenCreatedAtIsNotNull() {
        OffsetDateTime now = OffsetDateTime.now();
        Device device = new Device();
        device.setId(1L);
        device.setName("Lamp");
        device.setBrand("Philips");
        device.setState(DeviceState.AVAILABLE);
        device.setCreatedAt(now);

        DeviceResponse resp = DeviceMapper.toResponse(device);

        assertThat(resp.id()).isEqualTo(1L);
        assertThat(resp.name()).isEqualTo("Lamp");
        assertThat(resp.brand()).isEqualTo("Philips");
        assertThat(resp.state()).isEqualTo(DeviceState.AVAILABLE);
        assertThat(resp.createdAt()).isEqualTo(now.toString());
    }

    @Test
    void toResponse_shouldMapCreatedAtAsNull_whenCreatedAtIsNull() {
        Device device = new Device();
        device.setId(2L);
        device.setName("Fan");
        device.setBrand("LG");
        device.setState(DeviceState.IN_USE);
        device.setCreatedAt(null);

        DeviceResponse resp = DeviceMapper.toResponse(device);

        assertThat(resp.id()).isEqualTo(2L);
        assertThat(resp.name()).isEqualTo("Fan");
        assertThat(resp.brand()).isEqualTo("LG");
        assertThat(resp.state()).isEqualTo(DeviceState.IN_USE);
        assertThat(resp.createdAt()).isNull();
    }

    @Test
    void toResponse_shouldThrowNullPointerException_whenDeviceIsNull() {
        assertThatThrownBy(() -> DeviceMapper.toResponse(null))
                .isInstanceOf(NullPointerException.class);
    }
}

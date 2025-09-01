package com.github.rafael.devices.domain;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceTest {

    @Test
    void gettersAndSetters_shouldWork() {
        Device device = new Device();
        OffsetDateTime now = OffsetDateTime.now();

        device.setId(1L);
        device.setName("Lamp");
        device.setBrand("Philips");
        device.setState(DeviceState.IN_USE);
        device.setCreatedAt(now);

        assertThat(device.getId()).isEqualTo(1L);
        assertThat(device.getName()).isEqualTo("Lamp");
        assertThat(device.getBrand()).isEqualTo("Philips");
        assertThat(device.getState()).isEqualTo(DeviceState.IN_USE);
        assertThat(device.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void prePersist_shouldSetCreatedAtWhenNull() {
        Device device = new Device();
        device.setName("Lamp");
        device.setBrand("Philips");
        device.setState(DeviceState.AVAILABLE);

        assertThat(device.getCreatedAt()).isNull();

        device.prePersist();

        assertThat(device.getCreatedAt()).isNotNull();
    }

    @Test
    void prePersist_shouldNotOverrideExistingCreatedAt() {
        Device device = new Device();
        OffsetDateTime existing = OffsetDateTime.now().minusDays(1);
        device.setCreatedAt(existing);

        device.prePersist();

        assertThat(device.getCreatedAt()).isEqualTo(existing);
    }
}

package com.github.rafael.devices.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceStateTest {

    @Test
    void values_shouldContainAllStates() {
        assertThat(DeviceState.values())
                .containsExactly(DeviceState.AVAILABLE, DeviceState.IN_USE, DeviceState.INACTIVE);
    }

    @Test
    void valueOf_shouldReturnCorrectEnum() {
        assertThat(DeviceState.valueOf("AVAILABLE")).isEqualTo(DeviceState.AVAILABLE);
        assertThat(DeviceState.valueOf("IN_USE")).isEqualTo(DeviceState.IN_USE);
        assertThat(DeviceState.valueOf("INACTIVE")).isEqualTo(DeviceState.INACTIVE);
    }
}

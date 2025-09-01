package com.github.rafael.devices.api.dto;

import com.github.rafael.devices.domain.DeviceState;

public record DevicePatchRequest(
        String name,
        String brand,
        DeviceState state
) {
}

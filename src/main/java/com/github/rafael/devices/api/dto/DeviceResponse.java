package com.github.rafael.devices.api.dto;

import com.github.rafael.devices.domain.DeviceState;

public record DeviceResponse(
        Long id,
        String name,
        String brand,
        DeviceState state,
        String createdAt
) {
}
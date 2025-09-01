package com.github.rafael.devices.api.dto;

import com.github.rafael.devices.domain.DeviceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceCreateRequest(
        @NotBlank String name,
        @NotBlank String brand,
        @NotNull DeviceState state
) {
}
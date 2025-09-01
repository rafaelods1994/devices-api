package com.github.rafael.devices.it.dto;

import java.time.OffsetDateTime;

public record DeviceDto(
        Long id,
        String name,
        String brand,
        String state,
        OffsetDateTime createdAt
) {}

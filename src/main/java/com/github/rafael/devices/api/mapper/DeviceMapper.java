package com.github.rafael.devices.api.mapper;

import com.github.rafael.devices.api.dto.*;
import com.github.rafael.devices.domain.Device;

public class DeviceMapper {
    public static DeviceResponse toResponse(Device d) {
        return new DeviceResponse(
                d.getId(), d.getName(), d.getBrand(), d.getState(),
                d.getCreatedAt() != null ? d.getCreatedAt().toString() : null
        );
    }
}

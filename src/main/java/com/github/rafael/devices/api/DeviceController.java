package com.github.rafael.devices.api;

import com.github.rafael.devices.api.dto.*;
import com.github.rafael.devices.api.mapper.DeviceMapper;
import com.github.rafael.devices.domain.Device;
import com.github.rafael.devices.domain.DeviceState;
import com.github.rafael.devices.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse create(@RequestBody @Valid DeviceCreateRequest req) {
        Device d = service.create(req);
        return DeviceMapper.toResponse(d);
    }

    @GetMapping("/{id}")
    public DeviceResponse get(@PathVariable Long id) {
        return DeviceMapper.toResponse(service.get(id));
    }

    @GetMapping
    public Page<DeviceResponse> list(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) DeviceState state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(brand, state, pageable).map(DeviceMapper::toResponse);
    }

    @PutMapping("/{id}")
    public DeviceResponse update(@PathVariable Long id, @RequestBody @Valid DeviceUpdateRequest req) {
        return DeviceMapper.toResponse(service.update(id, req));
    }

    @PatchMapping("/{id}")
    public DeviceResponse patch(@PathVariable Long id, @RequestBody DevicePatchRequest req) {
        return DeviceMapper.toResponse(service.patch(id, req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

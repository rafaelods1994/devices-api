package com.github.rafael.devices.service;

import com.github.rafael.devices.api.dto.*;
import com.github.rafael.devices.domain.Device;
import com.github.rafael.devices.domain.DeviceState;
import com.github.rafael.devices.repo.DeviceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceService {
    private final DeviceRepository repo;

    public DeviceService(DeviceRepository repo) {
        this.repo = repo;
    }

    public Device create(DeviceCreateRequest req) {
        Device d = new Device();
        d.setName(req.name());
        d.setBrand(req.brand());
        d.setState(req.state());
        return repo.save(d);
    }

    @Transactional(readOnly = true)
    public Device get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Device not found: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Device> list(String brand, DeviceState state, Pageable pageable) {
        if (brand != null && !brand.isBlank()) return repo.findByBrandIgnoreCase(brand, pageable);
        if (state != null) return repo.findByState(state, pageable);
        return repo.findAll(pageable);
    }

    public Device update(Long id, DeviceUpdateRequest req) {
        Device d = get(id);
        // Rule: creation time cannot be updated (already enforced by entity)
        // Rule: name/brand cannot be updated if the device is IN_USE
        if (d.getState() == DeviceState.IN_USE &&
                (!d.getName().equals(req.name()) || !d.getBrand().equals(req.brand()))) {
            throw new IllegalStateException("Cannot change name/brand while device is IN_USE");
        }
        d.setName(req.name());
        d.setBrand(req.brand());
        d.setState(req.state());
        return d;
    }

    public Device patch(Long id, DevicePatchRequest req) {
        Device d = get(id);
        if (req.name() != null || req.brand() != null) {
            if (d.getState() == DeviceState.IN_USE) {
                throw new IllegalStateException("Cannot change name/brand while device is IN_USE");
            }
        }
        if (req.name() != null) d.setName(req.name());
        if (req.brand() != null) d.setBrand(req.brand());
        if (req.state() != null) d.setState(req.state());
        return d;
    }

    public void delete(Long id) {
        Device d = get(id);
        // Rule: IN_USE devices cannot be deleted
        if (d.getState() == DeviceState.IN_USE) {
            throw new IllegalStateException("Cannot delete device while IN_USE");
        }
        repo.delete(d);
    }
}

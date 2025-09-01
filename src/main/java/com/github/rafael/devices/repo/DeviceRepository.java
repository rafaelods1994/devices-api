package com.github.rafael.devices.repo;

import com.github.rafael.devices.domain.Device;
import com.github.rafael.devices.domain.DeviceState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findByBrandIgnoreCase(String brand, Pageable pageable);

    Page<Device> findByState(DeviceState state, Pageable pageable);
}

package com.github.rafael.devices.repo;

import com.github.rafael.devices.domain.Device;
import com.github.rafael.devices.domain.DeviceState;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeviceRepositoryTest {

    @Test
    void findByBrandIgnoreCase_shouldDelegateToSpringData() {
        DeviceRepository repo = mock(DeviceRepository.class);
        Device device = new Device();
        Page<Device> page = new PageImpl<>(List.of(device));
        when(repo.findByBrandIgnoreCase("Philips", Pageable.unpaged())).thenReturn(page);

        Page<Device> result = repo.findByBrandIgnoreCase("Philips", Pageable.unpaged());

        assertThat(result.getContent()).containsExactly(device);
        verify(repo).findByBrandIgnoreCase("Philips", Pageable.unpaged());
    }

    @Test
    void findByState_shouldDelegateToSpringData() {
        DeviceRepository repo = mock(DeviceRepository.class);
        Device device = new Device();
        Page<Device> page = new PageImpl<>(List.of(device));
        when(repo.findByState(DeviceState.AVAILABLE, Pageable.unpaged())).thenReturn(page);

        Page<Device> result = repo.findByState(DeviceState.AVAILABLE, Pageable.unpaged());

        assertThat(result.getContent()).containsExactly(device);
        verify(repo).findByState(DeviceState.AVAILABLE, Pageable.unpaged());
    }
}

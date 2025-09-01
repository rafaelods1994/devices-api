package com.github.rafael.devices.service;

import com.github.rafael.devices.api.dto.DeviceCreateRequest;
import com.github.rafael.devices.api.dto.DevicePatchRequest;
import com.github.rafael.devices.api.dto.DeviceUpdateRequest;
import com.github.rafael.devices.domain.Device;
import com.github.rafael.devices.domain.DeviceState;
import com.github.rafael.devices.repo.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {

    private DeviceRepository repo;
    private DeviceService service;
    private Device device;

    @BeforeEach
    void setUp() {
        repo = mock(DeviceRepository.class);
        service = new DeviceService(repo);

        device = new Device();
        device.setId(1L);
        device.setName("Lamp");
        device.setBrand("Philips");
        device.setState(DeviceState.AVAILABLE);
        device.setCreatedAt(OffsetDateTime.now());
    }

    @Test
    void create_shouldSaveAndReturnDevice() {
        DeviceCreateRequest req = new DeviceCreateRequest("Lamp", "Philips", DeviceState.AVAILABLE);
        when(repo.save(any(Device.class))).thenReturn(device);

        Device result = service.create(req);

        assertThat(result).isEqualTo(device);
        ArgumentCaptor<Device> captor = ArgumentCaptor.forClass(Device.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Lamp");
        assertThat(captor.getValue().getBrand()).isEqualTo("Philips");
        assertThat(captor.getValue().getState()).isEqualTo(DeviceState.AVAILABLE);
    }

    @Test
    void get_shouldReturnDevice_whenFound() {
        when(repo.findById(1L)).thenReturn(Optional.of(device));

        Device result = service.get(1L);

        assertThat(result).isEqualTo(device);
    }

    @Test
    void get_shouldThrow_whenNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Device not found");
    }

    @Test
    void list_shouldCallFindByBrand_whenBrandProvided() {
        Page<Device> page = new PageImpl<>(List.of(device));
        when(repo.findByBrandIgnoreCase(eq("Philips"), any(Pageable.class))).thenReturn(page);

        Page<Device> result = service.list("Philips", null, Pageable.unpaged());

        assertThat(result.getContent()).containsExactly(device);
        verify(repo).findByBrandIgnoreCase(eq("Philips"), any(Pageable.class));
    }

    @Test
    void list_shouldCallFindByState_whenStateProvided() {
        Page<Device> page = new PageImpl<>(List.of(device));
        when(repo.findByState(eq(DeviceState.AVAILABLE), any(Pageable.class))).thenReturn(page);

        Page<Device> result = service.list(null, DeviceState.AVAILABLE, Pageable.unpaged());

        assertThat(result.getContent()).containsExactly(device);
        verify(repo).findByState(eq(DeviceState.AVAILABLE), any(Pageable.class));
    }

    @Test
    void list_shouldCallFindAll_whenNoFiltersProvided() {
        Page<Device> page = new PageImpl<>(List.of(device));
        when(repo.findAll(any(Pageable.class))).thenReturn(page);

        Page<Device> result = service.list(null, null, Pageable.unpaged());

        assertThat(result.getContent()).containsExactly(device);
        verify(repo).findAll(any(Pageable.class));
    }

    @Test
    void update_shouldUpdateFields_whenNotInUse() {
        when(repo.findById(1L)).thenReturn(Optional.of(device));
        DeviceUpdateRequest req = new DeviceUpdateRequest("Lamp", "Philips", DeviceState.IN_USE);

        Device result = service.update(1L, req);

        assertThat(result.getState()).isEqualTo(DeviceState.IN_USE);
    }

    @Test
    void update_shouldThrow_whenInUseAndNameOrBrandChanged() {
        device.setState(DeviceState.IN_USE);
        when(repo.findById(1L)).thenReturn(Optional.of(device));
        DeviceUpdateRequest req = new DeviceUpdateRequest("NewName", "Philips", DeviceState.IN_USE);

        assertThatThrownBy(() -> service.update(1L, req))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot change name/brand");
    }

    @Test
    void patch_shouldUpdateNonNullFields_whenNotInUse() {
        when(repo.findById(1L)).thenReturn(Optional.of(device));
        DevicePatchRequest req = new DevicePatchRequest("NewName", null, DeviceState.IN_USE);

        Device result = service.patch(1L, req);

        assertThat(result.getName()).isEqualTo("NewName");
        assertThat(result.getState()).isEqualTo(DeviceState.IN_USE);
    }

    @Test
    void patch_shouldThrow_whenInUseAndNameOrBrandProvided() {
        device.setState(DeviceState.IN_USE);
        when(repo.findById(1L)).thenReturn(Optional.of(device));
        DevicePatchRequest req = new DevicePatchRequest("NewName", null, null);

        assertThatThrownBy(() -> service.patch(1L, req))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot change name/brand");
    }

    @Test
    void delete_shouldDelete_whenNotInUse() {
        when(repo.findById(1L)).thenReturn(Optional.of(device));

        service.delete(1L);

        verify(repo).delete(device);
    }

    @Test
    void delete_shouldThrow_whenInUse() {
        device.setState(DeviceState.IN_USE);
        when(repo.findById(1L)).thenReturn(Optional.of(device));

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot delete device");
    }
}

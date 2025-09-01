package com.github.rafael.devices.api;

import com.github.rafael.devices.api.dto.*;
import com.github.rafael.devices.domain.Device;
import com.github.rafael.devices.domain.DeviceState;
import com.github.rafael.devices.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DeviceControllerTest {

    private DeviceService service;
    private DeviceController controller;

    private Device device;

    @BeforeEach
    void setUp() {
        service = mock(DeviceService.class);
        controller = new DeviceController(service);

        device = new Device();
        device.setId(1L);
        device.setName("Lamp");
        device.setBrand("Philips");
        device.setState(DeviceState.AVAILABLE);
        device.setCreatedAt(OffsetDateTime.now());
    }

    @Test
    void create_shouldCallServiceAndReturnMappedResponse() {
        DeviceCreateRequest req = new DeviceCreateRequest("Lamp", "Philips", DeviceState.AVAILABLE);
        when(service.create(req)).thenReturn(device);

        DeviceResponse resp = controller.create(req);

        assertThat(resp.id()).isEqualTo(device.getId());
        assertThat(resp.name()).isEqualTo(device.getName());
        assertThat(resp.brand()).isEqualTo(device.getBrand());
        assertThat(resp.state()).isEqualTo(device.getState());
        assertThat(resp.createdAt()).isEqualTo(device.getCreatedAt().toString());

        verify(service).create(req);
    }

    @Test
    void get_shouldReturnMappedResponse() {
        when(service.get(1L)).thenReturn(device);

        DeviceResponse resp = controller.get(1L);

        assertThat(resp.id()).isEqualTo(1L);
        verify(service).get(1L);
    }

    @Test
    void list_shouldPassParamsAndReturnMappedPage() {
        Page<Device> page = new PageImpl<>(List.of(device));
        when(service.list(eq("Philips"), eq(DeviceState.AVAILABLE), any(Pageable.class)))
                .thenReturn(page);

        Page<DeviceResponse> result = controller.list("Philips", DeviceState.AVAILABLE, 0, 20);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).id()).isEqualTo(1L);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).list(eq("Philips"), eq(DeviceState.AVAILABLE), pageableCaptor.capture());
        assertThat(pageableCaptor.getValue().getPageNumber()).isEqualTo(0);
        assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(20);
    }

    @Test
    void update_shouldCallServiceAndReturnMappedResponse() {
        DeviceUpdateRequest req = new DeviceUpdateRequest("Lamp", "Philips", DeviceState.IN_USE);
        when(service.update(1L, req)).thenReturn(device);

        DeviceResponse resp = controller.update(1L, req);

        assertThat(resp.id()).isEqualTo(1L);
        verify(service).update(1L, req);
    }

    @Test
    void patch_shouldCallServiceAndReturnMappedResponse() {
        DevicePatchRequest req = new DevicePatchRequest(null, "LG", null);
        when(service.patch(1L, req)).thenReturn(device);

        DeviceResponse resp = controller.patch(1L, req);

        assertThat(resp.id()).isEqualTo(1L);
        verify(service).patch(1L, req);
    }

    @Test
    void delete_shouldCallServiceDelete() {
        controller.delete(1L);
        verify(service).delete(1L);
    }
}

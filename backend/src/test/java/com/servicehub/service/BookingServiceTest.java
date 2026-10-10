package com.servicehub.service;

import com.servicehub.dto.CreateBookingRequest;
import com.servicehub.entity.Booking;
import com.servicehub.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    private BookingRepository repository;
    private BookingService service;

    @BeforeEach
    void setUp() {
        repository = mock(BookingRepository.class);
        service = new BookingService(repository);
    }

    @Test
    void shouldCreateBookingWhenSlotIsAvailable() {
        CreateBookingRequest request = request("10:00", "11:00");

        Booking expected = new Booking(
                1L, "Aadesh Kumar", "Plumbing", "Raj Services",
                LocalDate.of(2026, 10, 10),
                LocalTime.of(10, 0), LocalTime.of(11, 0), "PENDING"
        );

        when(repository.hasOverlap(anyString(), any(), any(), any())).thenReturn(false);
        when(repository.save(anyString(), anyString(), anyString(), any(), any(), any()))
                .thenReturn(expected);

        Booking result = service.createBooking(request);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("PENDING", result.status());
        verify(repository).save(
                "Aadesh Kumar", "Plumbing", "Raj Services",
                LocalDate.of(2026, 10, 10),
                LocalTime.of(10, 0), LocalTime.of(11, 0)
        );
    }

    @Test
    void shouldRejectOverlappingBooking() {
        when(repository.hasOverlap(anyString(), any(), any(), any())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createBooking(request("10:30", "11:30"))
        );

        assertEquals(
                "The selected provider is already booked for this time.",
                exception.getMessage()
        );

        verify(repository, never()).save(anyString(), anyString(), anyString(), any(), any(), any());
    }

    @Test
    void shouldRejectInvalidTimeRange() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createBooking(request("12:00", "11:00"))
        );

        assertEquals("End time must be after start time.", exception.getMessage());
        verifyNoInteractions(repository);
    }

    private CreateBookingRequest request(String start, String end) {
        CreateBookingRequest request = new CreateBookingRequest();
        request.setCustomerName("Aadesh Kumar");
        request.setServiceName("Plumbing");
        request.setProviderName("Raj Services");
        request.setBookingDate(LocalDate.of(2026, 10, 10));
        request.setStartTime(LocalTime.parse(start));
        request.setEndTime(LocalTime.parse(end));
        return request;
    }
}

package com.servicehub.controller;

import com.servicehub.entity.Booking;
import com.servicehub.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    void shouldCreateBookingThroughApi() throws Exception {
        Booking booking = new Booking(
                1L, "Aadesh Kumar", "Plumbing", "Raj Services",
                LocalDate.of(2026, 10, 10),
                LocalTime.of(10, 0), LocalTime.of(11, 0), "PENDING"
        );

        when(bookingService.createBooking(any())).thenReturn(booking);

        String json = """
                {
                  "customerName": "Aadesh Kumar",
                  "serviceName": "Plumbing",
                  "providerName": "Raj Services",
                  "bookingDate": "2026-10-10",
                  "startTime": "10:00",
                  "endTime": "11:00"
                }
                """;

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldRejectOverlappingBookingThroughApi() throws Exception {
        when(bookingService.createBooking(any()))
                .thenThrow(new IllegalArgumentException(
                        "The selected provider is already booked for this time."));

        String json = """
                {
                  "customerName": "Aadesh Kumar",
                  "serviceName": "Plumbing",
                  "providerName": "Raj Services",
                  "bookingDate": "2026-10-10",
                  "startTime": "10:30",
                  "endTime": "11:30"
                }
                """;

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("The selected provider is already booked for this time."));
    }

    @Test
    void shouldListBookings() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(List.of());

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldRejectMissingRequiredFields() throws Exception {
        String json = """
                {
                  "customerName": "",
                  "serviceName": "",
                  "providerName": ""
                }
                """;

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}

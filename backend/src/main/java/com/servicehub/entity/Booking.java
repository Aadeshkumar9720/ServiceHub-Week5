package com.servicehub.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public record Booking(
        Long id,
        String customerName,
        String serviceName,
        String providerName,
        LocalDate bookingDate,
        LocalTime startTime,
        LocalTime endTime,
        String status
) {
}

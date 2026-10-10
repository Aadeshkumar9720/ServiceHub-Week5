package com.servicehub.repository;

import com.servicehub.entity.Booking;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookingRepository {

    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public synchronized Booking save(
            String customerName,
            String serviceName,
            String providerName,
            LocalDate bookingDate,
            LocalTime startTime,
            LocalTime endTime) {

        Booking booking = new Booking(
                sequence.getAndIncrement(),
                customerName,
                serviceName,
                providerName,
                bookingDate,
                startTime,
                endTime,
                "PENDING"
        );

        bookings.add(booking);
        return booking;
    }

    public synchronized List<Booking> findAll() {
        return List.copyOf(bookings);
    }

    public synchronized boolean hasOverlap(
            String providerName,
            LocalDate bookingDate,
            LocalTime startTime,
            LocalTime endTime) {

        return bookings.stream()
                .filter(existing -> existing.providerName().equalsIgnoreCase(providerName))
                .filter(existing -> existing.bookingDate().equals(bookingDate))
                .anyMatch(existing ->
                        startTime.isBefore(existing.endTime())
                                && endTime.isAfter(existing.startTime()));
    }
}

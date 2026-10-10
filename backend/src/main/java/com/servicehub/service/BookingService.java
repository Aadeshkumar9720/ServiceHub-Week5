package com.servicehub.service;

import com.servicehub.dto.CreateBookingRequest;
import com.servicehub.entity.Booking;
import com.servicehub.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Creates a booking after validating its time range and provider availability.
     */
    public Booking createBooking(CreateBookingRequest request) {
        LocalTime start = request.getStartTime();
        LocalTime end = request.getEndTime();

        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        if (bookingRepository.hasOverlap(
                request.getProviderName(),
                request.getBookingDate(),
                start,
                end)) {
            throw new IllegalArgumentException(
                    "The selected provider is already booked for this time.");
        }

        return bookingRepository.save(
                request.getCustomerName(),
                request.getServiceName(),
                request.getProviderName(),
                request.getBookingDate(),
                start,
                end
        );
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}

package com.servicehub.controller;

import com.servicehub.dto.CreateBookingRequest;
import com.servicehub.entity.Booking;
import com.servicehub.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@Valid @RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping
    public List<Booking> getBookings() {
        return bookingService.getAllBookings();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBookingError(IllegalArgumentException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    public record ErrorResponse(String message) {}
}

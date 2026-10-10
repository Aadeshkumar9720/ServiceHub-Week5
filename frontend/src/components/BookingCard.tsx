import type { Booking } from "../types/Booking";

interface Props {
  booking: Booking;
}

export default function BookingCard({ booking }: Props) {
  return (
    <article data-testid="booking-card">
      <h2>{booking.serviceName}</h2>
      <p>Customer: {booking.customerName}</p>
      <p>Provider: {booking.providerName}</p>
      <p>{booking.bookingDate} · {booking.startTime}–{booking.endTime}</p>
      <strong>Status: {booking.status}</strong>
    </article>
  );
}

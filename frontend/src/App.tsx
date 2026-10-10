import { useEffect, useState } from "react";
import BookingCard from "./components/BookingCard";
import type { Booking } from "./types/Booking";

export default function App() {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("/api/bookings")
      .then(async (response) => {
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return response.json();
      })
      .then(setBookings)
      .catch((err: Error) => setError(err.message));
  }, []);

  return (
    <main style={{ maxWidth: 800, margin: "40px auto", fontFamily: "Arial" }}>
      <h1>ServiceHub – Week 5</h1>
      <p>Testing Strategy & Quality Assurance Implementation</p>
      {error && <p role="alert">Unable to load bookings: {error}</p>}
      {bookings.length === 0 && !error ? (
        <p>No bookings yet. Create one through the API.</p>
      ) : (
        bookings.map((booking) => <BookingCard key={booking.id} booking={booking} />)
      )}
    </main>
  );
}

import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import BookingCard from "./BookingCard";
import type { Booking } from "../types/Booking";

describe("BookingCard", () => {
  it("displays booking information and status", () => {
    const booking: Booking = {
      id: 1,
      customerName: "Aadesh Kumar",
      serviceName: "Plumbing",
      providerName: "Raj Services",
      bookingDate: "2026-10-10",
      startTime: "10:00",
      endTime: "11:00",
      status: "PENDING"
    };

    render(<BookingCard booking={booking} />);

    expect(screen.getByRole("heading", { name: "Plumbing" })).toBeInTheDocument();
    expect(screen.getByText("Customer: Aadesh Kumar")).toBeInTheDocument();
    expect(screen.getByText("Provider: Raj Services")).toBeInTheDocument();
    expect(screen.getByText("Status: PENDING")).toBeInTheDocument();
  });
});

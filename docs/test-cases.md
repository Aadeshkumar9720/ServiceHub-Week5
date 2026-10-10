# ServiceHub Week 5 – Executable Test Cases

| ID | Scenario | Expected |
|---|---|---|
| TC-BOOK-001 | Create valid booking | Booking is created with PENDING status |
| TC-BOOK-002 | Create overlapping booking | Request is rejected with HTTP 400 |
| TC-BOOK-003 | End time before start time | Request is rejected with HTTP 400 |
| TC-BOOK-004 | List bookings | HTTP 200 and JSON array returned |
| TC-BOOK-005 | Missing required fields | HTTP 400 |
| TC-UI-001 | Booking card displays details | Booking details and status are visible |

## Testing layers

### Unit tests
`BookingServiceTest` isolates business logic with Mockito.

### Integration/API tests
`BookingControllerTest` uses MockMvc to verify the REST HTTP contract.

### Frontend component test
`BookingCard.test.tsx` verifies that booking information is rendered correctly.

## Run

Backend:
```powershell
cd backend
mvn clean test
```

Frontend:
```powershell
cd frontend
npm install
npm run test
```

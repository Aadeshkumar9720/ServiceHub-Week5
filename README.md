# ServiceHub – Week 5 Testing Implementation

This Week 5 implementation adds executable automated tests to the ServiceHub booking module.

## Included
- Spring Boot backend on port 8081
- Booking REST API
- Booking service with validation and overlap detection
- JUnit 5 + Mockito unit tests
- Spring Boot MockMvc controller/API tests
- React + TypeScript frontend
- Vitest + React Testing Library component test
- Test case documentation

## Backend
```powershell
cd backend
mvn clean test
mvn spring-boot:run
```

Backend: http://localhost:8081

## Frontend
```powershell
cd frontend
npm install
npm run test
npm run dev
```

Frontend: http://localhost:5174

The Vite development server proxies `/api` to the backend at port 8081.

## API examples

Create booking:
```powershell
$body = @{
    customerName = "Aadesh Kumar"
    serviceName = "Plumbing"
    providerName = "Raj Services"
    bookingDate = "2026-10-10"
    startTime = "10:00"
    endTime = "11:00"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/api/bookings" `
  -Method Post -ContentType "application/json" -Body $body
```

List bookings:
```powershell
Invoke-RestMethod -Uri "http://localhost:8081/api/bookings"
```

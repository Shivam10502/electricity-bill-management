# Electricity Bill Management System

A web-based system for managing electricity bills, with user authentication, bill payments, and complaint management.

## Project Structure

```
├── Frontend
│   ├── index.html          # Main landing page
│   ├── login.html          # User login
│   ├── register.html       # New user registration
│   ├── payBill.html        # Bill payment
│   ├── viewBills.html      # View bill history
│   ├── viewUsage.html      # Usage statistics
│   ├── complaintStatus.html # Check complaint status
│   └── registerComplaint.html # Submit new complaint
│
├── Backend (Spring Boot)
│   └── backend/
│       ├── pom.xml         # Maven configuration
│       └── src/
│           └── main/
│               ├── java/com/example/electricity/
│               │   ├── controller/    # REST APIs
│               │   ├── model/         # Data models
│               │   └── service/       # Business logic
│               └── resources/
│                   └── application.properties
```

## Features

- User Authentication (Login/Register)
- Bill Management
  - View Bills
  - Pay Bills
  - Usage History
- Complaint System
  - Register Complaints
  - Track Complaint Status
- Responsive UI
- RESTful Backend APIs
- Token-based Authentication

## Tech Stack

- Frontend: HTML, CSS, JavaScript
- Backend: Java Spring Boot
- Build Tool: Maven
- Database: In-memory (demo mode)

## Setup & Running

1. Start Backend:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

2. Open Frontend:
   - Open `index.html` in a web browser
   - Or serve using a local HTTP server

## API Endpoints

- Authentication:
  - POST `/api/auth/login`
  - POST `/api/auth/register`
  - GET `/api/auth/status`
  
- Bills:
  - GET `/api/payments/bills`
  - POST `/api/payments/pay`
  
- Complaints:
  - POST `/api/complaints/register`
  - GET `/api/complaints/my`
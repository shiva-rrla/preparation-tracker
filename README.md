# Preparation Tracker

A production-grade full-stack application for tracking learning goals, certifications, interview preparation, courses, books, and study progress.

## Tech Stack

### Backend
- **Java 21** with **Spring Boot 3.4**
- **Spring Security 6** + **JWT Authentication**
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL** database
- **Flyway** database migrations
- **MapStruct** for DTO mapping
- **OpenAPI 3 / Swagger UI**
- **JUnit 5**, **Mockito**, **Testcontainers**

### Frontend
- **React 18** + **TypeScript 5**
- **Material UI (MUI) v5**
- **Redux Toolkit** for state management
- **React Router v6**
- **Chart.js** for analytics
- **Axios** for API calls

### Infrastructure
- **Docker** + **Docker Compose**
- **Kubernetes** manifests included

## Features

1. **User Management** — Registration, login, JWT auth, profile, change password, role-based access (ADMIN/USER)
2. **Preparation Categories** — Predefined + custom categories for organizing resources
3. **Resource Management** — Track Udemy, YouTube, docs, books, blogs, GitHub repos, certifications, practice platforms
4. **Preparation Plans** — Set goals with target dates, priorities, status tracking
5. **Study Sessions** — Log daily study activities with duration and topics
6. **Learning Roadmaps** — Structured learning paths with completion tracking
7. **Certification Tracking** — Exam dates, costs, progress, pass/fail status
8. **Interview Preparation** — Topic-based question tracking with revision counts
9. **Notes Management** — Markdown support, tags, search
10. **Revision Planner** — Spaced repetition with reminder dates
11. **Daily Dashboard** — Today's plan, pending tasks, streaks, progress
12. **Analytics** — Charts for hours studied, topic progress, trends
13. **Goals Module** — SMART goals with deadlines and completion %
14. **Reminder System** — In-app notifications for upcoming exams and deadlines
15. **Global Search** — Cross-resource search
16. **Audit Trail** — Created/modified timestamps on all entities

## Quick Start

### Prerequisites
- Java 21
- Maven 3.9+
- Node.js 20+
- Docker & Docker Compose (optional)

### Local Development

1. **Clone and start PostgreSQL**
```bash
cd preparation-tracker
docker-compose up -d postgres
```

2. **Run Backend**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
API will be at `http://localhost:8080`
Swagger UI at `http://localhost:8080/swagger-ui.html`

3. **Run Frontend**
```bash
cd frontend
npm install
npm start
```
App will be at `http://localhost:3000`

### Docker Compose (Full Stack)
```bash
docker-compose up --build
```
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- pgAdmin: http://localhost:5050
- Mailpit: http://localhost:8025

### Kubernetes Deployment
```bash
kubectl apply -f k8s/
```

## Testing

### Backend
```bash
cd backend
mvn test                           # Unit tests
mvn verify -P integration-test    # Integration tests with Testcontainers
```

### Frontend
```bash
cd frontend
npm test
npm run build
```

## API Documentation

Swagger UI is available at `/swagger-ui.html` when the application is running.
All endpoints are prefixed with `/api/v1`.

### Authentication
- `POST /api/v1/auth/register` — Register new user
- `POST /api/v1/auth/login` — Login and get JWT
- `POST /api/v1/auth/refresh` — Refresh access token
- `GET /api/v1/auth/me` — Get current user
- `POST /api/v1/auth/change-password` — Change password

### Protected Endpoints
- `/api/v1/categories`, `/api/v1/resources`, `/api/v1/preparation-plans`
- `/api/v1/study-sessions`, `/api/v1/certifications`, `/api/v1/goals`
- `/api/v1/roadmaps`, `/api/v1/notes`, `/api/v1/revisions`
- `/api/v1/notifications`, `/api/v1/interview-topics`, `/api/v1/dashboard`
- `/api/v1/users`

All list endpoints support pagination: `?page=0&size=20&sort=createdAt,desc`

## Database Schema

Tables:
- `users`, `roles`, `user_roles`
- `categories`, `resources`
- `preparation_plans`, `study_sessions`
- `certifications`, `goals`
- `roadmaps`, `roadmap_items`
- `notes`, `revisions`, `notifications`
- `interview_topics`

Migrations are managed by Flyway in `backend/src/main/resources/db/migration/`.

## Architecture

- **Hexagonal Architecture** with clear separation of concerns
- Domain entities in `domain/entity/`
- DTOs in `dto/request/` and `dto/response/`
- Mappers in `mapper/` (MapStruct)
- Repositories in `repository/` (Spring Data JPA)
- Services in `service/` (business logic)
- Controllers in `controller/` (REST API)
- Security in `security/` (JWT, filters, config)
- Global exception handling in `exception/`

## Project Structure

```
preparation-tracker/
├── backend/
│   ├── src/main/java/com/prept/tracker/
│   │   ├── PreparationTrackerApplication.java
│   │   ├── config/          # OpenAPI, CORS, JPA auditing
│   │   ├── controller/      # REST controllers
│   │   ├── domain/
│   │   │   ├── entity/      # JPA entities
│   │   │   └── enums/       # Enums
│   │   ├── dto/
│   │   │   ├── common/      # Pagination DTOs
│   │   │   ├── request/     # Request DTOs
│   │   │   └── response/    # Response DTOs
│   │   ├── exception/       # Global exception handler
│   │   ├── mapper/          # MapStruct mappers
│   │   ├── repository/      # Spring Data JPA repos
│   │   ├── security/
│   │   │   ├── config/      # SecurityConfig
│   │   │   ├── jwt/         # JwtTokenProvider, JwtAuthenticationFilter
│   │   │   └── service/     # AuthenticationService, CustomUserDetailsService
│   │   └── service/         # Business services
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── db/migration/    # Flyway migrations
│   ├── src/test/java/       # Unit & integration tests
│   ├── pom.xml
│   └── Dockerfile
├── frontend/
│   ├── src/
│   │   ├── App.tsx
│   │   ├── index.tsx
│   │   ├── components/      # Layout, Sidebar, DataTable, etc.
│   │   ├── pages/           # All page components
│   │   ├── services/        # API clients
│   │   ├── store/           # Redux slices
│   │   ├── themes/          # MUI theme
│   │   ├── types/           # TypeScript interfaces
│   │   └── utils/           # Date helpers, constants
│   ├── public/
│   ├── package.json
│   └── Dockerfile
├── docker-compose.yml
└── k8s/                     # Kubernetes manifests
```

## CI/CD Ready

GitHub Actions workflow provided in `.github/workflows/ci.yml`:
- Builds backend with Maven
- Runs unit and integration tests
- Builds frontend with npm
- Validates TypeScript compilation
- Builds Docker images

## dummy changes

## License

MIT

## Checking

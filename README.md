# WorkNest — Employee Management System (Backend API)

Production-ready REST API built with Spring Boot 3, Java 17, and PostgreSQL for WorkNest EMS.

[![Deployed on Render](https://img.shields.io/badge/Deployed%20with-Render-46E3B7?logo=render)](https://render.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-6DB33F?logo=springboot)](https://spring.io)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791?logo=postgresql)](https://www.postgresql.org)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)](https://www.docker.com)

## 🏛️ Architecture & Features

- **Spring Boot 3 + Java 17**: High performance layered architecture.
- **PostgreSQL Database**: Configured with Spring Data JPA / Hibernate.
- **Auto DATABASE_URL Parsing**: Automatically translates Render/Neon `postgres://...` URLs to standard JDBC `jdbc:postgresql://...` format via `DatabaseConfig.java`.
- **CORS Configured**: Allows cross-origin API calls from your Vercel frontend.
- **Optimized for Render Free Tier**: Multi-stage `Dockerfile` tuned with `-XX:MaxRAMPercentage=75.0` to respect memory limits.
- **Built-in Seeder**: Pre-load demo company records via `POST /api/employees/seed`.

## 📡 REST API Reference

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/employees` | List employees (filters: `?query=`, `?department=`, `?status=`) |
| `GET` | `/api/employees/{id}` | Get employee by ID |
| `POST` | `/api/employees` | Create a new employee |
| `PUT` | `/api/employees/{id}` | Update existing employee |
| `DELETE`| `/api/employees/{id}` | Delete employee |
| `GET` | `/api/employees/stats`| Get dashboard stats & department breakdown |
| `POST` | `/api/employees/seed` | Seed database with sample employees |
| `GET` | `/api/health` | Health check endpoint for uptime monitor |

## 🚀 Live Deployment on Render

1. Log in to [Render](https://dashboard.render.com).
2. Create a new **PostgreSQL** database:
   - Name: `worknest-postgres`
   - Plan: **Free**
   - Copy the database URL.
3. Create a new **Web Service**:
   - Connect this GitHub repository.
   - Runtime: **Docker**
   - Plan: **Free**
   - Add Environment Variables:
     - `DATABASE_URL` = *(Your PostgreSQL connection string)*
     - `PORT` = `8080`
     - `CORS_ALLOWED_ORIGINS` = `*`
4. Click **Create Web Service**.

## 💻 Local Execution

```bash
mvn clean spring-boot:run
```
API runs at `http://localhost:8080`.

# Hosting Guide (Forever-Free Options)

This guide lists practical hosting options to run the Spring Boot backend, React frontend, and MySQL database on long-lived free tiers.

## Option A: Oracle Cloud Infrastructure (OCI) Always Free

### Why OCI?
Oracle's Always Free tier provides generous resources that can run a small production-like stack with no time limits.

### Architecture
- Compute: 1x AMD/Arm VM (Always Free) running Docker/Compose
- Reverse proxy: Nginx (optional) for SSL/HTTP/2
- Backend: Spring Boot container
- Frontend: Static files served by Nginx or a lightweight Node server
- Database: MySQL in Docker (data persisted on attached volume)

### Steps Overview
1) Create OCI account and provision a VM (Ubuntu 22.04 LTS).
2) Open ports in OCI Security List: 80, 443, and custom if needed.
3) SSH into VM and install Docker + Docker Compose.
4) Clone the repo and configure environment variables.
5) Use `docker-compose.yml` to bring up MySQL, backend, and frontend.
6) (Optional) Set up a domain + Let’s Encrypt (via `nginx-proxy`/`certbot`).

### Example Compose override (on the VM)
Create `docker-compose.override.yml` and adjust credentials safely (don’t commit secrets):

```yaml
services:
  db:
    environment:
      MYSQL_DATABASE: banking_db
      MYSQL_USER: banking_user
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
    volumes:
      - db-data:/var/lib/mysql

  backend:
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/banking_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
      SPRING_DATASOURCE_USERNAME: banking_user
      SPRING_DATASOURCE_PASSWORD: ${MYSQL_PASSWORD}
      SERVER_PORT: 8080
    depends_on:
      - db

  frontend:
    environment:
      VITE_API_BASE_URL: http://localhost:8080/api

volumes:
  db-data:
```

Bring it up:

```bash
docker compose up -d --build
```

### Notes about Oracle Autonomous DB
OCI Always Free includes Autonomous Database (Oracle DB, not MySQL). You can use it with Spring Boot via the Oracle JDBC driver if you prefer Oracle DB over MySQL. If you want MySQL specifically, the simplest free path is a Compute VM + Docker MySQL as shown.

## Option B: Fly.io (Free Allowance)
- Run the Spring Boot app as a single VM with Postgres add-on (limited free credits).
- Frontend can be served as static assets on Fly Machines or via CDN.
- Persistent free capacity may change; check Fly.io quotas.

## Option C: Render (Free Web Service + Postgres)
- Free web service often sleeps on inactivity; fine for demos.
- Free Postgres tier available; check storage and connection limits.
- Frontend can be a static site with free tier.

## Option D: Railway (Limited Free Credits)
- Credits reset monthly; suitable for light usage but not strictly "forever".

## Databases (Forever/Long-Lived Free Options)
- MySQL on OCI VM via Docker (recommended with OCI Always Free).
- PlanetScale (MySQL-compatible) free tier (non-persistent branches have limits; review current plan).
- Neon (Postgres) generous free tier for development/demo workloads.
- Supabase (Postgres) has a free tier; ensure limits fit your usage.

## Domain and TLS
- Use a domain from any registrar, point DNS A/AAAA to your OCI VM.
- Use Let’s Encrypt with `certbot` or `nginx-proxy` + `acme-companion` for automatic SSL.

## CI/CD
- GitHub Actions in `.github/workflows/ci.yml` already builds backend and frontend.
- For deployment, add a separate workflow or deploy manually to your chosen platform.

## Cost & Quota Caveats
Free tiers evolve. Always confirm current quotas and terms for the chosen provider before production use.



# 🚀 Deployment Guide

## Production Deployment Checklist

### Pre-Deployment

- [ ] Update database credentials in environment variables
- [ ] Set `spring.jpa.show-sql=false` in production
- [ ] Configure proper logging levels
- [ ] Set up SSL/TLS certificates
- [ ] Configure CORS for production domain
- [ ] Review and update security settings
- [ ] Set up database backups
- [ ] Configure monitoring and alerts

### Environment Variables

Create a `.env` file or set environment variables:

```bash
# Server
SERVER_PORT=8080

# Database
SPRING_DATASOURCE_URL=jdbc:mysql://your-db-host:3306/banking_db
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password

# Logging
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_COM_BANKING=WARN
```

### Building for Production

**Backend:**
```bash
.\mvnw.cmd clean package -DskipTests
```

**Frontend:**
```bash
cd frontend
npm run build
```

### Deployment Options

1. **Traditional Server** - Deploy JAR file and serve frontend from Nginx
2. **Docker** - Containerize both services
3. **Cloud Platforms** - Deploy to AWS, Azure, GCP, Railway, Heroku
4. **Kubernetes** - For microservices architecture

---

## Security Recommendations

1. **Use HTTPS** - Always use SSL/TLS in production
2. **Environment Variables** - Never commit credentials
3. **Password Hashing** - Implement BCrypt for passwords
4. **JWT Tokens** - Replace simple tokens with JWT
5. **Rate Limiting** - Implement API rate limiting
6. **Input Validation** - Validate all inputs server-side
7. **SQL Injection Prevention** - Use parameterized queries (JPA handles this)
8. **CORS** - Restrict CORS to specific domains

---

## Monitoring

- Set up application monitoring (Spring Boot Actuator)
- Database connection monitoring
- Error tracking (Sentry, Loggly)
- Performance monitoring (New Relic, Datadog)


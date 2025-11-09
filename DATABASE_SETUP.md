# Database Setup Guide

## Quick Setup

### Option 1: Local MySQL (Recommended for Development)

1. **Install MySQL** (if not already installed)
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Or use MySQL via Docker: `docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0`

2. **Create Database**
   ```sql
   CREATE DATABASE banking_db;
   ```

3. **Update `application.properties`**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/banking_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   ```

4. **Run the Application**
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

### Option 2: Cloud MySQL (Railway, AWS RDS, etc.)

#### Railway MySQL Setup

1. **Get Database Credentials from Railway Dashboard**
   - Go to your Railway project → MySQL service → Variables tab
   - Note down: `RAILWAY_TCP_PROXY_DOMAIN`, `RAILWAY_TCP_PROXY_PORT`, `MYSQL_ROOT_PASSWORD`

2. **Update `application.properties`** (Already configured for Railway)
   - The configuration uses Railway environment variables automatically
   - If running locally, update the default values in `application.properties`
   - Or set environment variables:
     ```bash
     # Windows PowerShell
     $env:RAILWAY_TCP_PROXY_DOMAIN="switchback.proxy.rlwy.net"
     $env:RAILWAY_TCP_PROXY_PORT="54062"
     $env:MYSQL_ROOT_PASSWORD="your_password"
     ```

3. **For Other Cloud Providers**
   ```properties
   spring.datasource.url=jdbc:mysql://your-host:port/database_name?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### Option 3: Environment Variables (Production)

Set these environment variables (they will override application.properties):

```bash
# Windows PowerShell
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/banking_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:SPRING_DATASOURCE_USERNAME="root"
$env:SPRING_DATASOURCE_PASSWORD="your_password"

# Linux/Mac
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/banking_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD="your_password"
```

## Troubleshooting

### Error: "Failed to configure a DataSource"

**Solution**: Make sure:
1. MySQL is running
2. Database credentials in `application.properties` are correct
3. Database exists (or `createDatabaseIfNotExist=true` is in URL)

### Error: "Access denied for user"

**Solution**: 
1. Check username and password
2. Verify MySQL user has proper permissions
3. Check if MySQL allows connections from your IP

### Error: "Communications link failure"

**Solution**:
1. Check if MySQL is running: `mysql -u root -p`
2. Verify port 3306 is not blocked by firewall
3. For cloud databases, check if your IP is whitelisted

## Database Schema

The application will automatically create tables on first run (due to `spring.jpa.hibernate.ddl-auto=update`).

To manually create schema, run `src/main/resources/schema.sql` in your MySQL client.

## Initial Data

To insert initial roles and sample data, uncomment sections in `src/main/resources/data.sql`.


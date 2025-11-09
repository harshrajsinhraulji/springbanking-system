# Railway MySQL Setup Guide

## Current Configuration

Your application is configured to use Railway MySQL with the following credentials:

- **Database**: `railway`
- **Username**: `root`
- **Password**: `sIIFIeiYvPpvitAhaGGIwldlqieitnYr`

## Getting Railway Connection Details

1. **Go to Railway Dashboard**
   - Navigate to your project
   - Click on your MySQL service
   - Go to the **Variables** tab

2. **Find These Values**:
   - `RAILWAY_TCP_PROXY_DOMAIN` - Public proxy domain (e.g., `switchback.proxy.rlwy.net`)
   - `RAILWAY_TCP_PROXY_PORT` - Public proxy port (e.g., `54062`)
   - `MYSQL_ROOT_PASSWORD` - Already set in config
   - `MYSQL_DATABASE` - Already set to `railway`

## Updating Configuration

### Option 1: Update application.properties Directly

Edit `src/main/resources/application.properties` and update:
```properties
spring.datasource.url=jdbc:mysql://YOUR_RAILWAY_TCP_PROXY_DOMAIN:YOUR_RAILWAY_TCP_PROXY_PORT/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&connectTimeout=60000&socketTimeout=60000
```

### Option 2: Use Environment Variables (Recommended)

Set these environment variables before running:
```powershell
# Windows PowerShell
$env:RAILWAY_TCP_PROXY_DOMAIN="switchback.proxy.rlwy.net"
$env:RAILWAY_TCP_PROXY_PORT="54062"
$env:MYSQL_ROOT_PASSWORD="sIIFIeiYvPpvitAhaGGIwldlqieitnYr"
```

```bash
# Linux/Mac
export RAILWAY_TCP_PROXY_DOMAIN="switchback.proxy.rlwy.net"
export RAILWAY_TCP_PROXY_PORT="54062"
export MYSQL_ROOT_PASSWORD="sIIFIeiYvPpvitAhaGGIwldlqieitnYr"
```

## Testing Connection

1. **Start the application**:
   ```bash
   npm start
   # or
   .\mvnw.cmd spring-boot:run
   ```

2. **Check health endpoint**:
   - Open: http://localhost:8080/health/db
   - Should show database connection status

3. **Test database endpoint**:
   - Open: http://localhost:8080/api/test/connection
   - Should show database connection details

## Troubleshooting

### Connection Timeout

- Check if Railway MySQL service is running
- Verify `RAILWAY_TCP_PROXY_DOMAIN` and `RAILWAY_TCP_PROXY_PORT` are correct
- Check firewall/network settings

### Access Denied

- Verify `MYSQL_ROOT_PASSWORD` is correct
- Check if your IP is whitelisted (Railway allows all by default for TCP proxy)

### SSL Errors

- The configuration uses `useSSL=false` for Railway
- If you need SSL, update the connection string

## Notes

- Railway TCP Proxy is for **external access** (from your local machine)
- Railway Private Domain is for **internal access** (when deployed on Railway)
- Current config uses TCP Proxy for local development
- When deploying to Railway, the environment variables will automatically override settings


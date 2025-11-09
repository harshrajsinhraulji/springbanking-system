# 🚂 Railway Backend Deployment - Step by Step

## Prerequisites
- ✅ GitHub repository with your code
- ✅ Railway account (free)
- ✅ Database already hosted on Railway

---

## Step 1: Sign Up for Railway

1. Go to [railway.app](https://railway.app)
2. Click **"Start a New Project"**
3. Sign up with **GitHub** (recommended for easy deployment)
4. Authorize Railway to access your repositories

---

## Step 2: Create New Project

1. Click **"New Project"** button
2. Select **"Deploy from GitHub repo"**
3. Choose your `springbanking-system` repository
4. Railway will create a new project

---

## Step 3: Configure Backend Service

Railway should auto-detect your Java project. If not:

1. **Go to your service settings**
2. **Build Settings:**
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -jar target/springbanking-system-0.0.1-SNAPSHOT.jar`
   - Or: `java -jar target/*.jar`

3. **Port:** Railway auto-detects port 8080

---

## Step 4: Get Database Connection Details

1. **Go to your Railway project**
2. **Find your MySQL service** (the database you already set up)
3. **Click on it** → Go to **"Variables"** tab
4. **Copy these values:**
   - `MYSQLHOST` (or `RAILWAY_PRIVATE_DOMAIN`)
   - `MYSQLPORT` (usually 3306)
   - `MYSQLDATABASE`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`

5. **Or use the public TCP proxy:**
   - `RAILWAY_TCP_PROXY_DOMAIN`
   - `RAILWAY_TCP_PROXY_PORT`

---

## Step 5: Set Environment Variables

1. **In your backend service**, go to **"Variables"** tab
2. **Click "New Variable"**
3. **Add these variables:**

```bash
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://[MYSQLHOST]:[MYSQLPORT]/[MYSQLDATABASE]?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true

# Example with TCP Proxy:
SPRING_DATASOURCE_URL=jdbc:mysql://switchback.proxy.rlwy.net:54062/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC

SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=[YOUR_MYSQLPASSWORD]

# JPA Settings
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false

# Server Settings
SERVER_PORT=8080

# CORS (update with your frontend URL later)
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
```

**Important:** Replace `[MYSQLHOST]`, `[MYSQLPORT]`, etc. with actual values from your database service.

---

## Step 6: Generate Public Domain

1. **Go to "Settings"** tab in your backend service
2. **Scroll to "Networking"** section
3. **Click "Generate Domain"**
4. **Copy the generated URL** (e.g., `springbanking-backend.railway.app`)

This is your backend API URL!

---

## Step 7: Deploy

1. **Railway will automatically deploy** when you:
   - Push to GitHub
   - Or click "Redeploy" in Railway dashboard

2. **Watch the deployment logs:**
   - Go to "Deployments" tab
   - Click on the latest deployment
   - Watch the build process

3. **Wait for "Deployment successful"** ✅

---

## Step 8: Test Your Backend

1. **Health Check:**
   ```
   https://your-backend-url.railway.app/api/health
   ```
   Should return: `{"status":"UP",...}`

2. **Database Check:**
   ```
   https://your-backend-url.railway.app/api/health/db
   ```
   Should show database connection info

3. **Swagger UI:**
   ```
   https://your-backend-url.railway.app/swagger-ui.html
   ```
   Should show API documentation

---

## Step 9: Update CORS (After Frontend Deployment)

Once you deploy your frontend, update CORS:

1. **Add environment variable:**
   ```
   CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
   ```

2. **Or update `CorsConfig.java`** to include your frontend domain

3. **Redeploy** the backend

---

## 🐛 Troubleshooting

### Build Fails

**Error:** `Maven build failed`
- **Solution:** Check build logs
- Ensure `pom.xml` is correct
- Verify Java version (should be 17)

**Error:** `Port already in use`
- **Solution:** Railway handles this automatically
- Check if port 8080 is set correctly

### Database Connection Fails

**Error:** `Connection refused`
- **Solution:** 
  - Verify database service is running
  - Check connection string format
  - Ensure credentials are correct
  - Try using TCP proxy URL instead

**Error:** `Access denied`
- **Solution:**
  - Verify username/password
  - Check database user permissions

### Application Won't Start

**Error:** `Application failed to start`
- **Solution:**
  - Check Railway logs
  - Verify all environment variables are set
  - Check for missing dependencies

---

## 📊 Monitoring

1. **View Logs:**
   - Go to "Deployments" → Click deployment → View logs

2. **Resource Usage:**
   - Check "Metrics" tab
   - Monitor CPU, Memory, Network

3. **Deployment History:**
   - View all deployments in "Deployments" tab

---

## 🔄 Auto-Deploy from GitHub

Railway automatically deploys when you:
- Push to `main` branch
- Merge pull requests

To disable auto-deploy:
- Go to Settings → Source → Disable auto-deploy

---

## 💡 Pro Tips

1. **Use Railway's private networking** for database (faster, more secure)
2. **Set up health checks** (Railway does this automatically)
3. **Monitor your $5 free credit** in the dashboard
4. **Use environment variables** for all configuration
5. **Enable deployment notifications** in Settings

---

## ✅ Checklist

- [ ] Railway account created
- [ ] Project created from GitHub
- [ ] Environment variables set
- [ ] Public domain generated
- [ ] Deployment successful
- [ ] Health check passes
- [ ] Database connection works
- [ ] Swagger UI accessible
- [ ] CORS configured (after frontend deploy)

---

**Your backend is now live! 🎉**

Next: Deploy frontend to Vercel (see `VERCEL_FRONTEND_DEPLOY.md`)


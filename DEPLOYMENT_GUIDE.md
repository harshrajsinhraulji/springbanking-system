# 🚀 Free Hosting Deployment Guide

Complete step-by-step guide to deploy Spring Banking System for FREE!

## 📋 Table of Contents

1. [Overview](#overview)
2. [Backend Hosting Options](#backend-hosting-options)
3. [Frontend Hosting Options](#frontend-hosting-options)
4. [Recommended Setup](#recommended-setup)
5. [Step-by-Step Deployment](#step-by-step-deployment)

---

## 🎯 Overview

Your database is already hosted (Railway). Now we need to deploy:
- **Backend**: Spring Boot API (port 8080)
- **Frontend**: React App (port 3000)

**Best Free Options:**
- **Backend**: Railway or Render
- **Frontend**: Vercel or Netlify

---

## 🔧 Backend Hosting Options

### Option 1: Railway (Recommended) ⭐

**Why Railway?**
- ✅ Free tier: $5 credit/month
- ✅ Easy deployment from GitHub
- ✅ Automatic HTTPS
- ✅ Environment variables management
- ✅ Same platform as your database

**Limitations:**
- Free tier: $5 credit/month (~500 hours)
- Sleeps after inactivity (wakes on request)

### Option 2: Render

**Why Render?**
- ✅ Free tier available
- ✅ Automatic deployments from GitHub
- ✅ Free SSL certificates
- ✅ Good documentation

**Limitations:**
- Free tier sleeps after 15 min inactivity
- Slower cold starts

### Option 3: Fly.io

**Why Fly.io?**
- ✅ Generous free tier
- ✅ Global edge network
- ✅ Fast cold starts

**Limitations:**
- More complex setup
- Requires CLI installation

---

## 🎨 Frontend Hosting Options

### Option 1: Vercel (Recommended) ⭐

**Why Vercel?**
- ✅ Perfect for React apps
- ✅ Free tier: Unlimited
- ✅ Automatic deployments
- ✅ Global CDN
- ✅ Zero configuration
- ✅ Custom domains

**Limitations:**
- None for personal projects!

### Option 2: Netlify

**Why Netlify?**
- ✅ Free tier: 100GB bandwidth/month
- ✅ Automatic deployments
- ✅ Form handling
- ✅ Easy setup

**Limitations:**
- 100GB bandwidth limit (usually enough)

### Option 3: GitHub Pages

**Why GitHub Pages?**
- ✅ Completely free
- ✅ Integrated with GitHub

**Limitations:**
- Static sites only (needs build)
- No server-side features

---

## 🏆 Recommended Setup

**Best Combination:**
1. **Backend**: Railway (same as database)
2. **Frontend**: Vercel (best for React)

**Why this combo?**
- Both free
- Easy to set up
- Professional performance
- Automatic deployments
- Great documentation

---

## 📝 Step-by-Step Deployment

### Part 1: Deploy Backend to Railway

#### Step 1: Prepare Your Code

1. **Ensure your code is pushed to GitHub**
   ```bash
   git add .
   git commit -m "Ready for deployment"
   git push origin main
   ```

2. **Verify your `application.properties` has environment variable support**
   - Already configured! ✅
   - Database URL uses Railway variables

#### Step 2: Create Railway Account

1. Go to [railway.app](https://railway.app)
2. Click **"Start a New Project"**
3. Sign up with GitHub (recommended)
4. Authorize Railway to access your repositories

#### Step 3: Deploy Backend

1. **Create New Service**
   - Click **"New Project"**
   - Select **"Deploy from GitHub repo"**
   - Choose your `springbanking-system` repository
   - Railway will auto-detect it's a Java/Maven project

2. **Configure Build Settings**
   - Railway auto-detects Maven
   - Build command: `./mvnw clean package -DskipTests`
   - Start command: `java -jar target/*.jar`
   - Port: `8080`

3. **Set Environment Variables**
   - Go to **Variables** tab
   - Add these variables:
     ```
     SPRING_DATASOURCE_URL=jdbc:mysql://[YOUR_RAILWAY_DB_HOST]:[PORT]/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
     SPRING_DATASOURCE_USERNAME=root
     SPRING_DATASOURCE_PASSWORD=[YOUR_RAILWAY_DB_PASSWORD]
     SPRING_JPA_HIBERNATE_DDL_AUTO=update
     SPRING_JPA_SHOW_SQL=false
     ```
   - **Get your DB credentials from Railway MySQL service**

4. **Generate Public URL**
   - Go to **Settings** → **Networking**
   - Click **"Generate Domain"**
   - Copy the URL (e.g., `springbanking-backend.railway.app`)

5. **Deploy**
   - Railway will automatically build and deploy
   - Watch the logs for progress
   - Wait for "Deployment successful"

6. **Test Backend**
   - Visit: `https://your-backend-url.railway.app/api/health`
   - Should return: `{"status":"UP",...}`

#### Step 4: Update CORS (If Needed)

Your `CorsConfig.java` should allow your frontend domain. Update if deploying to different domain.

---

### Part 2: Deploy Frontend to Vercel

#### Step 1: Prepare Frontend

1. **Update API URL**
   - Create/update `frontend/.env.production`:
     ```env
     VITE_API_BASE_URL=https://your-backend-url.railway.app/api
     ```

2. **Commit and Push**
   ```bash
   cd frontend
   git add .
   git commit -m "Update API URL for production"
   git push origin main
   ```

#### Step 2: Create Vercel Account

1. Go to [vercel.com](https://vercel.com)
2. Click **"Sign Up"**
3. Sign up with GitHub (recommended)
4. Authorize Vercel to access your repositories

#### Step 3: Deploy Frontend

1. **Import Project**
   - Click **"Add New Project"**
   - Select your `springbanking-system` repository
   - Vercel will auto-detect settings

2. **Configure Project**
   - **Root Directory**: `frontend`
   - **Framework Preset**: Vite
   - **Build Command**: `npm run build`
   - **Output Directory**: `dist`
   - **Install Command**: `npm install`

3. **Environment Variables**
   - Click **"Environment Variables"**
   - Add:
     ```
     VITE_API_BASE_URL=https://your-backend-url.railway.app/api
     ```
   - Select **Production**, **Preview**, and **Development**

4. **Deploy**
   - Click **"Deploy"**
   - Vercel will build and deploy
   - Wait for deployment to complete (~2-3 minutes)

5. **Get Your URL**
   - After deployment, you'll get a URL like:
     `https://springbanking-system.vercel.app`
   - Copy this URL

#### Step 4: Update Backend CORS

1. Go back to Railway backend
2. Update environment variables:
   ```
   CORS_ALLOWED_ORIGINS=https://your-frontend-url.vercel.app
   ```
3. Or update `CorsConfig.java` to allow your Vercel domain

---

### Part 3: Update Frontend API URL

1. **Update Vercel Environment Variables**
   - Go to Vercel project settings
   - Update `VITE_API_BASE_URL` with your Railway backend URL
   - Redeploy if needed

---

## 🔍 Testing Your Deployment

### Test Backend

1. **Health Check**
   ```
   https://your-backend.railway.app/api/health
   ```

2. **Swagger UI**
   ```
   https://your-backend.railway.app/swagger-ui.html
   ```

3. **Database Connection**
   ```
   https://your-backend.railway.app/api/health/db
   ```

### Test Frontend

1. **Visit your Vercel URL**
   ```
   https://your-frontend.vercel.app
   ```

2. **Test Login**
   - Try customer login
   - Try admin login
   - Check if API calls work

---

## 🐛 Troubleshooting

### Backend Issues

**Problem**: Backend won't start
- **Solution**: Check Railway logs
- Check environment variables are set correctly
- Verify database connection string

**Problem**: Database connection fails
- **Solution**: 
  - Verify Railway MySQL service is running
  - Check connection string format
  - Ensure database credentials are correct

**Problem**: CORS errors
- **Solution**: Update `CorsConfig.java` with frontend URL

### Frontend Issues

**Problem**: API calls fail
- **Solution**: 
  - Check `VITE_API_BASE_URL` in Vercel environment variables
  - Verify backend URL is correct
  - Check browser console for errors

**Problem**: Build fails
- **Solution**: 
  - Check Vercel build logs
  - Ensure all dependencies are in `package.json`
  - Verify Node.js version (should be 18+)

---

## 📊 Alternative: All-in-One Railway Deployment

If you want everything on Railway:

### Deploy Frontend to Railway

1. **Create New Service in Railway**
   - Add service to existing project
   - Select **"Deploy from GitHub repo"**
   - Choose your repository

2. **Configure as Static Site**
   - Build command: `cd frontend && npm install && npm run build`
   - Start command: `npx serve -s frontend/dist -l 3000`
   - Port: `3000`

3. **Set Environment Variables**
   ```
   VITE_API_BASE_URL=https://your-backend-url.railway.app/api
   ```

4. **Deploy**
   - Railway will build and deploy
   - Get public URL

---

## 🔐 Security Checklist

Before going live:

- [ ] Change default admin credentials
- [ ] Use strong passwords
- [ ] Enable HTTPS (automatic on Railway/Vercel)
- [ ] Review CORS settings
- [ ] Set `SPRING_JPA_SHOW_SQL=false` in production
- [ ] Use environment variables for all secrets
- [ ] Enable rate limiting (future improvement)

---

## 📈 Monitoring

### Railway
- View logs in Railway dashboard
- Monitor resource usage
- Check deployment history

### Vercel
- View analytics
- Check build logs
- Monitor performance

---

## 💰 Cost Breakdown

**Free Tier Limits:**

| Service | Free Tier |
|---------|-----------|
| Railway | $5 credit/month (~500 hours) |
| Vercel | Unlimited (personal projects) |
| Netlify | 100GB bandwidth/month |

**Estimated Monthly Cost: $0** ✅

---

## 🎉 Next Steps

1. **Deploy backend to Railway**
2. **Deploy frontend to Vercel**
3. **Test everything**
4. **Share your live URL!**

---

## 📚 Additional Resources

- [Railway Documentation](https://docs.railway.app)
- [Vercel Documentation](https://vercel.com/docs)
- [Render Documentation](https://render.com/docs)
- [Spring Boot Deployment Guide](https://spring.io/guides/gs/spring-boot-for-azure/)

---

## 🆘 Need Help?

Common issues:
1. Check deployment logs
2. Verify environment variables
3. Test API endpoints directly
4. Check CORS configuration
5. Review error messages in browser console

---

**Good luck with your deployment! 🚀**


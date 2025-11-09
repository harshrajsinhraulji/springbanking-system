# ▲ Vercel Frontend Deployment - Step by Step

## Prerequisites
- ✅ GitHub repository with your code
- ✅ Vercel account (free)
- ✅ Backend already deployed (Railway URL)

---

## Step 1: Sign Up for Vercel

1. Go to [vercel.com](https://vercel.com)
2. Click **"Sign Up"**
3. Sign up with **GitHub** (recommended)
4. Authorize Vercel to access your repositories

---

## Step 2: Prepare Your Frontend

### Update Environment Variables

1. **Create/Update `frontend/.env.production`:**
   ```env
   VITE_API_BASE_URL=https://your-backend-url.railway.app/api
   ```

2. **Or create `frontend/.env.example`:**
   ```env
   VITE_API_BASE_URL=https://your-backend-url.railway.app/api
   ```

3. **Commit and push:**
   ```bash
   git add frontend/.env.production
   git commit -m "Add production environment variables"
   git push origin main
   ```

---

## Step 3: Import Project to Vercel

1. **Click "Add New Project"** in Vercel dashboard
2. **Select your GitHub repository** (`springbanking-system`)
3. **Click "Import"**

---

## Step 4: Configure Project Settings

Vercel should auto-detect Vite/React. Configure if needed:

### Framework Preset
- **Framework Preset:** Vite
- (Auto-detected)

### Build Settings
- **Root Directory:** `frontend`
- **Build Command:** `npm run build`
- **Output Directory:** `dist`
- **Install Command:** `npm install`

### Environment Variables
1. **Click "Environment Variables"**
2. **Add variable:**
   - **Name:** `VITE_API_BASE_URL`
   - **Value:** `https://your-backend-url.railway.app/api`
   - **Environments:** Select all (Production, Preview, Development)
3. **Click "Save"**

---

## Step 5: Deploy

1. **Click "Deploy"** button
2. **Wait for build to complete** (~2-3 minutes)
3. **Watch build logs** for any errors

---

## Step 6: Get Your URL

After deployment completes:

1. **You'll see a success message**
2. **Your URL will be:**
   ```
   https://springbanking-system.vercel.app
   ```
   (or similar, based on your repo name)

3. **Click the URL** to visit your live site!

---

## Step 7: Test Your Frontend

1. **Visit your Vercel URL**
2. **Test features:**
   - ✅ Login page loads
   - ✅ Customer registration works
   - ✅ Dashboard loads
   - ✅ API calls work (check browser console)
   - ✅ Transactions load

---

## Step 8: Update Backend CORS

Now that you have your frontend URL:

1. **Go back to Railway backend**
2. **Add/Update environment variable:**
   ```
   CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
   ```

3. **Or update `CorsConfig.java`:**
   ```java
   allowedOrigins = "https://your-frontend.vercel.app"
   ```

4. **Redeploy backend** (Railway auto-redeploys on variable change)

---

## Step 9: Custom Domain (Optional)

### Add Custom Domain

1. **Go to Vercel project settings**
2. **Click "Domains"**
3. **Add your domain** (e.g., `banking.yourdomain.com`)
4. **Follow DNS configuration instructions**
5. **Wait for SSL certificate** (automatic, ~5 minutes)

---

## 🔄 Auto-Deploy from GitHub

Vercel automatically deploys when you:
- Push to `main` branch
- Merge pull requests
- Create new branches (preview deployments)

### Preview Deployments

- Every branch gets a preview URL
- Perfect for testing before merging
- Automatically deleted when branch is deleted

---

## 🐛 Troubleshooting

### Build Fails

**Error:** `npm install failed`
- **Solution:**
  - Check `package.json` is correct
  - Verify Node.js version (should be 18+)
  - Check build logs for specific errors

**Error:** `Build command failed`
- **Solution:**
  - Verify `npm run build` works locally
  - Check for TypeScript/ESLint errors
  - Review build logs

### API Calls Fail

**Error:** `CORS error` or `Network error`
- **Solution:**
  - Verify `VITE_API_BASE_URL` is set correctly
  - Check backend CORS configuration
  - Verify backend URL is accessible

**Error:** `404 Not Found` on API calls
- **Solution:**
  - Check API URL format
  - Verify backend is running
  - Check backend routes

### Environment Variables Not Working

**Error:** `API URL is undefined`
- **Solution:**
  - Verify environment variable name: `VITE_API_BASE_URL`
  - Must start with `VITE_` for Vite
  - Redeploy after adding variables

---

## 📊 Analytics & Monitoring

### Vercel Analytics

1. **Go to project settings**
2. **Enable Analytics** (free tier available)
3. **View:**
   - Page views
   - Performance metrics
   - User locations

### Function Logs

1. **Go to "Deployments"**
2. **Click on a deployment**
3. **View "Functions" tab** for serverless function logs

---

## 🔐 Security

### Environment Variables

- ✅ Never commit `.env` files
- ✅ Use Vercel environment variables
- ✅ Different values for Production/Preview/Development

### HTTPS

- ✅ Automatic HTTPS on all Vercel deployments
- ✅ Free SSL certificates
- ✅ Automatic renewal

---

## 💡 Pro Tips

1. **Use Preview Deployments** for testing
2. **Set up deployment notifications** (Slack, email)
3. **Use Vercel CLI** for local testing:
   ```bash
   npm i -g vercel
   vercel
   ```

4. **Optimize images** (Vercel does this automatically)
5. **Enable Edge Functions** for better performance

---

## 📈 Performance

Vercel automatically:
- ✅ Optimizes images
- ✅ Minifies JavaScript/CSS
- ✅ Caches static assets
- ✅ Uses global CDN
- ✅ Enables compression

---

## ✅ Checklist

- [ ] Vercel account created
- [ ] Project imported from GitHub
- [ ] Root directory set to `frontend`
- [ ] Environment variables configured
- [ ] Build settings correct
- [ ] Deployment successful
- [ ] Frontend URL accessible
- [ ] Login page works
- [ ] API calls work
- [ ] Backend CORS updated
- [ ] Custom domain added (optional)

---

## 🎉 You're Live!

Your frontend is now deployed and accessible worldwide!

**Next Steps:**
1. Test all features
2. Share your URL
3. Monitor analytics
4. Set up custom domain (optional)

---

## 📚 Additional Resources

- [Vercel Documentation](https://vercel.com/docs)
- [Vite Deployment Guide](https://vitejs.dev/guide/static-deploy.html)
- [Environment Variables Guide](https://vercel.com/docs/concepts/projects/environment-variables)

---

**Congratulations! Your full-stack app is now live! 🚀**


# Application Setup Guide

## Environment Configuration

### Step 1: Database Setup

1. **Create MySQL Database**
   ```sql
   CREATE DATABASE banking_db;
   ```

2. **Update Configuration**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/banking_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### Step 2: Environment Variables (Optional)

Create a `.env` file in the project root:
```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/banking_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_password
SERVER_PORT=8080
```

### Step 3: Frontend Configuration

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure API URL**
   
   Create `frontend/.env`:
   ```env
   VITE_API_BASE_URL=http://localhost:8080/api
   ```

## Running the Application

### Option 1: Using Scripts (Windows)

```powershell
# Start both services
.\start-all.ps1

# Stop all services
.\stop-services.ps1
```

### Option 2: Manual Start

**Terminal 1 - Backend:**
```bash
.\mvnw.cmd spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd frontend
npm run dev
```

## Access Points

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **Health Check**: http://localhost:8080/health

## Troubleshooting

### Database Connection Issues

1. Verify MySQL is running
2. Check database credentials
3. Ensure database exists
4. Check firewall settings

### Port Already in Use

```powershell
# Check ports
.\check-ports.ps1

# Stop services
.\stop-services.ps1
```

### Frontend Build Issues

```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```


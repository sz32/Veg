# Quick Start Guide - Product APIs

## 🚀 Run Locally in 3 Steps

### Step 1: Build the Module
```bash
cd /Users/zeel/StudioProjects/FullCreative/Personal/AiAgent
./gradlew :product-apis:build
```

### Step 2: Run the Server
```bash
./gradlew :product-apis:run
```

### Step 3: Test the API
Open your browser or use curl:
```bash
curl http://localhost:8080/api/v1/products
```

---

## 🐳 Run with Docker

### Build and Run
```bash
cd product-apis
docker-compose up --build
```

### Stop
```bash
docker-compose down
```

---

## ☁️ Deploy to Cloud

### Heroku (Free Tier Available)

1. **Install Heroku CLI**
```bash
brew tap heroku/brew && brew install heroku
```

2. **Login**
```bash
heroku login
```

3. **Create App**
```bash
heroku create your-product-api
```

4. **Build & Deploy**
```bash
# From project root
./gradlew :product-apis:fatJar

# Create a git repo if not already
cd product-apis
git init
git add .
git commit -m "Initial commit"

# Deploy
heroku git:remote -a your-product-api
git push heroku main
```

5. **Open App**
```bash
heroku open
```

### Railway (Easiest Deployment)

1. **Install Railway CLI**
```bash
npm i -g @railway/cli
```

2. **Login & Deploy**
```bash
cd product-apis
railway login
railway init
railway up
```

3. **Get URL**
```bash
railway domain
```

### Render (Free with Auto-Deploy)

1. Go to https://render.com
2. Click **New +** → **Web Service**
3. Connect your GitHub repo
4. Configure:
   - **Name**: product-apis
   - **Build Command**: `./gradlew :product-apis:fatJar`
   - **Start Command**: `java -jar product-apis/build/libs/product-apis-1.0.0.jar`
5. Click **Create Web Service**

---

## 📱 Connect to Android App

Update your Android app's network configuration:

```kotlin
// In your Android app
object ApiConfig {
    const val BASE_URL = "https://your-app-name.herokuapp.com/"
    // or
    const val BASE_URL = "https://your-app.railway.app/"
}
```

Then use Retrofit or Ktor Client to make requests:

```kotlin
// Get all products
val response = apiService.getProducts()
```

---

## ✅ Verify Deployment

Test your deployed API:

```bash
# Replace with your actual URL
curl https://your-app-name.herokuapp.com/health

curl https://your-app-name.herokuapp.com/api/v1/products
```

---

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

### Build Fails
```bash
# Clean and rebuild
./gradlew :product-apis:clean :product-apis:build --refresh-dependencies
```

### Check Logs
```bash
# Local logs
tail -f product-apis/logs/product-apis.log

# Heroku logs
heroku logs --tail

# Railway logs
railway logs
```

---

## 📚 Next Steps

1. ✅ Deploy to cloud platform
2. ✅ Update Android app with API URL
3. ✅ Test all endpoints
4. ✅ Monitor logs
5. ⚡ Optional: Add authentication
6. ⚡ Optional: Connect to real database

---

**Need Help?** Check the full [README.md](README.md) for detailed documentation.


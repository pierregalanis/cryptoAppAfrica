Access Swagger UI in your browser:

Local: http://localhost:8080/swagger-ui/index.html

EC2: http://3.148.173.108:8080/swagger-ui/index.html

public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
http
.csrf(csrf -> csrf.disable())
.authorizeHttpRequests(auth -> auth
.requestMatchers(
"/api/auth/**",
"/api/crypto/**",
"/v3/api-docs/**",
"/swagger-ui/**",
"/swagger-ui.html"
).permitAll()
.anyRequest().authenticated()
requestMatchers("/api/test/**", "/actuator/**").permitAll() : allow endpoints


Allowed access to:

/api/auth/** → for login/register

/api/crypto/** → for public crypto APIs

/v3/api-docs/**, /swagger-ui/**, /swagger-ui.html → Swagger/OpenAPI access



## 🔍 Swagger API Testing Guide for AfricaCrypto

This guide explains how to use **Swagger UI** to test your AfricaCrypto backend APIs locally.

---

### ✅ 1. Start Your Application

Make sure your Spring Boot app is running:

```bash
mvn clean package
java -jar target/africacrypto-0.0.1-SNAPSHOT.jar
```

By default, it runs on:

```
http://localhost:8080
```

---

### ✅ 2. Open Swagger UI

Visit Swagger UI in your browser:

```
http://localhost:8080/swagger-ui/index.html
```

---

### ✅ 3. Log In to Get a Token

- Expand the endpoint:
  `POST /api/auth/login`
- Click **"Try it out"**
- Enter this example body:

```json
{
  "email": "aboh@example.com",
  "password": "Smash12!"
}
```

- Click **"Execute"**

💡 You’ll get a JWT token in the response body.

---

### ✅ 4. Authorize Requests

- Click the **"Authorize"** button at the top right of Swagger UI
- In the popup, paste your token in this format:

```
Bearer <your_token_here>
```

📌 Example:

```
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYm9oQGV4YW1wbGUuY29tIiwidXNlcklkIjox...
```

Click **"Authorize"**, then close the modal.

---

### ✅ 5. Test Endpoints

You can now try any authenticated endpoints such as:

- `GET /api/user/watchlist`
- `POST /api/user/watchlist`
- `DELETE /api/user/watchlist/{coinId}`

Use **"Try it out"** and **"Execute"** to see live results.

---

### ✅ Notes

- You **must authorize** before testing protected endpoints.
- The token expires after a while — login again if needed.
- Make sure `/v3/api-docs/**` and `/swagger-ui/**` are permitted in your `SecurityConfig.java`.

---
ytest watchlist:
{
"coinId": "bitcoin"
}


Enjoy testing! 🚀


# ✅ How to Test and Use Promote-to-Admin in AfricaCrypto Backend

This guide walks through registering users, promoting one to ADMIN, and verifying access to admin-only routes.

---

## 🎯 Goal

Promote a regular user (e.g. `john@example.com`) to `ADMIN` so they can access protected `/admin/**` endpoints.

---

## ✅ Step-by-Step Guide

### 👤 1. Register Two Users

```bash
# Register a regular user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john", "email":"john@example.com", "password":"123456"}'

# Register intended admin
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin", "email":"admin@example.com", "password":"adminpass"}'
```

---

### 🛠 2. Promote Admin Manually via SQL (One-Time Setup)

```sql
-- Get the user ID of the admin
SELECT id FROM users WHERE email = 'admin@example.com';

-- Promote to ADMIN using the correct user_id (e.g., ID = 2)
INSERT INTO user_roles (user_id, role) VALUES (2, 'ADMIN');
```

---

### 🔐 3. Login as Admin to Get a Token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com", "password":"adminpass"}'
```

Copy the returned **JWT token**.

---

### 🚀 4. Promote a User via the API

```bash
curl -X POST "http://localhost:8080/api/admin/promote-to-admin?email=john@example.com" \
  -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN"
```

Expected response:
```
✅ john@example.com promoted to ADMIN
```

---

### 🧪 5. Verify John Can Access Admin Routes

Login as John:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com", "password":"123456"}'
```

Use John’s token to test an admin-only route:

```bash
curl -X GET http://localhost:8080/api/admin/test \
  -H "Authorization: Bearer JOHN_JWT_TOKEN"
```

Expected result:
```
🔒 Welcome, ADMIN!
```

---

## ❓ FAQ

### ➤ Is this how I’ll promote employees to ADMIN?
Yes — in production, only users with `ADMIN` or `SUPER_ADMIN` roles should have access to the `/promote-to-admin` endpoint. You can restrict this further using `@PreAuthorize("hasRole('SUPER_ADMIN')")` if needed.

You’ll use this endpoint in your internal tools or dashboard to promote team members.

---

## 🧠 Summary Table

| Step                      | Command/Action                                             |
|---------------------------|-----------------------------------------------------------|
| Register users            | `/api/auth/register` for both admin and regular user     |
| Promote via DB (1-time)   | Insert into `user_roles` manually                        |
| Login as admin            | `/api/auth/login` for admin account                     |
| Promote via endpoint      | `/api/admin/promote-to-admin?email=...` with JWT header |
| Verify admin access       | Login as promoted user and test `/api/admin/*`           |

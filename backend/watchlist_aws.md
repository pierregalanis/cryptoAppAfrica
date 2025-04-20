
# 🪙 AfricaCrypto Backend - Watchlist & AWS Secret Setup Guide

This guide explains how to:

1. ✅ Add a cryptocurrency to a user's watchlist
2. ❌ Remove a cryptocurrency from the watchlist
3. 🔐 Store and use your JWT secret securely with AWS SSM Parameter Store 
4. to login to aws CLI: aws configure

---

## 📥 Add to Watchlist

**Endpoint:**  
`POST /api/watchlist/add`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "userId": 1,
  "cryptoSymbol": "BTC"
}
```

**Example using curl:**
```bash
curl -X POST http://localhost:8080/api/watchlist/add \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "cryptoSymbol": "BTC"}'
```

---

## 🗑 Remove from Watchlist

**Endpoint:**  
`DELETE /api/watchlist/delete`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "userId": 1,
  "cryptoSymbol": "BTC"
}
```

**Example using curl:**
```bash
curl -X DELETE http://localhost:8080/api/watchlist/delete \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "cryptoSymbol": "BTC"}'
```

---

## 🔐 Store JWT Secret in AWS Parameter Store

To securely store your JWT signing key in AWS SSM Parameter Store:

```bash
aws ssm put-parameter \
  --name "africacrypto-jwt-secret" \
  --value "your-super-secret-key-here" \
  --type "SecureString" \
  --region us-east-1
```

📝 You can retrieve it in EC2 or your local terminal like this:

```bash
export JWT_SECRET=$(aws ssm get-parameter --name "africacrypto-jwt-secret" --with-decryption --query Parameter.Value --output text)
```

---

## ⚙️ Spring Boot Config

Update your `application.properties`:

```properties
jwt.secret=${JWT_SECRET}
```

This ensures the JWT secret is injected from your environment or AWS securely, without hardcoding secrets in your source code.

---

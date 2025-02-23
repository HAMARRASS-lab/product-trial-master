# Alten E-commerce Project Documentation

This document describes how to set up and test the Alten e-commerce website application, comprising a front-end and back-end system.

## Project Structure
```
product-trial-master/
├── front (Angular Front-end)
└── backend (Spring Boot Back-end)
```

---

## Front-end Setup

**Technologies Used:**
- Angular (latest version)
- TypeScript
- HTML5 & CSS3
- Bootstrap (optional)

### Installation and Launch

Navigate to the front-end directory:

```bash
cd product-trial-master/front
npm install
ng serve --open
```

---

## Back-end Setup

### Technologies
- Java 17
- Spring Boot
- Docker

### Run the Application

**With Docker Compose:**
```bash
docker-compose up --build
```

**Manually (without compose):**
```bash
docker build -t backend-app .
docker run -p 8080:8080 backend-app
```

---

## Testing the APIs

You can test the APIs using one of two methods:

### Method 1: IntelliJ HTTP Client

Create HTTP requests in IntelliJ: `Tools → HTTP Client → Create request`

**Example requests:**

#### Admin Login
```http
POST http://localhost:8080/auth/login
Content-Type: application/x-www-form-urlencoded

email=admin@admin.com&password=admin123
```

#### User Login
```http
POST http://localhost:8080/auth/login
Content-Type: application/x-www-form-urlencoded

email=hamza@gmail.com&password=1234
```

#### Add User Account
```http
POST http://localhost:8080/addUserAccount
Content-Type: application/json

{
  "username": "username",
  "firstname": "hamarras",
  "email": "hamza@gmail.com",
  "password": "1234"
}
```

#### Products Management

**Get Products:**
```http
GET http://localhost:8080/api/products
Accept: application/json
Authorization: Bearer <TOKEN>
```

**Add Product:**
```http
POST http://localhost:8080/api/addProduct
Content-Type: application/json
Authorization: Bearer <ADMIN_TOKEN>

{
  "code": "P012",
  "name": "Redmi124",
  "description": "High-end gaming laptop",
  "image": "laptop.jpg",
  "category": "Electronics",
  "price": 1500,
  "quantity": 10,
  "internalReference": "LTP-001",
  "shellId": 1,
  "rating": 10,
  "inventoryStatus": "INSTOCK"
}
```

**Update Product:**
```http
PUT http://localhost:8080/api/products/1
Content-Type: application/json
Authorization: Bearer <ADMIN_TOKEN>

{
  "code": "P012",
  "name": "IPHONE 13",
  "description": "High-end gaming laptop",
  "image": "laptop.jpg",
  "category": "TEST",
  "price": 1500,
  "quantity": 10,
  "internalReference": "LTP-001",
  "shellId": 1,
  "rating": 10,
  "inventoryStatus": "INSTOCK"
}
```

**Delete Product:**
```http
DELETE http://localhost:8080/api/deleteProduct/2
Authorization: Bearer <ADMIN_TOKEN>
```

#### Shopping Cart & Wishlist (User)

- **Add to Cart:**
  ```http
  POST http://localhost:8080/api/cart/add/{productId}
  Authorization: Bearer <USER_TOKEN>
  ```

- **View Cart:**
  ```http
  GET http://localhost:8080/api/cart
  Authorization: Bearer <USER_TOKEN>
  ```

- **Remove from Cart:**
  ```http
  DELETE http://localhost:8080/api/cart/remove/{productId}
  Authorization: Bearer <USER_TOKEN>
  ```

- **Add to Wishlist:**
  ```http
  POST http://localhost:8080/api/wishlist/add/{productId}
  Authorization: Bearer <USER_TOKEN>
  ```

- **View Wishlist:**
  ```http
  GET http://localhost:8080/api/wishlist
  Authorization: Bearer <USER_TOKEN>
  ```

### Method 2: Swagger UI

Access Swagger UI at:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

**Swagger Steps:**
1. Login with Admin credentials:
   - Email: `admin@admin.com`
   - Password: `admin123`

2. Click **Authorize**, and paste the token from login.
3. Test API endpoints via Swagger interface.

---

### User Roles & Actions Summary

- **Admin:**
  - Login
  - Add/Delete/Update Products
  - Create user accounts

- **User:**
  - Login
  - Manage shopping cart
  - Manage wishlist


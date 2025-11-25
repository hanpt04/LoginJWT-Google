# JWT Authentication with Spring Boot

Dự án Spring Boot triển khai xác thực JWT (JSON Web Token) và OAuth2 với Google Login.

## 📋 Mục lục
- [Tính năng](#tính-năng)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Cài đặt](#cài-đặt)
- [Cấu hình](#cấu-hình)
- [API Endpoints](#api-endpoints)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Sử dụng](#sử-dụng)

## ✨ Tính năng

- ✅ Xác thực JWT (JSON Web Token)
- ✅ Đăng nhập bằng username/password
- ✅ Đăng nhập bằng Google OAuth2
- ✅ Phân quyền người dùng (Role-based Authorization)
- ✅ Bảo mật API với Spring Security
- ✅ Session Stateless
- ✅ Custom UserDetails Service
- ✅ H2 Database (In-memory)

## 🛠 Công nghệ sử dụng

- **Spring Boot 3.5.6**
- **Java 17**
- **Spring Security**
- **Spring Data JPA**
- **JWT (JSON Web Token) - io.jsonwebtoken v0.11.5**
- **OAuth2 Client (Google Login)**
- **H2 Database**
- **Lombok**
- **Maven**

## 📦 Yêu cầu hệ thống

- Java 17 trở lên
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code...)

## 🚀 Cài đặt

### 1. Clone dự án

```bash
git clone <repository-url>
cd JWT
```

### 2. Cài đặt dependencies

```bash
mvn clean install
```

### 3. Chạy ứng dụng

**Windows (PowerShell):**
```powershell
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

Hoặc chạy trực tiếp từ IDE bằng cách run `JwtApplication.java`

Ứng dụng sẽ chạy tại: `http://localhost:8080`

## ⚙️ Cấu hình

### application.properties

```properties
# Application Name
spring.application.name=JWT

# JWT Configuration
jwt.secret=mySecretKeyForJwtSigning1234567890abcdefghijklmnopqrstuvwxyz
jwt.expiration.ms=3600000

# Google OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:8080/login/oauth2/code/google
spring.security.oauth2.client.registration.google.scope=email,profile

# Frontend/Mobile URLs
frontend.url=http://localhost:3000
mobile.url=http://localhost:8081
```

**⚠️ Lưu ý:** 
- Thay đổi `jwt.secret` với secret key của bạn trong môi trường production
- Cấu hình Google OAuth2 credentials tại [Google Cloud Console](https://console.cloud.google.com/)

## 🔌 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Mô tả | Authentication |
|--------|----------|-------|----------------|
| GET | `/auth` | Test endpoint | Public |
| POST | `/auth/login` | Đăng nhập với username/password | Public |
| GET | `/auth/login-with-google` | Redirect đến Google OAuth2 | Public |

#### Request Body - Login

```json
{
  "username": "your_username",
  "password": "your_password"
}
```

#### Response - Login Success

```
HTTP/1.1 200 OK
Authorization: Bearer <jwt_token>

<jwt_token_string>
```

### Protected Endpoints

| Method | Endpoint | Mô tả | Required Role |
|--------|----------|-------|---------------|
| GET | `/test` | Test endpoint cho user | USER |
| GET | `/test/admin` | Test endpoint cho admin | ADMIN |

### Sử dụng Token

Thêm JWT token vào header của request:

```
Authorization: Bearer <your_jwt_token>
```

## 📁 Cấu trúc dự án

```
src/main/java/thuha/com/jwt/
├── controller/
│   ├── AuthController.java          # Xử lý authentication
│   └── TestController.java          # Test endpoints
├── model/
│   ├── Account.java                 # Entity Account
│   ├── LoginRequest.java            # DTO cho login request
│   └── Role.java                    # Enum Role (USER, ADMIN)
├── repo/
│   └── AccountRepo.java             # Repository cho Account
├── security/
│   ├── CustomOAuth2User.java        # Custom OAuth2 User
│   ├── CustomOAuthUserService.java  # OAuth2 User Service
│   ├── CustomUserDetails.java       # Custom UserDetails
│   ├── CustomUserDetailsService.java# UserDetails Service
│   ├── JwtAuthenticationFilter.java # JWT Filter
│   ├── JwtUtils.java                # JWT Utilities
│   ├── OAuth2AuthenticationSuccessHandler.java # OAuth2 Success Handler
│   └── SecurityConfig.java          # Spring Security Configuration
├── service/
│   └── AccountService.java          # Account Service
└── JwtApplication.java              # Main Application
```

## 📖 Sử dụng

### 1. Đăng nhập bằng Username/Password

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "password"
  }'
```

### 2. Sử dụng JWT Token

```bash
curl -X GET http://localhost:8080/test \
  -H "Authorization: Bearer <your_jwt_token>"
```

### 3. Đăng nhập bằng Google

Truy cập: `http://localhost:8080/auth/login-with-google`

Hoặc trực tiếp: `http://localhost:8080/oauth2/authorization/google`

## 🔒 Security Configuration

- **CSRF**: Disabled (phù hợp cho REST API)
- **Session Management**: STATELESS (không lưu session)
- **HTTP Basic**: Disabled
- **JWT Filter**: Xác thực token cho mọi request
- **OAuth2**: Hỗ trợ đăng nhập bằng Google

## 🧪 Testing

Chạy tests:

```bash
mvn test
```

## 📝 Ghi chú

- Database H2 chạy in-memory, dữ liệu sẽ mất khi restart application
- Token JWT mặc định có thời hạn 1 giờ (3600000ms)
- PasswordEncoder hiện tại là NoOpPasswordEncoder (⚠️ không nên dùng trong production)

## 🔄 Phát triển tiếp

- [ ] Chuyển sang BCryptPasswordEncoder
- [ ] Thêm Refresh Token
- [ ] Tích hợp database thực (MySQL, PostgreSQL)
- [ ] Thêm unit tests
- [ ] Thêm Swagger/OpenAPI documentation
- [ ] Xử lý exception toàn cục
- [ ] Logging và monitoring

## 👨‍💻 Tác giả

Thu Ha - Semester 7 - SWD

## 📄 License

Dự án học tập - Semester 7

---

**Ngày tạo:** November 25, 2025


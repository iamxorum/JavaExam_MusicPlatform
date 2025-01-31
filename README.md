# 🎵 Music Streaming Platform Subscription Service

A Spring Boot application that manages music streaming subscriptions with a simple frontend interface.

## ✨ Features

- 🎯 Create new subscriptions with different plans (FREE, PREMIUM, FAMILY)
- 🗑️ Delete expired or cancelled subscriptions 
- ✅ Validation of subscription data
- 🔄 RESTful API with Swagger documentation
- 🖥️ Frontend interface for subscription management

## 🛠️ Tech Stack

### 🔙 Backend
- ☕ Java 17
- 🍃 Spring Boot 3.2.2
- 🔒 Spring Security
- 💾 Spring Data JPA
- 🐘 PostgreSQL
- 🔄 MapStruct
- 🏗️ Lombok
- 📚 OpenAPI (Swagger)

### 🎨 Frontend
- 📄 HTML5
- 🎨 CSS3
- 💻 JavaScript (Vanilla)
- 🐍 Python (for serving static files)

## 🚀 Getting Started

### 📋 Prerequisites

- ☕ Java 17 or higher
- 🏗️ Maven
- 🐳 Docker (for PostgreSQL)
- 🌐 Web browser
- 🐍 Python 3.x

### 💾 Database Setup

1. Create a `.env` file in the root directory with the following variables:
POSTGRES_USER={{USER}}
POSTGRES_PASSWORD={{PSW}}
POSTGRES_DB=examen_java

2. Start the PostgreSQL container:
docker-compose up -d

### 🏃‍♂️ Running the Application

1. Build the project:
./mvnw clean install

2. Run the application:
./mvnw spring-boot:run

3. Run the frontend (in a separate terminal):
cd frontend
python -m http.server 3000

4. Access the application:
- 🌐 Frontend: http://localhost:3000
- 📚 Swagger UI: http://localhost:8080/swagger-ui.html
- 📖 API Docs: http://localhost:8080/v3/api-docs

## 🔌 API Endpoints

### 📝 Create Subscription
POST /api/subscriptions
Creates a new subscription with the provided details.

### Delete Subscription
DELETE /api/subscriptions/{id}
Deletes an expired or cancelled subscription.

## 🧪 Testing

Run the tests using:
./mvnw test

## Configuration

The application can be configured through application.properties. Key configurations include:

spring.datasource.url=jdbc:postgresql://localhost:5433/examen_java
spring.jpa.hibernate.ddl-auto=update
spring.security.user.name=admin
spring.security.user.password=admin


## ⚠️ Error Handling

The application includes comprehensive error handling for:
- 🔄 Duplicate subscriptions
- ❌ Invalid subscription states
- 🔍 Not found subscriptions
- ⚡ Validation errors

## 🔒 Security

Basic security configuration is implemented with Spring Security. Currently configured to allow all requests for development purposes.

## 🖥️ Frontend Interface

The frontend provides a simple interface with two main functions:
- ➕ Add new subscriptions
- ➖ Delete existing subscriptions

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. 🔀 Fork the repository
2. 🌿 Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. 💾 Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. 🚀 Push to the branch (`git push origin feature/AmazingFeature`)
5. 🔍 Open a Pull Request
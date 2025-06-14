# Redis Spring Boot Demo

This project demonstrates Redis caching implementation in a Spring Boot application with a product catalog example.

## Prerequisites

- Java 17 or higher
- Maven
- Docker and Docker Compose
- RedisInsight (Optional - for Redis GUI)

## Project Setup

### 1. Start Redis using Docker

```bash
# Start Redis container
./redis-manage.sh start

# Check Redis status
./redis-manage.sh status

# Connect to Redis CLI
./redis-manage.sh cli

# View Redis logs
./redis-manage.sh logs

# Stop Redis
./redis-manage.sh stop
```

### 2. Start the Spring Boot Application

```bash
# Build and run the application
mvn spring-boot:run
```

### 3. Test the Application

```bash
# Get a product (First call - cache miss)
curl http://localhost:8080/api/products/P001

# Get the same product (Second call - cache hit)
curl http://localhost:8080/api/products/P001

# Update a product (evicts cache)
curl -X PUT http://localhost:8080/api/products/P001 \
  -H "Content-Type: application/json" \
  -d '{"name":"iPhone 15 Pro","price":1099.99}'

# Delete a product
curl -X DELETE http://localhost:8080/api/products/P001
```

### 4. Redis GUI Setup (Optional)

1. Download RedisInsight from https://redis.com/redis-enterprise/redis-insight/
2. Install and open RedisInsight
3. Add new database connection:
   - Host: localhost
   - Port: 6379
   - Name: Local Redis

## Redis CLI Commands

```bash
# Connect to Redis CLI
docker exec -it redis-cache redis-cli

# Basic Redis commands
PING                    # Check if Redis is responding
KEYS *                 # List all keys
GET products::P001     # Get cached product
TTL products::P001     # Check time-to-live of key
MONITOR                # Watch Redis commands in real-time
INFO                   # Get Redis server information
```

## Project Structure

```
src/main/java/com/example/redisdemo/
├── RedisDemoApplication.java
├── config/
│   └── RedisConfig.java
├── controller/
│   └── ProductController.java
├── model/
│   └── Product.java
└── service/
    └── ProductService.java
```

## Cache Configuration

The application uses Spring's caching abstraction with Redis:

- Cache name: "products"
- Key format: "products::{id}"
- TTL: 1 hour (3600000 milliseconds)
- Serialization: JSON

## Docker Compose

The Redis container is configured with:
- Port: 6379
- Persistence: AOF enabled
- Health check: Every 10 seconds
- Volume: redis-data for persistence

## Troubleshooting

1. If Redis connection fails:
   ```bash
   # Check Redis container status
   ./redis-manage.sh status
   
   # Check Redis logs
   ./redis-manage.sh logs
   ```

2. If application fails to start:
   ```bash
   # Check if Redis is running
   docker ps | grep redis-cache
   
   # Check application logs
   tail -f logs/application.log
   ``` 
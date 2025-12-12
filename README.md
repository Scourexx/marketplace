# MarketPlace - Интернет-дүкен платформасы

## Жоба сипаттамасы

MarketPlace - бұл Spring Boot негізінде құрылған заманауи интернет-дүкен платформасы. Жоба RESTful API, PostgreSQL мәліметтер базасы, Spring Security аутентификациясы және Docker контейнерлеу технологияларын қолданады.

## Технологиялық стек

- **Java 21** - бағдарламалау тілі
- **Spring Boot 3.4.0** - негізгі фреймворк
- **Spring Security** - аутентификация және авторизация
- **PostgreSQL 14.6** - реляциялық мәліметтер базасы
- **Flyway** - мәліметтер базасы миграцияларын басқару
- **Docker & Docker Compose** - контейнерлеу
- **MapStruct** - DTO маппинг
- **Lombok** - код генерациясы
- **JUnit 5 & Mockito** - тестілеу

## Жоба құрылымы

```
MarketPlace/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── satbayev/kz/marketplace/
│   │   │       ├── config/          # Конфигурациялар
│   │   │       ├── controller/      # REST және JSP контроллерлер
│   │   │       ├── domain/
│   │   │       │   ├── entity/      # JPA сущностилер
│   │   │       │   └── repository/  # JPA репозиторийлер
│   │   │       ├── dto/             # Data Transfer Objects
│   │   │       ├── mapper/          # MapStruct мапперлер
│   │   │       ├── service/         # Бизнес-логика
│   │   │       └── util/            # Утилиталар
│   │   └── resources/
│   │       ├── db/migration/        # Flyway миграциялар
│   │       ├── templates/           # Thymeleaf шаблондары
│   │       └── application.yaml     # Конфигурация
│   └── test/                        # Тесттер
├── docker-compose.yaml              # Docker Compose конфигурациясы
├── Dockerfile                       # Backend контейнер конфигурациясы
└── pom.xml                          # Maven dependencies
```

## Мәліметтер базасы схемасы

### Негізгі сущностилер:

1. **UserAccount** - пайдаланушы аккаунты (username, password, ролдер)
2. **Customer** - тұтынушы ақпараты (firstName, lastName, email)
3. **Role** - ролдер (ADMIN, USER)
4. **Authority** - рұқсаттар (ROLE_ADMIN, ROLE_USER)
5. **Product** - өнімдер (name, price, category, stock, description)
6. **Basket** - себет (customer, product, quantity)
7. **Message** - хабарламалар

### ER-диаграмма байланыстары:

- `UserAccount` 1:1 `Customer`
- `UserAccount` 1:N `Role`
- `Role` 1:N `Authority`
- `Customer` 1:N `Basket`
- `Product` 1:N `Basket`
- `UserAccount` 1:N `Message`

## Алғышарттар

Жобаны іске қосу үшін қажетті:

- **Java 21** (немесе жоғары)
- **Maven 3.6+**
- **Docker** және **Docker Compose**
- **PostgreSQL 14+** (немесе Docker арқылы)

## Орнату және іске қосу

### 1-әдіс: Docker Compose арқылы (Ұсынылады)

```bash
# Жобаны клонировать ету
git clone <repository-url>
cd MarketPlace

# Maven арқылы жобаны құрастыру
mvn clean package -DskipTests

# Docker Compose арқылы іске қосу
docker-compose up -d

# Логтарды көру
docker-compose logs -f backend
```

Жоба мына адресте іске қосылады: `http://localhost:8081`

### 2-әдіс: Локальды іске қосу

```bash
# PostgreSQL мәліметтер базасын іске қосу (Docker арқылы)
docker-compose up -d postgres

# Жобаны құрастыру
mvn clean package

# Жобаны іске қосу
java -jar target/MarketPlace-0.0.1-SNAPSHOT.jar
```

### Конфигурация

`application.yaml` файлында мәліметтер базасы параметрлерін өзгертуге болады:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres
```

## API Эндпоинттер

### Продукттер API (`/api/products`)

| Метод | Эндпоинт | Сипаттама |
|-------|----------|-----------|
| GET | `/api/products` | Барлық өнімдерді алу |
| GET | `/api/products/{id}` | Өнімді ID бойынша алу |
| POST | `/api/products` | Жаңа өнім қосу |
| PUT | `/api/products/{id}` | Өнімді жаңарту |
| DELETE | `/api/products/{id}` | Өнімді жою |

### Пайдаланушылар API (`/api/users`)

| Метод | Эндпоинт | Сипаттама |
|-------|----------|-----------|
| GET | `/api/users` | Барлық пайдаланушыларды алу |
| GET | `/api/users/{id}` | Пайдаланушыны ID бойынша алу |
| POST | `/api/users` | Жаңа пайдаланушы қосу |
| PUT | `/api/users/{id}` | Пайдаланушыны жаңарту |
| DELETE | `/api/users/{id}` | Пайдаланушыны жою |
| PATCH | `/api/users/{id}/password` | Парольді өзгерту |
| PATCH | `/api/users/{id}/role-admin` | Рөлді админге өзгерту |

### Себет API (`/basket`)

| Метод | Эндпоинт | Сипаттама |
|-------|----------|-----------|
| GET | `/basket/{customerId}` | Тұтынушының себетін алу |
| POST | `/basket/add?customerId={id}&productId={id}&quantity={qty}` | Себетке өнім қосу |
| DELETE | `/basket/remove?customerId={id}&productId={id}` | Себеттен өнім жою |

## API тестілеу мысалдары

### cURL мысалдары

#### 1. Барлық өнімдерді алу
```bash
curl -X GET http://localhost:8081/api/products
```

#### 2. Өнімді ID бойынша алу
```bash
curl -X GET http://localhost:8081/api/products/1
```

#### 3. Жаңа өнім қосу
```bash
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Жаңа өнім",
    "price": 50000.0,
    "category": "Электроника",
    "stock": 10,
    "description": "Сипаттама",
    "status": true
  }'
```

#### 4. Өнімді жаңарту
```bash
curl -X PUT http://localhost:8081/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Жаңартылған өнім",
    "price": 55000.0,
    "category": "Электроника",
    "stock": 15,
    "description": "Жаңартылған сипаттама",
    "status": true
  }'
```

#### 5. Өнімді жою
```bash
curl -X DELETE http://localhost:8081/api/products/1
```

#### 6. Барлық пайдаланушыларды алу
```bash
curl -X GET http://localhost:8081/api/users
```

#### 7. Себетке өнім қосу
```bash
curl -X POST "http://localhost:8081/basket/add?customerId=1&productId=1&quantity=2"
```

#### 8. Тұтынушының себетін алу
```bash
curl -X GET http://localhost:8081/basket/1
```

## Тестілеу

### Барлық тесттерді іске қосу

```bash
mvn test
```

### Нақты тест класын іске қосу

```bash
mvn test -Dtest=ProductControllerTest
```

### Тесттік конфигурация

Тесттер үшін H2 in-memory базасы қолданылады (`application-test.yaml`).

## Flyway миграциялар

Flyway мәліметтер базасы схемасын басқару үшін қолданылады. Барлық миграциялар `src/main/resources/db/migration/` директориясында орналасқан.

### Миграциялар тізімі:

- `V1__create_authority_table.sql` - Role және Authority кестелерін құру
- `V2__create_role_table.sql` - Резерв (V1-де құрылған)
- `V3__create_customer_table.sql` - Customer кестесін құру
- `V4__create_user_account_table.sql` - UserAccount кестесін құру
- `V5__create_product_table.sql` - Product кестесін құру
- `V6__create_basket_table.sql` - Basket кестесін құру
- `V7__create_messages_table.sql` - Messages кестесін құру

### Миграцияларды қолмен іске қосу

```bash
mvn flyway:migrate
```

## Docker командалары

### Контейнерлерді іске қосу
```bash
docker-compose up -d
```

### Контейнерлерді тоқтату
```bash
docker-compose down
```

### Логтарды көру
```bash
docker-compose logs -f backend
docker-compose logs -f postgres
```

### Контейнерлерді қайта құру
```bash
docker-compose up -d --build
```

### Volume-дарды жою (мәліметтер базасы деректерін жою)
```bash
docker-compose down -v
```

## Безопасность

Жоба Spring Security қолданады:

- **Аутентификация**: Username/Password
- **Авторизация**: Role-based (ADMIN, USER)
- **Парольдер**: BCrypt хэштелген

### Ролдер:

- **ADMIN** - толық қолжетімділік
- **USER** - шектеулі қолжетімділік

## Логтау

Жоба Spring Boot стандартты логтау механизмін қолданады. Логтар консольге шығарылады.

## Мәселелерді шешу

### Мәліметтер базасына қосылу мәселесі

1. PostgreSQL контейнерінің іске қосылғанын тексеру:
```bash
docker-compose ps
```

2. Мәліметтер базасы параметрлерін тексеру (`application.yaml`)

### Порт бос емес

Егер 8081 порты бос емес болса, `application.yaml` файлында портты өзгертіңіз:
```yaml
server:
  port: 8082  # Басқа порт
```

### Flyway миграция мәселелері

Миграцияларды қолмен бастапқылау:
```bash
mvn flyway:baseline
mvn flyway:migrate
```


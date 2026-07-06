# Currency Converter — Spring Boot

A small Spring Boot service that converts amounts between currencies using live exchange rates, built as a refresher project to practice core Spring Boot concepts — REST APIs, caching, JPA, and clean layered architecture.

## Overview

The service exposes a simple REST endpoint that takes a source currency, target currency, and amount, then returns the converted value. Exchange rates are fetched from an external API and cached to avoid unnecessary calls, and every conversion is logged to a local database for history tracking.

## Features

- **REST API** for currency conversion via Spring's `RestClient`
- **Caching** — exchange rates are cached (Caffeine) for 1 hour, so repeated conversions between the same currency pair don't trigger a fresh API call every time
- **Conversion history** — every conversion request is persisted to an H2 database
- **Global exception handling** — centralized handling for validation errors and upstream API failures
- **Actuator support** — exposes cache stats/metrics via Spring Boot Actuator
- **Type-safe currency codes** — currencies modeled as an enum (`CurrencyCode`) instead of raw strings, with validation baked in

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Web (`RestClient`)
- Spring Data JPA + H2 (conversion history)
- Spring Cache + Caffeine (rate caching)
- Spring Boot Actuator
- Lombok
- Maven

## Architecture

```
Controller  →  Service  →  Client (RestClient)  →  External API (freecurrencyapi.com)
                  │
                  └──→  Repository (H2) — conversion history
```

- `CurrencyExchangeController` — exposes the `/api/convertCurrency` endpoint
- `CurrencyConvertService` / `Impl` — core conversion logic, uses `BigDecimal` for precision
- `CurrencyClient` / `Impl` — talks to the external exchange rate API
- `RecordService` / `Impl` + `ConversionRecordRepository` — logs each conversion to H2
- `CacheConfig` — configures the Caffeine cache manager for exchange rates
- `GlobalExceptionHandler` — maps `BadRequestException` / `TechnicalException` to proper HTTP responses
- `CurrencyCode` — enum of supported currency codes with helper lookups

## API

### Convert Currency

```
GET /api/convertCurrency?fromCurrency=USD&toCurrency=INR&amount=100
```

**Query Parameters**

| Param          | Type       | Description                          |
|----------------|------------|--------------------------------------|
| `fromCurrency` | String     | Source currency code (e.g. `USD`)    |
| `toCurrency`   | String     | Target currency code (e.g. `INR`)    |
| `amount`       | BigDecimal | Amount to convert                    |

**Sample Response**

```json
{
  "currency": "INR",
  "amount": 8345.67
}
```

## Getting Started

### Prerequisites

- Java 17+
- Maven (or use the included `mvnw` wrapper)
- A free API key from [freecurrencyapi.com](https://freecurrencyapi.com/)

### Setup

1. Clone the repo
   ```bash
   git clone https://github.com/pranav-027/currency-converter-spring-boot.git
   cd currency-converter-spring-boot
   ```

2. Set your API key as an environment variable (used in `application.properties` as `${API_KEY}`)
   ```bash
   export API_KEY=your_freecurrencyapi_key
   ```

3. Run the app
   ```bash
   ./mvnw spring-boot:run
   ```

4. Hit the endpoint
   ```bash
   curl "http://localhost:8080/api/convertCurrency?fromCurrency=USD&toCurrency=INR&amount=100"
   ```

### H2 Console

The H2 console is enabled for inspecting conversion history:
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/currency-data
```

### Cache Stats

With Actuator enabled, cache metrics are available at:
```
http://localhost:8080/actuator/caches
```

## Roadmap

- [x] Global exception handling
- [x] Conversion history persisted to a database
- [x] Caching for exchange rates to reduce API calls
- [ ] Pagination/filtering for conversion history
- [ ] Support for historical exchange rates (not just latest)
- [ ] Dockerize the application

## Motivation

This project started as a refresher on Spring Boot fundamentals — a chance to revisit layered architecture, caching strategies, and JPA while building something small but complete end-to-end.

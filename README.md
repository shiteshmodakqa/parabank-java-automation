# ParaBank Selenium WebDriver + Java Automation Framework

A scalable hybrid automation framework for the ParaBank assignment using **Java 17, Selenium WebDriver 4, TestNG, Maven and Java HttpClient**.

## Architecture

- Strict Page Object Model for UI simulation
- Service/API layer for deterministic setup and backend validation
- Dynamic usernames using timestamp + UUID
- Explicit condition-based waits; no static sleeps
- Integer-cent based currency calculations with `BigDecimal`
- Custom TestNG HTML reporter with glassmorphism UI
- API-only Scenario C
- CI-oriented global-state decision documented in `DECISIONS.md`

## Prerequisites

- Java 17+
- Maven 3.9+
- Internet access to the ParaBank sandbox
- Chrome/Chromium

WebDriverManager automatically provisions the ChromeDriver binary.

## Install

```bash
mvn clean install -DskipTests
```

## Run headless

```bash
mvn clean test -Dheadless=true
```

## Run headed

```bash
mvn clean test -Dheadless=false
```

## Run against another ParaBank URL

```bash
mvn clean test -DbaseUrl=https://parabank.parasoft.com/parabank -Dheadless=true
```

## Custom report

After execution:

```text
reports/index.html
```

Open that file in a browser. The dashboard contains total, passed, failed and skipped cards plus test-level results.

## Scenarios

### Scenario A

1. API-level `cleanDB` through ParaBank's SOAP service.
2. Open the administration page and set Loan Provider to `Web Service`.
3. Generate a unique user.
4. Register through the UI.
5. Open a Checking account.
6. Apply for a loan using the dynamically populated account dropdown.
7. Validate approval and loan account number.
8. Validate the loan deposit on the resulting account activity page.

### Scenario B

1. Reset database.
2. Create a dynamic user and checking account.
3. Transfer `$150.00`, `$25.50` and `$8.99`.
4. Navigate to Find Transactions.
5. Extract HTML table rows.
6. Parse currency strings into integer cents.
7. Compare the total debit amount with the mathematically expected transfer total.

Expected total:

```text
$184.49
```

### Scenario C

No browser is started. Java `HttpClient` performs the registration HTTP request and ParaBank REST calls, then validates customer/account/transaction response shapes against the expected TypeScript-equivalent schema represented by explicit field/type assertions in Java.

## Important sandbox limitation

ParaBank's database and application settings are global. A database reset can affect another CI worker. Therefore do not run destructive suites concurrently against the same public sandbox. Use a CI global lock or isolated ParaBank instance. See `DECISIONS.md`.

## Useful ParaBank endpoints

- Admin: `/parabank/admin.htm`
- REST base: `/parabank/services/bank`
- SOAP base: `/parabank/services/ParaBank`
- Transactions: `/services/bank/accounts/{accountId}/transactions`
- Customer accounts: `/services/bank/customers/{customerId}/accounts`

## Project structure

```text
src/main/java/com/parabank/
  api/          API/service clients
  config/       runtime configuration
  driver/       WebDriver lifecycle
  models/       domain records
  pages/        Page Objects
  utils/        waits, data and currency utilities

src/test/java/com/parabank/
  tests/        Scenario A/B/C
  listeners/    custom TestNG reporter

DECISIONS.md
README.md
pom.xml
testng.xml
```

# Architecture Decision Record

## 1. State Contention

ParaBank is a shared public sandbox. A unique username prevents ordinary identity collisions, but it does **not** solve collisions caused by global operations such as `cleanDB` or changing the global Loan Provider.

This framework therefore makes destructive global setup an exclusive operation. In CI, only one worker should execute a suite containing database reset/global configuration at a time. The recommended implementation is a CI-level lock (for example, a Jenkins lockable resource or a GitHub Actions concurrency group) named `parabank-global-sandbox`.

Within a permitted run, all users are generated dynamically from a timestamp plus UUID fragment. No test depends on `john/demo` or another shared account.

For parallel execution against a truly shared environment, the framework should be pointed at isolated ParaBank instances rather than attempting to make a global reset safe through application-level synchronization.

## 2. Currency Handling

Java `double` is not used for monetary aggregation. Selenium extracts strings such as `$25.50`, and `CurrencyUtils.cents()` removes currency symbols/thousands separators and converts the amount to an integer number of cents using `BigDecimal`.

Example:

```java
long cents = new BigDecimal("25.50")
    .movePointRight(2)
    .setScale(0, RoundingMode.HALF_UP)
    .longValueExact();
```

The expected total and actual HTML-table total are both compared as integer cents. This avoids binary floating-point errors such as `0.1 + 0.2 != 0.3`.

## 3. Design Pattern / API vs UI Boundary

The framework uses a strict Page Object Model for user simulation and a service layer for API operations.

**API/service layer:**
- global database cleanup
- REST login/customer/account/transaction queries
- headless deposits
- future teardown and deterministic data verification

**UI/Page Object layer:**
- registration simulation
- account creation
- loan application
- transfers
- Find Transactions table extraction
- assertions that represent what a real user can see

The principle is: use APIs for deterministic setup, state preparation and backend verification; use Selenium for behaviors that specifically validate the web application's UI workflow.

## 4. Synchronization

No `Thread.sleep()` or Selenium fixed-duration waits are used. Dynamic account dropdowns use explicit condition polling until the required account ID appears. This makes synchronization dependent on application state rather than elapsed time.

## 5. Reporting

A custom TestNG reporter is used instead of Allure or the default HTML report. It produces `reports/index.html` with translucent cards, `backdrop-filter: blur(...)`, layered backgrounds, and `#F48031` as the required primary accent for headings, active tabs and pass indicators.

# SDET Portfolio Demo — Selenium Java + TestNG + RestAssured

> **Automation framework demonstrating senior SDET skills.**
> Built by Pratiksha Khade | 6+ years QA Automation | Fintech & Banking Domain

[![CI Pipeline](https://github.com/YOUR_USERNAME/sdet-portfolio-demo/actions/workflows/ci.yml/badge.svg)](https://github.com/YOUR_USERNAME/sdet-portfolio-demo/actions/workflows/ci.yml)

---

## 🏗️ Framework Architecture

```
sdet-portfolio-demo/
├── src/test/java/com/demo/
│   ├── base/           → BasePage (POM) + BaseTest + DriverFactory
│   ├── config/         → ConfigManager (Singleton)
│   ├── pages/          → LoginPage + TextBoxPage (Page Objects)
│   └── tests/          → LoginTest + FormTest + APITest
├── .github/workflows/  → GitHub Actions CI pipeline
└── pom.xml             → Maven build config
```

## 🎯 Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 17 | Core language |
| Selenium WebDriver | 4.18.1 | Browser automation |
| TestNG | 7.9.0 | Test framework |
| RestAssured | 5.4.0 | API testing |
| Maven | 3.x | Build tool |
| WebDriverManager | 5.7.0 | Auto driver management |
| Allure | 2.25.0 | Test reporting |
| GitHub Actions | — | CI/CD pipeline |

## 🏛️ Design Patterns Used

| Pattern | Where | Why |
|---------|-------|-----|
| **Page Object Model** | All page classes | Separation of concerns, reusability |
| **Factory Method** | DriverFactory.java | Hide browser creation complexity |
| **Singleton** | ConfigManager.java | Single config instance across threads |
| **Fluent Interface** | Page methods | Readable, chainable test code |
| **ThreadLocal** | DriverFactory.java | Parallel test thread safety |

## 🚀 Run Locally

### Prerequisites
- Java 17+
- Maven 3.x
- Chrome browser

### Commands

```bash
# Clone the repo
git clone https://github.com/YOUR_USERNAME/sdet-portfolio-demo.git
cd sdet-portfolio-demo

# Run smoke tests (headless Chrome)
mvn clean test -Dheadless=true -P smoke

# Run all tests
mvn clean test -Dheadless=true

# Run with Firefox
mvn clean test -Dbrowser=firefox -Dheadless=true

# Generate Allure report
mvn allure:report
# Open: target/site/allure-maven-plugin/index.html
```

## 🔄 CI/CD Pipeline

GitHub Actions runs automatically on:
- Every push to `main` / `develop`
- Every Pull Request
- Nightly at 1:30 AM IST (8 PM UTC)
- Manual trigger from GitHub UI

**Pipeline stages:**
1. Checkout code
2. Setup Java 17 (Maven cache for faster builds)
3. Run tests headless Chrome
4. Generate Allure report
5. Upload report as artifact (downloadable from Actions tab)

## 📊 Test Coverage

| Suite | Tests | Description |
|-------|-------|-------------|
| LoginTest | 4 tests | Login page load, URL, title validation |
| FormTest | 4 tests | Form fill, submit, data-driven with @DataProvider |
| APITest | 6 tests | GET/POST/PUT, 404 negative, response time |

## 🌐 Test Sites Used

- **UI Tests:** [demoqa.com](https://demoqa.com) — free public demo site
- **API Tests:** [reqres.in](https://reqres.in) — free public REST API

> **Note:** This is a portfolio project demonstrating SDET skills and framework design.
> It is not affiliated with or derived from any proprietary codebase.

---

**Connect:** [LinkedIn](https://linkedin.com/in/pratiksha-khade) | [GitHub](https://github.com/YOUR_USERNAME)

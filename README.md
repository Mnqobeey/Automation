# SauceDemo Selenium Automation Framework

BDD UI automation for the SauceDemo checkout journey using Selenium WebDriver, Cucumber, Maven, and Java.

## Scope

This framework validates the core SauceDemo purchase flow:

1. Open the SauceDemo login page.
2. Sign in with the public demo user.
3. Add two products to the cart.
4. Validate the cart contents.
5. Complete checkout.
6. Confirm the order success message.

## Stack

- Java 17
- Maven
- Selenium WebDriver
- Cucumber
- JUnit
- WebDriverManager

## Project Structure

```text
src/test/java/com/mnqobeey/saucedemo
|-- config
|-- core
|-- hooks
|-- pages
|-- runners
`-- steps

src/test/resources
|-- config
|-- features
`-- log4j2.properties
```

## Configuration

Runtime settings live in `src/test/resources/config/test.properties`.

Common overrides:

- `base.url`
- `browser`
- `headless`
- `timeout.seconds`
- `saucedemo.username`
- `saucedemo.password`

Example:

```bash
mvn test -Dheadless=true
```

The default credentials are SauceDemo public test credentials, not private account credentials.

## Run Locally

```bash
mvn test
```

Run in headless mode:

```bash
mvn test -Dheadless=true
```

## Reports

Test reports are generated under `target/`, including:

- Surefire XML reports
- Cucumber HTML report
- Cucumber JSON report

## Notes

- Chrome is the default browser.
- WebDriverManager provisions the browser driver automatically.
- Generated files, IDE metadata, drivers, logs, and screenshots are ignored by Git.

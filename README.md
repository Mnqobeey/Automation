# CV Intelligence Automation Framework

UI automation framework for the `cv_intelligence` system using Selenium, Cucumber, Maven, and Java 17.

## Scope

This repository is dedicated to browser-based regression and smoke coverage for the CV Intelligence application. It is structured around the real product flow:

1. Load the application
2. Upload a CV file or paste CV text
3. Review extracted content
4. Complete review when the profile is ready
5. Export the final DOCX profile

## Stack

- Java 17
- Maven
- Selenium WebDriver
- Cucumber
- JUnit
- WebDriverManager

## Project structure

```text
src/test/java/com/cestasoft/cvintelligence
├─ assertions
├─ config
├─ hooks
├─ pages
├─ runners
├─ steps
└─ utils

src/test/resources
├─ config
├─ features
└─ testdata
```

## Scenarios included

- Smoke: application loads successfully
- Upload: structured CV text file can be analysed
- Upload: structured CV text can be pasted and analysed
- Review: review completion becomes available for valid input
- Export: DOCX export succeeds after review completion

## Configuration

Runtime configuration lives in `src/test/resources/config/test.properties`.

Supported overrides:

- `BASE_URL`
- `BROWSER`
- `HEADLESS`
- `DEFAULT_TIMEOUT_SECONDS`
- `DOWNLOAD_DIR`

## Run locally

```bash
mvn test
```

To point the suite at another environment:

```bash
mvn test -DBASE_URL=http://127.0.0.1:8000
```

## Notes

- Chrome is the default browser.
- The framework uses WebDriverManager to provision the driver.
- Download assertions are designed for generated `.docx` exports.

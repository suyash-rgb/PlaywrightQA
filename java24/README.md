# Spring Boot + Playwright

A Java 21 Spring Boot web application with a Playwright for Java browser test.

## Requirements

- JDK 21 or newer
- Maven 3.6.3 or newer

## Run the application

```powershell
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080).

Spring Boot DevTools is enabled for development. When you change Java code, trigger a project build/compile in your IDE (or run `mvn compile` in another terminal); DevTools detects the updated class files and restarts the application. In VS Code, enable Java project auto-build so this happens automatically after saving.

## Install the Playwright browser

Install Chromium once before running the browser test:

```powershell
mvn exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install chromium"
```

## Run tests

```powershell
mvn test
```

## Maven, startup, and Playwright command reference

Run these commands from the project root in PowerShell.

### Maven

```powershell
mvn -version
mvn clean
mvn compile
mvn test
mvn package
mvn clean verify
```

### Start the Spring Boot application

Run the application directly with Maven:

```powershell
mvn spring-boot:run
```

Or build and run the packaged JAR:

```powershell
mvn clean package
java -jar target\springboot-playwright-0.0.1-SNAPSHOT.jar
```

The application is available at [http://localhost:8080](http://localhost:8080). Stop it with `Ctrl+C`.

### Playwright

Install the Chromium browser used by the browser test. Playwright is also available on the runtime classpath:

```powershell
mvn exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install chromium"
```

Run the Playwright browser test together with the other tests:

```powershell
mvn test
```

### Open Google through the browser endpoint

Start the application, then request the endpoint:

```powershell
Invoke-RestMethod http://localhost:8080/browser/google
```

The endpoint launches a fresh headless Playwright Chromium browser, visits Google, returns the page title and URL, and closes the browser.

To open Google in a visible Chromium window instead, call:

```powershell
Invoke-RestMethod http://localhost:8080/browser/google/headful
```

The headful browser stays open after the endpoint returns. It opens on the machine running Spring Boot, requires an interactive desktop, and is closed when the application stops or when this endpoint is called again.

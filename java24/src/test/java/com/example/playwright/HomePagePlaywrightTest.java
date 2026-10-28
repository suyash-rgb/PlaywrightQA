package com.example.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomePagePlaywrightTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    void homePageLoadsInChromium() {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch(
                     new BrowserType.LaunchOptions().setHeadless(true))) {
            Page page = browser.newPage();
            page.navigate("http://127.0.0.1:" + port);

            assertEquals("Spring Boot + Playwright", page.title());
            assertEquals("Spring Boot is running", page.locator("h1").textContent());

            page.navigate("http://127.0.0.1:" + port + "/greeting?name=Suyash");
            assertEquals("Hello Suyash", page.locator("body").textContent());
        }
    }
}

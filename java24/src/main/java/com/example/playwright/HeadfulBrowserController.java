package com.example.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeadfulBrowserController {

    private Playwright headfulPlaywright;
    private Browser headfulBrowser;

    @GetMapping("/browser/google")
    public GooglePage openGoogle() {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch(
                     new BrowserType.LaunchOptions().setHeadless(true))) {
            Page page = browser.newPage();
            page.navigate("https://www.google.com");
            return new GooglePage(page.title(), page.url()); //opens google homepage and returns the title an url
        }
    }

    @GetMapping("/browser/google/headful")
    public synchronized GooglePage openGoogleHeadful() {
        closeHeadfulSession(); //will close the open browser instance if endpoint is hit again

        Playwright playwright = Playwright.create();
        Browser browser = null;
        boolean sessionStarted = false;
        try {
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("https://www.google.com");

            headfulPlaywright = playwright;
            headfulBrowser = browser;
            sessionStarted = true;
            return new GooglePage(page.title(), page.url());
        } finally {
            if (!sessionStarted) {
                if (browser != null) {
                    try {
                        browser.close();
                    } finally {
                        playwright.close();
                    }
                } else {
                    playwright.close();
                }
            }
        }
    }

    @PreDestroy
    public synchronized void closeHeadfulSession() {
        Browser browser = headfulBrowser;
        Playwright playwright = headfulPlaywright;
        headfulBrowser = null;
        headfulPlaywright = null;

        if (browser != null) {
            try {
                browser.close();
            } finally {
                if (playwright != null) {
                    playwright.close();
                }
            }
        } else if (playwright != null) {
            playwright.close();
        }
    }

    public record GooglePage(String title, String url) {
    }
}

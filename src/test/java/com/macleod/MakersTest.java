package com.macleod;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class MakersTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @Test
    void shouldPrintPageTitle() {
        page.navigate("https://makers.tech");
        System.out.println(page.title());
    }
    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}
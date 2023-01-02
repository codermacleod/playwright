package com.macleod;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public class ToDoTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator reactButton;

    @Test
    void shouldPrintPageTitle() {
        page.navigate("https://todomvc.com");
        System.out.println("First page" + page.title());
        reactButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setExact(true).setName("React"));
        reactButton.click();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("mvc.png")));
        System.out.println("Next page" + page.title());
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

    // in TodoTest.java


}

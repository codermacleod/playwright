package com.macleod;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public class SatrianiTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator merchButton, loginButton;
    @Test
    void shouldPrintButtonTitle() {
        page.navigate("http://satriani.com/");
        System.out.println(page.title());
        merchButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Gear/Merch"));
        // Get the text content of the specified element
        System.out.println(merchButton.textContent());
    }
    @Test
    void shouldSignIn(){
        page.navigate("http://satriani.com/");
        loginButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log in/Register"));
        loginButton.click();
        page.locator("input[name=\"nick\"]").click();
        page.locator("input[name=\"nick\"]").fill("codermacleod");
        page.locator("input[name=\"nick\"]").press("Tab");
        page.locator("input[name=\"password\"]").fill("gatetothestars");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log In")).click();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("satriani.png")));
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

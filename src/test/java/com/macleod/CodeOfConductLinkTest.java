package com.macleod;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CodeOfConductLinkTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator codeOfConduct;

    @Test
    void shouldCheckCodeOfConductLinkIsVisibleInFooter(){
        page.setViewportSize(1024, 1366);
        page.navigate("https://makers.tech/");
        codeOfConduct = page.getByText("Code of Conduct");
        assertThat(codeOfConduct).isVisible();
    }
    @Test
    void shouldCheckCodeOfConductLinkIsNotVisibleInFooter(){
        page.setViewportSize(390, 840);
        page.navigate("https://makers.tech/");
        codeOfConduct = page.getByText("Code of Conduct");
        assertThat(codeOfConduct).not().isVisible();
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

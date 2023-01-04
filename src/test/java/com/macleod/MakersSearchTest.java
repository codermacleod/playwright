package com.macleod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MakersSearchTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
//    Locator

    // in MakersSearchTest.java
    @Test
    void shouldFindSearchResultsForJava() {
        MakersSearchPage searchPage = new MakersSearchPage(page);
        searchPage.navigate();
        searchPage.searchFor("java");
        assertThat(searchPage.getSearchResultsHeading()).containsText("Results for java");
    }

    @Test
    void shouldNotFindSearchResultsForBadger() {
        MakersSearchPage searchPage = new MakersSearchPage(page);
        searchPage.navigate();
        searchPage.searchFor("badger");
        assertThat(searchPage.getSearchResultsHeading()).containsText("No results for badger");
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

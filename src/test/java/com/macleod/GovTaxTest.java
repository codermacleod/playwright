package com.macleod;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GovTaxTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    @Test
    void shouldCheckCarTax(){
        var searchPage = new GovSearchPage(page);
        //Start Website:
        searchPage.navigate();
        page.pause();

        //Accept or Reject Cookies:
        searchPage.acceptRejectCookies("Reject").click();

        //1. Search for "car tax" and on next page...
        //2. ...Expect Search Results Heading to be 'Search all content'.
        searchPage.searchFor("car tax");
        assertThat(searchPage.getSearchResultsHeading()).hasText("Search all content");

        
        //1. Click on link and on next page...
        //2. ...Expect Heading to be 'Tax your vehicle without a V11 reminder'.
        searchPage.getRequiredLink().click();
        assertThat(searchPage.getSearchResultsHeading()).hasText("Tax your vehicle without a V11 reminder");
    }
    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
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

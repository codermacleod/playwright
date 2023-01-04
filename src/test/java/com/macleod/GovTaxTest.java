package com.macleod;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GovTaxTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator searchText, taxVehicle;

    @Test
    void shouldCheckCarTax(){
        var searchPage = new GovSearchPage(page);
        //Start Website:
        searchPage.navigate();
        page.pause();
        //Accept or Reject Cookies:
        searchPage.acceptRejectCookies("reject").click();

        //
        searchPage.searchFor("car tax");
        assertThat(searchPage.getSearchResultsHeading()).hasText("Search all content");

        //Search:
        searchText = page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search"));
        searchText.click();
        searchText.fill("car tax");
        searchText.press("Enter");

        // Next page:
        taxVehicle = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Tax your vehicle without a V11 reminder"));
        taxVehicle.click();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("afterCookieReject.png")));
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

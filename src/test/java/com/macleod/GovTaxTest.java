package com.macleod;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public class GovTaxTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator cookies, searchText, taxVehicle;

    @Test
    void shouldCheckCarTax(){
        //Start Website:
        page.navigate("https://www.gov.uk/");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("beforeCookieReject.png")));
        
        //Reject Cookies:
        cookies = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject additional cookies"));
        if (cookies != null){
            cookies.click();
            System.out.println("rejected");
        }
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

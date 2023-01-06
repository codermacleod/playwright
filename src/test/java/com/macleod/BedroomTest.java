package com.macleod;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BedroomTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator shopByRoom, bedroom;

    @Test
    void browseToBedroom() {
        page.navigate("https://www.ikea.com/gb/en/");
        assertThat(page).hasURL("https://www.ikea.com/gb/en/");
        shopByRoom = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Shop by room"));
        shopByRoom.click();

        // Expect the aside (pull out menu) to be visible.
        assertThat(page.locator("aside")).isVisible();

        //If visible, click on Bedroom.
        bedroom = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bedroom"));
        bedroom.click();

        //Expect the Bedroom page to appear.
        assertThat(page).hasURL("https://www.ikea.com/gb/en/rooms/bedroom/");

        //***Note***
        // Only the 'aside'(pull-out menu) link, 'Bedroom' would be selected
        // since it has focus. In order to deviate and click outside the menu,
        // the overlay would need to be clicked first.
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


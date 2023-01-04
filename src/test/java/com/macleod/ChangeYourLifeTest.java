package com.macleod;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ChangeYourLifeTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator codeOfConduct, faqs, searchTextBox, noBadger;
    @Test
    void shouldCheckCodeOfConductAndFAQWorks(){
        //Setup
        page.navigate("https://makers.tech/");
//        page.pause();

        // Expect page to arrive at the URL
        assertThat(page).hasURL("https://makers.tech/");

        // Expect the title to contain "Change Your Life".
        assertThat(page).hasTitle(Pattern.compile("Change Your Life"));

        //1. Locate and hold the link (Code of Conduct), and
        //2. Expect there is a link on the page titled "Code of Conduct".
        codeOfConduct = page.locator("text=Code of Conduct");
        assertThat(codeOfConduct).hasText("Code of Conduct");

        //Click on Code of Conduct.
        codeOfConduct.click();

        //1. Expect page url to be https://makers.tech/code-of-conduct/.
        //2. Expect the page title includes the text "Code of Conduct".
        assertThat(page).hasURL("https://makers.tech/code-of-conduct/");
        assertThat(page).hasTitle(Pattern.compile("Code of Conduct"));

        // Go back to homepage.
        page.navigate("https://makers.tech/");
        assertThat(page).hasURL("https://makers.tech/");

        //1. Locate and hold the link (FAQs)
        //2. Expect there is a link on the page titled faqs.

        faqs = page.locator("a:has-text('FAQs')").first();
        assertThat(faqs).hasText("FAQs");

        //1. Click on FAQs.
        //2. Expect page to arrive at the URL
        faqs.click();
        assertThat(page).hasURL("https://faq.makers.tech/en/knowledge");

        //1. Locate and hold the search box with placeholder (Search for answers).
        //2. Click the empty field.
        //3. Enter the word (badger) into the search box.
        //4. Press enter.

        searchTextBox = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search for answers"));
        searchTextBox.click();
        searchTextBox.fill("badger");
        searchTextBox.press("Enter");

        //1. Locate and hold (No results for badger) heading.
        //2. Expect there is a heading on the page titled "No results for badger".

        noBadger = page.locator("text=No results for badger");
        assertThat(noBadger).hasText("No results for badger");
    }

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(2000));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
//        context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("src/test/resources/videos")));
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

}

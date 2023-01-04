package com.macleod;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ChangeYourLifeComparisonTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {

            // Browser
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setSlowMo(2000));
            // Context.
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("src/test/resources/videos")));;

            // Fields.
            Page page = context.newPage();
            Locator codeOfConduct, faqs, noBadger;


            // Setup.
            page.navigate("https://makers.tech/");
//            page.pause();
            // Expect page to arrive at the URL
            assertThat(page).hasURL("https://makers.tech/");

            // Expect the title to contain "Change Your Life".
            assertThat(page).hasTitle(Pattern.compile("Change Your Life"));

            //1. Expect page url to be https://makers.tech/code-of-conduct/.
            //2. Expect the page title includes the text "Code of Conduct".
            codeOfConduct = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Code of Conduct"));
            assertThat(codeOfConduct).hasText("Code of Conduct");

            //Click on Code of Conduct.
            codeOfConduct.click();

            // Go back to homepage and confirm it arrived.
            page.navigate("https://makers.tech/");
            assertThat(page).hasURL("https://makers.tech/");

            //1. Locate and hold the link (FAQs)
            //2. Expect there is a link on the page titled faqs.
            faqs = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("FAQs")).first();
            assertThat(faqs).hasText("FAQs");

            //Click on FAQs and confirm it arrived.
            faqs.click();
            assertThat(page).hasURL("https://faq.makers.tech/en/knowledge");

            //1. Locate and Click the empty field.
            //2. Enter the word (badger) into the search box.
            //3. Press enter.
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search for answers")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search for answers")).fill("badger");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search for answers")).press("Enter");

            noBadger = page.locator("text=No results for badger");
            assertThat(noBadger).hasText("No results for badger");

        }
    }
}


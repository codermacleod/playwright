package com.macleod;

import com.microsoft.playwright.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CrossBrowserTest {
    public static void main(String[] args) {

        // Create a new instance of Playwright
        Playwright playwright = Playwright.create();

        // Create an Array containing the browsers that
        // we want to test against:
        // * Chromium (Chrome / Edge)
        // * Firefox
        // * WebKit (Safari)
        List<BrowserType> browsersToTest = Arrays.asList(
                playwright.chromium(),
                playwright.firefox(),
                playwright.webkit());

        // Loop around our array of browsers
        // Load each browser into the `browserType` variable,
        // and then run the same test against each browser
        for (BrowserType browserType : browsersToTest) {

            // Launch whichever browser we're using in this loop
            try (Browser browser = browserType.launch()) {
                System.out.println("Running in " + browserType.name() + "...");

                // Create a new page (tab) in this browser
                Page page = browser.newPage();

                // Browse to the Makers website in this browser
                page.navigate("https://makers.tech");

                // Take a screenshot, and save it to a file which includes
                // the browser name in the filename (e.g. `makers-chromium.png`)
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("makers-" + browserType.name() + ".png")));

                System.out.println(page.title());
            }
        }

        // Close down Playwright and end the test
        playwright.close();
    }
}

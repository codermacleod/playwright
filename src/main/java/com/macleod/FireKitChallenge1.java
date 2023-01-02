package com.macleod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FireKitChallenge1 {

    public static void main(String[] args) {

        // Create a new instance of Playwright:
        Playwright playwright = Playwright.create();

        // Create an Array containing the browsers that
        // we want to test against:
        // * Firefox
        // * WebKit (Safari)
        List<BrowserType> browsersToTest = Arrays.asList(
                playwright.firefox(),
                playwright.webkit()
        );

        // Loop around our array of browsers
        // Load each browser into the `browserType` variable,
        // and then run the same test against each browser
        for (BrowserType browserType : browsersToTest) {
            try (Browser browser = browserType.launch()){
                System.out.println(browserType.name() + "...");

                // Create a new page in this browser
                Page page = browser.newPage();
                // Browse to the WhatIsMyBrowser website in this browser
                page.navigate("https://www.whatismybrowser.com/");

                // Take a screenshot, and save it to a file which includes
                // the browser name in the filename (e.g. `makers-chromium.png`)
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(browserType.name() + ".png")));

                // Print page title
                System.out.println(page.title());
            }
        }
        // Close down Playwright and end the test
        playwright.close();
    }
}

package com.macleod;// in com.macleod.HelloMakers.java

import com.microsoft.playwright.*;
import java.nio.file.Paths;

public class HelloMakers {
    public static void main(String[] args) {
        // Create a new instance of Playwright
        Playwright playwright = Playwright.create();

        // Use Playwright to open a new instance of Chromium
        Browser chromium = playwright.chromium().launch();

        // Open a new virtual page (tab) in your Chromium instance
        Page page = chromium.newPage();

        // Instruct the new page to browse to the Makers website
        page.navigate("https://makers.tech");

        // Take a screenshot of what's currently on the page,
        // and store it in a file called 'makers.png'
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("makers.png")));

        // Find the title of the webpage (the value inside the HTML
        // <title> element) and print it to the terminal
        System.out.println(page.title());

        // Close down Playwright and end the test
        playwright.close();
    }
}
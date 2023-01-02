package com.macleod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;

public class FindingFire {

    public static void main(String[] args) {
        // Create a new instance of Playwright
        Playwright playwright = Playwright.create();

        // Use playwright to open a new instance of Firefox:
        Browser firefox = playwright.firefox().launch();

        // Open a new virtual page within the Firefox instance:
        Page page = firefox.newPage();

        // Instruct the new page to go to Elsevier's website:
        page.navigate("https://elsevier.com");

        // Take a screenshot of what's currently on the page,
        // and store it in a file called 'elsevier.png':
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("elsevier.png")));

        // Find the title of the webpage (the value inside the HTML
        // <title> element) and print it to the terminal
        System.out.println(page.title());

        //Close down Playwright:
        playwright.close();
    }
}

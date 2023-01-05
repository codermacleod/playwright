package com.macleod;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DownloaderTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator downloadPage;
    Download download;

    @Test
    void shouldDownloadATextFileAndPrintContentsToConsole() throws IOException {
        page.navigate("https://the-internet.herokuapp.com/");
        assertThat(page).hasURL("https://the-internet.herokuapp.com/");

        downloadPage = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("File Download")).first();
        downloadPage.click();
        assertThat(page).hasURL("https://the-internet.herokuapp.com/download");

        // Wait for the download to start
        download = page.waitForDownload(() -> {
            // Perform the action that initiates download
            page.getByText("text.txt").click();
        });
        // Wait for the download process to complete
//        Path path = download.path();
        System.out.println(download.path());
        // Save downloaded file somewhere
        download.saveAs(Paths.get("/Users/danielmacleod/IdeaProjects/playwright-demo/src/test/resources/Downloads/text.txt"));

        // Print contents of file to console:
        BufferedReader myFile = new BufferedReader(new FileReader("/Users/danielmacleod/IdeaProjects/playwright-demo/src/test/resources/Downloads/text.txt"));
        String line = myFile.readLine();
        while(line != null)
        {
            System.out.println(line);
            line = myFile.readLine();
        }
        myFile.close();
        //---->END
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

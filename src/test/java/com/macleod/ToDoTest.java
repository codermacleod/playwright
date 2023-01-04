package com.macleod;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ToDoTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator addItem, itemsLeft, checkedBox;

    @Test
    void shouldAddItemToListAndThenCheckBoxToMarkAsComplete() {
        //Setup:
        page.navigate("https://todomvc.com/examples/react/#/");

        //Check page title:
        assertThat(page).hasTitle("React • TodoMVC");

        //Add item and assert it has been added to list:
        addItem = page.getByPlaceholder("What needs to be done?");
        addItem.click();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("beforeMilk.png")));
        addItem.fill("Buy some milk");
        addItem.press("Enter");

        //Confirm item was added:
        itemsLeft = page.locator("span.todo-count");
        assertThat(itemsLeft).hasText("1 item left");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("afterMilk.png")));
        System.out.println("It appears to have been added to the list...");


        System.out.println("I'll now tick the checkbox...");
        //Grab and check checkbox, then confirm with screenshot:
        checkedBox = page.locator("input.toggle");
        checkedBox.check();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("afterChecked.png")));

        //Confirm item was checked off:
        System.out.println("Final test about to run...");
        assertThat(itemsLeft).hasText("0 items left");
        System.out.println("Final test finished.:)");

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

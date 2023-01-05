package com.macleod;

// in MakersSearchPage.java

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class MakersSearchPage {
    private final Page page;
    private final Locator searchBox;
    private final Locator searchResultsHeading;

    public MakersSearchPage(Page page) {
        this.page = page;
        this.searchBox = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search for answers"));
        this.searchResultsHeading = page.locator("h1");
    }

    public void navigate() {
        page.navigate("https://faq.makers.tech/en/knowledge");
    }

    public void searchFor(String searchTerm) {
        searchBox.fill(searchTerm);
        searchBox.press("Enter");
    }

    public Locator getSearchResultsHeading() {
        return this.searchResultsHeading;
    }
}

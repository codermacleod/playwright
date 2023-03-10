package com.macleod;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class GovSearchPage {

    private Page page;
    private Locator searchBox, searchResultsHeading,
            cookieBannerReject, cookieBannerAccept, requiredLink;

    public GovSearchPage(Page page) {
        this.page = page;
        this.searchBox = page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search"));
        this.searchResultsHeading = page.locator("h1").first();
        this.cookieBannerReject = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject additional cookies"));
        this.cookieBannerAccept = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept additional cookies"));
        this.requiredLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Tax your vehicle without a V11 reminder"));
    }

    public void navigate(){
        page.navigate("https://www.gov.uk/");
    }
    public Locator acceptRejectCookies(String acceptReject){
        if (cookieBannerReject != null){
            acceptReject.toLowerCase();
            if (acceptReject.equals("accept") )
                return cookieBannerAccept;
            else if (acceptReject.equals("reject") ) {
                return cookieBannerReject;
            }
        }
        return cookieBannerReject;
    }

    public void searchFor(String searchTerm) {
        searchBox.fill(searchTerm);
        searchBox.press("Enter");
    }

    public Locator getSearchResultsHeading(){
        return this.searchResultsHeading;
    }

    public Locator getRequiredLink(){
        return requiredLink;
    }




}

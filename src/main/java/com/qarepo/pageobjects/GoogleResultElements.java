package com.qarepo.pageobjects;

import org.openqa.selenium.By;

public class GoogleResultElements {
    private static By by;

    private GoogleResultElements() {
    }

    public static By text_SearchResultTitle() {
        return By.xpath("//div[@id='search']//a/h3");
    }

    public static By link_SearchResultURL() {
        return By.xpath("//div[@id='search']//a//cite");
    }

    public static By text_SearchResultDescription() {
        return By.xpath("//div[@id='search']//span[@class='st']");
    }

    public static By button_NextPage() {
        return By.id("pnnext");
    }
}

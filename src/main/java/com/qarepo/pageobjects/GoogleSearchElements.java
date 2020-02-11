package com.qarepo.pageobjects;

import org.openqa.selenium.By;

public class GoogleSearchElements {
    private static By by;

    private GoogleSearchElements() {
    }

    public static By textBox_Search() {
        return By.xpath("//input[@title='Search']");
    }

    public static By button_GoogleSearch() {
        return By.xpath("//input[@aria-label='Google Search']");
    }
}

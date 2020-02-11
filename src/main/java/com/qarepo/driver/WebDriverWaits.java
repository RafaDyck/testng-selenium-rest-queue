package com.qarepo.driver;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class WebDriverWaits {
    private static final Logger logger = LogManager.getLogger(WebDriverWaits.class);
    private static FluentWait<WebDriver> wait;
    private static int defaultWait = 10;
    private static int defaultPolling = 1;

    public static WebElement findElementWithWait(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(e -> driver.findElement(by));
        return driver.findElement(by);
    }

    public static WebElement findElementWithWait(By by) {
        return findElementWithWait(by, defaultWait, defaultPolling);
    }

    public static WebElement findElementWithClickableWait(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(ExpectedConditions.elementToBeClickable(by));
        return driver.findElement(by);
    }

    public static WebElement findElementWithClickableWait(By by) {
        return findElementWithClickableWait(by, defaultWait, defaultPolling);
    }

    public static void findElementWithInvisibilityWait(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling)).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void findElementWithInvisibilityWait(By by) {
        findElementWithInvisibilityWait(by, defaultWait, defaultPolling);
    }

    public static WebElement findElementWithVisibilityWait(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    public static WebElement findElementWithVisibilityWaitWithoutException(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(ExpectedConditions.visibilityOfElementLocated(by));
        try {
            return driver.findElement(by);
        } catch (Exception e) {
            return null;
        }
    }

    public static WebElement findElementWithVisibilityWait(By by) {
        return findElementWithVisibilityWait(by, 10, defaultPolling);
    }

    public static List<WebElement> findElementsWithWait(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(e -> driver.findElement(by));
        return driver.findElements(by);
    }

    public static List<WebElement> findElementsWithWait(By by) {
        return findElementsWithWait(by, 10, defaultPolling);
    }

    public static WebElement findElementWithTextContainsWait(By by, String textContains, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(ExpectedConditions.textToBePresentInElementLocated(by, textContains));
        return driver.findElement(by);
    }

    public static WebElement findElementWithTextContainsWait(By by, String textContains) {
        return findElementWithTextContainsWait(by, textContains, 10, defaultPolling);
    }

    public static void waitForUrl(String expectedURL, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(ExpectedConditions.urlContains(expectedURL));
    }

    public static void waitForUrl(String expectedURL) {
        waitForUrl(expectedURL, 10, defaultPolling);
    }

    public static void clickElementWithWait(By by, long timeout, long polling) {
        for (int i = 0; i < 10; i++) {
            try {
                WebDriver driver = WebDriverThreadManager.getDriver();
                wait = new FluentWait<>(driver);
                wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                        .ignoring(WebDriverException.class).until(ExpectedConditions.elementToBeClickable(by));
                WebDriverThreadManager.getDriver().findElement(by).click();
                break;
            } catch (ElementClickInterceptedException e) {
                logger.log(Level.WARN, "[WebDriver Hash: " + WebDriverThreadManager.getDriver().hashCode()
                        + "][attempt: " + (i + 1) + "] Failed to click element: " + by.toString());
            }
        }
    }

    public static void clickElementWithWait(By by) {
        clickElementWithWait(by, 10, defaultPolling);
    }
}


package org.gutenberg.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BrowserUtils {

    public static void wait(int seconds) {
        try {
            Thread.sleep(1000L * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void waitFor(int duration, String timeUnit) {

        try {
            if(Objects.equals(timeUnit, "seconds")){
                Thread.sleep(1000L * duration);
            }
            if(Objects.equals(timeUnit, "minutes")){
                Thread.sleep(60000L * duration);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for element to be not stale
     * @param element
     */
    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            try {
                element.isDisplayed();
                break;
            } catch (StaleElementReferenceException st) {
                y++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    /**
     * Waits for the provided element to be visible on the page
     * @param element
     * @param timeOutInSeconds
     * @return
     */
    public static WebElement waitForVisibility(WebElement element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(WebDriverHelper.get(), Duration.ofSeconds(timeOutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Clicks on an element using JavaScript
     * @param element
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) WebDriverHelper.get()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) WebDriverHelper.get()).executeScript("arguments[0].click();", element);
    }
    /**
     * Waits for provided element to be clickable
     * @param element
     * @param timeOutInSeconds
     * @return
     */
    public static WebElement waitForClickablility(WebElement element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(WebDriverHelper.get(), Duration.ofSeconds(timeOutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static String getScreenshot(String name) {

        SimpleDateFormat df = new SimpleDateFormat("-yyyy-MM-dd-HH-mm");
        String date = df.format(new Date());
        TakesScreenshot ts = (TakesScreenshot) WebDriverHelper.get();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * Wait 15 seconds with polling interval of 200 milliseconds then click
     * @param webElement of element
     */
    public static void clickWithWait(WebElement webElement) {
        Wait<WebDriver> wait = new FluentWait<>(WebDriverHelper.get())
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(800))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);
        WebElement element = wait.until(driver -> webElement);
        try {
            element.click();
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * waits for backgrounds processes on the browser to complete
     */
    public static void waitForPageToLoad(int timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = driver -> {
            assert driver != null;
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        };
        try {
            WebDriverWait wait = new WebDriverWait(WebDriverHelper.get(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Wait for proper page title
     * @param pageTitle
     */
    public static void waitForPageTitle(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(WebDriverHelper.get(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs(pageTitle));
    }

    /**
     * This method will convert list of WebElements into list of string
     * @param listOfWebElements
     * @return list of strings
     */
    public static List<String> getListOfString(List<WebElement> listOfWebElements) {
        List<String> listOfStrings = new ArrayList<>();
        for (WebElement element : listOfWebElements) {
            String value = element.getText().trim();
            if (value.length() > 0) {
                listOfStrings.add(value);
            }
        }
        return listOfStrings;
    }
}

package org.gutenberg.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;

public class WebDriverHelper {

    private static final ThreadLocal<WebDriver> DriverPool = new ThreadLocal<>();

    WebDriverHelper() {
    }
    public static WebDriver get() {

        if (DriverPool.get() == null) {
            System.out.println("Starting to Create Driver");
            ChromeOptions chromeOptions = new ChromeOptions();
            String browserParamFromEnv = System.getProperty("browser");
            String configFilePath = "configuration.properties";
            String browser = browserParamFromEnv == null ? FileReader.getProperty("browser",configFilePath) : browserParamFromEnv;
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    DriverPool.set(new ChromeDriver());
                    break;
                case "chrome_headless":
                    WebDriverManager.chromedriver().setup();
                    DriverPool.set(new ChromeDriver(new ChromeOptions().setHeadless(true)));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    DriverPool.set(new FirefoxDriver());
                    break;
                case "firefox_headless":
                    WebDriverManager.firefoxdriver().setup();
                    DriverPool.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
                    break;
                case "ie":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    }
                    WebDriverManager.iedriver().setup();
                    DriverPool.set(new InternetExplorerDriver());
                    break;
                case "edge":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
                        throw new WebDriverException("Your OS doesn't support Edge");
                    }
                    WebDriverManager.edgedriver().setup();
                    chromeOptions.setBinary("/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge");
                    EdgeOptions edgeOptions = new EdgeOptions().merge(chromeOptions);
                    DriverPool.set(new EdgeDriver(edgeOptions));
                    break;
                case "safari":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
                        throw new WebDriverException("Your OS doesn't support Safari");
                    }
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    DriverPool.set(new SafariDriver());
                    break;
                case "remote_chrome":
                    try {
                        chromeOptions.setCapability("platform", Platform.ANY);
                        DriverPool.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "remote_firefox":
                    try {
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.setCapability("platform", Platform.ANY);
                        DriverPool.set(new RemoteWebDriver(new URL("http://localhost:4444//wd/hub"), firefoxOptions));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        return DriverPool.get();
    }

    public static void close() {
        DriverPool.get().quit();
        DriverPool.remove();
    }
}
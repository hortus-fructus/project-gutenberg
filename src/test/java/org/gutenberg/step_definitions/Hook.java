package org.gutenberg.step_definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.gutenberg.utilities.FileReader;
import org.gutenberg.utilities.WebDriverHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hook {
    private static final Logger logger = Logger.getLogger(Hook.class);

    @Before
    public void setup() {
        System.out.println("This is test output");
        logger.info("##############################");
        logger.info("Test setup!");
        String configFilePath = "configuration.properties";
        String browser = FileReader.getProperty("browser", configFilePath);
        if (!browser.contains("remote") && !browser.contains("mobile")) {
            WebDriverHelper.get().manage().window().maximize();
        }
    }

    @After
    public void teardown(Scenario scenario) {
        //if test failed - do this
        if (scenario.isFailed()) {
            logger.error("Test failed!");
            byte[] data = ((TakesScreenshot) WebDriverHelper.get()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(data, "image/png", "Screenshot");
        } else {
            logger.info("Cleanup!");
            logger.info("Test completed!");
        }
        logger.info("##############################");
        WebDriverHelper.close();
        //EmailHelper.sendEmail();
    }
}




package org.gutenberg.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.gutenberg.utilities.BrowserUtils;
import org.gutenberg.utilities.FileReader;
import org.gutenberg.utilities.SeleniumHelper;
import org.gutenberg.utilities.WebDriverHelper;

public class StepDefinitions {
    private static String filePath;
    @Given("I import file from {string}")
    public void I_import_file_from(String string) {
        filePath = string;
    }
    @Then("I navigate to {string}")
    public void I_navigate_to(String string) {
        WebDriverHelper.get().get(FileReader.getProperty(string,filePath));
    }
    @Then("Page title should be {string}")
    public void Page_title_should_be(String string) {
        BrowserUtils.waitForPageTitle(string);
    }
    @Then("I enter {string} into {string}")
    public void I_enter_text_into(String string1,String string2) {
        String element = FileReader.getProperty(string2,filePath);
        SeleniumHelper.getElement(element).sendKeys(string1);
    }
    @Then("I click on element {string}")
    public void I_click_on_element(String string) {
        String element = FileReader.getProperty(string,filePath);
        SeleniumHelper.getElement(element).click();
    }
    @Then("I wait for {string} {string}")
    public void I_wait_for(String string1,String string2) {
        BrowserUtils.waitFor(Integer.parseInt(string1),string2);
    }
    @Then("Element {string} should contains text {string}")
    public void Element_should_contains_text(String string1,String string2) {
        String element = FileReader.getProperty(string1,filePath);
        assert SeleniumHelper.getElement(element).getText().contains(string2);
    }
}

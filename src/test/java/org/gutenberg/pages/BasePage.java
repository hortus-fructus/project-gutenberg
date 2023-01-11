package org.gutenberg.pages;

import org.gutenberg.utilities.WebDriverHelper;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    public BasePage() {
        PageFactory.initElements(WebDriverHelper.get(), this);
    }
}

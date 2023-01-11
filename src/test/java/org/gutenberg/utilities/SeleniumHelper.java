package org.gutenberg.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SeleniumHelper {

    public static WebElement getElement(String element){
        WebElement ele = null;
        if(element.startsWith("x('")){
            String xpath = element.replaceAll("x\\('", "").replaceAll("'\\)","");
            ele = WebDriverHelper.get().findElement(By.xpath(xpath));
        }if(element.startsWith("('")){
            String selector = element.replaceAll("\\('", "").replaceAll("'\\)","");
            ele = WebDriverHelper.get().findElement(By.cssSelector(selector));
        }
        return ele;
    }
}

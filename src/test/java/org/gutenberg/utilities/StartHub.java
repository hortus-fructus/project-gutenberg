package org.gutenberg.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.grid.Main;
import org.openqa.selenium.net.PortProber;

public class StartHub {
    public static void startHub(){
//        int port = PortProber.findFreePort();
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();

        Main.main(
                new String[] { "standalone", "--port", "4444"});
    }
}

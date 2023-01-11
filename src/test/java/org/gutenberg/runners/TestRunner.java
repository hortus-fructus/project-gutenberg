package org.gutenberg.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org/gutenberg/step_definitions",
        dryRun = false,
        tags ="@Search",
        plugin = {"pretty","html:target/default-cucumber-reports",
                "json:target/cucumber.json",
                "rerun:target/rerun.txt"
        }
        ,monochrome = false
)
public class TestRunner {

}

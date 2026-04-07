package com.cestasoft.cvintelligence.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.cestasoft.cvintelligence.steps", "com.cestasoft.cvintelligence.hooks"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)
public class TestRunner {
}

package com.cestasoft.cvintelligence.hooks;

import com.cestasoft.cvintelligence.config.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class TestHooks {
    @Before
    public void beforeScenario() {
        DriverFactory.createDriver();
    }

    @After
    public void afterScenario() {
        DriverFactory.quitDriver();
    }
}

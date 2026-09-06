package com.ratemymedia.e2e.hooks;

import com.ratemymedia.e2e.context.TestContext;
import com.ratemymedia.e2e.drivers.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        context.setDriver(DriverFactory.createDriver());
    }

    @After
    public void tearDown() {
        if (context.driver() != null) {
            context.driver().quit();
        }
    }
}

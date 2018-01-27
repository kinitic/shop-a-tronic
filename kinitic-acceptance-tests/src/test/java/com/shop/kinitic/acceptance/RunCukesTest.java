package com.shop.kinitic.acceptance;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/*
 *  Cucumber tags examples:
 *  tags = {"@tagA","~@Ignore"}             -->     @tagA AND ~@Ignore
 *  tags = {"@tagA,@tagB","~@Ignore"}       -->     (@tagA OR @tagB) AND ~@Ignore
 *
 *  tags is the only property that will be overridden from command line
 * */

@RunWith(Cucumber.class)
@Cucumber.Options(format = {"pretty", "html:target/cucumber-html-reports-with-screenshots", "json-pretty:target/cucumber-report.json"},
        monochrome = true,
        features = "src/test/resources",
        glue = "classpath:com.shop.kinitic",
        tags = "~@Ignore")
public class RunCukesTest {

}
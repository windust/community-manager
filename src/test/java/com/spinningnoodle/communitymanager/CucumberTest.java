package com.spinningnoodle.communitymanager;

import com.spinningnoodle.communitymanager.security.SecurityConfig;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


@RunWith(Cucumber.class)
@EnableAutoConfiguration(exclude = { SecurityConfig.class})
@CucumberOptions(features = "src/test/resources")
public class CucumberTest {
}
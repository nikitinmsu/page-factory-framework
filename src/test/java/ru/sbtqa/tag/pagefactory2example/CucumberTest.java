package ru.sbtqa.tag.pagefactory2example;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import ru.sbtqa.tag.pagefactory.Tag;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, plugin = {"pretty"},
        glue = {"ru.sbtqa.tag.stepdefs"},
        features = {"src/test/resources/en/features/"}
)
public class CucumberTest extends Tag {}
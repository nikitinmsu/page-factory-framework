package ru.sbtqa.tag.pagefactory2example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.sbtqa.tag.pagefactory.junit.CoreSetupSteps;
import ru.sbtqa.tag.pagefactory.web.junit.WebSteps;
import ru.sbtqa.tag.pagefactory2example.web.pages.MainPage;
import ru.sbtqa.tag.pagefactory2example.web.pages.projects.FilePage;
import ru.sbtqa.tag.pagefactory2example.web.pages.projects.Pf2ExamplePage;


public class JunitTest {

    private static WebSteps steps;

    @BeforeClass
    public static void before() {
        steps = WebSteps.getInstance();
    }

    @AfterClass
    public static void after() {
        CoreSetupSteps.tearDown();
    }
}

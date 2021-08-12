package ru.sbtqa.tag.stepdefs.en;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class MyStepdefs {

    @When("^waiting (\\d+) seconds$")
    public void waitSeconds(int timeout) throws InterruptedException {
        Thread.sleep(timeout * 1000);
    }


}

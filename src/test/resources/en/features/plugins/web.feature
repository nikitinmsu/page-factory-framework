#language:en
@web-plugin
Feature: Example of using web-plugin

  Background: Log in and add first subscription
    Given user is on the page "Main_ETPMarkets"
    When user clicks the button "sign-in"
    Then user is on the page "LogIn_ETPMarkets"

    When user fills the field "login" with value "test.qa.2@esprow.com"
    And user fills the field "password" with value "temporaryAccount"
    And user clicks the button "signIn"
    Then user is on the page "ETPSubscription"


  @test_1
  Scenario: Add one subscription and check list
    #add first subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 5.0"
    Then user checks in the element "protocol type" value "FIX 5.0"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 5.0" "1"



  @test_2
  Scenario: Add two subscription and check list
    #add first subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 5.0"
    Then user checks in the element "protocol type" value "FIX 5.0"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 5.0" "1"

    #add second subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 4.3"
    Then user checks in the element "protocol type" value "FIX 4.3"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 4.3" "2"

  @test_3
  Scenario: Add two subscription and delete all and check list
    #add first subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 5.0"
    Then user checks in the element "protocol type" value "FIX 5.0"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 5.0" "1"

    #add second subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 4.3"
    Then user checks in the element "protocol type" value "FIX 4.3"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 4.3" "2"

    When user (press on following number of checkbox in subscription list) "All"
    And user clicks the button "Delete selected subscriptions"
    And user (check count of elements in subscription list) "0"


  @test_4
  Scenario: Add two subscription and delete first and check list
    #add first subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 5.0"
    Then user checks in the element "protocol type" value "FIX 5.0"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 5.0" "1"

    #add second subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 4.3"
    Then user checks in the element "protocol type" value "FIX 4.3"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 4.3" "2"

    When user (press on following number of checkbox in subscription list) "1"
    And user clicks the button "Delete selected subscriptions"
    And user (check count of elements in subscription list) "1"


  @test_5
  Scenario: Add two subscription and delete first and check list
    #add first subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 5.0"
    Then user checks in the element "protocol type" value "FIX 5.0"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 5.0" "1"

    #add second subscription
    When user clicks the button "Add Exchange"
    And user (select Protocol type) "FIX 4.3"
    Then user checks in the element "protocol type" value "FIX 4.3"

    When user clicks the button "plus number of session"
    And user clicks the button "subscription add button"
    Then user (checks text in following item in the list) "FIX 4.3" "2"

    When user (press on following number of checkbox in subscription list) "2"
    And user clicks the button "Delete selected subscriptions"
    And user (check count of elements in subscription list) "1"
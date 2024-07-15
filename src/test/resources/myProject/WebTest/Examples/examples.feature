@ExamplesWebTest @WebTesting
Feature: Examples all selenium features

  Scenario: Selenium TextBox Test
    Then I filled the sample textbox with Hola Mundo!
    Then I filled the other sample textbox


  Scenario: Selenium Dropdown/Multiselect Test
    Then I selected the sample dropdown with text doc 1
    Then I selected the dropdown using filters
    Then I selected the other sample multiselect


  Scenario: Find a text in a WebElement (selenium) List
    Then I searching for a element with text Register

  Scenario: Handle Alerts
    Then I closed the system alert

  Scenario: Switching windows
    Then I clicked on selenium143 link
    Then I switched to the window called selenium143
    Then I switched to the window called main

  Scenario: Switching frames
    Then I switched through the frames

  Scenario: Modifying table attributes
    Then I modified table attributes
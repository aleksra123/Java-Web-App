# new feature
# Tags: optional
    
Feature: Acceptance tests for Students and Modules logic
	As a Web application user
	I want to have an Entity Module and Student
	So that both of them work as predefined and do exactly what i expect

  # Open the browser and check for Entries
  Scenario Outline: Is the Website running and Entities receive data
    Given I navigate to <link>
    When I scroll to element having class <list>
    Then element having class <checkbox> should be present

  Examples:
    | link                                | list                      | checkbox          |
    | "http://127.0.0.1:8080/#!CwStudents"| "students-ListContents"   | "gwt-CheckBox"    |
    | "http://127.0.0.1:8080/#!CwModules" | "modules-ListContents"    | "gwt-CheckBox"    |

  # The whole CRUD Operations should work fine
  Scenario Outline: Add new Modules
    Given I click on element having xpath <button>
    When I enter <modulename> into input field having class <textfield1>
    And I enter <description> into input field having xpath <textfield2>
    And I enter <students> into input field having xpath <textfield3>
    And I click on element having xpath <button2>
    Then I wait for 1 sec
    Then element having xpath <proove> should have text as <true>

  Examples:
    | button                              | modulename    | textfield1    | description           | textfield2                                    | students  | textfield3                                    | button2                               | proove                                | true          |
    | "//button[contains(text(),'Add')]"  | "NEWMODULE"   | "gwt-TextBox" | "This is NEWMODULE"   | "(//input[contains(@class,'gwt-TextBox')])[2]"| "no one"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWMODULE')]"  | "NEWMODULE"   |
    | "//button[contains(text(),'Add')]"  | "NEWMODULE1"  | "gwt-TextBox" | "This is NEWMODULE1"  | "(//input[contains(@class,'gwt-TextBox')])[2]"| "no one"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWMODULE1')]" | "NEWMODULE1"  |

  # One of the added Modules should be deleted because of persistance
  Scenario: Delete one Module
    Given I toggle checkbox having xpath "//tr[td[contains(text(),"NEWMODULE")]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"
    When I click on element having xpath "//button[contains(text(),'Delete')]"
    Then I wait for 1 sec
    And element having xpath "//td[text() ='NEWMODULE']" should not be present

  Scenario: navigate Back
    Given I navigate back
    Then I wait for 1 sec

  # It should be possible to go back to Students and to insert min one student and up to 20 Studnets without any Exeptions
  Scenario Outline: add twenty Students
    Given I click on element having xpath <button>
    And I wait for 1 sec
    When I enter <studentsname> into input field having xpath <textfield1>
    And I enter <studentssurname> into input field having xpath <textfield2>
    And I enter <gender> into input field having xpath <textfield3>
    And I enter <birth> into input field having xpath <textfield4>
    And I click on element having xpath <textfield5>
    And I click on element having xpath <selectionDD>
    And I click on element having xpath <button2>
    Then I wait for 1 sec

  Examples:
    | button                              | studentsname     | textfield1                                        | studentssurname   | textfield2                                    | gender    | textfield3                                    | birth         | textfield4                                    | textfield5                                    | selectionDD                           | button2                               | proove                                           | true                    |
    | "//button[contains(text(),'Add')]"  | "NEWNAME1"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME1"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME1 NEWSURNAME1')]"  | "NEWNAME1 NEWSURNAME1"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME2"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME2"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME2 NEWSURNAME2')]"  | "NEWNAME2 NEWSURNAME2"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME3"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME3"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME3 NEWSURNAME3')]"  | "NEWNAME3 NEWSURNAME3"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME4"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME4"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME4 NEWSURNAME4')]"  | "NEWNAME4 NEWSURNAME4"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME5"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME5"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME5 NEWSURNAME5')]"  | "NEWNAME5 NEWSURNAME5"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME6"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME6"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME6 NEWSURNAME6')]"  | "NEWNAME6 NEWSURNAME6"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME7"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME7"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME7 NEWSURNAME7')]"  | "NEWNAME7 NEWSURNAME7"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME8"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME8"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME8 NEWSURNAME8')]"  | "NEWNAME8 NEWSURNAME8"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME9"       | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME9"     | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME9 NEWSURNAME9')]"  | "NEWNAME9 NEWSURNAME9"  |
    | "//button[contains(text(),'Add')]"  | "NEWNAME10"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME10"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME10 NEWSURNAME10')]"| "NEWNAME10 NEWSURNAME10"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME11"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME11"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME11 NEWSURNAME11')]"| "NEWNAME11 NEWSURNAME11"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME12"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME12"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME12 NEWSURNAME12')]"| "NEWNAME12 NEWSURNAME12"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME13"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME13"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME13 NEWSURNAME13')]"| "NEWNAME13 NEWSURNAME13"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME14"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME14"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME14 NEWSURNAME14')]"| "NEWNAME14 NEWSURNAME14"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME15"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME15"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME15 NEWSURNAME15')]"| "NEWNAME15 NEWSURNAME15"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME16"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME16"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME16 NEWSURNAME16')]"| "NEWNAME16 NEWSURNAME16"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME17"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME17"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME17 NEWSURNAME17')]"| "NEWNAME17 NEWSURNAME17"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME18"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME18"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME18 NEWSURNAME18')]"| "NEWNAME18 NEWSURNAME18"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME19"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME19"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME19 NEWSURNAME19')]"| "NEWNAME19 NEWSURNAME19"|
    | "//button[contains(text(),'Add')]"  | "NEWNAME20"      | "(//input[contains(@class,'gwt-TextBox')])[1]"    | "NEWSURNAME20"    | "(//input[contains(@class,'gwt-TextBox')])[2]"| "female"  | "(//input[contains(@class,'gwt-TextBox')])[3]"| "09.05.1990"  | "(//input[contains(@class,'gwt-TextBox')])[4]"| "(//input[contains(@class,'gwt-TextBox')])[5]"| "//div[contains(text(),'NEWMODULE1')]"| "//button[contains(text(),'Save')]"   | "//td[contains(text(),'NEWNAME20 NEWSURNAME20')]"| "NEWNAME20 NEWSURNAME20"|


  # Check if at least one Student Element is present
  Scenario: Check if NEWNAME NEWSURNAME present
    Given element having xpath "//td[contains(text(),'NEWNAME')]" should be present


#  # Try to add a Student to full class
#  Scenario: Try to add a student to afull class
#    Given I click on element having xpath "//button[contains(text(),'Add')]"
#    And I wait for 1 sec
#    When I enter "NEWNAME21" into input field having xpath "(//input[contains(@class,'gwt-TextBox')])[1]"
#    And I enter "NEWSURNAME21" into input field having xpath "(//input[contains(@class,'gwt-TextBox')])[2]"
#    And I enter "male" into input field having xpath "(//input[contains(@class,'gwt-TextBox')])[3]"
#    And I enter "09.05.1990" into input field having xpath "(//input[contains(@class,'gwt-TextBox')])[4]"
#    And I click on element having xpath "(//input[contains(@class,'gwt-TextBox')])[5]"
#    And I click on element having xpath "//div[contains(text(),'NEWMODULE1')]"
#    And I click on element having xpath "//button[contains(text(),'Save')]"
#    Then I wait for 10 sec
#    And element having xpath "//td[text() = 'NEWNAME21 NEwSURNAME21')]" should be present

#  # Now I also want to check if the new student is not enrolled in the same class as the others and instead has a default class
#  Scenario: Is student enrolled in different class
#    Given I click on element having xpath "//td[text() = 'NEWNAME21 NEwSURNAME21')]"
#    Then element having xpath "(//input[contains(@class,'gwt-TextBox')])[5]" should have text as "0"
#


  # Delete a couple added students one by one for persistance
  Scenario Outline: Delete the added Student
    Given I toggle checkbox having xpath <checkbox>
    When I click on element having xpath <button>
    Then I wait for 1 sec

  Examples:
    | checkbox                                                                                      | button                                | proove                                    |
    | "//tr[td[text()="NEWNAME1 NEWSURNAME1"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME1 NEWSURNAME1']"     |
    | "//tr[td[text()="NEWNAME2 NEWSURNAME2"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME2 NEWSURNAME2']"     |
    | "//tr[td[text()="NEWNAME3 NEWSURNAME3"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME3 NEWSURNAME3']"     |
    | "//tr[td[text()="NEWNAME4 NEWSURNAME4"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME4 NEWSURNAME4']"     |
    | "//tr[td[text()="NEWNAME5 NEWSURNAME5"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME5 NEWSURNAME5']"     |
    | "//tr[td[text()="NEWNAME6 NEWSURNAME6"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME6 NEWSURNAME6']"     |
    | "//tr[td[text()="NEWNAME7 NEWSURNAME7"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME7 NEWSURNAME7']"     |
    | "//tr[td[text()="NEWNAME8 NEWSURNAME8"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME8 NEWSURNAME8']"     |
    | "//tr[td[text()="NEWNAME9 NEWSURNAME9"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"     | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME9 NEWSURNAME9']"     |
    | "//tr[td[text()="NEWNAME10 NEWSURNAME10"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//button[contains(text(),'Delete')]" | "//td[text()='NEWNAME10 NEWSURNAME10']"   |

  # Delete the rest of added student all together
  Scenario Outline: Delete all Students together
    Given I toggle checkbox having xpath <checkbox1>
    And I toggle checkbox having xpath <checkbox2>
    And I toggle checkbox having xpath <checkbox3>
    And I toggle checkbox having xpath <checkbox4>
    And I toggle checkbox having xpath <checkbox5>
    When I click on element having xpath <button>
    Then I wait for 1 sec

  Examples:
    | checkbox1                                                                                     | checkbox2                                                                                     | checkbox3                                                                                     | checkbox4                                                                                     | checkbox5                                                                                     | button                                |
    | "//tr[td[text()="NEWNAME11 NEWSURNAME11"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//tr[td[text()="NEWNAME12 NEWSURNAME12"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//tr[td[text()="NEWNAME13 NEWSURNAME13"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//tr[td[text()="NEWNAME14 NEWSURNAME14"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//tr[td[text()="NEWNAME15 NEWSURNAME15"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//button[contains(text(),'Delete')]" |
    | "//tr[td[text()="NEWNAME16 NEWSURNAME16"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//tr[td[text()="NEWNAME17 NEWSURNAME17"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//tr[td[text()="NEWNAME18 NEWSURNAME18"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//tr[td[text()="NEWNAME19 NEWSURNAME19"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//tr[td[text()="NEWNAME20 NEWSURNAME20"]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"   | "//button[contains(text(),'Delete')]" |

  # Check if the deleted Elements are Present
  Scenario: Check if NEWNAME NEWSURNAME present
    Given element having xpath "//td[contains(text(),'NEWNAME')]" should not be present

  # One the last added Module because of persistance
  Scenario: Delete Module with name NEWMODULE1
    Given I navigate forward
    And I toggle checkbox having xpath "//tr[td[contains(text(),"NEWMODULE1")]]/td/span['gwt-CheckBox']/input[@type="checkbox"]"
    When I click on element having xpath "//button[contains(text(),'Delete')]"
    And I wait for 1 sec
    Then element having xpath "//td[contains(text(),'NEWMODULE1')]" should not be present
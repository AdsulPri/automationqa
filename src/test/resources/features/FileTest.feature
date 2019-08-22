@FileTest
Feature: US01 Verifying file values

  @Test2_FileTest1
  Scenario: TC01 verify each value for given range
    Given user has a file as "Invalid.txt"
    When value for each line is in between upper limit "500" and lower limit "100" inclusive
    Then the file is "valid"
    
    @Test2_FileTest1
  Scenario: TC02 verify each value for given range
    Given user has a file as "Valid.txt"
    When value for each line is in between upper limit "500" and lower limit "100" inclusive
    Then the file is "valid"

Feature: US02 Verifying logs
  As a support engineer I want to verify the log file generated is correct

  @logtest1
  Scenario: verify range of values in given test file
    Given I have a file as "xyz.txt"
    When value for each line is in betweenr upper limit "500" and lower limit "100" inclusive
    Then the file is "valid"

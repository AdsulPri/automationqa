Feature: US02 : Hostel world website city wise search for hostels

  @Test1_VerifySearchByCityName
  Scenario Outline: Home page navigation to "<city name>" hostels
    Given user is on hostelworld homepage
    When user enters city name as "<city name>"
    And selects the same from list
    And clicks on search button
    Then the hostels loaded are for same "<city name>" city

    Examples: 
      | city name |
      | Mumbai    |
      | Cork      |
      | Mumbai    |
      | New Delhi |
      | New York  |
      | London    |
      
 

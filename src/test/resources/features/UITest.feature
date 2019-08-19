@HostelWorld_Homepage
Feature: City search for hostels
  As a user I should be able to search city wise hostels

  @Test1_CityFilter
  Scenario Outline: Home page navigation to city hostels
    Given user is on hostelworld homepage
    When user enters city name as <City>
    Then I verify the hostels loaded are for same <City>

    Examples: 
      | City      |
      | Dublin    |
      | Cork      |
      | Mumbai    |
      | New Delhi |
      | New York  |
      | London    |

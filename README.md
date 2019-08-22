# Build Status

[![Build Status](https://travis-ci.com/AdsulPri/automationqa.svg?branch=master)](https://travis-ci.com/AdsulPri/automationqa)

# QA Automation

QA assignments are fun and opportunity to learn a lot of stuff!


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

System prerequisites :

1. Maven
2. Java JDK 8
3. Setup system variables
4. Your favorite IDE

Application/project prerequisites :

1. Import project from git and build as maven project
2. In ./src/main/resources/config/copnfig.properties file get your git personal token added/replaced
3. In ./src/test/resources/testdata folder - add files you want to validate - remember the file name
4. Download machine suitable chromedriver.exe file and replace it with the one at - ./src/test/resources/chrome/chromedriver.exe

```
Git personal token can be generated at - https://github.com/settings/tokens
Chrome driver exes are available at - https://chromedriver.chromium.org/downloads

```

## Installation

To run the tests in ./src/test/resources/features:

PART 1 : File Test(@FileTest)
If you have added new files please replace the name in the feature file "FileTest.feature" with your file name. 

```
  Scenario: TC01 verify each value for given range
    Given user has a file as "Invalid.txt"
    When value for each line is in between upper limit "500" and lower limit "100" inclusive
    Then the file is "valid"
```

PART 2 : UI Test(@Test1_VerifySearchByCityName)
You can add city of your choice, similar to other cities as shown below in "UITest.Feature" and enjoy the test

```
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
```

PART 3 : API Test(@API_Test)
As per prerequisites check you have added your personal token.
Also check access to url : https://gist.github.com/<your-git-Username>

PART 4 : Integration of PART 1 with Travis CI shown as above can be built with job specified below

##  Running the tests

1. Everything at once
You can run ./src/test/java/cucumberRunner/TestRunner.java as JUnit test - (as the tags line is commented it will take all feature files and will execute it)
OR
In terminal run : mvn clean test -Dorg.freemarker.loggerLibrary=none

2. Tag wise - Use appropriate tag as mentioned in above section to run it one by one.
OR
In terminal run : mvn test -Dcucumber.options="--tags @FileTest" -Dorg.freemarker.loggerLibrary=none


## Reports and logs

Results of test cases :

PART 1 : File Test(@FileTest)   - File wise log file at ./target/reports/logs
PART 2 : UI Test(@Test1_VerifySearchByCityName) - City wise html report with screenshot at ./target/reports
PART 3 : API Test(@API_Test) - Log file detailing steps and API calls at ./target/reports/logs


## Built With

* [TravisCI](https://travis-ci.com/AdsulPri/automationqa/builds) - The CI framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Priyanka Adsul** - *Assignment work* - [AdsulPri](https://github.com/AdsulPri)

## Acknowledgments

* Regards : Google
* Inspiration and thanks : HostelWorld QA assignment

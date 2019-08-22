@API_Test
Feature: Test API for basic operations

  @Test1_create_gist
  Scenario: Create gists using api
    Given user has file to share
    And git access key
    When create gist api is called
    Then response status code should be 201
    And gist URLs should be received

@Test2_contentsOfGist
  Scenario: Get contents of all gists
    Given user has gist URL
    And git access key
    When get gist api is called
    Then response status code should be 200
    And gist files should be received
    
@Test3_deleteGist
  Scenario: Delete a gist
    Given user has gist URL
    And git access key
    When delete gist api is called
    Then response status code should be 204
    And gist should be deleted


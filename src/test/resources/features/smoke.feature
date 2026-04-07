Feature: Smoke coverage
  As a tester
  I want basic confidence that the application is reachable
  So that deeper scenarios start from a healthy UI

  Scenario: Home page loads
    Given the CV Intelligence application is open
    Then the upload form is visible
    And the status text contains "Ready"

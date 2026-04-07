Feature: Upload and ingest CV content

  Scenario: Upload valid structured CV file
    Given the CV Intelligence application is open
    When I upload the test file "structured_cv.txt"
    Then the analysis completes successfully
    And the review workspace is visible

  Scenario: Paste valid structured CV text
    Given the CV Intelligence application is open
    When I switch to paste mode
    And I paste the test text "structured_cv.txt"
    And I submit the CV for analysis
    Then the analysis completes successfully
    And the review workspace is visible

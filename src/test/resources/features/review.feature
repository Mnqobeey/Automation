Feature: Review flow

  Scenario: Review can be completed for valid structured CV input
    Given the CV Intelligence application is open
    When I paste the test text "structured_cv.txt" in paste mode and submit it
    Then the analysis completes successfully
    When I open the recruiter preview
    Then the review complete action is available

Feature: DOCX export flow

  Scenario: DOCX export succeeds after review completion
    Given the CV Intelligence application is open
    When I paste the test text "structured_cv.txt" in paste mode and submit it
    Then the analysis completes successfully
    When I open the recruiter preview
    And I mark the review as complete
    And I download the DOCX profile
    Then a DOCX file is downloaded

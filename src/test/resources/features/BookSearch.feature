Feature: Book search
  As user, I should be able to search a book on the web page

  @Search
  Scenario: Search a book
    Given I import file from "src/test/resources/features/BookSearch.properties"
    When I navigate to "gutenberg_url"
    Then Page title should be "Free eBooks | Project Gutenberg"
    When I enter "War and Peace" into "search_box"
    And I click on element "go_button"
    And I wait for "2" "seconds"
    Then Element "search_result_header" should contains text "War and Peace"




Feature: Create & Validate Order

  Scenario Outline: Select Jackets
    Given user search "<Item>" in "<Category>" for "<Menu>"
    Then user is able to see resulted products
    When user select first product
    And user select size and color
    And user select quantity "<Qty>"
    And user add the product to cart
    Then user can able to see cart value updated

    Examples: 
      | Item    | Category | Menu | Qty |
      | Jackets | Tops     | Men  |   2 |

  Scenario Outline: Select Pants
    Given user search "<Item>" in "<Category>" for "<Menu>"
    Then user is able to see resulted products
    When user select first product
    And user select size and color
    And user select quantity "<Qty>"
    And user add the product to cart
    Then user can able to see cart value updated

    Examples: 
      | Item  | Category | Menu | Qty |
      | Pants | Bottoms  | Men  |   1 |

  Scenario: Place Order
    Given user navigate to cart
    Then user can able to fetch total cart price
    And user go to checkout page
    When user add new address
    And user select shipping method
    And user click on next button
    And user can able to fetch total price in summary page
    Then user can verify price on cart and summary page are same
    And user place the order
    Then user can able to see order number

  Scenario: Verify Order
    Given user navigate account page
    And user go to my order
    Then user can able to validate order

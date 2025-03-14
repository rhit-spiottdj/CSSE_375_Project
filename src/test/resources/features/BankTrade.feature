Feature: Bank Trade
  Test one player trading with the bank

  Scenario Outline: Successful trade with the bank when player and bank have sufficient resources
    Given the game is set up for bank trading
    And the player has <give_quantity> "<give_resource>"
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>"
    Then the player successfully receives <receive_quantity> "<take_resource>" from the bank
    Then the player still has 0 "<give_resource>"
    Then the bank has 19 + <give_quantity> "<give_resource>"
    Then the bank has 19 - <receive_quantity> "<take_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity |
      | 4             | ORE           | WOOL          | 1                |
      | 8             | BRICK         | GRAIN         | 2                |
      | 12            | LUMBER        | ORE           | 3                |

  Scenario Outline: Unsuccessful trade with the bank due to insufficient bank resources
    Given the game is set up for bank trading
    And the player has <give_quantity> "<give_resource>"
    And the bank does not have sufficient "<take_resource>"
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>"
    Then the player doesnt receive <receive_quantity> "<take_resource>" from the bank
    Then the player still has <give_quantity> "<give_resource>"
    Then the bank still has 19 "<give_resource>"
    Then the bank still has 0 "<take_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity |
      | 4             | ORE           | WOOL          | 1                |
      | 8             | BRICK         | GRAIN         | 2                |
      | 12            | LUMBER        | ORE           | 3                |

  Scenario Outline: Unsuccessful trade with the bank due to insufficient player resources
    Given the game is set up for bank trading
    And the player does not have resources
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>"
    Then the player doesnt receive <receive_quantity> "<take_resource>" from the bank
    Then the player still has 0 "<give_resource>"
    Then the bank still has 19 "<take_resource>"
    Then the bank still has 19 "<give_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity |
      | 4             | ORE           | WOOL          | 1                |
      | 8             | BRICK         | GRAIN         | 2                |
      | 12            | LUMBER        | ORE           | 3                |

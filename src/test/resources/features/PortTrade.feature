Feature: Bank Trade
  Test one player trading with the bank

  Scenario Outline: Successful trade with the Port when player and bank have sufficient resources
    Given the game is set up for bank trading
    And the player has <give_quantity> "<give_resource>"
    And the player has a three port
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>" at the port
    Then the player successfully receives <receive_quantity> "<take_resource>" from the bank
    Then the player still has 0 "<give_resource>"
    Then the bank has 19 + <give_quantity> "<give_resource>"
    Then the bank has 19 - <receive_quantity> "<take_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity |
      | 3             | ORE           | WOOL          | 1                |
      | 6             | BRICK         | GRAIN         | 2                |
      | 9             | LUMBER        | ORE           | 3                |

  Scenario Outline: Unsuccessful trade with the Port due to insufficient bank resources
    Given the game is set up for bank trading
    And the player has <give_quantity> "<give_resource>"
    And the bank does not have sufficient "<take_resource>"
    And the player has a three port
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>" at the port
    Then the player doesnt receive <receive_quantity> "<take_resource>" from the bank
    Then the player still has <give_quantity> "<give_resource>"
    Then the bank still has 19 "<give_resource>"
    Then the bank still has 0 "<take_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity |
      | 3             | ORE           | WOOL          | 1                |
      | 6             | BRICK         | GRAIN         | 2                |
      | 9             | LUMBER        | ORE           | 3                |


  Scenario Outline: Unsuccessful trade with the port due to insufficient player resources
    Given the game is set up for bank trading
    And the player does not have resources
    And the player has a three port
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>" at the port
    Then the player doesnt receive <receive_quantity> "<take_resource>" from the bank
    Then the player still has 0 "<give_resource>"
    Then the bank still has 19 "<take_resource>"
    Then the bank still has 19 "<give_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity |
      | 3             | ORE           | WOOL          | 1                |
      | 6             | BRICK         | GRAIN         | 2                |
      | 9             | LUMBER        | ORE           | 3                |


  Scenario Outline: Unsuccessful trade with the port due to bad player trade ratio
    Given the game is set up for bank trading
    And the player has <give_quantity> "<give_resource>"
    And the player has a three port
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>" at the port
    Then the player doesnt receive <receive_quantity> "<take_resource>" from the bank
    Then the player still has <give_quantity> "<give_resource>"
    Then the bank still has 19 "<take_resource>"
    Then the bank still has 19 "<give_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity |
      | 4             | ORE           | WOOL          | 1                |
      | 7             | BRICK         | GRAIN         | 2                |
      | 11            | LUMBER        | ORE           | 3                |

  Scenario Outline: Unsuccessful trade with the port due to wrong port
    Given the game is set up for bank trading
    And the player has <give_quantity> "<give_resource>"
    And the player has a "<wrong_resource>" two port
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>" at the port
    Then the player doesnt receive <receive_quantity> "<take_resource>" from the bank
    Then the player still has <give_quantity> "<give_resource>"
    Then the bank still has 19 "<take_resource>"
    Then the bank still has 19 "<give_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity | wrong_resource |
      | 4             | ORE           | WOOL          | 1                | BRICK          |
      | 7             | BRICK         | GRAIN         | 2                | GRAIN          |
      | 11            | LUMBER        | ORE           | 3                | ORE            |

  Scenario Outline: Successful trade with a two port
    Given the game is set up for bank trading
    And the player has <give_quantity> "<give_resource>"
    And the player has a "<give_resource>" two port
    When the player attempts to trade <give_quantity> "<give_resource>" for "<take_resource>" at the port
    Then the player successfully receives <receive_quantity> "<take_resource>" from the bank
    Then the player still has 0 "<give_resource>"
    Then the bank has 19 + <give_quantity> "<give_resource>"
    Then the bank has 19 - <receive_quantity> "<take_resource>"

    Examples:
      | give_quantity | give_resource | take_resource | receive_quantity |
      | 4             | ORE           | WOOL          | 2                |
      | 6             | BRICK         | GRAIN         | 3                |
      | 10            | LUMBER        | ORE           | 5                |
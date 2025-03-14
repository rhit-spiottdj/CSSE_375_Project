Feature: Upgrading to a city
  As a player, in order to receive more resources, I want to upgrade my settlements to cities


Scenario Outline: Valid city purchase
Given Player 1 has started their turn in a 3 person game
And the initial setup has been completed
And player 1 has resources <player1_resources>
  And the players numCities is greater than zero
When there is a settlement that is owned by the in-turn player at <index>
And the in-turn player attempts to buy a city at the index <index>
Then the city will replace the settlement at the location
And player 1 should have resources <expected_player1_resources>

Examples:
| player1_resources            | index |  expected_player1_resources |
| "ORE:ORE:ORE:GRAIN:GRAIN"    | 24    |  ""                         |

  Scenario Outline: Invalid city purchase, not enough resources
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And the players numCities is greater than zero
    When there is a settlement that is owned by the in-turn player at <index>
    And the in-turn player attempts to buy a city at the index <index>
    Then the city will not replace the settlement at the location
    And player 1 should have resources <expected_player1_resources>

    Examples:
      | player1_resources      | index |  expected_player1_resources |
      | "ORE:ORE:ORE:GRAIN"    | 24    |  "ORE:ORE:ORE:GRAIN"        |
      | ""                     | 24    |  ""                         |

  Scenario Outline: Invalid city purchase, settlement not owned by player
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And the players numCities is greater than zero
    When there is a not a settlement that is owned by the in-turn player at <index>
    And the in-turn player attempts to buy a city at the index <index>
    Then the city will not replace the settlement at the location
    And player 1 should have resources <expected_player1_resources>

    Examples:
      | player1_resources            | index |  expected_player1_resources       |
      | "ORE:ORE:ORE:GRAIN:GRAIN"    | 24    |  "ORE:ORE:ORE:GRAIN:GRAIN"        |
      | "ORE:ORE:ORE:GRAIN:GRAIN"    | 2     |  "ORE:ORE:ORE:GRAIN:GRAIN"        |

Scenario Outline: Invalid city purchase, no Cities left
  Given Player 1 has started their turn in a 3 person game
  And the initial setup has been completed
  And player 1 has resources <player1_resources>
  And the players numCities is greater than zero
  When there are no cities left
  And the in-turn player attempts to buy a city at the index <index>
  Then the city will not replace the settlement at the location
  And player 1 should have resources <expected_player1_resources>

  Examples:
    | player1_resources            | index |  expected_player1_resources |
    | "ORE:ORE:ORE:GRAIN:GRAIN"    | 0    |  "ORE:ORE:ORE:GRAIN:GRAIN"  |

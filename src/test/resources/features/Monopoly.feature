Feature: Play Monopoly
  Completes the Monopoly card effect

  Scenario Outline: Successfully take resource with 3 players
    Given Player 1 has started their turn in a 3 person game
    And player 1 has resources <player1_resources>
    And player 2 has resources <player2_resources>
    And player 3 has resources <player3_resources>
    When player activates a Monopoly for <resource>
    Then player 1 should have resources <expected_player1_resources>
    And player 2 should have resources <expected_player2_resources>
    And player 3 should have resources <expected_player3_resources>

    Examples:
      | player1_resources | player2_resources | player3_resources | resource  | expected_player1_resources| expected_player2_resources |  expected_player3_resources  |
      | ""                | "WOOL:WOOL"       | "LUMBER:WOOL"     | "WOOL"    | "WOOL:WOOL:WOOL"          | ""                         |  "LUMBER"                    |


  Scenario Outline: Successfully take resource with 4 players
    Given Player 1 has started their turn in a 4 person game
    And player 1 has resources <player1_resources>
    And player 2 has resources <player2_resources>
    And player 3 has resources <player3_resources>
    And player 4 has resources <player4_resources>
    When player activates a Monopoly for <resource>
    Then player 1 should have resources <expected_player1_resources>
    And player 2 should have resources <expected_player2_resources>
    And player 3 should have resources <expected_player3_resources>
    And player 4 should have resources <expected_player4_resources>

    Examples:
      | player1_resources | player2_resources | player3_resources | resource  | expected_player1_resources| expected_player2_resources |  expected_player3_resources  | player4_resources | expected_player4_resources  |
      | ""                | "WOOL:WOOL"       | "LUMBER:WOOL"     | "WOOL"    | "WOOL:WOOL:WOOL:WOOL"     | ""                         |  "LUMBER"                    | "WOOL"            | ""                          |


  Scenario Outline: Successfully take resource with 2 players
    Given Player 1 has started their turn in a 2 person game
    And player 1 has resources <player1_resources>
    And player 2 has resources <player2_resources>
    When player activates a Monopoly for <resource>
    Then player 1 should have resources <expected_player1_resources>
    And player 2 should have resources <expected_player2_resources>

    Examples:
      | player1_resources | player2_resources | resource  | expected_player1_resources| expected_player2_resources |
      | ""                | "WOOL:WOOL"       | "WOOL"    | "WOOL:WOOL"               | ""                         |
      | ""                | "LUMBER:LUMBER"   | "LUMBER"  | "LUMBER:LUMBER"           | ""                         |
      | ""                | "GRAIN:GRAIN"     | "GRAIN"   | "GRAIN:GRAIN"             | ""                         |
      | ""                | "BRICK:BRICK"     | "BRICK"   | "BRICK:BRICK"             | ""                         |
      | ""                | "ORE:ORE"         | "ORE"     | "ORE:ORE"                 | ""                         |
      | ""                | "ORE"             | "ORE"     | "ORE"                     | ""                         |
      | "BRICK"           | "WOOL"            | "WOOL"    | "BRICK:WOOL"              | ""                         |
      | "BRICK"           | "LUMBER:LUMBER"   | "LUMBER"  | "BRICK:LUMBER:LUMBER"     | ""                         |
      | "BRICK"           | "GRAIN"           | "WOOL"    | "BRICK"                   | "GRAIN"                    |
      | "BRICK"           | "LUMBER:ORE"      | "ORE"     | "BRICK:ORE"               | "LUMBER"                   |
      | "BRICK:ORE"       | "WOOL:WOOL:GRAIN:ORE" | "GRAIN"| "BRICK:ORE:GRAIN"        | "WOOL:WOOL:ORE"            |




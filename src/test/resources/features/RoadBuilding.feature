Feature: Play Road Building
  Completes the Road Building card effect

  Scenario Outline: Successfully build 2 roads for the in-turn player
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And player 1 has a Road Building Card
    When the player selects the first road to be placed at <index 1> and <index 2>
    And the player selects the second road to be placed at <index 3> and <index 4>
    And player activates a Road Building card
    Then player 1 should have resources <expected_player1_resources>
    And the roads should be placed on the board
    And the road building card should be marked as played

    Examples:
      | player1_resources | expected_player1_resources| index 1 | index 2 | index 3 | index 4 |
      | "WOOL:WOOL:WOOL"  | "WOOL:WOOL:WOOL"          | 6       | 24      | 24     | 26       |

    Scenario Outline: Invalid Intersection Selection for Road Building
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And player 1 has a Road Building Card
    When the player selects the first road to be placed at <index 1> and <index 2>
    And the player selects the second road to be placed at <index 3> and <index 4>
    And player activates a Road Building card
    Then player 1 should have resources <expected_player1_resources>
    And the roads should not be placed on the board
    And the road building card should not be marked as played

      Examples:
    | player1_resources | expected_player1_resources| index 1 | index 2 | index 3 | index 4 |
    | "WOOL:WOOL:WOOL"  | "WOOL:WOOL:WOOL"          | 6       | 24      | 24     | 25       |
    | "WOOL:WOOL:WOOL"  | "WOOL:WOOL:WOOL"          | 6       | 25      | 24     | 26       |

  Scenario Outline: Invalid Placement, Road already exists, Road Building
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And player 1 has a Road Building Card
    When the player selects the first road to be placed at <index 1> and <index 2>
    And the player selects the second road to be placed at <index 3> and <index 4>
    And player activates a Road Building card
    Then player 1 should have resources <expected_player1_resources>
    And the roads should not be placed on the board
    And the road building card should not be marked as played

    Examples:
      | player1_resources | expected_player1_resources| index 1 | index 2 | index 3 | index 4 |
      | "WOOL:WOOL:WOOL"  | "WOOL:WOOL:WOOL"          | 6       | 24      | 6     | 24        |



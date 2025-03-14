Feature: Buying Road
  As a player, in order to expand my territory, I want to buy a road

  Scenario Outline: Valid road purchase
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And the in-turn player numRoads is greater than zero
    When player 1 attempts to buy a road at indexes <index1> and <index2>
    Then the road should be placed at the indexes
    And player 1 should have resources <expected_player1_resources>

    Examples:
    | player1_resources | index1 | index2 | expected_player1_resources |
    | "BRICK:LUMBER"    | 6      | 24     |  ""                        |

  Scenario Outline: Invalid Resources to Buy Road
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And the in-turn player numRoads is greater than zero
    When player 1 attempts to buy a road at indexes <index1> and <index2>
    Then the road should not be placed
    And player 1 should have resources <expected_player1_resources>

    Examples:
      | player1_resources | index1 | index2 | expected_player1_resources |
      | "BRICK"           | 6      | 24     |  "BRICK"                   |

  Scenario Outline: Invalid Road Placement by Intersection
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And the in-turn player numRoads is greater than zero
    When player 1 attempts to buy a road at indexes <index1> and <index2>
    Then the road should not be placed
    And player 1 should have resources <expected_player1_resources>

    Examples:
      | player1_resources | index1 | index2 | expected_player1_resources |
      | "BRICK:LUMBER"    | 6      | 25     |  "BRICK:LUMBER"            |

    Scenario Outline: Invalid Road Placement by Invalid Road Owner
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
      And the in-turn player numRoads is greater than zero
    When player 1 attempts to buy a road at indexes <index1> and <index2>
      And the in-turn player does not own the connecting road
    Then the road should not be placed
    And player 1 should have resources <expected_player1_resources>

      Examples:
        | player1_resources | index1 | index2 | expected_player1_resources |
        | "BRICK:LUMBER"    | 29      | 31     |  "BRICK:LUMBER"            |

    Scenario Outline: Invalid Road Placement by Max Roads Reached
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And the in-turn player numRoads is zero
    When player 1 attempts to buy a road at indexes <index1> and <index2>
    Then the road should not be placed
    And player 1 should have resources <expected_player1_resources>

    Examples:
      | player1_resources | index1 | index2 | expected_player1_resources |
      | "BRICK:LUMBER"    | 6      | 24     |  "BRICK:LUMBER"            |

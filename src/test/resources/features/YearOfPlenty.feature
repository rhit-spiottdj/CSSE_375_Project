Feature: Play Year of Plenty
  Completes the Year of Plenty card effect

  Scenario Outline: Sufficient Resources to Give Out
    Given Player 1 has started their turn in a 2 person game
    And player 1 has resources <player1_resources>
    When player activates a Year of Plenty for <resource1> and <resource2>
    Then player 1 should have resources <expected_player1_resources>

    Examples:
      | player1_resources | resource1   | resource2   | expected_player1_resources|
      | ""                | "GRAIN"     | "GRAIN"     | "GRAIN: GRAIN"            |
      | ""                | "ORE"       | "ORE"       | "ORE: ORE"                |
      | ""                | "BRICK"     | "BRICK"     | "BRICK: BRICK"            |
      | ""                | "LUMBER"    | "LUMBER"    | "LUMBER: LUMBER"          |
      | ""                | "WOOL"      | "WOOL"      | "WOOL: WOOL"              |
      | ""                | "GRAIN"     | "ORE"       | "GRAIN: ORE"              |
      | ""                | "WOOL"      | "BRICK"     | "WOOL: BRICK"             |
      | ""                | "LUMBER"    | "GRAIN"     | "LUMBER: GRAIN"           |
      | "GRAIN"           | "GRAIN"     | "GRAIN"     | "GRAIN:GRAIN:GRAIN"       |
      | "GRAIN: GRAIN"    | "GRAIN"     | "GRAIN"     | "GRAIN:GRAIN:GRAIN:GRAIN" |

  Scenario Outline: Insufficient Resources to Give Out
    Given Player 1 has started their turn in a 2 person game
    And player 1 has resources <player1_resources>
    And player 2 has all resources except <player2_resources>
    When player activates a Year of Plenty for <resource1> and <resource2>
    Then player 1 should have resources <player1_resources>

    Examples:
      | player1_resources | player2_resources   | resource1   | resource2   |
      | ""                | ""                  | "GRAIN"     | "GRAIN"     |
      | ""                | ""                  | "ORE"       | "ORE"       |
      | ""                | ""                  | "BRICK"     | "BRICK"     |
      | ""                | ""                  | "LUMBER"    | "LUMBER"    |
      | ""                | ""                  | "WOOL"      | "WOOL"      |
      | ""                | ""                  | "GRAIN"     | "ORE"       |
      | ""                | ""                  | "WOOL"      | "BRICK"     |
      | ""                | ""                  | "LUMBER"    | "GRAIN"     |
      | ""                | ""                  | "GRAIN"     | "GRAIN"     |
      | ""                | ""                  | "GRAIN"     | "GRAIN"     |
      | "LUMBER:LUMBER"   | "LUMBER:LUMBER"     | "LUMBER"    | "LUMBER"    |
      | ""                | "LUMBER"            | "LUMBER"    | "LUMBER"    |

  Scenario Outline: Nearly Insufficent Resources to Give Out
    Given Player 1 has started their turn in a 2 person game
    And player 1 has resources <player1_resources>
    And player 2 has all resources except <player2_resources>
    When player activates a Year of Plenty for <resource1> and <resource2>
    Then player 1 should have resources <expected_player1_resources>

    Examples:
      | player1_resources | player2_resources   | resource1   | resource2   | expected_player1_resources |
      | ""                | "LUMBER:LUMBER"     | "LUMBER"    | "LUMBER"    | "LUMBER:LUMBER"            |
      | ""                | "LUMBER:WOOL"       | "LUMBER"    | "WOOL"      | "LUMBER:WOOL"              |
      | "WOOL"            | "LUMBER:WOOL:WOOL"  | "WOOL"      | "LUMBER"    | "WOOL:WOOL:LUMBER"         |
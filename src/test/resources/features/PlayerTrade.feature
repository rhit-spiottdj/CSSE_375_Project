Feature: Player Trade
  Trades between players

  Scenario Outline: Successful trade between two players
    Given a new game with 2 players trade
    And player 1 has resources <player1_resources>
    And player 2 has resources <player2_resources>
    When player 1 trades <player1_trade> with player 2 for <player2_trade>
    Then player 1 should have resources <expected_player1_resources>
    And player 2 should have resources <expected_player2_resources>

    Examples:
      | player1_resources     | player2_resources     | player1_trade       | player2_trade       | expected_player1_resources | expected_player2_resources |
      | "LUMBER: BRICK"       | "WOOL: GRAIN"         | "LUMBER: BRICK"     | "WOOL: GRAIN"       | "WOOL: GRAIN"              | "LUMBER: BRICK"            |
      | "LUMBER: BRICK: ORE"  | "WOOL: GRAIN: ORE"    | "LUMBER: BRICK"     | "WOOL: GRAIN"       | "WOOL: GRAIN: ORE"         | "LUMBER: BRICK: ORE"       |
      | "LUMBER: BRICK: WOOL" | "GRAIN: ORE: WOOL"    | "LUMBER: WOOL"      | "ORE: WOOL"         | "BRICK: ORE: WOOL"         | "LUMBER: WOOL: GRAIN"      |
      | "LUMBER: BRICK: GRAIN"| "WOOL: GRAIN: ORE"    | "LUMBER: BRICK"     | "WOOL: GRAIN"       | "WOOL: GRAIN: GRAIN"       | "LUMBER: BRICK: ORE"       |
      | "LUMBER: GRAIN: WOOL" | "BRICK: GRAIN: ORE"   | "LUMBER: GRAIN"     | "BRICK: ORE"        | "WOOL: BRICK: ORE"         | "LUMBER: GRAIN: GRAIN"     |
      | "BRICK: ORE: GRAIN"   | "LUMBER: WOOL: GRAIN" | "BRICK: GRAIN"      | "LUMBER: WOOL"      | "ORE: LUMBER: WOOL"        | "BRICK: GRAIN: GRAIN"      |

  Scenario Outline: Trade fails due to insufficient resources
    Given a new game with 2 players trade
    And player 1 has resources <player1_resources>
    And player 2 has resources <player2_resources>
    When player 1 trades <player1_trade> with player 2 for <player2_trade>
    Then the trade should fail
    And player 1 should have resources <expected_player1_resources>
    And player 2 should have resources <expected_player2_resources>

    Examples:
      | player1_resources     | player2_resources     | player1_trade       | player2_trade       | expected_player1_resources | expected_player2_resources |
      | "LUMBER"              | "WOOL: GRAIN"         | "LUMBER: BRICK"     | "WOOL: GRAIN"       | "LUMBER"                   | "WOOL: GRAIN"              |
      | "LUMBER: BRICK"       | "WOOL"                | "LUMBER: BRICK"     | "WOOL: GRAIN"       | "LUMBER: BRICK"            | "WOOL"                     |
      | "LUMBER: BRICK"       | "WOOL: GRAIN"         | "LUMBER: BRICK: ORE"| "WOOL: GRAIN"       | "LUMBER: BRICK"            | "WOOL: GRAIN"              |
      | "LUMBER"              | "WOOL: GRAIN: ORE"    | "LUMBER: BRICK"     | "WOOL: GRAIN"       | "LUMBER"                   | "WOOL: GRAIN: ORE"         |
      | "BRICK: WOOL"         | "LUMBER: GRAIN"       | "BRICK: WOOL: ORE"  | "LUMBER: GRAIN"     | "BRICK: WOOL"              | "LUMBER: GRAIN"            |
      | "ORE: WOOL"           | "BRICK: LUMBER: GRAIN"| "ORE: WOOL: BRICK"  | "LUMBER: GRAIN"     | "ORE: WOOL"                | "BRICK: LUMBER: GRAIN"     |

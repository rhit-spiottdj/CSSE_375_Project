Feature: Dice Rolling
  Receiving resources based on dice rolls, except when the robber blocks you.

  Scenario Outline: Roll dice and distribute resources
    Given a new game with <num_players> players dice
    And player1 has placed a settlement at intersection <settlement1>
    And player2 has placed a settlement at intersection <settlement2>
    And the dice roll is <dice_roll>
    When the dice is rolled
    Then player1 should receive <player1_resources>
    And player2 should receive <player2_resources>

    Examples:
      | num_players | settlement1 | settlement2 | dice_roll | player1_resources | player2_resources |
      | 2           | 5           | 10          | 3         | "NONE"            | "NONE"            |
      | 2           | 2           | 3           | 5         | "WOOL"            | "NONE"            |
      | 2           | 4           | 5           | 8         | "ORE"             | "NONE"            |

  Scenario Outline: Roll dice and handle robber
    Given a new game with <num_players> players dice
    And player1 has placed a settlement at intersection <settlement1>
    And player2 has placed a settlement at intersection <settlement2>
    And the robber is placed on hex <hex_with_robber>
    And the dice roll is <dice_roll>
    When the dice is rolled
    Then player1 should receive <player1_resources>
    And player2 should receive <player2_resources>

    Examples:
      | num_players | settlement1 | settlement2 | hex_with_robber | dice_roll | player1_resources | player2_resources |
      | 2           | 2           | 3           |     7           | 5         | "NONE"            | "NONE"           |
      | 2           | 4           | 5           |     3           | 8         | "NONE"            | "NONE"          |

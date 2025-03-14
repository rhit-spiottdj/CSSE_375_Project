Feature: Longest Road
  Calculate the longest road victory points.

  Scenario Outline: Longest road is awarded correctly
    Given a new game with <num_players> players road
    And player1 places a settlement at intersection <settlement1>
    And player2 places a settlement at intersection <settlement2>
    And player1 has placed roads at intersections <player1_roads>
    And player2 has placed roads at intersections <player2_roads>
    When the longest road is calculated
    Then player1 should have longest road

    Examples:
      | num_players | settlement1 | settlement2 | player1_roads                          | player2_roads       |
      | 2           | 0           | 4           | "0-6, 6-22, 22-23, 23-40, 40-42, 42-51"| "4-10, 10-28"       |
      | 2           | 0           | 4           | "0-12, 12-13, 13-32, 32-34, 34-48"     | "4-18, 18-2"        |

  Scenario Outline: Longest road is awarded correctly, player two wins
    Given a new game with <num_players> players road
    And player1 places a settlement at intersection <settlement1>
    And player2 places a settlement at intersection <settlement2>
    And player1 has placed roads at intersections <player1_roads>
    And player2 has placed roads at intersections <player2_roads>
    When the longest road is calculated
    Then player2 should have longest road

    Examples:
      | num_players | settlement1 | settlement2 | player1_roads                         | player2_roads                              |
      | 2           | 0           | 4           | "0-6, 6-22"                           | "4-10, 10-28, 28-8, 8-2"                   |
      | 2           | 0           | 22           | "0-12, 12-13, 13-32, 32-34, 34-48"   | "22-23, 23-40, 40-42, 42-51, 51-43, 43-53" |

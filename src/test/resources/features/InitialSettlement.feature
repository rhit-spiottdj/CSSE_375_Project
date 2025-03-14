Feature: Initial Settlement Placement
  The player places a settlements for free at the beginning of the game

  Scenario Outline: Valid initial settlement placement
    Given a new game board
    And the player "<player>" is ready to place their settlement
    When the player tries to place a settlement at intersection <index>
    Then the settlement is successfully placed at intersection <index>

    Examples:
      | player  | index |
      | Player1 | 0     |
      | Player2 | 5     |
      | Player1 | 10    |
      | Player2 | 15    |
      | Player1 | 20    |
      | Player2 | 25    |

  Scenario Outline: Invalid initial settlement placement
    Given a new game board
    And the player "<player>" is ready to place their settlement
    When the player tries to place a settlement at intersection <index>
    Then a settlement was not placed

    Examples:
      | player  | index |
      | Player1 | -1    |
      | Player2 | 54    |
      | Player1 | 100   |
      | Player2 | -5    |

  Scenario Outline: Settlement placement violation
    Given a new game board with settlements at intersections <existing_indexes>
    And the player "<player>" is ready to place their settlement
    When the player tries to place a settlement at intersection <index>
    Then a settlement was not placed

    Examples:
      | player  | existing_indexes | index |
      | Player1 | [0, 1, 2]         | 1    |
      | Player2 | [5, 6, 7]         | 6    |
      | Player1 | [10, 11, 12]      | 11   |
      | Player2 | [15, 16, 17]      | 16   |

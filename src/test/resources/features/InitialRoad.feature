Feature: Initial Road Placement
  The player places a road for free at the beginning of the game

  Scenario Outline: Valid initial road placement
    Given a newly set game board
    And A settlement is placed intersection <index>
    And the player "<player>" is ready to place their road
    When the player tries to place a settlement at intersection <index1> and <index2>
    Then the road is successfully placed at intersection <index1> and <index2>

    Examples:
      | index | player  | index1 | index2 |
      | 0     | Player1 | 0      | 1      |
      | 5     | Player2 | 5      | 6      |
      | 10    | Player1 | 10     | 11     |
      | 15    | Player2 | 15     | 16     |
      | 20    | Player1 | 20     | 21     |
      | 25    | Player2 | 25     | 26     |

  Scenario Outline: Invalid initial road placement
    Given a newly set game board
    And A settlement is placed intersection <index>
    And the player "<player>" is ready to place their road
    When the player tries to place a settlement at intersection <index1> and <index2>
    Then the road is not placed

    Examples:
      | index | player  | index1 | index2 |
      | 0     | Player1 | 0      | 2      |
      | 5     | Player2 | 5      | 7      |
      | 10    | Player1 | 10     | 12     |
      | 15    | Player2 | 15     | 17     |
      | 20    | Player1 | 20     | 22     |
      | 25    | Player2 | 25     | 27     |
      | 0     | Player1 | -1     | 54     |
      | 5     | Player2 | 0      | 54     |

    Scenario Outline: Road placement where road is already placed
        Given a newly set game board
        And A settlement is placed intersection <index>
        And the player "<player>" is ready to place their road
        And the player tries to place a settlement at intersection <index1> and <index2>
        When the player tries to place a settlement at intersection <index1> and <index2>
        Then the road is not placed

        Examples:
            | index | player  | index1 | index2 |
            | 0     | Player1 | 0      | 1      |
            | 5     | Player2 | 5      | 6      |
            | 10    | Player1 | 10     | 11     |
            | 15    | Player2 | 15     | 16     |
            | 20    | Player1 | 20     | 21     |
            | 25    | Player2 | 25     | 26     |

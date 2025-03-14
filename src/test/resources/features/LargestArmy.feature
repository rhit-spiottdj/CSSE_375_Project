Feature: Largest Army
  Calculate the Largest Army victory points.

  Scenario Outline: Largest army player two wins
    Given a new game with <num_players> players army
    When player 0 has played <player1_knights> knights
    And player 1 has played <player2_knights> knights
    Then player 1 should have largest army

    Examples:
      | num_players | player1_knights | player2_knights |
      | 2           | 3               | 4               |
      | 2           | 0               | 3               |
      | 2           | 2               | 3               |
      | 3           | 3               | 4               |
      | 3           | 2               | 5               |
      | 4           | 4               | 5               |
      | 4           | 3               | 6               |
      | 4           | 4               | 5               |

  Scenario Outline: Largest army player one wins
    Given a new game with <num_players> players army
    When player 0 has played <player1_knights> knights
    And player 1 has played <player2_knights> knights
    Then player 0 should have largest army

    Examples:
      | num_players | player1_knights | player2_knights |
      | 2           | 3               | 1               |
      | 2           | 3               | 3               |
      | 2           | 5               | 4               |
      | 3           | 4               | 3               |
      | 3           | 6               | 4               |
      | 4           | 5               | 4               |
      | 4           | 6               | 5               |

  Scenario Outline: No largest army
    Given a new game with <num_players> players army
    When player 0 has played <player1_knights> knights
    And player 1 has played <player2_knights> knights
    Then no player should have largest army

    Examples:
      | num_players | player1_knights | player2_knights |
      | 2           | 0               | 1               |
      | 2           | 0               | 0               |
      | 3           | 2               | 2               |
      | 3           | 1               | 0               |
      | 4           | 2               | 2               |
      | 4           | 0               | 0               |

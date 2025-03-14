Feature: Winning the game with enough victory points
  As a player, i want to keep track of my victory points so I know when I win. The game should tell me when I win.

  Scenario: Calculate Victory Points for new Settlement
    Given Player 1 has started their turn in a 2 person game
    And the initial setup has been completed
    And player 1 has resources "BRICK:LUMBER:WOOL:GRAIN"
    When player 1 attempts to buy a settlement at index 24
    Then the settlement should be placed at the index
    And the player should have 3 victory points
    And the game should not be over

  Scenario: Calculate Victory Points for new City
    Given Player 1 has started their turn in a 2 person game
    And the initial setup has been completed
    And player 1 has resources "ORE:ORE:ORE:GRAIN:GRAIN"
    When the in-turn player attempts to buy a city at the index 0
    Then the player should have 3 victory points
    And the game should not be over

  Scenario Outline: Calculate Victory Points for new Victory Point Card
    Given Player 1 has started their turn in a 2 person game
    And the initial setup has been completed
    When the in-turn player buys <num_cards> victory point cards
    Then the player should have <num_points> victory points
    And the game should not be over

    Examples:
      | num_cards | num_points |
      | 1         | 3          |
      | 2         | 4          |
      | 3         | 5          |
      | 4         | 6          |
      | 5         | 7          |

  Scenario: Player exceeds win threshold
    Given Player 1 has started their turn in a 2 person game
    And the initial setup has been completed
    And the in-turn player has 3 victory point cards
    And player1 has placed roads at intersections "6-22, 22-23, 23-40, 40-42"
    When the player has played 3 knights
    Then the player should have 11 victory points
    And the game should be over



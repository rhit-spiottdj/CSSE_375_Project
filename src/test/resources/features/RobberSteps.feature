Feature: Robber Actions
  As a player, in order to increase my chances of winning the game, I want to be able to perform actions with the robber

  Scenario Outline: Move Robber Successfully
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    When player 1 attempts to move the robber to hex index <hex>
    Then the robber should be on hex index <hex>

    Examples:
    | hex |
    | 1   |
    | 2   |
    | 3   |
    | 4   |
    | 5   |
    | 6   |
    | 7   |
    | 8   |
    | 9   |
    | 10  |
    | 11  |
    | 12  |
    | 13  |
    | 14  |
    | 15  |
    | 16  |
    | 17  |
    | 18  |

  Scenario Outline: Move Robber Unsuccessfully - Bad Index
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    When player 1 attempts to move the robber to hex index <hex>
    Then the robber should not be on hex index <hex>
    And the robber should be on hex index 0
    And an invalid index message should be caught

    Examples:
      | hex |
      | -1  |
      | 19  |
      | 20  |

  Scenario: Move Robber Unsuccessfully - Same Index
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    When player 1 attempts to move the robber to hex index 0
    Then the robber should be on hex index 0
    And a same index message should be caught

    Scenario: Robber Steal Successfully
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And the robber is on hex index 0
      And player 1 has resources "BRICK:LUMBER:WOOL:GRAIN"
      And player 2 has resources "BRICK:BRICK"
    When player 1 attempts to move the robber to hex index 4
    Then player 1 should attempt to steal from player 2
      And player 1 should have 1 more resource than before
      And player 2 should have 1 less resource than before

  Scenario: Robber Steal Unsuccessfully - No Resources
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    And player 1 has resources "BRICK:LUMBER:WOOL:GRAIN"
    And player 2 has resources ""
    When player 1 attempts to move the robber to hex index 4
    Then player 1 should attempt to steal from player 2
    And player 1 should have the same amount of resources as before
    And player 2 should have the same amount of resources as before
    And a no resources message should be caught


  Scenario: Robber Discard Successfully
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    When a 7 is rolled
    And player 1 has resources "BRICK:LUMBER:WOOL:GRAIN:ORE:BRICK:BRICK:BRICK"
    And player 2 has resources "BRICK:BRICK"
    Then player 1 should discard half of their resources
    And player 1 should have 4 resources
    And player 2 should have 2 resources

    Scenario: Robber Discard Unsuccessfully - Not Enough Resources for Discard
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    When a 7 is rolled
    And player 1 has resources "BRICK:LUMBER:WOOL:GRAIN"
    And player 2 has resources "BRICK:BRICK"
    Then player 1 should have 4 resources
    And player 2 should have 2 resources
      And no resources should be discarded


    Scenario: Robber Steal Unsuccessfully - No Player
    Given Player 1 has started their turn in a 3 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    And player 1 has resources "BRICK:LUMBER:WOOL:GRAIN"
    And player 2 has resources "BRICK:BRICK"
    And player 3 has resources ""
    When player 1 attempts to move the robber to hex index 5
    Then player 1 should attempt to steal from no player
      And a no player message should be caught
    And player 1 should have the same amount of resources as before



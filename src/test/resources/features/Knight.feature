Feature: Play Knight
  Completes the Knight card effect in Catan, moving the robber and stealing a resource

  Scenario: Successfully play knight and steal a single resource
    Given Player 1 has started their turn in a 2 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    And player 1 has a knight card
    And player 1 has resources "BRICK:WOOL:GRAIN:ORE"
    And player 2 has resources "BRICK:BRICK"
    When player 1 activates the knight card and moves the robber to hex index 4
    Then the robber should be at hex index 4
    And player 1 should have 1 more resource than before
    And player 2 should have 1 less resource than before

    Scenario: Unsuccessfully play knight - player has no knight cards
    Given Player 1 has started their turn in a 2 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    And player 1 has no knight cards
      And player 1 has resources "BRICK:WOOL:GRAIN:ORE"
      And player 2 has resources "BRICK:BRICK"
    When player 1 activates the knight card and moves the robber to hex index 4
    Then the robber should be at hex index 0
      And an exception for no knight cards should be thrown
    And player 1 should have the same resources as before
    And player 2 should have the same resources as before

  Scenario: Unsuccessfully play knight - player has no resources
    Given Player 1 has started their turn in a 2 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    And player 1 has a knight card
    And player 1 has resources ""
    And player 2 has resources "BRICK:BRICK"
    When player 1 activates the knight card and moves the robber to hex index 4
    Then the robber should be at hex index 4
    And a no resources exception should be thrown

    Scenario: Unsuccessfully play knight - no player to steal from
    Given Player 1 has started their turn in a 2 person game
    And the initial setup has been completed
    And the robber is on hex index 0
    And player 1 has a knight card
    And player 1 has resources "BRICK:WOOL:GRAIN:ORE"
    And player 2 has resources "BRICK:BRICK"
    When player 1 activates the knight card and moves the robber to hex 5 where there is no player
    Then the robber should be at hex index 5
    And a no player to steal from exception should be thrown
    And player 1 should have the same resources as before
    And player 2 should have the same resources as before

 Feature: Buying Settlement
    As a player, in order to expand my territory, I want to buy a settlement

    Scenario Outline: Valid Index and Resources for Buying a Settlement
    Given Player 1 has started their turn in a 3 person game
      And the initial setup has been completed
    And player 1 has resources <player1_resources>
    And player 2 has resources <player2_resources>
    And player 3 has resources <player3_resources>
      And the players number of settlements is greater than zero
    When player 1 attempts to buy a settlement at index <index>
    Then the settlement should be placed at the index
    And player 1 should have resources <expected_player1_resources>
    And player 2 should have resources <expected_player2_resources>
    And player 3 should have resources <expected_player3_resources>

    Examples:
        | player1_resources         | player2_resources             | player3_resources | index  | expected_player1_resources| expected_player2_resources | expected_player3_resources |
        | "BRICK:LUMBER:WOOL:GRAIN" | "BRICK:BRICK"                 | "LUMBER:WOOL"     | 24     | ""                        | "BRICK:BRICK"              | "LUMBER:WOOL"              |


    Scenario Outline: Invalid Resources for Buying a Settlement
    Given Player 1 has started their turn in a 3 person game
      And the initial setup has been completed
      And player 1 has resources <player1_resources>
      And player 2 has resources <player2_resources>
      And player 3 has resources <player3_resources>
      And the players number of settlements is greater than zero
      When player 1 attempts to buy a settlement at index <index>
      Then the settlement should not be placed at the index
      And player 1 should have resources <expected_player1_resources>
      And player 2 should have resources <expected_player2_resources>
      And player 3 should have resources <expected_player3_resources>

      Examples:
      | player1_resources         | player2_resources             | player3_resources | index  | expected_player1_resources| expected_player2_resources | expected_player3_resources |
      | "BRICK:LUMBER:WOOL"       | "BRICK:BRICK"                 | "LUMBER:WOOL"     | 24     | "BRICK:LUMBER:WOOL"        | "BRICK:BRICK"              | "LUMBER:WOOL"              |


    Scenario Outline: Invalid Owner of Road for Buying a Settlement
      Given Player 1 has started their turn in a 3 person game
      And the initial setup has been completed
      And player 1 has resources <player1_resources>
      And player 2 has resources <player2_resources>
      And player 3 has resources <player3_resources>
      And the players number of settlements is greater than zero
      When player 1 attempts to buy a settlement at index <index>
      And the in-turn player does not own the road at the index of the settlement
      Then the settlement should not be placed at the index
      And player 1 should have resources <expected_player1_resources>
      And player 2 should have resources <expected_player2_resources>
      And player 3 should have resources <expected_player3_resources>

        Examples:
        | player1_resources         | player2_resources             | player3_resources | index  | expected_player1_resources| expected_player2_resources | expected_player3_resources |
        | "BRICK:LUMBER:WOOL:GRAIN" | "BRICK:BRICK"                 | "LUMBER:WOOL"     | 29     | "BRICK:LUMBER:WOOL:GRAIN" | "BRICK:BRICK"              | "LUMBER:WOOL"              |

      Scenario Outline: Invalid Settlement - Max number of settlements reached
        Given Player 1 has started their turn in a 3 person game
        And the initial setup has been completed
        And player 1 has resources <player1_resources>
        And the players number of settlements is zero
        When player 1 attempts to buy a settlement at index <index>
        Then the settlement should not be placed at the index
        And player 1 should have resources <expected_player1_resources>

        Examples:
        | player1_resources         | index  | expected_player1_resources|
        | "BRICK:LUMBER:WOOL:GRAIN" | 24     | "BRICK:LUMBER:WOOL:GRAIN" |
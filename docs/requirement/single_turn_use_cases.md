Use Case Assignments:
- Basic Flow: Do nothing during turn
  - Alt Flow: Rolls a 7 (Robber) - Brady
- Build roads, settlements, and cities - Brady
- Trade resources with other players - Dom
- Trade with the bank (4:1) - Dom
- Trade with a port (3:1 or 2:1) - Dom
- Buying Development Card - Christian
- Playing Development Card - Christian

# UC2. Single Turn of Game

### Actor: Player (User)

### Description

This use case describes the basic flow of a single turn of the game without any additional actions.

### Basic Flow
1. Player rolls the dice by clicking the "Roll Dice" button.
2. System displays the number rolled, the domain, and the buttons "Build", "Trade", "Development Card", and "End Turn".
    1. Based on the number rolled, the System will distribute resources to the players based on the locations of their settlements and/or cities.
4. Player clicks the "End Turn" button.
5. System ends the player's turn and begins the next player's turn.

### Alternate Flows

#### AF1 - BF Step 1: Player rolls a 7
1. Player rolls the dice by clicking the "Roll Dice" button.
2. System decides the number rolled is a 7 and displays the domain and the button "Move Robber".
3. Player clicks the "Move Robber" button.
4. System displays the game domain and prompts the user to select a location for the robber.
5. Player selects a location for the robber.
6. System moves the robber to the selected location and prompts the player to select a player to steal from (if any).
7. Player selects a player to steal from.
8. System steals a random resource from the selected player and gives it to the current player.
9. Return to BF at step 3.

### Exceptions

#### AF1-2 Step 5: Player selects an invalid location
1. System displays an error message saying the location selected is invalid.
2. User confirms.
3. Return to AF step 4.

#### AF1-6 Step 7: Player selects a player with no resources
1. System displays an error message saying the selected player has no resources to steal.
2. User confirms.
3. Return to AF step 9.

### Preconditions

- The game has been set up and the players have been assigned their colors and starting resources as specificed in UC1.

### Postconditions
- The player's turn has ended and the next player's turn has begun.

### System or subsystem
- Game

### Other Stakeholders
- The other players in the game.

### Special Requirements:
- The game shall audit the number of resourses each player has when a 7 is rolled and the robber is moved. If a player has more than 7 resources, the player must discard half of their resources.
- The game shall not allow a player to roll the dice more than once per turn.

****

# UC3. Build A Structure

### Actor: Player (User)

### Description

This use case describes the process of a player building a road (or alternatively a settlement or city) on the game domain.

### Basic Flow
1. Player clicks the "Build" button.
2. System displays the following options:
   1. Road
   2. Settlement
   3. City
   4. Cancel
3. User selects Road.
4. System displays the game domain and prompts the user to select a location for the road.
5. Player selects a location for the road.
6. System places the road on the game domain and deducts the necessary resources from the player's inventory.
7. 

### Alternate Flows

#### AF1 - BF Step 3: Player builds a Settlement
1. Player clicks the "Build" button.
2. System displays the following options:
   1. Road
   2. Settlement
   3. City
   4. Cancel
3. User selects Settlement.
4. System displays the game domain and prompts the user to select a location for the settlement.
5. Player selects a location for the settlement.
6. System places the settlement on the game domain and deducts the necessary resources from the player's inventory.
7. Return to BF at step 2.

#### AF2 - BF Step 3: Player builds a City
1. Player clicks the "Build" button.
2. System displays the following options:
   1. Road
   2. Settlement
   3. City
   4. Cancel
3. User selects City.
4. System displays the game domain and prompts the user to select a location for the City from an existing Settlement.
5. Player selects a location for the City.
6. System places the City on the game domain and deducts the necessary resources from the player's inventory.
7. Return to BF at step 2.

### Exceptions

#### AF1-2 Step 5: Player selects an invalid location
1. System displays an error message saying the location selected is invalid.
2. User confirms.
3. Return to AF step 4.

#### AF1-2 Step 6: Player does not have enough resources to build
1. System displays an error message saying the player does not have enough resources to build the selected item.
2. User confirms.
3. Return to AF step 2.

### AF Flow 1-2 Step 6: Player places a structure that wins the game
1. System displays a message saying the player has won the game.
2. User confirms.
3. Game ends, system returns to the main menu.


### Preconditions

- The game has been set up and the players have been assigned their colors and starting resources as specificed in UC1.
- The players turn has begun.
- The player has selected the "Build" button from UC2.

### Postconditions

- The player has built a road, settlement, or city on the game domain.

### System or subsystem
- Game

### Other Stakeholders
- Other players in the game.

### Special Requirements:
- The Game shall not allow a player to build a road, settlement, or city on a location that is already occupied by another player's structure.

****

# UC4. Trade Resources with Other Players

### Actor: Player1 (User whose turn it), Player2 (User who is being traded with)

### Description

This use case describes the basic flow of one player trading resources with another player.

### Basic Flow
1. Player1 selects the "Trade" option.
2. System displays the following:
   1. Trade with other players.
   2. Trade with a port.
   3. Trade with the bank.
3. Player1 selects "Trade with other players".
4. System displays the following:
   1. All the other players to trade with.
   2. Cancel option.
5. Player1 selects who to trade with, Player2.
6. System displays the following:
   1. The available resources to be traded for Player1.
   2. Confirm option.
   3. Cancel option.
7. Player1 selects the resources to be traded.
8. Player1 selects the confirm option.
9. System displays the following:
   1. The available resources to be traded for Player2.
   2. The resources selected by Player1 to be traded.
   3. Confirm option.
   4. Cancel option.
10. Player2 selects the resources to be traded.
11. Player2 selects the confirm option.
12. System displays the following:
    1. The resources selected by Player2 to be traded.
    2. Confirm option.
    3. Cancel option.
13. Player1 selects the confirm option.
14. System trades the appropriate resources to the appropriate players.

### Alternate Flows

#### AF1 - BF Any step: Player1 or Player2 select the cancel option.
1. System returns to the state of UC2 BF Step 2.

### Exceptions

#### EF1 - BF Step1: Player1 selects "Trade" option with no resources to trade
1. System displays error message stating that Player1 has no resources to trade.
2. Player1 selects the confirm option.
3. System returns to the state of UC2 BF Step 2.

#### EF2 - BF Step 5: Player1 selects a player with no resources to trade.
1. System displays error message stating that Player2 has no resources to trade.
2. Player1 selects the confirm option.
3. System returns to the state of UC2 BF Step 2.

#### EF3 - BF Step 10: Player2 selects the same type of resource to trade as Player1
1. System displays error message stating that Player2 must pick a different type of resource to trade.
2. Player2 selects the confirm option.
3. System returns to BF Step 10.

### Preconditions

- The game has been set up and the players have been assigned their colors and starting resources as specificed in UC1.
- Player1's turn has begun
- Both Player1 and Player2 have resources to trade

### Postconditions
- Player1 and Player2 have traded resources

### System or subsystem
- Game

### Other Stakeholders
- The other players in the game.

### Special Requirements:
- The game will not allow Player1 and Player2 to trade resources of the same type.

****

# UC5. Trade Resources with 2:1 Port

### Actor: Player (User)

### Description

This use case describes the basic flow of the user trading with a 2:1 port.

### Basic Flow
1. Player selects the "Trade" option.
2. System displays the following:
   1. Trade with other players.
   2. Trade with a port.
   3. Trade with the bank.
3. Player selects "Trade with a port".
4. System displays the following:
   1. All ports where the player has a settlement or city.
   2. Cancel option.
5. Player selects which port to trade with.
6. System displays the following:
   1. The type of resource that the selected port accepts.
   2. The player's available resources.
   3. Confirm option.
   4. Cancel option.
7. Player selects two resources of the same type to trade.
8. Player selects the confirm option.
9. System removes the two selected resources from the player's deck.
10. System displays the following:
    1. The types of resource that the player can receive.
    2. Confirm option.
    3. Cancel option.
11. Player selects the type of resource to be received.
12. Player selects the confirm option.
13. System adds the selected resource to the player's deck.

### Alternate Flows

#### AF1 - BF Any step: Player selects the cancel option.
1. System returns to the state of UC2 BF Step 2.

### Exceptions

#### EF1 - BF Step1: Player selects "Trade" option with no resources to trade
1. System displays error message stating that the player has no resources to trade.
2. Player selects the confirm option.
3. System returns to the state of UC2 BF Step 2.

#### EF2 - BF Step 5: Player selects a port without enough of the required type of resource in the player's deck.
1. System displays error message stating that the player does not have enough resources to trade with the selected port.
2. Player selects the confirm option.
3. System returns to the state of UC2 BF Step 2.

### Preconditions

- The game has been set up and the players have been assigned their colors and starting resources as specificed in UC1.
- Player's turn has begun.
- Player has resources to trade.

### Postconditions
- Player has traded resources with the port.

### System or subsystem
- Game

### Other Stakeholders
- The other players in the game.

### Special Requirements:
- The game will not allow the player to trade resources of the same type that the port accepts

****

# UC6. Buy Development Card

### Actor: Player

### Description
The Player decides to buy a development card during their turn.

### Basic Flow
1. Player rolls the dice by clicking the "Roll Dice" button.
2. System displays the number rolled, the domain, and the buttons "Build", "Trade", "Development Card", and "End Turn".
   1. Based on the number rolled, the System will distribute resources to the players based on the locations of their settlements and/or cities.
3. Player clicks the "Buy Development Card" button.  
4. The game deducts one ore, one wheat, and one sheep from the player's inventory.
5. The Game displays the development card that the player has drawn on the screen.
6. The player clicks the "Confirm" button.
3. Player clicks the "End Turn" button.
4. System ends the player's turn and begins the next player's turn.

### Alternate Flow
Step 4: The player does not have enough resources to buy a development card.
1. The Game displays an error message saying the player does not have enough resources to buy a development card.
2. The player clicks the "Confirm" button.

Step 5: The player draws a victory point card, causing them to win the game.
1. The Game displays a message saying the player has won the game.
2. The player clicks the "Confirm" button.

### Exceptions
Any Step: The player exits the game.
1. The Game returns to the main menu.

Any Step: The player closes the Game.
1. The Game closes, killing the process.

### Preconditions
This player's turn has just started.

### Postconditions
This player's turn has ended. The next player is now taking their turn.

### System or subsystem
Game

### Other Stakeholders
The other players in the Game.

### Special Requirements:
The Game shall accurately track the inventory of the player.

****

# UC7. Using a Development Card

### Actor: Player

### Description
The Player decides to use a development card during their turn.

### Basic Flow
1. Player rolls the dice by clicking the "Roll Dice" button.
2. System displays the number rolled, the domain, and the buttons "Build", "Trade", "Development Card", and "End Turn".
   1. Based on the number rolled, the System will distribute resources to the players based on the locations of their settlements and/or cities. 
3. The player hovers over the development cards in their hand to look at what they have. 
4. The player selects a Knight card from their hand. 
5. The Game displays the chosen card. 
6. The player clicks the "Confirm" button. 
7. The Game prompts the player to move the robber to a new location. 
8. The player selects a new location for the robber on the domain. 
9. The Game displays the new location of the robber on the domain. 
10. The Game prompts the player to choose a settlement or city to steal a resource from. 
11. The player selects a settlement or city to steal a resource from. 
12. The Game displays the resources that the player has stolen. 
13. Player clicks the "End Turn" button. 
14. System ends the player's turn and begins the next player's turn.

### Alternate Flow

Step 1: The player can play a development card before rolling the dice.
1. Do steps 3-12
2. Do steps 1-2
3. Do steps 13-14

Step 4: The player selects a Road Building card from their hand.
1. The Game displays the chosen card.
2. The player clicks the "Confirm" button.
3. The Game prompts the player to select two locations for roads. It highlights the available locations on the domain green.
4. The player selects two locations for roads.
5. The Game places the roads on the domain.
6. Do steps 13-14

Step 4: The player selects a Year of Plenty card from their hand.
1. The Game displays the chosen card.
2. The player clicks the "Confirm" button.
3. The Game prompts the player to select two resources to take from the bank.
4. The player selects two resources to take from the bank.
5. The Game adds the resources to the player's inventory.
6. Do steps 13-14

Step 4: The player selects a Monopoly card from their hand.
1. The Game displays the chosen card.
2. The player clicks the "Confirm" button.
3. The Game prompts the player to select a resource to take from the other players.
4. The player selects a resource to take from the other players.
5. The Game takes the selected resource from the other players and adds it to the player's inventory.
6. Do steps 13-14

### Exceptions
Any Step: The player exits the game.
1. The Game returns to the main menu.

Any Step: The player closes the Game.
1. The Game closes, killing the process.

### Preconditions
This player's turn has just started.

### Postconditions
This player's turn has ended. The next player is now taking their turn.

### System or subsystem
Game

### Other Stakeholders
The other players in the Game.

### Special Requirements:
The Game shall accurately track the inventory of the player.

****

# UC8. Trade Resources with 3:1 Port

### Actor: Player (User)

### Description

This use case describes the basic flow of the user trading with a 3:1 port.

### Basic Flow
1. Player selects the "Trade" option.
2. System displays the following:
   1. Trade with other players.
   2. Trade with a port.
   3. Trade with the bank.
3. Player selects "Trade with a port".
4. System displays the following:
   1. All ports where the player has a settlement or city.
   2. Cancel option.
5. Player selects which port to trade with.
6. System displays the following:
   1. The player's available resources.
   2. Confirm option.
   3. Cancel option.
7. Player selects three resources of the same type to trade.
8. Player selects the confirm option.
9. System removes the three selected resources from the player's deck.
10. System displays the following:
   1. The types of resource that the player can receive.
   2. Confirm option.
   3. Cancel option.
11. Player selects the type of resource to be received.
12. Player selects the confirm option.
13. System adds the selected resource to the player's deck.

### Alternate Flows

#### AF1 - BF Any step: Player selects the cancel option.
1. System returns to the state of UC2 BF Step 2.

### Exceptions

#### EF1 - BF Step1: Player selects "Trade" option with no resources to trade
1. System displays error message stating that the player has no resources to trade.
2. Player selects the confirm option.
3. System returns to the state of UC2 BF Step 2.

#### EF2 - BF Step 5: Player selects a port without three resources of the same type in the player's deck.
1. System displays error message stating that the player does not have enough resources to trade with the selected port.
2. Player selects the confirm option.
3. System returns to the state of UC2 BF Step 2.

### Preconditions

- The game has been set up and the players have been assigned their colors and starting resources as specificed in UC1.
- Player's turn has begun.
- Player has resources to trade.

### Postconditions
- Player has traded resources with the port.

### System or subsystem
- Game

### Other Stakeholders
- The other players in the game.

### Special Requirements:
- The game will not allow the player to trade resources of the same type.

****

# UC9. Trade Resources with 3:1 Port

### Actor: Player (User)

### Description

This use case describes the basic flow of the user trading with the bank.

### Basic Flow
1. Player selects the "Trade" option.
2. System displays the following:
   1. Trade with other players.
   2. Trade with a port.
   3. Trade with the bank.
3. Player selects "Trade with the bank".
4. System displays the following:
   1. The player's available resources.
   2. Confirm option.
   3. Cancel option.
5. Player selects four resources of the same type to trade.
6. Player selects the confirm option.
7. System removes the four selected resources from the player's deck.
8. System displays the following:
   1. The types of resource that the player can receive.
   2. Confirm option.
   3. Cancel option.
9. Player selects the type of resource to be received.
10. Player selects the confirm option.
11. System adds the selected resource to the player's deck.

### Alternate Flows

#### AF1 - BF Any step: Player selects the cancel option.
1. System returns to the state of UC2 BF Step 2.

### Exceptions

#### EF1 - BF Step1: Player selects "Trade" option with no resources to trade
1. System displays error message stating that the player has no resources to trade.
2. Player selects the confirm option.
3. System returns to the state of UC2 BF Step 2.

#### EF2 - BF Step 5: Player does not have four resources of the same type in the player's deck.
1. System displays error message stating that the player does not have enough resources to trade with the bank.
2. Player selects the confirm option.
3. System returns to the state of UC2 BF Step 2.

### Preconditions

- The game has been set up and the players have been assigned their colors and starting resources as specificed in UC1.
- Player's turn has begun.
- Player has resources to trade.

### Postconditions
- Player has traded resources with the bank.

### System or subsystem
- Game

### Other Stakeholders
- The other players in the game.

### Special Requirements:
- The game will not allow the player to trade resources of the same type.





# Base Catan Features

### F1: Supports GUI for player interaction

### F2: Allows users to select team color

### F3: Users can click on game domain intersections to interact

### F4: Supports interactive robber

### F5: Trade cards with other users
When the following conditions are true:
- Both players agree to the trade terms.

### F6: Automatically distributes resources based on a dice roll
When the following conditions are true:
- The dice roll matches the number on a hex with a settlement or city.
- The player owns a settlement or city adjacent to the hex.

### F7: Play a knight card
When the following conditions are true:
- The player has a knight card in their hand.
- The player chooses to play the knight card during their turn.

### F8: Play a Year of Plenty Card
When the following conditions are true:
- The player has a Year of Plenty card in their hand.
- The player chooses two resources to gain from the bank.

### F9: PLay a Monopoly Card
When the following conditions are true:
- The player has a Monopoly card in their hand.
- The player declares a resource to collect from all other players.

### F10: Play a Road Building
When the following conditions are true:
- The player has a Road Building card in their hand.
- The player places two roads on the board.

### F11: Automatically update Largest Army
When the following conditions are true:
- A player has just played a knight card
- A player has played more knight cards than any other player.
- The player has played at least three knight cards.

### F12: Automatically update Longest Road
When the following conditions are true:
- A player has just placed a road.
- A player has placed more roads than any other player.
- The player has placed at least five roads.

### F13: Calculate Victory Points

### F14: Buy and Place a settlement on the board
When the following conditions are true:
- The player has the required resources (lumber, brick, grain, and wool).
- The player places the settlement on an intersection that is at least two roads away from any other settlement.

### F15: Buy and Place a road on the board
When the following conditions are true:
- The player has the required resources (lumber and brick).
- The player places the road on an edge that is connected to one of their other roads or structures.

### F16: Upgrade a settlement to a City
When the following conditions are true:
- The player has the required resources (2 grain and 3 ore).
- The player places the city on an intersection that already has a settlement.

### F17: Trade with a Port
When the following conditions are true:
- The player has a settlement or city adjacent to a port.
- The player trades resources at the port's specified rate.

### F18: Trade with the Bank
When the following conditions are true:
- The player trades four of the same resource for one of any other resource.

### F19: Automatically Steal a Resource when Robber moved
When the following conditions are true:
- The robber is moved to a hex with other players' settlements or cities.
- The player moving the robber steals a resource from one of the affected players.

### F20: Discard half of resources when a player rolls a 7 for the robber
When the following conditions are true:
- A player rolls a 7.
- Any player with more than 7 resource cards discards half of their cards.

### F21: Place initial settlements

### F22: Place initial roads

### F23: Automatically end the game when the win condition is met
When the following conditions are true:
- A player reaches 10 victory points.

### F24: Buy a development card
When the following conditions are true:
- The player has the required resources (grain, wool and ore).
- The player buys a development card from the bank.


# Cities & Knights Expansion Features (specific)

### F25: Draw Progress Card
When the following conditions are true:
	- Dice requirements are met
	- Improvement requirements are met 

### F26: Distribute Commodities
When the following conditions are true:
	- A city in a commodity producing environment is owned

### F27: Buy and Build a Wall on the board
When the following conditions are true:
	- The player owns a city
	- The player has 2 brick

### F28: Variable Robber Halving Rates
When the following conditions are true:
	 - threshold for halving = 7 + (2 * number_of_walls)

### F29: Hire a Knight
When the following conditions are true:
	- The player has an unoccupied road section
	- The player has 1 wool and 1 ore

### F30: Activate a Knight
When the following conditions are true:
	- The player owns an inactive knight
	- The player has one grain 
	
### F31: Promote a Knight
When the following conditions are true:
	- The specific knight has not been promoted on the current turn already
	- The player has 1 wool and 1 ore
	- The player has level 3 politics (only required for promoting to highest level knight)

### F32: Perform Knight Action
When the following conditions are true:
	- The knight was not activated on the current turn

### F33: Perform Barbarian Attack
When the following conditions are true:
	- The barbarian ship is at the end of the barbarian track

### F34: Automatically Down-grade City (linked to F34)
When the following conditions are true:
	- The barbarian strength is greater than knight strength
	- The player has the lowest strength

### F35: Automatically Distribute Victory Point (linked to F34)
When the following conditions are true:
	- The knight strength is greater than or equal to the barbarian strength
	- The player has the highest strength

### F36: Improve City
When the following conditions are true:
	- The player owns a city
	- The player has the required amount of commodities to upgrade (level specific)

### F37: Build Metropolis
When the following conditions are true:
	- The player is upgrading to the 4th level of city improvement
	- The player is the first to upgrade to level 4 in the specific category

### F38: Play Progress Card
When the following conditions are true:
	- The player has rolled the dice



# method: setNumPlayers(int): int

## Step 1
- Input: An integer representing the number of players
- Output: The length of the new players array
## Step 2
- Input: An interval from [2, 4]
- Output: A number
## Step 3
- Input: Interval
  - Low - e: 1
  - Low: 2
  - High: 4
  - High + e: 5
- Output: A number
## Step 4
### Test 1
- Input: 1
- Output: Exception
### Test 2
- Input: 2
- Output: 2
### Test 3
- Input: 4
- Output: 4
### Test 4
- Input: 5
- Output: Exception


# method: setTurnOrder(int[] diceRolls): Player[]

## Step 1:
- Input: An array of type Player unsorted and an array of int representing player rolls
- Output: An array of type Player sorted based on the roll order

## Step 2:
- Input: A pair of array
- Output: A sorted array

## Step 3:
- Input: A pair of array
  - Both array are empty
  - The first array has elements, the second array has no elements
  - The first array has no elements, the second array has elements
  - Both arrays have equal number of elements
  - The first array has one more element than the second array
  - The first array has one fewer element than the second array
- Output: A sorted array based on the largest number in the second array

## Step 4:
### Test 1:
- Input: [], []
- Output: Exception

### Test 2:
- Input: [Player1, Player2], []
- Output: Exception

### Test 3:
- Input: [], [4, 6]
- Output: Exception

### Test 4:
- Input: [Player1, Player2, Player3], [6, 10, 4]
- Output: [Player2, Player3, Player1]

### Test 5:
- Input: [Player1, Player2, Player3, Player4], [6, 10, 4]
- Output: Exception

### Test 6:
- Input: [Player1, Player2, Player3], [6, 10, 4, 8]
- Output: Exception

### Test 7:
- Input: [Player1, Player2, Player3, Player4], [4, 6, 6, 5]
- Output: [Player2, Player3, Player4, Player1]

### Test 8:
- Input: [Player1, Player2, Player3], [7,6,5]
- Output: [Player1, Player2, Player3]

### Test 9:
- Input: [Player1, Player2, Player 3], [5,6,7]
- Output: [Player3, Player2, Player 1]


# method: setPlayer(int index, Player player): boolean success

## Step 1
Input: index in array, Player to put in array, internal player count
Output: Success or failure

## Step 2
Input: Array index, Player object, interval of player count
Output: boolean, player set at index

## Step 3
Input:
  Array indices: -1, 0, {1,2,3} (max values), max values + 1 
  Any player object
  Interval: [2,4] (integers)

Output: true, false, player set at index

## Step 4:
### Test 1-3:
Input: index -1, 2-4 count
Output: false, player not set at index
### Test 4-6:
Input: index 0, 2-4 count
Output: true,player set at index
### Test 7-9
Input: index 1-3, 2-4 count 
Output: true,player set at index
### Test 10-12:
Input: index 2-4, 2-4 count
Output: false, player not player set at index


# method: placeInitialSettlement(BoardManager boardManager, int selectedIndex, Player player): void

Note: The main BVA for this functionality is tested in the BoardManager class, since this is a passthrough method.
The only assertions that need to be made here are that the method is called

## Step 1:
- Input: The boardManager for where to place the settlement, the owner of the settlement, the index of the intersection to place the settlement
- Output: An updated board with the initial settlements and roads placed on the board

## Step 2:
- Input: A boardManager object, a number, a player object
- Output: An updated board

## Step 3:
- Input: A number
  - min value
  - value greater than 0
  - max value
  - out of bounds value
- Input: A boardManager object
- Input: A player object
- Output: An updated board with the initial settlements placed on the board

## Step 4:
### Test 1:
- Input: boardManager, Player1, 0
- Output: An updated board with the initial settlement placed on the board at intersection 0

### Test 2:
- Input: boardManager, Player1, 1
- Output: An updated board with the initial settlement placed on the board at intersection 1

### Test 3:
- Input: boardManager, Player1, 53
- Output: An updated board with the initial settlement placed on the board at intersection 53

### Test 4:
- Input: boardManager, Player1, -1
- Output: An Exception for invalid index

### Test 5:
- Input: boardManager, Player1, 0
- Output: An error occurs in board manager, false is returned, and an exception is thrown

# method: giveInitialResources(BoardManager boardManager,int intersection): void

Note: The main BVA for this functionality is tested in the BoardManager, Player, and Bank classses, since this method calls functionality from all of them.
The only assertions that need to be made here are that the method is called

## Step 1:
- Input: The boardManager for where to place the settlement, the intersection index, the player, the bank
- Output: An updated player resource collection based on where they placed their second settlement

## Step 2:
- Input: A boardManager object, a number
- Output: An updated player resource collection

## Step 3:
- Input: A number
  - min value
  - value greater than 0
  - max value
  - out of bounds value
- Input: A boardManager object
- Output: An updated player resource collection

## Step 4:
### Test 1:
- Input: boardManager, 0
- Output: An updated player resource collection

### Test 2:
- Input: boardManager, 1
- Output: An updated player resource collection

### Test 3:
- Input: boardManager, 53
- Output: An updated player resource collection

### Test 4:
- Input: boardManager, Player1, -1
- Output: An Exception for invalid index

### Test 5: 
Input: boardManager, 1, empty Bank
Output: Player resource collection is not updated

# method: placeRoad(BoardManager boardManager,int intersection1, int intersection2, Player p): void

Note: The main BVA for this functionality is tested in the BoardManager class, since this is a passthrough method.
The only assertions that need to be made here are that the method is called

## Step 1:
- Input: The boardManager for where to place the settlement, the first intersection index, the second intersection index, and the player
- Output: An updated board that has the road placed on the board

## Step 2:
- Input: A boardManager object, a number, a number, a player object
- Output: An updated board

## Step 3:
- Input: A number (x2 unique numbers)
  - min value
  - value greater than 0
  - max value
  - out of bounds value
- Input: A boardManager object
- Input: A player object
- Output: An updated board with the road placed on the board

## Step 4:
### Test 1:
- Input: boardManager, 0, 1, Player1
- Output: An updated board with the road placed on the board

### Test 2:
- Input: boardManager, 1, 0, Player1 
- Output: An updated board with the road placed on the board

### Test 3:
- Input: boardManager, 3, 2, Player1
- Output: An updated board with the road placed on the board

### Test 4:
- Input: boardManager, 2, 3, Player1
- Output: An updated board with the road placed on the board

### Test 5:
- Input: boardManager, 52, 53, Player1
- Output: An updated board with the road placed on the board

### Test 6:
- Input: boardManager, 53, 52, Player1
- Output: An updated board with the road placed on the board

### Test 7:
- Input: boardManager, 0, 0, Player1
- Output: An Exception

### Test 8:
- Input: boardManager, 0, 1, Player1
- Output: An error occurs in board manager, false is returned and an exception is thrown




# method: calculateVictoryPointsForPlayer(Player): int
## Step 1:
- Input: The player to calculate victory points for,
  - their development cards,
  - the settlements/cities in the game,
  - Longest road owner
  - Most armies owner
- Output: The total number of victory points the player has,
  - a boolean flag if game is over

## Step 2:
- Input: A Player object,
  - their collection of development cards,
  - Collection of structures, 
  - Cases: They own longest road, they don't own longest road
  - Cases: They own most armies, they don't own most armies
- Output: Count, boolean

## Step 3:
- Input: Player object
- Input: Player's Development Card Collection
  - 0-10 victory point cards
- Input: Collection of structures
  - Empty collection
  - 1 Structure owned by Player
  - 1 Not owned by Player
  - 2 Structures, One Owned one Not
  - 5 Settlements owned by player
  - 4 Cities owned by player
  - 4 Settlements and 5 cities owned by player
  - 1 city and 1 settlement owned by player
- Input: Longest Road
  - Owned by Player
  - Not owned by Player
- Input: Most armies
  - Owned by Player
  - Not owned by Player
- Output: Number of victory points,
  - gameOver flag set true if victory points >= 10

## Step 4:
### Test 1:
Input: Player, 0 victory point cards, empty structures, no roads/armies
Output: 0 Victory Points, gameOver false
### Test 2-11:
Input: Player, x in [1,10] victory point cards, empty structures, no roads/armies
Output: x Victory Points
### Test 12-16:
Input: Player, 0 point cards, y in [1,5] settlements owned by player, no others
Output: y Victory Points
### Test 17-20
Input: Player, 0 point cards, z in [1,4] cities owned by player, no others
Output: 2(z) Victory Points
### Test 21
Input: Player, 0 point cards, 1 city and 1 settlement owned by player, no others
Output: 3 Victory Points
### Test 22
Input: Player, 0 point cards, 4 city and 5 settlement owned by player, no others
Output: 13 Victory Points
### Test 23
Input: Player, 0 point cards, 1 settlement owned by player 1 not, no others
Output: 1 Victory Points
### Test 24
Input: Player, 0 point cards, no structures, has longest road
Output: 2 Victory Points
### Test 25
Input: Player, 0 point cards, no structures, has most armies
Output: 2 Victory Points
### Test 26
Input: Player, 5 point card, 4 settlements, no others
Output: 9 Victory Points, gameOver false
### Test 26
Input: Player, 5 point card, 5 settlements, no others
Output: 10 Victory Points, gameOver false
### Test 26
Input: Player, 5 point card, 4 settlements, longest road
Output: 11 Victory Points, gameOver false


# method: moveRobber(BoardManager boardManager, int intersection): void

Note: The main BVA for this functionality is tested in the BoardManager class, since this is a passthrough method.

## Step 1:
- Input: The boardManager for where to place the robber, the intersection index
- Output: An updated board with the robber placed on the board, true if the robber was successfully placed

## Step 2:
- Input: A boardManager object, a number
- Output: An updated board, true/false

## Step 3:
- Input: A number
  - min value: 0
  - value greater than 0
  - max value: 18
  - out of bounds value
- Input: A boardManager object
- Output: An updated board with the robber placed on the board

## Step 4:
### Test 1:
- Input: boardManager, 0
- Output: An updated board with the robber placed on the board

### Test 2:
- Input: boardManager, 1
- Output: An updated board with the robber placed on the board

### Test 3:
- Input: boardManager, 18 
- Output: An updated board with the robber placed on the board

### Test 4:
- Input: boardManager, -1
- Output: An Exception for invalid index

### Test 5:
- Input: boardManager, index is the same as where the robber is now
- Output: An Exception for same index

### Test 6:
- Input: boardManager, 19
- Output: An exception for invalid index

# method: getHexagonPlayers(BoardManager boardManager): ArrayList<Player>

Note: The main BVA for this functionality is tested in the BoardManager class, since this is a passthrough method.

## Step 1:
- Input: The boardManager for where to get the players
- Output: An array of players that have settlements/cities on the hexagon with the robber on it

## Step 2:
- Input: A boardManager object
- Output: An array list of players

## Step 3:
- Input: A boardManager object
  - No players have settlements/cities on the hexagon with the robber
  - One player has a settlement/city on the hexagon with the robber
  - Two players have settlements/cities on the hexagon with the robber
  - Three players have settlements/cities on the hexagon with the robber
    - Four players have settlements/cities on the hexagon with the robber
- Output: An array list of players

## Step 4:
### Test 1:
- Input: boardManager, no players have settlements/cities on the hexagon with the robber
- Output: An array list with no players

### Test 2:
- Input: boardManager, one player has a settlement/city on the hexagon with the robber
- Output: An array list with one player

### Test 3:
- Input: boardManager, two players have settlements/cities on the hexagon with the robber
- Output: An array list with two players

### Test 4:
- Input: boardManager, three players have settlements/cities on the hexagon with the robber
- Output: An array list with three players

### Test 5:
- Input: boardManager, four players have settlements/cities on the hexagon with the robber
- Output: An array list with four players

# method: tryRobberSteal(BoadManager boardManager, Player currentPlayer, Player selectedPlayerToSteal): void

Note: The main BVA for this functionality is tested in the BoardManager class, since this is a passthrough method.

## Step 1:
- Input: The boardManager for where to place the robber, the current player, the player to steal from
- Output: The Resource the current player has stolen from the selected player

## Step 2:
- Input: A boardManager object, a player object, a player object
- Output: A resource

## Step 3:
- Input: A boardManager object
    - The selected player has no resources
    - The selected player has resources
- Output: A resource

## Step 4:
### Test 1:
- Input: boardManager, Player1, Player2, Player2 has no resources
- Output: An Exception

### Test 2:
- Input: boardManager, Player1, Player2, Player2 has resources
- Output: A resource

### Test 3: 
- Input: boardManager, Player1, null
- Output: An Exception

# method: getHexagonPlayers(BoardManager boardManager): ArrayList<Player>

Note: The main BVA for this functionality is tested in the BoardManager class, since this is a passthrough method.

## Step 1:

- Input: The boardManager for where to get the players
- Output: An array of players that have settlements/cities on the hexagon with the robber on it

## Step 2:

- Input: A boardManager object
- Output: An array list of players

## Step 3:

- Input: A boardManager object

- Output: An array list of players

## Step 4:

### Test 1:

- Input: boardManager, no players have settlements/cities on the hexagon with the robber

- Output: []

### Test 2:

- Input: boardManager, one player has a settlement/city on the hexagon with the robber

- Output: [Player1]

### Test 3:

- Input: boardManager, two players have settlements/cities on the hexagon with the robber

- Output: [Player1, Player2]

### Test 4:

- Input: boardManager, three players have settlements/cities on the hexagon with the robber

- Output: [Player1, Player2, Player3]

### Test 5:

- Input: boardManager, four players have settlements/cities on the hexagon with the robber

- Output: [Player1, Player2, Player3, Player4]





# method setInTurnPlayer(int index): void

## BVA Step 1
Input: Index of player, number of players

Output: inTurn set to Player at index

## BVA Step 2
Input: 
- Index Interval [0-MAX PLAYER]
- Max player Count

Output: 
- Player, IndexOutOfBounds Exception

## BVA Step 3
Input:
- Index: 
  - -1, 0,1,2,3,4, 5
- Max Player Count
  - 2,3,4

Output: Player, IndexOutOfBounds Exception
## BVA Step 4
### Test 1-3
Input: Index -1, Max Player [2-4]
Output: IndexOutOfBounds exception("Outside player range")

### Test 4-6
Input: Index 0, Max Player [2-4]
Output: inTurn = players[0]

### Test 7-9
Input: Index 1, Max Player [2-4]
Output: inTurn = players[1]

### Test 10-12
Input: Index max player -1, Max Player [2-4]
Output: inTurn = players[MAX-1]

### Test 13-15 
Input: Index max player, Max Player [2-4]
Output: IndexOutOfBounds exception("Outside player range")


# method: buildSettlement(BoardManager boardManager, int intersection, Player player): boolean

## BVA Step 1

Input: BoardManager, Intersection, Player
Output: true or false value if the settlement was built and resources were deducted from bank

## BVA Step 2

Input:
- Intersection: 
  - Number
- Player
- BoardManager

Output: true or false value if the settlement was built and resources were deducted from bank

## BVA Step 3

Input:
- Intersection: 
  - -1, 0, 1, 53
- Player
- BoardManager

Output: true or false value if the settlement was built and resources were deducted from bank


## BVA Step 4

### Test 1
Input: Intersection -1, Player, BoardManager

Output: Exception

### Test 2-4
Input: Intersection 0, 1, 53, Player does not have resources, BoardManager

Output: false

### Test 5-7
Input: Intersection 0, 1, 53, Player has resources, BoardManager

Output: true, 1 brick, 1 wood, 1 wheat, 1 sheep deducted from player, 1 settlement added to board


# method: buildRoad(BoardManager boardManager, int intersection1, int intersection2, Player player): boolean

## BVA Step 1

Input: BoardManager, Intersection1, Intersection2, Player

Output: true or false value if the road was built and resources were deducted from player

## BVA Step 2

Input:
- Intersection1, Intersection2: 
  - Number
- Player
- BoardManager

Output: true or false value if the road was built and resources were deducted from player

## BVA Step 3

Input:
- Intersection1, Intersection2: 
  - -1, 0, 1, 53
- Player
  - Player has resources, Player does not have resources
- BoardManager

Output: true or false value if the road was built and resources were deducted from player

## BVA Step 4

### Test 1-3
Input: Intersection1 {0,1,53}, Intersection2 {0,1,53}, Player, BoardManager

Output: Exception, intersections are the same

### Test 4-7

Input: Intersection1 {0,1,53}, Intersection2 {0, 1, 53}, Player does not have resources, BoardManager

Output: false, no resources deducted, no road added

### Test 8-11

Input: Intersection1 {0,1,53}, Intersection2 {0, 1, 53}, Player has resources, BoardManager

Output: true, 1 brick, 1 wood deducted from player, 1 road added to board

# method: buildCity(BoardManager boardManager, int intersection, Player player): boolean

## BVA Step 1

Input: BoardManager, Intersection, Player
Output: true or false value if the city was built and resources were deducted from bank

## BVA Step 2

Input:
- Intersection:
  - Number
- Player
- BoardManager

Output: true or false value if the city was built and resources were deducted from bank

## BVA Step 3

Input:
- Intersection:
  - -1, 0, 1, 53
- Player
- BoardManager

Output: true or false value if the city was built and resources were deducted from bank


## BVA Step 4

### Test 1
Input: Intersection -1, Player, BoardManager

Output: Exception

### Test 2-4
Input: Intersection 0, 1, 53, Player does not have resources, BoardManager

Output: false

### Test 5-7
Input: Intersection 0, 1, 53, Player has resources, BoardManager

Output: true, 2 wheat, 3 ore deducted from player, 1 city added to board


# method: distributeResourcesOnRoll(BoardManager boardManager): int

## BVA Step 1

Input: the board

Output: if the operation was successful (0), failed (1), or the robber was rolled (2)

## BVA Step 2

Input:
- BoardManager

Output: Number

## BVA Step 3

Input:
- BoardManager

Output: Number (0, 1, or 2)

## BVA Step 4

### Test 1

Input: BoardManager

Output: 0, successful distribution

### Test 2

Input: BoardManager

Output: 1, failed distribution

### Test 3

Input: BoardManager

Output: 2, robber was rolled


# method: setNextPlayerInTurn(): int

### This increments the player index and sets new player, it initializes at 0 and therefore has no meaning in testing below zero
### Just need to verify that it wraps back to zero at max value

## BVA Step 1:
Input: current player index in players, num Players

Output: returns current player index updated (player 1 if first call), new inTurn player

## BVA Step 2:
Input: Array index, interval [2-4]

Output: Array index, New inTurn Player set

## BVA Step 3:
Input: Array Index:
- -1, 0, max index
NumPlayers:
- 2,3,4

Output: Array Index:
- 0, max index
Correct Player set

## BVA Step 4:
### Test 1-3
Input: 0, 2-4 players
Output: 1, player2 in turn
### Test 4-5
Input: n-2, n=[3,4] players
Output: n-1, player (n) in turn
### Test 6-8
Input: n-1, n=[2,4] players
Output: 0, player 1 in turn
### Test 9-11
Input: -1, n=[2,4] players
Output: 0, player 1 in turn


# isNotValidTradeRatio(PortTradeRatio, int amount): boolean
### amount interval is checked before calling this, we know it is 0-19
### PortTradeRatio is a data class and doesn't need to be mocked

## BVA Step 1
Input: Port used for the trade, amount of resources to trade

Output: boolean whether amount is multiple of port trade ratio

## BVA Step 2
Input: Port Cases, interval of resources

Output: boolean

## BVA Step 3
Input: Port cases
- Three-to-one
- Two-to-one
Resources:
- 0,1,2,3,4,6,19

Output: true, false

## BVA Step 4
### Test 1-2
Input: Two/Three ratio, 0
Output: True
### Test 3-4
Input: Two/Three ratio, 1
Output: False
### Test 5-7
Input: Two Ratio, 2,4,6
Output: True
### Test 8-9
Input: Three Ratio, 3,6
Output: True
### Test 10
Input: Three Ratio, 19
Output: False

# method: playerTradeWithPort(Port port, ResourceType giving, ResourceType taking, int numResources): void

## BVA Step 1

Input: Port used for trade, Player who is trading with the port, The resource type of what is being received by the 
port, The resource type of what is being taken from the port, The number of resources to be taken by the player

Output: An updated player resource collection

## BVA Step 2

Input: Port cases, interval of resources, Resource receiving cases, Resource taking cases

Output: Player's resource collection updated

## BVA Step 3

Input: 
- Port Cases:
  - 3 to 1
  - 2 to 1
- Resources:
  -{0, 1, 2, 3, 4, 6, 8, 9, 19}
- Receiving Resource Cases
  - LUMBER
  - BRICK
  - GRAIN
  - ORE
  - WOOL
- Taking Resource Cases
  - LUMBER
  - BRICK
  - GRAIN
  - ORE
  - WOOL

## BVA Step 4

### Test Case 1
Input: 2 to 1 Port, 0, 2, 4, 6, 8 Lumber, Brick

Output: Updated player resource collection with lumber removed and brick added

### Test Case 2
Input: 2 to 1 Port, 1, 3, 9, 19 Lumber, Brick

Output: player resource collection is not updated

### Test Case 3
Input: 3 to 1 Port, 0, 3, 6, 9 Lumber, Brick

Output: Updated player resource collection with lumber removed and brick added

### Test Case 4
Input: 3 to 1 Port, 1, 2, 4, 8, 19, Lumber, Brick

Output: Player resource collection is not updated

### Test Case 5
Input: 2 to 1 port, 0, 2, 4, 6, 8, Lumber, Brick, bank with insufficient resources

Output: Player resource collection is not updated

# method: getNumberCardsToDiscard(Player player): int

## BVA Step 1

Input: Player

Output: Number of cards to discard

## BVA Step 2

Input:
- Player

Output: Number

## BVA Step 3

Input:
- Player with no cards, 1 card, 7 cards, 8 cards, 95 (max) cards

Output: Number

## BVA Step 4

### Test 1

Input: Player with zero cards

Output: 0

### Test 2

Input: Player with 1 card

Output: 0

### Test 3

Input: Player with 7 cards

Output: 0

### Test 4

Input: Player with 8 cards

Output: 4

### Test 5

Input: Player with 95 cards

Output: 48

# method: playRoadBuildingCard(int[][] intersections): boolean

## BVA Step 1

Input: Array of intersections

Output: true or false if the card was successful

## BVA Step 2

Input:
- Array of intersections

Output: boolean

## BVA Step 3

Input:
- Array of intersections
  - 0, 1, 2, 53

Output: boolean

## BVA Step 4

### Test 1

Input: Array of intersections, all valid

Output: true

### Test 2

Input: Array of intersections, all invalid

Output: false


# method: playYearOfPlentyCard(ResourceType resource1, ResourceType resource2): boolean

### NOTE: This is a passthrough method, covering for mutation testing purposes

## BVA Step 1

Input: Two resources

Output: true or false if the card was successful

## BVA Step 2

Input:
- Two resources

Output: boolean

## BVA Step 3

Input:
- Two resources
- All resource types

Output: boolean

## BVA Step 4

### Test 1

Input: Two resources, all valid

Output: true

### Test 2

Input: Two resources, all invalid

Output: false

# method: playMonopolyCard(ResourceType resource): boolean

### NOTE: This is a passthrough method, covering for mutation testing purposes

## BVA Step 1

Input: resource

Output: true or false if the card was successful

## BVA Step 2

Input:
- resource

Output: boolean

## BVA Step 3

Input:
- resource
- All resource types

Output: boolean

## BVA Step 4

### Test 1

Input: resource, valid

Output: true

### Test 2

Input: resource, invalid

Output: false

# method: decrementResourcesFromBank(Collection<ResourceType> resources): void

## BVA Step 1

Input: Collection of resources

Output: the bank with the resources decremented

## BVA Step 2

Input:
- Collection of resources

Output: Bank with resources decremented

## BVA Step 3

Input:
- Collection of resources
  - 0, 1 of each, 95 (max) resources

Output: Bank with resources decremented

## BVA Step 4

### Test 1

Input: Collection of 0 resources

Output: Bank with no resources decremented

### Test 2

Input: Collection of 1 of each resource

Output: Bank with 1 of each resource decremented

### Test 3

Input: Collection of 95 resources

Output: Bank with 95 resources decremented


# method getNumPlayers():int

## BVA Step 1
Input: number of players
Output: return number of players

## BVA Step 2
Input: interval
Output: interval

## BVA Step 3
Input: 2,3,4
Output: same

## BVA Step 4
### Test 1-3
Input: 2-4
Output: return 2-4

# method getPlayers():int

## BVA Step 1
Input: number of players
Output: return player array

## BVA Step 2
Input: interval
Output: player array

## BVA Step 3
Input: 2,3,4
Output: player array of size 2,3,4

## BVA Step 4
### Test 1-3
Input: 2,3,4
Output: player array of size 2,3,4

# method isInTurnPlayer(Player): boolean

## BVA Step 1
Input: inTurn player, player to compare
Output: same player boolean

## BVA Step 2
Input: Cases
Output: boolean

## BVA Step 3
Input: Same players or different players
Output: true/false

## BVA Step 4
### Test 1
Input: Same player
Output: True

### Test 2
Input: Different players
Output: False


# INTEGRATION method: getDevelopmentCardsInBank(): Collection<DevelopmentCards>

## BVA Step 1
Input: Bank development cards collection
Output: Copy of dev cards collection

## BVA Step 2
Input: Collection
Output: Same Collection but copied

## BVA Step 3
Input: Empty, 1x Knight, 2x Knight, 14x Knight, 1x Monopoly
Output: Same cases

## BVA Step 4
### Test 1
Input: 0,1,2,14 KNight
Output: Same
### Test 2
Input: 1 Monopoly
Output: Same

# INTEGRATION method: getDevelopmentCardsInBank(): Collection<DevelopmentCards>

## BVA Step 1
Input: Bank development cards collection
Output: Copy of dev cards collection

## BVA Step 2
Input: Collection
Output: Same Collection but copied

## BVA Step 3
Input: Empty, 1x Knight, 2x Knight, 14x Knight, 1x Monopoly
Output: Same cases

## BVA Step 4
### Test 1
Input: 0,1,2,14 KNight
Output: Same
### Test 2
Input: 1 Monopoly
Output: Same

# INTEGRATION method: getPlayableDevelopmentCards(): Collection<DevelopmentCards>

## BVA Step 1
Input: In turn player playable development cards collection
Output: Copy of dev cards collection

## BVA Step 2
Input: Collection
Output: Same Collection but copied

## BVA Step 3
Input: Empty, 1x Knight, 2x Knight, 14x Knight, 1x Monopoly
Output: Same cases

## BVA Step 4
### Test 1
Input: 0,1,2,14 KNight
Output: Same
### Test 2
Input: 1 Monopoly
Output: Same

# INTEGRATION method: getFutureDevelopmentCards(): Collection<DevelopmentCards>

## BVA Step 1
Input: In turn player future development cards collection
Output: Copy of dev cards collection

## BVA Step 2
Input: Collection
Output: Same Collection but copied

## BVA Step 3
Input: Empty, 1x Knight, 2x Knight, 14x Knight, 1x Monopoly
Output: Same cases

## BVA Step 4
### Test 1
Input: 0,1,2,14 KNight
Output: Same
### Test 2
Input: 1 Monopoly
Output: Same

# INTEGRATION method: getUnplayableDevelopmentCards(): Collection<DevelopmentCards>

## BVA Step 1
Input: In turn player unplayable development cards collection
Output: Copy of dev cards collection

## BVA Step 2
Input: Collection
Output: Same Collection but copied

## BVA Step 3
Input: Empty, 1x Knight, 2x Knight, 14x Knight, 1x Monopoly
Output: Same cases

## BVA Step 4
### Test 1
Input: 0,1,2,14 Knight
Output: Same
### Test 2
Input: 1 Monopoly
Output: Same

# method: playerTradeWithBank(ResourceType giving, ResourceType taking, int numResources): boolean 

## BVA Step 1
Input: Player who is trading with the bank, The resource type of what is being received by the
bank, The resource type of what is being taken from the bank, The number of resources to be taken by the player

Output: Whether the trade was successful or not.

## BVA Step 2
Input: Interval of resources, ResourceType giving cases, ResourceType taking cases

Output: A boolean

## BVA Step 3
Input:
- Resources:
  -{0, 1, 2, 3, 4, 8, 12, 16, 19}
- Receiving Resource Cases
  - LUMBER
  - BRICK
  - GRAIN
  - ORE
  - WOOL
- Taking Resource Cases
  - LUMBER
  - BRICK
  - GRAIN
  - ORE
  - WOOL

Output: True or false

## BVA Step 4

### Test 1
Input:
- {0, 4, 8, 12, 16}
- LUMBER
- BRICK

Output: True

### Test 2
Input:
- {1, 2, 3, 5, 19}
- LUMBER
- BRICK

Output: False

# method: playKnight(Player toStealFrom, int newHexIndex): boolean

## BVA Step 1:
Input: The player whose turn it is, the player to steal from, the new location for the robber

Output: If resources were stolen or not;

## BVA Step 2
Input: an interval of valid hex locations, player to steal from cases

OutputL A boolean

## BVA Step 3
Input: 
- {-1, 0, 1, 18, 19}
- Player Cases:
  - Player.hasResources() = true
  - Player.hasResources() = false
Output: True of false

## BVA Step 4
### Test 1
Input:
- {0, 1, 18}
- Player.hasResources() = true
Output: False

### Test 2
Input:
- {0, 1, 18}
- Player.hasResources() = false
Output: True

### Test 3 
Input: 
- {-1, 19}
- Player.hasResources = true
Output: True

# method: decrementResourcesFromBank(Collection<ResourceType>): void
#### Only called when bank has resources (primarily for testing)
#### Can only meaningful test that obtainResources is called from bank as this is bank logic

## BVA Step 1
Input: Resource collection to remove
Output: Resources removed from bank

## BVA Step 2
Input: Collection size cases, resourcetype cases
Output: count resourcesRemoved

## BVA Step 3
Input: All ResourceType (grain, lumber, wool, brick ore)
Empty collection, grain, 2x grain, 19x grain, grain wool

Output: 0 removed, 1 removed of type, 2 removed of type 19x removed of type

## BVA Step 4
### Test 1-5
Input: 1 resource type removed (repeat for each)
Output: 1 removed of type
### Test 6-8
Input: 0 grain, 2 grain, 19 grain removed
Output: same amount removed
### Test 9
Input: 1 grain 1 wool
Output: same amount removed


# method: getCurrentDiceRoll():int
### pass through getter mutant fix

## BVA Step 1
Input: Dice roll value
Output: Dice roll value

## BVA Step 2
Input: interval 
Output: interval

## BVA Step 3
Input: [2-12]
Output: same

## BVA Step 4
### Test 1-11
Input: [2-12]
Output: [2-12]


# method: getRobberLocation():int
### pass through getter mutant fix

## BVA Step 1
Input: Robber location value
Output: Robber location value

## BVA Step 2
Input: interval
Output: interval

## BVA Step 3
Input: 0,1,18
Output: same

## BVA Step 4
### Test 1-3
Input: 0,1,18
Output: 0,1,18

# method: playerDiscardResource(Player, Collection<ResourceType>):boolean
### pass through getter mutant fix, logic is in bank and tested

## BVA Step 1
Input: player tries to discard resources
Output: boolean of success

## BVA Step 2
Input: cases
Output: boolean 

## BVA Step 3
Input: player successfully discards or fails to
Output: boolean

## BVA Step 4
### Test 1-2
Input: Player does/doesn't discard resources 
Output: true/false

# method: currentPlayerHasSufficientResources(Collection<ResourceType>):boolean
### pass through getter mutant fix, logic is in player and tested

## BVA Step 1
Input: player checks if they have the resources
Output: success or failure

## BVA Step 2
Input: cases
Output: boolean

## BVA Step 3
Input: player has or doesn't have resources
Output: boolean

## BVA Step 4
### Test 1-2
Input: Player does/doesn't have resources
Output: true/false

# method: playerTrade(Player, Collection<ResourceType>, Collection<ResourceType> ):boolean
### pass through getter mutant fix, logic is in bank and tested

## BVA Step 1
Input: Swaps resources with player in turn and player passed in
Output: success or failure

## BVA Step 2
Input: cases
Output: boolean

## BVA Step 3
Input: trade is successful or not
Output: boolean

## BVA Step 4
### Test 1-2
Input: Trade is successful or failure
Output: true/false
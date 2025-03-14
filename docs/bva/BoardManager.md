# method: generateHexagons(boolean randomize):Hexagon[]

## BVA Step 1
Input: a value to represent if the hexagons should be randomized

Output: domain hexagons

## BVA Step 2
Input: boolean

Output: collection (array) of Hexagon class that are randomized if the boolean is true, false otherwise

## BVA Step 3
Input: true or false

Output: array of 19 Hexagons, randomized if true, not randomized if false

## BVA Step 4
### Test 1: output array of 19 unique Hexagon items

### Test 2: output array of 19 Hexagons with 4 Wood, 4 Wheat, 4 Sheep, 3 Brick, 3 Ore, 1 Desert

### Test 3: output array of 19 hexagons with 1x 2, 2x 3, 2x 4, 2x 5, 2x 6, 2x 8, 2x 9, 2x 10, 2x 11, 1x 12


# method: generateIntersections():Intersection[]

## BVA Step 1
Input: none

Output: domain intersections

## BVA Step 2
Input: none

Output: collection (array) of Intersection class

## BVA Step 3
Input: none

Output: array of 54 Intersections

## BVA Step 4
### Test 1: output array of 54 unique Intersections items


# method: initializeBoardStructure(): void

## BVA Step 1
Input : none

Output: Hexagon[], Intersection[]

## BVA Step 2
Input: none

Output: Collection of Hexagon and Collection of Intersection

## BVA Step 3
Input: none

Output: Collection of Hexagon and Collection of Intersection linked together

## BVA Step 4
### Test 1: Input none, Verify each Hexagon has 6 intersections


[//]: # (# method: checkIfIntersectionIsPartOfHexagon&#40;Hexagon, Intersection&#41;:boolean)

[//]: # ()
[//]: # (## BVA Step 1)

[//]: # (Input: Hexagon tile, Intersection of Hexagon)

[//]: # ()
[//]: # (Output: true if intersection is part of hexagon)

[//]: # ()
[//]: # (## BVA Step 2)

[//]: # (Input: Hexagon, Intersection)

[//]: # ()
[//]: # (Output: boolean)

[//]: # ()
[//]: # (## BVA Step 3)

[//]: # (Input: Hexagon: null or not-null, centered at &#40;0,0&#41;, &#40;5,5&#41;; Intersection: null or not-null, &#40;0,0.6&#41;, &#40;0,0&#41; , &#40;0,0.7&#41;)

[//]: # ()
[//]: # (Output: true,false)

[//]: # ()
[//]: # (## BVA Step 4)

[//]: # (### Test 1: null Hexagon, Intersection -> NullPointerException)

[//]: # (### Test 2: Hexagon, null Intersection -> NullPointerException)

[//]: # (### Test 3: Hexagon &#40;0,0&#41;, Intersection &#40;0,0&#41; -> true)

[//]: # (### Test 4: Hexagon &#40;5,5&#41;, Intersection &#40;5,5&#41; -> true)

[//]: # (### Test 5: Hexagon &#40;0,0&#41;, Intersection &#40;0,0.6&#41; -> true)

[//]: # (### Test 6: Hexagon &#40;0,0&#41;, Intersection &#40;0,1&#41; -> true)

[//]: # ()
[//]: # (# method: addNearestHexagonsToIntersection&#40;Intersection, Hexagons[]&#41;:Intersection)

[//]: # ()
[//]: # (## BVA Step 1)

[//]: # (Input: Intersection point, all domain Hexagons)

[//]: # ()
[//]: # (Output: Intersection with adjacent Hexagons added)

[//]: # ()
[//]: # (## BVA Step 2)

[//]: # (Input: Intersection, collection of Hexagons)

[//]: # ()
[//]: # (Output: Intersection)

[//]: # ()
[//]: # (## BVA Step 3)

[//]: # (Input: Intersection: null or not-null; Hexagon[]: null or not-null)

[//]: # ()
[//]: # (Output: Intersection with Hexagons added if not null, else exception)

[//]: # ()
[//]: # (## BVA Step 4)

[//]: # (### Test 1: null Hexagon[], Intersection -> NullPointerException)

[//]: # (### Test 2: Hexagon[], null Intersection -> NullPointerException)

[//]: # (### Test 3: Hexagon[], Intersection -> Intersection with internal Hexagons set to Hexagon[])

# Method: checkSettlementPlacementLocation(int intersectionIndex) : Boolean

## BVA Step 1
Input: The index of the intersection to check, and the currently occupied intersections
Output: If the location is valid or not

## BVA Step 2
Input: Number, collection
Output: Boolean

## BVA Step 3
Input: 1, 53, 54, 0, -1, No occupied intersections, one occupied intersection,
three occupied intersections, all occupied intersections
Output: True, False

## BVA Step 4
### Test value 1
Input: -1
Output: False
### Test value 2
Input: 0
Output: True
### Test value 3
Input: 1
Output: True
### Test value 4
Input: 53
Output: True
### Test value 5
Input: 54
Output: False
### Test value 6 One Occupied
Input: 0
Output: True
Input: 6
Output: False
### Test value 7 Three Occupied
Input: 0
Output: true
Input: 1
Output: true
Input: 2
Output: true
Input: 12
Output: false
### Test value 8 All Occupied
Input: all intersections will try to be placed
Output: All intersections will try to be placed again, all should fail



# Method: makeAdjacentIntersections(Intersection[] intersections) : void
# This is more of a integration test than a unit test. Mocking all 54 intersections
# and their values is not feasable so we will directly use the make intersection method.

## BVA Step 1
Input: It is basically a setter that sets the adjacent intersections of the given intersections
Output: nothing

## BVA Step 2
Input: Collection
Output: Void

## BVA Step 3
Input: Collection of intersections
Output: Just check if the size of the adjacent intersections is 3

## BVA Step 4
### Test value 1
Input: intersections
Output: Check if 3 adjacent intersections are set for index 0, 1 and 2
### Test value 2
Input: intersections
Output: Check if 2 adjacent intersections are set for index 10

# Method: checkRoadPlacementLocation(int intersectionIndexOne, int intersectionIndexTwo, Player player) : Boolean

## BVA Step 1
Input: The index of the intersection to check, and the currently occupied intersections, 
and if the the structure you are attaching to is owned by you
Output: If the location is valid or not

## BVA Step 2 The collection is the collection of all structures. Roads and Settlements.
Input: Number, collection, Boolean
Output: Boolean

## BVA Step 3
Input: 1, 53, 54, 0, -1, No occupied intersections, one occupied intersection,
three occupied intersections, all occupied intersections, True, False
Output: True, False

## BVA Step 4
### Test value 1
Input: -1, 0, settlement on 0
Output: False
### Test value 2
Input: 0, 6, settlement on 0
Output: True
### Test value 3
Input: 1, 7, settlement on 1
Output: True
### Test value 4
Input: 53, 47, settlement on 53
Output: True
### Test value 5
Input: 54, 0
Output: False
### Test value 6 One Occupied
Input: 0, 6, settlement on 0
Output: True
Input: 6, 0
Output: False
### Test value 7 Three Occupied
Input: 0, 12, settlement on 0
Output: true
Input: 12, 1
Output: true
Input: 1, 7
Output: true
Input: 12, 14
Output: false
### Test value 8 All Occupied
Input: all intersections will try to be placed with settlements then 
all with roads multiple times to saturate the board
Output: All intersections will try to be placed with roads again, all should fail
### Test value 9
Input: 0, 6, Opponent settlement on 0
Output: False
### Test value 10
Input: 0, 6, settlement on 0
Output: True
Input: 0, 12
Output: True
Input: 0, 14
Output: True
### Test value 11
Input: 0, 1, settlement on 0
Output: False
### Test value 12
Input: 1, 0, settlement on 0
Output: False

### Test value 13
Input: 0, 54, Player1

Output: false

# method: getIntersectionSelection(): int

NOTE: Testing decision to only test the valid inputs since invalid inputs are caught elsewhere

## Step 1:
- Input: The internal value of the intersection selection
- Output: The index of the selected intersection and the value is reset to -1

## Step 2:
- Input: The internal value of the intersection selection
- Output: A number, then the value is reset to -1

## Step 3:
- Input: A number
    - min value
    - value greater than 0
    - max value
- Output: The index of the selected intersection, then the value is reset to -1

## Step 4:
### Test 1:
- Input: selectedIntersection is set to 0
- Output: 0, then selectedIntersection is set to -1

### Test 2:
-  Input: selectedIntersection is set to 1
- Output: 1, then selectedIntersection is set to -1

### Test 3:
- Input: selectedIntersection is set to 53
- Output: 53, then selectedIntersection is set to -1

# method: getHexSelection(): int

NOTE: Testing decision to only test the valid inputs since invalid inputs are caught elsewhere

## Step 1:
- Input: The internal value of the hex selection
- Output: The index of the selected hex and the value is reset to -1

## Step 2:
- Input: The internal value of the hex selection
- Output: A number, then the value is reset to -1

## Step 3:
- Input: A number
  - min value
  - value greater than 0
  - max value
- Output: The index of the selected hex, then the value is reset to -1

## Step 4:
### Test 1:
- Input: selectedHex is set to 0
- Output: 0, then selectedHex is set to -1

### Test 2:
-  Input: selectedHex is set to 1
- Output: 1, then selectedHex is set to -1

### Test 3:
- Input: selectedHex is set to 18
- Output: 18, then selectedHex is set to -1


# method: getIntersectionSettlement(int index): Settlement

NOTE: Testing decision to only test the valid inputs since invalid inputs are caught elsewhere

## BVA Step 1
Input: The index of the intersection to check, internal intersections
Output: The settlement at the intersection, or null

## BVA Step 2
Input: Number
Output: Settlement

## BVA Step 3
Input: 1, 53, 0, intersections with no settlements, intersections with settlements
Output: Settlement, null

## BVA Step 4
### Test value 1
Input: 0, intersection with settlement
Output: Settlement

### Test value 2
Input: 1, intersection with settlement
Output: Settlement

### Test value 3
Input: 53, intersection with settlement
Output: Settlement


### Test value 4-7
Input: 0,1,53 intersection with no settlement
Output: null


# method: getIntersectionSettlementColor(int index): Color

NOTE: Testing decision to only test the valid inputs since invalid inputs are caught elsewhere
Also will only be called with a intersection that has a settlement
## BVA Step 1
Input: The index of the intersection to check, internal intersections
Output: The color of the settlement at the intersection

## BVA Step 2
Input: Number
Output: Color

## BVA Step 3
Input: 1, 53, 0, intersections with settlements
Output: Color

## BVA Step 4
### Test value 1
Input: 0, intersection with settlement
Output: Color

### Test value 2
Input: 1, intersection with settlement
Output: Color

### Test value 3
Input: 53, intersection with settlement
Output: Color

# method: distributeResources(int intersection): List<ResourceType>

NOTE: testing decision to not test intersection for valid index since it is tested in GameManager
The only assertions that need to be made here are that the method is calle.

## BVA Step 1
Input: The index of the intersection to check, internal intersections
Output: A list of resources to distribute to the player

## BVA Step 2
Input: Number
Output: List of ResourceType

## BVA Step 3
Input: 1, 53, 0, hexagons with resources, hexagons with no resources
Output: List of ResourceType

## BVA Step 4
### Test value 1 
Input: 0, hexagons with resources around it with combinations of 3 resources
Output: list of resources with each combination of resources

### Test value 2
Input: 1, hexagons with resources around it with combinations of 3 resources
Output: list of resources with each combination of resources

### Test value 3
Input: 53, hexagons with resources around it with combinations of 3 resources
Output: list of resources with each combination of resources

### Test value 4-7
Input: 0,1,53 hexagons with no resources
Output: Empty list


# method: placeRoad(int one, int two, Player p, boolean init): Boolean

NOTE: Testing decision to only test the valid indexes since invalid indexes are caught in the checkRoadPlacementLocation method

# BVA Step 1
Input: The index of first intersection, the index of the second intersection, the player, and if it is the initial road placement
Output: A new road set to the two intersections (placed on the board)

# BVA Step 2
Input: Number, Number, Player, Boolean
Output: Boolean

# BVA Step 3
Input: 1, 53, 0, intersections with no roads, intersections with roads, Player1 init true, init false
Output: A new road set to the two intersections (placed on the board)

# BVA Step 4

### Test value 1
Input: 0, 6, Player1, no road between intersections, false
Output: A new road set to the two intersections (placed on the board), true

### Test value 2
Input: 2, 8, Player1, no road between intersections, false
Output: A new road set to the two intersections (placed on the board), true

### Test value 3
Input: 52, 42, Player1, no road between intersections, false
Output: A new road set to the two intersections (placed on the board), true

### Test value 4
Input: 1, 53, no road between intersections
Output: false

### Test value 5

Input: 0, 1, Player1, road between intersections, intersection1 has settlement, init true

Output: false

### Test value 6

Input: 0, 1, Player1, road between intersections, intersection2 has settlement, init false


# method: moveRobber(int hexIndex): void


## BVA Step 1

Input: The index of the hexagon to move the robber to

Output: The hasRobber value of the hexagon is set to true and the old hexagon hasRobber value is set to false


## BVA Step 2

Input: Number

Output: Void


## BVA Step 3

Input: 1, max val: 18, min val: 0, hexagon with robber on it, hexagon with no robber

Output: The hasRobber value of the hexagon is set to true and the old hexagon hasRobber value is set to false


## BVA Step 4

### NOTE: Exception handling is done in GameManager

### Test value 1

Input: 0, hexagon with no robber

Output: The hasRobber value of hexagon 0 is set to true and hexagon 1 hasRobber value is set to false


### Test value 2

Input: 1, hexagon with no robber

Output: The hasRobber value of hexagon 1 is set to true and hexagon 0 hasRobber value is set to false


### Test value 3

Input: 18, hexagon with no robber

Output: The hasRobber value of hexagon 18 is set to true and hexagon 0 hasRobber value is set to false

# method: getRobberLocation(): int

## BVA Step 1

Input: hexagons

Output: The index of the hexagon the robber is on

## BVA Step 2

Input: hexagons

Output: Number

## BVA Step 3

Input: hexagons with robber on it, hexagons with no robber

Output: The index of the hexagon the robber is on

## BVA Step 4

### Test value 1

Input: hexagons with robber on it

Output: 0

### Test value 2

Input: hexagons with robber on it

Output: 1

### Test value 3

Input: hexagons with no robber

Output: -1


# method: getHexagonPlayers(): ArrayList<Player>

## BVA Step 1

Input: hexagons

Output: The players that have settlements on the hexagon with the robber

## BVA Step 2

Input: hexagons

Output: Collection of players

## BVA Step 3

Input: hexagon with robber on it, hexagons with no robber

Output: The players that have settlements on the hexagon with the robber

## BVA Step 4

### Test value 1

Input: hexagon with robber on it and Player1 has a settlement on it

Output: [Player1]

### Test value 2

Input: hexagons with no robber

Output: []


# method: stealResource(Player currentPlayer, Player selectedPlayerToSteal): ResourceType

## BVA Step 1

Input: The current player and the player to steal from

Output: The resource type stolen

## BVA Step 2

Input: Player, Player

Output: ResourceType, player resources updated with the stolen resource

## BVA Step 3

Input: Players with resources

Output: The resource type stolen, player resources updated with the stolen resource for each resource type

### NOTE: At this point it is known that the player being stolen from has resources
## BVA Step 4

### Test value 1

Input: Player1, Player2 with Lumber

Output: Lumber, Player1 resources updated with the stolen Lumber resource

### Test value 2

Input: Player1, Player2 with Brick

Output: Brick, Player1 resources updated with the stolen Brick resource

### Test value 3

Input: Player1, Player2 with Ore

Output: Ore, Player1 resources updated with the stolen Ore resource

### Test value 4

Input: Player1, Player2 with Wheat

Output: Wheat, Player1 resources updated with the stolen Wheat resource

### Test value 5

Input: Player1, Player2 with Wool

Output: Sheep, Player1 resources updated with the stolen Wool resource

### Test value 6

Input: Player1, Player2 no resources

Output: null

# method: buildSettlement(int intersectionIndex, Player player): Boolean

### NOTE: Testing decision to only test the valid indexes since invalid indexes are caught in the GameManager buildSettlement method

## BVA Step 1
Input: The index of the intersection to build the settlement on, the player
Output: A new settlement set to the intersection (placed on the board) owned by the player, decremented numSettlements in Player

## BVA Step 2
Input: Number, Player
Output: Boolean, updated player object

## BVA Step 3
Input: 1, 53, 0, intersections with no settlements, intersections with settlements

Output: A new settlement set to the intersection (placed on the board) owned by the player if the location is valid, decremented numSettlements in Player

## BVA Step 4

### Test value 1-3
Input: 0, 1, 53, Player1, no settlement on intersection 0, 1, 53

Output: A new settlement set to the intersection (placed on the board) owned by the player, true, numSettlements decremented by 1

### Test value 4-6
Input: 0, 1, 53, Player1, no settlement on intersection 0, 1, 53, no road connecting to intersection 0, 1, 53

Output: false

### Test value 7-9
Input: 0, 1, 53, Player1, no settlement on intersection 0, 1, 53, road connecting to intersection 0, 1, 53 but player does not own the road

Output: false

### Test value 10-12
Input: 0, 1, 53, Player1, settlement on intersection 0, 1, 53

Output: false

# method: buildRoad(int intersectionIndexOne, int intersectionIndexTwo, Player player): Boolean

## BVA Step 1

Input: The index of the first intersection, the index of the second intersection, the player
Output: A new road set to the two intersections (placed on the board) owned by the player, true, updated player object

## BVA Step 2

Input: Number, Number, Player
Output: Boolean, updated player object

## BVA Step 3

Input: 1, 53, 0, intersections with no roads, intersections with roads
Output: A new road set to the two intersections (placed on the board) owned by the player if the location is valid, true, updated player object

## BVA Step 4

### Test value 1-3

Input: 0 1, 1 0, 52 53, Player1, no road between intersections 0 1, 1 0, 52 53

Output: A new road set to the two intersections (placed on the board) owned by the player, true, numRoads decremented by 1

### Test value 4-6

Input: 0 1, 1 0, 52 53, Player1, road between intersections 0 1, 1 0, 52 53

Output: false

### Test value 7
Input: -1 0, Player 1, no road between intersections

Output: false

### Test value 8
Input: 52 53, Player1, no road between intersections, no resources to build road

Output: false


# method: buildCity(int intersectionIndex, Player player): Boolean

### NOTE: Testing decision to only test the valid indexes since invalid indexes are caught in the GameManager buildSettlement method

## BVA Step 1
Input: The index of the intersection to build the city on, the player
Output: A new City set to the intersection (placed on the board) owned by the player, replacing the settlement, updated player object

## BVA Step 2
Input: Number, Player, Structure
Output: Boolean, updated player object

## BVA Step 3
Input: 1, 53, 0, intersections with no settlements, intersections with settlements
Structure on index is:
- null
- settlement
- city

Output: A new City set to the intersection (placed on the board) owned by the player if the location is valid, replacing the settlement, updated player object

## BVA Step 4

### Test value 1-3
Input: 0, 1, 53, Player1, no settlement on intersection 0, 1, 53

Output: false

### Test value 4-6
Input: 0, 1, 53, Player1, settlement on intersection 0, 1, 53

Output: true, settlement is replaced with city, numCities decremented by 1

### NOTE: Adding a test case for out of bounds indices for code coverage

### Test value 7
Input: -1, 54, Player1, settlement

Output: false, no settlement is upgraded to a city

### Test value 8
Input: 0, 1, 53, Player1, Player2, settlement on intersection 0, 1, 53

Output: false, no settlement is upgraded to a city

### Test value 9
Input: 0, 1, 53, Player1, city already there

Output: false, no settlement is upgraded to a city

### Test value 10
Input: 0, Player1, no settlement there

Output: false, no settlement is upgraded to a city

# method: distributeResourcesOnRoll(int currentDiceRoll, Bank bank): void

## BVA Step 1

Input: The current dice roll, the bank

Output: A number for the outcome, The resources are distributed to the players based on the dice roll, the bank is updated

## BVA Step 2

Input: Number, Bank object

Output: updated bank and player resources collection, Number

## BVA Step 3

Input: 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, bank with resources, bank with no resources

Output: updated bank and player resources collection, Number

## BVA Step 4

#### NOTE: 7 is not tested as it will never be passed into the method

### Test value 1-2

Input: 2, 12, bank with resources

Output: updated bank and player resources collection, 0

### Test value 3-10

Input: 2, 3, 4, 5, 6, 8, 9, 10, 11, bank with resources

Output: updated bank and player resources collection, 0

### Test value 11

Input: 2-12, bank with resources

Output: updated bank and player resources collection, 2

### Test value 12-22

Input: 2, 3, 4, 5, 6, 8, 9, 10, 11, 12 bank with no resources

Output: 1

### Test value 23

Input: 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, bank with resources, city

Output: 0

# method: removeRoad(int i, int i1) : boolean

## BVA Step 1

Input: The index of the first intersection, the index of the second intersection
Output: The road between the two intersections is removed

## BVA Step 2

Input: Number, Number

Output: Boolean

## BVA Step 3

Input: 1, 53, 0, intersections with no roads, intersections with roads

Output: The road between the two intersections is removed

## BVA Step 4

### Test value 1-3

Input: 0 1, 1 0, 52 53, no road between intersections 0 1, 1 0, 52 53

Output: false, no road is removed

### Test value 4-6

Input: 0 1, 1 0, 52 53, road between intersections 0 1, 1 0, 52 53

Output: true, road is removed


# method: ifHexValueZeroSetDesert(int[][] hexagonValues, int[] desertRowAndColumn, i, j) : void

## BVA Step 1

Input: The hexagon values, the row and column of the desert hexagon, the row and column of the current hexagon

Output: The hexagon is set to a desert hexagon if the value is 0

## BVA Step 2

Input: 2D array, 1D array, Number, Number

Output: Void

## BVA Step 3

Input: 2D array with 0, 1D array with row and column, 0-5 for row and column

Output: The hexagon is set to a desert hexagon if the value is 0

## BVA Step 4

### Test value 1

Input: 2D array with 0, 1D array with row and column, 0, 0

Output: The hexagon at 0, 0 is set to a desert hexagon

### Test value 2

Input: 2D array with 1, 1D array with row and column, 0, 0

Output: The hexagon at 0, 0 is not set to a desert hexagon

### Test value 3

Input: 2D array with 0, 1D array with row and column, 5, 5

Output: The hexagon at 5, 5 is set to a desert hexagon

### Test value 4

Input: 2D array with 1, 1D array with row and column, 5, 5

Output: The hexagon at 5, 5 is not set to a desert hexagon


# method: randomizeDesert(int[][] hexagonValues, Random random) : void

## BVA Step 1

Input: The hexagon values, the random object

Output: The desert hexagon is placed randomly on the board

## BVA Step 2

Input: 2D array, Random object

Output: Void

## BVA Step 3

Input: 2D array with 0, Random object

Output: The desert hexagon is placed randomly on the board

## BVA Step 4

### Note: this is the only test as it is only reached in this state

### Test value 1

Input: 2D array with 0, Random object

Output: The desert hexagon is placed randomly on the board

# method: reassignValueAtHexagon(int[][] hexagonValues, Hexagon hex, int[] rowColumn) : void

## BVA Step 1

Input: The hexagon values, the hexagon, the row and column of the hexagon

Output: The value of the hexagon is set in the hexagon values depending on if it is a desert hexagon

## BVA Step 2

Input: 2D array, Hexagon, 1D array

Output: Void

## BVA Step 3

Input: 2D array with 0, Hexagon with value 0, 1D array with row and column

Output: The value of the hexagon is set in the hexagon values depending on if it is a desert hexagon

## BVA Step 4

### Test value 1

Input: 2D array with 0, Hexagon with value 0, 1D array with row and column, hex is desert

Output: The value of the hexagon at row and column is set to not be a desert hexagon

### Test value 2

Input: 2D array with 0, Hexagon with value 1, 1D array with row and column, hex is desert

Output: The value of the hexagon at row and column is set to be a desert hexagon

# method: placeSettlementSetup(int intersectionIndex, Player player) : boolean

## BVA Step 1

Input: The index of the intersection to build the settlement on, the player

Output: A new settlement set to the intersection (placed on the board) owned by the player

## BVA Step 2

Input: Number, Player

Output: Boolean

## BVA Step 3

Input: 1, 53, 0, intersections with no settlements, intersections with settlements

Output: A new settlement set to the intersection (placed on the board) owned by the player if the location is valid

## BVA Step 4

### Test value 1-3

Input: 0, 1, 53, Player1, no settlement on intersection 0, 1, 53

Output: A new settlement set to the intersection (placed on the board) owned by the player, true

### Test value 4-6

Input: 0, 1, 53, Player1, settlement on intersection 0, 1, 53

Output: false


# method: peek get and setIntersectionSelection(int)

## BVA Step 1
Input: setIntersectionSelection called with int or not 
Output: return same intersection selected or -1 if not set

## BVA Step 2
Input: cases: set called or not, interval of selection indices
Output: interval

## BVA Step 3
Input: cases: set called or not, indices 0,1,55
Output: return -1 if not called, else return and set 0,1,55, then set field to -1

## BVA Step 4
### Test 1:
Input: set not called
Output: return -1
### Test 2-4:
Input: set called (0,1,56)
Output: peak/get return (0,1,56), then validate field = -1 after get

# method: peek get and setHexSelection(int)

## BVA Step 1
Input: setHexSelection called with int or not
Output: return same intersection selected or -1 if not set

## BVA Step 2
Input: cases: set called or not, interval of selection indices
Output: interval

## BVA Step 3
Input: cases: set called or not, indices 0,1,18
Output: return -1 if not called, else return and set 0,1,18, then set field to -1

## BVA Step 4
### Test 1:
Input: set not called
Output: return -1
### Test 2-4:
Input: set called (0,1,18)
Output: peak/get return (0,1,18), then validate field = -1 after get

# method getPorts():ArrayList<Port>

## BVA Step 1
Input: ports in board 
Output: get same ports

## BVA Step 2
Input: Collection
Output: Collection

## BVA Step 3
Input: Empty Collection, one Port, two Ports, 9 ports
Output: Same as input

## BVA Step 4
### Test 1-4
Input: Collection with (0,1,2,9) ports
Output: Collection with (0,1,2,9) ports

# method getPortIntersections(): int[][]

## BVA Step 1
Input: Nothing (hardCoded locations)
Output: copy of locations 

## BVA Step 2
Input: Nothing
Output: array/collection size

## BVA Step 3
Input: Nothing
Output: array is 9x2 

## BVA Step 4
### Test 1
Input: Nothing
Output: int[9][2]

# method: reassignHexWithValue(int[][] hexagonValues, Hexagon hex) : void

## BVA Step 1

Input: The hexagon values, the hexagon, the row and column of the hexagon

Output: The value of the hexagon is set in the hexagon values

## BVA Step 2

Input: 2D array, Hexagon

Output: Void

## BVA Step 3

Input: 2D array with 0, Hexagon with value 0, 1, 18, and is/is not desert

Output: The value of the hexagon is set in the hexagon values

## BVA Step 4

### Test value 1

Input: 2D array with 0, Hexagon with value 1 and is desert

Output: The value of the hexagon at row and column is set to 0

### Test value 2

Input: 2D array with 0, Hexagon with value 0 and is not desert

Output: The value of the hexagon at row and column is set to 1

# method: randomizeBoard(boolean randomize, HexagonHelper hexHelper) : void

## BVA Step 1

Input: If the board should be randomized, the hexagon helper

Output: The board is randomized if the boolean is true

## BVA Step 2

Input: Boolean, HexagonHelper

Output: Void

## BVA Step 3

Input: True, False, HexagonHelper

Output: The board is randomized if the boolean is true

## BVA Step 4

### Test value 1

Input: True, HexagonHelper

Output: The board is randomized

### Test value 2

Input: False, HexagonHelper

Output: The board is not randomized

# method: checkSettlementCostAndCount(Player player) : boolean

## BVA Step 1

Input: The player

Output: If the player has enough resources and settlements to build a settlement

## BVA Step 2

Input: Player

Output: Boolean

## BVA Step 3

Input: Player with resources and settlements, Player with no resources and settlements

Output: If the player has enough resources and settlements to build a settlement

## BVA Step 4

### Test value 1

Input: Player1 with resources and settlements

Output: true

### Test value 2

Input: Player1 with no resources and settlements

Output: false

### Test value 3

Input: Player1 with resources and no settlements

Output: false


# method: checkRoadCost(Player player) : boolean

## BVA Step 1

Input: The player

Output: If the player has enough resources and roads to build a road

## BVA Step 2

Input: Player

Output: Boolean

## BVA Step 3

Input: Player with resources and roads, Player with no resources and roads

Output: If the player has enough resources and roads to build a road

## BVA Step 4

### Test value 1

Input: Player1 with resources and roads

Output: true

### Test value 2

Input: Player1 with no resources and roads

Output: false

### Test value 3

Input: Player1 with resources and no roads

Output: false

# method: checkCityCost(Player player) : boolean

## BVA Step 1

Input: The player

Output: If the player has enough resources and cities to build a city

## BVA Step 2

Input: Player

Output: Boolean

## BVA Step 3

Input: Player with resources and cities, Player with no resources and cities

Output: If the player has enough resources and cities to build a city

## BVA Step 4

### Test value 1

Input: Player1 with resources and cities

Output: true

### Test value 2

Input: Player1 with no resources and cities

Output: false

### Test value 3

Input: Player1 with resources and no cities

Output: false

# method: randomizePorts(Shuffler shuffler) : void

## BVA Step 1

Input: The shuffler

Output: The ports are shuffled

## BVA Step 2

Input: Shuffler

Output: Void

## BVA Step 3

Input: Shuffler

Output: The ports are shuffled

## BVA Step 4

### Test value 1

Input: Shuffler

Output: The ports are shuffled

# method: generatePorts(boolean randomize) : void

## BVA Step 1

Input: If the ports should be randomized

Output: The ports are generated and randomized if the boolean is true

## BVA Step 2

Input: Boolean

Output: Void

## BVA Step 3

Input: True, False

Output: The ports are generated and randomized if the boolean is true

## BVA Step 4

### Test value 1

Input: True

Output: The ports are generated and randomized

### Test value 2

Input: False

Output: The ports are generated and not randomized


# method: addResAndValtoHex(HexagonHelper helper) : void

## BVA Step 1

Input: The hexagon helper

Output: The resources and values are added to the hexagon if not the last index, otherwise set to desert

## BVA Step 2

Input: HexagonHelper

Output: Void

## BVA Step 3

Input: HexagonHelper

Output: The resources and values are added to the hexagon if not the last index, otherwise set to desert

## BVA Step 4

### Test value 1

Input: HexagonHelper, heaxgon index is 1

Output: The resources and values are added to the hexagon

### Test value 2

Input: HexagonHelper, heaxgon index is 18

Output: resources and values are not set, only desert is set


# method: getRoadsOnBoard() : ArrayList<Road>

## BVA Step 1

Input: roads on board from board manager

Output: get same roads

## BVA Step 2

Input: None

Output: Collection

## BVA Step 3

Input: Empty Collection, one Road, two Roads, 60 Roads

Output: Same as input

## BVA Step 4

### Test 1-4

Input: Collection with (0,1,2,60) roads

Output: Collection with (0,1,2,60) roads

# method: getStructures() : Collection<Structure>

## BVA Step 1

Input: structures on board from board manager

Output: get same structures

## BVA Step 2

Input: None

Output: Collection

## BVA Step 3

Input: Empty Collection, one Structure, two Structures, 20 Structures

Output: Same as input

## BVA Step 4

### Test 1-4

Input: Collection with (0,1,2,20) structures

Output: Collection with (0,1,2,20) structures

# method: initCheck(int i, int i1, boolean init) : boolean

## BVA Step 1

Input: The index of the first intersection, the index of the second intersection, if it is the initial road placement

Output: if either intersection has a settlement

## BVA Step 2

Input: Number, Number, Boolean

Output: Boolean


## BVA Step 3

Input: 1, 0, intersections with no settlements, intersections with settlements, init true, init false

Output: if either intersection has a settlement

## BVA Step 4

### Test value 1-3

Input: 0 1, 1 0 no settlement between intersections 0 1, 1 0, init true, init false

Output: false

### Test value 4-6

Input: 0 1, 1 0, settlement at intersections 0 1, 1 0, init true, init false




# method: HexagonHelper.updateCurrentRowSize(int index):void
#### This kills mutants in an internal class method in BoardManager

## BVA Step 1
Input: index/row of board in domain
Output: Row size corresponding to index

## BVA Step 2
Input: interval
Output: interval

## BVA Step 3
Input: [0-2]
Output: [5-3]

## BVA Step 4
### Test 1-3
Input: [0-2]
Output: [5-3]

# method: IntersectionHelper.updateCurrentRowSize(int index):void
#### This kills mutants in an internal class method in BoardManager

## BVA Step 1
Input: index/row of board in domain
Output: Row size corresponding to index

## BVA Step 2
Input: interval
Output: interval

## BVA Step 3
Input: [0-2]
Output: [6-4]

## BVA Step 4
### Test 1-3
Input: [0-2]
Output: [6-4]


# method: placeCity(City, int, Player): void
### Before this method is called we know the player has met the requirements for building a city

## BVA Step 1
Input: index of city and player to be owner, city count 1,2,4
Output: City on index with player as owner, city count decremented by 1

## BVA Step 2
Input: interval of intersections, player object
Output: City at interval, city count decremented by 1

## BVA Step 3
Input: index 0,1,53, city count 1,2,4
Output: city at 0,1,53, count 0,1,3

## BVA Step 4
### Test 1-3
Input: index 0,1,53, city count 1
Output: city at 0,1,53, count 0
### Test 4-5
Input: index 0, city count 2,4
Output: city at 0, count 1,3


# method: makeSettlement(Settlement, int, Player): void
### Before this method is called we know the player has met the requirements for building a settlement and count is updated

## BVA Step 1
Input: index of settlement and player to be owner
Output: settlement on index with player as owner

## BVA Step 2
Input: interval of intersections, player object
Output: settlement at interval

## BVA Step 3
Input: index 0,1,53
Output: settlement at 0,1,53

## BVA Step 4
### Test 1-3
Input: index 0,1,53
Output: settlement at 0,1,53
### Test 4-5
Input: index 0
Output: settlement at 0

# method: addHexagonIfAdjacent(Collection<Hexagon>, Point, Hex)
### This is a mutant killing test for a private method, integration mostly 


## BVA Step 1
Input: Collection of Hexagons to add hex if meets criteria to, Point and Hex to compare distance
Output: Collection gains hex if point and hex are close enough

## BVA Step 2
Input: Cases of point and hex within distance
Output: case

## BVA Step 3
Input: Point and Hex are 0.59 apart, 0.6 apart, 0.61 apart
Output: hex added or not added to collection

## BVA Step 4
### Test 1 
Input: 0.59 apart
Output: added
### Test 2-3
Input: 0.60, 0.61 apart
Output: not added

# method: linkHexagonsAndIntersections(Hexagon[], Intersection[])
### This is a mutant killing test for a private method, integration logic

### this method also assumes non empty collections before call
### only need to test one hex in collection due to other testing

## BVA Step 1
Input: Hex Array and Intersections that should be combined
Output: Intersections contain hex

## BVA Step 2
Input: array of intersection and hex
Output: all intersection have hex

## BVA Step 3
Input: inter[] size 1, 2, hex[] size 1
Output: hex added to any inter

## BVA Step 4
### Test 1-2
Input: inter[] size 1/2
Output: hex added to all inter 


# method: addIntersectionIfAdjacent(Collection<Intersection>, Point, Intersection)
### This is a mutant killing test for a private method, integration mostly


## BVA Step 1
Input: Collection of Intersections to add intersection if meets criteria to, Point and inter to compare distance
Output: Collection gains inter if point and inter are close enough

## BVA Step 2
Input: Cases of point and inter within distance
Output: case

## BVA Step 3
Input: Point and inter are 0.59 apart, 0.6 apart, 0.61 apart
Output: hex added or not added to collection

## BVA Step 4
### Test 1
Input: 0.59 apart
Output: added
### Test 2-3
Input: 0.60, 0.61 apart
Output: not added


# method: addHexagon(HexagonHelper helper, double offsetX, double offsetY) {
### This is a mutant killing test for a helper method, only making sure the mutated logic is killed
### The HexagonHelper setter logic is under test for this bva
### no error on input needs to occur

## BVA Step 1
Input: helper, and hex location input  
Output: Check that fields set

## BVA Step 2
Input: helper with fields to set
Output: check fields set

## BVA Step 3
Input: helper fields, double are -2,0,2
Output: check fields set

## BVA Step 4
### Test 1-3:
Input: doubles are -2,0,2, check helper fieldss
Output: check fields set


# method: reassignHexIfNullFound(Hexagon[] hexagons, ResourceType resToSwap) : void
### This is a mutant killing test for a helper method, only making sure the mutated logic is killed

# BVA Step 1
Input: hexagons, resource type to swap

Output: hexagons with resource type swapped

# BVA Step 2

Input: hexagons, resource type to swap

Output: void

# BVA Step 3

Input: hexagons with null, resource type to swap

Output: hexagons with resource type swapped

# BVA Step 4

### Test 1

Input: hexagons with null, resource type to swap

Output: hexagons with resource type swapped

# method: reassignResourceIfValueIsZero(Hexagon[] hexagons, Hexagon hex) : void
### This is a mutant killing test for a helper method, only making sure the mutated logic is killed

# BVA Step 1

Input: hexagons, hexagon to check

Output: hexagons with hex value swapped

# BVA Step 2

Input: hexagons, hexagon to check

Output: void

# BVA Step 3

Input: hexagons with 0, hexagon to check

Output: hexagons with hex value swapped

# BVA Step 4

### Test 1

Input: hexagons with 0, hexagon to check

Output: hexagons with hex value swapped

# method: assignPredeterminedResources(Hexagon[] hexagons, ResourceType[] res) : void
### This is a mutant killing test for a helper method, only making sure the mutated logic is killed

# BVA Step 1

Input: hexagons, resource types to assign

Output: hexagons with resource types assigned

# BVA Step 2

Input: hexagons, resource types to assign

Output: void

# BVA Step 3

Input: hexagons with null, resource types to assign

Output: hexagons with resource types assigned

# BVA Step 4

### Test 1

Input: hexagons with null, resource types to assign

Output: hexagons with resource types assigned




# method: setIntersectionAsAdjacent(int,int, point, intersection[])
### This is a mutant killing test for a private method, integration mostly

### We only need to worry about the case where the ints are equal, and a single intersection


## BVA Step 1
Input: intersection[i] doesn't have j as adjacent, add it if it's nearby
Output: Add j to i if it's nearby

## BVA Step 2
Input: case of j is adjacent or not
Output: add to i collection if adjacent

## BVA Step 3
Input: Inter i and j are 0.59 apart, 0.6 apart, 0.61 apart
Output: inter added or not added to collection

## BVA Step 4
### Test 1
Input: 0.59 apart
Output: added
### Test 2-3
Input: 0.60, 0.61 apart
Output: not added


# method: makeRoad((Road road, int one, int two, Player p): void
### Before this method is called we know the player has met the requirements for building a road and count is updated
### We only need to assert that the road fields were updated

## BVA Step 1
Input: intersections to test (new Road), player as owner
Output: road on indices with player as owner

## BVA Step 2
Input: pair of valid road index, player as owner
Output: road with fields set

## BVA Step 3
Input: road index [0,6] & [11,29], player
Output: check road is set with owner and index

## BVA Step 4
### Test 1
Input: index [0,6] with owner player
Output: road at [0,6] with owner player
### Test 2
Input: index [11,29] with owner player 
Output: road at [11,29] with owner player

# method getHexagons():Hexagon[]
# simple getter method just going to check same size hexes


## BVa Step 1
Input: Array of hexagons
Output: Array of hexagons

## BVA Step 2
Input: collection size cases
Output: same

## BVA Step 3
Input: empty array, 1 hex, 19 hexes
Output: same

## BVA Step 4
### Test 1-3
Input: Empty, 1 hex, 19 hexes
Output: same

# method: initializeBoardStructure(boolean):void
### this is a mutation killing BVA purely to fix bug that should be covered by BDD


## BVA Step 1
Input: boolean, random or not board initialization
Output: port list initialized to nonempty

## BVA Step 2
Input: boolean
Output: Port collection non-empty

## BVA Step 3
Input: true/false
Output: Port collection non-empty

## BVA Step 4
### Test 1-2
Input: true/false
Output: Port collection non-empty


# method: roadFound(road, int,int ):void
### this is a mutation killing BVA purely to fix bug that should be covered by BDD
### more so integration 

## Bva Step 1
Input: road and two int
Output: boolean where road has both int as intersection

## BVA step 2
Input: cases
Output: boolean

## BVA Step 3
Input: cases: one int in road, both in road, none in road
Output: true/false

## BVa Step 4
### Test 1:
Input: neither int in road
Output: false

### Test 2:
Input: one int in road
Output: false

### Test 3:
Input: other int in road
Output: false

### Test 4:
Input: both in road
Output: true
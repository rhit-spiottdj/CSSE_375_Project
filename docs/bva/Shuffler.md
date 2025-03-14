# method: createShuffledResourceTypes() : ArrayList<ResourceType>

## BVA Step 1
Input: Nothing

Output: Collection of all 19 resource types in a random order for the hexes

## BVA Step 2
Input: Nothing

Output: Collection of all 19 resource types in a random order for the hexes

## BVA Step 3
Input:

Output: Collection 
- The collection contains:
  - 4 Wood
  - 4 Wheat
  - 4 Sheep
  - 3 Brick
  - 3 Ore
  - 1 Desert

## BVA Step 4

### Test value 1
Input: Nothing

Output: Collection of size 19

### Test value 2
Input: Nothing

Output: Collection is shuffled randomly


### Test value 3
Input: Nothing

Output: Collection of all 19 resource types in a random order for the hexes
The collection contains:
- 4 Wood
- 4 Wheat
- 4 Sheep
- 3 Brick
- 3 Ore
- 1 Desert

### Test value 1
Input: Nothing

Output: Collection of size 19

# method: createShuffledPortTokens() : ArrayList<Port>
## BVA Step 1
Input: Nothing

Output: Collection of Port objects for each unique port in Catan

## BVA Step 2
Input: Nothing

Output: Collection

## BVA Step 3
Input: Nothing

Output: Collection containing
- 4x 3:1 Ports
- 2:1 Grain
- 2:1 Wool
- 2:1 Lumber
- 2:1 Brick
- 2:1 Ore

## BVA Step 4
### Test 1
Input: Nothing
Output: Collection containing
- 4x 3:1 Ports
- 2:1 Grain
- 2:1 Wool
- 2:1 Lumber
- 2:1 Brick
- 2:1 Ore

# method: createShuffledNumberTokens() : ArrayList<Integer>

## BVA Step 1
Input: Nothing

Output: Collection of Integer values from 2 to 12 in a random order

## BVA Step 2
Input: Nothing

Output: Collection of Integer values from 2 to 12 in a random order

## BVA Step 3
Input: Nothing

Output: Collection of Integer values from 2 to 12 in a random order
The collection contains:
- 1x 2
- 2x 3
- 2x 4
- 2x 5
- 2x 6
- 2x 8
- 2x 9
- 2x 10
- 2x 11
- 1x 12

## BVA Step 4
### Test value 1
Input: Nothing

Output: Collection of size 18
### Test value 2
Input: Nothing

Output: Collection is shuffled randomly
### Test value 3
Input: Nothing

Output: Collection of Integer values from 2 to 12 in a random order

# method: getHexagonValues(Hexagon[] hexagons) : ArrayList<Integer>

## BVA Step 1
Input: Collection of Hexagons

Output: Collection of Integer values from 2 to 12 that are associated with the hexagons

## BVA Step 2

Input: Collection of Hexagons

Output: Collection of Integer values from 2 to 12 that are associated with the hexagons

## BVA Step 3

Input: Collection of Hexagons

Output: Collection of Integer values from 2 to 12 that are associated with the hexagons

## BVA Step 4

### Test value 1

Input: Collection of Hexagons

Output: Collection of size 19

### Test value 2

Input: Collection of Hexagons

Output: Collection of Integer values from 2 to 12 in a random order
The collection contains:
- 1x 2
- 2x 3
- 2x 4
- 2x 5
- 2x 6
- 2x 8
- 2x 9
- 2x 10
- 2x 11
- 1x 12

# method ensureNoNeighborSixOrEight(Hexagon[] hexagons) : void

## BVA Step 1
Input: Collection of Hexagons

Output: Collection of Hexagons with no neighbors that have a value of 6 or 8

## BVA Step 2

Input: Collection of Hexagons

Output: Collection of Hexagons with no neighbors that have a value of 6 or 8

## BVA Step 3

Input: Collection of Hexagons

Output: Collection of Hexagons with no neighbors that have a value of 6 or 8

## BVA Step 4

### Test value 1

Input: Collection of Hexagons

Output: Collection of Hexagons with no neighbors that have a value of 6 or 8

# method: swapCell(Random random, int row, int col) : void

## BVA Step 1

Input: Random object, row, col

Output: cell at row, col is swapped with a random cell

## BVA Step 2

Input: Random object, Number, Number

Output: Nothing

## BVA Step 3

Input: Random object, 0-4, 0-4

Output: swapped cell

## BVA Step 4

### Test value 1

Input: Random object, 0, 0

Output: cell at 0, 0 is swapped with a random cell

# method: isValidNeighbor(int row, int col, int differentRow, int differentCol) : boolean

## BVA Step 1

Input: the row and column of a cell, the row and column of a potential neighbor

Output: true if the cell is a valid neighbor, false otherwise

## BVA Step 2

Input: Number, Number, Number, Number

Output: true or false

## BVA Step 3

Input: 0-4, 0-4, 0-4, 0-4

Output: true or false

## BVA Step 4

#### NOTE: this is asserting cases for mutation testing

### Test value 1

Input: 0, 0, -1, 0

Output: false

### Test value 2

Input: 0, 0, 0, -1

Output: false

### Test value 3

Input: 0, 0, 0, 0

Output: true

### Test value 4

Input: 0, 0, 5, 0

Output: false

### Test value 5

Input: 0, 0, 0, 5

Output: false

### Test value 6

Input: 3, 0, 1, 0

Output: false

### Test value 7

Input: 0, 3, 0, 1

Output: false

### Test value 8

Input: 3, 0, 0, 0

Output: true

### Test value 9

Input: 0, 3, 0, 0

Output: true

# method: isHasNeighborSixOrEight(Random rand, boolean hasNeighborSixOrEight) : boolean

## BVA Step 1

Input: Random object, boolean

Output: true if the cell has a neighbor with a value of 6 or 8, false otherwise

## BVA Step 2

Input: Random object, boolean

Output: true or false

## BVA Step 3

Input: Random object, true or false

Output: true or false

## BVA Step 4

### Test value 1

Input: Random object, true

Output: true if the cell has a neighbor with a value of 6 or 8, false otherwise



# method: ensurePortShuffle

## BVA Step 1
Input: Collection of Ports, (all unique objects)
Output: Shuffled version of Collection

## BVA Step 2
Input: Size cases
Output: Collection of same size cases, shuffled if not size 0 or 1

## BVA Step 3
Input: Sizes, 0, 1, 2, 9
Output: Size 0,1,2 ,9 . Ensure different Port Objects if not size 0 or 1

## BVA Step 4
### Test 1-2
Input: Size 0-1
Output: Output same Collection
### Test 3
Input: Size 2
Output: Output of collection but order reversed
### Test 4 
Input: size 9
Output: Collection in different order of same size


# method: createShuffledPortTokens()

## BVA Step 1
Input: Nothing
Output: Collection of 9 ports

## BVA Step 2
Input: Nothing
Output: Collection size, different order 

## BVA Step 3
Input: Nothing
Output: size = 9, Not a list starting with two 3:1 ports 
followed by   2:1  BRICK, GRAIN, LUMBER, ORE, WOOL

## BVA Step 4
### Test 1
Input: Nothing
Output:  size = 9, Not a list starting with two 3:1 ports
followed by   2:1  BRICK, GRAIN, LUMBER, ORE, WOOL

# method: testHasAdjacentSixOrEight(int row, int col)

#### This is a package private method, this BVA is only meant to fix the mutant the public method isn't covering
#### Thus, we assume constant row and col as we are validating the check around it

## BVA Step 1
Input: A central row and col hex index, hexagon values around it
Output: boolean whether it has six or eight adjacent

## BVA Step 2
Input: Hexagon value cases, cases of what side hex is adjacent 6
Output: boolean whether six or eight adjacent

## BVA Step 3
Input: value cases: 0,6,8, side cases: all sides, no sides, one side
Output: true/false

## BVA Step 4
### Test 1-2
Input: All sides 6 or 8
Output: true
### Test 3
Input: No sides 6 or 8
Output: false
### Test 4
Input: Side 1 is 6, rest 0 
Output: false
### Test 5
Input: Side 2 is 6, rest 0
Output: false
### Test 6
Input: Side 3 is 6, rest 0
Output: false
### Test 7
Input: Side 4 is 6, rest 0
Output: false





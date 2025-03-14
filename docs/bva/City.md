# Class : City

***Note: Getters and setters can be ignored for bva***

# method: distributeResources(ResourceType type) : void

## BVA Step 1
- Input: ResourceType type, Collection of resources in players inventory
- Output: updated resources to the owner of the settlement, 2 because of the city

## BVA Step 2
- Input: Cases
    - ResourceType type
        - Case 1: Wood
        - Case 2: Brick
        - Case 3: Wheat
        - Case 4: Sheep
        - Case 5: Ore
    - Collection of resources
        - Case 1: empty collection
        - Case 2: collection with one resource
        - Case 3: collection with > one resource
        - Case 4: collection with the max number of resources (95)


## BVA Step 3
I
Input:
- Wood, []
- Brick, []
- Wheat, []
- Sheep, []
- Ore, []
***
- Wood, [Wood]
- Brick, [Brick]
- Wheat, [Wheat]
- Sheep, [Sheep]
- Ore, [Ore]
***
- Wood, [Wood, Wood]
- Brick, [Brick, Brick]
- Wheat, [Wheat, Wheat]
- Sheep, [Sheep, Sheep]
- Ore, [Ore, Ore]
***
- Wood, (max size collection) Exception

Output:
- [Wood, Wood]
- [Brick, Brick]
- [Wheat, Wheat]
- [Sheep, Sheep]
- [Ore, Ore]
***
- [Wood, Wood, Wood]
- [Brick, Brick, Brick]
- [Wheat, Wheat, Wheat]
- [Sheep, Sheep, Sheep]
- [Ore, Ore, Ore]
***
- [Wood, Wood, Wood, Wood]
- [Brick, Brick, Brick, Brick]
- [Wheat, Wheat, Wheat, Wheat]
- [Sheep, Sheep, Sheep, Sheep]
- [Ore, Ore, Ore, Ore]
***
- Exception

## BVA Step 4

### Test value 1
- Input: Wood, []
- Output: [Wood, Wood]

### Test value 2
- Input: Brick, []
- Output: [Brick, Brick]

### Test value 3
- Input: Wheat, []
- Output: [Wheat, Wheat]

### Test value 4
- Input: Sheep, []
- Output: [Sheep, Sheep]

### Test value 5
- Input: Ore, []
- Output: [Ore, Ore]

### Test value 6
- Input: Wood, [Wood]
- Output: [Wood, Wood, Wood]

### Test value 7
- Input: Brick, [Brick]
- Output: [Brick, Brick, Brick]

### Test value 8
- Input: Wheat, [Wheat]
- Output: [Wheat, Wheat, Wheat]

### Test value 9
- Input: Sheep, [Sheep]
- Output: [Sheep, Sheep, Sheep]

### Test value 10
- Input: Ore, [Ore]
- Output: [Ore, Ore, Ore]

### Test value 11
- Input: Wood, [Wood, Wood]
- Output: [Wood, Wood, Wood, Wood]

### Test value 12
- Input: Brick, [Brick, Brick]
- Output: [Brick, Brick, Brick, Brick]

### Test value 13
- Input: Wheat, [Wheat, Wheat]
- Output: [Wheat, Wheat, Wheat, Wheat]

### Test value 14
- Input: Sheep, [Sheep, Sheep]
- Output: [Sheep, Sheep, Sheep, Sheep]

### Test value 15
- Input: Ore, [Ore, Ore]
- Output: [Ore, Ore, Ore, Ore]

### Test value 16
- Input: Wood, (size 95 collection)
- Output: Exception

### Test value 17
- Input: Wood, (size 94 collection)
- Output: Exception


# method getCost(): Collection<ResourceType>

## BVA Step 1
Input: Nothing
Output: Collection of resources to build

## BVA Step 2
Input: Nothing
Output: One Case, collection of resources to build

## BVA Step 3
Input: Nothing
Output: Collection of 3x ORE, 2 GRAIN

## BVA Step 4
### Test 1:
Input: Nothing
Output: Collection of 3x ORE, 2 GRAIN



# method getVictoryPoints(): int

## BVA Step 1
Input: Nothing
Output: number of points this is worth

## BVA Step 2
Input: Nothing
Output: One Case, 2 victory point

## BVA Step 3
Input: Nothing
Output: value of 2

## BVA Step 4
### Test 1:
Input: Nothing
Output: 2
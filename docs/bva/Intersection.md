This is a data class, only testing uncovered setters/getters

# method getPort() and setPort(Port)

## BVA Step 1
Input: Port to set field
Output: Field set and get same port

## BVA Step 2
Input: Port object
Output: Port object

## BVA Step 3
Input: Port to set field
Output: Port field set, return Port

## BVA Step 4
### Test 1:
Input: Port object
Output: Same Port object and field set

# method getUniqueIndex():int

## BVA Step 1
Input: unique index on board
Output: get same unique index on board

## BVA Step 2
Input: unique index on board int
Output: unique index on board int

## BVA Step 3
Input: unique index on board of intersection
Output: return same unique index on board

## BVA Step 4
### Test 1-2:
Input: 0-1
Output: return 0-1

# method getHexagons() and setHexagons(Hexagon[])

## BVA Step 1
Input: Hexagons to set field
Output: Field set and get same Hexagons

## BVA Step 2
Input: Hexagons object
Output: Hexagons object

## BVA Step 3
Input: Hexagons to set field
Output: Hexagons field set, return Hexagons

## BVA Step 4
### Test 1:
Input: Hexagons object
Output: Same Hexagons object and field set

# method: removeRoad(Road)
#### This BVA is to attempt to fix a mutant, it should be covered fully under integration testing

## BVA Step 1
Input: Road to remove
Output: road not in map

## BVA Step 2
Input: Cases
Output: road not in map

## BVA Step 3
Input: Cases: Road in or not in map
Output: road not in map

## BVA Step 4
### Test 1:
Input: Road in map
Output: Not in map
### Test 2:
Input: Road not in map
Output: Not in map
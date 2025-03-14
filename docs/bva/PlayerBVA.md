# Class: Player

# method: addResource(ResourceType resource):void

## BVA Step 1
- Input: A type of resource, A collection of resources

- Output: A collection of resources

## BVA Step 2
- Input: Cases
  - ResourceType type
    - Case 1: Wood
    - Case 2: Brick
    - Case 3: Wheat
    - Case 4: Sheep
    - Case 5: Ore
  - Collection(ResourceType)
    - Case 1: empty collection
    - Case 2: collection with 1 resource
    - Case 3: collection with >1 resource
    - Case 4: collection with maximum number of resources (95)

- Output: 
  - Collection(ResourceType)

## BVA Step 3
- Input:
  - Wood, []
  - Brick, []
  - Wheat, []
  - Sheep, []
  - Ore, []
- Output:
  - A collection with the resource added.
***
- Input: 
  - Wood, [Wood]
  - Brick, [Brick]
  - Wheat, [Wheat]
  - Sheep, [Sheep]
  - Ore, [Ore]
-Output: 
  - A collection with the resource added.
***
- Input:
  - Wood, [Wood, Wood]
  - Brick, [Brick, Brick]
  - Wheat, [Wheat, Wheat]
  - Sheep, [Sheep, Sheep]
  - Ore, [Ore, Ore]
- Output:
  - A collection with the resource added.
***
- Input:
  - Wood, (max size) collection
  - Brick, (max size) collection
  - Wheat, (max size) collection
  - Sheep, (max size) collection
  - Ore, (max size) collection
- Output:
  - Exception

## BVA Step 4
***
### Each Test case is done with every ResourceType value
***
### Test value 1
- Input: Wood, []
- Output: [Wood]
### Test value 2
- Input: Wood, [Wood]
- Output: [Wood, Wood]
### Test value 3
- Input: Wood, [Wood, Wood]
- Output: [Wood, Wood, Wood]
### Test value 4
- Input: Wood, Collection of size 95
- Output: Exception

# method: removeResource(ResourceType resource):void

## BVA Step 1
- Input: A type of resource, A collection of resources

- Output: A collection of resources, true or false

## BVA Step 2
- Input: Cases
  - ResourceType type
    - Case 1: Wood
    - Case 2: Brick
    - Case 3: Wheat
    - Case 4: Sheep
    - Case 5: Ore
  - Collection(ResourceType)
    - Case 1: empty collection
    - Case 2: collection with 1 resource
    - Case 3: collection with >1 resource
    - Case 4: collection with maximum number of resources (95)

- Output:
  - Collection(ResourceType)
  - A boolean

## BVA Step 3
- Input:
  - Wood, []
  - Brick, []
  - Wheat, []
  - Sheep, []
  - Ore, []
- Output:
  - False
***
- Input:
  - Wood, [Wood]
  - Brick, [Brick]
  - Wheat, [Wheat]
  - Sheep, [Sheep]
  - Ore, [Ore]
    -Output:
  - A collection with the resource removed.
  - True
***
- Input:
  - Wood, [Wood, Wood]
  - Brick, [Brick, Brick]
  - Wheat, [Wheat, Wheat]
  - Sheep, [Sheep, Sheep]
  - Ore, [Ore, Ore]
- Output:
  - A collection with the resource removed.
  - True
***
- Input:
  - Wood, (max size) collection
  - Brick, (max size) collection
  - Wheat, (max size) collection
  - Sheep, (max size) collection
  - Ore, (max size) collection

- Output:
  - A collection with the resource removed.
  - True

## BVA Step 4
***
### Each Test case is done with every ResourceType value
***
### Test value 1
- Input: Wood, []
- Output: 0
### Test value 2
- Input: Wood, [Wood, Wood]
- Output: [Wood], 1
### Test value 3
- Input: Wood, [Wood, Wood, Wood]
- Output: [Wood, Wood], 1
### Test value 4
- Input: Wood, Collection of size 95 (with a Wood element)
- Output: A collection with the resource removed, 1
***
### The following tests are not done for each ResourceType
***
### Test value 5
- Input: Wheat, [Wood]
- Output: 0
### Test value 6
- Input: Sheep, [Ore, Wheat, Wood] 
- Output: 0

# method: checkNumResource():int

## BVA Step 1
- Input: A collection of resources

- Output: The number of resources in the collection

## BVA Step 2
- Input: A Collection(ResourceType)

- Output: A number

## BVA Step 3
- Input:
  - An empty collection
  - A collection with 1 resource
  - A collection with >1 resource
  - A collection with maximum number of resources (95)

- Output:
  - A number

## BVA Step 4
### Test value 1
- Input: []
- Output: 0
### Test value 2
- Input: [Wood]
- Output: 1
### Test value 3
- Input: [Brick]
- Output: 1
### Test value 4
- Input: [Wood, Wheat]
- Output: 2
### Test value 5
- Input: [Wood, Wheat, Ore]
- Output: 3
### Test value 6
- Input: Collection of size 95
- Output: 95

# method: hasResources(Collection<ResourceType> resources):boolean

## BVA Step 1
- Input: 2 collections of resources

- Output: True or false

## BVA Step 2
- Input: A Collection(ResourceType), A collection(ResourceType)

- Output: A boolean

## BVA Step 3
- Input:
  - An empty collection
  - A collection with 1 resource
  - A collection with >1 resource
  - A collection with maximum number of resources (95)
  - An empty collection
  - A collection with 1 resource
  - A collection with >1 resource
  - A collection with maximum number of resources (95)

- Output:
  - 0
  - 1

## BVA Step 4
### Test value 1
- Input: [], []
- Output: An exception
***
### Each Test case is done with every ResourceType value
***
### Test value 2
- Input: [], [Wood]
- Output: An exception
### Test value 3
- Input: [Wood], [Wood]
- Output: True
### Test value 4
- Input: [Wood], []
- Output: False
### Test value 5
- Input: [Ore], [Sheep]
- Output: False
### Test value 6
- Input: [Ore, Ore], [Ore, Ore, Sheep]
- Output: True
### Test value 7
- Input: [Wood, Ore], [Wood, Sheep]
- Output: False
### Test value 8
- Input: A collection of size 95 of Wood elements, [Wood, Wood, Wood]
- Output: False
### Test value 9
- Input: A collection of size 95 of Wood elements, A collection of size 95 of Wood elements
- Output: True



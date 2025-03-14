# Class: Player

# method: addResource(ResourceType type): void

## BVA Step 1
Input: Players collection of resources

Output: Players collection with new resource(s)

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

Output: Collection with new resource(s) added

## BVA Step 3
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
- [Wood]
- [Brick]
- [Wheat]
- [Sheep]
- [Ore]
***
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
- Exception

### Test value 1
- Input: Wood, []
- Output: [Wood]

### Test value 2
- Input: Brick, []
- Output: [Brick]

### Test value 3
- Input: Wheat, []
- Output: [Wheat]

### Test value 4
- Input: Sheep, []
- Output: [Sheep]

### Test value 5
- Input: Ore, []
- Output: [Ore]

### Test value 6
- Input: Wood, [Wood]
- Output: [Wood, Wood]

### Test value 7
- Input: Brick, [Brick]
- Output: [Brick, Brick]

### Test value 8
- Input: Wheat, [Wheat]
- Output: [Wheat, Wheat]

### Test value 9
- Input: Sheep, [Sheep]
- Output: [Sheep, Sheep]

### Test value 10
- Input: Ore, [Ore]
- Output: [Ore, Ore]

### Test value 11
- Input: Wood, [Wood, Wood]
- Output: [Wood, Wood, Wood]

### Test value 12
- Input: Brick, [Brick, Brick]
- Output: [Brick, Brick, Brick]

### Test value 13
- Input: Wheat, [Wheat, Wheat]
- Output: [Wheat, Wheat, Wheat]

### Test value 14
- Input: Sheep, [Sheep, Sheep]
- Output: [Sheep, Sheep, Sheep]

### Test value 15
- Input: Ore, [Ore, Ore]
- Output: [Ore, Ore, Ore]

### Test value 16
- Input: Wood, (size 95 collection)
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
  - false
  - true

## BVA Step 4
### Test value 1
- Input: Checking=[], Player=[]
- Output: True
### Test value 2-6
- Input: Checking=[], Player=[Any resource]
- Output: True
### Test value 7-11
- Input: [Any resource], [Any resource]
- Output: True
### Test value 12-16
- Input: Checking=[Any resource], Player=[]
- Output: False
### Test value 17
- Input: [Ore], Player=[Sheep]
- Output: False
### Test value 18
- Input: [Ore, Ore], Player=[Ore, Ore, Sheep]
- Output: True
### Test value 19
- Input: [Wood, Ore], Player=[Wood, Sheep]
- Output: False
### Test value 20
- Input: A collection of size 19 of each resource, Player=[Wood, Wood, Wood]
- Output: False
### Test value 21
- Input: A collection of size 19 of each resource, A collection of size 19 of each resource
- Output: True

# method: getNumOwnedResource(ResourceType): int
## BVA Step 1
Input: Collection of resources and a resource type

Output: number of that resource type in the collection

## BVA Step 2
Input:
Collection
- empty collection
- collection with 1 resource
- collection with > 1 resource
- collection with max resources
ResourceType Cases
- BRICK, GRAIN, LUMBER, ORE, WOOL

Output: Interval

## BVA Step 3
Input:
Collection
- No resources,
- one of each resource
- two of each resource
- One of each except one
- 19 of each resource
  ResourceType Cases
- BRICK, GRAIN, LUMBER, ORE, WOOL

Output: 0-19 depending on number of resource in collection

## BVA Step 4
### Test 1-5:
Input: Empty collection, use each resource type
Output: 0
### Test 6-10: 
Input: Collection of one of each resource, use each resource type
Output: 1
### Test 11-15:
Input: Collection of two of each resource, use each resource type
Output: 2
### Test 16-20:
Input: One of each resource in collection except type to check
Output: 0
### Test 21-25:
Input: 19 of each resource, use each resource type
Output: 19

# method: setDevelopmentCardAsPlayed(DevelopmentCards):void

## BVA Step 1:
Input: DevelopmentCard to play, player collection of playable & unplayable cards
Output: Remove card from played cards and add to unplayable if exists

## BVA Step 2:
Input:
DevelopmentCard in collection boolean
Playable Development Card cases
- KNIGHT, MONOPOLY, ROAD BUILDING, YEAR OF PLENTY
Playable Cards Collection, empty, one item, two items, max size, duplicates 
Unplayable Cards Collection, empty, one item, two items, max size, duplicates 

Output: Played & Unplayed Card Collection updated
## BVA Step 3:
Input: 
DevelopmentCard Cases
- In collection
- Not in collection
Playable Development Card cases
- KNIGHT, MONOPOLY, ROAD BUILDING, YEAR OF PLENTY
Playable Cards Cases
- Empty, {KNIGHT}, {KNIGHT KNIGHT}, {KNIGHT MONOPOLY}, {14x KNIGHT}
Unplayable Cards Cases
- Empty, {KNIGHT}, {2xKnight}, {VP Knight}, {13x Knight}

Output: Throws illegal argument exception("Player doesn't own any of that card"),
Playable Cards contains one less of Dev card
Unplayable Card contains one more of Dev card

## BVA Step 4:
### Test 1-4:
Input: PlayableDevCard in Collection, Playable ={PlayableDevCard}, Unplayable Empty
Output: Playable = {}, Unplayable = {PlayableDevCard}
### Test 5-8:
Input: PlayableDevCard in Collection, Playable & Unplayable Empty
Output: Throws illegal argument exception("Player doesn't own any of that card")
### Test 9:
Input: Knight in Collection, Playable ={Knight Monopoly}, Unplayable Empty
Output: Playable = {Monopoly}, Unplayable = {Knight}
### Test 10:
Input: Knight in Collection, Playable ={14xKnight}, Unplayable Empty
Output: Playable = {13xKnight}, Unplayable = {Knight}
### Test 11:
Input: Knight in Collection, Playable ={Knight}, Unplayable = {Knight}
Output: Playable = {}, Unplayable = {2xKnight}
### Test 12:
Input: Knight in Collection, Playable ={Knight}, Unplayable = {2xKnight}
Output: Playable = {}, Unplayable = {3xKnight}
### Test 13:
Input: Knight in Collection, Playable ={Knight}, Unplayable = {Knight, VP}
Output: Playable = {}, Unplayable = {2xKNIGHT, VP}
### Test 14:
Input: Knight in Collection, Playable ={Knight}, Unplayable = {13xKnight}
Output: Playable = {}, Unplayable = {14xKnight}

# method: addDevelopmentCard(DevelopmentCards):void

## BVA Step 1:
Input: DevelopmentCard, future dev cards, Unplayable Dev cards

Output: future or Unplayable dev cards gets the new DevCard

## BVA Step 2:
Input:
DevelopmentCard Cases
- Any of enum value
future Cards and Unplayable Cards cases
- Empty, one item, two items, max size

Output: Collection of future or unplayable updated

## BVA Step 3:
Input:
DevelopmentCard Cases
- KNIGHT, MONOPOLY, ROAD, YEAR, VICTORY
future Cards Cases
- Empty, {KNIGHT}, {KNIGHT ROAD}, 13x KNIGHT
Unplayable Cards Cases
- Empty, {VP}, {VP KNIGHT}, 4x VP

Output: future Cards gains 1 card or Unplayable Cards gains 1 card,
future Cases: 
- Empty, {One Dev Card}, {14x Knight}
Unplayable Cases:
- Empty, {VP}, {5x VP}

## BVA Step 4:
### Test 1-4:
Input: AnyDevCard except VP, Both empty
Output: future = {AnyDevCArd except VP}, unplayable = {}
### Test 5:
Input: VP card, Both empty
Output: future = {}, Unplayable = {VP}
### Test 6:
Input: Knight, future = {KNIGHT}, Unplayable empty
Output: future = {2x Knight}, Unplayable empty
### Test 7:
Input: Knight, future = {KNIGHT, ROAD}, Unplayable empty
Output: future = {2x Knight, ROAD}, Unplayable empty
### Test 8:
Input: Knight, Future = {13x Knight}, Unplayable empty
Output: Future = {14xKnight}, Unplayable empty
### Test 9:
Input: VP, Future = {}, Unplayable = {VP}
Output: Future = {}, Unplayable = {2xVP}
### Test 10:
Input: VP, Future = {}, Unplayable = {VP, KNIGHT}
Output: Future = {}, Unplayable = {2xVP, KNIGHT}
### Test 11:
Input: VP, Future = {}, Unplayable = {4xVP}
Output: Future = {}, Unplayable = {5xVP}

# method: startTurn(DevelopmentCard): void
#### We only need to verify that this method emptys the
#### future development card collection, as we know it will be added to the right collection

## BVA Step 1
Input: Collection of DevCards that aren't yet playable
Output: Empty collection of DevCards with them added to Playable

## BVA Step 2
Input: Collection size cases
Output: Empty collection, playable size count

## BVA Step 3
Input: Empty collection, 1 {KNIGHT}, 2 {KNIGHT}, (14x{knight},2x{Monopoly/Plenty/RoadBuilding})
Output: Empty collection, 1,2,20 cards

## BVA Step 4
### Test 1
Input: Empty Collection
Output: Empty Collection, empty playable

### Test 2
Input: 1 {KNIGHT},
Output: Empty Collection, 1 playable

### Test 3
Input: 2 {KNIGHT},
Output: Empty Collection, 2 playable

### Test 4
Input: (14x{knight},2x{Monopoly/Plenty/RoadBuilding})
Output: Empty Collection, 20 playable


# method: isDevCardPlayed():boolean and setDevCardPlayed()

## BVA Step 1
Input: current state of devCardPlayed
Output: Field set to true and get same boolean

## BVA Step 2
Input: boolean
Output: boolean

## BVA Step 3
Input: setDevCardPlayed called or not
Output: true/false

## BVA Step 4
### Test 1:
Input: isDevCardPlayed() with no setDevCardPlayed
Output: false
### Test 2:
Input: isDevCardPlayed() with setDevCardPlayed
Output: true

# method getPlayerColor(): Color

## BVA Step 1
Input: Player color
Output: Player color

## BVA Step 2
Input: Player Color cases
Output: Color cases

## BVA Step 3
Input: Player color red,black, green,blue
Output: Player color red,black, green, blue

## BVA Step 4
### Test 1-4:
Input: x = (red,black, green,blue)
Output: x

# method getPlayerName(): String

## BVA Step 1
Input: Player name
Output: Player name

## BVA Step 2
Input: Player name cases
Output: name cases

## BVA Step 3
Input: Player name "", "test", "test2"
Output: Player name "", "test", "test2"

## BVA Step 4
### Test 1-4:
Input: x = (red,black, green,blue)
Output: x

# method get:int and setVictoryPoints()

## BVA Step 1
Input: num victory points
Output: get same num victory points

## BVA Step 2
Input: num victory points interval
Output: num victory points interval

## BVA Step 3
Input: num victory points
Output: return same num victory points

## BVA Step 4
### Test 1-11:
Input: 0-10
Output: return 0-10

# method get and setNumRoads(int)

## BVA Step 1
Input: num roads played
Output: check field set and return

## BVA Step 2
Input: num roads interval
Output: num roads interval, field set

## BVA Step 3
Input: num roads 0-13
Output: num roads 0-13, set, return same value

## BVA Step 4
### Test 1-14:
Input: 0-13
Output: return 0-13, set, return same value

# method get and setNumCities(int)

## BVA Step 1
Input: num cities played
Output: check field set and return same value

## BVA Step 2
Input: num cities interval
Output: num cities interval, field set

## BVA Step 3
Input: num cities 0-4
Output: num cities 0-4, set, return same value

## BVA Step 4
### Test 1-5:
Input: 0-4
Output: return 0-4, check set field

# method get and setNumSettlements(int)

## BVA Step 1
Input: num settlements played
Output: check field set, return same value

## BVA Step 2
Input: num settlements interval
Output: num settlements interval

## BVA Step 3
Input: num settlements 0-3
Output: num settlements 0-3, set, return same value

## BVA Step 4
### Test 1-4:
Input: 0-3
Output: return 0-3, check set field

# method: hasDevelopmentCard(DevelopmentCard):boolean

## BVA Step 1
Input: Collection of player development Cards, card type
Output: whether owns card

## BVA Step 2
Input: card type in collection, card type cases
Output: boolean

## BVA Step 3
Input: Card in collection or card not in collection, Card Cases: knight,plenty,monopoly,vp,road
Output: true/false

## BVA Step 4
### Test 1-5
Input: knight,plenty,monopoly,vp,road in collection and checked
Output: true
### Test 6-10
Input: Empty Collection, any card
Output: false
### Test 11
Input: Knight in Collection, check for Road
Output: false

# method: hasUnplayableDevelopmentCard(DevelopmentCard):boolean

## BVA Step 1
Input: Collection of player development Cards, card type
Output: whether owns card

## BVA Step 2
Input: card type in collection, card type cases
Output: boolean

## BVA Step 3
Input: Card in collection or card not in collection, Card Cases: knight,plenty,monopoly,vp,road
Output: true/false

## BVA Step 4
### Test 1-5
Input: knight,plenty,monopoly,vp,road in collection and checked
Output: true
### Test 6-10
Input: Empty Collection, any card
Output: false
### Test 11
Input: Knight in Collection, check for Road
Output: false

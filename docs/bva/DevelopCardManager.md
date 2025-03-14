
# method:playYearOfPlentyCard(Player player, ResourceType resource1, ResourceType resource2):Collection<ResourceType>

## BVA Step 1
Input: Player, Catan Resource, Catan Resource, Bank has sufficient resources

Output: Collection of player's resources are returned with 2 additional resources of their choice

## BVA Step 2
Input: Collection
- empty collection
- collection with 1 resource
- collection with > 1 resource
- collection with max resources
ResourceType Cases
- BRICK, GRAIN, LUMBER, ORE, WOOL
Bank Cases
- Sufficient resources
- Insufficient resources

Output: Collection of resources with 2 more resources
- Cases
    - Collection with 2 of the same resource added (5 cases)
    - Collection with 2 different resources added (10 cases)

## BVA Step 3
Input: All pairs with repitition of ResourceType
- BRICK, GRAIN, LUMBER, ORE, WOOL
Player Resources = (empty, [LUMBER], [LUMBER LUMBER], [19xLUMBER,19xGRAIN,19xORE,19xWOOL,19xBRICK])
Bank sufficient or insufficient resource

Output:

## BVA Step 4
### Test value 1-5 (This was merged into test 6-15 after passing)
Player empty resources, bank sufficient resources, LUMBER, LUMBER -> resources[LUMBER,LUMBER]
Repeat for each resource type
### Test value 6-15
Player empty resources, bank sufficient resources, LUMBER, WOOL -> resources[LUMBER, WOOL]
Repeat for each pair combination of resource type
### Test value 16
Player [LUMBER], bank sufficient resources, LUMBER, LUMBER -> player resources[LUMBER, LUMBER, LUMBER]
### Test value 17
Player resources [LUMBER LUMBER],bank sufficient resources,  LUMBER, LUMBER -> resources[LUMBERx4]
### Test value 18
Player resources [19x each resource],bank sufficient resources,  LUMBER, LUMBER -> resources[19x each resource]
Note the above case isn't ever possible, as the bank will only have sufficient resources if the player doesn't have all resources
### Test value 19
Player empty resources, bank insufficient resources, LUMBER, LUMBER -> player empty resources
### Test value 20
Player empty resources, bank insufficient resources, WOOL, LUMBER -> player empty resources


# method: playMonopolyCard(Collection<Player>, Player, ResourceType):Collection<ResourceType>

## BVA Step 1
Input: All Players in game, Player playing card, ResourceType they are claiming

Output: Returns collection of individual player's resources with resources stolen from other players
Other player's resource collection has resourced removed

## BVA Step 2
Input: Player Collection (2-4 players only)
- collection with 2/min players
- collection with 3 players
- collection with 4/max players
Individual Player and each Players' Resource Collection
- empty collection
- collection with 1 resource
- collection with > 1 resource
- collection with max resources
ResourceType Cases
- BRICK, GRAIN, LUMBER, ORE, WOOL

Output: ResourceType Collection corresponding to individual player with resourcetype stolen from other players in collection
Collection of Players with their resource collection missing resourcetype

## BVA Step 3
Input: ResourceType
- BRICK, GRAIN, LUMBER, ORE, WOOL
Individual player Resources = (empty, [any individual resource], [LUMBER WOOL], [19xLUMBER,19xGRAIN,19xORE,19xWOOL,19xBRICK])
Collection of Players with 2,3, or 4 players
Players in collection resources = (empty, [any individual resource], [all duplicate pairs]) Different resources don't matter with implementation
Single player in collection having resources = [19xEach Resource]

Output:

## BVA Step 4
### Test value 1
Player empty resource, collection of 2 players, LUMBER, other player resource = [],
-> both have empty resources
### Test value 2-6
Player empty resource, collection of 2 players, ResourceType R, other player resource = [R],
-> original player = [R], other player = []
### Test value 7-11
Player empty resource, collection of 2 players, ResourceType R, other player resource = [R R],
-> original player = [R R], other player = []
### Test value 12-16
Player resource = [R], collection of 2 players, Resource type R, other player resource = [],
-> original player = [R], other = []
### Test value 17
Player resource = [19x Each Resource], collection of 2 players, LUMBER, other player resource = [],
-> original player = [19x Each Resource], other = []
### Test value 18
Player resource = [], collection of 2 players, LUMBER, other player resource = [19x Each Resource],
-> original player = [19xLUMBER], other = [19x All Other Resource]
### Test value 19
Player resource = [], collection of 3 players, LUMBER, other players each have = [LUMBER],
-> original player = [LUMBER LUMBER], others empty
### Test value 20
Player resource = [], collection of 4 players, LUMBER, other players each have = [LUMBER],
-> original player = [3xLUMBER ], others empty
### Test value 21
Player resource = [], collection of 4 players, LUMBER, two other players have = [WOOL], one player has = [LUMBER],
-> original player = [LUMBER], two others = [WOOL], one other = []


# method: playRoadBuildingCard(Player player, Intersection[][] intersections): void

## BVA Step 1
Input: Player placing road, intersections they want to place the road on

Output: Adds roads to the board

## BVA Step 2
Input: Cases (This just calls the already implemented road placing logic twice)
- Both roads successfully placed
- One bad road placement
- Both bad road placements

Cases
- Same intersection pairs
- Different intersection pairs

Cases
- Two intersection pairs
- Less than two intersections pairs
- Only one intersection per pair
- More than two intersection pairs

Output: boolean of success, throwable error

## BVA Step 3
Input: Road cases
- Valid Road from (0,1), (1,53)
- Valid Road from (0,1), invalid road from (53,7)
- Invalid roads from (1,9), (53,7)

Identical road intersections
- (0,1), (0,1)
Different road intersections
- (0,1), (1,53)

Correct intersection array dimensions
- {{0,1},{1,53}}
- {{0,1,3},{1,53,4}} (extra ignored)
Incorrect intersection array dimensions
- {{0},{1}}
- {{0,1}}

Output: true and false, IllegalArgumentException

## BVA Step 4
### Test Value 1
Input: Roads from (0,1), (1,53)
Output: True
### Test Value 2
Input: Roads from (0,1), (53,7)
Output: False
### Test Value 3
Input: Roads from (1,9), (53,7)
Output: False
### Test Value 4
Input: Roads from (0,1), (0,1)
Output: False
### Test Value 5
Input: Roads from (0,1), (1,53), passed as {{0,1,3},{1,53,4}}
Output: IllegalArgumentException
### Test Value 6
Input: Intersections passed as {{0},{1}}
Output: IllegalArgumentException
### Test Value 7
Input: Intersections passed as {{0,1}}
Output: IllegalArgumentException
### Test Value 8
Input: Roads from (0,1), (1,53), passed as {{0,1},{1,53},{3,4}}
Output: IllegalArgumentException

# method: findLongestRoad(Player[] players, Road[] roads): Player
# Restrictions: roads >= 5, broken roads, no ties

## BVA Step 1
Input: All players in the game, all roads on the board, settlements on the intersections associated with the roads
Output: If any player won longest road, The winning player

## BVA Step 2
Input: Collection (2-4 players only), Cases, Cases
Output: Boolean, Cases

## BVA Step 3
Input: 2 players, 4 players, one |5| road, two |5| roads, one road splits into two, both |4|, four roads > |5|, no settlement, friend settlement, enemy settlement
Output: true, false, Player1, Player4

## BVA Step 4
### Test Value 1
Input: 2 players, 2 |5| roads, no settlements
Output: false, null
### Test Value 2
Input: 2 players, |5| road, no settlements
Output: true, Player1
### Test Value 3
Input: 2 players, |5| road, friend settlement
Output: true, Player1
### Test Value 4
Input: 2 players, |5| road, enemy settlement
Output: false, null
### Test Value 5
Input: 4 players, three roads |5|, one |6|, no settlements
Output: true, Player4
### Test Value 6
Input: 2 players, one road splits into two |4|, no settlements
Output: false, null
### Test Value 7
Input: 2 players, one road splits into two |5|, no settlements
Output: true, player1
### Test Value 8
Input: 2 players, 2 |5| road both on Player1, no settlements
Output: true, Player1

# method: playKnightCard(Player player, Player toStealFrom, int newRobberHexagon): ResourceType
### This relies on the BVA tested moveRobber and stealResources in GameManager and BoardDisplay.
### Thus, we don't strictly verify the move and steal logic works, only that it succeeds.

## BVA Step 1
Input: The player who uses the card, their playable dev cards and resources, player to steal from,
the new robber hex location, and the current robber location
Output: robber moved and player resource stolen

## BVA Step 2
Input: 
Player cases
- The player has a knight card in its hand
- The player does not have a knight card in its hand
Player resources collection
- empty, single resource, multiple resources
Player toStealFrom cases
- Owns structure on newRobberHex
- Not on newRobberHex
Hexagon count for new robber location
Robber location cases:
- New robber location
- Same as old location

Largest Army cases
- player has the most knights among players above 2
- player has played 2 knights and is playing third
- no player has played 3 knights

Output: throw error if fail,
updated player dev card collection, 
updated both players' resource collections,
return resource stolen

## BVA Step 3
Input: 
Player has or does not have Knight Card in dev cards list
Player toStealFrom
- owns settlement on Robber hex (use hex 0)
- doesn't own settlement on Robber hex 
- null 
Player resources
- empty, {GRAIN}, {WOOL}, {WOOL WOOL}
Player toStealFrom resources
- empty, {GRAIN}
LargestArmy
- Player has played 2 knights
- Player has played 0 knights
- Player has played 3 knights, while another already has 3
- Player has played 2 knights, while another has 3

Hexagon cases
- Valid integer range from [0-18], test 0, 1, 18 as success, -1, 19 failure cases
- Ensure robber isn't already on hex
Robber Starting location
- Starts at hex 0 
- Starts at hex 18

Output: IllegalArgumentException("Invalid hex index") if bad hex index,
IllegalArgumentException("Robber is already on this hex") if unmoved robber,
IllegalArgumentException("Player does not have a knight card"),
IllegalArgumentException("Player is not on the hex"),
verify new robber location,
verify player toStealFrom lost resource if they had any,
verify player gained resource if possible,
return stolen resource or null if none stolen
player gets Largest Army if they have at least 3 knights and the most knights


## BVA Step 4
### Test 1
Input: Player has dev {KNIGHT} & no resources,  RobberFrom 18->0, toStealFrom on Hex 0 and has {GRAIN}, no knights played
Output: Player has no dev & {GRAIN}, robber at 0, toStealFrom has {}, no LargestArmy, return GRAIN
### Test 2
Input: Player has no dev & no resources,  RobberFrom 18->0, toStealFrom on Hex 0 and has {GRAIN}, no knights played
Output: IllegalArgumentException("Player does not have a knight card")
### Test 3
Input: Player has dev {Knight} & no resources,  RobberFrom 18->18, toStealFrom on Hex 0 and has {GRAIN}, no knights played
Output: IllegalArgumentException("Robber is already on this hex") if unmoved robber
### Test 4
Input: Player has dev {Knight} & no resources,  RobberFrom 18->0, toStealFrom on Hex 18 and has {GRAIN}, no knights played
Output: IllegalArgumentException("Player is not on the hex")
### Test 5-6
Input: Player has dev {Knight} & no resources,  RobberFrom 18->-1 or 19, toStealFrom on Hex 1 or 19 and has {GRAIN}, no knights played
Output: IllegalArgumentException("Invalid hex index")
### Test 7-8
Input: Player has dev {Knight} & no resources,  RobberFrom 0->1 or 18, toStealFrom on Hex 1 or 18 and has {GRAIN}, no knights played
Output: Player has no dev & {GRAIN}, robber at 0, toStealFrom has {}, no LargestArmy, return GRAIN
### Test 9
Input: Player has dev {Knight} & no resources,  RobberFrom 18->0, toStealFrom on Hex 0 and has {GRAIN}, two knights played
Output: Player has no dev & {GRAIN}, robber at 0, toStealFrom has {}, player gets LargestArmy, return GRAIN
### Test 10
Input: Player has dev {Knight} & no resources,  RobberFrom 18->0, toStealFrom on Hex 0 and has {GRAIN}, 2 knights played another player has 3
Output: Player has no dev & {GRAIN}, robber at 0, toStealFrom has {}, no LargestArmy, return GRAIN
### Test 11
Input: Player has dev {Knight} & no resources,  RobberFrom 18->0, toStealFrom on Hex 0 and has {GRAIN}, 3 knights played another player has 3
Output: Player has no dev & {GRAIN}, robber at 0, toStealFrom has {}, player gets LargestArmy, return GRAIN
### Test 12
Input: Player has dev {Knight} & no resources,  RobberFrom 18->0, toStealFrom on Hex 0 and has {}, 0 knights played
Output: Player has no dev & {}, robber at 0, toStealFrom has {}, no largestArmy, return null
### Test 13
Input: Player has dev {Knight} & no resources,  RobberFrom 18->0, None to steal from on hex, 0 knights played
Output: Player has no dev & {}, robber at 0, no largestArmy, return null
### Test 14
Input: Player has dev {Knight} & no resources,  RobberFrom 18->0, null Player, 0 knights played
Output: IllegalArgumentException("Hex has players and none specified");


# method getLargestArmyOwner() and setLargestArmyOwner(Player)

## BVA Step 1
Input: Player to set field
Output: Field set and get same Player

## BVA Step 2
Input: Player object
Output: Player object

## BVA Step 3
Input: Player to set field, test two different player objects (player1/player2)
Output: Player field set, return Player

## BVA Step 4
### Test 1-2:
Input: Player object, first and second players
Output: Same Player object and field set


# method getLongestRoadOwner() and setLongestRoadOwner(Player)

## BVA Step 1
Input: Player to set field
Output: Field set and get same Player

## BVA Step 2
Input: Player object
Output: Player object

## BVA Step 3
Input: Player to set field, test two different player objects (player1/player2)
Output: Player field set, return Player

## BVA Step 4
### Test 1-2:
Input: Player object, first and second players
Output: Same Player object and field set
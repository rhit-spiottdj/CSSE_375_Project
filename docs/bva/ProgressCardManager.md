
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
# Class: Bank

# Method: obtainResource(ResourceType taking, int quantity) : boolean

## BVA Step 1
Input: The resource type and quantity, resources in reserve
Output: A true or false value, resources in reserve amount updated

## BVA Step 2
Input: Cases, Number, Collection of Resources
Output: Boolean, Collection of Resources

## BVA Step 3
Input: 
Resource Cases:
Lumber, Brick, Grain, Wool, Ore
Quantity Number:
-1, 0, 1, {max quantity=19}, {max quantity=19 + 1}, Integer.MIN_VALUE, Integer.MAX_VALUE
Collection:
Empty, 1 resource, 2 different resources, 2 of the same resource, 19 of each resource (Max)

Output: True, False
Collection: Empty, 1 resource, 2 different resources, 2 of the same resource, 19 of each resource (Max)

## BVA Step 4
### Test value 1
Input: Lumber, -1, Max resources
Output: False, Max resources
### Test value 2
Input: Lumber, Integer.MIN_VALUE, Max resources
Output: False, Max Resources
### Test value 3
Input: Lumber, 0, Max resources
Output: False, max resources
### Test value 4
Input: Lumber, {max quantity=19 + 1}, Max resources
Output: False, max resources
### Test value 5
Input: Brick, {max quantity=19}, Max resources
Output: True, 18 Brick rest Max
### Test value 6-10
Input: Any Resource, 1, Max resources
Output: True, 18 Of Resource rest Max
### Test value 11
Input: Brick, Integer.MAX_VALUE, Max resources
Output: False, max resources
### Test value 12
Input: Grain, 1, 1 grain resource
Output: True, 0 resources
### Test value 13
Input: Grain, 1, 1 grain and 1 ore resource
Output: True, 1 ore
### Test value 14
Input: Grain, 1, no resources
Output: False, no resources

# Method: tradeResourcePort(Port type, ResourceType giving, ResourceType taking, int quantity) : boolean

### Player is verified to have the resources to trade before this method is called
### Checking if a quantity greater than 9 is being traded for is pointless as the player cannot 
### have the resources to complete the trade

## BVA Step 1
Input: The port type,
    the resource type you are giving,
    the resource type you want,
    and a positive number,
    the amount of resources in the bank,
    resources in bank
Output: A true or false value, updated resource amounts in bank

## BVA Step 2
Input: Port cases, two Resource type cases, Number, Case whether bank has sufficient resources, count of resources in bank
Output: Boolean, count of changed bank resources

## BVA Step 3
Input: 
Port Type
- Three_To_One
- Two_To_One:
  - Lumber
  - Brick
  - Grain
  - Wool
  - Ore
Resource Type to give or Take
- Lumber
- Brick
- Grain
- Wool
- Ore
Amount:
- -1, 0, 1, {max quantity=9}, Integer.Min
Bank:
- Sufficient Resources
- Insufficient Resources
Bank Count of changed resource
- 0, 1, 2, 3, 19
Output: 
Success:
- True/False
Bank Count of changed resource
- 0, 1, 2, 19
## BVA Step 4
### Test value 1
Input: Three_To_One, Lumber:(19 in bank), Brick:(19 in bank), -1, 
Output: False
### Test value 2
Input: Three_To_One, Lumber:(19 in bank), Brick:(19 in bank), Integer.MIN_VALUE
Output: False
### Test value 3
Input: Three_To_One, Lumber:(19 in bank), Brick:(19 in bank), 0
Output: False
### Test value 4
Input: Three_To_One, Lumber:(16 in bank), Brick:(19 in bank), 1
Output: True, Lumber:(19 in bank), Brick:(18 in bank)
### Test value 5
Input: Two_To_One:Grain, Grain:(1 in bank), Wool:(19 in bank), {max-quantity=9}
Output: True, Grain:(19 in bank), Wool:(10 in bank)
### Test value 6-9
Input: Two_To_One:{a ResourceType}, Same ResourceType:(17 in bank) as Port, Brick:(19 in bank), 1
Output: True, ResourceType:(19 in bank), Brick:(18 in bank)
### Test value 10
Input: Two_To_One:Brick, Brick:(17 in bank) as Port, Grain:(19 in bank), 1
Output: True, Brick:(19 in bank), Grain:(18 in bank)
### Test value 11
Input: Two_To_One:Grain, Lumber:(19 in bank) , Brick:(19 in bank), 1
Output: False
### Test value 12
Input: Two_To_One:Grain, Lumber:(19 in bank) , Brick:(19 in bank), 1
Output: False
### Test value 13
Input: Two_To_One:Grain, Grain:(0 in bank), Brick:(1 in bank), 1
Output: True, Grain:(2 in bank), Brick:(0 in bank)
### Test value 14
Input: Two_To_One:Grain, Grain:(1 in bank), Brick:(2 in bank), 1
Output: True, Grain:(3 in bank), Brick:(1 in bank)
### Test value 14
Input: Three_To_One:Grain, Grain:(2 in bank), Brick:(3 in bank), 1
Output: True, Grain:(5 in bank), Brick:(2 in bank)
### Test value 15
Input: Three_To_One:Grain, Grain:(2 in bank), Brick:(0 in bank), 1
Output: False

# Method: tradeResourceBank(ResourceType giving, ResourceType taking, int quantity) : boolean

## BVA Step 1
Input: the resource type you are giving, the resource type you want, and a positive number
Output: A true or false value

## BVA Step 2
Input: Cases, Cases, Number
Output: Boolean, cases

## BVA Step 3
Input: Lumber, Brick, Grain, Wool, Ore, -1, 0, 1, {max quantity=19}, {max quantity=19 + 1}, Integer.MIN_VALUE, Integer.MAX_VALUE
Output: True, False, bank inventory unchanged, bank inventory updated

## BVA Step 4
### Test value 1
Input: Lumber, Brick, -1
Output: False
### Test value 2
Input: Lumber, Brick, Integer.MIN_VALUE
Output: False
### Test value 3
Input: Lumber, Brick, 0
Output: False
### Test value 4
Input: Lumber, Brick, 1
Output: True
### Test value 5
Input: Grain, Wool, {max quantity=19}
Output: True
### Test value 6
Input: Lumber, Brick, {max quantity=19 + 1}
Output: False
### Test value 7
Input: Lumber, Brick, Integer.MAX_VALUE
Output: False
### Test value 8
Input: Ore, Wool, 1
Output: True
Bank Inventory Change: Ore increases by 4, Wool decreases by 1
### Test value 9
Input: Brick, Grain, 20
Output: False
Bank Inventory Change: None

# Method: setupDevelopmentCards(): void

## BVA Step 1
Input: nothing
Output: Collection of initial development Cards for game

## BVA Step 2
Input: Nothing
Output: Collection size

## BVA Step 3
Input: nothing
Output: Collection with 14x Knight, 2x Monopoly/YearOfPlenty/Road Building, 5x Victory, 14x Knight 

## BVA Step 4
### Test 1:
Input: nothing
Output: Collection with 14x Knight, 2x Monopoly/YearOfPlenty/Road Building, 5x Victory, 14x Knight

# Method: obtainDevelopmentCard(DevelopmentCards card) : boolean
## BVA Step 1
Input: Player wants to obtain a development card
Output: A true or false value

## BVA Step 2
Input: Cases
Output: True or false and the player has the development card (integration)

## BVA Step 3
Input: player has enough resources, player does not have enough resources,
player has enough resources but the cards are out of stock
Output: True, False

## BVA Step 4
### Test value 1
Input: Player has enough resources, card is in stock
Output: True
### Test value 2
Input: Player does not have enough resources, card is in stock
Output: False
### Test value 3
Input: Player has enough resources, card is out of stock
Output: False
### (integration)
### Test value 4
Input: Player has enough resources, card is in stock
Output: True, Player has the card
### (integration)
### Test value 5
Input: Player has enough resources, card is in stock
Output: True, size of development card deck decreased by 1
### (integration)
### Test value 6
Input: Player has enough resources, card is in stock
Output: True, Player spent resources


# Method: PlayerTrade(Player player1, Player player2, Collection<ResourceType> resources1, Collection<ResourceType> resources2) : boolean
## BVA Step 1
Input: The two players trading and the resources they want to trade
Output: A true or false value for if the trade was successful and 
the resources of the players updated

## BVA Step 2
Input: Cases x2, and Collection with Duplicates x2
#The cases are whether the player has the resources to trade or not
Output: Boolean, Collection

## BVA Step 3
Input: Player doesn't have resources, player has resources,
Trades: empty, one, three, max resources (19 of each)
Make these combinations: different sized collections, same sized collections, 
one empty other not, both non-empty, duplicates, no duplicates
Output: True, False, Player resources updated

## BVA Step 4
### Test value 1
Input: both Player's don't have resources, grain for grain
Output: False, Player resources unchanged
### Test value 2
Input: Player1 has resources, Player2 doesn't, grain for grain
Output: False, Player resources unchanged
### Test value 3
Input: Player1 hasn't resources, Player2 does, ore for ore
Output: False, Player resources unchanged
### Test value 4
Input: both have resources, ore for ore
Output: True, Player resources updated
### Test value 5
Input: both have resources, both empty trades
Output: True, Player resources updated
### Test value 6
Input: both have resources, three brick for one ore
Output: True, Player resources updated
### Test value 7
Input: both have resources, one wool, one lumber, one brick for one ore
Output: True, Player resources updated
### Test value 8
Input: both have resources, empty for one wool
Output: True, Player resources updated
### Test value 9
Input: both have resources, (19 of each resource) for empty
Output: True, Player resources updated

# Method: playerDiscard(Player player, Collection<ResourceType> resources) : boolean

## BVA Step 1
Input: The player and the resources they want to discard
Output: A true or false value for if the discard was successful and
the player's resources updated and the banks resources updated

## BVA Step 2
Input: Cases, Collection with Duplicates
Output: Boolean, Collection

### Note: The collection is never empty at this step
## BVA Step 3
Input: Player object, collection of resources to discard
Discards: one, two, three, max resources (19)

Output: True, False, Player resources updated, Bank resources updated

## BVA Step 4

### Test value 1-5
Input: Player object, one of each resource
Output: True, Player resources - one of the resource, Bank resources + one of the resource

### Test value 6-10
Input: Player object, 2 of each resource
Output: True, Player resources - 2 of each resource, Bank resources + 2 of each resource


### Test value 11-15
Input: Player object, 19 of each resource
Output: True, Player resources - 19 of each resource, Bank resources + 19 of each resource

### Test value 16
Input: Player object that does not have resources to discard
Output: False, Player resources unchanged, Bank resources unchanged

### Test value 17-21
Input: Player object with resource to give back
Output: False, player remove resources error, Bank resources unchanged

### Test value 22-26
Input: Player object with resource to give back, bank has max resource of that type
Output: False, Player resources unchanged, Bank resources unchanged


# Method: giveBackResource(ResourceType giving, int amount) : boolean

## BVA Step 1
Input: The resource type and quantity, resources in reserve
Output: A true or false value, resources in reserve amount updated

## BVA Step 2
Input: Cases, Number, Collection of Resources
Output: Boolean, Collection of Resources

## BVA Step 3
Input:
Resource Cases:
Lumber, Brick, Grain, Wool, Ore
Quantity Number:
-1, 0, 1, {max quantity=19}, {max quantity=19 + 1}, Integer.MIN_VALUE, Integer.MAX_VALUE
Collection:
Empty, 1 resource, 2 different resources, 2 of the same resource, 19 of each resource (Max)

Output: True, False
Collection: Empty, 1 resource, 2 different resources, 2 of the same resource, 19 of each resource (Max)

## BVA Step 4
### Test value 1
Input: Lumber, -1, empty resources
Output: False, less than 1

### Test value 2
Input: Lumber, Integer.MIN_VALUE, empty resources
Output: False, less than 1

### Test value 3
Input: Lumber, 0, empty resources
Output: False, less than 1 resource

### Test value 4
Input: Lumber, {max quantity=19 + 1}, empty resources
Output: False, max resources

### Test value 5-10
Input: Resource being tested (brick, grain, wool, ore), 19, empty resources
Output: True, 19 of resource being tested rest are empty

### Test value 11-15
Input: Resource being tested (brick, grain, wool, ore), 1, empty resources
Output: True, 1 of resource being tested rest are empty

### Test value 16-20
Input: Resource being tested (brick, grain, wool, ore), 1, max resources in bank
Output: False, max resources

### Test value 21-25
Input: Resource being tested (brick, grain, wool, ore), 1, 1 resource in bank
Output: True, 2 resources in bank

### Test value 26-30
Input: Resource being tested (brick, grain, wool, ore), 1, 2 resources in bank
Output: True, 3 resources in bank

### Test value 31-35
Input: Resource being tested (brick, grain, wool, ore), 2, 1 resource in bank
Output: True, 3 resource in bank

### Test value 36-40
Input: Resource being tested (brick, grain, wool, ore), 2, 2 resources in bank
Output: True, 4 resources in bank


# method: get:int and setBankResource(ResourceType, int)

## BVA Step 1
Input: Resource Type and amount to set in bank
Output: Amount of resource in bank

## BVA Step 2
Input: Resource type cases, count
Output: count

## BVA Step 3
Input: grain,wool,brick,ore,lumber, Amounts: 0,1,19
Output: 0,1,19

## BVA Step 4
### Test 1-5
Input: ResourceType x, 5
Output: 5 
### Test 6-8
Input: Grain, 0,1,19
Output: 0,1,19

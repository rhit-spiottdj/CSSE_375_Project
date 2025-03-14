# Class: DiceManager

# method: getDie(int index):int // 1 indexed

## BVA Step 1
Input: A positive number
Output: A number or throw an exception

## BVA Step 2
Input: Number
Output: Number or Exception

## BVA Step 3
Input: -1, 0, 1, 2, numOfDice, numOfDice + 1
Output: 1, 6 or IllegalArgumentException("The input must be between [1, numOfDice]")

## BVA Step 4
### Test value 1
Input: -1
Output: IllegalArgumentException("The input must be between [1, numOfDice]")
### Test value 2
Input: numOfDice + 1
Output: IllegalArgumentException("The input must be between [1, numOfDice]")
### Test value 3
Input: 0
Output: IllegalArgumentException("The input must be between [1, numOfDice]")
### Test value 4
Input: 1 
Output: 1 
### Test value 5
Input: 2 (dice 1 = 3, dice 2 = 6)
Output: 6 
### Test value 6
Input: numOfDice (last dice = 5)
Output: 5

# method: getCurrentDiceTotal():int

## BVA Step 1
Input: None
Output: a number

## BVA Step 2
Input: None 
Output: Number

## BVA Step 3
Input: None
Output: 1, 6 * number of dice, 3 + 5 (3 on dice 1 and 5 on dice 2)

## BVA Step 4
### Test value 1
Input: None
Output: 1
### Test value 2
Input: None
Output: 6 * number of dice (2 dice)
### Test value 3
Input: None
Output: 3 + 5 (3 on dice 1 and 5 on dice 2)

# method: DiceManager(int numDice)

## BVA Step 1
Input: The number of dice to roll
Output: None or throw an exception

## BVA Step 2
Input: Number
Output: Exception or None

## BVA Step 3
Input: -1, 0, 1, 3
Output: IllegalArgumentException("The input must be between [1, 2]") or None

## BVA Step 4
### Test value 1
Input: -1
Output: IllegalArgumentException("The input must be between [1, 2]")
### Test value 2
Input: 0
Output: IllegalArgumentException("The input must be between [1, 2")
### Test value 3
Input: 1
Output: None
### Test value 4
Input: 2
Output: None
### Test value 5
Input: 3
Output: IllegalArgumentException("The input must be between [1, 2]")


# method: rollAllDice():int

## BVA Step 1
Input: None
Output: The combined value of all the dice after rolling them.

## BVA Step 2
Input: None
Output: Number

## BVA Step 3
Input: None
Output: 1, 6 * number of dice

## BVA Step 4
### Test value 1
Input: None
Output: 1
### Test value 2
Input: None
Output: 6 * number of dice (2 dice)
### Test value 3
Input: None
Output: 3 + 5 (3 on dice 1 and 5 on dice 2)

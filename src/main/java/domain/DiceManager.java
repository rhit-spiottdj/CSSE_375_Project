package domain;

import java.util.Random;

public class DiceManager {

    Random random;
    int[] dice;

    public static final int DICE_MAX_ROLL = 6;
    public static final int DICE_MIN_ROLL = 0;
    private int lastRoll;
    private boolean playerHasRolledDice;

    public DiceManager(int numDice) {
        if (numDice < 1 || numDice > 2) {
            throw new IllegalArgumentException("The input must be between [1, 2]");
        }
        this.dice = new int[numDice];
        this.random = new Random();
    }

    public int rollAllDice() {
        return rollAllDice(random);
    }

    public int rollAllDice(Random random) {
        playerHasRolledDice = true;
        updateDice(random);
        int sum = getSumOfDice();
        lastRoll = sum;
        return sum;
    }

    private void updateDice(Random random) {
        for (int i = 0; i < dice.length; i++) {
            dice[i] = random.nextInt(DICE_MAX_ROLL) + 1;
        }
    }

    private int getSumOfDice(){
        int sum = 0;
        for(int roll: dice){
            sum += roll;
        }
        return sum;
    }


    public int getCurrentDiceRoll(Random random) {
        if (!playerHasRolledDice) {
            return rollAllDice(random);
        }
        return lastRoll;
    }



    public int getCurrentDiceRoll() {
        return getCurrentDiceRoll(random);
    }

    public int getDie(int i, Random random) {
        if (i < 1 || i > dice.length) {
            throw new IllegalArgumentException("The input must be between [1," + dice.length + "]");
        }
        rollDiceIfNotRolled(random);
        return dice[i-1];
    }

    private void rollDiceIfNotRolled(Random random) {
        if (!playerHasRolledDice) {
            rollAllDice(random);
        }
    }

    public int getDie(int i) {
        return getDie(i, random);
    }

    public void invalidateDice() {
        playerHasRolledDice = false;
    }

    public int getNumDice() {
        return dice.length;
    }

    public int[] getIndividualDiceRolls(){
        return dice.clone();
    }

    public boolean hasPlayerRolledDice() {
        return playerHasRolledDice;
    }
}

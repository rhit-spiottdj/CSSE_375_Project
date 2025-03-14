package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestDiceManager {

    @Test
    public void testDiceManagerZeroDice() {
        Exception exception = assertThrows(
            IllegalArgumentException.class, () -> new DiceManager(0));
        assertTrue(exception.getMessage().contains("The input must be between [1, 2]"));
    }

    @Test
    public void testDiceManagerNegativeDice() {
        Exception exception = assertThrows(
            IllegalArgumentException.class, () -> new DiceManager(-1));
        assertTrue(exception.getMessage().contains("The input must be between [1, 2]"));
    }

    @Test
    public void testDiceManagerOneDice() {
        DiceManager dm = new DiceManager(1);
        assertNotNull(dm);
    }

    @Test
    public void testDiceManagerMaxDice() {
        DiceManager dm = new DiceManager(2);
        assertNotNull(dm);
    }

    @Test
    public void testDiceManagerMaxDicePlusOne() {
        Exception exception = assertThrows(
            IllegalArgumentException.class, () -> new DiceManager(3));
        assertTrue(exception.getMessage().contains("The input must be between [1, 2]"));
    }

    @Test
    public void testRollAllDiceOne() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(0);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(1);

        int actual = dm.rollAllDice(random);

        EasyMock.verify(random);

        assertEquals(1, actual);
    }

    @Test
    public void testRollTwoDiceMax() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(5);
        EasyMock.expect(random.nextInt(6)).andReturn(5);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(2);

        int actual = dm.rollAllDice(random);

        EasyMock.verify(random);

        assertEquals(12, actual);
    }

    @Test
    public void testRollTwoDiceDifferent() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(2);
        EasyMock.expect(random.nextInt(6)).andReturn(4);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(2);

        int actual = dm.rollAllDice(random);

        EasyMock.verify(random);

        assertEquals(8, actual);
    }

    @Test
    public void testGetCurrentDiceRollOne() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(0);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(1);

        dm.rollAllDice(random);
        int actual = dm.getCurrentDiceRoll();

        EasyMock.verify(random);

        assertEquals(1, actual);
    }

    @Test
    public void testGetCurrentTwoDiceRoll() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(5);
        EasyMock.expect(random.nextInt(6)).andReturn(5);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(2);

        int actual = dm.getCurrentDiceRoll(random);

        EasyMock.verify(random);

        assertEquals(12, actual);
    }


    @Test
    public void testGetCurrentTwoDifferentDiceRoll() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(2);
        EasyMock.expect(random.nextInt(6)).andReturn(4);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(2);

        int actual = dm.getCurrentDiceRoll(random);

        EasyMock.verify(random);

        assertEquals(8, actual);
    }

    @Test
    public void testGetDieOne() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(0);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(1);

        dm.rollAllDice(random);
        int actual = dm.getDie(1);

        EasyMock.verify(random);

        assertEquals(1, actual);
    }

    @Test
    public void testGetDieZero() {
        Random random = EasyMock.mock(Random.class);

        EasyMock.replay(random);

        int numDice = 1;
        DiceManager dm = new DiceManager(numDice);

        Exception exception = assertThrows(
            IllegalArgumentException.class, () -> dm.getDie(0));
        EasyMock.verify(random);

        assertTrue(exception.getMessage().contains("The input must be between [1,"
            + numDice + "]"));
    }

    @Test
    public void testGetDieNegativeOne() {
        Random random = EasyMock.mock(Random.class);

        EasyMock.replay(random);

        int numDice = 1;
        DiceManager dm = new DiceManager(numDice);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            dm.getDie(-1));
        EasyMock.verify(random);

        assertTrue(exception.getMessage().contains("The input must be between [1,"
            + numDice + "]"));
    }

    @Test
    public void testGetDieOverNumDice() {
        Random random = EasyMock.mock(Random.class);

        EasyMock.replay(random);

        int numDice = 2;
        DiceManager dm = new DiceManager(numDice);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            dm.getDie(4));
        EasyMock.verify(random);

        assertTrue(exception.getMessage().contains("The input must be between [1,"
            + numDice + "]"));
    }

    @Test
    public void testGetDieTwo() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(2);
        EasyMock.expect(random.nextInt(6)).andReturn(5);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(2);

        int actual = dm.getDie(2, random);

        EasyMock.verify(random);

        assertEquals(6, actual);
    }

    @Test
    public void testGetDieMax() {
        Random random = EasyMock.mock(Random.class);
        int numDice = 2;
        for (int i = 0; i < numDice; i++) {
            EasyMock.expect(random.nextInt(6)).andReturn(i%6);
        }

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(numDice);

        int actual = dm.getDie(numDice, random);

        EasyMock.verify(random);

        assertEquals(2, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2})
    public void testGetNumDice(int numDice) {
        DiceManager manager = new DiceManager(numDice);
        assertEquals(numDice,manager.getNumDice());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6})
    public void testGetIndividualDiceRolls(int roll) {
        DiceManager manager = new DiceManager(2);

        for(int i = 0; i < 6; i++){
            int[] actual = new int[]{roll,i};
            manager.dice = actual;
            assertArrayEquals(actual,manager.getIndividualDiceRolls());
        }
    }

    @Test
    public void testHasPlayerRolledDice_haveNot(){
        DiceManager manager = new DiceManager(2);
        assertFalse(manager.hasPlayerRolledDice());
    }

    @Test
    public void testHasPlayerRolledDice_has(){
        DiceManager manager = new DiceManager(2);
        manager.rollAllDice();
        assertTrue(manager.hasPlayerRolledDice());
    }

    @Test
    public void testEntireClassStressTest() {
        Random random = EasyMock.mock(Random.class);
        int numDice = 2;

        EasyMock.expect(random.nextInt(6)).andReturn(5);
        EasyMock.expect(random.nextInt(6)).andReturn(4);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(numDice);

        int actual = dm.rollAllDice(random);

        EasyMock.verify(random);

        assertEquals(11, actual);
        assertEquals(11, dm.getCurrentDiceRoll());
        assertEquals(6, dm.getDie(1));
        assertEquals(5, dm.getDie(2));

        EasyMock.reset(random);
        dm.invalidateDice();

        EasyMock.expect(random.nextInt(6)).andReturn(4);
        EasyMock.expect(random.nextInt(6)).andReturn(3);

        EasyMock.replay(random);

        actual = dm.rollAllDice(random);

        EasyMock.verify(random);

        assertEquals(9, actual);
        assertEquals(9, dm.getCurrentDiceRoll());
        assertEquals(5, dm.getDie(1));
        assertEquals(4, dm.getDie(2));

        EasyMock.reset(random);
        dm.invalidateDice();

        EasyMock.expect(random.nextInt(6)).andReturn(3);
        EasyMock.expect(random.nextInt(6)).andReturn(2);

        EasyMock.replay(random);

        actual = dm.getCurrentDiceRoll(random);

        EasyMock.verify(random);

        assertEquals(7, actual);
        assertEquals(7, dm.getCurrentDiceRoll());
        assertEquals(4, dm.getDie(1));
        assertEquals(3, dm.getDie(2));

        EasyMock.reset(random);
        dm.invalidateDice();

        EasyMock.expect(random.nextInt(6)).andReturn(2);
        EasyMock.expect(random.nextInt(6)).andReturn(1);

        EasyMock.replay(random);

        actual = dm.getDie(2, random);

        EasyMock.verify(random);

        assertEquals(2, actual);
    }

    @Test
    public void testRollAllDice_oneDiceMin() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(0);

        EasyMock.replay(random);



        DiceManager dm = new DiceManager(1);

        dm.random = random;

        int actual = dm.rollAllDice();

        EasyMock.verify(random);

        assertEquals(1, actual);
    }

    @Test
    public void testRollAllDice_oneDiceMax() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(5);

        EasyMock.replay(random);



        DiceManager dm = new DiceManager(1);

        dm.random = random;

        int actual = dm.rollAllDice();

        EasyMock.verify(random);

        assertEquals(6, actual);
    }

    @Test
    public void testRollAllDice_twoDiceMin() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(0);
        EasyMock.expect(random.nextInt(6)).andReturn(0);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(2);

        dm.random = random;

        int actual = dm.rollAllDice();

        EasyMock.verify(random);

        assertEquals(2, actual);
    }

    @Test
    public void testRollAllDice_twoDiceMax() {
        Random random = EasyMock.mock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(5);
        EasyMock.expect(random.nextInt(6)).andReturn(5);

        EasyMock.replay(random);

        DiceManager dm = new DiceManager(2);

        dm.random = random;

        int actual = dm.rollAllDice();

        EasyMock.verify(random);

        assertEquals(12, actual);
    }
}

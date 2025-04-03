package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import static domain.ResourceType.*;
import static org.easymock.EasyMock.eq;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    GameManager manager;
    BoardManager boardManager;
    DevelopmentCardManager cardManager;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    Player player6;

    Player[] players;
    ArrayList<DevelopmentCards> cards;
    ArrayList<Structure> structures;

    @Test
    public void testGameManager_setNumPlayers1_expectException() {
        GameManager manager = new GameManager();
        String expectedMessage = "Minimum amount of players allowed is 2";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.setNumPlayers(1)).getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGameManager_setNumPlayers2_expect2() {
        GameManager manager = new GameManager();
        assertEquals(2, manager.setNumPlayers(2));
    }

    @Test
    public void testGameManager_setNumPlayers4_expect4() {
        GameManager manager = new GameManager();
        assertEquals(4, manager.setNumPlayers(4));
    }

    @Test
    public void testGameManager_setNumPlayers6_expect6() {
        GameManager manager = new GameManager();
        assertEquals(6, manager.setNumPlayers(6));
    }

    @Test
    public void testGameManager_setNumPlayers7_expectException() {
        GameManager manager = new GameManager();
        String expectedMessage = "Maximum amount of players allowed is 6";
        String actualMessage = assertThrows(IllegalArgumentException.class,
                () -> manager.setNumPlayers(7)).getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGameManager_setTurnOrderEmptyCollections_expectException() {
        GameManager manager = new GameManager();
        String expectedMessage = "Illegal Argument";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.setTurnOrder(new int[0])).getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGameManager_setTurnOrder2ndEmptyCollection_expectException() {
        GameManager manager = new GameManager();

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);

        manager.setNumPlayers(2);
        manager.players = new Player[]{player1, player2};

        String expectedMessage = "Illegal Argument";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.setTurnOrder(new int[0])).getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGameManager_setTurnOrder1stEmptyCollection_expectException() {
        GameManager manager = new GameManager();
        int[] rolledDices = new int[]{4, 6};

        String expectedMessage = "Illegal Argument";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.setTurnOrder(rolledDices)).getMessage();
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void testGameManager_setTurnOrderEqualCollections_expectCollection() {
        GameManager manager = new GameManager();
        int[] rolledDices = new int[]{6, 10, 4};

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);

        manager.setNumPlayers(3);
        manager.players = new Player[]{player1, player2, player3};

        Player[] sortedPlayers = manager.setTurnOrder(rolledDices);

        assertEquals(player1, sortedPlayers[1]);
        assertEquals(player2, sortedPlayers[0]);
        assertEquals(player3, sortedPlayers[2]);
    }

    @Test
    public void testGameManager_setTurnOrderEqualCollections_duplicateRollImpossibleCase() {
        GameManager manager = new GameManager();
        int[] rolledDices = new int[]{4,6,6,5};

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);

        manager.setNumPlayers(4);
        manager.players = new Player[]{player1, player2, player3, player4};

        Player[] sortedPlayers = manager.setTurnOrder(rolledDices);

        assertEquals(player1, sortedPlayers[3]);
        assertEquals(player2, sortedPlayers[0]);
        assertEquals(player3, sortedPlayers[1]);
        assertEquals(player4, sortedPlayers[2]);
    }

    @Test
    public void testGameManager_setTurnOrderEqualCollections_ascendingOrder() {
        GameManager manager = new GameManager();
        int[] rolledDices = new int[]{5,6,7};

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);

        manager.setNumPlayers(3);
        manager.players = new Player[]{player1, player2, player3};

        Player[] sortedPlayers = manager.setTurnOrder(rolledDices);

        assertEquals(player1, sortedPlayers[2]);
        assertEquals(player2, sortedPlayers[1]);
        assertEquals(player3, sortedPlayers[0]);
    }

    @Test
    public void testGameManager_setTurnOrderEqualCollections_descendingOrder() {
        GameManager manager = new GameManager();
        int[] rolledDices = new int[]{7,6,5};

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);

        manager.setNumPlayers(3);
        manager.players = new Player[]{player1, player2, player3};

        Player[] sortedPlayers = manager.setTurnOrder(rolledDices);

        assertEquals(player1, sortedPlayers[0]);
        assertEquals(player2, sortedPlayers[1]);
        assertEquals(player3, sortedPlayers[2]);
    }

    @Test
    public void testGameManager_setTurnOrderUnequalCollection1_expectException() {
        GameManager manager = new GameManager();
        int[] rolledDices = new int[]{6, 10, 4};

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);

        manager.setNumPlayers(4);
        manager.players = new Player[]{player1, player2, player3, player4};

        String expectedMessage = "Unequal Collection Sizes";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.setTurnOrder(rolledDices)).getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGameManager_setTurnOrderUnequalCollection2_expectException() {
        GameManager manager = new GameManager();
        int[] rolledDices = new int[]{6, 10, 4, 8};

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);

        manager.setNumPlayers(3);
        manager.players = new Player[]{player1, player2, player3};

        String expectedMessage = "Unequal Collection Sizes";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.setTurnOrder(rolledDices)).getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    public void testSetPlayer_negativeOneIndex(int numPlayers) {
        GameManager manager = new GameManager();
        manager.setNumPlayers(numPlayers);

        Player player = EasyMock.createMock(Player.class);

        boolean actual = manager.setPlayer(-1, player);

        assertFalse(actual);

    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    public void testSetPlayer_zeroIndex(int numPlayers) {
        GameManager manager = new GameManager();
        manager.setNumPlayers(numPlayers);

        Player player = EasyMock.createMock(Player.class);

        boolean actual = manager.setPlayer(0, player);

        assertTrue(actual);

        assertEquals(player, manager.players[0]);

    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    public void testSetPlayer_maxIndex(int numPlayers) {
        GameManager manager = new GameManager();
        manager.setNumPlayers(numPlayers);

        Player player = EasyMock.createMock(Player.class);

        boolean actual = manager.setPlayer(numPlayers - 1, player);

        assertTrue(actual);

        assertEquals(player, manager.players[numPlayers - 1]);

    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    public void testSetPlayer_overMaxIndex(int numPlayers) {
        GameManager manager = new GameManager();
        manager.setNumPlayers(numPlayers);

        Player player = EasyMock.createMock(Player.class);

        boolean actual = manager.setPlayer(numPlayers, player);

        assertFalse(actual);

    }

    @Test
    public void testGameManager_placeInitialSettlementInvalidIndex_expectException() {

        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        String expectedMessage = "Invalid Index";

        Player player1 = EasyMock.createMock(Player.class);

        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.placeInitialSettlement(-1, player1)).getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGameManager_placeInitialSettlement_expectErrorPlacingSettlementException() {

        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        String expectedMessage = "Error placing settlement, try again";

        Player player1 = EasyMock.createMock(Player.class);



        EasyMock.expect(boardManager.placeSettlementSetup(eq(0), eq(player1),
            EasyMock.anyObject(Settlement.class))).andReturn(false);

        EasyMock.replay(boardManager);

        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.placeInitialSettlement( 0, player1)).getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testGameManager_placeInitialSettlement_validIndex(int index) {

        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);


        try {
            EasyMock.expect(boardManager.placeSettlementSetup(eq(index), eq(player1),
                EasyMock.anyObject(Settlement.class))).andReturn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EasyMock.replay(boardManager);

        manager.placeInitialSettlement(index, player1);


        EasyMock.verify(boardManager);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 54})
    public void testGameManager_giveInitialResources_invalidIndex(int val) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        Player player = EasyMock.createMock(Player.class);
        GameManager manager = new GameManager(4,boardManager);

        String expectedMessage = "Invalid Index";

        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.giveInitialResources(val, player)).getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testGameManager_giveInitialResources_validIndex(int val) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        Player player = EasyMock.createMock(Player.class);

        player.addResource(BRICK);

        EasyMock.expect(boardManager.distributeInitialResources(val)).andReturn(new ArrayList<>(List.of(BRICK)));

        Bank bank = EasyMock.createMock(Bank.class);

        EasyMock.expect(bank.obtainResource(BRICK, 1)).andReturn(true);

        GameManager manager = new GameManager(new Player[]{player}, boardManager, bank);

        EasyMock.replay(boardManager, player, bank);

        manager.giveInitialResources(val, player);

        EasyMock.verify(boardManager, player, bank);
    }

    @Test
    public void testGameManager_giveInitialResources_validIndexEmptyBank() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(boardManager.distributeInitialResources(1)).andReturn(new ArrayList<>(List.of(BRICK)));

        Bank bank = EasyMock.createMock(Bank.class);

        EasyMock.expect(bank.obtainResource(BRICK, 1)).andReturn(false);

        GameManager manager = new GameManager(new Player[]{player}, boardManager, bank);

        EasyMock.replay(boardManager, player, bank);

        manager.giveInitialResources(1, player);

        EasyMock.verify(boardManager, player, bank);
    }


    @Test
    public void testGameManager_distributeResources_noResources() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(boardManager.distributeInitialResources(0)).andReturn(new ArrayList<>());

        Bank bank = EasyMock.createMock(Bank.class);

        EasyMock.replay(boardManager, player);

        GameManager manager = new GameManager(new Player[]{player}, boardManager, bank);

        String expectedMessage = "No resources to distribute";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.giveInitialResources(0, player)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManager, player);
    }

    @ParameterizedTest
    @CsvSource({"0, 1", "1, 0", "2, 3", "3, 2", "52, 53", "53, 52"})
    public void testGameManager_placeRoad_expectBoardManagerCall(int val1, int val2) {
        BoardManager boardManager = EasyMock.createNiceMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.expect(boardManager.placeRoad(val1, val2, player1, false)).andReturn(true);

        EasyMock.replay(boardManager);

        manager.placeRoad(val1, val2, player1, false);

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_placeRoad_expectException() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.replay(boardManager);

        String expectedMessage = "Cannot place road on the same intersection";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.placeRoad( 0, 0, player1, false)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_placeInitialSettlement_expectBoardManagerException() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        //test with any settlement object
        EasyMock.expect(boardManager.placeSettlementSetup(eq(0), eq(player1),
                EasyMock.anyObject(Settlement.class)))
            .andReturn(false);

        EasyMock.replay(boardManager, player1);

        String expectedMessage = "Error placing settlement, try again";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.placeInitialSettlement( 0, player1)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_placeRoad_expectBoardManagerException() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.expect(boardManager.placeRoad(0, 1, player1, false)).andReturn(false);

        EasyMock.replay(boardManager);

        String expectedMessage = "Error placing road, try again";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.placeRoad( 0, 1, player1,false)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_noPoints() {
        calculateVictoryPointsForPlayer_setupStandard();

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(0);

        calculateVictoryPointsForPlayer_replayStandard();

        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(0, actual);
        assertFalse(manager.isGameOver());
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void testGameManager_calculateVictoryPointsForPlayer_victoryPointCards(int numCards) {
        calculateVictoryPointsForPlayer_setupStandard();

        for (int i = 0; i < numCards; i++) {
            cards.add(DevelopmentCards.VICTORY);
        }

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(numCards);

        calculateVictoryPointsForPlayer_replayStandard();

        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(numCards, actual);
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void testGameManager_calculateVictoryPointsForPlayer_ownedSettlements(
        int numSettlements) {
        calculateVictoryPointsForPlayer_setupStandard();

        for (int i = 0; i < numSettlements; i++) {
            Structure settlement = EasyMock.createMock(Settlement.class);
            EasyMock.expect(settlement.getOwner()).andReturn(player1);
            EasyMock.expect(settlement.getVictoryPoints()).andReturn(1);
            structures.add(settlement);
        }

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(numSettlements);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(numSettlements, actual);
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    public void testGameManager_calculateVictoryPointsForPlayer_ownedCities(int numCities) {
        calculateVictoryPointsForPlayer_setupStandard();

        for (int i = 0; i < numCities; i++) {
            Structure city = EasyMock.createMock(City.class);
            EasyMock.expect(city.getOwner()).andReturn(player1);
            EasyMock.expect(city.getVictoryPoints()).andReturn(2);
            structures.add(city);
        }

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(numCities*2);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(numCities * 2, actual);
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_oneEachStructure() {
        calculateVictoryPointsForPlayer_setupStandard();


        Structure city = EasyMock.createMock(City.class);
        EasyMock.expect(city.getOwner()).andReturn(player1);
        EasyMock.expect(city.getVictoryPoints()).andReturn(2);
        structures.add(city);

        Structure settlement = EasyMock.createMock(Settlement.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player1);
        EasyMock.expect(settlement.getVictoryPoints()).andReturn(1);
        structures.add(settlement);

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(3);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(3, actual);
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_maxStructures() {
        calculateVictoryPointsForPlayer_setupStandard();

        for (int i = 0; i < 4; i++) {
            Structure city = EasyMock.createMock(City.class);
            EasyMock.expect(city.getOwner()).andReturn(player1);
            EasyMock.expect(city.getVictoryPoints()).andReturn(2);
            structures.add(city);
        }

        for (int i = 0; i < 5; i++) {
            Structure settlement = EasyMock.createMock(Settlement.class);
            EasyMock.expect(settlement.getOwner()).andReturn(player1);
            EasyMock.expect(settlement.getVictoryPoints()).andReturn(1);
            structures.add(settlement);
        }

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(13);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(13, actual);
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_oneOwnedStructureOneNot() {
        calculateVictoryPointsForPlayer_setupStandard();

        Player player2 = EasyMock.createMock(Player.class);

        Structure settlement = EasyMock.createMock(Settlement.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player1);
        EasyMock.expect(settlement.getVictoryPoints()).andReturn(1);
        structures.add(settlement);

        Structure settlement2 = EasyMock.createMock(Settlement.class);
        EasyMock.expect(settlement2.getOwner()).andReturn(player2);
        structures.add(settlement2);

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);

        calculateVictoryPointsForPlayer_expectStandard(1);

        calculateVictoryPointsForPlayer_replayStandard();
        EasyMock.replay(player2);
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(1, actual);
        calculateVictoryPointsForPlayer_verifyStandard();
        EasyMock.verify(player2);
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_ownLongestRoad() {
        calculateVictoryPointsForPlayer_setupStandard();

        calculateVictoryPointsForPlayer_expectLongestRoad(true);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(2);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(2, actual);
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_ownLargestArmy() {
        calculateVictoryPointsForPlayer_setupStandard();

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(true);
        calculateVictoryPointsForPlayer_expectStandard(2);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(2, actual);
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_nineVictoryPoints() {
        calculateVictoryPointsForPlayer_setupStandard();

        for (int i = 0; i < 4; i++) {
            cards.add(DevelopmentCards.VICTORY);
        }

        for (int i = 0; i < 5; i++) {
            Structure settlement = EasyMock.createMock(Settlement.class);
            EasyMock.expect(settlement.getOwner()).andReturn(player1);
            EasyMock.expect(settlement.getVictoryPoints()).andReturn(1);
            structures.add(settlement);
        }

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(9);

        EasyMock.expect(player1.getVictoryPoints()).andReturn(9);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(9, actual);
        assertEquals(9, player1.getVictoryPoints());
        assertFalse(manager.isGameOver());
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_tenVictoryPoints() {
        calculateVictoryPointsForPlayer_setupStandard();

        for (int i = 0; i < 5; i++) {
            cards.add(DevelopmentCards.VICTORY);
        }

        for (int i = 0; i < 5; i++) {
            Structure settlement = EasyMock.createMock(Settlement.class);
            EasyMock.expect(settlement.getOwner()).andReturn(player1);
            EasyMock.expect(settlement.getVictoryPoints()).andReturn(1);
            structures.add(settlement);
        }

        calculateVictoryPointsForPlayer_expectLongestRoad(false);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(10);

        EasyMock.expect(player1.getVictoryPoints()).andReturn(10);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(10, actual);
        assertEquals(10, player1.getVictoryPoints());
        assertTrue(manager.isGameOver());
        calculateVictoryPointsForPlayer_verifyStandard();
    }

    @Test
    public void testGameManager_calculateVictoryPointsForPlayer_elevenVictoryPoints() {
        calculateVictoryPointsForPlayer_setupStandard();

        for (int i = 0; i < 5; i++) {
            cards.add(DevelopmentCards.VICTORY);
        }

        for (int i = 0; i < 4; i++) {
            Structure settlement = EasyMock.createMock(Settlement.class);
            EasyMock.expect(settlement.getOwner()).andReturn(player1);
            EasyMock.expect(settlement.getVictoryPoints()).andReturn(1);
            structures.add(settlement);
        }



        calculateVictoryPointsForPlayer_expectLongestRoad(true);
        calculateVictoryPointsForPlayer_expectLargestArmy(false);
        calculateVictoryPointsForPlayer_expectStandard(11);

        EasyMock.expect(player1.getVictoryPoints()).andReturn(11);

        calculateVictoryPointsForPlayer_replayStandard();
        int actual = manager.calculateVictoryPointsForPlayer(player1);

        assertEquals(11, actual);
        assertEquals(11, player1.getVictoryPoints());
        assertTrue(manager.isGameOver());
        calculateVictoryPointsForPlayer_verifyStandard();
    }




    private void calculateVictoryPointsForPlayer_setupStandard() {
        boardManager = EasyMock.createMock(BoardManager.class);
        cardManager = EasyMock.createMock(DevelopmentCardManager.class);
        manager = new GameManager(2, boardManager, cardManager);
        player1 = EasyMock.createMock(Player.class);
        manager.inTurn = player1;
        cards = new ArrayList<>();
        structures = new ArrayList<>();
    }

    private void calculateVictoryPointsForPlayer_expectStandard(int victoryPoints) {
        EasyMock.expect(boardManager.getStructures()).andReturn(structures);
        EasyMock.expect(player1.getUnplayableDevelopmentCards()).andReturn(cards);
        player1.setVictoryPoints(victoryPoints);
    }

    private void calculateVictoryPointsForPlayer_verifyStandard() {
        EasyMock.verify(player1, boardManager, cardManager);
        for (Structure structure : structures) {
            EasyMock.verify(structure);
        }
    }

    private void calculateVictoryPointsForPlayer_replayStandard() {
        EasyMock.replay(player1, boardManager, cardManager);
        for (Structure structure : structures) {
            EasyMock.replay(structure);
        }
    }

    private void calculateVictoryPointsForPlayer_expectLongestRoad(boolean hasRoad) {
        if (hasRoad) {
            EasyMock.expect(cardManager.getLongestRoadOwner()).andReturn(player1);
        } else {
            EasyMock.expect(cardManager.getLongestRoadOwner()).andReturn(null);
        }
    }

    private void calculateVictoryPointsForPlayer_expectLargestArmy(boolean hasRoad) {
        if (hasRoad) {
            EasyMock.expect(cardManager.getLargestArmyOwner()).andReturn(player1);
        } else {
            EasyMock.expect(cardManager.getLargestArmyOwner()).andReturn(null);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 19})
    public void testGameManager_moveRobber_invalidIndex(int val) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        String expectedMessage = "Invalid Hex Index";

        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.moveRobber( val)).getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGameManager_moveRobber_sameIndexExpectException() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        EasyMock.expect(boardManager.getRobberLocation()).andReturn(0);

        EasyMock.replay(boardManager);

        String expectedMessage = "Robber is already on this hex";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.moveRobber(0)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManager);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 18})
    public void testGameManager_moveRobber_validIndex(int index) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        EasyMock.expect(boardManager.getRobberLocation()).andReturn(3);
        boardManager.moveRobber(index);

        EasyMock.replay(boardManager);

        manager.moveRobber( index);

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_ExpectNone() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player[] players = new Player[]{player1, player2, player3, player4};
        manager.players = players;


        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(new ArrayList<>());

        EasyMock.replay(boardManager);

        ArrayList<Player> actual = manager.getHexagonPlayers();

        assertEquals(0, actual.size());

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_ExpectOne() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player[] players = new Player[]{player1, player2, player3, player4};
        manager.players = players;


        EasyMock.expect(boardManager.getHexagonPlayers())
            .andReturn(new ArrayList<>(List.of(player1)));

        EasyMock.replay(boardManager);

        ArrayList<Player> actual = manager.getHexagonPlayers();

        assertEquals(1, actual.size());

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_ExpectTwo() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player[] players = new Player[]{player1, player2, player3, player4};
        manager.players = players;


        EasyMock.expect(boardManager.getHexagonPlayers())
            .andReturn(new ArrayList<>(List.of(player1, player2)));

        EasyMock.replay(boardManager);

        ArrayList<Player> actual = manager.getHexagonPlayers();

        assertEquals(2, actual.size());

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_ExpectThree() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player[] players = new Player[]{player1, player2, player3, player4};
        manager.players = players;


        EasyMock.expect(boardManager.getHexagonPlayers())
            .andReturn(new ArrayList<>(List.of(player1, player2, player3)));

        EasyMock.replay(boardManager);

        ArrayList<Player> actual = manager.getHexagonPlayers();

        assertEquals(3, actual.size());

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_ExpectFour() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player[] players = new Player[]{player1, player2, player3, player4};
        manager.players = players;


        EasyMock.expect(boardManager.getHexagonPlayers())
            .andReturn(new ArrayList<>(List.of(player1, player2, player3, player4)));

        EasyMock.replay(boardManager);

        ArrayList<Player> actual = manager.getHexagonPlayers();

        assertEquals(4, actual.size());

        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_ExpectFive() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(6,boardManager);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player player5 = EasyMock.createMock(Player.class);
        Player[] players = new Player[]{player1, player2, player3, player4, player5};
        manager.players = players;

        EasyMock.expect(boardManager.getHexagonPlayers())
                .andReturn(new ArrayList<>(List.of(player1, player2, player3, player4, player5)));
        EasyMock.replay(boardManager);
        ArrayList<Player> actual = manager.getHexagonPlayers();
        assertEquals(5, actual.size());
        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_ExpectSix() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(6,boardManager);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player player5 = EasyMock.createMock(Player.class);
        Player player6 = EasyMock.createMock(Player.class);
        Player[] players = new Player[]{player1, player2, player3, player4, player5, player6};
        manager.players = players;

        EasyMock.expect(boardManager.getHexagonPlayers())
                .andReturn(new ArrayList<>(List.of(player1, player2, player3, player4, player5, player6)));
        EasyMock.replay(boardManager);
        ArrayList<Player> actual = manager.getHexagonPlayers();
        assertEquals(6, actual.size());
        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_tryRobberSteal_expectException() {
        Player player1Mock = EasyMock.createMock(Player.class);
        Player player2Mock = EasyMock.createMock(Player.class);

        EasyMock.expect(player2Mock.getResources()).andReturn(new ArrayList<>());

        Player[] players = new Player[]{player1Mock, player2Mock};

        BoardManager boardManagerMock = EasyMock.createMock(BoardManager.class);

        EasyMock.replay(boardManagerMock, player1Mock, player2Mock);

        GameManager gameManager = new GameManager(players, boardManagerMock);

        String expectedMessage = "Player has no resources to steal";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> gameManager.tryRobberSteal(player1Mock,
                player2Mock)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManagerMock, player1Mock, player2Mock);
    }

    @Test
    public void testGameManager_tryRobberSteal_expectNoPlayerException() {
        Player player1Mock = EasyMock.createMock(Player.class);


        Player[] players = new Player[]{player1Mock};

        BoardManager boardManagerMock = EasyMock.createMock(BoardManager.class);

        EasyMock.replay(boardManagerMock, player1Mock);

        GameManager gameManager = new GameManager(players, boardManagerMock);

        String expectedMessage = "No player to steal from";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> gameManager.tryRobberSteal(player1Mock,
                null)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManagerMock, player1Mock);
    }

    @Test
    public void testGameManager_tryRobberSteal_expectResource() {
        Player player1Mock = EasyMock.createMock(Player.class);
        Player player2Mock = EasyMock.createMock(Player.class);

        ArrayList<ResourceType> resources = new ArrayList<>();
        resources.add(ResourceType.LUMBER);
        resources.add(BRICK);
        resources.add(ResourceType.WOOL);
        EasyMock.expect(player2Mock.getResources()).andReturn(resources);

        Player[] players = new Player[]{player1Mock, player2Mock};

        BoardManager boardManagerMock = EasyMock.createMock(BoardManager.class);

        EasyMock.expect(boardManagerMock.stealResource(player1Mock, player2Mock))
            .andReturn(ResourceType.LUMBER);

        EasyMock.replay(boardManagerMock, player1Mock, player2Mock);

        GameManager gameManager = new GameManager(players, boardManagerMock);

        ResourceType actual = gameManager.tryRobberSteal(player1Mock,
            player2Mock);

        assertEquals(ResourceType.LUMBER, actual);

        EasyMock.verify(boardManagerMock, player1Mock, player2Mock);
    }

    @Test
    public void testGameManager_getHexagonPlayers_expectEmpty() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(new ArrayList<>());
        EasyMock.replay(boardManager);
        ArrayList<Player> actual = manager.getHexagonPlayers();
        assertEquals(0, actual.size());
        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_expectOne() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(new ArrayList<>(List.of(player1)));
        EasyMock.replay(boardManager);
        ArrayList<Player> actual = manager.getHexagonPlayers();
        assertEquals(1, actual.size());
        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_expectTwo() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(new ArrayList<>(List.of(player1, player2)));
        EasyMock.replay(boardManager);
        ArrayList<Player> actual = manager.getHexagonPlayers();
        assertEquals(2, actual.size());
        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_expectThree() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(new ArrayList<>(List.of(player1, player2, player3)));
        EasyMock.replay(boardManager);
        ArrayList<Player> actual = manager.getHexagonPlayers();
        assertEquals(3, actual.size());
        EasyMock.verify(boardManager);
    }

    @Test
    public void testGameManager_getHexagonPlayers_expectFour() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(new ArrayList<>(List.of(player1, player2, player3, player4)));
        EasyMock.replay(boardManager);
        ArrayList<Player> actual = manager.getHexagonPlayers();
        assertEquals(4, actual.size());
        EasyMock.verify(boardManager);
    }



    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_setInTurnPlayer_negativeIndex(int numPlayers){
        setupPlayers(numPlayers);
        String actualMessage = assertThrows(IndexOutOfBoundsException.class,
            () -> manager.setInTurnPlayer(-1)).getMessage();
        String expectedMessage = "Outside player range";
        assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_setInTurnPlayer_zeroIndex(int numPlayers){
        setupPlayers(numPlayers);
        manager.setInTurnPlayer(0);
        assertEquals(player1, manager.inTurn);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_setInTurnPlayer_maxIndex(int numPlayers){
        setupPlayers(numPlayers);
        manager.setInTurnPlayer(numPlayers-1);
        assertEquals(players[numPlayers-1], manager.inTurn);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_setInTurnPlayer_overMaxIndex(int numPlayers){
        setupPlayers(numPlayers);
        String actualMessage = assertThrows(IndexOutOfBoundsException.class,
            () -> manager.setInTurnPlayer(numPlayers)).getMessage();
        String expectedMessage = "Outside player range";
        assertEquals(expectedMessage, actualMessage);
    }

    private void setupPlayers(int numPlayers){
        manager = new GameManager(numPlayers);
        player1 = EasyMock.createMock(Player.class);
        player2 = EasyMock.createMock(Player.class);
        player3 = EasyMock.createMock(Player.class);
        player4 = EasyMock.createMock(Player.class);
        player5 = EasyMock.createMock(Player.class);
        player6 = EasyMock.createMock(Player.class);

        players = new Player[]{player1,player2,player3,player4,player5,player6};
        for(int i = 0; i < numPlayers; i++){
            manager.setPlayer(i,players[i]);
        }
    }


    @Test
    public void testGameManager_buildSettlement_expectException() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.replay(boardManager);

        String expectedMessage = "Invalid Index";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.buildSettlement( -1, player1)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManager);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testGameManager_buildSettlement_insufficientResources(int index) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.expect(boardManager.buildSettlement(index, player1)).andReturn(false);

        EasyMock.replay(boardManager);

        assertFalse(manager.buildSettlement(index, player1));

        EasyMock.verify(boardManager);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testGameManager_buildSettlement_validIndexAndResources(int index) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player1 = EasyMock.createMock(Player.class);

        GameManager manager = new GameManager(new Player[]{player1}, boardManager, bank);

        EasyMock.expect(boardManager.buildSettlement(index, player1)).andReturn(true);

        for(ResourceType resource : Settlement.getCost()){
            EasyMock.expect(player1.removeResource(resource)).andReturn(true);
            EasyMock.expect(bank.giveBackResource(resource, 1)).andReturn(true);
        }
        EasyMock.replay(boardManager, player1, bank);

        assertTrue(manager.buildSettlement( index, player1));

        EasyMock.verify(boardManager, player1, bank);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testGameManager_buildRoad_sameIntersection(int index) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4, boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.replay(boardManager);

        String expectedMessage = "Cannot place road on the same intersection";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.buildRoad(index, index, player1)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManager);
    }

    @ParameterizedTest
    @CsvSource({"0, 1", "1, 0", "2, 3", "3, 2", "52, 53", "53, 52"})
    public void testGameManager_buildRoad_expectFalse(int val1, int val2) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4,boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.expect(boardManager.buildRoad(val1, val2, player1)).andReturn(false);

        EasyMock.replay(boardManager);

        manager.buildRoad(val1, val2, player1);

        EasyMock.verify(boardManager);
    }

    @ParameterizedTest
    @CsvSource({"0, 1", "1, 0", "2, 3", "3, 2", "52, 53", "53, 52"})
    public void testGameManager_buildRoad_expectTrue(int val1, int val2) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player1 = EasyMock.createMock(Player.class);

        GameManager manager = new GameManager(new Player[]{player1}, boardManager, bank);

        EasyMock.expect(boardManager.buildRoad(val1, val2, player1)).andReturn(true);

        EasyMock.expect(player1.removeResource(BRICK)).andReturn(true);
        EasyMock.expect(player1.removeResource(ResourceType.LUMBER)).andReturn(true);
        EasyMock.expect(bank.giveBackResource(BRICK, 1)).andReturn(true);
        EasyMock.expect(bank.giveBackResource(ResourceType.LUMBER, 1)).andReturn(true);

        EasyMock.replay(boardManager, player1, bank);

        manager.buildRoad(val1, val2, player1);

        EasyMock.verify(boardManager, player1, bank);
    }

    @Test
    public void testGameManager_buildCity_expectException() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4, boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.replay(boardManager);

        String expectedMessage = "Invalid Index";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.buildCity( -1, player1)).getMessage();

        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(boardManager);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testGameManager_buildCity_insufficientResources(int index) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        GameManager manager = new GameManager(4, boardManager);

        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.expect(boardManager.buildCity(index, player1)).andReturn(false);

        EasyMock.replay(boardManager);

        manager.buildCity( index, player1);

        EasyMock.verify(boardManager);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testGameManager_buildCity_validIndexAndResources(int index) {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player1 = EasyMock.createMock(Player.class);

        GameManager manager = new GameManager(new Player[]{player1}, boardManager, bank);

        EasyMock.expect(boardManager.buildCity(index, player1)).andReturn(true);

        for(ResourceType resource : City.getCost()){
            EasyMock.expect(player1.removeResource(resource)).andReturn(true);
            EasyMock.expect(bank.giveBackResource(resource, 1)).andReturn(true);
        }
        EasyMock.replay(boardManager, player1, bank);

        manager.buildCity(index, player1);

        EasyMock.verify(boardManager, player1, bank);
    }

    @Test
    public void testGameManager_distributeResourcesOnRoll_expectZero(){
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        DiceManager dm = EasyMock.createMock(DiceManager.class);

        EasyMock.expect(dm.getCurrentDiceRoll()).andReturn(EasyMock.anyInt());
        EasyMock.expect(bm.distributeResourcesOnRoll(EasyMock.anyInt(), bank)).andReturn(0);

        EasyMock.replay(bm, bank, dm);

        GameManager gm = new GameManager(new Player[]{}, bm, bank, dm);

        assertEquals(0, gm.distributeResourcesOnRoll(gm.getCurrentDiceRoll()));

        EasyMock.verify(bm, bank, dm);
    }

    @Test
    public void testGameManager_distributeResourcesOnRoll_expectOne(){
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        DiceManager dm = EasyMock.createMock(DiceManager.class);

        EasyMock.expect(dm.getCurrentDiceRoll()).andReturn(EasyMock.anyInt());
        EasyMock.expect(bm.distributeResourcesOnRoll(EasyMock.anyInt(), bank)).andReturn(1);

        EasyMock.replay(bm, bank, dm);

        GameManager gm = new GameManager(new Player[]{}, bm, bank, dm);

        assertEquals(1, gm.distributeResourcesOnRoll(gm.getCurrentDiceRoll()));

        EasyMock.verify(bm, bank, dm);
    }

    @Test
    public void testGameManager_distributeResourcesOnRoll_expectTwo(){
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        DiceManager dm = EasyMock.createMock(DiceManager.class);

        EasyMock.expect(dm.getCurrentDiceRoll()).andReturn(EasyMock.anyInt());
        EasyMock.expect(bm.distributeResourcesOnRoll(EasyMock.anyInt(), bank)).andReturn(2);

        EasyMock.replay(bm, bank, dm);

        GameManager gm = new GameManager(new Player[]{}, bm, bank, dm);

        assertEquals(2, gm.distributeResourcesOnRoll(gm.getCurrentDiceRoll()));

        EasyMock.verify(bm, bank, dm);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_setNextPlayerInTurn_setPlayerTwo(int numPlayers){
        setupPlayers(numPlayers);

        manager.inTurnIndex = 0;
        int index = manager.setNextPlayerInTurn();

        assertEquals(player2, manager.inTurn);
        assertEquals(1, index);
    }

    @ParameterizedTest
    @ValueSource(ints = {3,4})
    public void testGameManager_setNextPlayerInTurn_setLastPlayer(int numPlayers){
        setupPlayers(numPlayers);

        manager.inTurnIndex = numPlayers-2;
        int index = manager.setNextPlayerInTurn();

        assertEquals(players[numPlayers-1], manager.inTurn);
        assertEquals(numPlayers-1, index);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_setNextPlayerInTurn_setFirstPlayer(int numPlayers){
        setupPlayers(numPlayers);

        manager.inTurnIndex = numPlayers-1;
        int index = manager.setNextPlayerInTurn();

        assertEquals(players[0], manager.inTurn);
        assertEquals(0, index);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_setNextPlayerInTurn_firstCall(int numPlayers){
        setupPlayers(numPlayers);

        int index = manager.setNextPlayerInTurn();

        assertEquals(players[0], manager.inTurn);
        assertEquals(0, index);
    }

    @ParameterizedTest
    @EnumSource()
    public void testGameManager_isValidRatio_zero(PortTradeRatio ratio){
        assertTrue(GameManager.isValidRatio(ratio, 0));
    }

    @ParameterizedTest
    @EnumSource()
    public void testGameManager_isValidRatio_one(PortTradeRatio ratio){
        assertFalse(GameManager.isValidRatio(ratio, 1));
    }

    @ParameterizedTest
    @ValueSource(ints = {2,4,6})
    public void testGameManager_isValidRatio_twoRatios(int amount){
        assertTrue(GameManager.isValidRatio(PortTradeRatio.TWO_TO_ONE, amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {3,6})
    public void testGameManager_isValidRatio_threeRatios(int amount){
        assertTrue(GameManager.isValidRatio(PortTradeRatio.THREE_TO_ONE, amount));
    }

    @Test
    public void testGameManager_isValidRatio_maxResource(){
        assertFalse(GameManager.isValidRatio(PortTradeRatio.THREE_TO_ONE, 19));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 4, 6, 8})
    public void testGameManager_playerTradeWith2to1Port_expectValidInput(int val) {
        Player player = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < val; i++) {
            resources.add(ResourceType.LUMBER);
        }
        Bank bank = EasyMock.createMock(Bank.class);
        Port port = EasyMock.createMock(Port.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        EasyMock.expect(port.getPortTradeRatio()).andReturn(PortTradeRatio.TWO_TO_ONE).anyTimes();
        EasyMock.expect(bank.tradeResourcePort(port, ResourceType.LUMBER, BRICK,
        val / 2)).andReturn(true);
        EasyMock.replay(port, bank);
        GameManager manager = new GameManager(new Player[]{player}, bmanager, bank);
        manager.inTurn = player;
        for(int j = 0; j < val; j++) {
            resources.remove(ResourceType.LUMBER);
            EasyMock.expect(player.removeResource(ResourceType.LUMBER)).andReturn(true);

        }
        for(int k = 0; k < val/2; k++) {
            resources.add(BRICK);
            player.addResource(BRICK);
            EasyMock.expectLastCall();
        }
        EasyMock.expect(player.getResources()).andReturn(resources);
        EasyMock.replay(player);
        assertTrue(manager.playerTradeWithPort(port, ResourceType.LUMBER, BRICK, val));

        assertEquals(val/2, player.getResources().size());
        EasyMock.verify(player, bank, port);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 9, 19})
    public void testGameManager_playerTradeWith2to1Port_expectInvalidInput(int val) {
        Player player = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < val; i++) {
            resources.add(ResourceType.LUMBER);
        }
        Bank bank = EasyMock.createMock(Bank.class);
        Port port = EasyMock.createMock(Port.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        EasyMock.expect(port.getPortTradeRatio()).andReturn(PortTradeRatio.TWO_TO_ONE).anyTimes();

        EasyMock.replay(port, bank);
        GameManager manager = new GameManager(new Player[]{player}, bmanager, bank);
        manager.inTurn = player;
        EasyMock.expect(player.getResources()).andReturn(resources);
        EasyMock.replay(player);
        assertFalse(manager.playerTradeWithPort(port, ResourceType.LUMBER, BRICK, val));

        assertEquals(val, player.getResources().size());
        EasyMock.verify(player, bank, port);
    }
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 7})
    public void testGameManager_getNumberOfCardsToDiscard_lessThanSeven(int numCards){
        Player player = EasyMock.createMock(Player.class);

        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < numCards; i++){
            resources.add(BRICK);
        }

        EasyMock.expect(player.getResources()).andReturn(resources);

        EasyMock.replay(player);

        BoardManager boardManager = EasyMock.createMock(BoardManager.class);

        GameManager manager = new GameManager(new Player[]{player}, boardManager);

        assertEquals(0, manager.getNumberCardsToDiscard(player));

        EasyMock.verify(player);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 6, 9})
    public void testGameManager_playerTradeWith3to1Port_expectValidInput(int val) {
        Player player = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < val; i++) {
            resources.add(ResourceType.LUMBER);
        }
        Bank bank = EasyMock.createMock(Bank.class);
        Port port = EasyMock.createMock(Port.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        EasyMock.expect(port.getPortTradeRatio()).andReturn(PortTradeRatio.THREE_TO_ONE).anyTimes();
        EasyMock.expect(bank.tradeResourcePort(port, ResourceType.LUMBER, BRICK,
                val / 3)).andReturn(true);
        EasyMock.replay(port, bank);
        GameManager manager = new GameManager(new Player[]{player}, bmanager, bank);
        manager.inTurn = player;
        for(int j = 0; j < val; j++) {
            resources.remove(ResourceType.LUMBER);
            EasyMock.expect(player.removeResource(ResourceType.LUMBER)).andReturn(true);

        }
        for(int k = 0; k < val/3; k++) {
            resources.add(BRICK);
            player.addResource(BRICK);
            EasyMock.expectLastCall();
        }
        EasyMock.expect(player.getResources()).andReturn(resources);
        EasyMock.replay(player);
        assertTrue(manager.playerTradeWithPort(port, ResourceType.LUMBER, BRICK, val));

        assertEquals(val/3, player.getResources().size());
        EasyMock.verify(player, bank, port);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8, 19})
    public void testGameManager_playerTradeWith3to1Port_expectInvalidInput(int val) {
        Player player = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < val; i++) {
            resources.add(ResourceType.LUMBER);
        }
        Bank bank = EasyMock.createMock(Bank.class);
        Port port = EasyMock.createMock(Port.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        EasyMock.expect(port.getPortTradeRatio()).andReturn(PortTradeRatio.THREE_TO_ONE).anyTimes();

        EasyMock.replay(port, bank);
        GameManager manager = new GameManager(new Player[]{player}, bmanager, bank);
        manager.inTurn = player;

        EasyMock.expect(player.getResources()).andReturn(resources);
        EasyMock.replay(player);
        assertFalse(manager.playerTradeWithPort(port, ResourceType.LUMBER, BRICK, val));

        assertEquals(val, player.getResources().size());
        EasyMock.verify(player, bank, port);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 4, 6, 8})
    public void testGameManager_playerTradeWith2to1Port_expectBankNoTrade(int val) {
        Player player = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < val; i++) {
            resources.add(ResourceType.LUMBER);
        }
        Bank bank = EasyMock.createMock(Bank.class);
        Port port = EasyMock.createMock(Port.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        EasyMock.expect(port.getPortTradeRatio()).andReturn(PortTradeRatio.TWO_TO_ONE).anyTimes();
        EasyMock.expect(bank.tradeResourcePort(port, ResourceType.LUMBER, BRICK,
                val / 2)).andReturn(false);
        EasyMock.replay(port, bank);
        GameManager manager = new GameManager(new Player[]{player}, bmanager, bank);
        manager.inTurn = player;

        EasyMock.expect(player.getResources()).andReturn(resources);
        EasyMock.replay(player);
        assertFalse(manager.playerTradeWithPort(port, ResourceType.LUMBER, BRICK, val));

        assertEquals(val, player.getResources().size());
        EasyMock.verify(player, bank, port);
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 95})
    public void testGameManager_getNumberOfCardsToDiscard_moreThanSeven(int numCards){
        Player player = EasyMock.createMock(Player.class);

        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < numCards; i++){
            resources.add(BRICK);
        }

        EasyMock.expect(player.getResources()).andReturn(resources);
        EasyMock.expect(player.getResources()).andReturn(resources);

        EasyMock.replay(player);

        BoardManager boardManager = EasyMock.createMock(BoardManager.class);

        GameManager manager = new GameManager(new Player[]{player}, boardManager);

        assertEquals(numCards / 2, manager.getNumberCardsToDiscard(player));

        EasyMock.verify(player);

    }

    @Test
    public void testGameManager_playRoadBuilding_expectFalse(){
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        int[][] intersections = new int[][]{{0, 1}, {1, 2}};
        EasyMock.expect(dcm.playRoadBuilding(player, intersections)).andReturn(false);


        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.setInTurnPlayer(0);

        assertFalse(gm.playRoadBuildingCard(intersections));

        EasyMock.verify(bm, bank, player, dcm);
    }

    @Test
    public void testGameManager_playRoadBuilding_expectTrue(){
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        int[][] intersections = new int[][]{{0, 1}, {1, 2}};
        EasyMock.expect(dcm.playRoadBuilding(player, intersections)).andReturn(true);
        EasyMock.expect(bm.getRoadsOnBoard()).andReturn(new ArrayList<>());

        EasyMock.expect(dcm.findLongestRoad(EasyMock.anyObject(), EasyMock.anyObject())).andReturn(true);

        EasyMock.expect(player.getUnplayableDevelopmentCards()).andReturn(new ArrayList<>());

        EasyMock.expect(bm.getStructures()).andReturn(new ArrayList<>());

        EasyMock.expect(dcm.getLongestRoadOwner()).andReturn(null);
        EasyMock.expect(dcm.getLargestArmyOwner()).andReturn(null);
        player.setVictoryPoints(0);

        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.setInTurnPlayer(0);

        assertTrue(gm.playRoadBuildingCard(intersections));

        EasyMock.verify(bm, bank, player, dcm);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testGameManager_playYearOfPlenty_expectTrue(ResourceType res) {
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        Collection<ResourceType> resources = new ArrayList<>();
        resources.add(res);
        resources.add(res);
        EasyMock.expect(dcm.playYearOfPlenty(player,res, res)).andReturn(resources);

        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.setInTurnPlayer(0);

        assertEquals(gm.playYearOfPlenty(res, res),resources);

        EasyMock.verify(bm, bank, player, dcm);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testGameManager_playYearOfPlenty_expectFalse(ResourceType res) {
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        EasyMock.expect(dcm.playYearOfPlenty(player,res, res)).andReturn(new ArrayList<>());

        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.setInTurnPlayer(0);

        assertEquals(gm.playYearOfPlenty(res, res), new ArrayList<>());

        EasyMock.verify(bm, bank, player, dcm);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testGameManager_playMonopoly_expectTrue(ResourceType res) {
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        Collection<ResourceType> resources = new ArrayList<>();
        resources.add(res);
        resources.add(res);
        EasyMock.expect(dcm.playMonopoly(player, res)).andReturn(resources);

        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.setInTurnPlayer(0);

        assertEquals(gm.playMonopolyCard(res),resources);

        EasyMock.verify(bm, bank, player, dcm);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testGameManager_playMonopoly_expectFalse(ResourceType res) {
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        EasyMock.expect(dcm.playMonopoly(player, res)).andReturn(new ArrayList<>());

        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.setInTurnPlayer(0);

        assertEquals(gm.playMonopolyCard(res), new ArrayList<>());

        EasyMock.verify(bm, bank, player, dcm);
    }

    @Test
    public void testGameManager_testDecrementResources_expectNoChange() {
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        Collection<ResourceType> resources = new ArrayList<>();

        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.decrementResourcesFromBank(resources);

        EasyMock.verify(bm, bank, player, dcm);
    }

    @Test
    public void testGameManager_testDecrementResourcesFromBank_expect1DecrementedFromEach() {
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        Collection<ResourceType> resources = new ArrayList<>();
        resources.add(BRICK);
        resources.add(ResourceType.LUMBER);
        resources.add(ORE);
        resources.add(ResourceType.GRAIN);
        resources.add(ResourceType.WOOL);

        EasyMock.expect(bank.obtainResource(BRICK, 1)).andReturn(true);
        EasyMock.expect(bank.obtainResource(ResourceType.LUMBER, 1)).andReturn(true);
        EasyMock.expect(bank.obtainResource(ORE, 1)).andReturn(true);
        EasyMock.expect(bank.obtainResource(ResourceType.GRAIN, 1)).andReturn(true);
        EasyMock.expect(bank.obtainResource(ResourceType.WOOL, 1)).andReturn(true);

        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.decrementResourcesFromBank(resources);

        EasyMock.verify(bm, bank, player, dcm);
    }

    @Test
    public void testGameManager_testDecrementResourcesFromBank_expect19DecrementedFromEach() {
        BoardManager bm = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        Player player = EasyMock.createMock(Player.class);
        DevelopmentCardManager dcm = EasyMock.createMock(DevelopmentCardManager.class);

        Collection<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < 19; i++){
            resources.add(BRICK);
            resources.add(ResourceType.LUMBER);
            resources.add(ORE);
            resources.add(ResourceType.GRAIN);
            resources.add(ResourceType.WOOL);
        }

        for(int i = 0; i < 19; i++){
            EasyMock.expect(bank.obtainResource(BRICK, 1)).andReturn(true);
            EasyMock.expect(bank.obtainResource(ResourceType.LUMBER, 1)).andReturn(true);
            EasyMock.expect(bank.obtainResource(ORE, 1)).andReturn(true);
            EasyMock.expect(bank.obtainResource(ResourceType.GRAIN, 1)).andReturn(true);
            EasyMock.expect(bank.obtainResource(ResourceType.WOOL, 1)).andReturn(true);
        }

        EasyMock.replay(bm, bank, player, dcm);

        GameManager gm = new GameManager(dcm, new Player[]{player}, bm, bank);

        gm.decrementResourcesFromBank(resources);

        EasyMock.verify(bm, bank, player, dcm);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_getNumPlayers(int numPlayers){
        GameManager manager = new GameManager(numPlayers);

        assertEquals(numPlayers,manager.getNumPlayers());
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6})
    public void testGameManager_getPlayers(int numPlayers){
        GameManager manager = new GameManager(numPlayers);

        assertEquals(numPlayers,manager.getPlayers().length);
    }

    @Test
    public void testGameManager_isInTurnPlayer_different(){
        manager = new GameManager(2);
        player1 = EasyMock.createMock(Player.class);
        player2 = EasyMock.createMock(Player.class);
        manager.setPlayer(0, player1);
        manager.setPlayer(1, player2);
        manager.inTurn = player1;

        assertFalse(manager.isInTurnPlayer(player2));
    }

    @Test
    public void testGameManager_isInTurnPlayer_same(){
        manager = new GameManager(2);
        player1 = EasyMock.createMock(Player.class);
        player2 = EasyMock.createMock(Player.class);
        manager.setPlayer(0, player1);
        manager.setPlayer(1, player2);
        manager.inTurn = player1;

        assertTrue(manager.isInTurnPlayer(player1));
    }

    //Extra Integration Test
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,14})
    public void testGameManager_getDevelopmentCardsInBank_differentAmounts(int amount){
        manager = new GameManager(2);

        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            cards.add(DevelopmentCards.KNIGHT);
        }

        manager.bank.developmentCards = cards;

        Collection<DevelopmentCards> returnCards = manager.getDevelopmentCardsInBank();

        for(int i = 0; i < amount; i++){
            assertTrue(returnCards.remove(DevelopmentCards.KNIGHT));
        }
        assertTrue(returnCards.isEmpty());
    }

    @Test
    public void testGameManager_getDevelopmentCardsInBank_differentType(){
        manager = new GameManager(2);

        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(DevelopmentCards.MONOPOLY);

        manager.bank.developmentCards = cards;

        Collection<DevelopmentCards> returnCards = manager.getDevelopmentCardsInBank();

        assertTrue(returnCards.remove(DevelopmentCards.MONOPOLY));
        assertTrue(returnCards.isEmpty());
    }

    //Extra Integration Test
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,14})
    public void testGameManager_getPlayableDevelopmentCards_differentAmounts(int amount){
        manager = new GameManager(2);
        player1 = new Player(Color.RED, "todd", new ArrayList<>());
        manager.setPlayer(0, player1);
        manager.setNextPlayerInTurn();


        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            cards.add(DevelopmentCards.KNIGHT);
        }

        player1.developmentCards = cards;

        Collection<DevelopmentCards> returnCards = manager.getPlayableDevelopmentCards();

        for(int i = 0; i < amount; i++){
            assertTrue(returnCards.remove(DevelopmentCards.KNIGHT));
        }
        assertTrue(returnCards.isEmpty());
    }

    //Extra Integration Test
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,14})
    public void testGameManager_getUnplayableDevelopmentCards_differentAmounts(int amount){
        manager = new GameManager(2);
        player1 = new Player(Color.RED, "todd", new ArrayList<>());
        manager.setPlayer(0, player1);
        manager.setNextPlayerInTurn();


        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            cards.add(DevelopmentCards.KNIGHT);
        }

        player1.unplayableDevelopmentCards = cards;

        Collection<DevelopmentCards> returnCards = manager.getUnplayableDevelopmentCards();

        for(int i = 0; i < amount; i++){
            assertTrue(returnCards.remove(DevelopmentCards.KNIGHT));
        }
        assertTrue(returnCards.isEmpty());
    }

    //Extra Integration Test
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,14})
    public void testGameManager_getFutureDevelopmentCards_differentAmounts(int amount){
        manager = new GameManager(2);
        player1 = new Player(Color.RED, "todd", new ArrayList<>());
        manager.setPlayer(0, player1);
        manager.setNextPlayerInTurn();


        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            cards.add(DevelopmentCards.KNIGHT);
        }

        player1.futureDevelopmentCards = cards;

        Collection<DevelopmentCards> returnCards = manager.getFutureDevelopmentCards();

        for(int i = 0; i < amount; i++){
            assertTrue(returnCards.remove(DevelopmentCards.KNIGHT));
        }
        assertTrue(returnCards.isEmpty());
    }

    @Test
    public void testGameManager_getPlayableDevelopmentCards_differentType(){
        manager = new GameManager(2);
        player1 = new Player(Color.RED, "todd", new ArrayList<>());
        manager.setPlayer(0, player1);
        manager.setNextPlayerInTurn();


        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(DevelopmentCards.MONOPOLY);

        player1.developmentCards = cards;

        Collection<DevelopmentCards> returnCards = manager.getPlayableDevelopmentCards();

        assertTrue(returnCards.remove(DevelopmentCards.MONOPOLY));
        assertTrue(returnCards.isEmpty());
    }

    @Test
    public void testGameManager_getFutureDevelopmentCards_differentType(){
        manager = new GameManager(2);
        player1 = new Player(Color.RED, "todd", new ArrayList<>());
        manager.setPlayer(0, player1);
        manager.setNextPlayerInTurn();


        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(DevelopmentCards.MONOPOLY);

        player1.futureDevelopmentCards = cards;

        Collection<DevelopmentCards> returnCards = manager.getFutureDevelopmentCards();

        assertTrue(returnCards.remove(DevelopmentCards.MONOPOLY));
        assertTrue(returnCards.isEmpty());
    }

    @Test
    public void testGameManager_getUnplayableDevelopmentCards_differentType(){
        manager = new GameManager(2);
        player1 = new Player(Color.RED, "todd", new ArrayList<>());
        manager.setPlayer(0, player1);
        manager.setNextPlayerInTurn();


        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(DevelopmentCards.MONOPOLY);

        player1.unplayableDevelopmentCards = cards;

        Collection<DevelopmentCards> returnCards = manager.getUnplayableDevelopmentCards();

        assertTrue(returnCards.remove(DevelopmentCards.MONOPOLY));
        assertTrue(returnCards.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints={0, 4, 8, 12, 16})
    public void playerTradeWithBank_expectTrueValidInputs(int numResources) {
        Player player = EasyMock.createMock(Player.class);
        Bank bank = EasyMock.createMock(Bank.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < numResources; i++) {
            resources.add(LUMBER);
        }
        EasyMock.expect(bank.tradeResourceBank(LUMBER, BRICK, numResources / 4)).andReturn(true);
        EasyMock.replay(bank);
        GameManager manager = new GameManager(new Player[] {player}, bmanager, bank);
        manager.inTurn = player;
        for(int j = 0; j < numResources; j++) {
            resources.remove(LUMBER);
            EasyMock.expect(player.removeResource(LUMBER)).andReturn(true);
        }

        for(int k = 0; k < numResources / 4; k++) {
            resources.add(BRICK);
            player.addResource(BRICK);
            EasyMock.expectLastCall();
        }
        EasyMock.expect(player.getResources()).andReturn((resources));
        EasyMock.replay(player);
        assertTrue(manager.playerTradeWithBank(LUMBER, BRICK, numResources));
        assertEquals(numResources/4, player.getResources().size());
        EasyMock.verify(bank, player);
    }

    @ParameterizedTest
    @ValueSource(ints={1, 2, 3, 5, 19})
    public void playerTradeBank_expectFalseInvalidInputs(int numResources) {
        Player player = EasyMock.createMock(Player.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i < numResources; i++) {
            resources.add(LUMBER);
        }
        EasyMock.expect(bank.tradeResourceBank(LUMBER, BRICK, numResources / 4)).andReturn(false);
        EasyMock.replay(bank);
        GameManager manager = new GameManager(new Player[] {player}, bmanager, bank);
        manager.inTurn = player;
        EasyMock.expect(player.getResources()).andReturn(resources);
        EasyMock.replay(player);
        assertFalse(manager.playerTradeWithBank(LUMBER, BRICK, numResources));
        assertEquals(numResources, player.getResources().size());
        EasyMock.verify(bank, player);
    }

    @ParameterizedTest
    @ValueSource(ints={0, 1, 18})
    public void testPlayKnight_validIndexPlayerResources_expectFalse(int index) {
        Player player = EasyMock.createMock(Player.class);
        Player playerToStealFrom = EasyMock.createMock(Player.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        DevelopmentCardManager devmanager = EasyMock.createMock(DevelopmentCardManager.class);
        GameManager manager = new GameManager(devmanager, new Player[] {player, playerToStealFrom}, bmanager, bank);
        manager.inTurn = player;
        EasyMock.expect(devmanager.playKnight(player, playerToStealFrom, index)).andReturn(LUMBER);
        EasyMock.replay(player, playerToStealFrom, devmanager, bmanager);
        assertFalse(manager.playKnight(playerToStealFrom, index));
        EasyMock.verify(player, playerToStealFrom, devmanager, bmanager);
    }

    @ParameterizedTest
    @ValueSource(ints={0, 1, 18})
    public void testPlayKnight_validIndexPlayerNoResources_expectTrue(int index) {
        Player player = EasyMock.createMock(Player.class);
        Player playerToStealFrom = EasyMock.createMock(Player.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        DevelopmentCardManager devmanager = EasyMock.createMock(DevelopmentCardManager.class);
        GameManager manager = new GameManager(devmanager, new Player[] {player, playerToStealFrom}, bmanager, bank);
        manager.inTurn = player;
        EasyMock.expect(devmanager.playKnight(player, playerToStealFrom, index)).andReturn(null);
        EasyMock.replay(player, playerToStealFrom, devmanager, bmanager);
        assertTrue(manager.playKnight(playerToStealFrom, index));
        EasyMock.verify(player, playerToStealFrom, devmanager, bmanager);
    }

    @ParameterizedTest
    @ValueSource(ints={-1, 19})
    public void testPlayKnight_InvalidIndex_expectTrue(int index) {
        Player player = EasyMock.createMock(Player.class);
        Player playerToStealFrom = EasyMock.createMock(Player.class);
        BoardManager bmanager = EasyMock.createMock(BoardManager.class);
        Bank bank = EasyMock.createMock(Bank.class);
        DevelopmentCardManager devmanager = EasyMock.createMock(DevelopmentCardManager.class);
        GameManager manager = new GameManager(devmanager, new Player[] {player, playerToStealFrom}, bmanager, bank);
        manager.inTurn = player;
        EasyMock.expect(devmanager.playKnight(player, playerToStealFrom, index)).andReturn(null);
        EasyMock.replay(player, playerToStealFrom, devmanager, bmanager);
        assertTrue(manager.playKnight(playerToStealFrom, index));
        EasyMock.verify(player, playerToStealFrom, devmanager, bmanager);
    }

    @ParameterizedTest
    @EnumSource
    public void testDecrementResourcesFromBank_eachType(ResourceType type){
        GameManager manager = new GameManager(2);
        Bank bank = EasyMock.createMock(Bank.class);
        manager.bank = bank;
        EasyMock.expect(bank.obtainResource(type,1)).andReturn(true);

        EasyMock.replay(bank);

        Collection<ResourceType> resources = new ArrayList<>();
        resources.add(type);
        manager.decrementResourcesFromBank(resources);

        EasyMock.verify(bank);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,2,19})
    public void testDecrementResourcesFromBank_differentAmount(int numOfResource){
        GameManager manager = new GameManager(2);
        Bank bank = EasyMock.createMock(Bank.class);
        manager.bank = bank;

        Collection<ResourceType> resources = new ArrayList<>();
        for(int i = 0; i <numOfResource; i++){
            EasyMock.expect(bank.obtainResource(GRAIN,1)).andReturn(true);
            resources.add(GRAIN);
        }


        EasyMock.replay(bank);


        manager.decrementResourcesFromBank(resources);

        EasyMock.verify(bank);
    }

    @Test
    public void testDecrementResourcesFromBank_differentType(){
        GameManager manager = new GameManager(2);
        Bank bank = EasyMock.createMock(Bank.class);
        manager.bank = bank;

        Collection<ResourceType> resources = new ArrayList<>();
        EasyMock.expect(bank.obtainResource(GRAIN,1)).andReturn(true);
        EasyMock.expect(bank.obtainResource(ORE,1)).andReturn(true);
        resources.add(GRAIN);
        resources.add(ORE);


        EasyMock.replay(bank);


        manager.decrementResourcesFromBank(resources);

        EasyMock.verify(bank);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4,5,6,7,8,9,10,11,12})
    public void testGetCurrentDiceRoll(int roll){
        GameManager manager = new GameManager(2);
        DiceManager dice = EasyMock.createMock(DiceManager.class);
        manager.diceManager = dice;
        EasyMock.expect(dice.getCurrentDiceRoll()).andReturn(roll);

        EasyMock.replay(dice);
        assertEquals(roll, manager.getCurrentDiceRoll());

        EasyMock.verify(dice);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,18})
    public void testGetRobberLocation(int location){
        GameManager manager = new GameManager(2);
        BoardManager board = EasyMock.createMock(BoardManager.class);
        manager.boardManager = board;
        EasyMock.expect(board.getRobberLocation()).andReturn(location);

        EasyMock.replay(board);
        assertEquals(location, manager.getRobberLocation());

        EasyMock.verify(board);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    public void testPlayerDiscardResources(boolean success){
        GameManager manager = new GameManager(2);
        Bank bank = EasyMock.createMock(Bank.class);
        manager.bank = bank;
        Player player = EasyMock.createMock(Player.class);
        Collection<ResourceType> resources = new ArrayList<>();
        EasyMock.expect(bank.playerDiscardResources(player,resources)).andReturn(success);

        EasyMock.replay(bank);
        assertEquals(success, manager.playerDiscardResources(player, resources));

        EasyMock.verify(bank);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    public void testCurrentPlayerHasSufficientResources(boolean success){
        GameManager manager = new GameManager(2);
        Player player = EasyMock.createMock(Player.class);
        manager.inTurn = player;
        Collection<ResourceType> resources = new ArrayList<>();
        EasyMock.expect(player.hasResources(resources)).andReturn(success);

        EasyMock.replay(player);
        assertEquals(success, manager.currentPlayerHasSufficientResources(resources));

        EasyMock.verify(player);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    public void testPlayerTrade(boolean success){
        GameManager manager = new GameManager(2);
        Player player = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Bank bank = EasyMock.createMock(Bank.class);
        manager.bank = bank;
        manager.inTurn = player;
        Collection<ResourceType> resources = new ArrayList<>();
        EasyMock.expect(bank.playerTrade(player, player2, resources, resources)).andReturn(success);

        EasyMock.replay(bank);
        assertEquals(success, manager.playerTrade(player2,resources,resources));

        EasyMock.verify(bank);
    }
}

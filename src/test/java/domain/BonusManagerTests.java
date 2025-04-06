package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class BonusManagerTests {

    Bank bank;
    Player player;
    Player player2;
    Player player3;
    Player player4;

    Player[] players;

    BonusManager bonusManager;

    BoardManager boardManager;

    @BeforeEach
    public void setUp(){
        bank = EasyMock.createMock(Bank.class);
        player = EasyMock.createMock(Player.class);
        player2 = EasyMock.createMock(Player.class);
        boardManager = EasyMock.createMock(BoardManager.class);
        players = new Player[]{player,player2};

        bonusManager = new BonusManager();
    }

    private Road[] setupRoads(Player owner, int numberOfRoads, Intersection startingPoint) {
        Road[] roads = new Road[numberOfRoads];
        Intersection a;
        if (startingPoint == null) {
            a = new Intersection(null, 0);
        }
        else {
            a = startingPoint;
        }
        Intersection b = null;
        for (int i = 0; i < numberOfRoads; i++) {
            a.setOwner(owner);
            roads[i] = new Road();
            roads[i].setOwner(owner);
            a.setRoads(roads[i]);
            b = new Intersection(null, i + 1);
            roads[i].setIntersections(a, b);
            a = b;
        }
        if (b != null) {
            b.setOwner(owner);
        }
        return roads;
    }

//### Test Value 1
//Input: 2 players, |5| roads, no settlements
//Output: false

    @Test
    public void testLongestRoadTwoFiveContinuousRoads() {
        player = EasyMock.createMock(Player.class);
        Road[] roads1 = setupRoads(player, 5, null);
        Player[] players = new Player[2];
        player2 = EasyMock.createMock(Player.class);
        Road[] roads2 = setupRoads(player2, 5, null);
        Road[] roads = new Road[10];
        System.arraycopy(roads1, 0, roads, 0, 5);
        System.arraycopy(roads2, 0, roads, 5, 5);
        players[0] = player;
        players[1] = player2;

        boolean actual = bonusManager.findLongestRoad(players, roads);

        assertFalse(actual);
        assertNull(bonusManager.getLongestRoadOwner());
    }

//### Test Value 2
//Input: 2 players, |5| road, no settlements
//Output: true, Player1

    @Test
    public void testLongestRoadFiveContinuousRoads() {
        player = EasyMock.createMock(Player.class);
        Road[] roads = setupRoads(player, 5, null);
        Player[] players = new Player[2];
        player2 = EasyMock.createMock(Player.class);
        players[0] = player;
        players[1] = player2;

        boolean actual = bonusManager.findLongestRoad(players, roads);

        assertTrue(actual);
        assertEquals(player, bonusManager.getLongestRoadOwner());
    }

    @Test
    public void testLongestRoadFiveContinuousRoadsTwice() {
        player = EasyMock.createMock(Player.class);
        Road[] roads = setupRoads(player, 5, null);
        Road[] roads2 = setupRoads(player, 5, null);
        Player[] players = new Player[2];
        player2 = EasyMock.createMock(Player.class);
        players[0] = player;
        players[1] = player2;

        Road[] roads3 = new Road[10];
        System.arraycopy(roads, 0, roads3, 0, 5);
        System.arraycopy(roads2, 0, roads3, 5, 5);

        boolean actual = bonusManager.findLongestRoad(players, roads3);

        assertTrue(actual);
        assertEquals(player, bonusManager.getLongestRoadOwner());
    }

//### Test Value 3
//Input: 2 players, |5| road, friend settlement
//Output: true, Player1

    @Test
    public void testLongestRoadFiveContinuousRoadsFriendSettlement() {
        player = EasyMock.createMock(Player.class);
        Road[] roads = setupRoads(player, 5, null);
        Settlement settlement = new Settlement();
        settlement.setOwner(player);
        roads[2].getIntersections()[1].setStructure(settlement);
        Player[] players = new Player[2];
        player2 = EasyMock.createMock(Player.class);
        players[0] = player;
        players[1] = player2;

        boolean actual = bonusManager.findLongestRoad(players, roads);

        assertTrue(actual);
        assertEquals(player, bonusManager.getLongestRoadOwner());
    }

//### Test Value 4
//Input: 2 players, |5| road, enemy settlement
//Output: false

    @Test
    public void testLongestRoadFiveContinuousRoadsEnemySettlement() {
        player = EasyMock.createMock(Player.class);
        Road[] roads = setupRoads(player, 5, null);
        Player[] players = new Player[2];
        player2 = EasyMock.createMock(Player.class);
        Settlement settlement = new Settlement();
        settlement.setOwner(player2);
        roads[2].getIntersections()[1].setStructure(settlement);
        players[0] = player;
        players[1] = player2;

        boolean actual = bonusManager.findLongestRoad(players, roads);

        assertFalse(actual);
        assertNull(bonusManager.getLongestRoadOwner());
    }

//### Test Value 5
//Input: 4 players, three roads |5|, one |6|, no settlements
//Output: true, Player4

    @Test
    public void testLongestRoadFiveContinuousRoadsOneSix() {
        player = EasyMock.createMock(Player.class);
        Road[] roads1 = setupRoads(player, 5, null);
        player2 = EasyMock.createMock(Player.class);
        Road[] roads2 = setupRoads(player2, 5, null);
        player3 = EasyMock.createMock(Player.class);
        Road[] roads3 = setupRoads(player3, 6, null);
        player4 = EasyMock.createMock(Player.class);
        Road[] roads4 = setupRoads(player4, 5, null);
        Player[] players = new Player[4];
        players[0] = player;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;

        Road[] roads = new Road[21];
        System.arraycopy(roads1, 0, roads, 0, 5);
        System.arraycopy(roads2, 0, roads, 5, 5);
        System.arraycopy(roads3, 0, roads, 10, 6);
        System.arraycopy(roads4, 0, roads, 16, 5);

        boolean actual = bonusManager.findLongestRoad(players, roads);

        assertTrue(actual);
        assertEquals(player3, bonusManager.getLongestRoadOwner());
    }

//### Test Value 6
//Input: 2 players, one road splits into two |4|, no settlements
//Output: false

    @Test
    public void testLongestRoadFourContinuousSplitRoads() {
        player = EasyMock.createMock(Player.class);
        Road[] roads1 = setupRoads(player, 4, null);
        Intersection a = roads1[3].getIntersections()[0];
        Road[] roads2 = setupRoads(player, 1, a);
        Player[] players = new Player[2];
        player2 = EasyMock.createMock(Player.class);
        players[0] = player;
        players[1] = player2;

        Road[] roads = new Road[5];
        System.arraycopy(roads1, 0, roads, 0, 4);
        System.arraycopy(roads2, 0, roads, 4, 1);

        boolean actual = bonusManager.findLongestRoad(players, roads);

        assertFalse(actual);
        assertNull(bonusManager.getLongestRoadOwner());
    }

//### Test Value 7
//Input: 2 players, one road splits into two |5|, no settlements
//Output: true, player1

    @Test
    public void testLongestRoadFiveContinuousSplitRoads() {
        player = EasyMock.createMock(Player.class);
        Road[] roads1 = setupRoads(player, 5, null);
        Intersection a = roads1[4].getIntersections()[0];
        Road[] roads2 = setupRoads(player, 1, a);
        Player[] players = new Player[2];
        player2 = EasyMock.createMock(Player.class);
        players[0] = player;
        players[1] = player2;

        Road[] roads = new Road[6];
        System.arraycopy(roads1, 0, roads, 0, 5);
        System.arraycopy(roads2, 0, roads, 5, 1);

        boolean actual = bonusManager.findLongestRoad(players, roads);

        assertTrue(actual);
        assertEquals(player, bonusManager.getLongestRoadOwner());
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1})
    public void testBonusManager_getAndSetLargestArmyOwner(int playerIndex){

        bonusManager.setLargestArmyOwner(players[playerIndex]);

        assertEquals(players[playerIndex], bonusManager.largestArmyOwner);
        assertEquals(players[playerIndex], bonusManager.getLargestArmyOwner());
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1})
    public void testBonusManager_getAndSetLongestRoadOwner(int playerIndex){

        bonusManager.setLongestRoadOwner(players[playerIndex]);

        assertEquals(players[playerIndex], bonusManager.longestRoadOwner);
        assertEquals(players[playerIndex], bonusManager.getLongestRoadOwner());
    }
}

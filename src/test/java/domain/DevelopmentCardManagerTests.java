package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardManagerTests {

    Bank bank;
    Player player;
    Player player2;
    Player player3;
    Player player4;

    Player[] players;

    DevelopmentCardManager manager;

    BoardManager boardManager;

    @BeforeEach
    public void setUp(){
        bank = EasyMock.createMock(Bank.class);
        player = EasyMock.createMock(Player.class);
        player2 = EasyMock.createMock(Player.class);
        boardManager = EasyMock.createMock(BoardManager.class);
        players = new Player[]{player,player2};

        manager = new DevelopmentCardManager(players, bank, boardManager);
    }

    @ParameterizedTest
    @MethodSource("returnTwoResources")
    public void testPlayYearOfPlenty_emptyResourcesAndSufficientBank_addTwoResource(
        ResourceType resourceOne, ResourceType resourceTwo){

        ArrayList<ResourceType> playerResources = new ArrayList<>();
        playerResources.add(resourceOne);
        playerResources.add(resourceTwo);

        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(resourceOne, 1))).andReturn(false);
        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(resourceTwo, 1))).andReturn(false);

        player.addResource(resourceOne);
        player.addResource(resourceTwo);
        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(bank,player);

        Collection<ResourceType> resources = manager.playYearOfPlenty(player,
            resourceOne, resourceTwo);

        assertTrue(resources.remove(resourceOne));
        assertTrue(resources.remove(resourceTwo));
        assertTrue(resources.isEmpty());

        EasyMock.verify(bank,player);
    }

    @Test
    public void testPlayYearOfPlenty_oneResourceAndSufficientBank(){

        ResourceType lumber = ResourceType.LUMBER;

        ArrayList<ResourceType> playerResources = new ArrayList<>();
        for(int i = 0; i< 3; i++){
            playerResources.add(lumber);
        }

        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(lumber, 1))).andReturn(false);

        player.addResource(lumber);
        player.addResource(lumber);
        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(bank,player);

        Collection<ResourceType> resources = manager.playYearOfPlenty(player, lumber, lumber);

        assertTrue(resources.remove(lumber));
        assertTrue(resources.remove(lumber));
        assertTrue(resources.remove(lumber));
        assertTrue(resources.isEmpty());

        EasyMock.verify(bank,player);
    }

    @Test
    public void testPlayYearOfPlenty_twoResourceAndSufficientBank(){
//
//        ResourceType lumber = ResourceType.LUMBER;
//
//        ArrayList<ResourceType> playerResources = new ArrayList<>();
//        for(int i = 0; i< 4; i++){
//            playerResources.add(lumber);
//        }
//
//        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(lumber, 2))).andReturn(false);
//        player.addResource(lumber);
//        player.addResource(lumber);
//        EasyMock.expect(player.getResources()).andReturn(playerResources);
//
//        EasyMock.replay(bank,player);
//
//        Collection<ResourceType> resources = manager.playYearOfPlenty(player, lumber, lumber);
//
//        assertTrue(resources.remove(lumber));
//        assertTrue(resources.remove(lumber));
//        assertTrue(resources.remove(lumber));
//        assertTrue(resources.remove(lumber));
//        assertTrue(resources.isEmpty());
//
//        EasyMock.verify(bank,player);
    }

    @Test
    public void testPlayYearOfPlenty_maxResourceAndSufficientBank(){

        ResourceType lumber = ResourceType.LUMBER;

        ArrayList<ResourceType> playerResources = new ArrayList<>();
        for(int i = 0; i < 19; i++){
            playerResources.addAll(Arrays.asList(ResourceType.values()));
        }

        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(lumber, 1))).andReturn(false);

        player.addResource(lumber);
        player.addResource(lumber);
        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(bank,player);

        Collection<ResourceType> resources = manager.playYearOfPlenty(player, lumber, lumber);

        for(int i = 0; i < 19; i++){
            for (ResourceType resource1 : ResourceType.values()) {
                assertTrue(resources.remove(resource1));
            }
        }
        assertTrue(resources.isEmpty());

        EasyMock.verify(bank,player);
    }

    @Test
    public void testPlayYearOfPlenty_insufficientBank_sameResource(){

        ResourceType lumber = ResourceType.LUMBER;

        ArrayList<ResourceType> playerResources = new ArrayList<>();

        EasyMock.expect(bank.noMoreResource(EasyMock.eq(new ResourceTransaction(lumber, 1)))).andReturn(true);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(bank,player);

        Collection<ResourceType> resources = manager.playYearOfPlenty(player, lumber, lumber);

        assertTrue(resources.isEmpty());

        EasyMock.verify(bank,player);
    }

    @Test
    public void testPlayYearOfPlenty_insufficientBank_differentResource(){

        ResourceType lumber = ResourceType.LUMBER;
        ResourceType wool = ResourceType.WOOL;

        ArrayList<ResourceType> playerResources = new ArrayList<>();

        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(lumber, 1))).andReturn(false);
        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(wool, 1))).andReturn(true);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(bank,player);

        Collection<ResourceType> resources = manager.playYearOfPlenty(player, lumber, wool);

        assertTrue(resources.isEmpty());

        EasyMock.verify(bank,player);
    }

    @Test
    public void testPlayMonopoly_allEmpty(){
        ResourceType resource = ResourceType.LUMBER;

        ArrayList<ResourceType> playerResources = new ArrayList<>();

        EasyMock.expect(player2.removeResource(resource)).andReturn(false);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2);

        Collection<ResourceType> resources = manager.playMonopoly(player,resource);

        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2);
    }

    @ParameterizedTest
    @EnumSource
    public void testPlayMonopoly_oneResource(ResourceType resource){

        ArrayList<ResourceType> playerResources = new ArrayList<>();

        playerResources.add(resource);

        EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        player.addResource(resource);
        EasyMock.expect(player2.removeResource(resource)).andReturn(false);


        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2);

        Collection<ResourceType> resources = manager.playMonopoly(player,resource);

        assertTrue(resources.remove(resource));
        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2);
    }

    @ParameterizedTest
    @EnumSource
    public void testPlayMonopoly_twoResource(ResourceType resource){

        ArrayList<ResourceType> playerResources = new ArrayList<>();

        for(int i = 0; i < 2;i++){
            playerResources.add(resource);
            EasyMock.expect(player2.removeResource(resource)).andReturn(true);
            player.addResource(resource);
        }

        EasyMock.expect(player2.removeResource(resource)).andReturn(false);


        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2);

        Collection<ResourceType> resources = manager.playMonopoly(player,resource);

        assertTrue(resources.remove(resource));
        assertTrue(resources.remove(resource));
        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2);
    }

    @ParameterizedTest
    @EnumSource
    public void testPlayMonopoly_playerOneResourceOthersEmpty(ResourceType resource){

        ArrayList<ResourceType> playerResources = new ArrayList<>();

        playerResources.add(resource);

        EasyMock.expect(player2.removeResource(resource)).andReturn(false);


        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2);

        Collection<ResourceType> resources = manager.playMonopoly(player,resource);

        assertTrue(resources.remove(resource));
        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2);
    }

    @Test
    public void testPlayMonopoly_playerMaxResourceElseEmpty(){
        ResourceType resource = ResourceType.LUMBER;
        ArrayList<ResourceType> playerResources = new ArrayList<>();

        for(int i = 0; i < 19; i++){
            playerResources.addAll(Arrays.asList(ResourceType.values()));
        }

        EasyMock.expect(player2.removeResource(resource)).andReturn(false);


        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2);

        Collection<ResourceType> resources = manager.playMonopoly(player,resource);

        for(int i = 0; i < 19; i++){
            for (ResourceType resource1 : ResourceType.values()) {
                assertTrue(resources.remove(resource1));
            }
        }
        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2);
    }

    @Test
    public void testPlayMonopoly_playerEmptyOtherMax(){
        ResourceType resource = ResourceType.LUMBER;
        ArrayList<ResourceType> playerResources = new ArrayList<>();

        for(int i = 0; i < 19;i++){
            playerResources.add(resource);
            EasyMock.expect(player2.removeResource(resource)).andReturn(true);
            player.addResource(resource);
        }

        EasyMock.expect(player2.removeResource(resource)).andReturn(false);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2);

        Collection<ResourceType> resources = manager.playMonopoly(player,resource);

        for(int i = 0; i < 19; i++){
            assertTrue(resources.remove(resource));
        }
        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2);
    }

    @Test
    public void testPlayMonopoly_threePlayers(){
        ResourceType resource = ResourceType.LUMBER;
        ArrayList<ResourceType> playerResources = new ArrayList<>();

        playerResources.add(resource);
        playerResources.add(resource);

        Player player3 = EasyMock.createMock(Player.class);
        players = new Player[]{player,player2,player3};
        manager = new DevelopmentCardManager(players, bank, boardManager);

        EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        EasyMock.expect(player3.removeResource(resource)).andReturn(true);
        player.addResource(resource);
        player.addResource(resource);


        EasyMock.expect(player2.removeResource(resource)).andReturn(false);
        EasyMock.expect(player3.removeResource(resource)).andReturn(false);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2,player3);

        Collection<ResourceType> resources = manager.playMonopoly(player,resource);

        for(int i = 0; i < 2; i++){
            assertTrue(resources.remove(resource));
        }
        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2,player3);
    }

    @Test
    public void testPlayMonopoly_fourPlayers(){
        ResourceType resource = ResourceType.LUMBER;
        ArrayList<ResourceType> playerResources = new ArrayList<>();

        playerResources.add(resource);
        playerResources.add(resource);
        playerResources.add(resource);

        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        players = new Player[]{player,player2,player3,player4};
        manager = new DevelopmentCardManager(players, bank, boardManager);

        EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        EasyMock.expect(player3.removeResource(resource)).andReturn(true);
        EasyMock.expect(player4.removeResource(resource)).andReturn(true);
        player.addResource(resource);
        player.addResource(resource);
        player.addResource(resource);


        EasyMock.expect(player2.removeResource(resource)).andReturn(false);
        EasyMock.expect(player3.removeResource(resource)).andReturn(false);
        EasyMock.expect(player4.removeResource(resource)).andReturn(false);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2,player3,player4);

        Collection<ResourceType> resources = manager.playMonopoly(player,resource);

        for(int i = 0; i < 3; i++){
            assertTrue(resources.remove(resource));
        }
        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2,player3,player4);
    }

    @Test
    public void testPlayMonopoly_fourPlayersOneHasDesiredResource(){
        ResourceType lumber = ResourceType.LUMBER;
        ArrayList<ResourceType> playerResources = new ArrayList<>();

        playerResources.add(lumber);

        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        players = new Player[]{player,player2,player3,player4};
        manager = new DevelopmentCardManager(players, bank, boardManager);

        EasyMock.expect(player2.removeResource(lumber)).andReturn(false);
        EasyMock.expect(player3.removeResource(lumber)).andReturn(false);
        EasyMock.expect(player4.removeResource(lumber)).andReturn(true);

        player.addResource(lumber);

        EasyMock.expect(player4.removeResource(lumber)).andReturn(false);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2,player3,player4);

        Collection<ResourceType> resources = manager.playMonopoly(player,lumber);

        assertTrue(resources.remove(lumber));

        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2,player3,player4);
    }

    @Test
    public void testPlayRoadBuilding_twoValidIntersections(){
        int[][] intersections = new int[][]{{0,1},{1,53}};

        EasyMock.expect(boardManager.placeRoad(intersections[0][0],intersections[0][1],player,
            false)).andReturn(true);
        EasyMock.expect(boardManager.placeRoad(intersections[1][0],intersections[1][1],player,
            false)).andReturn(true);
        EasyMock.expect(player.getNumRoads()).andReturn(2).anyTimes();
        player.setNumRoads(0);

        EasyMock.replay(boardManager);
        EasyMock.replay(player);

        boolean actual = manager.playRoadBuilding(player,intersections);

        EasyMock.verify(boardManager);
        EasyMock.verify(player);

        assertTrue(actual);

    }


    @Test
    public void testPlayRoadBuilding_oneValidIntersection(){
        int[][] intersections = new int[][]{{0,1},{53,7}};

        EasyMock.expect(boardManager.placeRoad(intersections[0][0],intersections[0][1],player,
            false)).andReturn(true);
        EasyMock.expect(boardManager.placeRoad(intersections[1][0],intersections[1][1],player,
            false)).andReturn(false);
        EasyMock.expect(boardManager.removeRoad(intersections[0][0],intersections[0][1])).andReturn(true);

        EasyMock.replay(boardManager);
        EasyMock.replay(player);

        boolean actual = manager.playRoadBuilding(player,intersections);

        EasyMock.verify(boardManager);
        EasyMock.verify(player);

        assertFalse(actual);

    }

    @Test
    public void testPlayRoadBuilding_noValidIntersection(){
        int[][] intersections = new int[][]{{1,9},{53,7}};

        EasyMock.expect(boardManager.placeRoad(intersections[0][0],intersections[0][1],player,
            false)).andReturn(false);

        EasyMock.replay(boardManager);
        EasyMock.replay(player);

        boolean actual = manager.playRoadBuilding(player,intersections);

        EasyMock.verify(boardManager);
        EasyMock.verify(player);

        assertFalse(actual);

    }

    @Test
    public void testPlayRoadBuilding_identicalIntersection(){
        int[][] intersections = new int[][]{{1,9},{1,9}};

        boolean actual = manager.playRoadBuilding(player,intersections);

        assertFalse(actual);

    }

    @Test
    public void testPlayRoadBuilding_tooManyIntersection(){
        int[][] intersections = new int[][]{{0,1},{1,53},{3,4}};

        String expectedMessage = "Intersections need to be passed in 2x2 array";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playRoadBuilding(player,intersections)).getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void testPlayRoadBuilding_notEnoughIntersection(){
        int[][] intersections = new int[][]{{0,1}};

        String expectedMessage = "Intersections need to be passed in 2x2 array";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playRoadBuilding(player,intersections)).getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void testPlayRoadBuilding_insufficientIntersectionPerPair(){
        int[][] intersections = new int[][]{{0},{1}};

        String expectedMessage = "Intersections need to be passed in 2x2 array";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playRoadBuilding(player,intersections)).getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void testPlayRoadBuilding_tooManyIntersectionPerPair(){
        int[][] intersections = new int[][]{{0,1,3},{1,53,4}};

        String expectedMessage = "Intersections need to be passed in 2x2 array";
        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playRoadBuilding(player,intersections)).getMessage();

        assertEquals(expectedMessage, actualMessage);

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

        boolean actual = manager.findLongestRoad(players, roads);

        assertFalse(actual);
        assertNull(manager.getLongestRoadOwner());
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

        boolean actual = manager.findLongestRoad(players, roads);

        assertTrue(actual);
        assertEquals(player, manager.getLongestRoadOwner());
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

        boolean actual = manager.findLongestRoad(players, roads3);

        assertTrue(actual);
        assertEquals(player, manager.getLongestRoadOwner());
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

        boolean actual = manager.findLongestRoad(players, roads);

        assertTrue(actual);
        assertEquals(player, manager.getLongestRoadOwner());
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

        boolean actual = manager.findLongestRoad(players, roads);

        assertFalse(actual);
        assertNull(manager.getLongestRoadOwner());
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

        boolean actual = manager.findLongestRoad(players, roads);

        assertTrue(actual);
        assertEquals(player3, manager.getLongestRoadOwner());
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

        boolean actual = manager.findLongestRoad(players, roads);

        assertFalse(actual);
        assertNull(manager.getLongestRoadOwner());
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

        boolean actual = manager.findLongestRoad(players, roads);

        assertTrue(actual);
        assertEquals(player, manager.getLongestRoadOwner());
    }

    @Test
    public void testPlayKnight_playerHasKnight_simpleValidPlay() {

        playKnight_expectStandardErrorChecks();

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);
        playKnight_expectNoPlayedKnights();

        EasyMock.replay(player,player2,boardManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(ResourceType.GRAIN, stolen);

        EasyMock.verify(player,boardManager);
        assertNull(manager.largestArmyOwner);
    }

    @Test
    public void testPlayKnight_playerHasKnight_robberAlreadyOnHex() {
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);

        EasyMock.replay(player,player2,boardManager);

        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playKnight(player,player2,18)).getMessage();

        String expectedMessage = "Robber is already on this hex";
        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(player,boardManager);
        assertNull(manager.largestArmyOwner);
    }

    @Test
    public void testPlayKnight_playerHasKnight_playerNotOnHex() {
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        ArrayList<Player> onHexagon = new ArrayList<>(List.of(new Player[]{}));
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(onHexagon);
        boardManager.moveRobber(18);

        EasyMock.replay(player,player2,boardManager);

        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playKnight(player,player2,0)).getMessage();


        String expectedMessage = "Player is not on the hex";
        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(player,boardManager);
        assertNull(manager.largestArmyOwner);
    }


    @ParameterizedTest
    @ValueSource(ints = {-1,19})
    public void testPlayKnight_playerHasKnight_invalidHexIndex(int invalidIndices) {
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);

        EasyMock.replay(player,player2,boardManager);

        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playKnight(player,player2,invalidIndices)).getMessage();

        String expectedMessage = "Invalid hex index";
        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(player,boardManager);
        assertNull(manager.largestArmyOwner);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,18})
    public void testPlayKnight_playerHasKnight_alternativeSimpleValidPlay(int hexIndex) {

        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(0);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(0);
        ArrayList<Player> onHexagon = new ArrayList<>(List.of(new Player[]{player2}));
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(onHexagon);

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(hexIndex);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);
        playKnight_expectNoPlayedKnights();

        EasyMock.replay(player,player2,boardManager);

        ResourceType stolen = manager.playKnight(player,player2,hexIndex);
        assertEquals(ResourceType.GRAIN, stolen);

        EasyMock.verify(player,boardManager);
        assertNull(manager.largestArmyOwner);
    }

    @Test
    public void testPlayKnight_playerHasKnight_firstLargestArmy() {

        playKnight_expectStandardErrorChecks();
        DevelopmentCards[] cardArray = new DevelopmentCards[]{DevelopmentCards.KNIGHT,
            DevelopmentCards.KNIGHT};
        ArrayList<DevelopmentCards> cards = new ArrayList<>(List.of(cardArray));
        EasyMock.expect(player.getUnplayableDevelopmentCards()).andReturn(cards);

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);

        EasyMock.replay(player,player2,boardManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(ResourceType.GRAIN, stolen);

        EasyMock.verify(player,player2, boardManager);
        assertEquals(manager.largestArmyOwner,player);
    }

    @Test
    public void testPlayKnight_playerHasKnight_tiedLargestArmy() {

        playKnight_expectStandardErrorChecks();
        manager.largestArmyOwner = player2;
        DevelopmentCards[] cardArray =
            new DevelopmentCards[]{DevelopmentCards.KNIGHT, DevelopmentCards.KNIGHT};
        ArrayList<DevelopmentCards> cards = new ArrayList<>(List.of(cardArray));
        EasyMock.expect(player.getUnplayableDevelopmentCards()).andReturn(cards);

        cardArray = new DevelopmentCards[]{DevelopmentCards.KNIGHT, DevelopmentCards.KNIGHT,
            DevelopmentCards.KNIGHT};
        cards = new ArrayList<>(List.of(cardArray));
        EasyMock.expect(player2.getUnplayableDevelopmentCards()).andReturn(cards);

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);

        EasyMock.replay(player,player2,boardManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(ResourceType.GRAIN, stolen);

        EasyMock.verify(player,player2, boardManager);
        assertEquals(manager.largestArmyOwner,player2);
    }

    @Test
    public void testPlayKnight_playerHasKnight_stealLargestArmy() {

        playKnight_expectStandardErrorChecks();
        manager.largestArmyOwner = player2;
        DevelopmentCards[] cardArray = new DevelopmentCards[]{DevelopmentCards.KNIGHT,
            DevelopmentCards.KNIGHT, DevelopmentCards.KNIGHT};
        ArrayList<DevelopmentCards> cards = new ArrayList<>(List.of(cardArray));
        EasyMock.expect(player.getUnplayableDevelopmentCards()).andReturn(cards);
        EasyMock.expect(player2.getUnplayableDevelopmentCards()).andReturn(cards);

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);

        EasyMock.replay(player,player2,boardManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(ResourceType.GRAIN, stolen);
        EasyMock.verify(player,player2, boardManager);
        assertEquals(manager.largestArmyOwner,player);
    }

    @Test
    public void testPlayKnight_playerHasKnight_noResourcesToSteal() {

        playKnight_expectStandardErrorChecks();

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(null);
        playKnight_expectNoPlayedKnights();

        EasyMock.replay(player,player2,boardManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(null, stolen);

        EasyMock.verify(player,boardManager);
        assertNull(manager.largestArmyOwner);
    }

    private void playKnight_expectStandardErrorChecks(){
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        ArrayList<Player> onHexagon = new ArrayList<>(List.of(new Player[]{player2}));
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(onHexagon);

    }

    private void playKnight_expectNoPlayedKnights(){
        for(Player player: players){
            EasyMock.expect(player.getUnplayableDevelopmentCards()).andReturn(new ArrayList<>());
        }
    }

    @Test
    public void testPlayKnight_playerDoesNotHaveKnight() {
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(false);

        EasyMock.replay(player);

        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playKnight(player,player2,0)).getMessage();

        String expectedMessage = "Player does not have a knight card";
        assertEquals(expectedMessage, actualMessage);
        EasyMock.verify(player);

    }

    @Test
    public void testPlayKnight_playerHasKnight_noPlayerOnHexAndNullPassed() {

        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        ArrayList<Player> onHexagon = new ArrayList<>(List.of(new Player[]{}));
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(onHexagon);

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        playKnight_expectNoPlayedKnights();

        EasyMock.replay(player,player2,boardManager);

        ResourceType stolen = manager.playKnight(player,null,0);
        assertEquals(null, stolen);

        EasyMock.verify(player,boardManager);
        assertNull(manager.largestArmyOwner);
    }

    @Test
    public void testPlayKnight_playerHasKnight_playerOnHexAndNullPassed() {

        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        ArrayList<Player> onHexagon = new ArrayList<>(List.of(new Player[]{player2}));
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(onHexagon);

        boardManager.moveRobber(0);

        EasyMock.replay(player,player2,boardManager);


        String actualMessage = assertThrows(IllegalArgumentException.class,
            () -> manager.playKnight(player,null,0)).getMessage();

        String expectedMessage = "Hex has players and none specified";
        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(player,boardManager);
        assertNull(manager.largestArmyOwner);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1})
    public void testDevelopmentCardManager_getAndSetLargestArmyOwner(int playerIndex){

        manager.setLargestArmyOwner(players[playerIndex]);


        assertEquals(players[playerIndex], manager.largestArmyOwner);
        assertEquals(players[playerIndex], manager.getLargestArmyOwner());
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1})
    public void testDevelopmentCardManager_getAndSetLongestRoadOwner(int playerIndex){

        manager.setLongestRoadOwner(players[playerIndex]);


        assertEquals(players[playerIndex], manager.longestRoadOwner);
        assertEquals(players[playerIndex], manager.getLongestRoadOwner());
    }

    private static Stream<Arguments> returnTwoResources() {
        Stream.Builder<Arguments> argumentBuilder = Stream.builder();
        for (ResourceType resource1 : ResourceType.values()) {
            for (ResourceType resource2 : ResourceType.values()) {
                if (resource1.equals(resource2)){
                    continue;
                }
                argumentBuilder.add(Arguments.of(resource1, resource2));
            }
        }
        return argumentBuilder.build();
    }
}

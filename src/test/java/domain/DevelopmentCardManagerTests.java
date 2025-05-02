package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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
    BonusManager bonusManager;

    BoardManager boardManager;

    @BeforeEach
    public void setUp(){
        bank = EasyMock.createMock(Bank.class);
        player = EasyMock.createMock(Player.class);
        player2 = EasyMock.createMock(Player.class);
        boardManager = EasyMock.createMock(BoardManager.class);
        bonusManager = EasyMock.createMock(BonusManager.class);
        players = new Player[]{player,player2};

        manager = new DevelopmentCardManager(players, bank, boardManager, bonusManager);
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

        EasyMock.expect(bank.noMoreResource(EasyMock.eq(new ResourceTransaction(lumber, 1)))).andReturn(false).times(2); // Expect 2 calls for 2 resources


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

        ResourceType lumber = ResourceType.LUMBER;

        ArrayList<ResourceType> playerResources = new ArrayList<>();
        for(int i = 0; i< 4; i++){
            playerResources.add(lumber);
        }

        EasyMock.expect(bank.noMoreResource(EasyMock.eq(new ResourceTransaction(lumber, 1)))).andReturn(false).times(2); // Expect 2 calls for 2 resources
        player.addResource(lumber);
        player.addResource(lumber);
        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(bank,player);

        Collection<ResourceType> resources = manager.playYearOfPlenty(player, lumber, lumber);

        assertTrue(resources.remove(lumber));
        assertTrue(resources.remove(lumber));
        assertTrue(resources.remove(lumber));
        assertTrue(resources.remove(lumber));
        assertTrue(resources.isEmpty());

        EasyMock.verify(bank,player);
    }

    @Test
    public void testPlayYearOfPlenty_maxResourceAndSufficientBank(){

        ResourceType lumber = ResourceType.LUMBER;

        ArrayList<ResourceType> playerResources = new ArrayList<>();
        for(int i = 0; i < 19; i++){
            playerResources.addAll(Arrays.asList(ResourceType.values()));
        }

        EasyMock.expect(bank.noMoreResource(EasyMock.eq(new ResourceTransaction(lumber, 1)))).andReturn(false).times(2); // Expect 2 calls for 2 resources

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

        EasyMock.expect(bank.noMoreResource(EasyMock.eq(new ResourceTransaction(lumber, 1)))).andReturn(true); // Only need 1 check if first fails


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
        manager = new DevelopmentCardManager(players, bank, boardManager, bonusManager);

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
        manager = new DevelopmentCardManager(players, bank, boardManager, bonusManager);

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
        manager = new DevelopmentCardManager(players, bank, boardManager, bonusManager);

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


    @Test
    public void testPlayKnight_playerHasKnight_simpleValidPlay() {

        playKnight_expectStandardErrorChecks();

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);
        player.incrementPlayedKnightCount(); // Expect increment
        bonusManager.updateLargestArmy(player); // Expect bonus update

        EasyMock.replay(player,player2,boardManager, bonusManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(ResourceType.GRAIN, stolen);

        EasyMock.verify(player, player2, boardManager, bonusManager); // Verify bonusManager
    }

    @Test
    public void testPlayKnight_playerHasKnight_robberAlreadyOnHex() {
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);

        EasyMock.replay(player,player2,boardManager, bonusManager); // Include bonusManager

        String actualMessage = assertThrows(IllegalArgumentException.class,
                () -> manager.playKnight(player,player2,18)).getMessage();

        String expectedMessage = "Robber is already on this hex";
        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(player,boardManager, bonusManager); // Verify bonusManager
    }

    @Test
    public void testPlayKnight_playerHasKnight_playerNotOnHex() {
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        ArrayList<Player> onHexagon = new ArrayList<>(List.of(new Player[]{}));
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(onHexagon);
        boardManager.moveRobber(18); // Move robber back

        EasyMock.replay(player,player2,boardManager, bonusManager); // Include bonusManager

        String actualMessage = assertThrows(IllegalArgumentException.class,
                () -> manager.playKnight(player,player2,0)).getMessage();


        String expectedMessage = "Player is not on the hex";
        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(player,boardManager, bonusManager); // Verify bonusManager
    }


    @ParameterizedTest
    @ValueSource(ints = {-1,19})
    public void testPlayKnight_playerHasKnight_invalidHexIndex(int invalidIndices) {
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);

        EasyMock.replay(player,player2,boardManager, bonusManager); // Include bonusManager

        String actualMessage = assertThrows(IllegalArgumentException.class,
                () -> manager.playKnight(player,player2,invalidIndices)).getMessage();

        String expectedMessage = "Invalid hex index";
        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(player,boardManager, bonusManager); // Verify bonusManager
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
        player.incrementPlayedKnightCount(); // Expect increment
        bonusManager.updateLargestArmy(player); // Expect bonus update

        EasyMock.replay(player,player2,boardManager, bonusManager); // Include bonusManager

        ResourceType stolen = manager.playKnight(player,player2,hexIndex);
        assertEquals(ResourceType.GRAIN, stolen);

        EasyMock.verify(player,player2, boardManager, bonusManager); // Verify bonusManager
    }

    @Test
    public void testPlayKnight_playerHasKnight_firstLargestArmy() {

        playKnight_expectStandardErrorChecks();

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);
        player.incrementPlayedKnightCount(); // Expect increment
        bonusManager.updateLargestArmy(player); // Expect bonus update


        EasyMock.replay(player,player2,boardManager, bonusManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(ResourceType.GRAIN, stolen);

        EasyMock.verify(player,player2, boardManager, bonusManager);
    }

    @Test
    public void testPlayKnight_playerHasKnight_tiedLargestArmy() {

        playKnight_expectStandardErrorChecks();
        bonusManager.largestArmyOwner = player2;

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);
        player.incrementPlayedKnightCount(); // Expect increment
        bonusManager.updateLargestArmy(player); // Expect bonus update

        EasyMock.replay(player,player2,boardManager, bonusManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(ResourceType.GRAIN, stolen);

        EasyMock.verify(player,player2, boardManager, bonusManager);
        assertEquals(bonusManager.largestArmyOwner,player2);
    }

    @Test
    public void testPlayKnight_playerHasKnight_stealLargestArmy() {

        playKnight_expectStandardErrorChecks();
        bonusManager.largestArmyOwner = player2;

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(ResourceType.GRAIN);
        player.incrementPlayedKnightCount(); // Expect increment
        bonusManager.updateLargestArmy(player); // Expect bonus update

        EasyMock.replay(player,player2,boardManager, bonusManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(ResourceType.GRAIN, stolen);
        EasyMock.verify(player,player2, boardManager, bonusManager);
        assertEquals(bonusManager.largestArmyOwner,player);
    }

    @Test
    public void testPlayKnight_playerHasKnight_noResourcesToSteal() {

        playKnight_expectStandardErrorChecks();

        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(null);
        player.incrementPlayedKnightCount(); // Expect increment
        bonusManager.updateLargestArmy(player); // Expect bonus update


        EasyMock.replay(player,player2,boardManager, bonusManager);

        ResourceType stolen = manager.playKnight(player,player2,0);
        assertEquals(null, stolen);

        EasyMock.verify(player, player2, boardManager, bonusManager);
    }

    private void playKnight_expectStandardErrorChecks(){
        EasyMock.expect(player.hasDevelopmentCard(DevelopmentCards.KNIGHT)).andReturn(true);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        EasyMock.expect(boardManager.getRobberLocation()).andReturn(18);
        ArrayList<Player> onHexagon = new ArrayList<>(List.of(new Player[]{player2}));
        EasyMock.expect(boardManager.getHexagonPlayers()).andReturn(onHexagon);

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
        assertNull(bonusManager.largestArmyOwner);
    }

    private void playKnight_expectNoPlayedKnights() {
        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        boardManager.moveRobber(0);
        EasyMock.expect(boardManager.stealResource(player,player2)).andReturn(null);
        player.incrementPlayedKnightCount(); // Expect increment
        bonusManager.updateLargestArmy(player); // Expect bonus update
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
        assertNull(bonusManager.largestArmyOwner);
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

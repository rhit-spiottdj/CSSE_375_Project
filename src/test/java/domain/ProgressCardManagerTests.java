package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressCardManagerTests {

    Bank bank;
    Player player;
    Player player2;

    Collection<Player> players;
    ProgressCardManager manager;

    @BeforeEach
    public void setUp(){
        bank = EasyMock.createMock(Bank.class);
        player = EasyMock.createMock(Player.class);
        player2 = EasyMock.createMock(Player.class);
        players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        manager = new ProgressCardManager(bank);
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

        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(lumber, 2))).andReturn(false);

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

        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(lumber, 2))).andReturn(false);
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

        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(lumber, 2))).andReturn(false);

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

        EasyMock.expect(bank.noMoreResource(new ResourceTransaction(lumber, 2))).andReturn(true);

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

        Collection<ResourceType> resources = manager.playMonopoly(players,player,resource);

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

        Collection<ResourceType> resources = manager.playMonopoly(players,player,resource);

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

        Collection<ResourceType> resources = manager.playMonopoly(players,player,resource);

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

        Collection<ResourceType> resources = manager.playMonopoly(players,player,resource);

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

        Collection<ResourceType> resources = manager.playMonopoly(players,player,resource);

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

        Collection<ResourceType> resources = manager.playMonopoly(players,player,resource);

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
        players.add(player3);

        EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        EasyMock.expect(player3.removeResource(resource)).andReturn(true);
        player.addResource(resource);
        player.addResource(resource);


        EasyMock.expect(player2.removeResource(resource)).andReturn(false);
        EasyMock.expect(player3.removeResource(resource)).andReturn(false);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2,player3);

        Collection<ResourceType> resources = manager.playMonopoly(players,player,resource);

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
        players.add(player3);
        Player player4 = EasyMock.createMock(Player.class);
        players.add(player4);

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

        Collection<ResourceType> resources = manager.playMonopoly(players,player,resource);

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
        players.add(player3);
        Player player4 = EasyMock.createMock(Player.class);
        players.add(player4);

        EasyMock.expect(player2.removeResource(lumber)).andReturn(false);
        EasyMock.expect(player3.removeResource(lumber)).andReturn(false);
        EasyMock.expect(player4.removeResource(lumber)).andReturn(true);

        player.addResource(lumber);

        EasyMock.expect(player4.removeResource(lumber)).andReturn(false);

        EasyMock.expect(player.getResources()).andReturn(playerResources);

        EasyMock.replay(player,player2,player3,player4);

        Collection<ResourceType> resources = manager.playMonopoly(players,player,lumber);

        assertTrue(resources.remove(lumber));

        assertTrue(resources.isEmpty());

        EasyMock.verify(player,player2,player3,player4);
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

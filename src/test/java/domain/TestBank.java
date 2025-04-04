package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TestBank {

    Bank bank;

    Port threePort;

    @BeforeEach
    public void setup(){
        bank = new Bank();
        threePort = new Port(PortTradeRatio.THREE_TO_ONE, ResourceType.LUMBER);
    }

    @Test
    public void testObtainResourceLumberNegOne() {
        Bank bank = new Bank();
        assertFalse(bank.obtainResource(new ResourceTransaction(ResourceType.LUMBER, -1)));

        for (ResourceType resource : ResourceType.values()) {
            assertEquals(19, bank.resources.get(resource));
        }
    }


    @Test
    public void testObtainResourceLumberMin() {
        Bank bank = new Bank();
        assertFalse(bank.obtainResource(new ResourceTransaction(ResourceType.LUMBER, Integer.MIN_VALUE)));

        for (ResourceType resource : ResourceType.values()) {
            assertEquals(19, bank.resources.get(resource));
        }
    }

    @Test
    public void testObtainResourceLumberZero() {
        Bank bank = new Bank();
        assertFalse(bank.obtainResource(new ResourceTransaction(ResourceType.LUMBER, 0)));
        for (ResourceType resource : ResourceType.values()) {
            assertEquals(19, bank.resources.get(resource));
        }
    }


    @Test
    public void testObtainResourceBrickMax() {
        Bank bank = new Bank();


        assertTrue(bank.obtainResource(new ResourceTransaction(ResourceType.BRICK, 19)));

        for (ResourceType resource : ResourceType.values()) {
            if (resource.equals(ResourceType.BRICK)) {
                assertEquals(0, bank.resources.get(resource));
            } else {
                assertEquals(19, bank.resources.get(resource));
            }
        }
    }

    @Test
    public void testObtainResourceBrickMaxPlusOne() {
        Bank bank = new Bank();
        assertFalse(bank.obtainResource(new ResourceTransaction(ResourceType.BRICK, 20)));

        for (ResourceType resource : ResourceType.values()) {
            assertEquals(19, bank.resources.get(resource));
        }
    }

    @Test
    public void testObtainResourceBrickMinMinusOne() {
        Bank bank = new Bank();
        assertFalse(bank.obtainResource(new ResourceTransaction(ResourceType.BRICK, Integer.MAX_VALUE)));

        for (ResourceType resource : ResourceType.values()) {
            assertEquals(19, bank.resources.get(resource));
        }
    }


    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testObtainResourceOneResource(ResourceType resourceToTest) {
        Bank bank = new Bank();
        assertTrue(bank.obtainResource(new ResourceTransaction(resourceToTest, 1)));

        for (ResourceType resource : ResourceType.values()) {
            if (resource.equals(resourceToTest)) {
                assertEquals(18, bank.resources.get(resource));
            } else {
                assertEquals(19, bank.resources.get(resource));
            }
        }
    }

    @Test
    public void testObtainResourceNoRemaining() {
        Bank bank = new Bank();

        for (ResourceType resource : ResourceType.values()) {
            bank.resources.put(resource, 0);
        }

        assertFalse(bank.obtainResource(new ResourceTransaction(ResourceType.GRAIN, 1)));

        for (ResourceType resource : ResourceType.values()) {
            assertEquals(0, bank.resources.get(resource));
        }

    }

    @Test
    public void testObtainResourceOneRemaining() {
        Bank bank = new Bank();

        for (ResourceType resource : ResourceType.values()) {
            bank.resources.put(resource, 0);
        }
        bank.resources.put(ResourceType.GRAIN, 1);


        assertTrue(bank.obtainResource(new ResourceTransaction(ResourceType.GRAIN, 1)));

        for (ResourceType resource : ResourceType.values()) {
            assertEquals(0, bank.resources.get(resource));
        }
    }

    @Test
    public void testObtainResourceTwoRemaining() {
        Bank bank = new Bank();
        for (ResourceType resource : ResourceType.values()) {
            bank.resources.put(resource, 0);
        }
        bank.resources.put(ResourceType.GRAIN, 1);
        bank.resources.put(ResourceType.ORE, 1);

        assertTrue(bank.obtainResource(new ResourceTransaction(ResourceType.GRAIN, 1)));

        for (ResourceType resource : ResourceType.values()) {
            if (resource.equals(ResourceType.ORE)) {
                assertEquals(1, bank.resources.get(resource));
            } else {
                assertEquals(0, bank.resources.get(resource));
            }
        }
    }


    @Test
    public void testTradePort_ThreeLBNegOne() {
        assertFalse(
            bank.tradeResourcePort(threePort, ResourceType.LUMBER, ResourceType.BRICK, -1));
    }

    @Test
    public void testTradePort_ThreeLBMin() {
       assertFalse(bank.tradeResourcePort(threePort, ResourceType.LUMBER, ResourceType.BRICK,
                Integer.MIN_VALUE));

    }

    @Test
    public void testTradePort_ThreeLBZero() {
        assertFalse(bank.tradeResourcePort(threePort, ResourceType.LUMBER, ResourceType.BRICK, 0));
    }

    @Test
    public void testTradePort_ThreeLBOne() {
        bank.obtainResource(new ResourceTransaction(ResourceType.LUMBER, 3));
        assertTrue(bank.tradeResourcePort(threePort, ResourceType.LUMBER, ResourceType.BRICK, 1));
        assertEquals(18, bank.resources.get(ResourceType.BRICK));
        assertEquals(19, bank.resources.get(ResourceType.LUMBER));
    }

    @Test
    public void testTradePort_TwoGWNine() {
        bank.obtainResource(new ResourceTransaction(ResourceType.GRAIN,18));
        Port twoPort = new Port(PortTradeRatio.TWO_TO_ONE, ResourceType.GRAIN);
        assertTrue(bank.tradeResourcePort(twoPort, ResourceType.GRAIN, ResourceType.WOOL, 9));
        assertEquals(19, bank.resources.get(ResourceType.GRAIN));
        assertEquals(10, bank.resources.get(ResourceType.WOOL));
    }

    @ParameterizedTest
    @EnumSource(names = {"GRAIN", "WOOL", "LUMBER", "ORE"})
    public void testTradePort_twoResourceEachTypeBrickOne(ResourceType giveAway){
        bank.obtainResource(new ResourceTransaction(giveAway,2));
        Port twoPort = new Port(PortTradeRatio.TWO_TO_ONE, giveAway);
        assertTrue(bank.tradeResourcePort(twoPort, giveAway, ResourceType.BRICK, 1));
        assertEquals(19, bank.resources.get(giveAway));
        assertEquals(18, bank.resources.get(ResourceType.BRICK));
    }

    @Test
    public void testTradePort_twoResourceBrickGrainOne(){
        bank.obtainResource(new ResourceTransaction(ResourceType.BRICK,2));
        Port twoPort = new Port(PortTradeRatio.TWO_TO_ONE, ResourceType.BRICK);
        assertTrue(bank.tradeResourcePort(twoPort, ResourceType.BRICK, ResourceType.GRAIN, 1));
        assertEquals(19, bank.resources.get(ResourceType.BRICK));
        assertEquals(18, bank.resources.get(ResourceType.GRAIN));
    }

    @Test
    public void testTradePort_twoInvalidGive(){
        Port twoPort = new Port(PortTradeRatio.TWO_TO_ONE, ResourceType.GRAIN);
        assertFalse(bank.tradeResourcePort(twoPort, ResourceType.LUMBER, ResourceType.BRICK, 1));
    }

    @Test
    public void testTradePort_emptyGiveOneTake(){
        bank.obtainResource(new ResourceTransaction(ResourceType.GRAIN,19));
        bank.obtainResource(new ResourceTransaction(ResourceType.BRICK,18));
        Port twoPort = new Port(PortTradeRatio.TWO_TO_ONE, ResourceType.GRAIN);
        assertTrue(bank.tradeResourcePort(twoPort, ResourceType.GRAIN, ResourceType.BRICK, 1));
        assertEquals(2, bank.resources.get(ResourceType.GRAIN));
        assertEquals(0, bank.resources.get(ResourceType.BRICK));
    }

    @Test
    public void testTradePort_oneGiveTwoTake(){
        bank.obtainResource(new ResourceTransaction(ResourceType.GRAIN,18));
        bank.obtainResource(new ResourceTransaction(ResourceType.BRICK,17));
        Port twoPort = new Port(PortTradeRatio.TWO_TO_ONE, ResourceType.GRAIN);
        assertTrue(bank.tradeResourcePort(twoPort, ResourceType.GRAIN, ResourceType.BRICK, 1));
        assertEquals(3, bank.resources.get(ResourceType.GRAIN));
        assertEquals(1, bank.resources.get(ResourceType.BRICK));
    }

    @Test
    public void testTradePort_twoGiveThreeTake(){
        bank.obtainResource(new ResourceTransaction(ResourceType.GRAIN,17));
        bank.obtainResource(new ResourceTransaction(ResourceType.BRICK,16));
        assertTrue(bank.tradeResourcePort(threePort, ResourceType.GRAIN, ResourceType.BRICK, 1));
        assertEquals(5, bank.resources.get(ResourceType.GRAIN));
        assertEquals(2, bank.resources.get(ResourceType.BRICK));
    }

    @Test
    public void testTradePort_emptyTake(){
        bank.obtainResource(new ResourceTransaction(ResourceType.GRAIN,17));
        bank.obtainResource(new ResourceTransaction(ResourceType.BRICK,19));
        assertFalse(bank.tradeResourcePort(threePort, ResourceType.GRAIN, ResourceType.BRICK, 1));
    }

    @Test
    public void testTradeBankLBNegOne() {
        Bank bank = new Bank();
        assertFalse(bank.tradeResourceBank(ResourceType.LUMBER, ResourceType.BRICK, -1));
    }

    @Test
    public void testTradeBankLBMin() {
        Bank bank = new Bank();
        assertFalse(
            bank.tradeResourceBank(ResourceType.LUMBER, ResourceType.BRICK, Integer.MIN_VALUE));
    }

    @Test
    public void testTradeBankLBZero() {
        Bank bank = new Bank();
        assertFalse(bank.tradeResourceBank(ResourceType.LUMBER, ResourceType.BRICK, 0));
    }

    @Test
    public void testTradeBankLBOne() {
        Bank bank = new Bank();
        assertTrue(bank.tradeResourceBank(ResourceType.LUMBER, ResourceType.BRICK, 1));
    }

    @Test
    public void testTradeBankGWMax() {
        Bank bank = new Bank();
        assertTrue(bank.tradeResourceBank(ResourceType.GRAIN, ResourceType.WOOL, 19));
    }

    @Test
    public void testTradeBankLBMaxPlusOne() {
        Bank bank = new Bank();
        assertFalse(bank.tradeResourceBank(ResourceType.LUMBER, ResourceType.BRICK, 20));
    }

    @Test
    public void testTradeBankLBMinMinusOne() {
        Bank bank = new Bank();
        assertFalse(
            bank.tradeResourceBank(ResourceType.LUMBER, ResourceType.BRICK, Integer.MAX_VALUE));
    }

    @Test
    public void testTradeBankOWOne() {
        Bank bank = new Bank();
        bank.setBankResource(new ResourceTransaction(ResourceType.ORE, 0));
        bank.setBankResource(new ResourceTransaction(ResourceType.WOOL, 19));
        assertTrue(bank.tradeResourceBank(ResourceType.ORE, ResourceType.WOOL, 1));
        assertEquals(4, bank.resources.get(ResourceType.ORE));
        assertEquals(18, bank.resources.get(ResourceType.WOOL));
    }

    @Test
    public void testTradeBankBGMaxPlusOne() {
        Bank bank = new Bank();
        assertFalse(bank.tradeResourceBank(ResourceType.BRICK, ResourceType.GRAIN, 20));
        assertEquals(19, bank.resources.get(ResourceType.BRICK));
        assertEquals(19, bank.resources.get(ResourceType.GRAIN));
    }

    @Test
    public void testObtainDevelopmentCard() {
        Bank bank = new Bank();
        DevelopmentCards card = DevelopmentCards.KNIGHT; //No need to mock, its just an enum
        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(card);
        bank.setDevelopmentCards(cards);
        Player player = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        resources.add(ResourceType.ORE);
        resources.add(ResourceType.GRAIN);
        resources.add(ResourceType.WOOL);
        EasyMock.expect(player.hasResources(resources)).andReturn(true);
        player.addDevelopmentCard(card);
        EasyMock.expectLastCall();
        for(ResourceType resource: resources){
            EasyMock.expect(player.removeResource(resource)).andReturn(true);
        }

        EasyMock.replay(player);
        assertTrue(bank.obtainDevelopmentCard(player));
        EasyMock.verify(player);
    }

    @Test
    public void testObtainDevelopmentCardNoResources() {
        Bank bank = new Bank();
        DevelopmentCards card = DevelopmentCards.KNIGHT;
        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(card);
        bank.setDevelopmentCards(cards);
        Player player = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources = new ArrayList<>();
        resources.add(ResourceType.ORE);
        resources.add(ResourceType.GRAIN);
        resources.add(ResourceType.WOOL);
        EasyMock.expect(player.hasResources(resources)).andReturn(false);
        EasyMock.replay(player);
        assertFalse(bank.obtainDevelopmentCard(player));
        EasyMock.verify(player);
    }

    @Test
    public void testObtainDevelopmentCardNoStock() {
        Bank bank = new Bank();
        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        bank.setDevelopmentCards(cards);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);
        assertFalse(bank.obtainDevelopmentCard(player));
        EasyMock.verify(player);
    }

    @Test
    public void testObtainDevelopmentCardPlayerHasResources() {
        Bank bank = new Bank();
        DevelopmentCards card = DevelopmentCards.KNIGHT;
        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(card);
        bank.setDevelopmentCards(cards);
        ArrayList<ResourceType> resourcesPlayer = new ArrayList<>();
        resourcesPlayer.add(ResourceType.ORE);
        resourcesPlayer.add(ResourceType.GRAIN);
        resourcesPlayer.add(ResourceType.WOOL);
        Player player = new Player(Color.BLUE, "Player", resourcesPlayer);
        assertTrue(bank.obtainDevelopmentCard(player));
        assertEquals(1, player.getFutureDevelopmentCards().size());
    }

    @Test
    public void testObtainDevelopmentCardSizeDecrease() {
        Bank bank = new Bank();
        DevelopmentCards card = DevelopmentCards.KNIGHT;
        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(card);
        bank.setDevelopmentCards(cards);
        ArrayList<ResourceType> resourcesPlayer = new ArrayList<>();
        resourcesPlayer.add(ResourceType.ORE);
        resourcesPlayer.add(ResourceType.GRAIN);
        resourcesPlayer.add(ResourceType.WOOL);
        Player player = new Player(Color.BLUE, "Player", resourcesPlayer);
        assertTrue(bank.obtainDevelopmentCard(player));
        assertEquals(0, bank.getDevelopmentCards().size());
    }

    @Test
    public void testObtainDevelopmentCardSpend() {
        Bank bank = new Bank();
        DevelopmentCards card = DevelopmentCards.KNIGHT;
        ArrayList<DevelopmentCards> cards = new ArrayList<>();
        cards.add(card);
        bank.setDevelopmentCards(cards);
        ArrayList<ResourceType> resourcesPlayer = new ArrayList<>();
        resourcesPlayer.add(ResourceType.ORE);
        resourcesPlayer.add(ResourceType.GRAIN);
        resourcesPlayer.add(ResourceType.WOOL);
        Player player = new Player(Color.BLUE, "Player", resourcesPlayer);
        assertTrue(bank.obtainDevelopmentCard(player));
        assertEquals(0, player.getResources().size());
    }



//### Test value 1
//    Input: both Player's don't have resources, grain for grain
//    Output: False, Player resources unchanged

    @Test
    public void testPlayerTradeNoResources() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        resources1.add(ResourceType.GRAIN);
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        resources2.add(ResourceType.GRAIN);
        EasyMock.expect(player1.hasResources(resources1)).andReturn(false);
        EasyMock.expect(player1.hasResources(resources1)).andReturn(false);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(false);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertFalse(bank.playerTrade(player1, player2, resources1, resources2));
        assertFalse(player1.hasResources(resources1));
        assertFalse(player2.hasResources(resources2));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

//### Test value 2
//    Input: Player1 has resources, Player2 doesn't, grain for grain
//    Output: False, Player resources unchanged

    @Test
    public void testPlayerTradeOnePlayerNoResources() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        resources1.add(ResourceType.GRAIN);
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        resources2.add(ResourceType.GRAIN);
        EasyMock.expect(player1.hasResources(resources1)).andReturn(true);
        EasyMock.expect(player1.hasResources(resources2)).andReturn(true);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(false);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(false);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertFalse(bank.playerTrade(player1, player2, resources1, resources2));
        assertTrue(player1.hasResources(resources1));
        assertFalse(player2.hasResources(resources2));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

//### Test value 3
//    Input: Player1 hasn't resources, Player2 does, ore for ore
//    Output: False, Player resources unchanged

    @Test
    public void testPlayerTradeOnePlayerNoResources2() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        resources1.add(ResourceType.ORE);
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        resources2.add(ResourceType.ORE);
        EasyMock.expect(player1.hasResources(resources1)).andReturn(false);
        EasyMock.expect(player1.hasResources(resources2)).andReturn(false);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertFalse(bank.playerTrade(player1, player2, resources1, resources2));
        assertFalse(player1.hasResources(resources1));
        assertTrue(player2.hasResources(resources2));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

//### Test value 4
//    Input: both have resources, ore for ore
//    Output: True, Player resources updated

    @Test
    public void testPlayerTradeBothPlayersHaveResources() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        resources1.add(ResourceType.ORE);
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        resources2.add(ResourceType.ORE);
        EasyMock.expect(player1.hasResources(resources1)).andReturn(true);
        for(ResourceType resource: resources1){
            EasyMock.expect(player1.removeResource(resource)).andReturn(true);
        }
        addResources(player1,resources2);
        EasyMock.expectLastCall();
        EasyMock.expect(player1.hasResources(resources2)).andReturn(true);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);
        for(ResourceType resource: resources2){
            EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        }
        addResources(player2,resources1);
        EasyMock.expectLastCall();
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertTrue(bank.playerTrade(player1, player2, resources1, resources2));
        assertTrue(player1.hasResources(resources2));
        assertTrue(player2.hasResources(resources1));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

//### Test value 5
//    Input: both have resources, both empty trades
//    Output: True, Player resources updated

    @Test
    public void testPlayerTradeBothPlayersHaveResourcesEmpty() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        EasyMock.expect(player1.hasResources(resources1)).andReturn(true);
        for(ResourceType resource: resources1){
            EasyMock.expect(player1.removeResource(resource)).andReturn(true);
        }
        addResources(player1,resources2);
        EasyMock.expectLastCall();
        EasyMock.expect(player1.hasResources(resources2)).andReturn(true);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);
        for(ResourceType resource: resources2){
            EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        }
        addResources(player2,resources1);
        EasyMock.expectLastCall();
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertTrue(bank.playerTrade(player1, player2, resources1, resources2));
        assertTrue(player1.hasResources(resources2));
        assertTrue(player2.hasResources(resources1));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

//### Test value 6
//    Input: both have resources, three brick for one ore
//    Output: True, Player resources updated

    @Test
    public void testPlayerTradeBothPlayersHaveResourcesThreeForOne() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        resources1.add(ResourceType.BRICK);
        resources1.add(ResourceType.BRICK);
        resources1.add(ResourceType.BRICK);
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        resources2.add(ResourceType.ORE);
        EasyMock.expect(player1.hasResources(resources1)).andReturn(true);
        for(ResourceType resource: resources1){
            EasyMock.expect(player1.removeResource(resource)).andReturn(true);
        }
        addResources(player1,resources2);
        EasyMock.expectLastCall();
        EasyMock.expect(player1.hasResources(resources2)).andReturn(true);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);
        for(ResourceType resource: resources2){
            EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        }
        addResources(player2,resources1);
        EasyMock.expectLastCall();
        EasyMock.expect(player2.hasResources(resources1)).andReturn(true);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertTrue(bank.playerTrade(player1, player2, resources1, resources2));
        assertTrue(player1.hasResources(resources2));
        assertTrue(player2.hasResources(resources1));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

//### Test value 7
//    Input: both have resources, one wool, one lumber, one brick for one ore
//    Output: True, Player resources updated

    @Test
    public void testPlayerTradeBothPlayersHaveResourcesOneEach() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        resources1.add(ResourceType.WOOL);
        resources1.add(ResourceType.LUMBER);
        resources1.add(ResourceType.BRICK);
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        resources2.add(ResourceType.ORE);
        EasyMock.expect(player1.hasResources(resources1)).andReturn(true);
        for(ResourceType resource: resources1){
            EasyMock.expect(player1.removeResource(resource)).andReturn(true);
        }
        addResources(player1,resources2);
        EasyMock.expectLastCall();
        EasyMock.expect(player1.hasResources(resources2)).andReturn(true);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);
        for(ResourceType resource: resources2){
            EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        }
        addResources(player2,resources1);
        EasyMock.expectLastCall();
        EasyMock.expect(player2.hasResources(resources1)).andReturn(true);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertTrue(bank.playerTrade(player1, player2, resources1, resources2));
        assertTrue(player1.hasResources(resources2));
        assertTrue(player2.hasResources(resources1));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

//### Test value 8
//    Input: both have resources, empty for one wool
//    Output: True, Player resources updated

    @Test
    public void testPlayerTradeEmptyForOne() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        resources2.add(ResourceType.WOOL);
        EasyMock.expect(player1.hasResources(resources1)).andReturn(true);
        for(ResourceType resource: resources1){
            EasyMock.expect(player1.removeResource(resource)).andReturn(true);
        }
        addResources(player1,resources2);
        EasyMock.expectLastCall();
        EasyMock.expect(player1.hasResources(resources2)).andReturn(true);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);
        for(ResourceType resource: resources2){
            EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        }
        addResources(player2,resources1);
        EasyMock.expectLastCall();
        EasyMock.expect(player2.hasResources(resources1)).andReturn(true);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertTrue(bank.playerTrade(player1, player2, resources1, resources2));
        assertTrue(player1.hasResources(resources2));
        assertTrue(player2.hasResources(resources1));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

//### Test value 9
//    Input: both have resources, (19 of each resource) for empty
//    Output: True, Player resources updated

    @Test
    public void testPlayerTradeAllForEmpty() {
        Bank bank = new Bank();
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        ArrayList<ResourceType> resources1 = new ArrayList<>();
        addAllResources(resources1);
        ArrayList<ResourceType> resources2 = new ArrayList<>();
        EasyMock.expect(player1.hasResources(resources1)).andReturn(true);
        for(ResourceType resource: resources1){
            EasyMock.expect(player1.removeResource(resource)).andReturn(true);
        }
        addResources(player1,resources2);
        EasyMock.expectLastCall();
        EasyMock.expect(player1.hasResources(resources2)).andReturn(true);
        EasyMock.expect(player2.hasResources(resources2)).andReturn(true);
        for(ResourceType resource: resources2){
            EasyMock.expect(player2.removeResource(resource)).andReturn(true);
        }
        addResources(player2,resources1);
        EasyMock.expectLastCall();
        EasyMock.expect(player2.hasResources(resources1)).andReturn(true);

        EasyMock.replay(player1);
        EasyMock.replay(player2);

        assertTrue(bank.playerTrade(player1, player2, resources1, resources2));
        assertTrue(player1.hasResources(resources2));
        assertTrue(player2.hasResources(resources1));

        EasyMock.verify(player1);
        EasyMock.verify(player2);
    }

    private void addAllResources(ArrayList<ResourceType> resources) {
        for (int i = 0; i < 19; i++) {
            resources.addAll(Arrays.asList(ResourceType.values()));
        }
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_playerDiscardResources_expectOneResource(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 18);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(EasyMock.anyObject())).andReturn(true);

        ArrayList<ResourceType> resourcesToDiscard = new ArrayList<>();
        resourcesToDiscard.add(resource);
        EasyMock.expect(player.removeResource(resource)).andReturn(true);
        EasyMock.replay(player);
        assertTrue(bank.playerDiscardResources(player, resourcesToDiscard));
        assertEquals(19, bank.resources.get(resource));
        EasyMock.verify(player);

    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_playerDiscardResources_expectTwoResources(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 17);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(EasyMock.anyObject())).andReturn(true);

        ArrayList<ResourceType> resourcesToDiscard = new ArrayList<>();
        resourcesToDiscard.add(resource);
        resourcesToDiscard.add(resource);
        EasyMock.expect(player.removeResource(resource)).andReturn(true);
        EasyMock.expect(player.removeResource(resource)).andReturn(true);
        EasyMock.replay(player);
        assertTrue(bank.playerDiscardResources(player, resourcesToDiscard));
        assertEquals(19, bank.resources.get(resource));
        EasyMock.verify(player);

    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_playerDiscardResources_expectMaxResources(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 0);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(EasyMock.anyObject())).andReturn(true);

        ArrayList<ResourceType> resourcesToDiscard = new ArrayList<>();
        for(int i = 0; i < 19; i++){
            resourcesToDiscard.add(resource);
            EasyMock.expect(player.removeResource(resource)).andReturn(true);
        }
        EasyMock.replay(player);
        assertTrue(bank.playerDiscardResources(player, resourcesToDiscard));
        assertEquals(19, bank.resources.get(resource));
        EasyMock.verify(player);

    }

    @Test
    public void testBank_playerDiscardResources_playerDoesNotHaveResourcesExpectFalse() {
        Bank bank = new Bank();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(EasyMock.anyObject())).andReturn(false);
        EasyMock.replay(player);
        assertFalse(bank.playerDiscardResources(player, new ArrayList<>()));
        EasyMock.verify(player);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_playerDiscardResources_playerRemoveResourcesExpectFalse(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 19);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(EasyMock.anyObject())).andReturn(true);
        EasyMock.expect(player.removeResource(resource)).andReturn(false);
        EasyMock.replay(player);
        assertFalse(bank.playerDiscardResources(player, new ArrayList<>(Arrays.asList(resource))));
        EasyMock.verify(player);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_giveBackResource_bankMaxResourceExpectFalse(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 19);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(EasyMock.anyObject())).andReturn(true);
        EasyMock.expect(player.removeResource(resource)).andReturn(true);
        EasyMock.replay(player);
        assertFalse(bank.playerDiscardResources(player, new ArrayList<>(Arrays.asList(resource))));
        EasyMock.verify(player);

    }

    @Test
    public void testBank_giveBackResource_minValExpectFalse() {
        Bank bank = new Bank();
        bank.resources.put(ResourceType.LUMBER, 0);
        assertFalse(bank.giveBackResource(new ResourceTransaction(ResourceType.LUMBER, Integer.MIN_VALUE)));
        assertEquals(0, bank.resources.get(ResourceType.LUMBER));
    }

    @Test
    public void testBank_giveBackResource_negOneExpectFalse() {
        Bank bank = new Bank();
        bank.resources.put(ResourceType.LUMBER, 0);
        assertFalse(bank.giveBackResource(new ResourceTransaction(ResourceType.LUMBER, -1)));
        assertEquals(0, bank.resources.get(ResourceType.LUMBER));
    }

    @Test
    public void testBank_giveBackResource_zeroExpectFalse() {
        Bank bank = new Bank();
        bank.resources.put(ResourceType.LUMBER, 0);
        assertFalse(bank.giveBackResource(new ResourceTransaction(ResourceType.LUMBER, 0)));
        assertEquals(0, bank.resources.get(ResourceType.LUMBER));
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_giveBackResource_oneExpectTrue(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 0);
        assertTrue(bank.giveBackResource(new ResourceTransaction(resource, 1)));
        assertEquals(1, bank.resources.get(resource));
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_giveBackResource_maxValExpectTrue(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 0);
        assertTrue(bank.giveBackResource(new ResourceTransaction(resource, 19)));
        assertEquals(19, bank.resources.get(resource));
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_giveBackResource_maxResourcesExpectFalse(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 19);
        assertFalse(bank.giveBackResource(new ResourceTransaction(resource, 1)));
        assertEquals(19, bank.resources.get(resource));
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_giveBackResource_oneResourceInBankExpectTrue(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 1);
        assertTrue(bank.giveBackResource(new ResourceTransaction(resource, 1)));
        assertEquals(2, bank.resources.get(resource));
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_giveBackResource_twoResourceInBankExpectTrue(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 2);
        assertTrue(bank.giveBackResource(new ResourceTransaction(resource, 1)));
        assertEquals(3, bank.resources.get(resource));
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_giveBackResource_oneResourceInBankGiveBackTwoExpectTrue(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 1);
        assertTrue(bank.giveBackResource(new ResourceTransaction(resource, 2)));
        assertEquals(3, bank.resources.get(resource));
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBank_giveBackResource_twoResourceInBankGiveBackTwoExpectTrue(ResourceType resource) {
        Bank bank = new Bank();
        bank.resources.put(resource, 2);
        assertTrue(bank.giveBackResource(new ResourceTransaction(resource, 2)));
        assertEquals(4, bank.resources.get(resource));
    }

    @Test
    public void testBank_setupDevelopmentCards(){
        Bank bank = new Bank();

        Collection<DevelopmentCards> cards = bank.getDevelopmentCards(); // Enum so can use directly

        for(int i  = 0; i < 14; i++){
            assertTrue(cards.remove(DevelopmentCards.KNIGHT));
        }
        for(int i  = 0; i < 5; i++){
            assertTrue(cards.remove(DevelopmentCards.VICTORY));
        }
        for(int i  = 0; i < 2; i++){
            assertTrue(cards.remove(DevelopmentCards.MONOPOLY));
            assertTrue(cards.remove(DevelopmentCards.PLENTY));
            assertTrue(cards.remove(DevelopmentCards.ROAD));
        }

        assertTrue(cards.isEmpty());
    }


    @ParameterizedTest
    @EnumSource
    public void testGetAndSetBankResource_resources(ResourceType resource){
        bank.setBankResource(new ResourceTransaction(resource, 5));
        assertEquals(5, bank.getBankResource(resource));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,19})
    public void testGetAndSetBankResource_amounts(int amount){
        bank.setBankResource(new ResourceTransaction(ResourceType.GRAIN, amount));
        assertEquals(amount, bank.getBankResource(ResourceType.GRAIN));
    }

    private void addResources(Player player, Collection<ResourceType> resources){
        Iterator<ResourceType> iter = resources.iterator();
        while(iter.hasNext()){
            player.addResource(iter.next());
        }
    }
}

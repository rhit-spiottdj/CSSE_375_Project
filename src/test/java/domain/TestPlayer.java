package domain;

import io.cucumber.java.af.En;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static domain.DevelopmentCards.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    private Player createTestPlayer() {
        return new Player(Color.BLUE, "Player1", new ArrayList<>());
    }

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_addEachResourceToEmpty(ResourceType resource) {

        Player player = createTestPlayer();
        player.addResource(resource);

        assertTrue(player.getResources().contains(resource));
        assertEquals(1, player.getResources().size());
    }


    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_addEachResourceToSingleSize(ResourceType resource) {

        Player player = createTestPlayer();
        player.addResource(resource);
        player.addResource(resource);

        assertTrue(player.getResources().contains(resource));
        assertEquals(2, player.getResources().size());

    }

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_addEachResourceToMultipleSize(ResourceType resource) {

        Player player = createTestPlayer();
        player.addResource(resource);
        player.addResource(resource);
        player.addResource(resource);

        assertTrue(player.getResources().contains(resource));
        assertEquals(3, player.getResources().size());

    }

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_addEachResourceToMaxSizeException(ResourceType resource) {

        Player player = createTestPlayer();
        for (int i = 0; i < 95; i++) {
            player.addResource(resource);
        }

        String expected = "Maximum resources reached, cannot add more";

        String actual = assertThrows(IllegalStateException.class,
                () -> player.addResource(resource)).getMessage();


        assertEquals(expected, actual);
    }

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_removeEachResourceToEmptyException(ResourceType resource) {

        Player player = createTestPlayer();

        assertFalse(player.removeResource(resource));
    }


    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_removeEachResourceToSingleSize(ResourceType resource) {

        Player player = createTestPlayer();
        player.addResource(resource);
        player.addResource(resource);

        assertTrue(player.removeResource(resource));
        assertEquals(1, player.getResources().size());

    }

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_removeEachResourceToMultipleSize(ResourceType resource) {

        Player player = createTestPlayer();
        player.addResource(resource);
        player.addResource(resource);
        player.addResource(resource);

        assertTrue(player.removeResource(resource));
        assertEquals(2, player.getResources().size());

    }

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_removeEachResourceToMaxSize(ResourceType resource) {

        Player player = createTestPlayer();
        for (int i = 0; i < 95; i++) {
            player.addResource(resource);
        }

        assertTrue(player.removeResource(resource));
        assertEquals(94, player.getResources().size());
    }

    @Test
    public void testPlayer_checkNumResourceToEmpty() {
        Player player = createTestPlayer();

        assertEquals(0, player.checkNumResource());
    }

    @Test
    public void testPlayer_checkNumResourceToSingleSize() {
        Player player = createTestPlayer();
        player.addResource(ResourceType.BRICK);

        assertEquals(1, player.checkNumResource());
    }

    @Test
    public void testPlayer_checkNumResourceToSingleSize2() {
        Player player = createTestPlayer();
        player.addResource(ResourceType.LUMBER);

        assertEquals(1, player.checkNumResource());
    }

    @Test
    public void testPlayer_checkNumResourceToMultipleSize() {
        Player player = createTestPlayer();
        player.addResource(ResourceType.LUMBER);
        player.addResource(ResourceType.GRAIN);

        assertEquals(2, player.checkNumResource());

        player.addResource(ResourceType.ORE);

        assertEquals(3, player.checkNumResource());
    }

    @Test
    public void testPlayer_checkNumResourceToMaxSize() {
        Player player = createTestPlayer();
        for (int i = 0; i < 95; i++) {
            player.addResource(ResourceType.LUMBER);
        }

        assertEquals(95, player.checkNumResource());
    }

    
    // Changed the assert; was not sure why we would want this to be true
    // as the player does not have resources. Included behavior for
    // hasResources that checks the collection has a size of > 0 
    @Test
    public void testPlayer_hasResourcesBothEmpty() {

        Player player = createTestPlayer();

        boolean actual = player.hasResources(new ArrayList<>());
        assertFalse(actual);
    }

    // Lines 176-178
    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_hasResourcesToEmptyToSingleSizeException(ResourceType resource) {

        Player player = createTestPlayer();
        player.addResource(resource);

        boolean actual = player.hasResources(new ArrayList<>());
        assertFalse(actual);
    }
    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_hasResourcesToSingleSizeToSingleSizeExpectTrue(ResourceType resource) {
        ArrayList<ResourceType> listToCheck = new ArrayList<>();
        listToCheck.add(resource);
        Player player = createTestPlayer();
        player.addResource(resource);

        assertTrue(player.hasResources(listToCheck));
    }

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testPlayer_hasResourcesToSingleSizeToEmptyExpectFalse(ResourceType resource) {
        ArrayList<ResourceType> listToCheck = new ArrayList<>();
        listToCheck.add(resource);
        Player player = createTestPlayer();

        assertFalse(player.hasResources(listToCheck));
    }

    @Test
    public void testPlayer_hasResourcesToSingleSizeToSingleSizeExpectFalse() {
        ArrayList<ResourceType> listToCheck = new ArrayList<>();
        listToCheck.add(ResourceType.ORE);
        Player player = createTestPlayer();
        player.addResource(ResourceType.WOOL);

        assertFalse(player.hasResources(listToCheck));
    }

    @Test
    public void testPlayer_hasResourcesToMultipleSizeToMultipleSizeExpectTrue() {
        ArrayList<ResourceType> listToCheck = new ArrayList<>();
        listToCheck.add(ResourceType.ORE);
        listToCheck.add(ResourceType.ORE);
        Player player = createTestPlayer();
        player.addResource(ResourceType.ORE);
        player.addResource(ResourceType.ORE);
        player.addResource(ResourceType.WOOL);

        assertTrue(player.hasResources(listToCheck));
    }

    @Test
    public void testPlayer_hasResourcesToMultipleSizeToMultipleSizeExpectFalse() {
        ArrayList<ResourceType> listToCheck = new ArrayList<>();
        listToCheck.add(ResourceType.LUMBER);
        listToCheck.add(ResourceType.ORE);
        Player player = createTestPlayer();
        player.addResource(ResourceType.LUMBER);
        player.addResource(ResourceType.WOOL);

        assertFalse(player.hasResources(listToCheck));
    }

    @Test
    public void testPlayer_hasResourcesToMaxSizeToMultipleSizeExpectFalse() {
        ArrayList<ResourceType> listToCheck = new ArrayList<>();
        Player player = createTestPlayer();
        for (int i = 0; i < 19; i++) {
            for(ResourceType resource: ResourceType.values()){
                listToCheck.add(resource);
            }
        }
        player.addResource(ResourceType.LUMBER);
        player.addResource(ResourceType.LUMBER);
        player.addResource(ResourceType.LUMBER);

        assertFalse(player.hasResources(listToCheck));
    }

    @Test
    public void testPlayer_hasResourcesToMaxSizeToMaxSizeExpectFalse() {
        ArrayList<ResourceType> listToCheck = new ArrayList<>();
        Player player = createTestPlayer();
        for (int i = 0; i < 19; i++) {
            for(ResourceType resource: ResourceType.values()){
                listToCheck.add(resource);
            }
        }
        for (int i = 0; i < 19; i++) {
            for(ResourceType resource: ResourceType.values()){
                player.addResource(resource);
            }
        }

        assertTrue(player.hasResources(listToCheck));
    }



    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testPlayer_getNumOwnedResource_empty(ResourceType resourceType){
        Player player = createTestPlayer();

        int actual = player.getNumOwnedResource(resourceType);

        assertEquals(0, actual);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testPlayer_getNumOwnedResource_oneResource(ResourceType resourceType){
        ArrayList<ResourceType> resources = new ArrayList<>();
        resources.add(resourceType);
        Player player = new Player(Color.ORANGE, "Todd", resources);

        int actual = player.getNumOwnedResource(resourceType);

        assertEquals(1, actual);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testPlayer_getNumOwnedResource_twoResources(ResourceType resourceType){
        ArrayList<ResourceType> resources = new ArrayList<>();
        resources.add(resourceType);
        resources.add(resourceType);
        Player player = new Player(Color.ORANGE, "Todd", resources);

        int actual = player.getNumOwnedResource(resourceType);

        assertEquals(2, actual);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testPlayer_getNumOwnedResource_allExceptOneResource(ResourceType resourceType){
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(ResourceType resource: ResourceType.values()){
            if(!resource.equals(resourceType)){
                resources.add(resource);
            }
        }
        Player player = new Player(Color.ORANGE, "Todd", resources);

        int actual = player.getNumOwnedResource(resourceType);

        assertEquals(0, actual);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testPlayer_getNumOwnedResource_maxResources(ResourceType resourceType){
        ArrayList<ResourceType> resources = new ArrayList<>();
        for(ResourceType resource: ResourceType.values()){
            for(int i = 0; i < 19; i++ ){
                resources.add(resource);
            }
        }
        Player player = new Player(Color.ORANGE, "Todd", resources);

        int actual = player.getNumOwnedResource(resourceType);

        assertEquals(19, actual);
    }

    @ParameterizedTest
    @EnumSource(names = {"KNIGHT", "MONOPOLY",
            "PLENTY", "ROAD"})
    public void testPlayer_setDevelopmentCardAsPlayed_simpleSingleCard(DevelopmentCards devCard){
        Player player = createTestPlayer();
        player.developmentCards.add(devCard);

        player.setDevelopmentCardAsPlayed(devCard);

        assertTrue(player.getDevelopmentCards().isEmpty());

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(1, unplayableCards.size());
        assertTrue(unplayableCards.contains(devCard));
    }

    @ParameterizedTest
    @EnumSource(names = {"KNIGHT", "MONOPOLY",
            "PLENTY", "ROAD"})
    public void testPlayer_setDevelopmentCardAsPlayed_simpleMissingCard(DevelopmentCards devCard){
        Player player = createTestPlayer();

        String actual = assertThrows(IllegalArgumentException.class,
                () -> player.setDevelopmentCardAsPlayed(devCard)).getMessage();

        String expected = "Player doesn't own any of that card";

        assertEquals(expected, actual);
    }

    @Test
    public void testPlayer_setDevelopmentCardAsPlayed_simpleMixedCard(){
        Player player = createTestPlayer();
        player.developmentCards.add(KNIGHT);
        player.developmentCards.add(DevelopmentCards.MONOPOLY);

        player.setDevelopmentCardAsPlayed(KNIGHT);

        Collection<DevelopmentCards> playableCards = player.getDevelopmentCards();
        assertEquals(1,playableCards.size());
        assertTrue(playableCards.contains(DevelopmentCards.MONOPOLY));

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(1, unplayableCards.size());
        assertTrue(unplayableCards.contains(KNIGHT));
    }

    @Test
    public void testPlayer_setDevelopmentCardAsPlayed_knightWithSingleUnplayableCard(){
        Player player = createTestPlayer();

        player.developmentCards.add(KNIGHT);
        player.unplayableDevelopmentCards.add(KNIGHT);


        player.setDevelopmentCardAsPlayed(KNIGHT);


        Collection<DevelopmentCards> playableCards = player.getDevelopmentCards();
        assertTrue(playableCards.isEmpty());

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(2, unplayableCards.size());
        for(int i = 0; i < 2; i++){
            assertTrue(unplayableCards.remove(KNIGHT));
        }
    }

    @Test
    public void testPlayer_setDevelopmentCardAsPlayed_knightWithTwoUnplayableCard(){
        Player player = createTestPlayer();

        player.developmentCards.add(KNIGHT);
        player.unplayableDevelopmentCards.add(KNIGHT);
        player.unplayableDevelopmentCards.add(KNIGHT);


        player.setDevelopmentCardAsPlayed(KNIGHT);


        Collection<DevelopmentCards> playableCards = player.getDevelopmentCards();
        assertTrue(playableCards.isEmpty());

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(3, unplayableCards.size());
        for(int i = 0; i < 3; i++){
            assertTrue(unplayableCards.remove(KNIGHT));
        }
    }

    @Test
    public void testPlayer_setDevelopmentCardAsPlayed_knightWithMaxUnplayableKnightCard(){
        Player player = createTestPlayer();

        player.developmentCards.add(KNIGHT);
        for(int i = 0; i < 13; i++){
            player.unplayableDevelopmentCards.add(KNIGHT);
        }

        player.setDevelopmentCardAsPlayed(KNIGHT);


        Collection<DevelopmentCards> playableCards = player.getDevelopmentCards();
        assertTrue(playableCards.isEmpty());

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(14, unplayableCards.size());
        for(int i = 0; i < 14; i++){
            assertTrue(unplayableCards.remove(KNIGHT));
        }
    }

    @ParameterizedTest
    @EnumSource(names = {"KNIGHT", "MONOPOLY",
            "PLENTY", "ROAD"})
    public void testPlayer_addDevelopmentCard_simplePlayableCard(DevelopmentCards devCard){
        Player player = createTestPlayer();


        player.addDevelopmentCard(devCard);


        Collection<DevelopmentCards> playableCards = player.getFutureDevelopmentCards();
        assertEquals(1,playableCards.size());
        assertTrue(playableCards.remove(devCard));

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertTrue(unplayableCards.isEmpty());

    }

    @Test
    public void testPlayer_addDevelopmentCard_simpleUnplayableCard(){
        Player player = createTestPlayer();

        player.addDevelopmentCard(VICTORY);


        Collection<DevelopmentCards> playableCards = player.getFutureDevelopmentCards();
        assertTrue(playableCards.isEmpty());

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(1,unplayableCards.size());
        assertTrue(unplayableCards.remove(VICTORY));

    }

    @Test
    public void testPlayer_addDevelopmentCard_knightWithPlayableKnightCard(){
        Player player = createTestPlayer();

        player.futureDevelopmentCards.add(KNIGHT);

        player.addDevelopmentCard(KNIGHT);


        Collection<DevelopmentCards> playableCards = player.getFutureDevelopmentCards();
        assertEquals(2,playableCards.size());
        assertTrue(playableCards.remove(KNIGHT));
        assertTrue(playableCards.remove(KNIGHT));

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertTrue(unplayableCards.isEmpty());

    }

    @Test
    public void testPlayer_addDevelopmentCard_knightWithPlayableMixCards(){
        Player player = createTestPlayer();

        player.futureDevelopmentCards.add(KNIGHT);
        player.futureDevelopmentCards.add(ROAD);

        player.addDevelopmentCard(KNIGHT);


        Collection<DevelopmentCards> playableCards = player.getFutureDevelopmentCards();
        assertEquals(3,playableCards.size());
        assertTrue(playableCards.remove(KNIGHT));
        assertTrue(playableCards.remove(KNIGHT));
        assertTrue(playableCards.remove(ROAD));

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertTrue(unplayableCards.isEmpty());

    }

    @Test
    public void testPlayer_addDevelopmentCard_knightWithPlayableMaxCards(){
        Player player = createTestPlayer();

        for(int i = 0; i < 13; i++){
            player.futureDevelopmentCards.add(KNIGHT);
        }

        player.addDevelopmentCard(KNIGHT);

        Collection<DevelopmentCards> playableCards = player.getFutureDevelopmentCards();
        assertEquals(14,playableCards.size());
        for(int i = 0; i < 13; i++){
            assertTrue(playableCards.remove(KNIGHT));
        }

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertTrue(unplayableCards.isEmpty());

    }

    @Test
    public void testPlayer_addDevelopmentCard_VPWithUnplayableVPCard(){
        Player player = createTestPlayer();

        player.unplayableDevelopmentCards.add(VICTORY);

        player.addDevelopmentCard(VICTORY);

        Collection<DevelopmentCards> playableCards = player.getFutureDevelopmentCards();
        assertTrue(playableCards.isEmpty());

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(2,unplayableCards.size());
        for(int i = 0; i < 2; i++){
            assertTrue(unplayableCards.remove(VICTORY));
        };

    }

    @Test
    public void testPlayer_addDevelopmentCard_VPWithUnplayableMixCards(){
        Player player = createTestPlayer();

        player.unplayableDevelopmentCards.add(VICTORY);
        player.unplayableDevelopmentCards.add(KNIGHT);

        player.addDevelopmentCard(VICTORY);

        Collection<DevelopmentCards> playableCards = player.getFutureDevelopmentCards();
        assertTrue(playableCards.isEmpty());

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(3,unplayableCards.size());
        for(int i = 0; i < 2; i++){
            assertTrue(unplayableCards.remove(VICTORY));
        }
        assertTrue(unplayableCards.remove(KNIGHT));

    }

    @Test
    public void testPlayer_addDevelopmentCard_VPWithUnplayableMaxCards(){
        Player player = createTestPlayer();

        for(int i = 0; i < 4;i++){
            player.unplayableDevelopmentCards.add(VICTORY);

        }


        player.addDevelopmentCard(VICTORY);

        Collection<DevelopmentCards> playableCards = player.getFutureDevelopmentCards();
        assertTrue(playableCards.isEmpty());

        Collection<DevelopmentCards> unplayableCards = player.getUnplayableDevelopmentCards();
        assertEquals(5,unplayableCards.size());
        for(int i = 0; i < 5; i++){
            assertTrue(unplayableCards.remove(VICTORY));
        }

    }

    @Test
    public void testPlayer_startTurn_empty() {
        Player player = createTestPlayer();

        player.startTurn();

        assertTrue(player.futureDevelopmentCards.isEmpty());
        assertEquals(0,player.developmentCards.size());
    }

    @Test
    public void testPlayer_startTurn_oneKnight() {
        Player player = createTestPlayer();

        player.addDevelopmentCard(KNIGHT);

        player.startTurn();

        assertTrue(player.futureDevelopmentCards.isEmpty());
        assertEquals(1,player.developmentCards.size());
    }

    @Test
    public void testPlayer_startTurn_twoKnight() {
        Player player = createTestPlayer();

        player.addDevelopmentCard(KNIGHT);
        player.addDevelopmentCard(KNIGHT);

        player.startTurn();

        assertTrue(player.futureDevelopmentCards.isEmpty());
        assertEquals(2,player.developmentCards.size());
    }

    @Test
    public void testPlayer_startTurn_maxCard() {
        Player player = createTestPlayer();

        for(int i = 0; i < 14; i++){
            player.addDevelopmentCard(KNIGHT);
        }
        for(int i = 0; i < 2; i++){
            player.addDevelopmentCard(MONOPOLY);
            player.addDevelopmentCard(PLENTY);
            player.addDevelopmentCard(ROAD);
        }

        player.startTurn();

        assertTrue(player.futureDevelopmentCards.isEmpty());
        assertEquals(20,player.developmentCards.size());
    }

    @Test
    public void testPlayer_getDevCardPlayed(){
        Player player = createTestPlayer();
        assertFalse(player.isDevCardPlayed());
    }

    @Test
    public void testPlayer_getAndSetDevCardPlayed(){
        Player player = createTestPlayer();
        player.setDevCardPlayed();
        assertTrue(player.isDevCardPlayed());
    }

    @ParameterizedTest
    @MethodSource("colorParameters")
    public void testPlayer_getPlayerColor(Color color){
        Player player = new Player(color, "todd", new ArrayList<>());

        Color returnedColor = player.getPlayerColor();
        assertEquals(color, returnedColor);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "test", "test2"})
    public void testPlayer_getPlayerName(String name){
        Player player = new Player(Color.RED,name, new ArrayList<>());

        String returnedName = player.getPlayerName();
        assertEquals(name, returnedName);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10})
    public void testPlayer_getAndSetNumVictoryPoints(int numPoints){
        Player player = createTestPlayer();

        player.setVictoryPoints(numPoints);

        assertEquals(numPoints, player.victoryPoints);
        assertEquals(numPoints, player.getVictoryPoints());

    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10})
    public void testPlayer_setNumVictoryPoints(int numPoints){
        Player player = createTestPlayer();

        player.setVictoryPoints(numPoints);

        assertEquals(numPoints, player.victoryPoints);
        assertEquals(numPoints, player.getVictoryPoints());

    }


    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11,12,13})
    public void testPlayer_getAndSetNumRoads(int numRoads){
        Player player = createTestPlayer();

        player.setNumRoads(numRoads);

        assertEquals(numRoads, player.numRoads);
        assertEquals(numRoads, player.getNumRoads());

    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    public void testPlayer_getAndSetNumSettlements(int numSettlements){
        Player player = createTestPlayer();

        player.setNumSettlements(numSettlements);

        assertEquals(numSettlements, player.numSettlements);
        assertEquals(numSettlements, player.getNumSettlements());
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4})
    public void testPlayer_getAndSetNumCities(int numCities){
        Player player = createTestPlayer();

        player.setNumCities(numCities);

        assertEquals(numCities, player.numCities);
        assertEquals(numCities, player.getNumCities());
    }

    @ParameterizedTest
    @EnumSource
    public void testHasDevelopmentCard_hasCard(DevelopmentCards card){
        Player player = createTestPlayer();
        player.developmentCards.add(card);

        assertTrue(player.hasDevelopmentCard(card));
    }

    @ParameterizedTest
    @EnumSource
    public void testHasDevelopmentCard_hasNoCard(DevelopmentCards card){
        Player player = createTestPlayer();

        assertFalse(player.hasDevelopmentCard(card));
    }

    @Test
    public void testHasDevelopmentCard_differentCards(){
        Player player = createTestPlayer();
        player.developmentCards.add(KNIGHT);

        assertFalse(player.hasDevelopmentCard(ROAD));
    }

    @ParameterizedTest
    @EnumSource
    public void testHasUnplayableDevelopmentCard_hasCard(DevelopmentCards card){
        Player player = createTestPlayer();
        player.unplayableDevelopmentCards.add(card);

        assertTrue(player.hasUnplayableDevelopmentCard(card));
    }

    @ParameterizedTest
    @EnumSource
    public void testHasUnplayableDevelopmentCard_hasNoCard(DevelopmentCards card){
        Player player = createTestPlayer();

        assertFalse(player.hasUnplayableDevelopmentCard(card));
    }

    @Test
    public void testHasUnplayableDevelopmentCard_differentCards(){
        Player player = createTestPlayer();
        player.unplayableDevelopmentCards.add(KNIGHT);

        assertFalse(player.hasUnplayableDevelopmentCard(ROAD));
    }


    private static Stream<Color> colorParameters(){
        return Stream.of(Color.RED, Color.BLUE, Color.GREEN, Color.BLACK);
    }
    
    @Test
    public void testResourcesNotBlankEmpty() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	assertFalse(player.validateResourcesNotBlank(player.resources));
    }
    
    @Test
    public void testResourcesNotBlankWithResources() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType b = ResourceType.BRICK;
    	player.addResource(b);
    	assertTrue(player.validateResourcesNotBlank(player.resources));
    }
    
    @Test
    public void testGetEnoughForRoadBlank() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	assertFalse(player.getEnoughForRoad());
    }
    
    @Test
    public void testGetEnoughForRoadLumber() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType l = ResourceType.LUMBER;
    	player.addResource(l);
    	assertFalse(player.getEnoughForRoad());
    }
    
    @Test
    public void testGetEnoughForRoadBrick() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType b = ResourceType.BRICK;
    	player.addResource(b);
    	assertFalse(player.getEnoughForRoad());
    }
    
    @Test
    public void testGetEnoughForRoadEnough() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType b = ResourceType.BRICK;
    	ResourceType l = ResourceType.LUMBER;
    	player.addResource(l);
    	player.addResource(b);
    	assertTrue(player.getEnoughForRoad());
    }
    
    @Test
    public void testGetEnoughForSettlementBlank() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	assertFalse(player.getEnoughForSettlement());
    }
    
    @Test
    public void testGetEnoughForSettlementLumberMissing() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType b = ResourceType.BRICK;
    	ResourceType w = ResourceType.WOOL;
    	ResourceType g = ResourceType.GRAIN;
    	player.addResource(b);
    	player.addResource(w);
    	player.addResource(g);
    	assertFalse(player.getEnoughForSettlement());
    }
    
    @Test
    public void testGetEnoughForSettlementBrickMissing() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType l = ResourceType.LUMBER;
    	ResourceType w = ResourceType.WOOL;
    	ResourceType g = ResourceType.GRAIN;
    	player.addResource(l);
    	player.addResource(w);
    	player.addResource(g);
    	assertFalse(player.getEnoughForSettlement());
    }
    
    @Test
    public void testGetEnoughForSettlementWoolMissing() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType b = ResourceType.BRICK;
    	ResourceType l = ResourceType.LUMBER;
    	ResourceType g = ResourceType.GRAIN;
    	player.addResource(b);
    	player.addResource(l);
    	player.addResource(g);
    	assertFalse(player.getEnoughForSettlement());
    }
    
    @Test
    public void testGetEnoughForSettlementGrainMissing() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType b = ResourceType.BRICK;
    	ResourceType w = ResourceType.WOOL;
    	ResourceType l = ResourceType.LUMBER;
    	player.addResource(b);
    	player.addResource(w);
    	player.addResource(l);
    	assertFalse(player.getEnoughForSettlement());
    }
    
    @Test
    public void testGetEnoughForSettlementEnough() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType b = ResourceType.BRICK;
    	ResourceType l = ResourceType.LUMBER;
    	ResourceType w = ResourceType.WOOL;
    	ResourceType g = ResourceType.GRAIN;
    	player.addResource(l);
    	player.addResource(b);
    	player.addResource(g);
    	player.addResource(w);
    	assertTrue(player.getEnoughForSettlement());
    }
        
    @Test
    public void testGetEnoughForCityBlank() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	assertFalse(player.getEnoughForCity());
    }
    
    @Test
    public void testGetEnoughForCityOreMissing() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType o = ResourceType.ORE;
    	ResourceType g = ResourceType.GRAIN;
    	player.addResource(o);
    	player.addResource(o);
    	player.addResource(g);
    	player.addResource(g);
    	assertFalse(player.getEnoughForCity());
    }
    
    @Test
    public void testGetEnoughForCityGrainMissing() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType o = ResourceType.ORE;
    	ResourceType g = ResourceType.GRAIN;
    	player.addResource(o);
    	player.addResource(o);
    	player.addResource(o);
    	player.addResource(g);
    	assertFalse(player.getEnoughForCity());
    }
    
    @Test
    public void testGetEnoughForCityEnough() {
    	Player player = new Player(Color.RED, "d", new ArrayList<>());
    	ResourceType o = ResourceType.ORE;
    	ResourceType g = ResourceType.GRAIN;
    	player.addResource(o);
    	player.addResource(o);
    	player.addResource(o);
    	player.addResource(g);
    	player.addResource(g);
    	assertTrue(player.getEnoughForCity());
    }

}
package domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class Player {
    public static final int MAX_HAND_SIZE_ROBBER = 7;
    ArrayList<DevelopmentCards> futureDevelopmentCards;
    Color playerColor;
    String playerName;

    final static int maxResources = 95;

    final static int SETTLEMENTS_AFTER_SETUP = 3;

    final static int CITIES_AFTER_SETUP = 4;

    final static int ROADS_AFTER_SETUP = 13;

    private static final int MAX_RESOURCES = 95;
    Collection<ResourceType> resources;
    Collection<DevelopmentCards> developmentCards;
    ArrayList<DevelopmentCards> unplayableDevelopmentCards;

    int numSettlements = SETTLEMENTS_AFTER_SETUP;

    int numCities = CITIES_AFTER_SETUP;

    int numRoads = ROADS_AFTER_SETUP;

    int victoryPoints = 2;

    private boolean devCardPlayed = false;
    private int playedKnightCount = 0;

    public Player(Color c, String name, Collection<ResourceType> col) {
        this.playerColor = c;
        this.playerName = name;
        initializeEmptyCollections(col);
    }

    private void initializeEmptyCollections(Collection<ResourceType> col) {
        this.resources = new ArrayList<>(col);
        this.developmentCards = new ArrayList<>();
        this.unplayableDevelopmentCards = new ArrayList<>();
        this.futureDevelopmentCards = new ArrayList<>();
    }

    public Collection<DevelopmentCards> getFutureDevelopmentCards(){
        return new ArrayList<>(futureDevelopmentCards);
    }

    public void startTurn(){
        while(!futureDevelopmentCards.isEmpty()){
            developmentCards.add(futureDevelopmentCards.remove(0));
        }
        devCardPlayed = false;
    }

    public void setDevCardPlayed(){
        devCardPlayed = true;
    }

    public boolean isDevCardPlayed(){
        return devCardPlayed;
    }

    public ArrayList<ResourceType> getResources() {
        return new ArrayList<>(resources);
    }

    public void addResource(ResourceType resource) {
        if (resources.size() == MAX_RESOURCES) {
            throw new IllegalStateException("Maximum resources reached, cannot add more");
        }
        resources.add(resource);
    }

    public void setDevelopmentCardAsPlayed(DevelopmentCards devCard) {
        if(!developmentCards.contains(devCard)){
            throw new IllegalArgumentException("Player doesn't own any of that card");
        }
        developmentCards.remove(devCard);
        unplayableDevelopmentCards.add(devCard);
    }

    public boolean hasDevelopmentCard(DevelopmentCards card){
        return developmentCards.contains(card);
    }

    public boolean hasUnplayableDevelopmentCard(DevelopmentCards card){
        return unplayableDevelopmentCards.contains(card);
    }




    public void addDevelopmentCard(DevelopmentCards devCard) {
        if(devCard.equals(DevelopmentCards.VICTORY)){
            unplayableDevelopmentCards.add(devCard);
        }else{
            futureDevelopmentCards.add(devCard);
        }
    }

    public Collection<DevelopmentCards> getDevelopmentCards() {
        return new ArrayList<>(developmentCards);
    }

    public Collection<DevelopmentCards> getUnplayableDevelopmentCards() {
        return new ArrayList<>(unplayableDevelopmentCards);
    }

    public int checkNumResource() {
        return resources.size();
    }

    public boolean removeResource(ResourceType resource) {
        return resources.remove(resource);
    }

    public boolean hasResources(Collection<ResourceType> resourcesToCheck) {
        Collection<ResourceType> resourcesDuplicate = new ArrayList<>(resources);
        if (!validateResourcesNotBlank(resourcesToCheck)) return false;
        for (ResourceType resourceType : resourcesToCheck) {
            if (!resourcesDuplicate.remove(resourceType))   return false;
        }
        return true;
    }

    public boolean validateResourcesNotBlank(Collection<ResourceType> resourcesToCheck) {
        if (resourcesToCheck.size() <= 0) {
            return false;
        }
        return true;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public String getPlayerName() {
        return playerName;
    }


    public int getNumOwnedResource(ResourceType resourceType) {
        int numRemoved = 0;
        Collection<ResourceType> resourceDupe = new ArrayList<>(resources);
        while(resourceDupe.remove(resourceType)) numRemoved++;
        return numRemoved;
    }
    

    public void setVictoryPoints(int victoryPoints){
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints(){
        return victoryPoints;
    }

    public int getNumRoads(){
        return numRoads;
    }

    public int getNumCities(){
        return numCities;
    }

    public int getNumSettlements(){
        return numSettlements;
    }

    public void setNumRoads(int numRoads){
        this.numRoads = numRoads;
    }

    public void setNumCities(int numCities){
        this.numCities = numCities;
    }

    public void setNumSettlements(int numSettlements){
        this.numSettlements = numSettlements;
    }

    public void incrementPlayedKnightCount() {
        this.playedKnightCount++;
    }

    public int getPlayedKnightCount() {
        return playedKnightCount;
    }

    public void resetPlayedKnightCount() {
        this.playedKnightCount = 0;
    }
    
    public boolean getEnoughForSettlement() {
    	return resources.contains(ResourceType.BRICK)
    			&& resources.contains(ResourceType.LUMBER)
    			&& resources.contains(ResourceType.WOOL)
    			&& resources.contains(ResourceType.GRAIN);
    }
    
    public boolean getEnoughForRoad() {
    	return resources.contains(ResourceType.BRICK)
    			&& resources.contains(ResourceType.LUMBER);
    }
    
    public boolean getEnoughForCity() {
    	int grain = 0;
    	int ore = 0;
    	for (int i = 0; i < resources.size(); i++) {
    		if (((ArrayList) resources).get(i) == ResourceType.GRAIN) {
    			grain++;
    		} else if (((ArrayList) resources).get(i) == ResourceType.ORE) {
    			ore++;
    		}
    	}
    	return grain >=2 && ore >= 3;
    }
}
package domain;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardSteps {

    ResourceType resourceOne;
    ResourceType resourceTwo;

    int[] road1Indexes = new int[2];
    int[] road2Indexes = new int[2];

    boolean result;

    String exceptionMessage;

    PlayerTrade playerTradeSteps;


    public DevelopmentCardSteps(PlayerTrade playerTradeSteps){
        this.playerTradeSteps = playerTradeSteps;
    }


    @Given("Player {int} has started their turn in a {int} person game")
    public void player_has_started_their_turn_in_a_person_game(Integer playerInTurn, Integer numPlayers) {
        playerTradeSteps.initNumPlayers(numPlayers);
        int playerInTurnIndex = playerInTurn - 1;
        playerTradeSteps.gameManager.setInTurnPlayer(playerInTurnIndex);
    }

    @Given("player {int} has all resources except {string}")
    public void player_has_all_resources_except(int playerNum, String resources) {
        List<ResourceType> resourceList = playerTradeSteps.parseResources(resources);
        Collection<ResourceType> maxResourceList = getMaxResources();
        maxResourceList.removeAll(resourceList);
        playerTradeSteps.gameManager.decrementResourcesFromBank(maxResourceList);

        int playerIndex = playerNum - 1;

        addResourcesToPlayer(playerTradeSteps.players[playerIndex], maxResourceList);

    }

    private void addResourcesToPlayer(Player player, Collection<ResourceType> resources){
        for(ResourceType resource: resources){
            player.addResource(resource);
        }
    }

    private Collection<ResourceType> getMaxResources() {
        ArrayList<ResourceType> resources = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            for (ResourceType resource : ResourceType.values()) {
                resources.add(resource);
            }
        }
        return resources;
    }

    @When("player activates a Year of Plenty for {string} and {string}")
    public void player_activates_a_Year_of_Plenty_for_and(String resource1,
        String resource2) {
        resourceOne = ResourceType.valueOf(resource1);
        resourceTwo = ResourceType.valueOf(resource2);

        playerTradeSteps.gameManager.playYearOfPlenty(resourceOne,resourceTwo);
    }

    @When("player activates a Monopoly for {string}")
    public void player_activates_a_Monopoly_for(String resource) {
        resourceOne = ResourceType.valueOf(resource);
        playerTradeSteps.gameManager.playMonopolyCard(resourceOne);
    }

    @When("the player selects the first road to be placed at {int} and {int}")
    public void the_player_selects_the_first_road_to_be_placed_at_and(Integer ind1, Integer ind2) {
        road1Indexes[0] = ind1;
        road1Indexes[1] = ind2;
    }

    @When("the player selects the second road to be placed at {int} and {int}")
    public void playerSelectsTheSecondRoadToBePlacedAtIndexAndIndex(int ind1, int ind2) {
        road2Indexes[0] = ind1;
        road2Indexes[1] = ind2;
    }

    @And("player activates a Road Building card")
    public void playerActivatesARoadBuildingCard() {
        int[][] roadIndexes = {road1Indexes, road2Indexes};
        result = playerTradeSteps.gameManager.playRoadBuildingCard(roadIndexes);
    }

    @And("the roads should be placed on the board")
    public void theRoadsShouldBePlacedOnTheBoard() {
        assertTrue(result);
    }

    @And("the road building card should be marked as played")
    public void theRoadBuildingCardShouldBeMarkedAsPlayed() {
        playerTradeSteps.gameManager.inTurn.setDevelopmentCardAsPlayed(DevelopmentCards.ROAD);
        assertTrue(playerTradeSteps.gameManager.inTurn.hasUnplayableDevelopmentCard(DevelopmentCards.ROAD));
    }
    @When("player {int} activates the knight card and moves the robber to hex index {int}")
    public void player_activates_the_knight_card_and_moves_the_robber_to_hex_index(Integer int1, Integer int2) {
        try{
            result =
                playerTradeSteps.gameManager.playKnight(playerTradeSteps.gameManager.players[1], int2);
        } catch (Exception e){
            exceptionMessage = e.getMessage();
        }

    }

    @When("player {int} activates the knight card and moves the robber to hex {int} where there is no player")
    public void playerActivatesTheKnightCardAndMovesTheRobberToHexWhereThereIsNoPlayer(int arg0,
        int arg1) {
        try{
            result =
                playerTradeSteps.gameManager.playKnight(null, arg1);
        } catch (Exception e){
            exceptionMessage = e.getMessage();
        }
    }


    @Then("the robber should be at hex index {int}")
    public void theRobberShouldBeAtHexIndex(int arg0) {
        assertEquals(playerTradeSteps.gameManager.getRobberLocation(), arg0);
    }

    @Given("player {int} has a knight card")
    public void player_has_a_knight_card(Integer int1) {
        playerTradeSteps.gameManager.players[int1 - 1].developmentCards.add(DevelopmentCards.KNIGHT);
    }

    @And("player {int} has a Road Building Card")
    public void playerHasARoadBuildingCard(int player) {
        playerTradeSteps.players[player-1].developmentCards.add(DevelopmentCards.ROAD);
    }

    @And("the roads should not be placed on the board")
    public void theRoadsShouldNotBePlacedOnTheBoard() {
        assertFalse(result);
    }

    @And("the road building card should not be marked as played")
    public void theRoadBuildingCardShouldNotBeMarkedAsPlayed() {
        assertFalse(playerTradeSteps.gameManager.inTurn.hasUnplayableDevelopmentCard(DevelopmentCards.ROAD));
    }
    @And("player {int} has no knight cards")
    public void playerHasNoKnightCards(int arg0) {
        assertEquals(playerTradeSteps.gameManager.players[arg0 - 1].getDevelopmentCards().size(), 0);
    }

    @And("player {int} should have the same resources as before")
    public void playerShouldHaveTheSameResourcesAsBefore(int arg0) {
        if(arg0 == 1){
            assertEquals(playerTradeSteps.gameManager.players[0].getResources().size(), 4);
        } else {
            assertEquals(playerTradeSteps.gameManager.players[1].getResources().size(), 2);
        }
    }

    @And("an exception for no knight cards should be thrown")
    public void anExceptionForNoKnightCardsShouldBeThrown() {
        assertEquals(exceptionMessage, "Player does not have a knight card");
    }

    @And("a no resources exception should be thrown")
    public void aNoResourcesExceptionShouldBeThrown() {
        assertFalse(result);
    }
    @Then("a no player to steal from exception should be thrown")
    public void a_no_player_to_steal_from_exception_should_be_thrown() {
        assertEquals(exceptionMessage, "Hex has players and none specified");
    }


}

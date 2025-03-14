package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuyingRoad {

    private final PlayerTrade playerTradeSteps;

    private boolean roadPlaced;

    public BuyingRoad(PlayerTrade playerTradeSteps) {
        this.playerTradeSteps = playerTradeSteps;
    }


    @When("player {int} attempts to buy a road at indexes {int} and {int}")
    public void player_attempts_to_buy_a_road_at_indexes_and(Integer int1, Integer index1,
        Integer index2) {
        roadPlaced = playerTradeSteps.gameManager.buildRoad(index1, index2,
            playerTradeSteps.gameManager.inTurn);
    }
    @Then("the road should be placed at the indexes")
    public void the_road_should_be_placed_at_the_indexes() {
        assertTrue(roadPlaced);
    }

    @Then("the road should not be placed")
    public void theRoadShouldNotBePlaced() {
        assertFalse(roadPlaced);
    }

    @And("the in-turn player does not own the connecting road")
    public void theInTurnPlayerDoesNotOwnTheConnectingRoad() {
        playerTradeSteps.gameManager.placeRoad(11, 29,
            playerTradeSteps.gameManager.getPlayers()[1], false);
    }


    @And("the in-turn player numRoads is greater than zero")
    public void theInTurnPlayerNumRoadsIsGreaterThanZero() {
        assertTrue(playerTradeSteps.gameManager.getPlayers()[0].getNumRoads() > 0);
    }

    @And("the in-turn player numRoads is zero")
    public void theInTurnPlayerNumRoadsIsZero() {
        playerTradeSteps.gameManager.getPlayers()[0].setNumRoads(0);
    }
}

package domain;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class BuyingSettlement {

    private boolean result;
    private final PlayerTrade playerTradeSteps;

    public BuyingSettlement(PlayerTrade playerTradeSteps){
        this.playerTradeSteps = playerTradeSteps;
    }


    @When("player {int} attempts to buy a settlement at index {int}")
    public void player_attempts_to_buy_a_settlement_at_index(Integer int1, Integer index) {
        setUpSuccessful();
        result = playerTradeSteps.gameManager.buildSettlement(index,
                playerTradeSteps.gameManager.inTurn);

    }

    private void setUp() {
        playerTradeSteps.gameManager.boardManager.initializeBoardStructure(false);
        //set initial settlements
        playerTradeSteps.gameManager.placeInitialSettlement(0,
            playerTradeSteps.gameManager.getPlayers()[0]);
        playerTradeSteps.gameManager.placeInitialSettlement(5,
            playerTradeSteps.gameManager.getPlayers()[1]);
        playerTradeSteps.gameManager.placeInitialSettlement(10,
            playerTradeSteps.gameManager.getPlayers()[0]);
        playerTradeSteps.gameManager.placeInitialSettlement(13,
            playerTradeSteps.gameManager.getPlayers()[1]);

        //set initial roads
        playerTradeSteps.gameManager.placeRoad(0, 6,
            playerTradeSteps.gameManager.getPlayers()[0], false);
        playerTradeSteps.gameManager.placeRoad(5, 11,
            playerTradeSteps.gameManager.getPlayers()[1], false);
        playerTradeSteps.gameManager.placeRoad(10, 4,
            playerTradeSteps.gameManager.getPlayers()[0], false);
        playerTradeSteps.gameManager.placeRoad(5, 19,
            playerTradeSteps.gameManager.getPlayers()[1], false);
    }

    private void setUpSuccessful() {
        playerTradeSteps.gameManager.placeRoad(6, 24,
            playerTradeSteps.gameManager.getPlayers()[0], false);
    }

    @Then("the settlement should be placed at the index")
    public void theSettlementShouldBePlacedAtTheIndex() {
        assertTrue(result);
    }

    @And("the initial setup has been completed")
    public void theInitialSetupHasBeenCompleted() {
        setUp();
    }

    @Then("the settlement should not be placed at the index")
    public void theSettlementShouldNotBePlacedAtTheIndex() {
        assertFalse(result);
    }


    @And("the in-turn player does not own the road at the index of the settlement")
    public void theInTurnPlayerDoesNotOwnTheRoadAtTheIndexOfTheSettlement() {
        setUpWrongOwner();
    }

    private void setUpWrongOwner() {
        playerTradeSteps.gameManager.placeRoad(11, 29,
            playerTradeSteps.gameManager.getPlayers()[1], false);
    }

    @And("the players number of settlements is greater than zero")
    public void thePlayersNumberOfSettlementsIsGreaterThanZero() {
        assertTrue(playerTradeSteps.gameManager.getPlayers()[0].getNumSettlements() > 0);
    }

    @And("the players number of settlements is zero")
    public void thePlayersNumberOfSettlementsIsZero() {
        playerTradeSteps.gameManager.getPlayers()[0].setNumSettlements(0);
    }
}

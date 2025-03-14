package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuyingCity {

    private PlayerTrade playerTradeSteps;

    private boolean cityBuilt;

    public BuyingCity(PlayerTrade playerTrade) {
        this.playerTradeSteps = playerTrade;
    }

    @When("the in-turn player attempts to buy a city at the index {int}")
    public void the_in_turn_player_attempts_to_buy_a_city_at_the_index(Integer index) {
        cityBuilt = playerTradeSteps.gameManager.buildCity(index, playerTradeSteps.gameManager.inTurn);
    }

    @When("there is a settlement that is owned by the in-turn player at {int}")
    public void there_is_a_settlement_that_is_owned_by_the_in_turn_player_at(Integer index) {
        setUpSettlementToUpgrade(index);
    }

    private void setUpSettlementToUpgrade(int index) {
        playerTradeSteps.gameManager.placeInitialSettlement(index, playerTradeSteps.gameManager.inTurn);
    }

    @Then("the city will replace the settlement at the location")
    public void the_city_will_replace_the_settlement_at_the_location() {
        assertTrue(cityBuilt);
    }

    @Then("the city will not replace the settlement at the location")
    public void theCityWillNotReplaceTheSettlementAtTheLocation() {
        assertFalse(cityBuilt);
    }

    @When("there is a not a settlement that is owned by the in-turn player at {int}")
    public void there_is_a_not_a_settlement_that_is_owned_by_the_in_turn_player_at(Integer int1) {
        setUpWrongOwnerSettlementToUpgrade(int1);
    }

    private void setUpWrongOwnerSettlementToUpgrade(int index) {
        playerTradeSteps.gameManager.placeInitialSettlement(index,
            playerTradeSteps.gameManager.getPlayers()[1]);
    }

    @And("the players numCities is greater than zero")
    public void thePlayersNumCitiesIsGreaterThanZero() {
        assertTrue(playerTradeSteps.gameManager.getPlayers()[0].getNumCities() > 0);
    }

    @When("there are no cities left")
    public void thereAreNoCitiesLeft() {
        playerTradeSteps.gameManager.getPlayers()[0].setNumCities(0);
    }
}

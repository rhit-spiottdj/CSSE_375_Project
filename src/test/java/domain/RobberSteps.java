package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RobberSteps {

    private PlayerTrade playerTradeSteps;

    private boolean resourcesDiscarded;


    private ResourceType resource;


    private String exception;
    public RobberSteps(PlayerTrade playerTradeSteps) {
        this.playerTradeSteps = playerTradeSteps;
    }

    @Given("the robber is on hex index {int}")
    public void the_robber_is_on_hex_index(Integer int1) {
      assertEquals(playerTradeSteps.gameManager.getRobberLocation(), int1);
    }

    @When("player {int} attempts to move the robber to hex index {int}")
    public void player_attempts_to_move_the_robber_to_hex_index(Integer int1, Integer location) {
        try {
            playerTradeSteps.gameManager.moveRobber(location);
        } catch (Exception e) {
            exception = e.getMessage();
        }
    }

    @Then("the robber should be on hex index {int}")
    public void the_robber_should_be_on_hex_index(Integer int1) {
        assertEquals(playerTradeSteps.gameManager.getRobberLocation(), int1);
    }

    @And("an invalid index message should be caught")
    public void anInvalidIndexMessageShouldBeCaught() {
        assertEquals("Invalid Hex Index", exception);
    }

    @Then("the robber should not be on hex index {int}")
    public void the_robber_should_not_be_on_hex_index(Integer int1) {
        assertTrue(playerTradeSteps.gameManager.getRobberLocation() != int1);
    }

    @And("a same index message should be caught")
    public void aSameIndexMessageShouldBeCaught() {
        assertEquals("Robber is already on this hex", exception);
    }

    @Then("player {int} should attempt to steal from player {int}")
    public void player_should_steal_from_player(Integer int1, Integer int2) {
        Player player1 = playerTradeSteps.gameManager.inTurn;
        Player player2 = playerTradeSteps.gameManager.players[int2 - 1];
        try {
            resource = playerTradeSteps.gameManager.tryRobberSteal(player1, player2);
        } catch (Exception e) {
            exception = e.getMessage();
        }
        if(resource == null) {
            exception = "Player has no resources to steal";
        }
    }

    @Then("player {int} should have {int} more resource than before")
    public void player_should_have_more_resource_than_before(Integer int1, Integer int2) {
        assertEquals(5, playerTradeSteps.gameManager.inTurn.getResources().size());
    }
    @Then("player {int} should have {int} less resource than before")
    public void player_should_have_less_resource_than_before(Integer int1, Integer int2) {
        assertEquals(1, playerTradeSteps.gameManager.players[int1 - 1].getResources().size());
    }

    @Then("player {int} should have the same amount of resources as before")
    public void player_should_have_the_same_amount_of_resources_as_before(Integer int1) {
        if(int1 == 1) {
            assertEquals(4, playerTradeSteps.gameManager.inTurn.getResources().size());
        }
        else {
            assertEquals(0, playerTradeSteps.gameManager.players[int1 - 1].getResources().size());
        }
    }

    @Then("a no resources message should be caught")
    public void a_no_resources_message_should_be_caught() {
        assertEquals("Player has no resources to steal", exception);
    }


    @When("a {int} is rolled")
    public void aIsRolled(int arg0) {

    }

    @Then("player {int} should discard half of their resources")
    public void playerShouldDiscardHalfOfTheirResources(int arg0) {
        Collection<ResourceType> resToDiscard = new ArrayList<>();
        resToDiscard.add(ResourceType.BRICK);
        resToDiscard.add(ResourceType.LUMBER);
        resToDiscard.add(ResourceType.GRAIN);
        resToDiscard.add(ResourceType.WOOL);

        playerTradeSteps.gameManager.decrementResourcesFromBank(resToDiscard);

            resourcesDiscarded =
                playerTradeSteps.gameManager.playerDiscardResources(playerTradeSteps.gameManager.inTurn,
                resToDiscard);
    }

    @Then("player {int} should have {int} resources")
    public void player_should_have_resources(Integer int1, Integer int2) {
       if(int1 == 1){
              assertEquals(int2, playerTradeSteps.gameManager.inTurn.getResources().size());
         }
         else {
              assertEquals(int2,
                  playerTradeSteps.gameManager.players[int1 - 1].getResources().size());
       }
    }


    @And("no resources should be discarded")
    public void noResourcesShouldBeDiscarded() {
        assertFalse(resourcesDiscarded);
    }

    @And("a no player message should be caught")
    public void aNoPlayerMessageShouldBeCaught() {
        assertEquals("No player to steal from", exception);
    }

    @Then("player {int} should attempt to steal from no player")
    public void playerShouldAttemptToStealFromNoPlayer(int arg0) {
        try {
            resource = playerTradeSteps.gameManager.tryRobberSteal(playerTradeSteps.gameManager.inTurn, null);
        } catch (Exception e) {
            exception = e.getMessage();
        }
    }
}

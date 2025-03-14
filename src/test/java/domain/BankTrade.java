package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class BankTrade {

    private GameManager gameManager;
    private Player player;

    private Port port;

    @Given("the game is set up for bank trading")
    public void the_game_is_set_up_for_bank_trading() {
        gameManager = new GameManager(2);
        gameManager.boardManager.initializeBoardStructure(false);
        player = new Player(Color.RED, "Player", new ArrayList<>());
        gameManager.setPlayer(0, player);
    }

    @Given("the bank does not have sufficient {string}")
    public void the_bank_does_not_have_sufficient(String resource) {
        gameManager.bank.setBankResource(ResourceType.valueOf(resource), 0);
    }

    @Given("the player has {int} {string}")
    public void the_player_has(Integer quantity, String resource) {
        for(int i = 0; i < quantity; i++){
            player.addResource(ResourceType.valueOf(resource));
        }
    }

    @Given("the player does not have resources")
    public void the_player_does_not_have() {
        for(ResourceType resource: player.getResources()) player.removeResource(resource);
    }

    @When("the player attempts to trade {int} {string} for {string}")
    public void the_player_attempts_to_trade(Integer giveQuantity, String giveResource, String takeResource) {
        gameManager.setInTurnPlayer(0);
        if (gameManager.currentPlayerHasSufficientResources(Collections.nCopies(giveQuantity,
            ResourceType.valueOf(giveResource)))) {
            gameManager.playerTradeWithBank(ResourceType.valueOf(giveResource), ResourceType.valueOf(takeResource), giveQuantity);
        }
    }

    @Then("the player successfully receives {int} {string} from the bank")
    public void the_player_successfully_receives_from_the_bank(Integer receiveQuantity, String resource) {
        assertEquals(receiveQuantity.intValue(), player.getNumOwnedResource(ResourceType.valueOf(resource)));
    }

    @Then("the player doesnt receive {int} {string} from the bank")
    public void the_trade_with_the_bank_should_fail(Integer receiveQuantity, String resource) {
        assertNotEquals(receiveQuantity.intValue(),
            player.getNumOwnedResource(ResourceType.valueOf(resource)));
    }

    @Then("the player still has {int} {string}")
    public void the_player_still_has(Integer quantity, String resource) {
        assertEquals(quantity.intValue(), player.getNumOwnedResource(ResourceType.valueOf(resource)));
    }

    @Then("the bank still has {int} {string}")
    public void the_bank_still_has(Integer quantity, String resource) {
        assertEquals(quantity.intValue(),
            gameManager.bank.getBankResource(ResourceType.valueOf(resource)));
    }

    @Then("the bank has {int} - {int} {string}")
    public void the_bank_has_minus(Integer oldQuantity, Integer quantity, String resource) {
        assertEquals(oldQuantity - quantity,
            gameManager.bank.getBankResource(ResourceType.valueOf(resource)));
    }

    @Then("the bank has {int} + {int} {string}")
    public void the_bank_has_plus(Integer oldQuantity, Integer quantity, String resource) {
        assertEquals(oldQuantity + quantity,
            gameManager.bank.getBankResource(ResourceType.valueOf(resource)));
    }

    @And("the player has a three port")
    public void thePlayerHasAThreePort() {
        Settlement settlement = new Settlement();
        settlement.setOwner(player);
        port = new Port(PortTradeRatio.THREE_TO_ONE,
            ResourceType.GRAIN);
        gameManager.boardManager.intersections[20].setStructure(settlement);
        gameManager.boardManager.intersections[20].setPort(port);
    }

    @When("the player attempts to trade {int} {string} for {string} at the port")
    public void thePlayerAttemptsToTradeGive_quantityForAtThePort(int amount, String giving
        , String taking) {
        gameManager.setInTurnPlayer(0);
        if (gameManager.currentPlayerHasSufficientResources(Collections.nCopies(amount,
            ResourceType.valueOf(giving)))) {
            gameManager.playerTradeWithPort(port,ResourceType.valueOf(giving),
                ResourceType.valueOf(taking), amount);
        }
    }

    @And("the player has a {string} two port")
    public void thePlayerHasATwoPort(String resource) {
        Settlement settlement = new Settlement();
        settlement.setOwner(player);
        port = new Port(PortTradeRatio.TWO_TO_ONE,
            ResourceType.valueOf(resource));
        gameManager.boardManager.intersections[20].setStructure(settlement);
        gameManager.boardManager.intersections[20].setPort(port);
    }
}

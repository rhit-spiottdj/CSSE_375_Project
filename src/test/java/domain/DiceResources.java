package domain;

import io.cucumber.java.en.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DiceResources {

    private GameManager gameManager;
    private BoardManager boardManager;
    private Player player1;
    private Player player2;
    private int diceRoll;

    @Given("a new game with {int} players dice")
    public void a_new_game_with_players(Integer numPlayers) {
        boardManager = new BoardManager();
        gameManager = new GameManager(numPlayers,boardManager);
        player1 = new Player(Color.RED, "Player1", new ArrayList<>());
        player2 = new Player(Color.BLUE, "Player2", new ArrayList<>());
        gameManager.setPlayer(0, player1);
        gameManager.setPlayer(1, player2);
        boardManager.initializeBoardStructure(false);
    }

    @Given("player1 has placed a settlement at intersection {int}")
    public void player1_has_placed_a_settlement_at_intersection(Integer index) {
        gameManager.placeInitialSettlement(index, player1);
    }

    @Given("player2 has placed a settlement at intersection {int}")
    public void player2_has_placed_a_settlement_at_intersection(Integer index) {
        gameManager.placeInitialSettlement(index, player2);
    }

    @Given("the dice roll is {int}")
    public void the_dice_roll_is(Integer roll) {
        diceRoll = roll;

    }

    @Given("the robber is placed on hex {int}")
    public void the_robber_is_placed_on_hex(Integer hexIndex) {
        gameManager.moveRobber(hexIndex);
    }

    @When("the dice is rolled")
    public void the_dice_is_rolled() {
        gameManager.distributeResourcesOnRoll(diceRoll);
    }


    @Then("player1 should receive {string}")
    public void player1_should_receive(String resources) {
        List<ResourceType> expectedResources = parseResources(resources);
        assertTrue(player1.hasResources(expectedResources));
    }

    @Then("player2 should receive {string}")
    public void player2_should_receive(String resources) {
        List<ResourceType> expectedResources = parseResources(resources);
        assertTrue(player2.hasResources(expectedResources));
    }

    private List<ResourceType> parseResources(String resources) {
        if ("NONE".equals(resources)) {
            return Collections.emptyList();
        }
        return Arrays.stream(resources.split(","))
            .map(String::trim)
            .map(ResourceType::valueOf)
            .collect(Collectors.toList());
    }
}
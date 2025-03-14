package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InitialSettlement {

    private BoardManager boardManager;
    private Player currentPlayer;
    private GameManager gameManager;
    private boolean placed;

    @Given("a new game board")
    public void a_new_game_board() {
        boardManager = new BoardManager();
        gameManager = new GameManager(4, boardManager);
        gameManager.boardManager.initializeBoardStructure(false);
    }

    @Given("the player {string} is ready to place their settlement")
    public void the_player_is_ready_to_place_their_settlement(String playerName) {
        currentPlayer = new Player(Color.RED, playerName, new ArrayList<>());
    }

    @Given("a new game board with settlements at intersections [{int}, {int}, {int}]")
    public void a_new_game_board_with_settlements_at_intersections(Integer int1, Integer int2,
        Integer int3) {
        boardManager = new BoardManager();
        gameManager = new GameManager(4,boardManager);
        boardManager.initializeBoardStructure(false);
        Player tempPlayer = new Player(Color.RED, "temp", new ArrayList<>());
        List<Integer> indexes = Arrays.asList(int1, int2, int3);
        for (Integer index : indexes) {
            boardManager.placeSettlementSetup(index, tempPlayer, new Settlement());
        }
    }

    @When("the player tries to place a settlement at intersection {int}")
    public void the_player_tries_to_place_a_settlement_at_intersection(Integer index) {
        try {
            gameManager.placeInitialSettlement(index, currentPlayer);
            placed = true;
        } catch (Exception e) {
            placed = false;
        }
    }

    @Then("the settlement is successfully placed at intersection {int}")
    public void the_settlement_is_successfully_placed_at_intersection(Integer index) {
        Intersection intersection = boardManager.getIntersections()[index];
        assertTrue(intersection.getStructure() != null &&
                   intersection.getStructure().getOwner().equals(currentPlayer));
    }

    @Then("a settlement was not placed")
    public void a_settlement_was_not_placed() {
        assertFalse(placed);
    }


}
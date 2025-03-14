package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitialRoad {

    private BoardManager boardManager;
    private Player currentPlayer;
    private boolean placed;

    @Given("a newly set game board")
    public void aNewlySetGameBoard() {
        boardManager = new BoardManager();
        boardManager.initializeBoardStructure(false);
    }

    @And("A settlement is placed intersection {int}")
    public void aSettlementIsPlacedIntersectionIndex(int index) {
        boardManager.placeSettlementSetup(index, currentPlayer, new Settlement());
    }

    @And("the player {string} is ready to place their road")
    public void thePlayerIsReadyToPlaceTheirRoad(String playerName) {
        currentPlayer = new Player(Color.RED, playerName, new ArrayList<>());
    }


    @When("the player tries to place a settlement at intersection {int} and {int}")
    public void thePlayerTriesToPlaceASettlementAtIntersectionIndexIndex(int index1, int index2) {
        placed = boardManager.placeRoad(index1, index2, currentPlayer, false);
    }


    @Then("the road is successfully placed at intersection {int} and {int}")
    public void theRoadIsSuccessfullyPlacedAtIntersectionIndexAndIndex(int index1,
        int index2) {
            Intersection intersection = boardManager.getIntersections()[index1];
            assertTrue(intersection.getRoads() != null);
        }

    @Then("the road is not placed")
    public void theRoadIsNotPlaced() {
        assertFalse(placed);
    }
}

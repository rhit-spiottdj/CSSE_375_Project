package domain;

import io.cucumber.java.en.*;

import java.awt.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LongestRoad {

    private PlayerTrade playerTradeSteps;
    private Player longestRoadOwner;



    public LongestRoad(PlayerTrade playerTradeSteps){
        this.playerTradeSteps = playerTradeSteps;
    }

    @Given("a new game with {int} players road")
    public void a_new_game_with_players(Integer numPlayers) {
        BoardManager boardManager = new BoardManager();
        boardManager.initializeBoardStructure(false);
        playerTradeSteps.gameManager = new GameManager(numPlayers, boardManager);
        playerTradeSteps.players = new Player[2];
        playerTradeSteps.players[0] = new Player(Color.RED, "Player1", new ArrayList<>());
        playerTradeSteps.players[1] = new Player(Color.BLUE, "Player2", new ArrayList<>());
        playerTradeSteps.gameManager.setPlayer(0, playerTradeSteps.players[0]);
        playerTradeSteps.gameManager.setPlayer(1, playerTradeSteps.players[1]);
        playerTradeSteps.gameManager.setNextPlayerInTurn();
    }

    @Given("player1 places a settlement at intersection {int}")
    public void player1_places_a_settlement_at_intersection(Integer intersection) {
        playerTradeSteps.gameManager.placeInitialSettlement(intersection,
            playerTradeSteps.players[0]);
    }

    @Given("player2 places a settlement at intersection {int}")
    public void player2_places_a_settlement_at_intersection(Integer intersection) {
        playerTradeSteps.gameManager.placeInitialSettlement(intersection,
            playerTradeSteps.players[1]);
    }

    @Given("player1 has placed roads at intersections {string}")
    public void player1_has_placed_roads_at_intersections(String roads) {
        placeRoads(roads, playerTradeSteps.players[0]);
        playerTradeSteps.gameManager.findLongestRoad();
    }

    @Given("player2 has placed roads at intersections {string}")
    public void player2_has_placed_roads_at_intersections(String roads) {
        placeRoads(roads, playerTradeSteps.players[1]);
    }

    @When("the longest road is calculated")
    public void the_longest_road_is_calculated() {
        playerTradeSteps.gameManager.findLongestRoad();
        int one =
            playerTradeSteps.gameManager.calculateVictoryPointsForPlayer(playerTradeSteps.players[0]);
        int two =
            playerTradeSteps.gameManager.calculateVictoryPointsForPlayer(playerTradeSteps.players[1]);
        if (one > two) {
            longestRoadOwner = playerTradeSteps.players[0];
        } else {
            longestRoadOwner = playerTradeSteps.players[1];
        }
    }

    @Then("player1 should have longest road")
    public void player1_should_have_longest_road() {
        assertEquals(playerTradeSteps.players[0], longestRoadOwner);
    }

    @Then("player2 should have longest road")
    public void player2_should_have_longest_road() {
        assertEquals(playerTradeSteps.players[1], longestRoadOwner);
    }

    private void placeRoads(String roads, Player player) {
        String[] roadPairs = roads.split(", ");
        for (String road : roadPairs) {
            String[] intersections = road.split("-");
            int inter1 = Integer.parseInt(intersections[0]);
            int inter2 = Integer.parseInt(intersections[1]);
            playerTradeSteps.gameManager.placeRoad(inter1, inter2, player, false);
        }
    }
}

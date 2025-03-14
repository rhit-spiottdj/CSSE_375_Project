package domain;

import io.cucumber.java.en.*;

import java.awt.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LargestArmy {

    private GameManager gameManager;
    private Player[] players;

    @Given("a new game with {int} players army")
    public void a_new_game_with_players(Integer numPlayers) {
        BoardManager boardManager = new BoardManager();
        boardManager.initializeBoardStructure(false);
        gameManager = new GameManager(numPlayers, boardManager);
        players = new Player[numPlayers];
        players[0] = new Player(Color.RED, "Player1", new ArrayList<>());
        players[1] = new Player(Color.BLUE, "Player2", new ArrayList<>());
        gameManager.setPlayer(0, players[0]);
        gameManager.setPlayer(1, players[1]);
    }

    @When("player {int} has played {int} knights")
    public void player1_has_played_knights(int playerNum, Integer numKnights) {
        gameManager.setInTurnPlayer(playerNum);
        for (int i = 0; i < numKnights; i++) {
            players[playerNum].developmentCards.add(DevelopmentCards.KNIGHT);
            players[(playerNum + 1) % 2].addResource(ResourceType.ORE);
            gameManager.playKnight(null, i + 1);
        }
    }


    @Then("player {int} should have largest army")
    public void player1_should_have_largest_army(int playerNum) {
        int one = gameManager.calculateVictoryPointsForPlayer(players[playerNum]);
        int two = gameManager.calculateVictoryPointsForPlayer(players[(playerNum + 1) % 2]);
        assertTrue(one > two);
    }

    @Then("no player should have largest army")
    public void no_player_should_have_largest_army() {
        gameManager.findLongestRoad();
        int one = gameManager.calculateVictoryPointsForPlayer(players[0]);
        int two = gameManager.calculateVictoryPointsForPlayer(players[1]);
        assertEquals(one, two);
    }
}

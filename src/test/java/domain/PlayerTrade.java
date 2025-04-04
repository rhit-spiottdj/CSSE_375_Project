package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTrade {

    protected GameManager gameManager;
    Player[] playerInitializers = new Player[]{new Player(Color.RED, "player1",
        new ArrayList<>()), new Player(Color.BLUE, "player2", new ArrayList<>()),
        new Player(Color.YELLOW, "player3", new ArrayList<>()), new Player(Color.GREEN, "player4"
        , new ArrayList<>())};

    protected Player[] players;
    protected boolean tradeResult;

    @Given("a new game with {int} players trade")
    public void a_new_game_with_2_players(int numPlayers) {
        initNumPlayers(numPlayers);
    }

    @Given("player {int} has resources {string}")
    public void player1_has_resources(int playerNum, String resources) {
        int playerIndex = playerNum -1;
        List<ResourceType> resourceList = parseResources(resources);
        for (ResourceType resource : resourceList) {
            if (players[playerIndex] == null) {
                fail();
            }
            else {
                players[playerIndex].addResource(resource);
                gameManager.bank.giveBackResource(new ResourceTransaction(resource, 1));
            }
        }
    }


    @When("player {int} trades {string} with player {int} for {string}")
    public void player1_trades_with_player2_for(int playerOneNum, String player1Resources,
        int playerTwoNum,
        String player2Resources) {
        int playerOneIndex = playerOneNum-1;
        int playerTwoIndex = playerTwoNum-1;
        List<ResourceType> resources1 = parseResources(player1Resources);
        List<ResourceType> resources2 = parseResources(player2Resources);
        gameManager.setInTurnPlayer(playerOneIndex);
        tradeResult = gameManager.playerTrade(players[playerTwoIndex], resources1, resources2);
    }

    @Then("player {int} should have resources {string}")
    public void player1_should_have_resources(int playerNum, String resources) {
        int playerIndex = playerNum -1;
        List<ResourceType> expectedResources = parseResources(resources);
        assertTrue(players[playerIndex].hasResources(expectedResources));
    }


    @Then("the trade should fail")
    public void the_trade_should_fail() {
        assertFalse(tradeResult);
    }

    protected List<ResourceType> parseResources(String resources) {
        if(resources.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.stream(resources.split(":"))
            .map(String::trim)
            .map(ResourceType::valueOf)
            .collect(Collectors.toList());
    }

    protected void initNumPlayers(int numPlayers){
        gameManager = new GameManager(numPlayers);
        players = new Player[numPlayers];
        for(int i = 0; i< numPlayers; i++){
            players[i] = playerInitializers[i];
            gameManager.setPlayer(i, playerInitializers[i]);
        }
    }
}

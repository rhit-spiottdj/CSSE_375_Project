package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class WinTheGame {


    private final PlayerTrade playerTradeSteps;

    public WinTheGame(PlayerTrade playerTradeSteps){
        this.playerTradeSteps = playerTradeSteps;
    }

    @And("the player should have {int} victory points")
    public void thePlayerShouldHaveVictoryPoints(int numPoints) {

        int actualPoints = playerTradeSteps.gameManager.calculateVictoryPointsForPlayer(
            playerTradeSteps.gameManager.inTurn);
        assertEquals(numPoints,actualPoints);
    }

    @And("the game should not be over")
    public void theGameShouldNotBeOver() {
        assertFalse(playerTradeSteps.gameManager.isGameOver());
    }

    @When("the in-turn player buys {int} victory point cards")
    public void theInTurnPlayerBuysNum_cardsVictoryPointCards(int numCards) {
        buyVictoryPointCards(numCards);
    }

    @And("the in-turn player has {int} victory point cards")
    public void theInTurnPlayerHasVictoryPointCards(int numCards) {
        buyVictoryPointCards(5);
    }

    private void buyVictoryPointCards(int numCards){
        for(int i = 0; i < numCards; i++){
            playerTradeSteps.players[0].addResource(ResourceType.GRAIN);
            playerTradeSteps.players[0].addResource(ResourceType.WOOL);
            playerTradeSteps.players[0].addResource(ResourceType.ORE);
            playerTradeSteps.gameManager.bank.developmentCards.add(0,DevelopmentCards.VICTORY);
            playerTradeSteps.gameManager.buyDevelopmentCard();
        }
    }

    @And("the game should be over")
    public void theGameShouldBeOver() {
        assertTrue(playerTradeSteps.gameManager.isGameOver());
    }

    @When("the player has played {int} knights")
    public void player1_has_played_knights(Integer numKnights) {
        boolean whichHex = false;
        int hexIndex = 10;
        for (int i = 0; i < numKnights; i++) {
            playerTradeSteps.gameManager.players[0].developmentCards.add(DevelopmentCards.KNIGHT);
            playerTradeSteps.gameManager.playKnight(null, hexIndex);
            if(whichHex){
                hexIndex = 10;
                whichHex = false;
            }else{
                hexIndex = 9;
                whichHex = true;
            }
        }
    }
}

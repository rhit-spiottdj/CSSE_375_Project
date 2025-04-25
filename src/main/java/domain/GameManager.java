package domain;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.*;

public class GameManager {

    public final static int MAX_PLAYERS = 6;

    public final static int MIN_PLAYERS = 2;
    private static final int MAX_INTERSECTION_INDEX = 53;
    private static final int MAX_HEX_INDEX = 18;
    public static final int MULTIPLE_OF_FOUR = 4;
    public static final int SCORE_TO_WIN = 10;
    private static final int BARBARIAN_ATTACK_THRESHOLD = 1;
    private static final int ROBBER_ROLL = 7;

    private int sevensRolledCounter = 0;

    public DiceManager diceManager;
    Player[] players;
    Player inTurn;

    int inTurnIndex = -1;
    private int numPlayers;
    public BoardManager boardManager;
    public BonusManager bonusManager;
    private DevelopmentCardManager cardManager;

    Bank bank;
    private boolean gameOver = false;

    private static ResourceBundle messages; // Added for messages

    private GameManager(Player[] players, int numPlayers, BoardManager boardManager, Bank bank,
                        DiceManager diceManager, DevelopmentCardManager cardManager, BonusManager bonusManager, Locale locale) {
        this.numPlayers = (players != null) ? players.length : numPlayers;
        this.players = (players != null) ? players : new Player[this.numPlayers];
        this.boardManager = (boardManager != null) ? boardManager : new BoardManager();
        this.bank = (bank != null) ? bank : new Bank();
        this.diceManager = (diceManager != null) ? diceManager : new DiceManager(2);
        this.bonusManager = (bonusManager != null) ? bonusManager : new BonusManager();
        this.cardManager = (cardManager != null) ? cardManager : new DevelopmentCardManager(this.players, this.bank, this.boardManager, this.bonusManager);
        messages = ResourceBundle.getBundle("messages", locale); // Initialize messages
    }

    protected GameManager() {
        this(null, 0, null, null, null, null, null, Locale.getDefault()); // Use default locale
    }

    public GameManager(int numPlayers) {
        this(null, numPlayers, null, null, null, null, null, Locale.getDefault());
    }

    public GameManager(int numPlayers, BoardManager boardManager) {
        this(null, numPlayers, boardManager, null, null, null, null, Locale.getDefault());
    }

    protected GameManager(int numPlayers, BoardManager boardManager, DevelopmentCardManager cardManager) {
        this(null, numPlayers, boardManager, null, null, cardManager, null, Locale.getDefault());
    }

    protected GameManager(Player[] players, BoardManager boardManager) {
        this(players, 0, boardManager, null, null, null, null, Locale.getDefault());
    }

    protected GameManager(Player[] players, BoardManager boardManager, Bank bank) {
        this(players, 0, boardManager, bank, null, null, null, Locale.getDefault());
    }

    protected GameManager(DevelopmentCardManager cardManager, Player[] players, BoardManager boardManager, Bank bank) {
        this(players, 0, boardManager, bank, null, cardManager, null, Locale.getDefault());
    }

    protected GameManager(Player[] players, BoardManager boardManager, Bank bank, DiceManager diceManager) {
        this(players, 0, boardManager, bank, diceManager, null, null, Locale.getDefault());
    }


    int setNumPlayers(int numPlayers) {
        validateNumPlayerRange(numPlayers);
        players = new Player[numPlayers];
        this.numPlayers = numPlayers;
        return players.length;
    }

    private void validateNumPlayerRange(int numPlayers) {
        if (numPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("Maximum amount of players allowed is 6");
        } else if (numPlayers < MIN_PLAYERS) {
            throw new IllegalArgumentException("Minimum amount of players allowed is 2");
        }
    }


    public Player[] setTurnOrder(int[] diceRolls) {
        ensureValidSetTurnOrder(diceRolls);

        return reorderPlayersBasedOnDice(diceRolls);
    }

    private Player[] reorderPlayersBasedOnDice(int[] rolls) {
        for(int i = 0; i < numPlayers; i++){
            int index = getMaxRollIndex(rolls);
            swapPlayerAtIndexAndClearRoll(i, index, rolls, players);
        }
        return players.clone();
    }

    private void swapPlayerAtIndexAndClearRoll(int offsetIndex, int betterRollIndex,int[] rolls,
                                               Player[] players ){
        Player temp = players[betterRollIndex];
        players[betterRollIndex] = players[offsetIndex];
        players[offsetIndex] = temp;
        rolls[betterRollIndex] = rolls[offsetIndex];
        rolls[offsetIndex] = -1;
    }

    private int getMaxRollIndex(int[] rolls){
        int[] maxAndIndex = new int[]{-1,-1};
        for(int i = 0; i < rolls.length;i++){
            updateMaxAndIndexIfNewMax(rolls, maxAndIndex, i);
        }
        return maxAndIndex[1];
    }

    private void updateMaxAndIndexIfNewMax(int[] rolls, int[] maxAndIndex, int i) {
        if(maxAndIndex[0] < rolls[i]){
            maxAndIndex[0] = rolls[i];
            maxAndIndex[1] = i;
        }
    }

    private void ensureValidSetTurnOrder(int[] diceRolls) {
        ensureNumPlayersAndDiceRollsNotZero(diceRolls);
        ensureNumPlayersEqualsDiceRollsLength(diceRolls);
    }

    private void ensureNumPlayersAndDiceRollsNotZero(int[] diceRolls) {
        if (numPlayers == 0 || diceRolls.length == 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
    }

    private void ensureNumPlayersEqualsDiceRollsLength(int[] diceRolls) {
        if (numPlayers != diceRolls.length) {
            throw new IllegalArgumentException("Unequal Collection Sizes");
        }
    }

    public boolean setPlayer(int index, Player player) {
        if (index < 0 || index >= numPlayers) {
            return false;
        }
        players[index] = player;
        return true;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Player[] getPlayers() {
        return players.clone();
    }


    public void placeInitialSettlement(int selectedIndex, Player player) {
        validateIntersectionRange(selectedIndex);
        if (!boardManager.placeSettlementSetup(selectedIndex, player, new Settlement())) {
            throw new IllegalArgumentException("Error placing settlement, try again");
        }
    }

    public boolean buildSettlement(int intersection, Player player) {
        validateIntersectionRange(intersection);
        if (!boardManager.buildSettlement(intersection, player))    return false;
        removeResourcesFromPlayerAndGiveBackToBank(Settlement.getCost(), player);
        return true;
    }


    public boolean buildCity(int intersection, Player player) {
        validateIntersectionRange(intersection);
        if (!boardManager.buildCity(intersection, player))  return false;
        removeResourcesFromPlayerAndGiveBackToBank(City.getCost(), player);
        return true;
    }

    public void giveInitialResources(int intersection, Player player) {
        validateIntersectionRange(intersection);

        List<ResourceType> resourcesToDist = boardManager.distributeInitialResources(intersection);

        validateResourcesToDistribute(resourcesToDist);
        distributeResourcesToPlayer(player, resourcesToDist);

    }

    private void distributeResourcesToPlayer(Player player, List<ResourceType> resourcesToDist) {
        for (ResourceType resource : resourcesToDist) {
            if (bank.obtainResource(new ResourceTransaction(resource, 1))) {
                player.addResource(resource);
            }
        }
    }

    private void validateResourcesToDistribute(List<ResourceType> resourcesToDist) {
        if (resourcesToDist.isEmpty()) {
            throw new IllegalArgumentException("No resources to distribute");
        }
    }

    private void validateIntersectionRange(int intersection) {
        if (intersection < 0 || intersection > MAX_INTERSECTION_INDEX) {
            throw new IllegalArgumentException("Invalid Index");
        }
    }

    public void decrementResourcesFromBank(Collection<ResourceType> resourcesToRemove) {
        for (ResourceType resource : resourcesToRemove) {
            bank.obtainResource(new ResourceTransaction(resource, 1));
        }
    }

    public void placeRoad(int intersection1, int intersection2,
                          Player player, boolean init) {
        validateDifferentIntersections(intersection1, intersection2);
        if (!boardManager.placeRoad(intersection1, intersection2, player, init)) {
            throw new IllegalArgumentException("Error placing road, try again");
        }
    }

    public int calculateVictoryPointsForPlayer(Player player) {
        int amount = calculateVictoryPointsForPlayer(boardManager, player);
        player.setVictoryPoints(amount);
        gameOver = amount >= SCORE_TO_WIN;
        return amount;
    }

    public int calculateVictoryPointsForPlayer(BoardManager boardManager, Player player) {
        int numVictoryCards = getNumVictoryCards(player);
        int structureVictoryPoints = getStructureVictoryPoints(boardManager, player);
        int longestRoadPoints = getLongestRoadPoints(player);
        int largestArmyPoints = getLargestArmyPoints(player);
        return numVictoryCards + structureVictoryPoints + longestRoadPoints + largestArmyPoints;
    }

    private int getLongestRoadPoints(Player player) {
        int longestRoadPoints = 0;
        if (player.equals(bonusManager.getLongestRoadOwner())) {
            longestRoadPoints = 2;
        }
        return longestRoadPoints;
    }

    private int getLargestArmyPoints(Player player) {
        int largestArmyPoints = 0;
        if (player.equals(bonusManager.getLargestArmyOwner())) {
            largestArmyPoints = 2;
        }
        return largestArmyPoints;
    }

    private int getStructureVictoryPoints(BoardManager boardManager, Player player) {
        int structureVictoryPoints = 0;
        for (Structure structure : boardManager.getStructures())
            if (structure.getOwner() != null && structure.getOwner().equals(player))
                structureVictoryPoints += structure.getVictoryPoints();
        return structureVictoryPoints;
    }

    private int getNumVictoryCards(Player player) {
        Collection<DevelopmentCards> cards = player.getUnplayableDevelopmentCards();
        int numVictoryCards = 0;
        while (cards.remove(DevelopmentCards.VICTORY))  numVictoryCards++;
        return numVictoryCards;
    }

    public void moveRobber(int hexIndex) {
        validateMoveRobberLocation(hexIndex);
        boardManager.moveRobber(hexIndex);
    }

    private void validateMoveRobberLocation(int hexIndex) {
        if (hexIndex < 0 || hexIndex > MAX_HEX_INDEX) {
            throw new IllegalArgumentException("Invalid Hex Index");
        }
        validateRobberIsntAlreadyOnHex(hexIndex);
    }

    private void validateRobberIsntAlreadyOnHex(int hexIndex) {
        if (boardManager.getRobberLocation() == hexIndex) {
            throw new IllegalArgumentException("Robber is already on this hex");
        }
    }

    public int getNumberCardsToDiscard(Player player) {
        if (player.getResources().size() > Player.MAX_HAND_SIZE_ROBBER) {
            return player.getResources().size() / 2;
        } else {
            return 0;
        }
    }


    public ResourceType tryRobberSteal(Player currentPlayer, Player selectedPlayerToSteal) {
        validateRobberSteal(selectedPlayerToSteal);
        return boardManager.stealResource(currentPlayer, selectedPlayerToSteal);
    }

    private void validateRobberSteal(Player selectedPlayerToSteal) {
        if(selectedPlayerToSteal == null){
            throw new IllegalArgumentException("No player to steal from");
        }
        validatePlayerHasResourcesToSteal(selectedPlayerToSteal);
    }

    private void validatePlayerHasResourcesToSteal(Player selectedPlayerToSteal) {
        if (selectedPlayerToSteal.getResources().isEmpty()) {
            throw new IllegalArgumentException("Player has no resources to steal");
        }
    }

    public ArrayList<Player> getHexagonPlayers() {
        return boardManager.getHexagonPlayers();
    }

    public int getCurrentDiceRoll() {
        return diceManager.getCurrentDiceRoll();
    }

    public boolean playerDiscardResources(Player player,
                                          Collection<ResourceType> resourcesToDiscard) {
        return bank.playerDiscardResources(player, resourcesToDiscard);
    }

    public boolean currentPlayerHasSufficientResources(
            Collection<ResourceType> resourcesToTradeAway) {
        return inTurn.hasResources(resourcesToTradeAway);
    }

    public void setInTurnPlayer(int playerIndex) {
        if (playerIndex < 0 || playerIndex >= numPlayers) {
            throw new IndexOutOfBoundsException("Outside player range");
        }
        inTurn = players[playerIndex];
    }

    public boolean playerTrade(Player player2, Collection<ResourceType> player1Resources,
                               Collection<ResourceType> player2Resources) {
        return bank.playerTrade(inTurn, player2, player1Resources, player2Resources);
    }

    public boolean isInTurnPlayer(Player player) {
        return player.equals(inTurn);
    }

    public boolean buildRoad(int intersection1, int intersection2, Player player) {
        validateDifferentIntersections(intersection1, intersection2);
        if (!boardManager.buildRoad(intersection1, intersection2, player))  return false;
        removeResourcesFromPlayerAndGiveBackToBank(Road.getCost(), player);
        return true;
    }

    private void validateDifferentIntersections(int intersection1, int intersection2) {
        if (intersection1 == intersection2) {
            throw new IllegalArgumentException("Cannot place road on the same intersection");
        }
    }

    private void removeResourcesFromPlayerAndGiveBackToBank(Collection<ResourceType> resourceCost,
                                                            Player player) {
        for (ResourceType resource : resourceCost) {
            player.removeResource(resource);
            bank.giveBackResource(new ResourceTransaction(resource, 1));
        }
    }

    public int distributeResourcesOnRoll(int roll) {
        if (roll == ROBBER_ROLL) {
            handleBarbarianTrigger();
            return 0; // Indicate 7 was rolled, but don't distribute resources yet
        } else {
            return boardManager.distributeResourcesOnRoll(roll, bank);
        }
    }

    private void handleBarbarianTrigger() {
        sevensRolledCounter++;
        if (sevensRolledCounter >= BARBARIAN_ATTACK_THRESHOLD) {
            handleBarbarianAttack();
            sevensRolledCounter = 0;
        }
    }


    private void handleBarbarianAttack() {
        int barbarianStrength = calculateBarbarianStrength();
        int totalKnightStrength = calculateTotalKnightStrength();
        String title = messages.getString("barbarianAttackTitle");
        String message;

        if (barbarianStrength > totalKnightStrength) {
            message = MessageFormat.format(messages.getString("barbariansWonMessage"), barbarianStrength, totalKnightStrength);
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
            applyBarbarianPenalty();
        } else {
            message = MessageFormat.format(messages.getString("playersWonMessage"), totalKnightStrength, barbarianStrength);
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
            applyBarbarianReward();
        }
        resetAllPlayerKnightCounts();
    }

    private int calculateBarbarianStrength() {
        int strength = 0;
        for(Structure structure: boardManager.getStructures()) {
            if(structure instanceof City) {
                strength++;
            }
        }
        return strength;
    }

    private int calculateTotalKnightStrength() {
        int strength = 0;
        for(Player player: players) {
            if(player != null) {
                strength += player.getPlayedKnightCount();
            }
        }
        return strength;
    }

    private void applyBarbarianPenalty() {
        Player playerWithFewestKnights = null;
        int minKnights = Integer.MAX_VALUE;
        for (Player player: players) {
            if(player != null) {
                if (player.getPlayedKnightCount() < minKnights) {
                    minKnights = player.getPlayedKnightCount();
                }
            }
        }

        ArrayList<Player> playersToPenalize = new ArrayList<>();
        for (Player player: players) {
            if(player != null) {
                if (player.getPlayedKnightCount() == minKnights) {
                    playersToPenalize.add(player);
                }
            }
        }

        StringBuilder penaltyMessage = new StringBuilder();
        for (Player player : playersToPenalize) {
            if (downgradeRandomCity(player)) {
                penaltyMessage.append(MessageFormat.format(messages.getString("cityDowngradedMessage"), player.getPlayerName())).append("\n");
            }
        }
        if (penaltyMessage.length() > 0) {
            JOptionPane.showMessageDialog(null, penaltyMessage.toString().trim(), messages.getString("barbarianAttackTitle"), JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean downgradeRandomCity(Player player) {
        ArrayList<Integer> playerCityLocations = new ArrayList<>();
        for(int i = 0; i < boardManager.intersections.length; i++) {
            Structure structure = boardManager.intersections[i].getStructure();
            if(structure instanceof City && structure.getOwner() != null && structure.getOwner().equals(player)) {
                playerCityLocations.add(i);
            }
        }
        if(!playerCityLocations.isEmpty()) {
            Random rand = new Random();
            int cityIndexToDowngrade = playerCityLocations.get(rand.nextInt(playerCityLocations.size()));

            boardManager.intersections[cityIndexToDowngrade].setStructure(null);
            boardManager.placeSettlementSetup(cityIndexToDowngrade, player, new Settlement());

            player.setNumCities(player.getNumCities() + 1);
            player.setNumSettlements(player.getNumSettlements() - 1);

            calculateVictoryPointsForPlayer(player);
            return true; // Indicate a city was downgraded
        }
        return false; // Indicate no city was downgraded
    }


    private void applyBarbarianReward() {
        Player playerWithMostKnights = null;
        int maxKnights = -1;
        int numPlayersWithMax = 0;
        for (Player player: players) {
            if(player != null) {
                if (player.getPlayedKnightCount() > maxKnights) {
                    maxKnights = player.getPlayedKnightCount();
                    playerWithMostKnights = player;
                    numPlayersWithMax = 1;
                } else if (player.getPlayedKnightCount() == maxKnights && maxKnights >= 0) {
                    numPlayersWithMax++;
                }
            }
        }
        if (numPlayersWithMax == 1 && playerWithMostKnights != null) {
            playerWithMostKnights.setVictoryPoints(playerWithMostKnights.getVictoryPoints() + 1);
            calculateVictoryPointsForPlayer(playerWithMostKnights);
            String message = MessageFormat.format(messages.getString("playerRewardedMessage"), playerWithMostKnights.getPlayerName());
            JOptionPane.showMessageDialog(null, message, messages.getString("barbarianAttackTitle"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetAllPlayerKnightCounts() {
        for (Player player : players) {
            if(player != null) {
                player.resetPlayedKnightCount();
            }
        }
    }


    public Collection<ResourceType> playYearOfPlenty(ResourceType resourceOne,
                                                     ResourceType resourceTwo){
        return cardManager.playYearOfPlenty(inTurn, resourceOne, resourceTwo);
    }

    public boolean playRoadBuildingCard(int[][] intersections){
        boolean success = cardManager.playRoadBuilding(inTurn, intersections);
        if(success) {
            bonusManager.findLongestRoad(players, boardManager.getRoadsOnBoard().toArray(new Road[0]));
            calculateVictoryPointsForPlayer(inTurn);
        }
        return success;
    }

    public Collection<ResourceType> playMonopolyCard(ResourceType resource){
        return cardManager.playMonopoly(inTurn, resource);
    }

    public boolean playKnight(Player toStealFrom, int newHexIndex){
        return cardManager.playKnight(inTurn, toStealFrom, newHexIndex) == null;
    }

    public boolean buyDevelopmentCard(){
        boolean success = bank.obtainDevelopmentCard(inTurn);
        calculateVictoryPointsForPlayer(inTurn);
        return success;
    }

    public Collection<DevelopmentCards> getDevelopmentCardsInBank(){
        return bank.getDevelopmentCards();
    }

    public Collection<DevelopmentCards> getPlayableDevelopmentCards(){
        return inTurn.getDevelopmentCards();
    }

    public Collection<DevelopmentCards> getUnplayableDevelopmentCards(){
        return inTurn.getUnplayableDevelopmentCards();
    }

    public Collection<DevelopmentCards> getFutureDevelopmentCards(){
        return inTurn.getFutureDevelopmentCards();
    }


    public int getRobberLocation(){
        return boardManager.getRobberLocation();
    }

    public boolean playerTradeWithBank(ResourceType giving, ResourceType taking, int numResources){
        if (numResources % MULTIPLE_OF_FOUR != 0) {
            return false;
        }
        boolean success = bank.tradeResourceBank(giving, taking, numResources / MULTIPLE_OF_FOUR);
        if (success) {
            doTradeWithBank(giving, taking, numResources);
        }
        return success;
    }

    private void doTradeWithBank(ResourceType giving, ResourceType taking, int numResources){
        removePlayerResource(giving, numResources);
        addPlayerResource(taking, numResources / MULTIPLE_OF_FOUR);
    }

    public boolean playerTradeWithPort(Port port, ResourceType giving, ResourceType taking,
                                       int numResources){
        if (portCheck(port, giving, taking, numResources)) {
            doTradeWithPort(port, giving, taking, numResources);
            return true;
        }
        return false;
    }

    private boolean portCheck(Port port, ResourceType giving, ResourceType taking,
                              int numResources) {
        return isValidRatio(port.getPortTradeRatio(), numResources) && bank.tradeResourcePort(port,
                giving, taking, numResources / port.getPortTradeRatio());
    }

    private void doTradeWithPort(Port port, ResourceType giving, ResourceType taking,
                                 int numResources) {
        removePlayerResource(giving, numResources);
        addPlayerResource(taking, numResources / port.getPortTradeRatio());
    }

    static boolean isValidRatio(int portTradeRatio, int numResources) {
        if (portTradeRatio <= 0) return false;
        if (numResources == 0) return true;
        int base = numResources/portTradeRatio;
        return numResources == base * portTradeRatio;
    }

    private void removePlayerResource(ResourceType resource, int amount){
        for(int i = 0; i < amount; i++){
            inTurn.removeResource(resource);
        }
    }

    private void addPlayerResource(ResourceType resource, int amount){
        for(int i = 0; i < amount; i++){
            inTurn.addResource(resource);
        }
    }

    public int setNextPlayerInTurn(){
        inTurnIndex++;
        if(inTurnIndex >= numPlayers) inTurnIndex = 0;
        inTurn = players[inTurnIndex];
        return inTurnIndex;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void findLongestRoad() {
        bonusManager.findLongestRoad(players, boardManager.getRoadsOnBoard().toArray(new Road[0]));
    }

    public int getSevensRolledCounter() {
        return sevensRolledCounter;
    }

    public int getBarbarianAttackThreshold() {
        return BARBARIAN_ATTACK_THRESHOLD;
    }
}
package domain;

import java.util.*;

public class GameManager {

    public final static int MAX_PLAYERS = 6;

    public final static int MIN_PLAYERS = 2;
    private static final int MAX_INTERSECTION_INDEX = 53;
    private static final int MAX_HEX_INDEX = 18;
    public static final int MULTIPLE_OF_FOUR = 4;
    public static final int SCORE_TO_WIN = 10;

    public DiceManager diceManager;
    Player[] players = new Player[MAX_PLAYERS];
    Player inTurn;

    int inTurnIndex = -1;
    private int numPlayers;
    public BoardManager boardManager;
    private DevelopmentCardManager cardManager;

    Bank bank;
    private boolean gameOver = false;

    //This one is for testing only
    protected GameManager() {
    }


    public GameManager(int numPlayers) {
        diceManager = new DiceManager(2);
        bank = new Bank();
        setNumPlayers(numPlayers);
        this.boardManager = new BoardManager();
        cardManager = new DevelopmentCardManager(players,bank,boardManager);

    }

    public GameManager(int numPlayers, BoardManager boardManager) {
        diceManager = new DiceManager(2);
        bank = new Bank();
        this.boardManager = boardManager;
        setNumPlayers(numPlayers);
        cardManager = new DevelopmentCardManager(players,bank,boardManager);
    }

    //This one is for testing only
    protected GameManager(int numPlayers, BoardManager boardManager,
        DevelopmentCardManager cardManager) {
        setNumPlayers(numPlayers);
        this.boardManager = boardManager;
        this.cardManager = cardManager;
    }

    protected GameManager(Player[] players, BoardManager boardManager) {
        this.players = players;
        this.numPlayers = players.length;
        this.boardManager = boardManager;

    }

    protected GameManager(Player[] players, BoardManager boardManager, Bank bank) {
        this.players = players;
        this.numPlayers = players.length;
        this.boardManager = boardManager;
        this.bank = bank;
    }

    protected GameManager(DevelopmentCardManager cardManager, Player[] players,
        BoardManager boardManager, Bank bank) {
        this.players = players;
        this.numPlayers = players.length;
        this.boardManager = boardManager;
        this.bank = bank;
        this.cardManager = cardManager;
    }

    protected GameManager(Player[] players, BoardManager boardManager, Bank bank,
        DiceManager diceManager) {
        this.players = players;
        this.numPlayers = players.length;
        this.boardManager = boardManager;
        this.bank = bank;
        this.diceManager = diceManager;
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
            if (bank.obtainResource(resource, 1)) {
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
            bank.obtainResource(resource, 1);
        }
    }

    public void placeRoad(int intersection1, int intersection2,
        Player player, boolean init) {
        validateDifferentIntersections(intersection1, intersection2);
        if (!boardManager.placeRoad(intersection1, intersection2, player, init)) {
            throw new IllegalArgumentException("Error placing road, try again");
        }
    }

    public void findLongestRoad() {
        cardManager.findLongestRoad(players, boardManager.getRoadsOnBoard().toArray(new Road[0]));
        calculateVictoryPointsForPlayer(inTurn);
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
        if (player.equals(cardManager.getLongestRoadOwner())) {
            longestRoadPoints = 2;
        }
        return longestRoadPoints;
    }

    private int getLargestArmyPoints(Player player) {
        int largestArmyPoints = 0;
        if (player.equals(cardManager.getLargestArmyOwner())) {
            largestArmyPoints = 2;
        }
        return largestArmyPoints;
    }

    private int getStructureVictoryPoints(BoardManager boardManager, Player player) {
        int structureVictoryPoints = 0;
        for (Structure structure : boardManager.getStructures())
            if (structure.getOwner().equals(player))
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
            bank.giveBackResource(resource, 1);
        }
    }

    public int distributeResourcesOnRoll(int roll) {
        return boardManager.distributeResourcesOnRoll(roll, bank);
    }


    public Collection<ResourceType> playYearOfPlenty(ResourceType resourceOne,
        ResourceType resourceTwo){
        return cardManager.playYearOfPlenty(inTurn, resourceOne, resourceTwo);
    }

    public boolean playRoadBuildingCard(int[][] intersections){
        boolean success = cardManager.playRoadBuilding(inTurn, intersections);
        if(success) findLongestRoad();
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
            giving, taking, numResources / port.getPortTradeRatio().getValue());
    }

    private void doTradeWithPort(Port port, ResourceType giving, ResourceType taking,
        int numResources) {
        removePlayerResource(giving, numResources);
        addPlayerResource(taking, numResources / port.getPortTradeRatio().getValue());
    }

    static boolean isValidRatio(PortTradeRatio portTradeRatio, int numResources) {
        int base = numResources/portTradeRatio.getValue();
        return numResources == base * portTradeRatio.getValue();
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
        if(inTurnIndex == numPlayers) inTurnIndex = 0;
        inTurn = players[inTurnIndex];
        return inTurnIndex;
    }

    public boolean isGameOver(){
        return gameOver;
    }
}


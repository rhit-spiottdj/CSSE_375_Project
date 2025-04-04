package domain;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class DevelopmentCardManager {

    private static final int AT_LEAST_THREE_UNIQUE_INTERSECTIONS = 3;
    private static final int MIN_FOR_ARMY = 2;
    private static final int MIN_ROADS_FOR_LONGEST = 4;
    public static final int MAX_HEX_INDEX = 18;

    Bank bank;

    BoardManager board;

    Player longestRoadOwner;

    Player largestArmyOwner;

    Player[] players;

    DevelopmentCardManager(Player[] players, Bank bank, BoardManager board){
        this.players = players;
        this.bank = bank;
        this.board = board;
    }


    public Collection<ResourceType> playYearOfPlenty(Player player, ResourceType resourceOne,
        ResourceType resourceTwo) {
        boolean insufficientResources = checkIfInsufficientResources(resourceOne, resourceTwo);
        if (!insufficientResources) addResourcesToPlayer(player, resourceOne, resourceTwo);
        return player.getResources();
    }

    private boolean checkIfInsufficientResources(ResourceType resource1, ResourceType resource2) {
        boolean insufficient;
        if (resource1.equals(resource2)) {
            ResourceTransaction transaction = new ResourceTransaction(resource1, 1);
            insufficient = bank.noMoreResource(transaction);
        } else insufficient = checkNoMoreResourceForEach(resource1, resource2);
        return insufficient;
    }

    private boolean checkNoMoreResourceForEach(ResourceType resource1, ResourceType resource2) {
        return bank.noMoreResource(new ResourceTransaction(resource1, 1)) ||
                bank.noMoreResource(new ResourceTransaction(resource2, 1));
    }

    private void addResourcesToPlayer(Player player, ResourceType resourceOne,
        ResourceType resourceTwo) {
        try {
            player.addResource(resourceOne);
            player.addResource(resourceTwo);
        } catch (IllegalStateException e) {
        }
    }

    public Collection<ResourceType> playMonopoly(Player player, ResourceType resource) {
        for(Player checkPlayer: players){
            if(checkPlayer.equals(player))  continue;
            while (checkPlayer.removeResource(resource))    player.addResource(resource);
        }
        return player.getResources();
    }

    public boolean playRoadBuilding(Player player, int[][] intersections) {
        validatePlayRoadBuilding(intersections);

        HashSet<Integer> intersectionSet = makeIntersectionSet(intersections);
        if (intersectionSet.size() < AT_LEAST_THREE_UNIQUE_INTERSECTIONS)   return false;
        return placeRoadBuildingRoads(player, intersections);
    }

    private void validateRobberNotOnHexAlready(int newHexIndex) {
        if(board.getRobberLocation() == newHexIndex){
            throw new IllegalArgumentException("Robber is already on this hex");
        }
    }

    private boolean placeRoadBuildingRoads(Player player, int[][] intersections) {
        if (placementFails(player, intersections)) {
            return false;
        }
        player.setNumRoads(player.getNumRoads() - 2);
        return true;
    }

    private boolean placementFails(Player player, int[][] intersections) {
        if(!board.placeRoad(intersections[0][0], intersections[0][1], player, false)) return true;
        return checkSecondRoadPlacement(player, intersections);
    }

    private boolean checkSecondRoadPlacement(Player player, int[][] intersections) {
        if(!board.placeRoad(intersections[1][0], intersections[1][1], player, false)){
            board.removeRoad(intersections[0][0], intersections[0][1]);
            return true;
        }
        return false;
    }

    private static HashSet<Integer> makeIntersectionSet(int[][] intersections) {
        HashSet<Integer> intersectionSet = new HashSet<>();
        for (int[] pair : intersections) {
            for (int intersection : pair) intersectionSet.add(intersection);
        }
        return intersectionSet;
    }

    private void validatePlayRoadBuilding(int[][] intersections) {
        if (intersections.length != 2 || intersections[0].length != 2) {
            throw new IllegalArgumentException("Intersections need to be passed in 2x2 array");
        }
    }

    public ResourceType playKnight(Player player, Player toStealFrom, int newHexIndex) {
        boolean playerToStealFrom = validatePlayKnight(player, toStealFrom, newHexIndex);
        ResourceType stolen = getPossibleStolenResource(player, toStealFrom, playerToStealFrom);
        updateLargestArmy(player);
        player.setDevelopmentCardAsPlayed(DevelopmentCards.KNIGHT);
        return stolen;
    }

    private ResourceType getPossibleStolenResource(Player player, Player toStealFrom,
        boolean playerToStealFrom) {
        ResourceType stolen = null;
        if(playerToStealFrom)   stolen = board.stealResource(player, toStealFrom);
        return stolen;
    }

    private boolean validatePlayKnight(Player player, Player toStealFrom, int newHexIndex) {
        validateHasKnightCard(player);
        validateRobberNotOnHexAlready(newHexIndex);
        validateHexIndexRange(newHexIndex);
        return validateTargetPlayerOnNewHex(toStealFrom, newHexIndex, player);
    }

    private boolean validateTargetPlayerOnNewHex(Player toStealFrom, int hexIndex, Player player) {
        int oldRobberLocation = board.getRobberLocation();
        ArrayList<Player> onHexagon = getPlayersOnHexAndEnsureValid(hexIndex, player);
        if(onHexagon.isEmpty() && toStealFrom == null)  return false;
        ensureValidPlayerForHex(toStealFrom, oldRobberLocation, onHexagon);
        return true;
    }

    private ArrayList<Player> getPlayersOnHexAndEnsureValid(int hexIndex, Player player) {
        ArrayList<Player> onHexagon = moveRobberAndGetPlayersOnHex(hexIndex);
        onHexagon.remove(player);
        return onHexagon;
    }

    private void ensureValidPlayerForHex(Player toStealFrom,
        int oldRobberLocation, ArrayList<Player> onHexagon) {
        noPlayerToStealFrom(toStealFrom);
        moveRobberBackAndErrorIfPlayerNotOnHex(toStealFrom, oldRobberLocation, onHexagon);
    }

    private ArrayList<Player> moveRobberAndGetPlayersOnHex(int newHexIndex) {
        board.moveRobber(newHexIndex);
        ArrayList<Player> onHexagon = board.getHexagonPlayers();
        return onHexagon;
    }

    private void moveRobberBackAndErrorIfPlayerNotOnHex(Player toStealFrom,
        int oldRobberLocation, ArrayList<Player> onHexagon) {
        if(!(onHexagon.contains(toStealFrom))){
            board.moveRobber(oldRobberLocation);
            throw new IllegalArgumentException("Player is not on the hex");
        }
    }

    private static void noPlayerToStealFrom(Player toStealFrom) {
        if(toStealFrom == null){
            throw new IllegalArgumentException("Hex has players and none specified");
        }
    }

    private void validateHexIndexRange(int newHexIndex) {
        if(newHexIndex < 0 || newHexIndex > MAX_HEX_INDEX){
            throw new IllegalArgumentException("Invalid hex index");
        }
    }

    private void validateHasKnightCard(Player player) {
        if(!player.hasDevelopmentCard(DevelopmentCards.KNIGHT)) {
            throw new IllegalArgumentException("Player does not have a knight card");
        }
    }

    static class SearchState {
        int maxLength = 0;
        Player maxPlayer = null;
        HashMap<Player, HashSet<Intersection>> playerIntersections = new HashMap<>();
    }


    public boolean findLongestRoad(Player[] players, Road[] roads) {
        SearchState state = new SearchState();
        preprocessRoads(players, roads, state);
        longestRoadLoop(players, state);
        return longestRoadDecision(state);
    }

    private boolean longestRoadDecision(SearchState state) {
        if (state.maxPlayer != null && state.maxLength > MIN_ROADS_FOR_LONGEST) {
            longestRoadOwner = state.maxPlayer;
            return true;
        }
        return false;
    }

    private void longestRoadLoop(Player[] players, SearchState state) {
        for (Player player : players) {
            longestRoadInnerLoop(player, state);
        }
    }

    static class DFSPackage {
        Intersection current;
        Player player;
        HashSet<Intersection> visited;
    }

    private void longestRoadInnerLoop(Player player, SearchState state) {
        for (Intersection intersection : state.playerIntersections.getOrDefault(player,
            new HashSet<>())) {
            longestRoadInnerInnerLoop(player, state, intersection);
        }
    }

    private void longestRoadInnerInnerLoop(Player player, SearchState state,
        Intersection intersection) {
        HashSet<Intersection> visited = new HashSet<>();
        DFSPackage dfsPackage = packageSetup(intersection, player, visited);
        dfs(dfsPackage, 0, state);
    }

    private DFSPackage packageSetup(Intersection intersection, Player player,
        HashSet<Intersection> visited) {
        DFSPackage dfsPackage = new DFSPackage();
        initializeDFS(intersection, player, visited, dfsPackage);
        return dfsPackage;
    }

    private void initializeDFS(Intersection intersection, Player player,
        HashSet<Intersection> visited, DFSPackage dfsPackage) {
        dfsPackage.current = intersection;
        dfsPackage.player = player;
        dfsPackage.visited = visited;
    }

    private void preprocessRoads(Player[] players, Road[] roads, SearchState state) {
        for (Road road : roads) {
            preprocessInnerLoop(players, road, state);
        }
    }

    private void preprocessInnerLoop(Player[] players, Road road, SearchState state) {
        Player owner = road.getOwner();
        if (owner != null) {
            preprocessStates(owner, road, state);
        }
    }

    private void preprocessStates(Player owner, Road road, SearchState state) {
        state.playerIntersections.putIfAbsent(owner, new HashSet<>());
        state.playerIntersections.get(owner).add(road.getIntersection(0));
        state.playerIntersections.get(owner).add(road.getIntersection(1));
    }

    private void dfs(DFSPackage dfsPackage, int length, SearchState state) {
        dfsPackage.visited.add(dfsPackage.current);
        setState(length, dfsPackage.player, state);
        dfsOuterLoop(dfsPackage, length, state);
        dfsPackage.visited.remove(dfsPackage.current);
    }

    private void dfsOuterLoop(DFSPackage dfsPackage, int length, SearchState state) {
        for (Road road : dfsPackage.current.getRoads()) {
            dfsInnerLoop(dfsPackage, road, length, state);
        }
    }

    private void dfsInnerLoop(DFSPackage dfsPackage, Road road, int length, SearchState state) {
        Intersection next = road.getOtherIntersection(dfsPackage.current);
        if (!dfsPackage.visited.contains(next) && road.getOwner() == dfsPackage.player &&
            next.notEnemySettlement(dfsPackage.player)) {
            dfsSetCurrentToNext(dfsPackage, length, state, next);
        }
    }

    private void dfsSetCurrentToNext(DFSPackage dfsPackage, int length, SearchState state,
        Intersection next) {
        dfsPackage.current = next;
        dfs(dfsPackage, length + 1, state);
    }

    private void setState(int length, Player player, SearchState state) {
        if (length > state.maxLength) {
            setStateHelper(length, player, state);
        } else if (length == state.maxLength && player != state.maxPlayer) {
            state.maxPlayer = null;
        }
    }

    private void setStateHelper(int length, Player player, SearchState state) {
        state.maxLength = length;
        state.maxPlayer = player;
    }

    private void updateLargestArmy(Player player) {
        int numPlayed = getNumPlayedKnights(player);
        if(largestArmyOwner == null && numPlayed == MIN_FOR_ARMY)    setLargestArmyOwner(player);
        else if(largestArmyOwner != null) {
            if(numPlayed + 1 > getMostKnightsPlayed())   setLargestArmyOwner(player);
        }
    }

    private int getMostKnightsPlayed(){
        return getNumPlayedKnights(largestArmyOwner);
    }

    private int getNumPlayedKnights(Player player){
        int knightPlayedCount = 0;
        for(DevelopmentCards card: player.getUnplayableDevelopmentCards()){
            if(card.equals(DevelopmentCards.KNIGHT))    knightPlayedCount++;
        }
        return knightPlayedCount;
    }

    protected Player getLongestRoadOwner(){
        return longestRoadOwner;
    }

    protected Player getLargestArmyOwner() {
        return largestArmyOwner;
    }

    protected void setLargestArmyOwner(Player player) {
        largestArmyOwner = player;
    }

    protected void setLongestRoadOwner(Player player) {
        longestRoadOwner = player;
    }
}

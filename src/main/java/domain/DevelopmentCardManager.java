package domain;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class DevelopmentCardManager {

    private static final int AT_LEAST_THREE_UNIQUE_INTERSECTIONS = 3;
    public static final int MAX_HEX_INDEX = 18;

    Bank bank;

    BoardManager board;
    BonusManager bonus;

    Player[] players;

    DevelopmentCardManager(Player[] players, Bank bank, BoardManager board, BonusManager bonus) {
        this.players = players;
        this.bank = bank;
        this.board = board;
        this.bonus = bonus;
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
        player.setNumRoads(player.getNumRoads() + 2);
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
        bonus.updateLargestArmy(player);
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
}

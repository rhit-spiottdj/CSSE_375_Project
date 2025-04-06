package domain;

import java.util.HashMap;
import java.util.HashSet;

public class BonusManager {
    
    private static final int MIN_FOR_ARMY = 2;
    private static final int MIN_ROADS_FOR_LONGEST = 4;
    
    Player longestRoadOwner;

    Player largestArmyOwner;

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

    public void updateLargestArmy(Player player) {
        int numPlayed = getNumPlayedKnights(player);
        if(largestArmyOwner == null && numPlayed == MIN_FOR_ARMY)
            setLargestArmyOwner(player);
        else if(largestArmyOwner != null) {
            if(numPlayed + 1 > getMostKnightsPlayed())
                setLargestArmyOwner(player);
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

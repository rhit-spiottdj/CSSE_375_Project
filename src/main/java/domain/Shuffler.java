package domain;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Shuffler {

    public static final int RESOURCES_WITH_FOUR_OCCURRENCES = 4;
    public static final int RESOURCES_WITH_THREE_OCCURRENCES = 3;
    private static final int SIX_ADJACENT = 6;
    private static final int EIGHT_ADJACENT = 8;

    private static final int DUPLICATE_TOKEN_START = 3;
    private static final int DUPLICATE_TOKEN_END = 11;
    private static final int ROBBER_TOKEN_TO_SKIP = 7;

    private static final int SINGLE_TWO_TOKEN = 2;
    private static final int SINGLE_TWELVE_TOKEN = 12;
    public static final int NUM_THREE_PORT = 4;
    private final ArrayList<ResourceType> resourceTypes;
    private final ArrayList<Integer> numberTokens;
    private final ArrayList<Port> portTokens;
    int[][] hexagonValues;

    public Shuffler() {
        resourceTypes = createShuffledResourceTypes();
        numberTokens = createShuffledNumberTokens();
        portTokens = createShuffledPortTokens();
    }

    private ArrayList<ResourceType> createShuffledResourceTypes() {
        ArrayList<ResourceType> resourceTypes = new ArrayList<>();

        addResourceWithFourOccurences(resourceTypes);
        addResourceWithThreeOccurrences(resourceTypes);

        Collections.shuffle(resourceTypes);

        return resourceTypes;
    }

    private void addResourceWithFourOccurences(ArrayList<ResourceType> resourceTypes) {
        for (int i = 0; i < RESOURCES_WITH_FOUR_OCCURRENCES; i++) {
            resourceTypes.add(ResourceType.GRAIN);
            resourceTypes.add(ResourceType.LUMBER);
            resourceTypes.add(ResourceType.WOOL);
        }
    }

    private void addResourceWithThreeOccurrences(ArrayList<ResourceType> resourceTypes) {
        for (int i = 0; i < RESOURCES_WITH_THREE_OCCURRENCES; i++) {
            resourceTypes.add(ResourceType.ORE);
            resourceTypes.add(ResourceType.BRICK);
        }
    }

    private ArrayList<Integer> createShuffledNumberTokens() {
        ArrayList<Integer> numberTokens = new ArrayList<>();
        addSetupNumberTokens(numberTokens);

        Collections.shuffle(numberTokens);

        return numberTokens;
    }

    private void addSetupNumberTokens(ArrayList<Integer> numberTokens) {
        for (int i = DUPLICATE_TOKEN_START; i <= DUPLICATE_TOKEN_END; i++) {
            addTokenIfNotRobber(numberTokens, i);
        }

        numberTokens.add(SINGLE_TWO_TOKEN);
        numberTokens.add(SINGLE_TWELVE_TOKEN);
    }

    private void addTokenIfNotRobber(ArrayList<Integer> numberTokens, int i) {
        if (i != ROBBER_TOKEN_TO_SKIP) {
            numberTokens.add(i);
            numberTokens.add(i);
        }
    }

    ArrayList<Port> createShuffledPortTokens() {
        ArrayList<Port> portTokens = new ArrayList<>();

        addThreeRatioPortTokens(portTokens);
        addTwoRatioPortTokens(portTokens);

        ensureShufflePorts(portTokens);


        return portTokens;
    }

    void ensureShufflePorts(ArrayList<Port> portTokens){
        if (portTokens.size() == 0 || portTokens.size() == 1) return;
        ArrayList<Port> dupeTokens = new ArrayList<>(portTokens);
        while(checkIfShallowArrayListCopy(dupeTokens, portTokens)) Collections.shuffle(portTokens);
    }

    private boolean checkIfShallowArrayListCopy(ArrayList list, ArrayList list2){
        for(int i = 0; i < list.size(); i++){
            if(!list.get(i).equals(list2.get(i))) return false;
        }
        return true;
    }

    private void addTwoRatioPortTokens(ArrayList<Port> portTokens) {
        for(ResourceType resource: ResourceType.values()){
            portTokens.add(new Port(PortTradeRatio.TWO_TO_ONE, resource));
        }
    }

    private void addThreeRatioPortTokens(ArrayList<Port> portTokens) {
        for(int i = 0; i < NUM_THREE_PORT; i++){
            portTokens.add(new Port(PortTradeRatio.THREE_TO_ONE, ResourceType.GRAIN));
        }
    }

    int[][] getHexagonValues(Hexagon[] hexagons) {
        initializeHexagonValuesToNegativeOne();

        setHexagonValues(hexagons);

        return hexagonValues;
    }

    private void setHexagonValues(Hexagon[] hexagons) {
        for (Hexagon hexagon : hexagons) {
            setHexagonValue(hexagon);
        }
    }

    private void setHexagonValue(Hexagon hexagon) {
        Point2D center = hexagon.getCenter();
        int row = (int) Math.round(center.getY() / Math.sqrt(0.5));
        int col = (int) Math.round(center.getX() - row * 0.5);
        hexagonValues[row + 2][col + 2] = hexagon.getValue();
    }

    private void initializeHexagonValuesToNegativeOne() {
        final int boardSize = 5;
        hexagonValues = new int[boardSize][boardSize];
        for (int[] hexagonValue : hexagonValues) {
            Arrays.fill(hexagonValue, -1);
        }
    }

    public void ensureNoNeighborSixOrEight(Random random) {
        boolean hasNeighborSixOrEight = true;
        while (hasNeighborSixOrEight) {
            hasNeighborSixOrEight = isHasNeighborSixOrEight(random, hasNeighborSixOrEight);
        }
    }

    boolean isHasNeighborSixOrEight(Random random, boolean hasNeighborSixOrEight) {
        for (int i = 0; i < hexagonValues.length; i++) {
            hasNeighborSixOrEight = fixAdjacentNeighborSixOrEight(random, i);
            if (hasNeighborSixOrEight)  break;
        }
        return hasNeighborSixOrEight;
    }

    private boolean fixAdjacentNeighborSixOrEight(Random random, int i) {
        for (int j = 0; j < hexagonValues[i].length; j++) {
            if (fixAdjacencyIfSixOrEight(random, i, j)) return true;
        }
        return false;
    }

    private boolean fixAdjacencyIfSixOrEight(Random random, int i, int j) {
        if (hexagonValues[i][j] == SIX_ADJACENT || hexagonValues[i][j] == EIGHT_ADJACENT) {
            if (hasAdjacentSixOrEightAndSwap(random, i, j)) return true;
        }
        return false;
    }

    private boolean hasAdjacentSixOrEightAndSwap(Random random, int i, int j) {
        if (hasAdjacentSixOrEight(i, j)) {
            swapCell(random, i, j);
            return true;
        }
        return false;
    }

    boolean hasAdjacentSixOrEight(int row, int col) {
        for (int differenceI = -1; differenceI <= 1; differenceI++) {
            for (int differenceJ = -1; differenceJ <= 1; differenceJ++)
                if (hasAdjacentSixOrEightCheck(row, col, differenceI, differenceJ))     return true;
        }
        return false;
    }

    private boolean hasAdjacentSixOrEightCheck(int row, int col, int difI, int difJ) {
        if ((difI != 0 || difJ != 0) && isValidNeighbor(row, col, difI, difJ)) {
            int neighborValue = hexagonValues[row + difI][col + difJ];
            if (neighborValue == SIX_ADJACENT || neighborValue == EIGHT_ADJACENT)   return true;
        }
        return false;
    }

    boolean isValidNeighbor(int row, int col, int differenceI, int differenceJ) {

        int neighborI = row + differenceI;
        int neighborJ = col + differenceJ;
        return neighborI >= 0 && neighborI < hexagonValues.length && neighborJ >= 0 &&
               neighborJ < hexagonValues[neighborI].length;
    }

    void swapCell(Random random, int row, int col) {
        int[] swapRowAndCol = new int[2];
        swapRowAndCol[0] = random.nextInt(hexagonValues.length);
        swapRowAndCol[1] = random.nextInt(hexagonValues[swapRowAndCol[0]].length);
        getValidHexagonValueSwap(random, swapRowAndCol);
        swapHexagonValues(row, col, swapRowAndCol[0], swapRowAndCol[1]);
    }

    private void getValidHexagonValueSwap(Random random, int[] swapRowAndCol){
        while (!isValidSwap(swapRowAndCol[0], swapRowAndCol[1])) {
            swapRowAndCol[0] = random.nextInt(hexagonValues.length);
            swapRowAndCol[1] = random.nextInt(hexagonValues[swapRowAndCol[0]].length);
        }
    }

    private void swapHexagonValues(int row, int col, int swapRow, int swapCol) {
        int temp = hexagonValues[row][col];
        hexagonValues[row][col] = hexagonValues[swapRow][swapCol];
        hexagonValues[swapRow][swapCol] = temp;
    }

    private boolean isValidSwap(int row, int col) {
        return hexagonValues[row][col] != SIX_ADJACENT &&
               hexagonValues[row][col] != EIGHT_ADJACENT && hexagonValues[row][col] != 0 &&
               hexagonValues[row][col] != -1;
    }


    public ArrayList<ResourceType> getShuffledResourceTypes() {
        return new ArrayList<>(resourceTypes);
    }

    public ArrayList<Integer> getShuffledNumberTokens() {
        return new ArrayList<>(numberTokens);
    }

    public ArrayList<Port> getShuffledPortTokens() {
        return new ArrayList<>(portTokens);
    }
}

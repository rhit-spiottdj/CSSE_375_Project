package domain;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.*;

public class BoardManager {


    private static final int NUM_HEXAGONS = 19;
    private static final int HEX_MID_ROW_SIZE = 5;
    private static final int HALF_BOARD_SIZE = 3;
    private static final int RANDOM_BOUND = 5;
    private static final int MAX_HEX_INDEX = 18;
    private static final int NUM_INTERSECTIONS = 54;
    private static final int INTERSECTION_MID_ROW_SIZE = 6;
    private static final double INTERSECTION_HEIGHT_DIFF = 0.289;
    private static final int INTERSECTION_BOARD_MID = 4;
    private static final int END_OF_BOARD_INTERSECTIONS = 3;
    private static final double ADJ_DISTANCE = 0.6;
    private static final int MAX_INTERSECTION_INDEX = 53;
    private static final int[] PRESET_BOARD_ROW_1 = {-1, -1, 10, 2, 9};
    private static final int[] PRESET_BOARD_ROW_2 = {-1, 12, 6, 4, 10};
    private static final int[] PRESET_BOARD_ROW_3 = {9, 11, 0, 3, 8};
    private static final int[] PRESET_BOARD_ROW_4 = {8, 3, 4, 5, -1};
    private static final int[] PRESET_BOARD_ROW_5 = {5, 6, 11, -1, -1};
    static final int[] PORT_ONE = {10, 4};
    static final int[] PORT_TWO = {30, 44};
    static final int[] PORT_THREE = {51, 42};
    static final int[] PORT_FOUR = {53, 47};
    static final int[] PORT_FIVE = {31, 29};
    static final int[] PORT_SIX = {21, 19};
    static final int[] PORT_SEVEN = {39, 50};
    static final int[] PORT_EIGHT = {48, 34};
    static final int[] PORT_NINE = {20, 36};
    static int[][] PORT_INTERSECTIONS = new int[][]{
        PORT_ONE, PORT_TWO, PORT_THREE, PORT_FOUR, PORT_FIVE, PORT_SIX, PORT_SEVEN,
        PORT_EIGHT, PORT_NINE};
    public static final int NUM_THREE_TO_ONE_PORTS = 4;

    ArrayList<Port> ports = new ArrayList<>();

    int selectedIntersection = -1;

    int selectedHex = -1;

    Intersection[] intersections;
    Hexagon[] hexagons;
    List<Intersection> structureLocations = new LinkedList<>();

    List<Structure> structures = new LinkedList<>();

    List<Intersection> roads = new LinkedList<>();

    List<Road> roadsOnBoard = new ArrayList<>();

    Shuffler shuffler;

    private Random rand;

    public BoardManager() {
        this.rand = new Random();
    }

    protected BoardManager(Hexagon[] hexagons) {
        this.hexagons = hexagons;
    }

    protected BoardManager(Random rand) {
        this.rand = rand;
    }

    protected BoardManager(Intersection[] intersections) {
        this.intersections = intersections;
    }

    protected BoardManager(Intersection[] intersections, List<Intersection> structureLocations) {
        this.intersections = intersections;
        this.structureLocations = structureLocations;
    }

    protected BoardManager(Intersection[] intersections, List<Intersection> structureLocations,
        ArrayList<Intersection> roads) {
        this.intersections = intersections;
        this.structureLocations = structureLocations;
        this.roads = roads;
    }

    protected BoardManager(Hexagon[] hexagons, Intersection[] intersections) {
        this.hexagons = hexagons;
        this.intersections = intersections;
    }

    protected BoardManager(Intersection[] intersections, List<Intersection> roads,
        List<Road> roadsOnBoard) {
        this.intersections = intersections;
        this.roads = roads;
        this.roadsOnBoard = roadsOnBoard;
    }

    protected BoardManager(Shuffler shuffler, Intersection[] intersections) {
        this.shuffler = shuffler;
        this.intersections = intersections;
    }


    Hexagon[] generateHexagons(boolean randomize) {
        shuffler = new Shuffler();
        HexagonHelper helper = new HexagonHelper(shuffler);

        generateAllHexagonsAndRandomize(randomize, helper);

        this.hexagons = helper.hexagons;
        return helper.hexagons;
    }

    private void generateAllHexagonsAndRandomize(boolean randomize, HexagonHelper helper) {
        generateAllHexagons(helper);

        randomizeBoard(randomize, helper, new Random());
    }

    private void generateAllHexagons(HexagonHelper helper) {
        for (int i = 0; i < HALF_BOARD_SIZE; i++) {
            generateHexagonsAt(helper, i);
        }
    }

    private void generateHexagonsAt(HexagonHelper helper, int i) {
        helper.updateCurrentRowSize(i);
        helper.updateBaseOffset(i);
        int numInRightHalfOfRow = (int) Math.ceil(((double) HEX_MID_ROW_SIZE - i) / 2);
        addHexagonOnSide(helper, numInRightHalfOfRow);
        addMirroredHexagonsIfNotMiddle(helper, i, numInRightHalfOfRow);
    }

    private void addHexagonOnSide(HexagonHelper helper, int numInRightHalfOfRow) {
        for (int j = 0; j < numInRightHalfOfRow; j++) {
            addHexagonsToTop(helper, j);
        }
    }

    private void addMirroredHexagonsIfNotMiddle(HexagonHelper helper, int i,
        int numInRightHalfOfRow) {
        if (i != 0) {
            for (int j = 0; j < numInRightHalfOfRow; j++) {
                addHexagonsToBottom(helper, j);
            }
        }
    }

    void randomizeBoard(boolean randomize, HexagonHelper helper, Random rand) {
        if (randomize) {
            boardShuffleAndRandomization(helper, rand);
        } else {
            boardPredeterminedLayout(helper);
        }
    }

    private void addHexagonsToTop(HexagonHelper helper, int column) {
        addHexagon(helper, helper.offsetX + column, helper.offsetY);
        if (column != 0 || helper.currentRowSize % 2 == 0) {
            addHexagon(helper, -1 * (helper.offsetX + column), helper.offsetY);
        }
    }

    private void addHexagonsToBottom(HexagonHelper helper, int column) {
        addHexagon(helper, helper.offsetX + column, -1 * helper.offsetY);
        if (column != 0 || helper.currentRowSize % 2 == 0) {
            addHexagon(helper, -1 * (helper.offsetX + column), -1 * helper.offsetY);
        }
    }

    void addHexagon(HexagonHelper helper, double offsetX, double offsetY) {
        Point2D center = new Point2D.Double(offsetX, offsetY);
        helper.hexagons[helper.index] = new Hexagon(center, helper.index);
        addResAndValToHex(helper);
        helper.index++;
    }

    void boardShuffleAndRandomization(HexagonHelper helper, Random rand) {
        Shuffler shuffler = helper.shuffler;
        Hexagon[] hexagons = helper.hexagons;
        int[][] hexagonValues = shuffler.getHexagonValues(hexagons);

        randomizeAndReassignBoard(shuffler, hexagons, hexagonValues, rand);

    }

    private void randomizeAndReassignBoard(Shuffler shuffler, Hexagon[] hexagons,
        int[][] hexagonValues, Random rand) {
        shuffler.ensureNoNeighborSixOrEight(rand);

        randomizeDesert(hexagonValues, rand);

        reassignValue(hexagons, hexagonValues);

        reassignResource(hexagons);
    }

    void randomizePorts(Shuffler shuffler){

        ports = shuffler.getShuffledPortTokens();
        for(int i = 0; i < PORT_INTERSECTIONS.length; i++){
            intersections[PORT_INTERSECTIONS[i][0]].setPort(ports.get(i));
            intersections[PORT_INTERSECTIONS[i][1]].setPort(ports.get(i));
        }
    }

    private void predeterminedPorts(){
        ports = new ArrayList<>();
        int i;
        i = predeterminedThreeToOnePorts();
        predeterminedTwoToOnePorts(i);
    }

    private void predeterminedTwoToOnePorts(int i) {
        for(ResourceType resource: ResourceType.values()){
            i = addPredeterminedTwoToOnePort(i, resource);
        }
    }

    private int addPredeterminedTwoToOnePort(int i, ResourceType resource) {
        Port port = new Port(PortTradeRatio.TWO_TO_ONE, resource);
        intersections[PORT_INTERSECTIONS[i][0]].setPort(port);
        intersections[PORT_INTERSECTIONS[i++][1]].setPort(port);
        ports.add(port);
        return i;
    }

    private int predeterminedThreeToOnePorts() {
        int i;
        for(i = 0; i < NUM_THREE_TO_ONE_PORTS; i++){
            addPredeterminedThreeToOnePort(i);
        }
        return i;
    }

    private void addPredeterminedThreeToOnePort(int i) {
        Port port = new Port(PortTradeRatio.THREE_TO_ONE, ResourceType.GRAIN);
        intersections[PORT_INTERSECTIONS[i][0]].setPort(port);
        intersections[PORT_INTERSECTIONS[i][1]].setPort(port);
        ports.add(port);
    }

    private void boardPredeterminedLayout(HexagonHelper helper) {
        Hexagon[] hexagons = helper.hexagons;
        int[][] hexagonValues = getPredeterminedHexagonValues();
        ResourceType[] resourceTypes = getPredeterminedResourceLayout();

        loadPredeterminedBoardLayout(hexagons, hexagonValues, resourceTypes);

    }

    void loadPredeterminedBoardLayout(Hexagon[] hexagons, int[][] hexagonValues,
        ResourceType[] resourceTypes) {
        assignPredeterminedResources(hexagons, resourceTypes);

        reassignValue(hexagons, hexagonValues);

        reassignResource(hexagons);
    }

    private int[][] getPredeterminedHexagonValues() {
        int[][] hexagonValues =
            new int[][]{PRESET_BOARD_ROW_1, PRESET_BOARD_ROW_2, PRESET_BOARD_ROW_3,
                PRESET_BOARD_ROW_4, PRESET_BOARD_ROW_5};
        return hexagonValues;
    }

    private ResourceType[] getPredeterminedResourceLayout() {
        return new ResourceType[]{null, ResourceType.LUMBER, ResourceType.LUMBER, ResourceType.ORE,
            ResourceType.GRAIN, ResourceType.GRAIN, ResourceType.ORE, ResourceType.WOOL,
            ResourceType.LUMBER, ResourceType.WOOL, ResourceType.BRICK, ResourceType.BRICK,
            ResourceType.GRAIN, ResourceType.GRAIN, ResourceType.WOOL, ResourceType.BRICK,
            ResourceType.WOOL, ResourceType.LUMBER, ResourceType.ORE};
    }

    void assignPredeterminedResources(Hexagon[] hexagons, ResourceType[] resourceTypes) {
        for (int i = 0; i < hexagons.length; i++) {
            hexagons[i].setResource(resourceTypes[i]);
        }
    }


    private void reassignValue(Hexagon[] hexagons, int[][] hexagonValues) {
        for (Hexagon hexagon : hexagons) {
            reassignHexWithValue(hexagonValues, hexagon);
        }
    }

    void reassignHexWithValue(int[][] hexagonValues, Hexagon hexagon) {
        Point2D center = hexagon.getCenter();
        int row = (int) Math.round(center.getY() / Math.sqrt(0.5));
        int col = (int) Math.round(center.getX() - row * 0.5);
        reassignValueAtHex(hexagonValues, hexagon, new int[]{row,col});
    }

    void reassignValueAtHex(int[][] hexagonValues, Hexagon hexagon, int[] rowCol) {
        if (hexagon.getIsDesert() && hexagonValues[rowCol[0] + 2][rowCol[1] + 2] != 0) {
            reassignValueAtHexAndUpdateInfo(hexagon, false, hexagonValues, rowCol);
        } else if (!hexagon.getIsDesert() && hexagonValues[rowCol[0] + 2][rowCol[1] + 2] == 0) {
            reassignValueAtHexAndUpdateInfo(hexagon, true, hexagonValues, rowCol);
        } else  hexagon.setValue(hexagonValues[rowCol[0] + 2][rowCol[1] + 2]);
    }

    private void reassignValueAtHexAndUpdateInfo(Hexagon hexagon, boolean isDesert,
        int[][] hexagonValues, int[] rowCol) {
        hexagon.setDesert(isDesert);
        hexagon.setHasRobber(isDesert);
        hexagon.setValue(hexagonValues[rowCol[0] + 2][rowCol[1] + 2]);
    }

    private void reassignResource(Hexagon[] hexagons) {

        //find hexagon with value 0 and get its resource
        for (Hexagon hex : hexagons) {
            reassignResourceIfValueIsZero(hexagons, hex);
        }

    }

    void reassignResourceIfValueIsZero(Hexagon[] hexagons, Hexagon hex) {
        if (hex.getValue() == 0) {
            ResourceType resourceToSwap = hex.getResource();
            //find null resource hex and reassign
            swapWithHexIfNullFound(hexagons, resourceToSwap);
            hex.setResource(null);
        }
    }

    void swapWithHexIfNullFound(Hexagon[] hexagons, ResourceType resourceToSwap) {
        for (Hexagon hex2 : hexagons) {
            if (hex2.getResource() == null) {
                hex2.setResource(resourceToSwap);
            }
        }
    }

    private int[] findDesertInHexagonValuesForm(int[][] hexagonValues) {
        int[] desertRowAndCol = {0,0};
        searchForDesertInHexagonValuesForm(hexagonValues, desertRowAndCol);
        return desertRowAndCol;
    }

    private void searchForDesertInHexagonValuesForm(int[][] hexagonValues, int[] desertRowAndCol) {
        for (int i = 0; i < hexagonValues.length; i++) {
            for (int j = 0; j < hexagonValues[i].length; j++) {
                ifHexValueZeroSetDesert(hexagonValues, desertRowAndCol, i, j);
            }
        }
    }

    protected void ifHexValueZeroSetDesert(int[][] hexagonValues, int[] desertRowAndCol,
        int i, int j) {
        if (hexagonValues[i][j] == 0) {
            desertRowAndCol[0] = i;
            desertRowAndCol[1] = j;
        }
    }

    void randomizeDesert(int[][] hexagonValues, Random rand) {
        int[] desertIndex = findDesertInHexagonValuesForm(hexagonValues);

        int[] newRowAndCol = {rand.nextInt(RANDOM_BOUND),rand.nextInt(RANDOM_BOUND)};

        ensureNewLocation(hexagonValues, rand, desertIndex, newRowAndCol);

        swapHexWithLocation(hexagonValues, desertIndex, newRowAndCol);
    }

    private void swapHexWithLocation(int[][] hexagonValues, int[] hexIndex, int[] newRowAndCol) {
        int temp = hexagonValues[hexIndex[0]][hexIndex[1]];
        hexagonValues[hexIndex[0]][hexIndex[1]] = hexagonValues[newRowAndCol[0]][newRowAndCol[1]];
        hexagonValues[newRowAndCol[0]][newRowAndCol[1]] = temp;
    }

    private void ensureNewLocation(int[][] hexagonValues, Random rand, int[] desertIndex,
        int[] newRowAndCol) {
        while (newRowAndCol[0] == desertIndex[0] && newRowAndCol[1] == desertIndex[1] ||
               hexagonValues[newRowAndCol[0]][newRowAndCol[1]] == -1) {
            newRowAndCol[0] = rand.nextInt(RANDOM_BOUND);
            newRowAndCol[1] = rand.nextInt(RANDOM_BOUND);
        }
    }

    void addResAndValToHex(HexagonHelper helper) {
        if (helper.index != MAX_HEX_INDEX) {
            addResAndValToValidHex(helper);
        }

        helper.hexagons[helper.index].setDesert(true);
        helper.hexagons[helper.index].setHasRobber(true);
    }

    private void addResAndValToValidHex(HexagonHelper helper) {
        helper.hexagons[helper.index].setResource(helper.resourceTypes.get(0));
        helper.hexagons[helper.index].setValue(helper.numberTokens.get(0));
        helper.numberTokens.remove(0);
        helper.resourceTypes.remove(0);
    }

    Intersection[] generateIntersections() {
        IntersectionHelper helper = new IntersectionHelper();

        addAllIntersections(helper);

        makeAdjacentIntersections(helper.intersections);

        this.intersections = helper.intersections;

        return intersections;
    }

    private void addAllIntersections(IntersectionHelper helper) {
        for (int i = 0; i < INTERSECTION_BOARD_MID; i++) {
            prepareForAndAddIntersectionsInRow(helper, i);
        }
    }

    private void prepareForAndAddIntersectionsInRow(IntersectionHelper helper, int i) {
        helper.updateCurrentRowSize(i);
        helper.updateBaseOffset(i);
        int numInRightHalfOfRow = (int) Math.ceil(((double) INTERSECTION_MID_ROW_SIZE - i) / 2);
        addIntersectionsInRow(helper, i, numInRightHalfOfRow);
    }

    private void addIntersectionsInRow(IntersectionHelper helper, int i, int numInRightHalfOfRow) {
        addNormalIntersectionsInRow(helper, i, numInRightHalfOfRow);
        addFlippedIntersectionsInRow(helper, i, numInRightHalfOfRow);
    }

    private void addNormalIntersectionsInRow(IntersectionHelper helper,
        int i, int numInRightHalfOfRow) {
        for (int j = 0; j < numInRightHalfOfRow; j++) {
            addIntersectionsThatSubtractFromY(helper, i, j);
            addIntersectionsThatAddToY(helper, i, j);
        }
    }

    private void addFlippedIntersectionsInRow(IntersectionHelper helper,
        int i, int numInRightHalfOfRow) {
        for (int j = 0; j < numInRightHalfOfRow; j++) {
            addIntersectionsThatSubtractFromAndFlipY(helper, i, j);
            addIntersectionsThatAddToAndFlipY(helper, i, j);
        }
    }

    private void addIntersectionsThatAddToAndFlipY(IntersectionHelper helper, int i, int j) {
        if (i != END_OF_BOARD_INTERSECTIONS) {
            addIntersection(helper, helper.offsetX + j,
                -1 * (helper.offsetY + INTERSECTION_HEIGHT_DIFF));
            addFlippedIntersectionIfNotHorizontallyCentered(helper, j);
        }
    }

    private void addFlippedIntersectionIfNotHorizontallyCentered(IntersectionHelper helper, int j) {
        if (j != 0 || helper.currentRowSize % 2 == 0) {
            addIntersection(helper, -1 * (helper.offsetX + j),
                -1 * (helper.offsetY + INTERSECTION_HEIGHT_DIFF));
        }
    }

    private void addIntersectionsThatSubtractFromAndFlipY(IntersectionHelper helper, int row,
        int column) {
        if (row != 0) {
            addIntersection(helper, helper.offsetX + column,
                -1 * (helper.offsetY - INTERSECTION_HEIGHT_DIFF));
            addSubtractFlipIntersectionIfNotHorizontallyCentered(helper, column);
        }
    }

    private void addSubtractFlipIntersectionIfNotHorizontallyCentered(IntersectionHelper helper,
        int column) {
        if (column != 0 || helper.currentRowSize % 2 == 0) {
            addIntersection(helper, -1 * (helper.offsetX + column),
                -1 * (helper.offsetY - INTERSECTION_HEIGHT_DIFF));
        }
    }

    private void addIntersectionsThatAddToY(IntersectionHelper helper, int i, int j) {
        if (i != END_OF_BOARD_INTERSECTIONS) {
            addIntersection(helper, helper.offsetX + j, helper.offsetY + INTERSECTION_HEIGHT_DIFF);
            addIntersectionIfNotHorizontallyCentered(helper, j);
        }
    }

    private void addIntersectionIfNotHorizontallyCentered(IntersectionHelper helper, int j) {
        if (j != 0 || helper.currentRowSize % 2 == 0) {
            addIntersection(helper, -1 * (helper.offsetX + j),
                helper.offsetY + INTERSECTION_HEIGHT_DIFF);
        }
    }

    private void addIntersectionsThatSubtractFromY(IntersectionHelper helper, int row, int column) {
        if (row != 0) {
            addIntersection(helper, helper.offsetX + column,
                helper.offsetY - INTERSECTION_HEIGHT_DIFF);
            addSubtractIntersectionIfNotHorizontallyCentered(helper, column);
        }
    }

    private void addSubtractIntersectionIfNotHorizontallyCentered(IntersectionHelper helper,
        int column) {
        if (column != 0 || helper.currentRowSize % 2 == 0) {
            addIntersection(helper, -1 * (helper.offsetX + column),
                helper.offsetY - INTERSECTION_HEIGHT_DIFF);
        }
    }

    private void addIntersection(IntersectionHelper helper, double offsetX, double offsetY) {
        Point2D center = new Point2D.Double(offsetX, offsetY);
        helper.intersections[helper.index] = new Intersection(center, helper.index);
        helper.index++;
    }

    void makeAdjacentIntersections(Intersection[] intersections) {
        for (int i = 0; i < intersections.length; i++) {
            makeAdjacentIntersection(intersections, i);
        }
    }

    private void makeAdjacentIntersection(Intersection[] intersections, int i) {
        Point2D center = intersections[i].getCenter();
        setAllIntersectionsAsAdjacent(intersections, i, center);
    }

    private void setAllIntersectionsAsAdjacent(Intersection[] intersections,
        int i, Point2D center) {
        for (int j = 0; j < intersections.length; j++) {
            setIntersectionAsAdjacent(intersections, i, center, j);
        }
    }

    void setIntersectionAsAdjacent(Intersection[] intersections, int i, Point2D center,
        int j) {
        if (i != j && !intersections[i].getAdjacentIntersections().contains(j)) {
            Point2D otherCenter = intersections[j].getCenter();
            double distance = center.distance(otherCenter);
            if (distance < ADJ_DISTANCE)    intersections[i].addAdjacentIntersection(j);
        }
    }

    public Hexagon[] initializeBoardStructure(boolean randomize) {
        Hexagon[] hexagons = generateHexagons(randomize);
        Intersection[] intersections = generateIntersections();
        generatePorts(randomize);

        linkHexagonsAndIntersections(hexagons, intersections);
        return hexagons;
    }

    void linkHexagonsAndIntersections(Hexagon[] hexagons, Intersection[] intersections) {
        addIntersectionsToHexagons(hexagons, intersections);
        addHexagonsToIntersections(hexagons, intersections);
    }

    void generatePorts(boolean randomize){
        if(randomize){
            randomizePorts(shuffler);
        }else{
            predeterminedPorts();
        }
    }

    private void addHexagonsToIntersections(Hexagon[] hexagons, Intersection[] intersections) {
        for (Intersection inter : intersections) {
            addHexagonsToIntersection(hexagons, inter);
        }
    }

    private void addHexagonsToIntersection(Hexagon[] hexagons, Intersection inter) {
        ArrayList<Hexagon> relatedHexagons = new ArrayList<>();
        Point2D interCenter = inter.getCenter();
        addAllAdjacentHexagons(hexagons, relatedHexagons, interCenter);
        inter.setHexagons(relatedHexagons.toArray(new Hexagon[0]));
    }

    private void addAllAdjacentHexagons(Hexagon[] hexagons, ArrayList<Hexagon> relatedHexagons,
        Point2D interCenter) {
        for (Hexagon hex : hexagons) {
            addHexagonIfAdjacent(relatedHexagons, interCenter, hex);
        }
    }

    void addHexagonIfAdjacent(ArrayList<Hexagon> relatedHexagons,
        Point2D interCenter, Hexagon hex) {
        Point2D hexCenter = hex.getCenter();
        double distance = interCenter.distance(hexCenter);
        if (distance < ADJ_DISTANCE) {
            relatedHexagons.add(hex);
        }
    }

    private void addIntersectionsToHexagons(Hexagon[] hexagons, Intersection[] intersections) {
        for (Hexagon hex : hexagons) {
            addIntersectionsToHexagon(intersections, hex);
        }
    }

    private void addIntersectionsToHexagon(Intersection[] intersections, Hexagon hex) {
        ArrayList<Intersection> relatedIntersections = new ArrayList<>();
        Point2D hexCenter = hex.getCenter();
        addAllAdjacentIntersections(intersections, relatedIntersections, hexCenter);
        hex.setIntersections(relatedIntersections.toArray(new Intersection[0]));
    }

    private void addAllAdjacentIntersections(Intersection[] intersections,
        ArrayList<Intersection> relatedIntersections, Point2D hexCenter) {
        for (Intersection inter : intersections) {
            addIntersectionIfAdjacent(relatedIntersections, hexCenter, inter);
        }
    }

    void addIntersectionIfAdjacent(ArrayList<Intersection> relatedIntersections,
        Point2D hexCenter, Intersection inter) {
        Point2D interCenter = inter.getCenter();
        double distance = hexCenter.distance(interCenter);
        if (distance < ADJ_DISTANCE) {
            relatedIntersections.add(inter);
        }
    }

    public Intersection[] getIntersections() {
        return intersections.clone();
    }

    public Hexagon[] getHexagons() {
        return hexagons.clone();
    }


    protected boolean checkSettlementPlacementLocation(int index) {
        if (index < 0 || index > MAX_INTERSECTION_INDEX)    return false;
        Intersection intersection = intersections[index];
        if (checkIfViolatesSettlementRules(index, intersection))    return false;
        structureLocations.add(intersection);
        return true;
    }

    private boolean checkIfViolatesSettlementRules(int index, Intersection intersection) {
        for (Intersection inter : structureLocations) {
            if (inter.getAdjacentIntersections().contains(index)) return true;
        }

        return structureLocations.contains(intersection);
    }

    public boolean placeSettlementSetup(int index, Player player, Settlement settlement) {
        if (checkSettlementPlacementLocation(index)) {
            makeSettlement(index, player, settlement);
            return true;
        }
        return false;
    }

    public boolean buildSettlement(int index, Player player) {
        if (checkSettlementPlacement(index, player) && checkSettlementCostAndCount(player)) {
            placeSettlementAndDecrementCount(index, player);
            return true;
        }
        return false;
    }

    private void placeSettlementAndDecrementCount(int index, Player player) {
        makeSettlement(index, player, new Settlement());
        player.setNumSettlements(player.getNumSettlements() - 1);
    }

    void makeSettlement(int index, Player player, Settlement settlement) {
        settlement.setOwner(player);
        intersectionSetStructureAndAddOwner(index, player, settlement);
        structures.add(settlement);
        structureLocations.add(intersections[index]);
    }

    private void intersectionSetStructureAndAddOwner(int index, Player player,
        Settlement settlement) {
        intersections[index].setStructure(settlement);
        intersections[index].setOwner(player);
    }

    private boolean checkSettlementPlacement(int index, Player player) {
        if (indexOutOfBoundsOrViolatesSettlementRules(index) ||
            roadNotConnectingOrNotOwnedByPlayer(index, player)) {
            return false;
        }
        return true;
    }

    private boolean roadNotConnectingOrNotOwnedByPlayer(int index, Player player) {
        return !roads.contains(intersections[index])
               || !intersections[index].ownedByThisPlayer(player);

    }

    private boolean indexOutOfBoundsOrViolatesSettlementRules(int index) {
        return (index < 0 || index > MAX_INTERSECTION_INDEX ||
            checkIfViolatesSettlementRules(index, intersections[index]));
    }

    boolean checkSettlementCostAndCount(Player player) {
        return player.hasResources(Settlement.getCost()) && player.getNumSettlements() > 0;
    }

    public int peekIntersectionSelection() {
        return selectedIntersection;
    }

    public int peekHexSelection() {
        return selectedHex;
    }

    public int getIntersectionSelection() {
        int temp = selectedIntersection;
        selectedIntersection = -1;
        return temp;
    }

    public int getHexSelection() {
        int temp = selectedHex;
        selectedHex = -1;
        return temp;
    }

    public void setIntersectionSelection(int selectedIntersection) {
        this.selectedIntersection = selectedIntersection;
    }

    public void setHexSelection(int selectedHex) {
        this.selectedHex = selectedHex;
    }

    protected boolean checkRoadPlacementLocation(int one, int two, Player p, boolean init) {
        if (!checkValidRoadPlacementLocation(one, two, p, init)) {
            return false;
        }
        checkRoadPlacementThatHopefullyGetsDeleted(one, two, p);
        return true;
    }

    private boolean checkValidRoadPlacementLocation(int one, int two, Player p, boolean init) {
        if (invalidRoadIndices(one, two) || roadAlreadyExistsOnIntersections(one, two, init) ||
            intersectionsNotOwnedByPlayer(one, two, p) || nonAdjacentIntersections(one, two)) {
            return false;
        }
        return true;
    }

    private boolean nonAdjacentIntersections(int one, int two) {
        return !intersections[one].getAdjacentIntersections().contains(two);
    }

    private boolean intersectionsNotOwnedByPlayer(int one, int two, Player p) {
        return !intersections[one].ownedByThisPlayer(p) && !intersections[two].ownedByThisPlayer(p);
    }

    private boolean roadAlreadyExistsOnIntersections(int one, int two, boolean init) {
        if (initCheck(one, two, init)) {
            return roads.contains(intersections[one]) || roads.contains(intersections[two]);
        }
        return roads.contains(intersections[one]) && roads.contains(intersections[two]);
    }

    boolean initCheck(int one, int two, boolean init) {
        if(init) {
            return !(intersections[one].getStructure() == null
                 && intersections[two].getStructure() == null);
        }
        return false;
    }

    private boolean invalidRoadIndices(int one, int two) {
        return one < 0 | one > MAX_INTERSECTION_INDEX | two < 0 | two > MAX_INTERSECTION_INDEX;
    }

    private void checkRoadPlacementThatHopefullyGetsDeleted(int one, int two, Player p) {
        roads.add(intersections[one]);
        roads.add(intersections[two]);
        intersections[one].setOwner(p);
        intersections[two].setOwner(p);
    }

    public boolean placeRoad(int one, int two, Player p, boolean init) {
        if (checkRoadPlacementLocation(one, two, p, init)) {
            makeRoad(new Road(), one, two, p);
            return true;
        }
        return false;
    }

    public boolean buildRoad(int one, int two, Player p) {
        if (checkRoadPlacementLocation(one, two, p, false) && checkHasRoadResources(p)) {
            makeRoadAndDecrementCount(one, two, p);
            return true;
        }
        return false;
    }

    private void makeRoadAndDecrementCount(int one, int two, Player p) {
        makeRoad(new Road(), one, two, p);
        p.setNumRoads(p.getNumRoads() - 1);
    }

    void makeRoad(Road road, int one, int two, Player p) {
        setRoadIntersectionsAndOwner(one, two, p, road);
        roadsOnBoard.add(road);
        intersections[one].setRoads(road);
        intersections[two].setRoads(road);
    }

    private void setRoadIntersectionsAndOwner(int one, int two, Player p, Road road) {
        road.setIntersections(intersections[one], intersections[two]);
        road.setOwner(p);
    }

    private boolean checkCityPlacementLocation(int index, Player player) {
        if (index < 0 || index > MAX_INTERSECTION_INDEX)    return false;
        Intersection intersection = intersections[index];
        if (intersection.getStructure() == null)    return false;
        return intersection.getStructure().getOwner() == player &&
               intersection.getStructure() instanceof Settlement;
    }

    public boolean buildCity(int index, Player player) {
        if (checkCityPlacementLocation(index, player) && checkHasCityResources(player)) {
            placeCity(new City(), index, player);
            return true;
        }
        return false;
    }

    void placeCity(City city, int index, Player player) {

        city.setOwner(player);
        replaceSettlementWithCity(city, index);
        player.setNumCities(player.getNumCities() - 1);
    }

    private void replaceSettlementWithCity(City city, int index) {
        Structure structure = intersections[index].getStructure();
        structures.remove(structure);
        structures.add(city);
        intersections[index].setStructure(city);
    }

    boolean checkHasCityResources(Player player) {
        return player.hasResources(City.getCost()) && player.getNumCities() > 0;
    }

    boolean checkHasRoadResources(Player p) {
        return p.hasResources(Road.getCost()) && p.getNumRoads() > 0;
    }

    public void setIntersection(int index, Intersection intersection) {
        intersections[index] = intersection;
    }

    public List<ResourceType> distributeInitialResources(int intersection) {
        Hexagon[] hexesToDistribute = intersections[intersection].getHexagons();
        ArrayList<ResourceType> resources = new ArrayList<>();
        distributeResourcesAtEachHex(hexesToDistribute, resources);

        return resources;
    }

    private void distributeResourcesAtEachHex(Hexagon[] hexesToDistribute,
        ArrayList<ResourceType> resources) {
        for (Hexagon hex : hexesToDistribute) {
            distributeResourcesAtHex(hex, resources);
        }
    }

    private void distributeResourcesAtHex(Hexagon hex, ArrayList<ResourceType> resources) {
        ResourceType resourceType = hex.getResource();
        if (resourceType != null) {
            resources.add(resourceType);
        }
    }

    public Color getIntersectionSettlementColor(int index) {
        return intersections[index].getStructure().getOwner().getPlayerColor();
    }

    protected Collection<Structure> getStructures() {
        return structures;
    }

    public Structure getIntersectionSettlement(int index) {
        return intersections[index].getStructure();
    }

    public ArrayList<Road> getRoadsOnBoard() {
        return new ArrayList<>(roadsOnBoard);
    }

    Road getRoad(int i, int i1) {
        for (Road road : roadsOnBoard) {
            if (roadFound(i, i1, road)) return road;
        }
        return null;
    }

    boolean roadFound(int i, int i1, Road road) {
        return road.getIntersections()[0].equals(intersections[i]) &&
               road.getIntersections()[1].equals(intersections[i1]);
    }

    public void moveRobber(int hexIndex) {
        for (Hexagon hex : hexagons) {
            if (hex.getHasRobber()) hex.setHasRobber(false);
        }
        hexagons[hexIndex].setHasRobber(true);
    }

    public int getRobberLocation() {
        for (int i = 0; i < hexagons.length; i++) {
            if (hexagons[i].getHasRobber()) return i;
        }
        return -1;
    }

    public ArrayList<Player> getHexagonPlayers() {
        int robberLocation = getRobberLocation();
        Hexagon robberHex = hexagons[robberLocation];
        List<Player> players = getPlayersOnHex(robberHex);
        return new ArrayList<>(players);
    }

    private List<Player> getPlayersOnHex(Hexagon hex) {
        List<Player> players = new ArrayList<>();
        for (Intersection inter : hex.getIntersections()) {
            if (inter.getStructure() != null)   players.add(inter.getStructure().getOwner());
        }
        return players;
    }

    public ResourceType stealResource(Player currentPlayer, Player selectedPlayerToSteal) {
        ArrayList<ResourceType> resources = new ArrayList<>(selectedPlayerToSteal.getResources());
        if (resources.isEmpty())    return null;
        return swapRandomResource(currentPlayer, selectedPlayerToSteal, resources);
    }

    private ResourceType swapRandomResource(Player currentPlayer, Player selectedPlayerToSteal,
        ArrayList<ResourceType> resources) {
        int index = rand.nextInt(resources.size());
        ResourceType resource = resources.get(index);
        swapResourceBetweenPlayers(currentPlayer, selectedPlayerToSteal, resource);
        return resource;
    }

    private void swapResourceBetweenPlayers(Player currentPlayer, Player selectedPlayerToSteal,
        ResourceType resource) {
        selectedPlayerToSteal.removeResource(resource);
        currentPlayer.addResource(resource);
    }

    public int distributeResourcesOnRoll(int currentDiceRoll, Bank bank) {
        int returnValue = 0;
        for (Hexagon hex : hexagons) {
            returnValue = giveResourceBasedOnRoll(currentDiceRoll, bank, hex);
        }
        return returnValue;
    }

    private int giveResourceBasedOnRoll(int currentDiceRoll, Bank bank, Hexagon hex) {
        int returnValue = 0;
        if (hex.getValue() == currentDiceRoll && !hex.getHasRobber()) {
            returnValue = checkStructureExistsAndTryToGiveResource(bank, hex);
        } else if (hex.getValue() == currentDiceRoll && hex.getHasRobber()) returnValue = 2;
        return returnValue;
    }

    private int checkStructureExistsAndTryToGiveResource(Bank bank, Hexagon hex) {
        ResourceType resource = hex.getResource();
        Intersection[] intersections = hex.getIntersections();
        for (Intersection inter : intersections)
            if (inter.getStructure() != null) return tryAddResourcesFromRoll(bank, inter, resource);
        return 0;
    }

    private int tryAddResourcesFromRoll(Bank bank, Intersection inter,
        ResourceType resource) {
        Player player = inter.getStructure().getOwner();
        return tryToAddFromCityOrSettlement(bank, inter, resource, player);
    }

    @SuppressWarnings("methodlength")
    private int tryToAddFromCityOrSettlement(Bank bank, Intersection inter,
        ResourceType resource, Player player) {
        if (inter.getStructure() instanceof City) {
            if (bank.obtainResource(resource, 2)) {
                player.addResource(resource);
                player.addResource(resource);
            } else  return 1;
        } else if (bank.obtainResource(resource, 1))    player.addResource(resource);
        else  return 1;
        return 0;
    }
    public ArrayList<Port> getPorts(){
        return new ArrayList<>(ports);
    }

    public int[][] getPortIntersections() {
        return PORT_INTERSECTIONS.clone();
    }

    public boolean removeRoad(int i, int i1) {
        Road road = getRoad(i, i1);
        if (road == null)   return false;
        removeRoadFromPlay(i, i1, road);
        return true;
    }

    private void removeRoadFromPlay(int i, int i1, Road road) {
        roadsOnBoard.remove(road);
        removeFromBoard(i, i1, road);
        road.setIntersections(null, null);
    }

    private void removeFromBoard(int i, int i1, Road road) {
        roads.remove(intersections[i]);
        roads.remove(intersections[i1]);
        intersections[i].removeRoad(road);
        intersections[i1].removeRoad(road);
    }

    protected static class IntersectionHelper {

        Intersection[] intersections;

        double offsetX;

        double offsetY;

        int index;

        int currentRowSize;

        public IntersectionHelper() {
            this.intersections = new Intersection[NUM_INTERSECTIONS];
            this.index = 0;
            offsetX = 0;
            offsetY = 0;
        }

        public void updateCurrentRowSize(int index) {
            currentRowSize = INTERSECTION_MID_ROW_SIZE - index;
        }

        public void updateXBaseOffset(int row) {
            if (row % 2 != 0) {
                offsetX = 0;
            } else {
                offsetX = 0.5;
            }
        }

        public void updateYBaseOffset(int row) {
            offsetY = row * Math.sqrt(0.5);
        }

        public void updateBaseOffset(int row) {
            updateXBaseOffset(row);
            updateYBaseOffset(row);
        }
    }

    static class HexagonHelper {

        private static final int NUM_HEXAGONS = 19;

        private static final int HEX_MID_ROW_SIZE = 5;
        Hexagon[] hexagons;

        double offsetX;

        double offsetY;

        int index;

        int currentRowSize;

        ArrayList<ResourceType> resourceTypes;
        ArrayList<Integer> numberTokens;

        Shuffler shuffler;

        public HexagonHelper(Shuffler shuffler) {
            initializeSimpleFields();
            resourceTypes = shuffler.getShuffledResourceTypes();
            numberTokens = shuffler.getShuffledNumberTokens();
            this.shuffler = shuffler;
        }

        private void initializeSimpleFields() {
            this.hexagons = new Hexagon[NUM_HEXAGONS];
            this.index = 0;
            offsetX = 0;
            offsetY = 0;
        }

        public void updateCurrentRowSize(int index) {
            currentRowSize = HEX_MID_ROW_SIZE - index;
        }

        public void updateXBaseOffset(int row) {
            if (row % 2 != 0) {
                offsetX = 0.5;
            } else {
                offsetX = 0;
            }
        }

        public void updateYBaseOffset(int row) {
            offsetY = row * Math.sqrt(0.5);
        }

        public void updateBaseOffset(int row) {
            updateXBaseOffset(row);
            updateYBaseOffset(row);
        }
    }

}

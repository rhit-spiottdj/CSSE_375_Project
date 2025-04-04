package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.easymock.EasyMock.expect;
import static org.junit.jupiter.api.Assertions.*;

public class ShufflerTest {

    @Test
    public void testShuffle_validateShuffledResourcesSize() {

        Shuffler shuffler = new Shuffler();

        assertEquals(18, shuffler.getShuffledResourceTypes().size());
    }

    @Test
    public void testShuffle_validateCollectionShuffled() {

        Shuffler shuffler = new Shuffler();

        ArrayList<ResourceType> nonShuffled = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            nonShuffled.add(ResourceType.GRAIN);
            nonShuffled.add(ResourceType.LUMBER);
            nonShuffled.add(ResourceType.WOOL);
        }
        for (int i = 0; i < 3; i++) {
            nonShuffled.add(ResourceType.ORE);
            nonShuffled.add(ResourceType.BRICK);
        }

        assertNotEquals(nonShuffled, shuffler.getShuffledResourceTypes());

    }

    @Test
    public void testShuffle_validateShuffledResources() {

        Shuffler shuffler = new Shuffler();

        ArrayList<ResourceType> shuffled = shuffler.getShuffledResourceTypes();

        int wheatCount = 0;
        int woodCount = 0;
        int sheepCount = 0;
        int oreCount = 0;
        int brickCount = 0;
        int desertCount = 0;

        for (ResourceType resource : shuffled) {
            switch (resource) {
                case GRAIN:
                    wheatCount++;
                    break;
                case LUMBER:
                    woodCount++;
                    break;
                case WOOL:
                    sheepCount++;
                    break;
                case ORE:
                    oreCount++;
                    break;
                case BRICK:
                    brickCount++;
                    break;
                default:
                    break;
            }
        }

        assertEquals(4, wheatCount);
        assertEquals(4, woodCount);
        assertEquals(4, sheepCount);
        assertEquals(3, oreCount);
        assertEquals(3, brickCount);
    }

    @Test
    public void testShuffle_validateShuffledNumberTokensSize() {

        Shuffler shuffler = new Shuffler();

        assertEquals(18, shuffler.getShuffledNumberTokens().size());
    }

    @Test
    public void testShuffle_validateCollectionShuffledNumberTokens() {

        Shuffler shuffler = new Shuffler();

        ArrayList<Integer> nonShuffled = new ArrayList<>();
        for (int i = 3; i <= 11; i++) {
            if (i != 7) {
                nonShuffled.add(i);
                nonShuffled.add(i);
            }
        }
        nonShuffled.add(2);
        nonShuffled.add(12);

        assertNotEquals(nonShuffled, shuffler.getShuffledNumberTokens());

    }

    @Test
    public void testShuffle_validateShuffledNumberTokens() {

        Shuffler shuffler = new Shuffler();

        ArrayList<Integer> shuffled = shuffler.getShuffledNumberTokens();

        int[] numberTokenCounts = new int[13];

        for (int numberToken : shuffled) {
            numberTokenCounts[numberToken]++;
        }

        for (int i = 3; i <= 11; i++) {
            if (i != 7) {
                assertEquals(2, numberTokenCounts[i]);
            }
        }

        assertEquals(1, numberTokenCounts[2]);
        assertEquals(1, numberTokenCounts[12]);
    }

    @Test
    public void testShuffler_validateGetHexagonValues() {
        BoardManager boardManager = EasyMock.createMock(BoardManager.class);

        Integer[] numbersForHexagons = {6,4,11,0,8,10,2,10,9,4,12,9,3,5,11,6,3,8,5};

        Hexagon[] expectedHexagons = new Hexagon[19];

        Double[][] pointPairs = {{0.0,0.0}, {1.0, 0.0}, {-1.0,0.0}, {2.0,0.0}, {-2.0,0.0},
                                 {0.5, 0.7071067811865476},{-0.5, 0.7071067811865476},
                                 {1.5, 0.7071067811865476}, {-1.5, 0.7071067811865476},
                                 {0.5, -0.7071067811865476}, {-0.5, -0.7071067811865476},
                                 {1.5, -0.7071067811865476}, {-1.5, -0.7071067811865476},
                                 {0.0, 1.4142135623730951}, {1.0, 1.4142135623730951},
                                 {-1.0, 1.4142135623730951}, {0.0, -1.4142135623730951},
                                 {1.0, -1.4142135623730951}, {-1.0, -1.4142135623730951}};

        for (int i = 0; i < 19; i++) {
            Point2D point = EasyMock.createMock(Point2D.class);
            expect(point.getX()).andReturn(pointPairs[i][0]);
            expect(point.getY()).andReturn(pointPairs[i][1]);
            EasyMock.replay(point);
            Hexagon hexagon = EasyMock.createMock(Hexagon.class);
            expect(hexagon.getCenter()).andReturn(point);
            expect(hexagon.getValue()).andReturn(numbersForHexagons[i]);
            expectedHexagons[i] = hexagon;
            EasyMock.replay(hexagon);
        }

        EasyMock.expect(boardManager.generateHexagons(false)).andReturn(expectedHexagons);

        EasyMock.replay(boardManager);

        Shuffler shuffler = new Shuffler();

        int[][] actualValues = shuffler.getHexagonValues(boardManager.generateHexagons(false));

        int[][] expectedValues = {
                {-1, -1, 5, 3, 8},
                {-1, 3, 12, 4, 9},
                {8, 11, 6, 4, 0},
                {9, 2, 10, 10,-1},
                {6, 5, 11, -1, -1}
        };
        assertArrayEquals(expectedValues, actualValues);
        EasyMock.verify(boardManager);
    }

    @Test
    public void testShuffle_createShuffledPortTokens(){
        Shuffler shuffler = new Shuffler();
        ArrayList<Port> ports = shuffler.getShuffledPortTokens();

        int numThreeToOnePorts = 0;
        int expectedNumThreeToOnePorts = 4;

        ArrayList<ResourceType> resourcePorts = new ArrayList<>();

        for (Port port: ports){
            if(port.getPortTradeRatio() == 3){
                numThreeToOnePorts++;
            }else if (port.getPortTradeRatio() == 2){
                resourcePorts.add(port.getResourceType());
            }
        }

        for(ResourceType resource: ResourceType.values()){
            assertTrue(resourcePorts.remove(resource));
        }
        assertTrue(resourcePorts.isEmpty());

        assertEquals(expectedNumThreeToOnePorts, numThreeToOnePorts);

        assertEquals(9, ports.size());
    }

    @Test
    public void testShuffle_validateEnsureNoNeighborSixOrEight() {

        Random random = EasyMock.createMock(Random.class);
        expect(random.nextInt(5)).andReturn(4);
        expect(random.nextInt(5)).andReturn(2);

        EasyMock.replay(random);
        Shuffler shuffler = new Shuffler();
        int[][] hexagonValues = {
                {-1, -1, 6, 12, 8},
                {-1, 8, 11, 5, 10},
                {4, 9, 5, 11, 4},
                {6, 0, 10, 9,-1},
                {2, 3, 3, -1, -1}
        };
        int[][] expectedValues = {
                {-1, -1, 3, 12, 8},
                {-1, 8, 11, 5, 10},
                {4, 9, 5, 11, 4},
                {6, 0, 10, 9,-1},
                {2, 3, 6, -1, -1}
        };
        shuffler.hexagonValues = hexagonValues;
        shuffler.ensureNoNeighborSixOrEight(random);
        assertArrayEquals(expectedValues, hexagonValues);

        EasyMock.verify(random);
    }

    @Test
    public void testShuffler_testSwapCell_expectSwapped() {
        Random rand = EasyMock.createMock(Random.class);

        Shuffler shuffler = new Shuffler();

        final int[] PRESET_BOARD_ROW_1 = {-1, -1, 8, 2, 9};
        final int[] PRESET_BOARD_ROW_2 = {-1, 12, 6, 4, 10};
        final int[] PRESET_BOARD_ROW_3 = {9, 11, 0, 3, 10};
        final int[] PRESET_BOARD_ROW_4 = {8, 3, 4, 5, -1};
        final int[] PRESET_BOARD_ROW_5 = {5, 6, 11, -1, -1};
        int[][] hexagonValues =
            new int[][]{PRESET_BOARD_ROW_1, PRESET_BOARD_ROW_2, PRESET_BOARD_ROW_3,
                PRESET_BOARD_ROW_4, PRESET_BOARD_ROW_5};

        shuffler.hexagonValues = hexagonValues;

        expect(rand.nextInt(5)).andReturn(0);
        expect(rand.nextInt(5)).andReturn(2);

        expect(rand.nextInt(5)).andReturn(2);
        expect(rand.nextInt(5)).andReturn(4);



        EasyMock.replay(rand);

        shuffler.swapCell(rand, 0, 2);

        int[][] expectedValues = {
            {-1, -1, 10, 2, 9},
            {-1, 12, 6, 4, 10},
            {9, 11, 0, 3, 8},
            {8, 3, 4, 5, -1},
            {5, 6, 11, -1, -1}};

        assertArrayEquals(expectedValues, hexagonValues);



    }

    @Test
    public void testShuffler_testIsValidNeighbor_expectFalseLessThanZero() {
        Shuffler shuffler = new Shuffler();


        shuffler.hexagonValues = new int[5][5];

        assertFalse(shuffler.isValidNeighbor(0, 0, -1, 0));

        assertFalse(shuffler.isValidNeighbor(0, 0, 0, -1));
    }

    @Test
    public void testShuffler_testIsValidNeighbor_ExactlyZero() {
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[5][5];

        assertTrue(shuffler.isValidNeighbor(0, 0, 0, 0));
    }

    @Test
    public void testShuffler_testIsValidNeighbor_expectFalseGreaterThanFour() {
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[5][5];

        assertFalse(shuffler.isValidNeighbor(0, 0, 5, 0));

        assertFalse(shuffler.isValidNeighbor(0, 0, 0, 5));
    }

    @Test
    public void testShuffler_testIsValidNeighbor_ExactlyLengthMinusOne() {
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[5][5];

        int lastRow = shuffler.hexagonValues.length - 1;
        assertFalse(shuffler.isValidNeighbor(lastRow, 0, 1, 0));
        assertTrue(shuffler.isValidNeighbor(lastRow, 0, 0, 0));

        int lastRowLength = shuffler.hexagonValues[0].length - 1;
        assertFalse(shuffler.isValidNeighbor(0, lastRowLength, 0, 1));
        assertTrue(shuffler.isValidNeighbor(0, lastRowLength, 0, 0));
    }

    int[][] hexValues = {
        {-1, -1, 10, 2, 9},
        {-1, 12, 6, 4, 10},
        {9, 11, 0, 3, 8},
        {8, 3, 4, 5, -1},
        {5, 6, 11, -1, -1}};


    @Test
    public void testIsHasNeighborSixOrEight() {
        Random mockRandom = EasyMock.createMock(Random.class);
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = hexValues;

        EasyMock.expect(mockRandom.nextInt(shuffler.hexagonValues.length)).andReturn(0);
        EasyMock.expect(mockRandom.nextInt(shuffler.hexagonValues[0].length)).andReturn(2);

        EasyMock.replay(mockRandom);

        boolean hasNeighborSixOrEight = false;

        boolean result = shuffler.isHasNeighborSixOrEight(mockRandom, hasNeighborSixOrEight);

        EasyMock.verify(mockRandom);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1})
    public void testEnsureShufflePorts_noShuffle(int numPorts){
        Shuffler shuffler = new Shuffler();
        ArrayList<Port> portTokens = new ArrayList<>();
        for(int i = 0; i < numPorts; i++){
            portTokens.add(EasyMock.createMock(Port.class));
        }

        ArrayList<Port> dupeTokens = new ArrayList<>(portTokens);

        shuffler.ensureShufflePorts(portTokens);

        assertArrayEquals(dupeTokens.toArray(), portTokens.toArray());

    }

    @Test
    public void testEnsureShufflePorts_simpleShuffle(){
        Shuffler shuffler = new Shuffler();
        ArrayList<Port> portTokens = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            portTokens.add(EasyMock.createMock(Port.class));
        }

        ArrayList<Port> dupeTokens = new ArrayList<>(portTokens);

        shuffler.ensureShufflePorts(portTokens);

        assertFalse(Arrays.equals(dupeTokens.toArray(), portTokens.toArray()));
    }

    @Test
    public void testEnsureShufflePorts_fullShuffle(){
        Shuffler shuffler = new Shuffler();
        ArrayList<Port> portTokens = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            portTokens.add(EasyMock.createMock(Port.class));
        }

        ArrayList<Port> dupeTokens = new ArrayList<>(portTokens);

        shuffler.ensureShufflePorts(portTokens);

        assertFalse(Arrays.equals(dupeTokens.toArray(), portTokens.toArray()));
    }

    @Test
    public void testCreateShuffledPortTokens(){
        Shuffler shuffler = new Shuffler();
        ArrayList<Port> ports = shuffler.createShuffledPortTokens();

        assertEquals(9, ports.size());

        boolean result = true;
        for(int i = 0; i < 4; i++){
            result = result && PortTradeRatio.THREE_TO_ONE.equals(ports.get(i).getPortTradeRatio());
        }

        int index = 4;
        for(ResourceType resource: ResourceType.values()){
            result =
                result && PortTradeRatio.TWO_TO_ONE.equals(ports.get(index).getPortTradeRatio());
            result = result && resource.equals(ports.get(index).getResourceType());
            index++;
        }
        assertFalse(result);

    }

    @ParameterizedTest
    @ValueSource(ints = {6,8})
    public void testHasAdjacentSixOrEight_allSides(int sixOrEight){
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[][]{{0,sixOrEight,0},{sixOrEight,0,sixOrEight},{0,
            sixOrEight,0}};

        assertTrue(shuffler.hasAdjacentSixOrEight(1,1));
    }

    @Test
    public void testHasAdjacentSixOrEight_noAdjacent(){
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[][]{{0,0,0},{0,0,0},{0,
            0,0}};

        assertFalse(shuffler.hasAdjacentSixOrEight(1,1));
    }

    @Test
    public void testHasAdjacentSixOrEight_sideOneAdjacent(){
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[][]{{0,6,0},{0,0,0},{0,
            0,0}};

        assertTrue(shuffler.hasAdjacentSixOrEight(1,1));
    }

    @Test
    public void testHasAdjacentSixOrEight_sideTwoAdjacent(){
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[][]{{0,0,0},{0,0,6},{0,
            0,0}};

        assertTrue(shuffler.hasAdjacentSixOrEight(1,1));
    }

    @Test
    public void testHasAdjacentSixOrEight_sideThreeAdjacent(){
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[][]{{0,0,0},{6,0,0},{0,
            0,0}};

        assertTrue(shuffler.hasAdjacentSixOrEight(1,1));
    }

    @Test
    public void testHasAdjacentSixOrEight_sideFourAdjacent(){
        Shuffler shuffler = new Shuffler();

        shuffler.hexagonValues = new int[][]{{0,6,0},{0,0,0},{0,
            6,0}};

        assertTrue(shuffler.hasAdjacentSixOrEight(1,1));
    }




}

package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import presentation.GameDisplay;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

import static domain.ResourceType.GRAIN;
import static org.easymock.EasyMock.anyObject;
import static org.junit.jupiter.api.Assertions.*;


public class BoardManagerTests {
    BoardManager bm;

    @BeforeEach
    public void setup() {
        bm = new BoardManager();
    }

    @Test
    public void testGenerateHex_validateUniqueness() {
        Hexagon[] result = bm.generateHexagons(false);
        ArrayList<Point2D> centers = new ArrayList<>();
        for (Hexagon hex : result) {
            centers.add(hex.getCenter());
        }
        Set<Point2D> centerSet = new HashSet<Point2D>(centers);
        ArrayList<Point2D> noDuplicates = new ArrayList<>();
        noDuplicates.addAll(centerSet);
        assertEquals(noDuplicates.size(), centers.size());
    }
    
    @Test
    public void testGenerateHexValidateGrainResources() {
    	Hexagon[] result = bm.generateHexagons(false);
    	
    	int grainCount = 0;
    	
    	for (Hexagon hex : result) {
    		if (hex.getResource() == ResourceType.GRAIN) {
    			grainCount++;
    		}
    	}
    	assertEquals(4, grainCount);
    }
    
    @Test
    public void testGenerateHexValidateLumberResources() {
    	Hexagon[] result = bm.generateHexagons(false);
    	
    	int lumberCount = 0;
    	
    	for (Hexagon hex : result) {
    		if (hex.getResource() == ResourceType.LUMBER) {
    			lumberCount++;
    		}
    	}
    	assertEquals(4, lumberCount);
    }
    
    @Test
    public void testGenerateHexValidateWoolResources() {
    	Hexagon[] result = bm.generateHexagons(false);
    	
    	int woolCount = 0;
    	
    	for (Hexagon hex : result) {
    		if (hex.getResource() == ResourceType.WOOL) {
    			woolCount++;
    		}
    	}
    	assertEquals(4, woolCount);
    }
    
    @Test
    public void testGenerateHexValidateBrickResources() {
    	Hexagon[] result = bm.generateHexagons(false);
    	
    	int brickCount = 0;
    	
    	for (Hexagon hex : result) {
    		if (hex.getResource() == ResourceType.BRICK) {
    			brickCount++;
    		}
    	}
    	assertEquals(3, brickCount);
    }
    
    @Test
    public void testGenerateHexValidateOreResources() {

    	Hexagon[] result = bm.generateHexagons(false);
    	
    	int oreCount = 0;
    	
    	for (Hexagon hex : result) {
    		if (hex.getResource() == ResourceType.ORE) {
    			oreCount++;
    		}
    	}
    	assertEquals(3, oreCount);
    }
    
    @Test
    public void testGenerateHexValidateNumberTokensDesert()  {

    	Hexagon[] result = bm.generateHexagons(false);
    	
    	int desertCount = 0;
    	
    	for (Hexagon hex : result) {
    		if (hex.getValue() == 0) {
    			desertCount++;
    		}
    	}
    	assertEquals(1, desertCount);
    }
    
    @Test
    public void testGenerateHexValidateNumberTokensTwoCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int twoCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 2) {
    			twoCount++;
    		}
    	}
    	assertEquals(1, twoCount);
    }

    @Test
    public void testGenerateHexValidateNumberTokensThreeCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int threeCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 3) {
    			threeCount++;
    		}
    	}
    	assertEquals(2, threeCount);
    }

    @Test
    public void testGenerateHexValidateNumberTokensFourCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int fourCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 4) {
    			fourCount++;
    		}
    	}
    	assertEquals(2, fourCount);
    }

    @Test
    public void testGenerateHexValidateNumberTokensFiveCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int fiveCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 5) {
    			fiveCount++;
    		}
    	}
    	assertEquals(2, fiveCount);
    }

    @Test
    public void testGenerateHexValidateNumberTokensSixCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int sixCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 6) {
    			sixCount++;
    		}
    	}
    	assertEquals(2, sixCount);
    }

    @Test
    public void testGenerateHexValidateNumberTokensEightCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int eightCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 8) {
    			eightCount++;
    		}
    	}
    	assertEquals(2, eightCount);
    }

    @Test
    public void testGenerateHexValidateNumberTokensNineCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int nineCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 9) {
    			nineCount++;
    		}
    	}
    	assertEquals(2, nineCount);
    }

    @Test
    public void testGenerateHexValidateNumberTenCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int tenCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 10) {
    			tenCount++;
    		}
    	}
    	assertEquals(2, tenCount);
    }

    @Test
    public void testGenerateHexValidateNumberTokensElevenCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int elevenCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 11) {
    			elevenCount++;
    		}
    	}
    	assertEquals(2, elevenCount);
    }

    @Test
    public void testGenerateHexValidateNumberTokensTwelveCount()  {

    	Hexagon[] result = bm.generateHexagons(false);

    	int twelveCount = 0;

    	for (Hexagon hex : result) {
    		if (hex.getValue() == 12) {
    			twelveCount++;
    		}
    	}
    	assertEquals(1, twelveCount);
    }

    @Test
    public void testGenerateIntersections_validateUniqueness() {

        Intersection[] result = bm.generateIntersections();
        ArrayList<Point2D> centers = new ArrayList<>();
        for (Intersection inter : result) {
            centers.add(inter.getCenter());
        }
        Set<Point2D> centerSet = new HashSet<Point2D>(centers);
        ArrayList<Point2D> noDuplicates = new ArrayList<>();
        noDuplicates.addAll(centerSet);
        assertEquals(noDuplicates.size(), centers.size());
    }

    @Test
    public void testInitializeBoardStructure_validateHexagonsHaveIntersections() {

        Hexagon[] hexagons = bm.initializeBoardStructure(false);
        for (Hexagon hex : hexagons) {
            assertEquals(6, hex.getIntersections().length);
        }
    }

    @Test
    public void testMakeAdjacentIntersections() {

        Intersection[] intersections = bm.generateIntersections();
        bm.makeAdjacentIntersections(intersections);
        assertEquals(3, intersections[0].getAdjacentIntersections().size());
        assertEquals(3, intersections[1].getAdjacentIntersections().size());
        assertEquals(3, intersections[2].getAdjacentIntersections().size());
    }

    @Test
    public void testMakeAdjacentIntersections2() {

        Intersection[] intersections = bm.generateIntersections();
        bm.makeAdjacentIntersections(intersections);
        for (Intersection inter : intersections) {
            if (inter.getUniqueIndex() == 10) {
                assertEquals(2, inter.getAdjacentIntersections().size());
            }
        }
    }

    @Test
    public void testCheckSettlementPlacementLocation() {

        bm.generateIntersections();
        assertFalse(bm.checkSettlementPlacementLocation(-1));
    }

    @Test
    public void testCheckSettlementPlacementLocation2() {

        bm.generateIntersections();
        assertTrue(bm.checkSettlementPlacementLocation(0));
    }

    @Test
    public void testCheckSettlementPlacementLocation3() {

        bm.generateIntersections();
        assertTrue(bm.checkSettlementPlacementLocation(1));
    }

    @Test
    public void testCheckSettlementPlacementLocation4() {

        bm.generateIntersections();
        assertTrue(bm.checkSettlementPlacementLocation(53));
    }

    @Test
    public void testCheckSettlementPlacementLocation5() {

        bm.generateIntersections();
        assertFalse(bm.checkSettlementPlacementLocation(54));
    }

    @Test
    public void testCheckSettlementPlacementLocation6() {

        bm.generateIntersections();
        assertTrue(bm.checkSettlementPlacementLocation(0));
        assertFalse(bm.checkSettlementPlacementLocation(6));
    }

    @Test
    public void testCheckSettlementPlacementLocation7() {

        bm.generateIntersections();
        assertTrue(bm.checkSettlementPlacementLocation(0));
        assertTrue(bm.checkSettlementPlacementLocation(1));
        assertTrue(bm.checkSettlementPlacementLocation(2));
        assertFalse(bm.checkSettlementPlacementLocation(12));
    }


    @Test
    public void testCheckSettlementPlacementLocation8() {

        bm.generateIntersections();
        for (int i = 0; i < 54; i++) {
            bm.checkSettlementPlacementLocation(i);
        }
        for (int i = 0; i < 54; i++) {
            assertFalse(bm.checkSettlementPlacementLocation(i));
        }
    }

    @Test
    public void testCheckRoadPlacementLocation1() {

        bm.generateIntersections();
        Player player = EasyMock.createMock(Player.class);
        assertFalse(bm.checkRoadPlacementLocation(-1, 0, player, false));
    }

    @Test
    public void testCheckRoadPlacementLocation2() {

        bm.generateIntersections();
        Player player = EasyMock.createMock(Player.class);
        assertFalse(bm.checkRoadPlacementLocation(0, -1, player, false));
    }

    @Test
    public void testCheckRoadPlacementLocation3() {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.replay(settlement);
        intersections[1].setStructure(settlement);


        assertTrue(bm.checkRoadPlacementLocation(1, 7, player, false));
        EasyMock.verify(settlement);
    }

    @Test
    public void testCheckRoadPlacementLocation4() {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.replay(settlement);
        intersections[53].setStructure(settlement);

        assertTrue(bm.checkRoadPlacementLocation(53, 47, player, false));
        EasyMock.verify(settlement);
    }

    @Test
    public void testCheckRoadPlacementLocation5() {

        bm.generateIntersections();
        Player player = EasyMock.createMock(Player.class);
        assertFalse(bm.checkRoadPlacementLocation(54, 0, player, false));
    }

    @Test
    public void testCheckRoadPlacementLocation6() {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.replay(settlement);
        intersections[0].setStructure(settlement);

        assertTrue(bm.checkRoadPlacementLocation(0, 6, player, false));
        assertFalse(bm.checkRoadPlacementLocation(6, 0, player, false));
        EasyMock.verify(settlement);
    }

    @Test
    public void testCheckRoadPlacementLocation7() {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.replay(settlement);
        intersections[0].setStructure(settlement);

        assertTrue(bm.checkRoadPlacementLocation(0, 12, player, false));
        assertTrue(bm.checkRoadPlacementLocation(12, 1, player, false));
        assertTrue(bm.checkRoadPlacementLocation(1, 7, player, false));
        assertFalse(bm.checkRoadPlacementLocation(12, 14, player, false));
        EasyMock.verify(settlement);
    }

    @Test
    public void testCheckRoadPlacementLocation8() {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        for (int i = 0; i < 54; i++) {
            EasyMock.expect(settlement.getOwner()).andReturn(player);
        }
        EasyMock.replay(settlement);
        for (int i = 0; i < 54; i++) {
            Intersection in = intersections[i];
            in.setStructure(settlement);
            bm.setIntersection(i, in);
        }
        for (int i = 0; i < 54; i++) {
            for (int j = 0; j < 54; j++) {
                bm.checkRoadPlacementLocation(i, j, player, false);
            }
        }
        for (int i = 0; i < 54; i++) {
            for (int j = 0; j < 54; j++) {
                assertFalse(bm.checkRoadPlacementLocation(i, j, player, false));
            }
        }
    }

    @Test
    public void testCheckRoadPlacementLocation9() {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        Player opponent = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(opponent);
        EasyMock.replay(settlement);
        intersections[0].setStructure(settlement);

        assertFalse(bm.checkRoadPlacementLocation(0, 6, player, false));
        EasyMock.verify(settlement);
    }

    @Test
    public void testCheckRoadPlacementLocation10() {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.replay(settlement);
        intersections[0].setStructure(settlement);

        assertTrue(bm.checkRoadPlacementLocation(0, 6, player, false));
        assertTrue(bm.checkRoadPlacementLocation(0, 12, player, false));
        assertTrue(bm.checkRoadPlacementLocation(0, 14, player, false));
        EasyMock.verify(settlement);
    }

    @Test
    public void testCheckRoadPlacementLocation11() {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.replay(settlement);
        intersections[0].setStructure(settlement);

        assertFalse(bm.checkRoadPlacementLocation(0, 1, player, false));
        EasyMock.verify(settlement);
    }

    @Test
    public void testCheckRoadPlacementLocation12() {

        bm.generateIntersections();
        Player player = EasyMock.createMock(Player.class);
        assertFalse(bm.checkRoadPlacementLocation(0, 54, player, false));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_getSelectedIntersection_expectValidIndex(int val) {

        bm.selectedIntersection = val;
        assertEquals(val, bm.getIntersectionSelection());
        assertEquals(-1, bm.selectedIntersection);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_getIntersectionSettlement_expectNull(int val) {

        Intersection[] intersections = bm.generateIntersections();
        intersections[val].setStructure(null);
        bm.setIntersection(val, intersections[val]);
        assertNull(bm.getIntersectionSettlement(val));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_getIntersectionSettlement_validIndexExpectSettlement(int val) {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.replay(settlement);
        intersections[val].setStructure(settlement);
        bm.setIntersection(val, intersections[val]);
        assertEquals(settlement, bm.getIntersectionSettlement(val));
        EasyMock.verify(settlement);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_getIntersectionSettlementColor_validIndexExpectColorRed(int val) {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.expect(player.getPlayerColor()).andReturn(Color.RED);
        EasyMock.replay(settlement, player);
        intersections[val].setStructure(settlement);
        bm.setIntersection(val, intersections[val]);
        assertEquals(Color.RED, bm.getIntersectionSettlementColor(val));
        EasyMock.verify(settlement, player);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBoardManager_distributeResources_indexZero(ResourceType type) {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);
        Hexagon hex3 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getResource()).andReturn(type);
        EasyMock.expect(hex2.getResource()).andReturn(type);
        EasyMock.expect(hex3.getResource()).andReturn(type);

        Intersection inter1 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.getHexagons()).andReturn(new Hexagon[]{hex1, hex2, hex3});

        EasyMock.replay(hex1, hex2, hex3, inter1);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1, hex2, hex3}, new Intersection[]{inter1});

        List<ResourceType> resources = bm.distributeInitialResources(0);

        assertEquals(3, resources.size());
        assertTrue(resources.contains(type));

        EasyMock.verify(hex1, hex2, hex3, inter1);

    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBoardManager_distributeResources_indexOne(ResourceType type) {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);
        Hexagon hex3 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getResource()).andReturn(type);
        EasyMock.expect(hex2.getResource()).andReturn(type);
        EasyMock.expect(hex3.getResource()).andReturn(type);

        Intersection inter0 = EasyMock.createMock(Intersection.class);
        Intersection inter1 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.getHexagons()).andReturn(new Hexagon[]{hex1, hex2, hex3});

        EasyMock.replay(hex1, hex2, hex3, inter1);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1, hex2, hex3}, new Intersection[]{inter0, inter1});

        List<ResourceType> resources = bm.distributeInitialResources(1);

        assertEquals(3, resources.size());
        assertTrue(resources.contains(type));

        EasyMock.verify(hex1, hex2, hex3, inter1);
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBoardManager_distributeResources_indexMaxVal(ResourceType type) {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);
        Hexagon hex3 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getResource()).andReturn(type);
        EasyMock.expect(hex2.getResource()).andReturn(type);
        EasyMock.expect(hex3.getResource()).andReturn(type);

        Intersection[] intersections = new Intersection[54];

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getHexagons()).andReturn(new Hexagon[]{hex1, hex2, hex3});
            EasyMock.replay(intersections[i]);
        }

        EasyMock.replay(hex1, hex2, hex3);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1, hex2, hex3}, intersections);

        List<ResourceType> resources = bm.distributeInitialResources(53);

        assertEquals(3, resources.size());
        assertTrue(resources.contains(type));

        EasyMock.verify(hex1, hex2, hex3);
    }


    @ParameterizedTest
    @CsvSource({"0, 6", "6, 0", "2, 8", "8, 2", "52, 42", "42, 52"})
    public void testBoardManager_placeRoad_expectRoadPlaced(int val1, int val2) {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        intersections[val1].setOwner(player);
        intersections[val2].setOwner(player);
        EasyMock.replay(settlement);
        intersections[val1].setStructure(settlement);
        intersections[val2].setStructure(settlement);
        bm.setIntersection(val1, intersections[val1]);
        bm.setIntersection(val2, intersections[val2]);
        assertEquals(bm.getIntersections()[val1].getRoads(),
            bm.getIntersections()[val2].getRoads());
        assertTrue(bm.placeRoad(val1, val2, player, false));
        EasyMock.verify(settlement);
    }

    @ParameterizedTest
    @CsvSource({"1, 53", "53, 1"})
    public void testBoardManager_placeRoad_expectRoadNotPlaced(int val1, int val2) {

        Intersection[] intersections = bm.generateIntersections();
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        EasyMock.expect(settlement.getOwner()).andReturn(player);
        intersections[val1].setOwner(player);
        intersections[val2].setOwner(player);
        EasyMock.replay(settlement);
        intersections[val1].setStructure(settlement);
        intersections[val2].setStructure(settlement);
        bm.setIntersection(val1, intersections[val1]);
        bm.setIntersection(val2, intersections[val2]);
        assertFalse(bm.placeRoad(val1, val2, player, false));
        EasyMock.verify(settlement);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_getIntersectionSelection_expectValidIndex(int val) {

        bm.selectedIntersection = val;
        assertEquals(val, bm.getIntersectionSelection());
        assertEquals(-1, bm.selectedIntersection);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 18})
    public void testBoardManager_getHexSelection_expectValidIndex(int val) {

        bm.selectedHex = val;
        assertEquals(val, bm.getHexSelection());
        assertEquals(-1, bm.selectedHex);
    }

    @Test
    public void testBoardManager_moveRobber_expectNewRobberLocationHex1() {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getHasRobber()).andReturn(true);
        hex1.setHasRobber(false);

        EasyMock.expect(hex2.getHasRobber()).andReturn(false);

        hex2.setHasRobber(true);

        EasyMock.replay(hex1, hex2);

        Hexagon[] hexagons = new Hexagon[]{hex1, hex2};

        BoardManager bm = new BoardManager(hexagons);

        bm.moveRobber(1);

        EasyMock.verify(hex1, hex2);
    }

    @Test
    public void testBoardManager_moveRobber_expectNewRobberLocationHex0() {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getHasRobber()).andReturn(false);
        hex1.setHasRobber(true);

        EasyMock.expect(hex2.getHasRobber()).andReturn(true);

        hex2.setHasRobber(false);

        EasyMock.replay(hex1, hex2);

        Hexagon[] hexagons = new Hexagon[]{hex1, hex2};

        BoardManager bm = new BoardManager(hexagons);

        bm.moveRobber(0);

        EasyMock.verify(hex1, hex2);
    }

    @Test
    public void testBoardManager_moveRobber_expectNewRobberLocationHex18() {

        Hexagon[] hexagons = new Hexagon[19];

        hexagons[0] = EasyMock.createMock(Hexagon.class);
        EasyMock.expect(hexagons[0].getHasRobber()).andReturn(true);
        hexagons[0].setHasRobber(false);

        for (int i = 1; i < 19; i++) {
            hexagons[i] = EasyMock.createMock(Hexagon.class);
            EasyMock.expect(hexagons[i].getHasRobber()).andReturn(false);
        }

        hexagons[18].setHasRobber(true);

        for (int i = 0; i < 19; i++) {
            EasyMock.replay(hexagons[i]);
        }

        BoardManager bm = new BoardManager(hexagons);

        bm.moveRobber(18);

        for (int i = 0; i < 19; i++) {
            EasyMock.verify(hexagons[i]);
        }
    }

    @Test
    public void testBoardManager_getRobberLocation_expectValidIndex0() {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getHasRobber()).andReturn(true);

        EasyMock.replay(hex1, hex2);

        Hexagon[] hexagons = new Hexagon[]{hex1, hex2};

        BoardManager bm = new BoardManager(hexagons);

        assertEquals(0, bm.getRobberLocation());

        EasyMock.verify(hex1, hex2);
    }

    @Test
    public void testBoardManager_getRobberLocation_expectValidIndex1() {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getHasRobber()).andReturn(false);
        EasyMock.expect(hex2.getHasRobber()).andReturn(true);

        EasyMock.replay(hex1, hex2);

        Hexagon[] hexagons = new Hexagon[]{hex1, hex2};

        BoardManager bm = new BoardManager(hexagons);

        assertEquals(1, bm.getRobberLocation());

        EasyMock.verify(hex1, hex2);
    }

    @Test
    public void testBoardManager_getRobberLocation_expectNegativeOne() {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getHasRobber()).andReturn(false);
        EasyMock.expect(hex2.getHasRobber()).andReturn(false);

        EasyMock.replay(hex1, hex2);

        Hexagon[] hexagons = new Hexagon[]{hex1, hex2};

        BoardManager bm = new BoardManager(hexagons);

        assertEquals(-1, bm.getRobberLocation());

        EasyMock.verify(hex1, hex2);
    }


    @Test
    public void testBoardManager_getHexagonPlayers_expectPlayer() {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getHasRobber()).andReturn(true);

        Intersection inter1 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(hex1.getIntersections()).andReturn(new Intersection[]{inter1});

        Settlement settlement = EasyMock.createMock(Settlement.class);

        EasyMock.expect(inter1.getStructure()).andReturn(settlement);
        EasyMock.expect(inter1.getStructure()).andReturn(settlement);

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player);

        EasyMock.replay(hex1, inter1, settlement, player);

        Hexagon[] hexagons = new Hexagon[]{hex1};

        BoardManager bm = new BoardManager(hexagons);

        assertEquals(1, bm.getHexagonPlayers().size());

        EasyMock.verify(hex1, inter1, settlement, player);

    }

    @Test
    public void testBoardManager_getHexagonPlayers_expectEmpty() {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getHasRobber()).andReturn(true);

        Intersection inter1 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(hex1.getIntersections()).andReturn(new Intersection[]{inter1});

        Settlement settlement = EasyMock.createMock(Settlement.class);

        EasyMock.expect(inter1.getStructure()).andReturn(null);

        Player player = EasyMock.createMock(Player.class);

        EasyMock.replay(hex1, inter1, settlement, player);

        Hexagon[] hexagons = new Hexagon[]{hex1};

        BoardManager bm = new BoardManager(hexagons);

        assertEquals(0, bm.getHexagonPlayers().size());

        EasyMock.verify(hex1, inter1, settlement, player);

    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testBoardManager_stealResource_expectLumberStolen(ResourceType type) {

        Player selectedPlayerToSteal = EasyMock.createMock(Player.class);
        EasyMock.expect(selectedPlayerToSteal.getResources()).andReturn(new ArrayList<ResourceType>(Arrays.asList(type)));

        Player currentPlayer = EasyMock.createMock(Player.class);

        Random random = EasyMock.createMock(Random.class);

        EasyMock.expect(random.nextInt(1)).andReturn(0);

        EasyMock.expect(selectedPlayerToSteal.removeResource(type)).andReturn(true);

        currentPlayer.addResource(type);

        EasyMock.replay(selectedPlayerToSteal, currentPlayer, random);

        BoardManager bm = new BoardManager(random);

        assertEquals(type, bm.stealResource(currentPlayer, selectedPlayerToSteal));

        EasyMock.verify(selectedPlayerToSteal, currentPlayer, random);

    }

    @Test
    public void testBoardManager_stealResource_expectNoResourceStolen() {

        Player selectedPlayerToSteal = EasyMock.createMock(Player.class);
        EasyMock.expect(selectedPlayerToSteal.getResources()).andReturn(new ArrayList<ResourceType>());

        Player currentPlayer = EasyMock.createMock(Player.class);

        Random random = EasyMock.createMock(Random.class);

        EasyMock.replay(selectedPlayerToSteal, currentPlayer, random);

        BoardManager bm = new BoardManager(random);

        assertNull(bm.stealResource(currentPlayer, selectedPlayerToSteal));

        EasyMock.verify(selectedPlayerToSteal, currentPlayer, random);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildSettlement_expectSettlementBuilt(int val) {

        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);

        ArrayList<Intersection> roads = new ArrayList<Intersection>();

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            intersections[i].setStructure(anyObject(Settlement.class));
            EasyMock.expect(intersections[i].ownedByThisPlayer(player)).andReturn(true);
            intersections[i].setOwner(player);
            roads.add(intersections[i]);
            EasyMock.replay(intersections[i]);
        }


        EasyMock.expect(player.hasResources(Settlement.getCost())).andReturn(true);
        EasyMock.expect(player.getNumSettlements()).andReturn(1).anyTimes();
        player.setNumSettlements(0);

        EasyMock.replay(player);

        List<Intersection> structureLocations = new ArrayList<Intersection>();

        BoardManager bm = new BoardManager(intersections, structureLocations, roads);

        assertTrue(bm.buildSettlement(val, player));

        EasyMock.verify(player, intersections[val]);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildSettlement_expectNoResources(int val) {

        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);

        ArrayList<Intersection> roads = new ArrayList<Intersection>();

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].ownedByThisPlayer(player)).andReturn(true);
            roads.add(intersections[i]);
            EasyMock.replay(intersections[i]);
        }


        EasyMock.expect(player.hasResources(Settlement.getCost())).andReturn(false);

        EasyMock.replay(player);

        List<Intersection> structureLocations = new ArrayList<Intersection>();

        BoardManager bm = new BoardManager(intersections, structureLocations, roads);

        assertFalse(bm.buildSettlement(val, player));

        EasyMock.verify(player, intersections[val]);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildSettlement_expectFalseNotOwnedByPlayer(int val) {

        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);

        ArrayList<Intersection> roads = new ArrayList<Intersection>();

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].ownedByThisPlayer(player)).andReturn(false);
            roads.add(intersections[i]);
            EasyMock.replay(intersections[i]);
        }

        EasyMock.replay(player);

        List<Intersection> structureLocations = new ArrayList<Intersection>();

        BoardManager bm = new BoardManager(intersections, structureLocations, roads);

        assertFalse(bm.buildSettlement(val, player));

        EasyMock.verify(player, intersections[val]);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildSettlement_expectFalseNoRoadConnectingToIntersection(
        int val) {

        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);

        ArrayList<Intersection> roads = new ArrayList<Intersection>();

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.replay(intersections[i]);
        }

        EasyMock.replay(player);

        List<Intersection> structureLocations = new ArrayList<Intersection>();

        BoardManager bm = new BoardManager(intersections, structureLocations, roads);

        assertFalse(bm.buildSettlement(val, player));

        EasyMock.verify(player, intersections[val]);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildSettlement_expectSettlementAlreadyBuilt(int val) {

        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);

        List<Intersection> structureLocations = new ArrayList<Intersection>();

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getAdjacentIntersections()).andReturn(new ArrayList<Integer>());
            EasyMock.replay(intersections[i]);
        }

        structureLocations.add(intersections[val]);

        EasyMock.replay(player);

        BoardManager bm = new BoardManager(intersections, structureLocations);

        assertFalse(bm.buildSettlement(val, player));

        EasyMock.verify(player);
    }

    @ParameterizedTest
    @CsvSource({"0, 1"})
    public void testBoardManager_buildRoad_expectRoadBuiltIndexZeroOne(int val1, int val2) {
        Player player = EasyMock.createMock(Player.class);
        Road road = EasyMock.createMock(Road.class);

        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.ownedByThisPlayer(player)).andReturn(true);

        List<Integer> adj = new ArrayList<Integer>();
        adj.add(val2);
        EasyMock.expect(inter1.getAdjacentIntersections()).andReturn(adj);

        inter1.setOwner(player);
        inter2.setOwner(player);

        EasyMock.expect(player.hasResources(Road.getCost())).andReturn(true);
        EasyMock.expect(player.getNumRoads()).andReturn(1);
        EasyMock.expect(player.getNumRoads()).andReturn(1);
        player.setNumRoads(0);

        inter1.setRoads(anyObject(Road.class));
        inter2.setRoads(anyObject(Road.class));

        EasyMock.replay(player, road, inter1, inter2);
        Intersection[] intersections = new Intersection[]{inter1, inter2};

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertTrue(bm.buildRoad(val1, val2, player));

        EasyMock.verify(player, road, inter1, inter2);

    }

    @ParameterizedTest
    @CsvSource({"1, 2"})
    public void testBoardManager_buildRoad_expectRoadBuiltIndexOneTwo(int val1, int val2) {
        Player player = EasyMock.createMock(Player.class);
        Road road = EasyMock.createMock(Road.class);

        Intersection inter0 = EasyMock.createMock(Intersection.class);
        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.ownedByThisPlayer(player)).andReturn(true);

        List<Integer> adj = new ArrayList<Integer>();
        adj.add(val2);
        EasyMock.expect(inter1.getAdjacentIntersections()).andReturn(adj);

        inter1.setOwner(player);
        inter2.setOwner(player);

        EasyMock.expect(player.hasResources(Road.getCost())).andReturn(true);
        EasyMock.expect(player.getNumRoads()).andReturn(1);
        EasyMock.expect(player.getNumRoads()).andReturn(1);
        player.setNumRoads(0);

        inter1.setRoads(anyObject(Road.class));
        inter2.setRoads(anyObject(Road.class));

        EasyMock.replay(player, road, inter1, inter2);
        Intersection[] intersections = new Intersection[]{inter0, inter1, inter2};

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertTrue(bm.buildRoad(val1, val2, player));

        EasyMock.verify(player, road, inter1, inter2);

    }

    @ParameterizedTest
    @CsvSource({"52, 53"})
    public void testBoardManager_buildRoad_expectRoadBuiltIndexFiftyTwoFiftyThree(int val1, int val2) {
        Player player = EasyMock.createMock(Player.class);
        Road road = EasyMock.createMock(Road.class);

        Intersection inter52 = EasyMock.createMock(Intersection.class);
        Intersection inter53 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter52.ownedByThisPlayer(player)).andReturn(true);

        List<Integer> adj = new ArrayList<Integer>();
        adj.add(val2);
        EasyMock.expect(inter52.getAdjacentIntersections()).andReturn(adj);

        inter52.setOwner(player);
        inter53.setOwner(player);

        EasyMock.expect(player.hasResources(Road.getCost())).andReturn(true);
        EasyMock.expect(player.getNumRoads()).andReturn(1);
        EasyMock.expect(player.getNumRoads()).andReturn(1);
        player.setNumRoads(0);

        inter52.setRoads(anyObject(Road.class));
        inter53.setRoads(anyObject(Road.class));

        EasyMock.replay(player, road, inter52, inter53);
        Intersection[] intersections = new Intersection[54];

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getAdjacentIntersections()).andReturn(new ArrayList<Integer>());
            EasyMock.replay(intersections[i]);
        }

        intersections[52] = inter52;
        intersections[53] = inter53;

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertTrue(bm.buildRoad(val1, val2, player));

        EasyMock.verify(player, road, inter52, inter53);

    }

    @Test
    public void testBoardManager_buildRoad_invalidIndexExpectFalse() {
        Player player = EasyMock.createMock(Player.class);
        Intersection[] intersections = new Intersection[54];

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getAdjacentIntersections()).andReturn(new ArrayList<Integer>());
            EasyMock.replay(intersections[i]);
        }

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertFalse(bm.buildRoad(-1, 0, player));

    }

    @ParameterizedTest
    @CsvSource({"52, 53"})
    public void testBoardManager_buildRoad_expectFalseNoResources(int val1, int val2) {
        Player player = EasyMock.createMock(Player.class);
        Road road = EasyMock.createMock(Road.class);

        Intersection inter52 = EasyMock.createMock(Intersection.class);
        Intersection inter53 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter52.ownedByThisPlayer(player)).andReturn(true);

        List<Integer> adj = new ArrayList<Integer>();
        adj.add(val2);
        EasyMock.expect(inter52.getAdjacentIntersections()).andReturn(adj);

        inter52.setOwner(player);
        inter53.setOwner(player);

        EasyMock.expect(player.hasResources(Road.getCost())).andReturn(false);

        EasyMock.replay(player, road, inter52, inter53);
        Intersection[] intersections = new Intersection[54];

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getAdjacentIntersections()).andReturn(new ArrayList<Integer>());
            EasyMock.replay(intersections[i]);
        }

        intersections[52] = inter52;
        intersections[53] = inter53;

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertFalse(bm.buildRoad(val1, val2, player));

        EasyMock.verify(player, road, inter52, inter53);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildCity_expectNoResources(int val) {
        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);
        Settlement settlement = EasyMock.createMock(Settlement.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player).anyTimes();


        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getStructure()).andReturn(settlement).anyTimes();
        }

        EasyMock.expect(player.hasResources(City.getCost())).andReturn(false).anyTimes();

        EasyMock.replay(player, settlement);
        for (Intersection intersection : intersections) {
            EasyMock.replay(intersection);
        }

        BoardManager bm = new BoardManager(intersections);

        assertFalse(bm.buildCity(val, player));

        EasyMock.verify(player, settlement);
        for (Intersection intersection : intersections) {
            EasyMock.verify(intersection);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildCity_expectCityBuilt(int val) {
        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);
        Settlement settlement = EasyMock.createMock(Settlement.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player).anyTimes();

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getStructure()).andReturn(settlement).anyTimes();
            intersections[i].setStructure(anyObject(City.class));
        }

        EasyMock.expect(player.hasResources(City.getCost())).andReturn(true).anyTimes();
        EasyMock.expect(player.getNumCities()).andReturn(1).anyTimes();
        player.setNumCities(0);

        EasyMock.replay(player, settlement);
        for (Intersection intersection : intersections) {
            EasyMock.replay(intersection);
        }

        BoardManager bm = new BoardManager(intersections);

        assertTrue(bm.buildCity(val, player));

        EasyMock.verify(player, settlement, intersections[val]);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildCity_expectCityAlreadyBuilt(int val) {
        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);
        City city = EasyMock.createMock(City.class);
        EasyMock.expect(city.getOwner()).andReturn(player).anyTimes();

        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getStructure()).andReturn(city).anyTimes();
            intersections[i].setStructure(anyObject(City.class));
        }

        EasyMock.expect(player.hasResources(City.getCost())).andReturn(true).anyTimes();

        EasyMock.replay(player, city);
        for (Intersection intersection : intersections) {
            EasyMock.replay(intersection);
        }

        BoardManager bm = new BoardManager(intersections);

        assertFalse(bm.buildCity(val, player));

        EasyMock.verify(player, city);

    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 54})
    public void testBoardManager_buildCity_expectOutOfBoundsIndex(int val) {
        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);
        Settlement settlement = EasyMock.createMock(Settlement.class);
        EasyMock.expect(settlement.getOwner()).andReturn(player).anyTimes();


        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getStructure()).andReturn(settlement).anyTimes();
        }

        EasyMock.expect(player.hasResources(City.getCost())).andReturn(true).anyTimes();

        EasyMock.replay(player, settlement);
        for (Intersection intersection : intersections) {
            EasyMock.replay(intersection);
        }

        BoardManager bm = new BoardManager(intersections);

        assertFalse(bm.buildCity(val, player));

        EasyMock.verify(player, settlement);
        for (Intersection intersection : intersections) {
            EasyMock.verify(intersection);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_buildCity_expectNonPlayerSettlement(int val) {
        Intersection[] intersections = new Intersection[54];

        Player player = EasyMock.createMock(Player.class);
        Player opponent = EasyMock.createMock(Player.class);
        Settlement settlement = EasyMock.createMock(Settlement.class);
        EasyMock.expect(settlement.getOwner()).andReturn(opponent).anyTimes();


        for (int i = 0; i < 54; i++) {
            intersections[i] = EasyMock.createMock(Intersection.class);
            EasyMock.expect(intersections[i].getStructure()).andReturn(settlement).anyTimes();
        }

        EasyMock.expect(player.hasResources(City.getCost())).andReturn(true).anyTimes();

        EasyMock.replay(player, settlement);
        for (Intersection intersection : intersections) {
            EasyMock.replay(intersection);
        }

        BoardManager bm = new BoardManager(intersections);

        assertFalse(bm.buildCity(val, player));

        EasyMock.verify(player, settlement);
        for (Intersection intersection : intersections) {
            EasyMock.verify(intersection);
        }
    }

    @Test
    public void testBoardManager_buildCity_noSettlementOnIndex() {
        BoardManager board = new BoardManager();
        board.initializeBoardStructure(false);
        Intersection inter = EasyMock.createMock(Intersection.class);
        board.intersections[0] = inter;
        EasyMock.expect(inter.getStructure()).andReturn(null);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.replay(inter,player);

        assertFalse(board.buildCity(0, player));

        EasyMock.verify(inter,player);

    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12})
    public void testBoardManager_distributeResourcesOnRoll(int val) {
        // Create mocks using createNiceMock which is more forgiving
        Hexagon hex1 = EasyMock.createNiceMock(Hexagon.class);
        Structure structure = EasyMock.createNiceMock(Settlement.class);
        Intersection inter1 = EasyMock.createNiceMock(Intersection.class);
        Player player = EasyMock.createNiceMock(Player.class);
        Bank bank = EasyMock.createNiceMock(Bank.class);

        // Configure hex1
        EasyMock.expect(hex1.getValue()).andReturn(val).anyTimes();
        EasyMock.expect(hex1.getHasRobber()).andReturn(false).anyTimes();
        EasyMock.expect(hex1.getResource()).andReturn(ResourceType.BRICK).anyTimes();

        EasyMock.expect(hex1.getIntersections()).andReturn(new Intersection[]{inter1}).anyTimes();

        EasyMock.expect(inter1.getStructure()).andReturn(structure).anyTimes();

        EasyMock.expect(structure.getOwner()).andReturn(player).anyTimes();
        structure.distributeResources(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.expect(bank.obtainResource(EasyMock.anyObject(ResourceTransaction.class)))
                .andReturn(true).anyTimes();

        player.addResource(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.replay(hex1, inter1, structure, player, bank);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1}, new Intersection[]{inter1});

        int result = bm.distributeResourcesOnRoll(val, bank);

        assertTrue(result == 1);

        EasyMock.verify(hex1, inter1, structure, player, bank);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 8, 9, 10, 11})
    public void testBoardManager_distributeResourcesOnRoll_twoHexes(int val) {
        Hexagon hex1 = EasyMock.createNiceMock(Hexagon.class);
        Settlement settlement = EasyMock.createNiceMock(Settlement.class);
        Intersection inter1 = EasyMock.createNiceMock(Intersection.class);
        Player player = EasyMock.createNiceMock(Player.class);
        Bank bank = EasyMock.createNiceMock(Bank.class);
        Hexagon hex2 = EasyMock.createNiceMock(Hexagon.class);
        Settlement settlement2 = EasyMock.createNiceMock(Settlement.class);
        Intersection inter2 = EasyMock.createNiceMock(Intersection.class);
        Player player2 = EasyMock.createNiceMock(Player.class);

        EasyMock.expect(hex1.getValue()).andReturn(val).anyTimes();
        EasyMock.expect(hex1.getHasRobber()).andReturn(false).anyTimes();
        EasyMock.expect(hex1.getResource()).andReturn(ResourceType.BRICK).anyTimes();
        EasyMock.expect(inter1.getStructure()).andReturn(settlement).anyTimes();
        EasyMock.expect(settlement.getOwner()).andReturn(player).anyTimes();

        settlement.distributeResources(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.expect(hex1.getIntersections()).andReturn(new Intersection[]{inter1}).anyTimes();
        EasyMock.expect(bank.obtainResource(EasyMock.anyObject(ResourceTransaction.class))).andReturn(true).anyTimes();
        player.addResource(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.expect(hex2.getValue()).andReturn(val).anyTimes();
        EasyMock.expect(hex2.getHasRobber()).andReturn(false).anyTimes();
        EasyMock.expect(hex2.getResource()).andReturn(ResourceType.BRICK).anyTimes();
        EasyMock.expect(inter2.getStructure()).andReturn(settlement2).anyTimes();
        EasyMock.expect(settlement2.getOwner()).andReturn(player2).anyTimes();

        settlement2.distributeResources(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.expect(hex2.getIntersections()).andReturn(new Intersection[]{inter2}).anyTimes();
        player2.addResource(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.replay(hex1, inter1, settlement, player, bank, hex2, inter2, settlement2, player2);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1, hex2}, new Intersection[]{inter1, inter2});

        int result = bm.distributeResourcesOnRoll(val, bank);

        assertTrue(result >= 0);

        EasyMock.verify(hex1, inter1, settlement, player, bank, hex2, inter2, settlement2, player2);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12})
    public void testBoardManager_distributeResourcesOnRoll_robberExpect2() {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Player player = EasyMock.createMock(Player.class);
        Bank bank = EasyMock.createMock(Bank.class);

        EasyMock.expect(hex1.getValue()).andReturn(2);
        EasyMock.expect(hex1.getHasRobber()).andReturn(true);
        EasyMock.expect(hex1.getValue()).andReturn(2);
        EasyMock.expect(hex1.getHasRobber()).andReturn(true);

        EasyMock.replay(hex1, inter1, settlement, player, bank);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1}, new Intersection[]{inter1});


        assertEquals(2, bm.distributeResourcesOnRoll(2, bank));

        EasyMock.verify(hex1, inter1, settlement, player, bank);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12})
    public void testBoardManager_distributeResourcesOnRoll_noResourcesToDistributeSettlement(int val) {
        Hexagon hex1 = EasyMock.createNiceMock(Hexagon.class);
        Settlement settlement = EasyMock.createNiceMock(Settlement.class);
        Intersection inter1 = EasyMock.createNiceMock(Intersection.class);
        Player player = EasyMock.createNiceMock(Player.class);
        Bank bank = EasyMock.createNiceMock(Bank.class);

        EasyMock.expect(hex1.getValue()).andReturn(val).anyTimes();
        EasyMock.expect(hex1.getHasRobber()).andReturn(false).anyTimes();
        EasyMock.expect(hex1.getResource()).andReturn(ResourceType.BRICK).anyTimes();
        EasyMock.expect(hex1.getIntersections()).andReturn(new Intersection[]{inter1}).anyTimes();

        EasyMock.expect(inter1.getStructure()).andReturn(settlement).anyTimes();

        EasyMock.expect(settlement.getOwner()).andReturn(player).anyTimes();
        settlement.distributeResources(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.expect(bank.obtainResource(EasyMock.anyObject(ResourceTransaction.class))).andReturn(false).anyTimes();

        EasyMock.replay(hex1, inter1, settlement, player, bank);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1}, new Intersection[]{inter1});

        int result = bm.distributeResourcesOnRoll(val, bank);

        assertTrue(result == 1);

        EasyMock.verify(hex1, inter1, settlement, player, bank);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12})
    public void testBoardManager_distributeResourcesOnRoll_noResourcesToDistributeCity(int val) {
        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        City city = EasyMock.createMock(City.class);
        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Player player = EasyMock.createMock(Player.class);
        Bank bank = EasyMock.createMock(Bank.class);

        EasyMock.expect(hex1.getValue()).andReturn(val).anyTimes();
        EasyMock.expect(hex1.getHasRobber()).andReturn(false).anyTimes();
        EasyMock.expect(hex1.getResource()).andReturn(ResourceType.BRICK).anyTimes();

        EasyMock.expect(inter1.getStructure()).andReturn(city).anyTimes();
        EasyMock.expect(city.getOwner()).andReturn(player).anyTimes();
        city.distributeResources(ResourceType.BRICK);
        EasyMock.expectLastCall().anyTimes();

        EasyMock.expect(hex1.getIntersections()).andReturn(new Intersection[]{inter1}).anyTimes();
        EasyMock.expect(bank.obtainResource(EasyMock.anyObject(ResourceTransaction.class))).andReturn(false).anyTimes();

        EasyMock.replay(hex1, inter1, city, player, bank);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1}, new Intersection[]{inter1});

        int result = bm.distributeResourcesOnRoll(val, bank);
        assertTrue(result == 1);

        EasyMock.verify(hex1, inter1, city, player, bank);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12})
    public void testBoardManager_distributeResourcesOnRoll_singleAndDoubleHexes_City(int val) {
        Hexagon hex1 = EasyMock.createNiceMock(Hexagon.class);
        City city = EasyMock.createNiceMock(City.class);
        Intersection inter1 = EasyMock.createNiceMock(Intersection.class);
        Player player = EasyMock.createNiceMock(Player.class);
        Bank bank = EasyMock.createNiceMock(Bank.class);

        EasyMock.expect(hex1.getValue()).andReturn(val).anyTimes();
        EasyMock.expect(hex1.getHasRobber()).andReturn(false).anyTimes();
        EasyMock.expect(hex1.getResource()).andReturn(ResourceType.BRICK).anyTimes();
        EasyMock.expect(hex1.getIntersections()).andReturn(new Intersection[]{inter1}).anyTimes();

        EasyMock.expect(inter1.getStructure()).andReturn(city).anyTimes();

        EasyMock.expect(city.getOwner()).andReturn(player).anyTimes();
        city.distributeResources(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.expect(bank.obtainResource(EasyMock.anyObject(ResourceTransaction.class))).andReturn(true).anyTimes();

        player.addResource(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.replay(hex1, inter1, city, player, bank);

        BoardManager bm = new BoardManager(new Hexagon[]{hex1}, new Intersection[]{inter1});

        int result = bm.distributeResourcesOnRoll(val, bank);

        assertTrue(result == 2);

        EasyMock.verify(hex1, inter1, city, player, bank);
    }

    @ParameterizedTest
    @CsvSource({"0, 1"})
    public void testBoardManager_removeRoad_expectFalse(int val1, int val2) {
        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);
        Road road = EasyMock.createMock(Road.class);
        Player player = EasyMock.createMock(Player.class);

        BoardManager bm =
            new BoardManager(new Intersection[]{inter1, inter2}, new ArrayList<Intersection>(), new ArrayList<Road>());


        EasyMock.replay(inter1, inter2, road, player);

        assertFalse(bm.removeRoad(val1, val2));

        EasyMock.verify(inter1, inter2, road, player);
    }

    @ParameterizedTest
    @CsvSource({"0, 1"})
    public void testBoardManager_removeRoad_expectTrue(int val1, int val2) {
        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);
        Road road = EasyMock.createMock(Road.class);
        Player player = EasyMock.createMock(Player.class);


        EasyMock.expect(road.getIntersections()).andReturn(new Intersection[]{inter1, inter2});
        EasyMock.expect(road.getIntersections()).andReturn(new Intersection[]{inter1, inter2});

        road.setIntersections(null, null);
        inter1.removeRoad(road);
        inter2.removeRoad(road);

        EasyMock.replay(inter1, inter2, road, player);

        ArrayList<Road> roads = new ArrayList<Road>();
        roads.add(road);

        ArrayList<Intersection> intersections = new ArrayList<Intersection>();
        intersections.add(inter1);
        intersections.add(inter2);

        BoardManager bm = new BoardManager(new Intersection[]{inter1, inter2}, intersections, roads);

        assertTrue(bm.removeRoad(val1, val2));

        EasyMock.verify(inter1, inter2, road, player);
    }

    @Test
    public void testBoardManager_ifHexValueZeroSetDesert_expectTrue() {


        int[][] hexValues = getHexValues();

        int[] desertRowAndColumn = {-1, -1};



        bm.ifHexValueZeroSetDesert(hexValues, desertRowAndColumn, 2, 2);

        assertTrue(desertRowAndColumn[0] == 2 && desertRowAndColumn[1] == 2);
    }

    @Test
    public void testBoardManager_ifHexValueZeroSetDesert_expectFalse() {

        int[][] hexValues = getHexValues();

        int[] desertRowAndColumn = {-1, -1};



        bm.ifHexValueZeroSetDesert(hexValues, desertRowAndColumn, 2, 3);

        assertTrue(desertRowAndColumn[0] == -1 && desertRowAndColumn[1] == -1);
    }

    private int[][] getHexValues() {
        final int[] PRESET_BOARD_ROW_1 = {-1, -1, 10, 2, 9};
        final int[] PRESET_BOARD_ROW_2 = {-1, 12, 6, 4, 10};
        final int[] PRESET_BOARD_ROW_3 = {9, 11, 0, 3, 8};
        final int[] PRESET_BOARD_ROW_4 = {8, 3, 4, 5, -1};
        final int[] PRESET_BOARD_ROW_5 = {5, 6, 11, -1, -1};

        int[][] hexValues = new int[][]{PRESET_BOARD_ROW_1, PRESET_BOARD_ROW_2, PRESET_BOARD_ROW_3,
            PRESET_BOARD_ROW_4, PRESET_BOARD_ROW_5};

        return hexValues;
    }

    @Test
    public void testBoardManager_randomizeDesert() {


        int[][] hexValues = getHexValues();


        Random random = EasyMock.createMock(Random.class);

        EasyMock.expect(random.nextInt(5)).andReturn(2);
        EasyMock.expect(random.nextInt(5)).andReturn(3);

        EasyMock.replay(random);

        BoardManager bm = new BoardManager(random);

        bm.randomizeDesert(hexValues, random);

        assertEquals(0, hexValues[2][3]);

        EasyMock.verify(random);
    }

    @Test
    public void testBoardManager_randomizeDesert_withExtraEnsuresCall() {

        int[][] hexValues = getHexValues();

        Random random = EasyMock.createMock(Random.class);

        EasyMock.expect(random.nextInt(5)).andReturn(0);
        EasyMock.expect(random.nextInt(5)).andReturn(0);
        EasyMock.expect(random.nextInt(5)).andReturn(0);
        EasyMock.expect(random.nextInt(5)).andReturn(2);


        EasyMock.replay(random);

        BoardManager bm = new BoardManager(random);

        bm.randomizeDesert(hexValues, random);

        assertEquals(0, hexValues[0][2]);

        EasyMock.verify(random);
    }

    @Test
    public void testBoardManager_reassignValueAtHex_isDesert() {
        Hexagon hex = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex.getIsDesert()).andReturn(true);

        hex.setDesert(false);
        hex.setHasRobber(false);
        hex.setValue(3);

        EasyMock.replay(hex);

        int[][] hexValues = getHexValues();

        int[] rowCol = {0, 1};

        BoardManager bm = new BoardManager(new Hexagon[]{hex});

        bm.reassignValueAtHex(hexValues, hex, rowCol);

        assertEquals(0, hexValues[2][2]);

        EasyMock.verify(hex);

    }

    @Test
    public void testBoardManager_reassignValueAtHex_isNotDesert() {
        Hexagon hex = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex.getIsDesert()).andReturn(false);
        EasyMock.expect(hex.getIsDesert()).andReturn(false);

        hex.setDesert(true);
        hex.setHasRobber(true);
        hex.setValue(0);

        EasyMock.replay(hex);

        int[][] hexValues = getHexValues();

        int[] rowCol = {0, 0};

        BoardManager bm = new BoardManager(new Hexagon[]{hex});

        bm.reassignValueAtHex(hexValues, hex, rowCol);

        assertEquals(0, hexValues[2][2]);

        EasyMock.verify(hex);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_placeSettlementSetup_expectSettlementPlaced(int val) {
        Player player = EasyMock.createMock(Player.class);

        Settlement settlement = EasyMock.createMock(Settlement.class);

        Intersection[] intersections = new Intersection[54];
        for (int i = 0; i < 54; i++) {
            Intersection inter = EasyMock.createMock(Intersection.class);
            inter.setStructure(anyObject(Settlement.class));
            inter.setOwner(player);
            EasyMock.replay(inter);
            intersections[i] = inter;
        }

        settlement.setOwner(player);
        EasyMock.replay(player, settlement);

        BoardManager bm = new BoardManager(intersections);

        assertTrue(bm.placeSettlementSetup(val, player, settlement));

        assertTrue(bm.structureLocations.contains(intersections[val]));

        assertTrue(bm.structures.size() == 1);

        EasyMock.verify(player);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 53})
    public void testBoardManager_placeSettlementSetup_expectSettlementExists(int val) {
        Player player = EasyMock.createMock(Player.class);

        Settlement settlement = EasyMock.createMock(Settlement.class);

        Intersection[] intersections = new Intersection[54];
        for (int i = 0; i < 54; i++) {
            Intersection inter = EasyMock.createMock(Intersection.class);
            EasyMock.expect(inter.getAdjacentIntersections()).andReturn(new ArrayList<Integer>());
            EasyMock.replay(inter);
            intersections[i] = inter;
        }

        List<Intersection> structureLocations = new ArrayList<Intersection>();
        structureLocations.add(intersections[val]);

        EasyMock.replay(player);

        BoardManager bm = new BoardManager(intersections, structureLocations);

        assertFalse(bm.placeSettlementSetup(val, player, settlement));

        EasyMock.verify(player);

    }


    @Test
    public void testBoardManager_peekGetAndSetIntersectionSelection_noSet() {
        BoardManager boardManager = new BoardManager();

        assertEquals(-1, boardManager.peekIntersectionSelection());
        assertEquals(-1, boardManager.getIntersectionSelection());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 55})
    public void testBoardManager_peekGetAndSetIntersectionSelection_setTo(int index) {
        BoardManager boardManager = new BoardManager();

        boardManager.setIntersectionSelection(index);
        assertEquals(index, boardManager.selectedIntersection);
        assertEquals(index, boardManager.peekIntersectionSelection());
        assertEquals(index, boardManager.getIntersectionSelection());
        assertEquals(-1, boardManager.selectedIntersection);

    }

    @Test
    public void testBoardManager_peekGetAndSetHexSelection_noSet() {
        BoardManager boardManager = new BoardManager();

        assertEquals(-1, boardManager.peekHexSelection());
        assertEquals(-1, boardManager.getHexSelection());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 18})
    public void testBoardManager_peekGetAndSetHexSelection_setTo(int index) {
        BoardManager boardManager = new BoardManager();

        boardManager.setHexSelection(index);
        assertEquals(index, boardManager.selectedHex);
        assertEquals(index, boardManager.peekHexSelection());
        assertEquals(index, boardManager.getHexSelection());
        assertEquals(-1, boardManager.selectedHex);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 9})
    public void testBoardManager_getPorts(int numPorts) {
        BoardManager boardManager = new BoardManager();

        ArrayList<Port> ports = new ArrayList<>();
        for (int i = 0; i < numPorts; i++) {
            ports.add(EasyMock.createMock(Port.class));
        }
        boardManager.ports = ports;

        ArrayList<Port> returnedPorts = boardManager.getPorts();

        assertEquals(numPorts, returnedPorts.size());
    }

    @Test
    public void testBoardManager_getPortLocations() {
        BoardManager boardManager = new BoardManager();
        Coordinate[] locations = boardManager.getPortLocations();

        assertEquals(9, locations.length);
        assertEquals(BoardManager.PORT_ONE, locations[0]);
        assertEquals(BoardManager.PORT_NINE, locations[8]);
        assertEquals(10, locations[0].getIndex1());
        assertEquals(4, locations[0].getIndex2());
    }

    @Test
    public void testBoardManager_boardShuffleAndRandomization() {
        BoardManager.HexagonHelper hexagonHelper = EasyMock.createMock(BoardManager.HexagonHelper.class);

        hexagonHelper.shuffler = EasyMock.createMock(Shuffler.class);

        Hexagon[] hexagons = new Hexagon[19];
        for (int i = 0; i < 19; i++) {
            hexagons[i] = EasyMock.createMock(Hexagon.class);
            Point2D point = EasyMock.createMock(Point2D.class);
            EasyMock.expect(point.getX()).andReturn(0.0);
            EasyMock.expect(point.getY()).andReturn(0.0);
            EasyMock.expect(hexagons[i].getCenter()).andReturn(point);
            EasyMock.expect(hexagons[i].getIsDesert()).andReturn(false);
            EasyMock.expect(hexagons[i].getIsDesert()).andReturn(false);
            hexagons[i].setValue(3);
            EasyMock.expect(hexagons[i].getValue()).andReturn(3);
            EasyMock.replay(hexagons[i], point);
        }
        hexagonHelper.hexagons = hexagons;

        int[][] hexValues = getHexValues();

        EasyMock.expect(hexagonHelper.shuffler.getHexagonValues(hexagonHelper.hexagons)).andReturn(hexValues);

        Random random = EasyMock.createMock(Random.class);

        EasyMock.expect(random.nextInt(5)).andReturn(2);
        EasyMock.expect(random.nextInt(5)).andReturn(3);

        hexagonHelper.shuffler.ensureNoNeighborSixOrEight(random);
        EasyMock.expectLastCall();

        EasyMock.replay(hexagonHelper.shuffler, random);


        bm.boardShuffleAndRandomization(hexagonHelper, random);

        assertNotEquals(3, hexValues[2][3]);

        // Verify that the expected methods were called
        EasyMock.verify(hexagonHelper.shuffler, random);
    }

    @Test
    public void testBoardManager_testReassignValueAtHex_expectDesertValueChanged() {
        Hexagon hex = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex.getIsDesert()).andReturn(true);
        Point2D point = EasyMock.createMock(Point2D.class);

        EasyMock.expect(hex.getCenter()).andReturn(point);

        EasyMock.expect(point.getX()).andReturn(0.0);
        EasyMock.expect(point.getY()).andReturn(1.0);

        hex.setDesert(false);
        hex.setHasRobber(false);
        hex.setValue(4);

        EasyMock.replay(hex, point);

        int[][] hexValues = getHexValues();

        BoardManager bm = new BoardManager(new Hexagon[]{hex});

        bm.reassignHexWithValue(hexValues, hex);

        EasyMock.verify(hex, point);
    }

    @Test
    public void testBoardManager_testReassignValueAtHex_expectNoDesertNotChanged() {
        Hexagon hex = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex.getIsDesert()).andReturn(false);
        EasyMock.expect(hex.getIsDesert()).andReturn(false);
        Point2D point = EasyMock.createMock(Point2D.class);

        EasyMock.expect(hex.getCenter()).andReturn(point);

        EasyMock.expect(point.getX()).andReturn(0.0);
        EasyMock.expect(point.getY()).andReturn(1.0);

        hex.setValue(4);

        EasyMock.replay(hex, point);

        int[][] hexValues = getHexValues();

        BoardManager bm = new BoardManager(new Hexagon[]{hex});

        bm.reassignHexWithValue(hexValues, hex);

        EasyMock.verify(hex, point);
    }

    @Test
    public void testBoardManager_testRandomizeBoard_expectRandomized() {
        BoardManager.HexagonHelper hexagonHelper = EasyMock.createMock(BoardManager.HexagonHelper.class);

        hexagonHelper.shuffler = EasyMock.createMock(Shuffler.class);

        Hexagon[] hexagons = new Hexagon[19];
        for (int i = 0; i < 19; i++) {
            hexagons[i] = EasyMock.createMock(Hexagon.class);
            Point2D point = EasyMock.createMock(Point2D.class);
            EasyMock.expect(point.getX()).andReturn(0.0);
            EasyMock.expect(point.getY()).andReturn(0.0);
            EasyMock.expect(hexagons[i].getCenter()).andReturn(point);
            EasyMock.expect(hexagons[i].getIsDesert()).andReturn(false);
            EasyMock.expect(hexagons[i].getIsDesert()).andReturn(false);
            hexagons[i].setValue(3);
            EasyMock.expect(hexagons[i].getValue()).andReturn(3);
            EasyMock.replay(hexagons[i], point);
        }
        hexagonHelper.hexagons = hexagons;

        int[][] hexValues = getHexValues();

        EasyMock.expect(hexagonHelper.shuffler.getHexagonValues(hexagonHelper.hexagons)).andReturn(hexValues);

        Random random = EasyMock.createMock(Random.class);

        EasyMock.expect(random.nextInt(5)).andReturn(2);
        EasyMock.expect(random.nextInt(5)).andReturn(3);

        hexagonHelper.shuffler.ensureNoNeighborSixOrEight(random);
        EasyMock.expectLastCall();

        EasyMock.replay(hexagonHelper.shuffler, random);


        bm.randomizeBoard(true, hexagonHelper, random);

        assertNotEquals(3, hexValues[2][3]);

        // Verify that the expected methods were called
        EasyMock.verify(hexagonHelper.shuffler, random);
    }

    @Test
    public void testBoardManager_testRandomizeBoard_expectNotRandomized() {
        BoardManager.HexagonHelper hexagonHelper = EasyMock.createMock(BoardManager.HexagonHelper.class);

        hexagonHelper.shuffler = EasyMock.createMock(Shuffler.class);

        Hexagon[] hexagons = new Hexagon[19];
        for (int i = 0; i < 19; i++) {
            hexagons[i] = EasyMock.createMock(Hexagon.class);
            hexagons[i].setResource(null);
            EasyMock.expectLastCall().anyTimes();
            hexagons[i].setResource(anyObject(ResourceType.class));
            EasyMock.expectLastCall().anyTimes();
            Point2D point = EasyMock.createMock(Point2D.class);
            EasyMock.expect(point.getX()).andReturn(0.0).anyTimes();
            EasyMock.expect(point.getY()).andReturn(0.0).anyTimes();
            EasyMock.expect(hexagons[i].getCenter()).andReturn(point).anyTimes();
            EasyMock.expect(hexagons[i].getIsDesert()).andReturn(false);
            EasyMock.expect(hexagons[i].getIsDesert()).andReturn(false);
            hexagons[i].setDesert(true);
            hexagons[i].setHasRobber(true);
            hexagons[i].setValue(0);
            EasyMock.expect(hexagons[i].getValue()).andReturn(3);
            EasyMock.replay(hexagons[i], point);
        }
        hexagonHelper.hexagons = hexagons;

        int[][] hexValues = getHexValues();

        Random random = EasyMock.createMock(Random.class);


        bm.randomizeBoard(false, hexagonHelper, random);

        assertEquals(0, hexValues[2][2]);

        // Verify that the expected methods were called
        for (Hexagon hex : hexagons) {
            EasyMock.verify(hex);
        }
    }

    @Test
    public void testBoardManager_checkSettlementCostAndCount_expectTrue() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(Settlement.getCost())).andReturn(true);
        EasyMock.expect(player.getNumSettlements()).andReturn(1);


        EasyMock.replay(player);




        boolean result = bm.checkSettlementCostAndCount(player);

        assertTrue(result);


        EasyMock.verify(player);
    }

    @Test
    public void testBoardManager_checkSettlementCostAndCount_expectNoResources() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(Settlement.getCost())).andReturn(false);


        EasyMock.replay(player);




        boolean result = bm.checkSettlementCostAndCount(player);


        assertFalse(result);


        EasyMock.verify(player);
    }

    @Test
    public void testBoardManager_checkSettlementCostAndCount_expectNoSettlements() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(Settlement.getCost())).andReturn(true);
        EasyMock.expect(player.getNumSettlements()).andReturn(0);


        EasyMock.replay(player);




        boolean result = bm.checkSettlementCostAndCount(player);


        assertFalse(result);


        EasyMock.verify(player);

    }

    @Test
    public void testBoardManager_checkRoadCost_expectTrue() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(Road.getCost())).andReturn(true);
        EasyMock.expect(player.getNumRoads()).andReturn(1);


        EasyMock.replay(player);




        boolean result = bm.checkHasRoadResources(player);


        assertTrue(result);


        EasyMock.verify(player);
    }

    @Test
    public void testBoardManager_checkRoadCost_expectNoResources() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(Road.getCost())).andReturn(false);


        EasyMock.replay(player);




        boolean result = bm.checkHasRoadResources(player);


        assertFalse(result);


        EasyMock.verify(player);
    }

    @Test
    public void testBoardManager_checkRoadCost_expectNoSettlements() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(Road.getCost())).andReturn(true);
        EasyMock.expect(player.getNumRoads()).andReturn(0);


        EasyMock.replay(player);




        boolean result = bm.checkHasRoadResources(player);


        assertFalse(result);


        EasyMock.verify(player);

    }

    @Test
    public void testBoardManager_checkCityCost_expectTrue() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(City.getCost())).andReturn(true);
        EasyMock.expect(player.getNumCities()).andReturn(1);


        EasyMock.replay(player);




        boolean result = bm.checkHasCityResources(player);


        assertTrue(result);


        EasyMock.verify(player);
    }

    @Test
    public void testBoardManager_checkCityCost_expectNoResources() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(City.getCost())).andReturn(false);


        EasyMock.replay(player);




        boolean result = bm.checkHasCityResources(player);


        assertFalse(result);


        EasyMock.verify(player);
    }

    @Test
    public void testBoardManager_checkCityCost_expectNoSettlements() {

        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.hasResources(City.getCost())).andReturn(true);
        EasyMock.expect(player.getNumCities()).andReturn(0);


        EasyMock.replay(player);




        boolean result = bm.checkHasCityResources(player);


        assertFalse(result);


        EasyMock.verify(player);

    }

    private int[][] getPorts(){
         final int[] PORT_ONE = {10, 4};
         final int[] PORT_TWO = {30, 44};
         final int[] PORT_THREE = {51, 42};
         final int[] PORT_FOUR = {53, 47};
         final int[] PORT_FIVE = {31, 29};
         final int[] PORT_SIX = {21, 19};
         final int[] PORT_SEVEN = {39, 50};
         final int[] PORT_EIGHT = {48, 34};
         final int[] PORT_NINE = {20, 36};
         final int[][] PORT_INTERSECTIONS = new int[][]{
            PORT_ONE, PORT_TWO, PORT_THREE, PORT_FOUR, PORT_FIVE, PORT_SIX, PORT_SEVEN,
            PORT_EIGHT, PORT_NINE};

            return PORT_INTERSECTIONS;
    }

    private int[][] getShuffledPorts() {
        final int[] PORT_ONE = {10, 4};
        final int[] PORT_TWO = {30, 44};
        final int[] PORT_THREE = {51, 42};
        final int[] PORT_FOUR = {53, 47};
        final int[] PORT_FIVE = {31, 29};
        final int[] PORT_SIX = {21, 19};
        final int[] PORT_SEVEN = {39, 50};
        final int[] PORT_EIGHT = {48, 34};
        final int[] PORT_NINE = {20, 36};

        ArrayList<int[]> ports = new ArrayList<>();
        ports.add(PORT_ONE);
        ports.add(PORT_TWO);
        ports.add(PORT_THREE);
        ports.add(PORT_FOUR);
        ports.add(PORT_FIVE);
        ports.add(PORT_SIX);
        ports.add(PORT_SEVEN);
        ports.add(PORT_EIGHT);
        ports.add(PORT_NINE);

        Collections.shuffle(ports);

        int[][] shuffledPorts = new int[9][2];
        for (int i = 0; i < 9; i++) {
            shuffledPorts[i] = ports.get(i);
        }

        return shuffledPorts;

    }
    @Test
    public void testBoardManager_randomizePorts(){
        Shuffler shuffler = EasyMock.createMock(Shuffler.class);

        ArrayList<Port> ports = new ArrayList<>();

        Intersection[] intersections = new Intersection[54];
        //make 54 intersections
        for (int i = 0; i < 54; i++) {
            Intersection intersection = EasyMock.createMock(Intersection.class);
            intersection.setPort(anyObject(Port.class));
            EasyMock.expectLastCall().times(1);
            EasyMock.replay(intersection);
            intersections[i] = intersection;
        }

        for (int i = 0; i < 9; i++) {
            Port port = EasyMock.createMock(Port.class);
            EasyMock.replay(port);
            ports.add(port);
        }

        EasyMock.expect(shuffler.getShuffledPortTokens()).andReturn(ports);

        BoardManager bm = new BoardManager(intersections);


        EasyMock.replay(shuffler);

        bm.randomizePorts(shuffler);

        EasyMock.verify(shuffler);

        for (Port port : ports) {
            EasyMock.verify(port);
        }

        //only verify the intersections of the values in the PORT_INTERSECTIONS array
        for (int i = 0; i < 9; i++) {
            EasyMock.verify(intersections[BoardManager.PORT_LOCATIONS[i].getIndex1()]);
            EasyMock.verify(intersections[BoardManager.PORT_LOCATIONS[i].getIndex2()]);
        }

    }

    @Test
    public void testBoardManager_generatePorts_expectPortsRandomized() {
        Shuffler shuffler = EasyMock.createMock(Shuffler.class);

        ArrayList<Port> ports = new ArrayList<>();

        Intersection[] intersections = new Intersection[54];
        //make 54 intersections
        for (int i = 0; i < 54; i++) {
            Intersection intersection = EasyMock.createMock(Intersection.class);
            intersection.setPort(anyObject(Port.class));
            EasyMock.expectLastCall().times(1);
            EasyMock.replay(intersection);
            intersections[i] = intersection;
        }

        for (int i = 0; i < 9; i++) {
            Port port = EasyMock.createMock(Port.class);
            EasyMock.replay(port);
            ports.add(port);
        }

        EasyMock.expect(shuffler.getShuffledPortTokens()).andReturn(ports);

        EasyMock.replay(shuffler);

        BoardManager bm = new BoardManager(shuffler, intersections);


        bm.generatePorts(true);

        EasyMock.verify(shuffler);

        for (Port port : ports) {
            EasyMock.verify(port);
        }

        //only verify the intersections of the values in the PORT_INTERSECTIONS array
        for (int i = 0; i < 9; i++) {
            EasyMock.verify(intersections[BoardManager.PORT_LOCATIONS[i].getIndex1()]);
            EasyMock.verify(intersections[BoardManager.PORT_LOCATIONS[i].getIndex2()]);
        }
    }

    @Test
    public void testBoardManager_generatePorts_expectPortsNotRandomized() {
        Shuffler shuffler = EasyMock.createMock(Shuffler.class);

        ArrayList<Port> ports = new ArrayList<>();

        Intersection[] intersections = new Intersection[54];

        for (int i = 0; i < 54; i++) {
            Intersection intersection = EasyMock.createMock(Intersection.class);
            intersection.setPort(anyObject(Port.class));
            EasyMock.expectLastCall().times(1);
            EasyMock.replay(intersection);
            intersections[i] = intersection;
        }

        for (int i = 0; i < 9; i++) {
            Port port = EasyMock.createMock(Port.class);
            EasyMock.replay(port);
            ports.add(port);
        }

        EasyMock.replay(shuffler);

        BoardManager bm = new BoardManager(shuffler, intersections);


        bm.generatePorts(false);

        EasyMock.verify(shuffler);

        for (Port port : ports) {
            EasyMock.verify(port);
        }

        for (int i = 0; i < 9; i++) {
            EasyMock.verify(intersections[BoardManager.PORT_LOCATIONS[i].getIndex1()]);
            EasyMock.verify(intersections[BoardManager.PORT_LOCATIONS[i].getIndex2()]);
        }
    }

    @Test
    public void testBoardManager_testAddResAndValToHex_expectNotLastIndex() {
        BoardManager.HexagonHelper hexagonHelper = EasyMock.createMock(BoardManager.HexagonHelper.class);

        Hexagon hexagon = EasyMock.createMock(Hexagon.class);
        Hexagon[] hexagons = new Hexagon[19];
        hexagons[0] = hexagon;

        hexagon.setResource(EasyMock.anyObject(ResourceType.class));
        EasyMock.expectLastCall().anyTimes();

        hexagon.setValue(EasyMock.anyInt());
        EasyMock.expectLastCall().anyTimes();

        hexagon.setDesert(EasyMock.anyBoolean());
        EasyMock.expectLastCall().anyTimes();

        hexagon.setHasRobber(EasyMock.anyBoolean());
        EasyMock.expectLastCall().anyTimes();

        hexagonHelper.hexagons = hexagons;
        hexagonHelper.index = 0;

        ArrayList<ResourceType> resourceTypes = new ArrayList<>();
        resourceTypes.add(ResourceType.BRICK);
        hexagonHelper.resourceTypes = resourceTypes;

        ArrayList<Integer> numberTokens = new ArrayList<>();
        numberTokens.add(2);
        hexagonHelper.numberTokens = numberTokens;

        EasyMock.replay(hexagonHelper, hexagon);

        BoardManager bm = new BoardManager();
        bm.addResAndValToHex(hexagonHelper);

        EasyMock.verify(hexagonHelper, hexagon);
    }

    @Test
    public void testBoardManager_testAddResAndValToHex_expectLastIndex() {
        BoardManager.HexagonHelper hexagonHelper = EasyMock.createMock(BoardManager.HexagonHelper.class);

        Hexagon hexagon = EasyMock.createMock(Hexagon.class);
        Hexagon[] hexagons = new Hexagon[19];
        hexagons[18] = hexagon;

        hexagon.setDesert(EasyMock.anyBoolean());
        EasyMock.expectLastCall().anyTimes();

        hexagon.setHasRobber(EasyMock.anyBoolean());
        EasyMock.expectLastCall().anyTimes();

        hexagon.setValue(EasyMock.anyInt());
        EasyMock.expectLastCall().anyTimes();

        hexagonHelper.hexagons = hexagons;
        hexagonHelper.index = 18;

        ArrayList<ResourceType> resourceTypes = new ArrayList<>();
        resourceTypes.add(ResourceType.BRICK);
        hexagonHelper.resourceTypes = resourceTypes;

        ArrayList<Integer> numberTokens = new ArrayList<>();
        numberTokens.add(2);
        hexagonHelper.numberTokens = numberTokens;

        EasyMock.replay(hexagonHelper, hexagon);

        BoardManager bm = new BoardManager();
        bm.addResAndValToHex(hexagonHelper);

        EasyMock.verify(hexagonHelper, hexagon);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 60})
    public void testBoardManager_testGetRoadsOnBoard_expectReturn(int roads) {


        ArrayList<Road> roadList = new ArrayList<>();
        for (int i = 0; i < roads; i++) {
            Road road = EasyMock.createMock(Road.class);
            roadList.add(road);
        }

        bm.roadsOnBoard = roadList;

        assertEquals(roads, bm.getRoadsOnBoard().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 20})
    public void testBoardManager_testGetStructures_expectReturn(int structures) {


        ArrayList<Structure> structureList = new ArrayList<>();
        for (int i = 0; i < structures; i++) {
            Structure structure = EasyMock.createMock(Structure.class);
            structureList.add(structure);
        }

        bm.structures = structureList;

        assertEquals(structures, bm.getStructures().size());
    }

    @Test
    public void testBoardManager_testPlaceRoad_expectRoadPlaced() {

        int val1 = 0;
        int val2 = 1;

        Player player = EasyMock.createMock(Player.class);

        Road road = EasyMock.createMock(Road.class);


        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.ownedByThisPlayer(player)).andReturn(true);

        List<Integer> adj = new ArrayList<Integer>();
        adj.add(val2);
        EasyMock.expect(inter1.getAdjacentIntersections()).andReturn(adj);

        inter1.setOwner(player);
        inter2.setOwner(player);


        inter1.setRoads(anyObject(Road.class));
        inter2.setRoads(anyObject(Road.class));

        EasyMock.replay(player, road, inter1, inter2);
        Intersection[] intersections = new Intersection[]{inter1, inter2};

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertTrue(bm.placeRoad(val1, val2, player, false));

        EasyMock.verify(player, road, inter1, inter2);

    }

    @Test
    public void testBoardManager_testPlaceRoad_expectFalse() {

        int val1 = 0;
        int val2 = 1;

        Player player = EasyMock.createMock(Player.class);


        Road road = EasyMock.createMock(Road.class);

        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.ownedByThisPlayer(player)).andReturn(false);
        EasyMock.expect(inter2.ownedByThisPlayer(player)).andReturn(false);


        EasyMock.replay(player, road, inter1, inter2);
        Intersection[] intersections = new Intersection[]{inter1, inter2};

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertFalse(bm.placeRoad(val1, val2, player, false));

        EasyMock.verify(player, road, inter1, inter2);

    }

    @Test
    public void testBoardManager_testPlaceRoad_expectFalseForInitSetup1() {

        int val1 = 0;
        int val2 = 1;

        Player player = EasyMock.createMock(Player.class);

        Structure settlement = EasyMock.createMock(Settlement.class);

        Road road = EasyMock.createMock(Road.class);

        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.getStructure()).andReturn(settlement);

        EasyMock.expect(inter1.ownedByThisPlayer(player)).andReturn(false);
        EasyMock.expect(inter2.ownedByThisPlayer(player)).andReturn(false);


        EasyMock.replay(player, road, inter1, inter2);
        Intersection[] intersections = new Intersection[]{inter1, inter2};

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertFalse(bm.placeRoad(val1, val2, player, true));

        EasyMock.verify(player, road, inter1, inter2);
    }

    @Test
    public void testBoardManager_testPlaceRoad_expectFalseForInitSetup2() {

        int val1 = 0;
        int val2 = 1;

        Player player = EasyMock.createMock(Player.class);

        Structure settlement = EasyMock.createMock(Settlement.class);

        Road road = EasyMock.createMock(Road.class);

        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.getStructure()).andReturn(null);
        EasyMock.expect(inter2.getStructure()).andReturn(settlement);

        EasyMock.expect(inter1.ownedByThisPlayer(player)).andReturn(false);
        EasyMock.expect(inter2.ownedByThisPlayer(player)).andReturn(false);


        EasyMock.replay(player, road, inter1, inter2);
        Intersection[] intersections = new Intersection[]{inter1, inter2};

        BoardManager bm = new BoardManager(intersections, new ArrayList<Intersection>(), new ArrayList<Road>());

        assertFalse(bm.placeRoad(val1, val2, player, true));

        EasyMock.verify(player, road, inter1, inter2);
    }



    @Test
    public void testBoardManager_testInitCheck_expectFalse() {

        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        EasyMock.expect(inter1.getStructure()).andReturn(null);
        EasyMock.expect(inter2.getStructure()).andReturn(null);

        EasyMock.replay(inter1, inter2);

        Intersection[] intersections = new Intersection[]{inter1, inter2};

        BoardManager bm = new BoardManager(intersections);

        assertFalse(bm.initCheck(0, 1, true));

        EasyMock.verify(inter1, inter2);
    }

    @Test
    public void testBoardManager_testInitCheck_expectTrue1() {

        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        Settlement settlement = EasyMock.createMock(Settlement.class);

        EasyMock.expect(inter1.getStructure()).andReturn(settlement);
        EasyMock.expect(inter2.getStructure()).andReturn(null);

        EasyMock.replay(inter1, inter2);

        Intersection[] intersections = new Intersection[]{inter1, inter2};

        BoardManager bm = new BoardManager(intersections);

        assertTrue(bm.initCheck(0, 1, true));
    }

    @Test
    public void testBoardManager_testInitCheck_expectTrue2() {

        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);

        Settlement settlement = EasyMock.createMock(Settlement.class);

        EasyMock.expect(inter1.getStructure()).andReturn(null);
        EasyMock.expect(inter2.getStructure()).andReturn(settlement);

        EasyMock.replay(inter1, inter2);

        Intersection[] intersections = new Intersection[]{inter1, inter2};

        BoardManager bm = new BoardManager(intersections);

        assertTrue(bm.initCheck(0, 1, true));
    }


    @ParameterizedTest
    @ValueSource(ints = {0,1,2})
    public void testHexagonHelper_updateCurrentRowSize(int index){
        BoardManager manager = new BoardManager();
        manager.initializeBoardStructure(true);
        BoardManager.HexagonHelper helper = new BoardManager.HexagonHelper(manager.shuffler);
        helper.updateCurrentRowSize(index);
        assertEquals(5-index, helper.currentRowSize);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2})
    public void testIntersectionHelper_updateCurrentRowSize(int index){
        BoardManager manager = new BoardManager();
        manager.initializeBoardStructure(true);
        BoardManager.IntersectionHelper helper = new BoardManager.IntersectionHelper();
        helper.updateCurrentRowSize(index);
        assertEquals(6-index, helper.currentRowSize);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,53})
    public void testPlaceCity_differentIndices(int index){
        BoardManager manager = new BoardManager();
        manager.initializeBoardStructure(true);
        City city = EasyMock.createMock(City.class);
        Player player = EasyMock.createMock(Player.class);
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Intersection inter = EasyMock.createMock(Intersection.class);
        manager.intersections[index] = inter;

        city.setOwner(player);
        EasyMock.expect(player.getNumCities()).andReturn(1);
        player.setNumCities(0);
        EasyMock.expect(inter.getStructure()).andReturn(settlement);
        inter.setStructure(city);

        EasyMock.replay(city,inter,player);
        manager.placeCity(city, index, player);

        EasyMock.verify(city,inter,player);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,4})
    public void testPlaceCity_differentAmounts(int amount){
        BoardManager manager = new BoardManager();
        manager.initializeBoardStructure(true);
        City city = EasyMock.createMock(City.class);
        Player player = EasyMock.createMock(Player.class);
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Intersection inter = EasyMock.createMock(Intersection.class);
        manager.intersections[0] = inter;

        city.setOwner(player);
        EasyMock.expect(player.getNumCities()).andReturn(amount);
        player.setNumCities(amount-1);
        EasyMock.expect(inter.getStructure()).andReturn(settlement);
        inter.setStructure(city);

        EasyMock.replay(city,inter,player);
        manager.placeCity(city, 0, player);

        EasyMock.verify(city,inter,player);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,53})
    public void testMakeSettlement_differentIndices(int index){
        BoardManager manager = new BoardManager();
        manager.initializeBoardStructure(true);
        Player player = EasyMock.createMock(Player.class);
        Settlement settlement = EasyMock.createMock(Settlement.class);
        Intersection inter = EasyMock.createMock(Intersection.class);
        manager.intersections[index] = inter;

        settlement.setOwner(player);
        inter.setStructure(settlement);
        inter.setOwner(player);

        EasyMock.replay(settlement,inter,player);
        manager.makeSettlement(index, player, settlement);

        EasyMock.verify(settlement,inter,player);
    }

    @ParameterizedTest(name="with {0} intersection(s)")
    @ValueSource(ints = {1,2})
    void testLinkHexagonsAndIntersections(int numInter) {
        Hexagon hex     = EasyMock.createNiceMock(Hexagon.class);
        Point2D hexCtr  = EasyMock.createNiceMock(Point2D.class);
        EasyMock.expect(hex.getCenter()).andReturn(hexCtr).anyTimes();
        EasyMock.replay(hex, hexCtr);

        InterFixture[] fixtures = IntStream.range(0, numInter)
                .mapToObj(i -> new InterFixture(hexCtr))
                .toArray(InterFixture[]::new);

        Hexagon[]     hexes = { hex };
        Intersection[] inters = Arrays.stream(fixtures)
                .map(f -> f.inter)
                .toArray(Intersection[]::new);

        new BoardManager().linkHexagonsAndIntersections(hexes, inters);

        EasyMock.verify(hex, hexCtr);
        for (InterFixture f : fixtures) {
            EasyMock.verify(f.inter, f.center);
        }
    }


    @Test
    public void testAddHexagonIfAdjacent_add(){
        Point2D point = EasyMock.createMock(Point2D.class);
        Point2D hexPoint = EasyMock.createMock(Point2D.class);
        Hexagon hex = EasyMock.createMock(Hexagon.class);

        BoardManager board = new BoardManager();
        ArrayList<Hexagon> hexes = new ArrayList<>();

        EasyMock.expect(hex.getCenter()).andReturn(hexPoint);
        EasyMock.expect(point.distance(hexPoint)).andReturn(0.59);

        EasyMock.replay(hex,point,hexPoint);
        board.addHexagonIfAdjacent(hexes, point, hex);

        assertEquals(1,hexes.size());
        assertEquals(hexes.get(0),hex);
        EasyMock.verify(hex,point,hexPoint);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.6,0.61})
    public void testAddHexagonIfAdjacent_noAdd(double distance){
        Point2D point = EasyMock.createMock(Point2D.class);
        Point2D hexPoint = EasyMock.createMock(Point2D.class);
        Hexagon hex = EasyMock.createMock(Hexagon.class);

        BoardManager board = new BoardManager();
        ArrayList<Hexagon> hexes = new ArrayList<>();

        EasyMock.expect(hex.getCenter()).andReturn(hexPoint);
        EasyMock.expect(point.distance(hexPoint)).andReturn(distance);

        EasyMock.replay(hex,point,hexPoint);
        board.addHexagonIfAdjacent(hexes, point, hex);

        assertTrue(hexes.isEmpty());
        EasyMock.verify(hex,point,hexPoint);
    }

    @Test
    public void testAddIntersectionIfAdjacent_add(){
        Point2D point = EasyMock.createMock(Point2D.class);
        Point2D interPoint = EasyMock.createMock(Point2D.class);
        Intersection inter = EasyMock.createMock(Intersection.class);

        BoardManager board = new BoardManager();
        ArrayList<Intersection> inters = new ArrayList<>();

        EasyMock.expect(inter.getCenter()).andReturn(interPoint);
        EasyMock.expect(point.distance(interPoint)).andReturn(0.59);

        EasyMock.replay(inter,point,interPoint);
        board.addIntersectionIfAdjacent(inters, point, inter);

        assertEquals(1,inters.size());
        assertEquals(inters.get(0),inter);
        EasyMock.verify(inter,point,interPoint);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.6,0.61})
    public void testAddIntersectionIfAdjacent_noAdd(double distance){
        Point2D point = EasyMock.createMock(Point2D.class);
        Point2D interPoint = EasyMock.createMock(Point2D.class);
        Intersection inter = EasyMock.createMock(Intersection.class);

        BoardManager board = new BoardManager();
        ArrayList<Intersection> hexes = new ArrayList<>();

        EasyMock.expect(inter.getCenter()).andReturn(interPoint);
        EasyMock.expect(point.distance(interPoint)).andReturn(distance);

        EasyMock.replay(inter,point,interPoint);
        board.addIntersectionIfAdjacent(hexes, point, inter);

        assertTrue(hexes.isEmpty());
        EasyMock.verify(inter,point,interPoint);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-2, 0, 2})
    public void testAddHexagon(double distance) {
        BoardManager board = new BoardManager();

        Shuffler shuffler = EasyMock.createMock(Shuffler.class);

        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(0);
        ArrayList<ResourceType> resources = new ArrayList<>();
        resources.add(GRAIN);

        EasyMock.expect(shuffler.getShuffledResourceTypes()).andReturn(resources).anyTimes();
        EasyMock.expect(shuffler.getShuffledNumberTokens()).andReturn(ints).anyTimes();

        EasyMock.replay(shuffler);

        BoardManager.HexagonHelper helper = new BoardManager.HexagonHelper(shuffler);

        helper.hexagons = new Hexagon[19];
        helper.hexagons[0] = new Hexagon(new Point2D.Double(0, 0), 0);

        board.addHexagon(helper, distance, distance);

        assertNotNull(helper.hexagons[0]);
        assertEquals(0, helper.hexagons[0].getUniqueIndex());

        Point2D center = helper.hexagons[0].getCenter();
        assertEquals(distance, center.getX(), 0.01);
        assertEquals(distance, center.getY(), 0.01);

        EasyMock.verify(shuffler);
    }

    @Test
    public void testBoardManager_testReassignHexIfNullFound_expectSwapped() {

        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getResource()).andReturn(ResourceType.BRICK);
        EasyMock.expect(hex2.getResource()).andReturn(null);
        hex2.setResource(ResourceType.BRICK);

        Hexagon[] hexagons = new Hexagon[]{hex1, hex2};

        BoardManager bm = new BoardManager(hexagons);

        EasyMock.replay(hex1, hex2);

        bm.swapWithHexIfNullFound(hexagons, ResourceType.BRICK);

        EasyMock.verify(hex1, hex2);
    }

    @Test
    public void testBoardManager_testReassignResourceIfValueIsZero() {

        Hexagon hex1 = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);

        EasyMock.expect(hex1.getValue()).andReturn(0);

        EasyMock.expect(hex1.getResource()).andReturn(ResourceType.BRICK);
        EasyMock.expect(hex1.getResource()).andReturn(ResourceType.BRICK);
        EasyMock.expect(hex2.getResource()).andReturn(null);
        hex2.setResource(ResourceType.BRICK);
        hex1.setResource(null);

        Hexagon[] hexagons = new Hexagon[]{hex1, hex2};

        BoardManager bm = new BoardManager(hexagons);

        EasyMock.replay(hex1, hex2);

        bm.reassignResourceIfValueIsZero(hexagons, hex1);

        EasyMock.verify(hex1, hex2);
    }

    @Test
    public void testBoardManager_assignPredeterminedResources() {

        Hexagon[] hexagons = new Hexagon[19];

        for (int i = 0; i < 19; i++) {
            hexagons[i] = EasyMock.createMock(Hexagon.class);
            hexagons[i].setResource(EasyMock.anyObject(ResourceType.class));
            EasyMock.replay(hexagons[i]);
        }

        BoardManager bm = new BoardManager(hexagons);

        ResourceType[] predeterminedResources = getPredeterminedResources();

        bm.assignPredeterminedResources(hexagons, predeterminedResources);

        for (int i = 0; i < 19; i++) {
            EasyMock.verify(hexagons[i]);
        }
    }

    private ResourceType[] getPredeterminedResources() {
        return new ResourceType[]{null, ResourceType.LUMBER, ResourceType.LUMBER, ResourceType.ORE,
            ResourceType.GRAIN, ResourceType.GRAIN, ResourceType.ORE, ResourceType.WOOL,
            ResourceType.LUMBER, ResourceType.WOOL, ResourceType.BRICK, ResourceType.BRICK,
            ResourceType.GRAIN, ResourceType.GRAIN, ResourceType.WOOL, ResourceType.BRICK,
            ResourceType.WOOL, ResourceType.LUMBER, ResourceType.ORE};
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.6,0.61})
    public void testSetIntersectionAsAdjacent_noAdd(double distance ){
        Point2D interPoint1 = EasyMock.createMock(Point2D.class);
        Point2D interPoint2 = EasyMock.createMock(Point2D.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);
        Intersection inter1 = EasyMock.createMock(Intersection.class);

        BoardManager board = new BoardManager();
        Intersection[] inters = new Intersection[2];
        inters[1] = inter2;
        inters[0] = inter1;

        ArrayList<Integer> adjacent = new ArrayList<>();

        EasyMock.expect(inter1.getAdjacentIntersections()).andReturn(adjacent);

        EasyMock.expect(inter2.getCenter()).andReturn(interPoint2);
        EasyMock.expect(interPoint1.distance(interPoint2)).andReturn(distance);

        EasyMock.replay(inter1,inter2,interPoint1,interPoint2);
        board.setIntersectionAsAdjacent(inters, 0, interPoint1,1);

        EasyMock.verify(inter2,interPoint1,interPoint2,inter1);
    }


    @Test
    public void testMakeRoad_simpleRoad1(){
        BoardManager manager = new BoardManager();
        manager.initializeBoardStructure(true);
        Player player = EasyMock.createMock(Player.class);
        Road road = EasyMock.createMock(Road.class);
        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);
        manager.intersections[0] = inter1;
        manager.intersections[6] = inter2;

        road.setIntersections(inter1,inter2);
        road.setOwner(player);
        inter1.setRoads(road);
        inter2.setRoads(road);

        EasyMock.replay(road,inter1,inter2,player);
        manager.makeRoad(road, 0, 6, player);

        EasyMock.verify(road,inter1,inter2,player);
    }

    @Test
    public void testMakeRoad_simpleRoad2(){
        BoardManager manager = new BoardManager();
        manager.initializeBoardStructure(true);
        Player player = EasyMock.createMock(Player.class);
        Road road = EasyMock.createMock(Road.class);
        Intersection inter1 = EasyMock.createMock(Intersection.class);
        Intersection inter2 = EasyMock.createMock(Intersection.class);
        manager.intersections[11] = inter1;
        manager.intersections[29] = inter2;

        road.setIntersections(inter1,inter2);
        road.setOwner(player);
        inter1.setRoads(road);
        inter2.setRoads(road);

        EasyMock.replay(road,inter1,inter2,player);
        manager.makeRoad(road, 11, 29, player);

        EasyMock.verify(road,inter1,inter2,player);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,19})
    public void testGetHexagons(int numHex){
        Hexagon[] hexes = new Hexagon[numHex];
        for(int i = 0; i < numHex;i++){
            hexes[i] = EasyMock.createMock(Hexagon.class);
        }

        BoardManager board = new BoardManager();
        board.hexagons = hexes;

        Hexagon[] dupe = board.getHexagons();

        assertArrayEquals(dupe,hexes);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    public void testInitializeBoardStructure(boolean random){
        BoardManager boardManager = new BoardManager();
        ArrayList<Port> initialPorts = boardManager.ports;
        boardManager.initializeBoardStructure(random);
        assertTrue(initialPorts.isEmpty());
        assertFalse(boardManager.ports.isEmpty());
    }

    @Test
    public void testRoadFound_neither() {
        Road road = EasyMock.createMock(Road.class);

        BoardManager manager = new BoardManager();
        Intersection[] roadIntersections = new Intersection[2];
        Intersection[] otherIntersections = new Intersection[2];

        for(int i = 0; i < 2; i++) {
            roadIntersections[i] = EasyMock.createMock(Intersection.class);
        }

        otherIntersections[0] = EasyMock.createMock(Intersection.class);
        otherIntersections[1] = EasyMock.createMock(Intersection.class);

        manager.intersections = otherIntersections;

        EasyMock.expect(road.getIntersections()).andReturn(roadIntersections).anyTimes();

        EasyMock.replay(road, roadIntersections[0], roadIntersections[1], otherIntersections[0], otherIntersections[1]);

        assertFalse(manager.roadFound(0, 1, road));

        EasyMock.verify(road);
    }

    @Test
    public void testRoadFound_both() {
        Road road = EasyMock.createMock(Road.class);

        BoardManager manager = new BoardManager();
        Intersection[] roadIntersections = new Intersection[2];
        Intersection[] otherIntersections = new Intersection[2];

        for(int i = 0; i < 2; i++) {
            Intersection inter = EasyMock.createMock(Intersection.class);
            roadIntersections[i] = inter;
            otherIntersections[i] = inter;
        }

        manager.intersections = otherIntersections;

        EasyMock.expect(road.getIntersections()).andReturn(roadIntersections).anyTimes();

        EasyMock.replay(road, roadIntersections[0], roadIntersections[1]);

        assertTrue(manager.roadFound(0, 1, road));

        EasyMock.verify(road);
    }

    @Test
    public void testRoadFound_left() {
        Road road = EasyMock.createMock(Road.class);

        BoardManager manager = new BoardManager();
        Intersection[] roadIntersections = new Intersection[2];
        Intersection[] otherIntersections = new Intersection[2];

        Intersection shared = EasyMock.createMock(Intersection.class);
        Intersection separate1 = EasyMock.createMock(Intersection.class);
        Intersection separate2 = EasyMock.createMock(Intersection.class);

        roadIntersections[0] = shared;
        roadIntersections[1] = separate1;

        otherIntersections[0] = shared;
        otherIntersections[1] = separate2;

        manager.intersections = otherIntersections;

        EasyMock.expect(road.getIntersections()).andReturn(roadIntersections).anyTimes();

        EasyMock.replay(road, shared, separate1, separate2);

        assertFalse(manager.roadFound(0, 1, road));

        EasyMock.verify(road);
    }

    @Test
    public void testRoadFound_right() {
        Road road = EasyMock.createMock(Road.class);

        BoardManager manager = new BoardManager();
        Intersection[] roadIntersections = new Intersection[2];
        Intersection[] otherIntersections = new Intersection[2];

        Intersection shared = EasyMock.createMock(Intersection.class);
        Intersection separate1 = EasyMock.createMock(Intersection.class);
        Intersection separate2 = EasyMock.createMock(Intersection.class);

        roadIntersections[0] = separate1;
        roadIntersections[1] = shared;

        otherIntersections[0] = separate2;
        otherIntersections[1] = shared;

        manager.intersections = otherIntersections;

        EasyMock.expect(road.getIntersections()).andReturn(roadIntersections).anyTimes();

        EasyMock.replay(road, shared, separate1, separate2);

        assertFalse(manager.roadFound(0, 1, road));

        EasyMock.verify(road);
    }

    private static class InterFixture {
        final Intersection inter;
        final Point2D center;

        InterFixture(Point2D hexCenter) {
            this.inter  = EasyMock.createNiceMock(Intersection.class);
            this.center = EasyMock.createNiceMock(Point2D.class);

            EasyMock.expect(center.distance(hexCenter)).andReturn(0.1).anyTimes();
            EasyMock.expect(inter.getCenter())       .andReturn(center).anyTimes();

            inter.setHexagons(EasyMock.anyObject(Hexagon[].class));

            EasyMock.replay(inter, center);
        }
    }
    
// Need to complete and finish with EasyMock for automatic testing
// and CI implementation; tests pass when gd has manual intervention
    
//    @Test
//    public void testGetFormattedResourceStolenBrick() {
//    	GameDisplay gd = new GameDisplay(true);
//    	String resource = "BRICK";
//    	@SuppressWarnings("static-access")
//		String s = gd.getFormattedResourceStolen(resource);
//    	assertEquals(s, "Brick");
//    }
//    
//    @Test
//    public void testGetFormattedResourceStolenGrain() {
//    	GameDisplay gd = new GameDisplay(true);
//    	String resource = "GRAIN";
//    	@SuppressWarnings("static-access")
//		String s = gd.getFormattedResourceStolen(resource);
//    	assertEquals(s, "Grain");
//    }
//    
//    @Test
//    public void testGetFormattedResourceStolenLumber() {
//    	GameDisplay gd = new GameDisplay(true);
//    	String resource = "LUMBER";
//    	@SuppressWarnings("static-access")
//		String s = gd.getFormattedResourceStolen(resource);
//    	assertEquals(s, "Lumber");
//    }
//    
//    @Test
//    public void testGetFormattedResourceStolenWool() {
//    	GameDisplay gd = new GameDisplay(true);
//    	String resource = "WOOL";
//    	@SuppressWarnings("static-access")
//		String s = gd.getFormattedResourceStolen(resource);
//    	assertEquals(s, "Wool");
//    }
//    
//    @Test
//    public void testGetFormattedResourceStolenOre() {
//    	GameDisplay gd = new GameDisplay(true);
//    	String resource = "ORE";
//    	@SuppressWarnings("static-access")
//		String s = gd.getFormattedResourceStolen(resource);
//    	assertEquals(s, "Ore");
//    }
//    
//    @Test
//    public void testGetFormattedResourceStolenRandom() {
//    	GameDisplay gd = new GameDisplay(true);
//    	String resource = "QWERTY";
//    	@SuppressWarnings("static-access")
//		String s = gd.getFormattedResourceStolen(resource);
//    	assertEquals(s, "");
//    }
}


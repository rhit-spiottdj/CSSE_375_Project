package domain;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

public class IntersectionTest {

    @Test
    public void testIntersection_setAndGetPort(){
        Point2D point = EasyMock.createMock(Point2D.class);
        Intersection intersection = new Intersection(point, 0);
        Port port = EasyMock.createMock(Port.class);

        intersection.setPort(port);
        Port returnedPort = intersection.getPort();

        assertEquals(port, returnedPort);
        assertEquals(port, intersection.port);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1})
    public void testIntersection_getUniqueIndex(int index){
        Point2D point = EasyMock.createMock(Point2D.class);
        Intersection intersection = new Intersection(point, index);

        assertEquals(index, intersection.getUniqueIndex());
    }

    @Test
    public void testIntersection_setAndGetHexagons(){
        Point2D point = EasyMock.createMock(Point2D.class);
        Intersection intersection = new Intersection(point, 0);
        Hexagon hex = EasyMock.createMock(Hexagon.class);
        Hexagon hex2 = EasyMock.createMock(Hexagon.class);
        Hexagon[] hexes = new Hexagon[]{hex,hex2};

        intersection.setHexagons(hexes);
        Hexagon[] returnedHexes = intersection.getHexagons();

        assertArrayEquals(hexes, returnedHexes);
        assertArrayEquals(hexes, intersection.hexagons);
    }

    @Test
    public void testRemoveRoad_inMap(){
        Point2D point = EasyMock.createMock(Point2D.class);
        Intersection intersection = new Intersection(point, 0);
        Road road = EasyMock.createMock(Road.class);
        intersection.roads.add(road);

        intersection.removeRoad(road);

        assertTrue(intersection.roads.isEmpty());
    }

    @Test
    public void testRemoveRoad_notInMap(){
        Point2D point = EasyMock.createMock(Point2D.class);
        Intersection intersection = new Intersection(point, 0);
        Road road = EasyMock.createMock(Road.class);
        Road road2 = EasyMock.createMock(Road.class);
        intersection.roads.add(road2);

        intersection.removeRoad(road);

        assertEquals(1,intersection.roads.size());
        assertNotEquals(intersection.roads.iterator().next(),road);
    }
}

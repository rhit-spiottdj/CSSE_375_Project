package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HexagonTest {

    @ParameterizedTest
    @ValueSource(ints = {0,1})
    public void testHexagon_getUniqueIndex(int index){
        Point2D point = EasyMock.createMock(Point2D.class);
        Intersection intersection = new Intersection(point, index);

        assertEquals(index, intersection.getUniqueIndex());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    public void testHexagon_getAndSetIsDesert(boolean desert){
        Point2D point = EasyMock.createMock(Point2D.class);
        Hexagon hex = new Hexagon(point,0);

        hex.setDesert(desert);
        assertEquals(desert, hex.isDesert);
        assertEquals(desert, hex.getIsDesert());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    public void testHexagon_getAndSetHasRobber(boolean hasRobber){
        Point2D point = EasyMock.createMock(Point2D.class);
        Hexagon hex = new Hexagon(point,0);

        hex.setHasRobber(hasRobber);
        assertEquals(hasRobber, hex.hasRobber);
        assertEquals(hasRobber, hex.getHasRobber());
    }
}

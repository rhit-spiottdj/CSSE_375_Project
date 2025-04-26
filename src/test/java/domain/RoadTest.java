package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RoadTest {

    //NOTE: this also covers the false test case in BVA
    @Test
    public void createRoad_testDefaultValues() {
        Road road = new Road();
        assertFalse(road.isVisited());
    }

    @Test
    public void createRoad_testWithTrueValue() {
        Road road = new Road();
        road.setVisited(true);

        assertTrue(road.isVisited());
    }

    @Test
    public void createRoad_testWithVisitingRoadAndClearingVisited() {
        Road road = new Road();

        assertFalse(road.isVisited());

        road.setVisited(true);

        assertTrue(road.isVisited());

        road.setVisited(false);

        assertFalse(road.isVisited());
    }

    @ParameterizedTest
    @MethodSource("colorParameters")
    public void testRoad_getOwnerColor(Color color){
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.getPlayerColor()).andReturn(color);
        Road road = new Road();
        road.setOwner(player);

        EasyMock.replay(player);
        Color returnedColor = road.getOwnerColor();
        assertEquals(returnedColor, color);
        EasyMock.verify(player);
    }

    private static Stream<Color> colorParameters(){
        return Stream.of(Color.RED, Color.BLUE, Color.GREEN, Color.BLACK);
    }
}

package domain;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityTest {

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void testCity_distributeAllResourcesToMockPlayer(ResourceType resource) {
        Player playerMock = EasyMock.mock(Player.class);
        playerMock.addResource(resource);
        playerMock.addResource(resource);
        EasyMock.replay(playerMock);

        City city = new City();
        city.setOwner(playerMock);

        city.distributeResources(resource);
        EasyMock.verify(playerMock);
    }

    @Test
    public void testCity_getCost(){
        Collection<ResourceType> cost = City.getCost();

        assertTrue(cost.remove(ResourceType.ORE));
        assertTrue(cost.remove(ResourceType.ORE));
        assertTrue(cost.remove(ResourceType.ORE));
        assertTrue(cost.remove(ResourceType.GRAIN));
        assertTrue(cost.remove(ResourceType.GRAIN));
        assertTrue(cost.isEmpty());
    }

    @Test
    public void testCity_getVictoryPoints(){
        City city = new City();
        assertEquals(2, city.getVictoryPoints());
    }
}

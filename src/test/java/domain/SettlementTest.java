package domain;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SettlementTest {

    @EnumSource (ResourceType.class)
    @ParameterizedTest
    public void createSettlement_testDistributeEachResourceToMockedPlayer(ResourceType resource) {
        Player playerMock = EasyMock.mock(Player.class);
        playerMock.addResource(resource);
        EasyMock.replay(playerMock);

        Settlement settlement = new Settlement();
        settlement.setOwner(playerMock);

        settlement.distributeResources(resource);
        EasyMock.verify(playerMock);

    }

    @Test
    public void testSettlement_getCost(){
        Collection<ResourceType> cost = Settlement.getCost();

        assertTrue(cost.remove(ResourceType.BRICK));
        assertTrue(cost.remove(ResourceType.LUMBER));
        assertTrue(cost.remove(ResourceType.WOOL));
        assertTrue(cost.isEmpty());
    }

    @Test
    public void testSettlement_getVictoryPoints(){
        Settlement settlement = new Settlement();
        assertEquals(1, settlement.getVictoryPoints());
    }
}
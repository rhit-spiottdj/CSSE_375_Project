package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class City extends Structure {


    private static final List<ResourceType>
            COST = List.of(ResourceType.ORE, ResourceType.ORE, ResourceType.ORE,
            ResourceType.GRAIN, ResourceType.GRAIN);

    @Override
    public void distributeResources(ResourceType resourceType) {
        Player p = super.getOwner();
        if (p != null) {
            p.addResource(resourceType);
            p.addResource(resourceType);
        }
    }

    @Override
    public int getVictoryPoints() {
        return 2;
    }

    public static Collection<ResourceType> getCost() {
        return new ArrayList<>(COST);
    }
}
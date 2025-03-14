package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Settlement extends Structure {


    private static final List<ResourceType>
        COST = List.of(ResourceType.BRICK, ResourceType.LUMBER, ResourceType.GRAIN,
        ResourceType.WOOL);

    public Settlement() {
    }

    public static Collection<ResourceType> getCost() {
        return new ArrayList<>(COST);
    }

    @Override
    public void distributeResources(ResourceType resourceType) {
        Player p = super.getOwner();
        p.addResource(resourceType);
    }

    @Override
    public int getVictoryPoints() {
        return 1;
    }
}
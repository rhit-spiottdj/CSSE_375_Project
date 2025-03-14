package domain;

import java.awt.*;

import java.util.Collection;
import java.util.List;

public class Road {

    private static final java.util.List<ResourceType>
        COST = List.of(ResourceType.BRICK, ResourceType.LUMBER);
    private boolean isVisited;

    private Player owner = null;

    public Intersection[] intersections = new Intersection[2];

    //Will need an owner (Player) and location (Edge or Point) associated with the Road as well

    public Road() {
        this.isVisited = false;
    }

    public void setIntersections(Intersection a, Intersection b){
        intersections[0] = a;
        intersections[1] = b;
    }

    public Intersection[] getIntersections() {
        return intersections;
    }

    //These methods are for calculating the longest road
    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean value) {
        this.isVisited = value;
    }

    protected void setOwner(Player player) {
        this.owner = player;
    }

    protected Player getOwner() {
        return owner;
    }

    public Color getOwnerColor() {
        return owner.getPlayerColor();
    }

    public Intersection getIntersection(int i) {
        return intersections[i];
    }

    public Intersection getOtherIntersection(Intersection intersection) {
        if (intersections[0] == intersection) {
            return intersections[1];
        } else {
            return intersections[0];
        }
    }

    public static Collection<ResourceType> getCost() {
        return COST;
    }
}

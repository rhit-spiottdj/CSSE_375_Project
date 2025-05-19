package domain;

import java.awt.geom.Point2D;

public class Hexagon {

    public Point2D center;

    private ResourceType resource;

    private Intersection[] intersections;

    private int value;

    boolean isDesert;

    int uniqueIndex;

    boolean hasRobber;

    public static final int NUMBER_OF_INTERSECTIONS = 6;

    public Hexagon(Point2D center, int uniqueIndex){
        this.uniqueIndex = uniqueIndex;
        this.center = center;
        this.value = 0;
    }


    public ResourceType getResource(){
        return resource;
    }

    public void setResource(ResourceType resource){
        this.resource = resource;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
    protected void setIntersections(Intersection[] intersections){
        this.intersections = intersections;
    }

    public Intersection[] getIntersections(){
        return intersections.clone();
    }

    public Point2D getCenter(){
        return (Point2D) center.clone();
    }

    public boolean getIsDesert(){
        return isDesert;
    }
    public void setDesert(boolean isDesert){
        this.isDesert = isDesert;
    }

    public int getUniqueIndex() {
        return uniqueIndex;
    }

    public void setHasRobber(boolean hasRobber){
        this.hasRobber = hasRobber;
    }

    public boolean getHasRobber() {
        return hasRobber;

    }

}

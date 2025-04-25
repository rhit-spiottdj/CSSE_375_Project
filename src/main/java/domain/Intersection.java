package domain;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class Intersection {

    private Point2D center;

    Hexagon hexagons[];

    public Structure structure;

    HashSet<Road> roads = new HashSet<Road>();

    private int uniqueIndex;

    private HashSet<Player> owners = new HashSet<Player>();

    private List<Integer> adjacentIntersections = new ArrayList<>();

    Port port;

    protected Intersection(Point2D center, int uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
        this.center = center;
    }

    protected void setHexagons(Hexagon[] hexagons) {
        this.hexagons = hexagons;
    }

    protected void setStructure(Structure structure) {
        this.structure = structure;
        if (structure != null) {
            setOwner(structure.getOwner());
        }
    }

    protected void setOwner(Player player) {
        owners.add(player);
    }

    protected void addAdjacentIntersection(int adjacentIntersection) {
        adjacentIntersections.add(adjacentIntersection);
    }

    protected List<Integer> getAdjacentIntersections() {
        return new ArrayList<>(adjacentIntersections); 
    }

    protected Hexagon[] getHexagons() {
        return (hexagons == null) ? null : hexagons.clone();
    }

    public Structure getStructure() {
        return structure;
    }

    protected boolean ownedByThisPlayer(Player player) {
        return owners.contains(player);
    }

    public Point2D getCenter() {
        return new Point2D.Double(center.getX(), center.getY());
    }

    public int getUniqueIndex() {
        return uniqueIndex;
    }

    protected void setRoads(Road road) {
        roads.add(road);
    }

    public HashSet<Road> getRoads() {
        return new HashSet<>(roads);
    }

    void removeRoad(Road road) {
        roads.remove(road);
    }

    public boolean notEnemySettlement(Player owner) {
        return structure == null || structure.getOwner() == owner;
    }

    public Port getPort(){
        return port;
    }

    public void setPort(Port port){
        this.port = port;
    }
    
    public HashSet<Player> getOwners() {
    	return this.owners;
    }

}
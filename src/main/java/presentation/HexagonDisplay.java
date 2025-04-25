package presentation;

import domain.Hexagon;
import domain.Intersection;
import domain.ResourceType;
import domain.Road;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

import static presentation.BoardDisplay.*;

public class HexagonDisplay {

    public static final int TOP_LEFT = 2;
    public static final int BOTTOM_RIGHT = 5;
    public static final int MIDDLE_LEFT = 3;
    public static final int BOTTOM_LEFT = 4;
    public static final int MIDDLE_RIGHT = 0;
    public static final int TOP_RIGHT = 1;
    public static final int HEX_30_DEGREES = 30;
    public static final int HEX_90_DEGREES = 90;
    public static final int HEX_150_DEGREES = 150;
    private static final int HEX_RADIUS = 75;
    private static final int HEXAGON_BORDER_ADDON = 6;
    public static final int HEX_BORDER_RADIUS = HEX_RADIUS + HEXAGON_BORDER_ADDON;
    private static final int HEX_NUM_SIDES = 6;
    private static final int HEX_STROKE_WIDTH = 5;
    private static final int HEX_BOUNDARY_STROKE_WIDTH = 9;
    private static final int[] borderColor = {179, 143, 0};
    private static final int[] desertColor = {255, 255, 153};
    private static final int[] oreColor = {96, 96, 96};
    private static final int[] lumberColor = {0, 100, 0};
    private static final int[] woolColor = {144, 238, 144};
    private static final int[] brickColor = {139, 0, 0};
    private static final int[] grainColor = {255, 255, 0};
    private static final int LABEL_FONT_SIZE = 20;
    private static final int TEXT_OFFSET_X = 12;
    private static final int TEXT_OFFSET_Y = 5;
    private static final int OVAL_WIDTH = 30;
    private static final int OVAL_HEIGHT = 30;
    private static final int OVAL_OFFSET = 15;
    public static final int HALF_CIRCLE_DEGREES = 180;
    private final Hexagon hex;
    private final boolean displayUnique;
    private final ArrayList<Road> roadsToDraw = new ArrayList<>();
    protected Polygon hexToDraw;
    protected Polygon hexBoundaryPathToDraw;
    private Point2D displayCenter;
    boolean highlighted = false;
    private final Color highlightColor = Color.CYAN;


    protected HexagonDisplay(Hexagon hexClass, boolean displayUnique) {
        this.displayUnique = displayUnique;
        this.hex = hexClass;
        initializeDisplayCenter(hexClass);

        hexToDraw = generateHexagon(displayCenter, HEX_RADIUS);
        hexBoundaryPathToDraw = generateHexagon(displayCenter, HEX_BORDER_RADIUS);

    }

    private void initializeDisplayCenter(Hexagon hexClass) {
        Point2D center = hexClass.getCenter();
        int centerX = (int) (X_CENTER_MULT * center.getX() + X_CENTER_OFFSET);
        int centerY = (int) (Y_CENTER_MULT * center.getY() + Y_CENTER_OFFSET);

        displayCenter = new Point2D.Double(centerX, centerY);
    }

    private int determineHexSideFromAveragePoint(Point2D point) {
        double theta = Math.atan2(point.getY(), point.getX());
        if (theta > 0) {
            return getRightHexIndex(theta);
        } else  return getLeftHexIndex(theta);
    }

    private Integer getLeftHexIndex(double theta) {
        if (theta > degreesToRadian(-HEX_30_DEGREES))   return BOTTOM_LEFT;
        else if (theta > degreesToRadian(-HEX_90_DEGREES))    return MIDDLE_LEFT;
        else if (theta > degreesToRadian(-HEX_150_DEGREES))   return TOP_LEFT;
        return TOP_RIGHT;
    }

    private Integer getRightHexIndex(double theta) {
        if (theta < degreesToRadian(HEX_30_DEGREES))    return BOTTOM_LEFT;
        else if (theta < degreesToRadian(HEX_90_DEGREES)) return BOTTOM_RIGHT;
        else if (theta < degreesToRadian(HEX_150_DEGREES))    return MIDDLE_RIGHT;
        return TOP_RIGHT;
    }

    private double degreesToRadian(double degrees) {
        return degrees * Math.PI / HALF_CIRCLE_DEGREES;
    }

    private Polygon generateHexagon(Point2D point, int size) {
        Polygon hexagon = new Polygon();
        generateHexagonPoints(point, size, hexagon);
        return hexagon;
    }

    private void generateHexagonPoints(Point2D point, int size, Polygon hexagon) {
        for (int i = 0; i < Hexagon.NUMBER_OF_INTERSECTIONS; i++) {
            Point2D pointRes = generateSide(point, size, i);
            hexagon.addPoint((int) pointRes.getX(), (int) pointRes.getY());
        }
    }

    private Point2D generateSide(Point2D point, int size, int index) {
        int valX = (int) (point.getX() + size *
                Math.cos(Math.PI / 2 + index * 2 * Math.PI / HEX_NUM_SIDES));
        int valY = (int) (point.getY() + size *
                Math.sin(Math.PI / 2 + index * 2 * Math.PI / HEX_NUM_SIDES));

        return new Point2D.Double(valX, valY);
    }


    public void drawHexShape(Graphics2D g2) {
        drawInnerHexagon(g2);

        drawPathAroundHexagon(g2);

        findAllRoadsToDraw();

        drawRoadLine(g2);
    }

    private void findAllRoadsToDraw() {
        for (int i = 0; i < Hexagon.NUMBER_OF_INTERSECTIONS; i++) {
            HashSet<Road> roads = hex.getIntersections()[i].getRoads();
            findRoadsToDraw(roads);
        }
    }

    private void drawPathAroundHexagon(Graphics2D g2) {
        if(highlighted) {
            g2.setColor(highlightColor);
            g2.setStroke(new BasicStroke(HEX_BOUNDARY_STROKE_WIDTH + 4)); // Make highlight thicker
        } else {
            g2.setColor(new Color(borderColor[0], borderColor[1], borderColor[2]));
            g2.setStroke(new BasicStroke(HEX_BOUNDARY_STROKE_WIDTH));
        }
        g2.drawPolygon(hexBoundaryPathToDraw);
    }

    private void drawInnerHexagon(Graphics2D g2) {
        Color resourceColor = getColorFromResource();
        g2.setColor(resourceColor);
        g2.setStroke(new BasicStroke(HEX_STROKE_WIDTH));
        g2.fillPolygon(hexToDraw);
        g2.drawPolygon(hexToDraw);
    }

    private void findRoadsToDraw(HashSet<Road> roads) {
        for (Road road : roads) {
            checkAndAddIfRoadToDraw(road);
        }
    }

    private void checkAndAddIfRoadToDraw(Road road) {
        boolean[] hasIntersections = new boolean[]{false,false};
        checkIfRoadHasIntersections(road, hasIntersections);

        if (hasIntersections[0] && hasIntersections[1] && !roadsToDraw.contains(road)) {
            roadsToDraw.add(road);
        }
    }

    private void checkIfRoadHasIntersections(Road road, boolean[] hasIntersections) {
        for (Intersection intersection : hex.getIntersections()) {
            if (intersection.equals(road.getIntersections()[0]))    hasIntersections[0] = true;
            if (intersection.equals(road.getIntersections()[1]))    hasIntersections[1] = true;
        }
    }

    private void drawRoadLine(Graphics2D g2) {
        for (Road road : roadsToDraw) {
            drawIndividualRoadLine(g2, road);
        }
    }

    private void drawIndividualRoadLine(Graphics2D g2, Road road) {
        Point2D relativeCenter = getRelativeRoadCenter(g2, road);

        int sideStartingIndex = determineHexSideFromAveragePoint(relativeCenter);

        int sideEndingIndex = (sideStartingIndex + 1) % HEX_NUM_SIDES;

        drawRoadLineUsingHexSideIndices(g2, sideStartingIndex, sideEndingIndex);
    }

    private void drawRoadLineUsingHexSideIndices(Graphics2D g2, int sideStartingIndex,
                                                 int sideEndingIndex) {
        Point2D sideStartingPoint = generateSide(displayCenter, HEX_BORDER_RADIUS,
                sideStartingIndex);
        Point2D sideEndingPoint = generateSide(displayCenter, HEX_BORDER_RADIUS, sideEndingIndex);

        g2.drawLine((int) sideStartingPoint.getX(), (int) sideStartingPoint.getY(),
                (int) sideEndingPoint.getX(), (int) sideEndingPoint.getY());
    }

    private Point2D getRelativeRoadCenter(Graphics2D g2, Road road) {
        Point2D[] intersectionCenters = getRoadIntersectionsCenters(road);
        g2.setColor(road.getOwnerColor());
        g2.setStroke(new BasicStroke(HEX_BOUNDARY_STROKE_WIDTH));

        Point2D hexCenter = hex.getCenter();

        return calculateRoadCenter(intersectionCenters, hexCenter);
    }

    private Point2D[] getRoadIntersectionsCenters(Road road) {
        Point2D[] intersectionCenters = new Point2D[2];
        for (int i = 0; i < 2; i++) {
            intersectionCenters[i] = road.getIntersections()[i].getCenter();
        }
        return intersectionCenters;
    }

    private Point2D calculateRoadCenter(Point2D[] intersectionCenters, Point2D hexCenter) {
        double averageX = (intersectionCenters[0].getX() + intersectionCenters[1].getX()) / 2;
        double averageY = (intersectionCenters[0].getY() + intersectionCenters[1].getY()) / 2;

        double relativeXCenter = averageX - hexCenter.getX();
        double relativeYCenter = averageY - hexCenter.getY();

        return new Point2D.Double(relativeXCenter,relativeYCenter);
    }

    public void drawHexLabel(Graphics2D g2) {
        if (displayUnique) {
            displayHexText(g2, Color.BLUE, Integer.toString(hex.getUniqueIndex()));
        }
        String text = Integer.toString(hex.getValue());
        displayHexText(g2, getHexLabelColor(text), text);
    }

    private Color getHexLabelColor(String value) {
        if (value.equals("6") || value.equals("8")) {
            return Color.RED;
        } else if (value.equals("0")) {
            return new Color(0, 0, 0, 0);
        } else return Color.BLACK;
    }

    private void displayHexText(Graphics2D g2, Color textColor, String text) {
        setFontAndColor(g2, textColor);
        Point2D center = hex.getCenter();
        int textX = (int) (X_CENTER_MULT * center.getX() + X_CENTER_OFFSET) - TEXT_OFFSET_X;
        int textY = (int) (Y_CENTER_MULT * center.getY() + Y_CENTER_OFFSET) + TEXT_OFFSET_Y;
        g2.drawString(text, textX, textY);
    }

    private void setFontAndColor(Graphics2D g2, Color textColor) {
        Font font = new Font("Arial", Font.BOLD, LABEL_FONT_SIZE);
        g2.setFont(font);
        g2.setColor(textColor);
    }

    public void drawHexRobberIfHasRobber(Graphics2D g2) {
        if (hex.getHasRobber()) {
            drawHexRobber(g2);
        }
    }

    private void drawHexRobber(Graphics2D g2) {
        Point2D center = hex.getCenter();
        int centerX = (int) (X_CENTER_MULT * center.getX() + X_CENTER_OFFSET);
        int centerY = (int) (Y_CENTER_MULT * center.getY() + Y_CENTER_OFFSET);
        g2.setColor(Color.BLACK);
        g2.fillOval(centerX - OVAL_OFFSET, centerY - OVAL_OFFSET, OVAL_WIDTH, OVAL_HEIGHT);
    }

    private Color getColorFromResource() {
        ResourceType resource = hex.getResource();

        if (resource == null) {
            return new Color(desertColor[0], desertColor[1], desertColor[2]);
        }

        return convertValidResourceToColor(resource);
    }

    private Color convertValidResourceToColor(ResourceType resource) {
        switch(resource){
            case ORE: return new Color(oreColor[0], oreColor[1], oreColor[2]);
            case LUMBER: return new Color(lumberColor[0], lumberColor[1], lumberColor[2]);
            case WOOL: return new Color(woolColor[0], woolColor[1], woolColor[2]);
            case BRICK: return new Color(brickColor[0], brickColor[1], brickColor[2]);
            case GRAIN: return new Color(grainColor[0], grainColor[1], grainColor[2]);
            default: return new Color(0,0,0);
        }
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }


}
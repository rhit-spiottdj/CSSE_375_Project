package presentation;

import domain.BoardManager;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class BoardDisplay extends JPanel {
    private static final int INTERSECTION_BUTTON_BORDER_THICKNESS = 5;
    private static final int CITY_BUTTON_SIZE = 20;

    public static final int X_CENTER_OFFSET = 360;

    public static final int Y_CENTER_OFFSET = 370;

    public static final int X_CENTER_MULT = 140;
    public static final int Y_CENTER_MULT = 175;
    public static final double VECTOR_MAGNITUDE_SCALER = 0.2;
    public static final Color BROWN = new Color(101, 67, 33);
    public static final BasicStroke BRIDGE_STROKE = new BasicStroke(4);
    private HexagonDisplay[] hexDisplays;

    private IntersectionButtonManager intersectionButtonManager;

    private HexButtonManager hexButtonManager;

    BoardManager boardManager;

    static ResourceBundle messages;

    protected BoardDisplay(BoardManager boardManager, boolean randomize, Locale locale) {
        this.boardManager = boardManager;
        this.setLayout(null);
        boardSetup(boardManager, randomize);
        messages = ResourceBundle.getBundle("messages", new Locale(locale.getLanguage()));
    }

    private void boardSetup(BoardManager boardManager, boolean randomize) {
        Hexagon[] hexagons = boardManager.initializeBoardStructure(randomize);

        initializeButtonManagers();
        setupHexDisplays(hexagons);
        addActionListenersAndComponentsToManager(boardManager);
    }

    private void addActionListenersAndComponentsToManager(BoardManager boardManager) {
        addHexButtonsToManager(boardManager);
        addIntersectionsToManager(boardManager);

        addActionToIntersections(boardManager);
        addActionToHexButtons(boardManager);
    }

    private void initializeButtonManagers(){
        intersectionButtonManager = new IntersectionButtonManager();

        hexButtonManager = new HexButtonManager();
    }

    private void addHexButtonsToManager(BoardManager boardManager) {
        for (Hexagon hexagon : boardManager.getHexagons()) {
            this.add(
                hexButtonManager.createHexButton(hexagon.getCenter(), hexagon.getUniqueIndex()));
        }
    }

    private void addActionToIntersections(BoardManager boardManager) {
        for (JButton intersectionButton : intersectionButtonManager.intersectionButtons) {
            intersectionButton.addActionListener(e -> {
                intersectionAction(boardManager, intersectionButton);
            });
        }
    }

    private void intersectionAction(BoardManager boardManager, JButton intersectionButton) {
        intersectionButtonManager.setSelectedIntersection(
            intersectionButtonManager.intersectionButtons.indexOf(intersectionButton));

        boardManager.setIntersectionSelection(
            intersectionButtonManager.getSelectedIntersection());
    }


    private void addActionToHexButtons(BoardManager boardManager) {
        for (JButton hexButton : hexButtonManager.hexButtons) {
            hexButton.addActionListener(e -> {
                hexAction(boardManager, hexButton);
            });
        }
    }

    private void hexAction(BoardManager boardManager, JButton hexButton) {
        hexButtonManager.setSelectedHex(hexButtonManager.hexButtons.indexOf(hexButton));

        boardManager.setHexSelection(hexButtonManager.getSelectedHex());
    }

    private void addIntersectionsToManager(BoardManager boardManager) {
        for (Intersection intersection : boardManager.getIntersections()) {
            this.add(intersectionButtonManager.createIntersectionButton(intersection.getCenter(),
                intersection.getUniqueIndex()));
        }
    }

    private void setupHexDisplays(Hexagon[] hexagons) {
        hexDisplays = new HexagonDisplay[hexagons.length];
        for (int i = 0; i < hexagons.length; i++) {
            hexDisplays[i] = new HexagonDisplay(hexagons[i], false);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        paintHexes(g2);

        paintBridges(g2);

    }

    private void paintHexes(Graphics2D g2) {
        for (HexagonDisplay hexDisplay : hexDisplays) {
            hexDisplay.drawHexShape(g2);
            hexDisplay.drawHexLabel(g2);
            hexDisplay.drawHexRobberIfHasRobber(g2);
        }
    }

    private void paintBridges(Graphics2D g2) {
        Intersection[] intersections = boardManager.getIntersections();
        ArrayList<Port> ports = boardManager.getPorts();
        for(int i = 0; i < boardManager.getPortLocations().length; i++){
            setGraphicsAndDrawBridgesAtPort(g2, intersections, ports, i);
        }
    }

    private void setGraphicsAndDrawBridgesAtPort(Graphics2D g2, Intersection[] intersections,
        ArrayList<Port> ports, int i) {
        setGraphicToBridgeMode(g2);
        drawBridgesAtPort(g2, intersections, i, ports.get(i));
    }

    private void drawBridgesAtPort(Graphics2D g2, Intersection[] intersections, int i, Port port) {
        Intersection current = intersections[boardManager.getPortLocations()[i].getIndex1()];
        int[] bridgeEndPoint = calculatePortBridgeLocationAndDraw(g2, current.getCenter());
        current = intersections[boardManager.getPortLocations()[i].getIndex2()];
        int[] otherBridgeEndPoint = calculatePortBridgeLocationAndDraw(g2, current.getCenter());
        drawPortLabel(g2, bridgeEndPoint, otherBridgeEndPoint, port);
    }

    private void drawPortLabel(Graphics2D g2, int[] bridgeEndPoint, int[] otherBridgeEndPoint,
        Port port) {
        int[] center = calculateCenter(bridgeEndPoint, otherBridgeEndPoint);
        setGraphicToPortTextMode(g2);
        g2.drawString(portDisplay(port), center[0], center[1]);
    }

    private void setGraphicToPortTextMode(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(BRIDGE_STROKE);
    }

    private String portDisplay(Port port) {
        if(port.getPortTradeRatio() == 3){
            return "3:1";
        }else{
            return "2:1, " + getFormattedResource(port.getResourceType().toString());
        }
    }

    @SuppressWarnings("methodlength")
    private static String getFormattedResource(String resource) {
        switch (resource) {
            case "BRICK": return messages.getString("brick");
            case "LUMBER": return messages.getString("lumber");
            case "GRAIN": return messages.getString("grain");
            case "WOOL": return messages.getString("wool");
            case "ORE": return messages.getString("ore");
            default: return "";
        }
    }

    private int[] calculateCenter(int[] bridgeEndPoint, int[] otherBridgeEndPoint) {
        int[] centerPoint = new int[2];
        centerPoint[0] = (bridgeEndPoint[0] + otherBridgeEndPoint[0]) >>> 1;
        centerPoint[1] = (bridgeEndPoint[1] + otherBridgeEndPoint[1]) >>> 1;
        return centerPoint;
    }

    private void setGraphicToBridgeMode(Graphics2D g2) {
        g2.setColor(BROWN);
        g2.setStroke(BRIDGE_STROKE);
    }

    private int[] calculatePortBridgeLocationAndDraw(Graphics2D g2, Point2D center) {
        double vectorX = center.getX();
        double vectorY = center.getY();
        double magnitude = Math.sqrt(vectorX*vectorX + vectorY*vectorY);
        double vectorScaleFactor = VECTOR_MAGNITUDE_SCALER / magnitude;
        return drawPortBridge(g2, vectorX, vectorY, vectorScaleFactor);
    }

    private int[] drawPortBridge(Graphics2D g2, double vectorX, double vectorY,
        double vectorScaleFactor) {
        int[] displayPoint = convertPointToDisplay(vectorX,vectorY);
        int[] displayPointEnd = convertPointToDisplay(vectorX + vectorX * vectorScaleFactor,
            vectorY + vectorY * vectorScaleFactor);
        g2.drawLine(displayPoint[0], displayPoint[1], displayPointEnd[0], displayPointEnd[1]);
        return displayPointEnd;
    }

    private int convertXToDisplay(double centerX){
        return (int) Math.round(X_CENTER_MULT*centerX + X_CENTER_OFFSET);
    }

    private int convertYToDisplay(double centerY){
        return (int) Math.round(Y_CENTER_MULT*centerY + Y_CENTER_OFFSET);
    }

    private int[] convertPointToDisplay(double centerX, double centerY){
        return new int[]{convertXToDisplay(centerX),convertYToDisplay(centerY)};
    }


    public void redrawButtons(BoardManager boardManager) {
        for (Intersection intersection : boardManager.getIntersections()) {
            redrawButton(boardManager, intersection);
        }
    }

    private void redrawButton(BoardManager boardManager, Intersection intersection) {
        int index = intersection.getUniqueIndex();

        if (boardManager.getIntersectionSettlement(index) != null) {
            redrawButtonWithSettlement(boardManager, index);
        }
    }

    private void redrawButtonWithSettlement(BoardManager boardManager, int index) {
        intersectionButtonManager.intersectionButtons.get(index).setBorder(
            BorderFactory.createLineBorder(
            boardManager.getIntersectionSettlementColor(index),
            INTERSECTION_BUTTON_BORDER_THICKNESS));
    }

    public void upgradeToCityButton(int index) {
        intersectionButtonManager.intersectionButtons.get(index).setBounds(
            intersectionButtonManager.intersectionButtons.get(index).getX() - 2,
            intersectionButtonManager.intersectionButtons.get(index).getY() - 2, CITY_BUTTON_SIZE,
            CITY_BUTTON_SIZE);
    }

    public void toggleHexButtons(boolean toggle) {
        hexButtonManager.enableHexButtons(toggle);
    }

    public void toggleIntersectionButtons(boolean toggle) {
        intersectionButtonManager.enableIntersectionButtons(toggle);
    }

    @Override
    public Dimension getPreferredSize() {
        final int boardWidth = 850;
        final int boardHeight = 780;
        return new Dimension(boardWidth,boardHeight);
    }
}



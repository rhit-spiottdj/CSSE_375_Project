//BUILD GUI
package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class BuildManagerGUI {

    public static final int FRAME_WIDTH = 200;
    public static final int FRAME_HEIGHT = 300;
    private static final int MILLS_TO_WAIT = 10;
    private GameDisplay gameDisplay;
    private JFrame frame;
    private JLabel label;

    private JPanel buildFrame;

    private JButton buildSettlement;

    private JButton buildCity;

    private JButton buildRoad;

    private ResourceBundle messages;

    protected BuildManagerGUI(GameDisplay gameDisplay, Locale locale) {

        initializeFields(gameDisplay, locale);

        initializeFrameContent();

        displayFrame();
    }

    private void initializeFrameContent() {
        frame.getContentPane().setLayout(new GridLayout());
        initializeLabels();
        initializeButtons();
        initializeBuildOptionGUI();
        frame.add(buildFrame);
    }

    private void initializeFields(GameDisplay gameDisplay, Locale locale) {
        initializeSetFields(gameDisplay, locale);
        initializeMainFields();
    }

    private void initializeSetFields(GameDisplay gameDisplay, Locale locale) {
        this.gameDisplay = gameDisplay;
        this.messages = ResourceBundle.getBundle("messages", new Locale(locale.getLanguage()));
    }

    private void initializeButtons() {
        createBuildSettlementButton();
        createBuildRoadButton();
        createBuildCityButton();
    }

    private void initializeBuildOptionGUI() {
        buildFrame = new JPanel();
        buildFrame.setLayout(new BoxLayout(buildFrame, BoxLayout.Y_AXIS));

        addComponentsToFrame();
    }

    private void addComponentsToFrame() {
        JComponent[] componentsToAdd =
            new JComponent[]{label, buildSettlement, buildRoad, buildCity};

        for (Component component : componentsToAdd) {
            buildFrame.add(component);
        }
    }

    private void initializeMainFields() {
        frame = new JFrame();
    }

    private void initializeLabels() {
        label = new JLabel(messages.getString("buildMenuLabel"));
    }

    private void createBuildSettlementButton() {
        buildSettlement = new JButton(messages.getString("buildSettlementLabel"));
        buildSettlement.addActionListener(this::actionPerformed);
    }

    private void createBuildRoadButton() {
        buildRoad = new JButton(messages.getString("buildRoadLabel"));
        buildRoad.addActionListener(this::actionPerformed);
    }

    private void createBuildCityButton() {
        buildCity = new JButton(messages.getString("buildCityLabel"));
        buildCity.addActionListener(this::actionPerformed);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buildSettlement)   buildSettlementActionPreformed();
        else if (e.getSource() == buildCity)    buildCityActionPerformed();
        else if (e.getSource() == buildRoad)    buildRoadActionPerformed();
    }

    private void buildRoadActionPerformed() {
        gameDisplay.actionTaken = true;
        gameDisplay.buildRoad = true;
        frame.dispose();
    }

    private void buildCityActionPerformed() {
        gameDisplay.actionTaken = true;
        gameDisplay.buildCity = true;
        frame.dispose();
    }

    private void buildSettlementActionPreformed() {
        gameDisplay.actionTaken = true;
        gameDisplay.buildSettlement = true;
        frame.dispose();
    }

    private void displayFrame() {
        frame.pack();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
    }

    public void disposeFrame(){
        frame.dispose();
    }

    public boolean isShowing(){
        return frame.isShowing();
    }
}

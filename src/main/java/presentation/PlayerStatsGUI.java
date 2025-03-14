package presentation;

import domain.DevelopmentCards;
import domain.Player;
import domain.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;


//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission

public class PlayerStatsGUI {

    public static final int LEFT_PANEL_ROWS = 5;
    public static final int RIGHT_PANEL_ROWS = 5;
    public static final int PLAYER_PANEL_WIDTH = 200;
    public static final int PLAYER_PANEL_HEIGHT = 100;
    public static final int RESOURCE_PANEL_WIDTH = 200;
    public static final int RESOUCE_PANEL_HEIGHT = 500;
    Player player;

    JPanel playerNamePanel;
    public JPanel resourceDisplayPanel;

    public JPanel leftPanel;
    public JPanel rightPanel;

    private static ResourceBundle messages;

    protected PlayerStatsGUI(Player player, Locale locale) {
        initializeSetFields(player, locale);
        initializePanels();
        setupPanels();
        updatePanels();
        setPanelSizes();
    }

    private void initializeSetFields(Player player, Locale locale) {
        this.player = player;
        messages = ResourceBundle.getBundle("messages", new Locale(locale.getLanguage()));
    }

    private void setPanelSizes() {
        playerNamePanel.setMaximumSize(new Dimension(PLAYER_PANEL_WIDTH, PLAYER_PANEL_HEIGHT));
        resourceDisplayPanel.setMaximumSize(
            new Dimension(RESOURCE_PANEL_WIDTH, RESOUCE_PANEL_HEIGHT));
    }

    private void setupPanels() {
        resourceDisplayPanel.add(leftPanel);
        resourceDisplayPanel.add(rightPanel);
    }

    private void updatePanels() {
        updatePlayerPanelInformation();
        updateLeftPanelInformation();
        updateRightPanelInformation();
    }

    private void initializePanels() {
        playerNamePanel = new JPanel(new GridLayout(1, 2));
        resourceDisplayPanel = new JPanel(new GridLayout(1, 2));
        leftPanel = new JPanel(new GridLayout(LEFT_PANEL_ROWS, 1));
        rightPanel = new JPanel(new GridLayout(RIGHT_PANEL_ROWS, 1));
    }

    protected void updateResourcesView() {
        updateLeftPanel();
        updatePlayerNamePanel();
        updateRightPanel();
    }

    private void updateRightPanel() {
        rightPanel.removeAll();
        updateRightPanelInformation();
    }

    private void updateRightPanelInformation() {
        rightPanel.add(new JLabel(messages.getString("roadsLeftLabel") + player.getNumRoads()));
        rightPanel.add(new JLabel(messages.getString("settlementsLeftLabel") +
            player.getNumSettlements()));
        rightPanel.add(new JLabel(messages.getString("citiesLeftLabel") + player.getNumCities()));
        rightPanel.add(new JLabel(messages.getString("knightsLabel") + getNumPlayedKnights()));
    }

    private int getNumPlayedKnights(){
        Collection<DevelopmentCards> cards = player.getUnplayableDevelopmentCards();
        int numKnights = 0;
        while(cards.remove(DevelopmentCards.KNIGHT)) numKnights++;
        return numKnights;
    }

    private void updatePlayerNamePanel() {
        playerNamePanel.removeAll();
        updatePlayerPanelInformation();
    }

    private void updatePlayerPanelInformation() {
        playerNamePanel.add(new JLabel(player.getPlayerName()));
        playerNamePanel.add(new JLabel(messages.getString("victoryPointsLabel") +
            player.getVictoryPoints()));

    }

    private void updateLeftPanel() {
        leftPanel.removeAll();
        updateLeftPanelInformation();
    }

    private void updateLeftPanelInformation() {
        String[] labels = {"brickLabel","grainLabel","lumberLabel","woolLabel","oreLabel"};
        ResourceType[] resources = getResourceOrder();
        for(int i = 0; i < labels.length; i++){
            leftPanel.add(new JLabel(buildLeftPanelString(labels[i],resources[i])));
        }
    }

    private ResourceType[] getResourceOrder() {
        return new ResourceType[]{ResourceType.BRICK, ResourceType.GRAIN,
            ResourceType.LUMBER, ResourceType.WOOL, ResourceType.ORE};
    }

    private String buildLeftPanelString(String label, ResourceType resource){
        return messages.getString(label) + player.getNumOwnedResource(resource);
    }
}

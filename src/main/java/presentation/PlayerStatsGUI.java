package presentation;

import domain.Player;
import domain.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.Timer;

public class PlayerStatsGUI {

    public static final int LEFT_PANEL_ROWS = 5;
    public static final int RIGHT_PANEL_ROWS = 5;
    public static final int PLAYER_PANEL_WIDTH = 600;
    public static final int PLAYER_PANEL_HEIGHT = 100;
    public static final int RESOURCE_PANEL_WIDTH = 400;
    public static final int RESOUCE_PANEL_HEIGHT = 500;
    Player player;

    JPanel playerNamePanel;
    public JPanel resourceDisplayPanel;

    public JPanel leftPanel;
    public JPanel rightPanel;

    private static ResourceBundle messages;

    private Timer flashTimer;
    private final java.util.List<JLabel> resourceLabels = new ArrayList<>();
    private java.util.List<ResourceType> recentlyGainedResources = new ArrayList<>();
    private java.util.Map<ResourceType, Integer> previousResourceCounts = new HashMap<>(); // Store previous counts here


    protected PlayerStatsGUI(Player player, Locale locale) {
        initializeSetFields(player, locale);
        initializePanels();
        storeInitialResourceCounts(); // Store initial counts
        setupPanels();
        updatePanels();
        setPanelSizes();
    }

    private void storeInitialResourceCounts() {
        ResourceType[] resourceOrder = getResourceOrder();
        for (ResourceType resource : resourceOrder) {
            previousResourceCounts.put(resource, player.getNumOwnedResource(resource));
        }
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
        detectAndStoreGainedResources(); // Detect gains before redrawing
        updateLeftPanel(); // Redraw labels
        updatePlayerNamePanel();
        updateRightPanel();
        flashGainedResources(); // Trigger flash after redraw
    }


    private void detectAndStoreGainedResources() {
        recentlyGainedResources.clear();
        ResourceType[] resourceOrder = getResourceOrder();
        for (ResourceType resource : resourceOrder) {
            int currentCount = player.getNumOwnedResource(resource);
            if (currentCount > previousResourceCounts.getOrDefault(resource, 0)) {
                recentlyGainedResources.add(resource);
            }
            previousResourceCounts.put(resource, currentCount); // Update previous count for next comparison
        }
    }


    private void updateRightPanel() {
        rightPanel.removeAll();
        updateRightPanelInformation();
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void updateRightPanelInformation() {
        rightPanel.add(new JLabel(messages.getString("roadsLeftLabel") + player.getNumRoads()));
        rightPanel.add(new JLabel(messages.getString("settlementsLeftLabel") +
                player.getNumSettlements()));
        rightPanel.add(new JLabel(messages.getString("citiesLeftLabel") + player.getNumCities()));
        rightPanel.add(new JLabel(messages.getString("knightsLabel") + player.getPlayedKnightCount()));
    }


    private void updatePlayerNamePanel() {
        playerNamePanel.removeAll();
        updatePlayerPanelInformation();
        playerNamePanel.revalidate();
        playerNamePanel.repaint();
    }

    private void updatePlayerPanelInformation() {
        playerNamePanel.add(new JLabel(player.getPlayerName()));
        playerNamePanel.add(new JLabel(messages.getString("victoryPointsLabel") +
                player.getVictoryPoints()));

    }

    private void updateLeftPanel() {
        leftPanel.removeAll();
        resourceLabels.clear();
        updateLeftPanelInformation(); // Recreate labels with updated counts
        leftPanel.revalidate();
        leftPanel.repaint();
    }

    private void updateLeftPanelInformation() {
        String[] labels = {"brickLabel","grainLabel","lumberLabel","woolLabel","oreLabel"};
        ResourceType[] resources = getResourceOrder();
        for(int i = 0; i < labels.length; i++){
            JLabel resourceLabel = new JLabel(buildLeftPanelString(labels[i],resources[i]));
            resourceLabel.setOpaque(true);
            leftPanel.add(resourceLabel);
            resourceLabels.add(resourceLabel);
        }
    }

    private ResourceType[] getResourceOrder() {
        return new ResourceType[]{ResourceType.BRICK, ResourceType.GRAIN,
                ResourceType.LUMBER, ResourceType.WOOL, ResourceType.ORE};
    }

    private String buildLeftPanelString(String label, ResourceType resource){
        return messages.getString(label) + player.getNumOwnedResource(resource);
    }

    // Call this method externally when a resource gain event happens for this player
    public void triggerResourceGainIndication() {
        detectAndStoreGainedResources();
        flashGainedResources();
    }


    private void flashGainedResources() {
        if (recentlyGainedResources.isEmpty() || resourceLabels.isEmpty()) {
            return;
        }

        ResourceType[] resourceOrder = getResourceOrder();
        final Color originalColor = leftPanel.getBackground();
        final Color flashColor = Color.YELLOW;
        final int flashDuration = 2000;

        if (flashTimer != null && flashTimer.isRunning()) {
            flashTimer.stop();
            for (int i = 0; i < resourceLabels.size(); i++) {
                if (i < resourceOrder.length && resourceLabels.get(i) != null) {
                    resourceLabels.get(i).setBackground(originalColor);
                }
            }
        }

        for (int i = 0; i < resourceOrder.length; i++) {
            if (i < resourceLabels.size() && resourceLabels.get(i) != null) {
                if (recentlyGainedResources.contains(resourceOrder[i])) {
                    resourceLabels.get(i).setBackground(flashColor);
                } else {
                    resourceLabels.get(i).setBackground(originalColor);
                }
            }
        }
        resourceDisplayPanel.revalidate();
        resourceDisplayPanel.repaint();


        flashTimer = new Timer(flashDuration, e -> {
            for (int i = 0; i < resourceLabels.size(); i++) {
                if (i < resourceOrder.length && resourceLabels.get(i) != null) {
                    resourceLabels.get(i).setBackground(originalColor);
                }
            }
            recentlyGainedResources.clear(); // Clear *after* flash ends
            resourceDisplayPanel.revalidate();
            resourceDisplayPanel.repaint();
        });
        flashTimer.setRepeats(false);
        flashTimer.start();
    }
}
package presentation;

import domain.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.HashMap; // Add this import
import java.util.Map; // Add this import


public class PlayersStatsGUI {

    public JPanel frame;
    public PlayerStatsGUI playerStatsGUIs[]; // Holds individual GUIs
    public Player[] players;
    private Map<Player, PlayerStatsGUI> playerGuiMap; // Map players to their GUIs

    private Locale locale;

    public PlayersStatsGUI(Player[] players, Locale locale) {
        initializeSetFields(players, locale);
        playerGuiMap = new HashMap<>(); // Initialize map
        initializeFrame(players);
        initializePlayerGUIs(players);

    }

    private void initializeSetFields(Player[] players, Locale locale) {
        this.players = players;
        this.locale = locale;
        this.playerStatsGUIs = new PlayerStatsGUI[players.length];
    }

    private void initializeFrame(Player[] players) {
        frame = new JPanel();
        // Adjust grid layout rows if needed, based on number of players
        frame.setLayout(new GridLayout(players.length, 1)); // One row per player panel pair

    }

    private void initializePlayerGUIs(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            initializePlayerGUI(players, i);
        }
    }

    private void initializePlayerGUI(Player[] players, int i) {
        PlayerStatsGUI singlePlayerGUI = new PlayerStatsGUI(players[i], locale);
        playerStatsGUIs[i] = singlePlayerGUI;
        playerGuiMap.put(players[i], singlePlayerGUI); // Map player to their GUI

        // Maybe add both panels (name+resources) to a single sub-panel per player
        JPanel playerPanelContainer = new JPanel();
        playerPanelContainer.setLayout(new BoxLayout(playerPanelContainer, BoxLayout.Y_AXIS)); // Stack vertically
        playerPanelContainer.add(singlePlayerGUI.playerNamePanel);
        playerPanelContainer.add(singlePlayerGUI.resourceDisplayPanel);
        frame.add(playerPanelContainer); // Add the combined panel
    }


    public void updatePlayersStats() {
        for (int i = 0; i < players.length; i++) {
            if (playerStatsGUIs[i] != null) {
                playerStatsGUIs[i].updateResourcesView();
            }
        }
        frame.revalidate();
        frame.repaint();
    }

    public void triggerResourceGainIndicator(Player player) {
        PlayerStatsGUI gui = playerGuiMap.get(player);
        if (gui != null) {
            gui.triggerResourceGainIndication();
        }
    }
}
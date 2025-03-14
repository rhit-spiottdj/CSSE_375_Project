package presentation;

import domain.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission

public class PlayersStatsGUI {

    public static final int FRAME_WIDTH = 300;
    public static final int FRAME_HEIGHT = 500;
    public static final int FRAME_X_OFFSET = 1100;
    public JPanel frame;
    public PlayerStatsGUI playerStatsGUIs[];
    public Player[] players;

    private Locale locale;

    public PlayersStatsGUI(Player[] players, Locale locale) {
        initializeSetFields(players, locale);

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
        frame.setLayout(new GridLayout(players.length * 2, 1));

    }

    private void initializePlayerGUIs(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            initializePlayerGUI(players, i);
        }
    }

    private void initializePlayerGUI(Player[] players, int i) {
        playerStatsGUIs[i] = new PlayerStatsGUI(players[i], locale);
        frame.add(playerStatsGUIs[i].playerNamePanel);
        frame.add(playerStatsGUIs[i].resourceDisplayPanel);
    }


    public void updatePlayersStats() {
        for (int i = 0; i < players.length; i++) {
            playerStatsGUIs[i].updateResourcesView();
        }
        frame.revalidate();
        frame.repaint();
    }
}

package presentation;

import domain.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.HashMap; // Add this import
import java.util.Map; // Add this import


public class PlayersStatsGUI {

    public JPanel frame;
    public JFrame playerInfoFrame;
    public JFrame singlePlayerInfoFrame;
    public JLabel playerInfoLabel;
    public PlayerStatsGUI playerStatsGUIs[]; // Holds individual GUIs
    public Player[] players;
    private Map<Player, PlayerStatsGUI> playerGuiMap; // Map players to their GUIs
    public JButton showPlayerInfoBtn;

    private Locale locale;

    public PlayersStatsGUI(Player[] players, Locale locale) {
        initializeSetFields(players, locale);
        playerGuiMap = new HashMap<>(); // Initialize map
        initializeFrame(players);
//        initializePlayerGUIs(players);

    }

    private void initializeSetFields(Player[] players, Locale locale) {
        this.players = players;
        this.locale = locale;
        this.playerStatsGUIs = new PlayerStatsGUI[players.length];
    }

    private void initializeFrame(Player[] players) {
        frame = new JPanel();
        // Adjust grid layout rows if needed, based on number of players
        frame.setLayout(new GridLayout(1, 1));
        showPlayerInfoBtn = new JButton();
        showPlayerInfoBtn.setText("Show Player Information");
        showPlayerInfoBtn.addActionListener(e -> showPlayersInfo(e, players));
        frame.add(showPlayerInfoBtn);
    }

    private void showPlayersInfo(ActionEvent e, Player[] players) {
        playerInfoFrame = new JFrame();
        playerInfoFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        playerInfoFrame.setSize(new Dimension(1500, 100));
        playerInfoFrame.setLayout(new GridLayout(1, players.length, 5, 5));
        playerInfoLabel = new JLabel("Please pick which player's information to view");
        playerInfoFrame.add(playerInfoLabel);
//        initializePlayerGUIs(players);
        for(Player p : players) {
            JButton btn = new JButton(p.getPlayerName());
            PlayerStatsGUI singlePlayerGUI = new PlayerStatsGUI(p, locale);
            btn.addActionListener(f -> showPlayerInfoDialog(playerInfoFrame, p));
            playerInfoFrame.add(btn);
        }
        playerInfoFrame.setLocationRelativeTo(null);
        playerInfoFrame.setVisible(true);
    }

    private void showPlayerInfoDialog(JFrame playerInfoFrame, Player p) {
        singlePlayerInfoFrame = new JFrame();
        singlePlayerInfoFrame.setSize(new Dimension(800, 800));
        singlePlayerInfoFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initializePlayerGUI(p);
        singlePlayerInfoFrame.setVisible(true);
    }

//    private void initializePlayerGUIs(Player[] players) {
//        for (int i = 0; i < players.length; i++) {
//            initializePlayerGUI(players, i);
//        }
//    }

    private void initializePlayerGUI(Player p) {
        PlayerStatsGUI singlePlayerGUI = new PlayerStatsGUI(p, locale);
//        playerStatsGUIs[i] = singlePlayerGUI;
//        playerGuiMap.put(players[i], singlePlayerGUI); // Map player to their GUI

        // Maybe add both panels (name+resources) to a single sub-panel per player
        JPanel playerPanelContainer = new JPanel();
        playerPanelContainer.setLayout(new BoxLayout(playerPanelContainer, BoxLayout.Y_AXIS)); // Stack vertically
        playerPanelContainer.add(singlePlayerGUI.playerNamePanel);
        playerPanelContainer.add(singlePlayerGUI.resourceDisplayPanel);
        singlePlayerInfoFrame.add(playerPanelContainer); // Add the combined panel
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
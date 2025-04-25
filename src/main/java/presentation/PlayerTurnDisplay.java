package presentation;

import domain.GameManager;
import domain.Player;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class PlayerTurnDisplay {

    private static final int SMALL_INSET_PADDING = 10;
    private static final int LARGE_INSET_PADDING = 40;
    private static final int TOP_ROW_BUTTON = 1;
    private static final int BOTTOM_ROW_BUTTON_INDEX = 2;
    private static final int PLAYER_ACTION_BUTTON_WIDTH = 3;
    private static final int RIGHT_BUTTON_COLUMN = 4;

    private static final int CENTER_BUTTON_COLUMN_INDEX = 4;

    private static final int CENTER_BUTTON_WIDTH = 2;
    private static final int LEFT_BUTTON_COLUMN = 0;
    private static final int PLAYER_SMALL_INSET_PADDING = 20;
    private static final int PLAYER_LARGE_INSET_PADDING = 60;

    private static final int PLAYER_LABEL_WIDTH = 7;
    private static final int PLAYER_LABEL_HEIGHT = 1;
    public static final int END_TURN_BUTTON_WIDTH = 7;
    private ResourceBundle messages;
    protected String playerName;
    protected boolean turnEnded;
    public JPanel frame;
    private JPanel panel;
    private JButton tradeButton;
    private JButton endTurnButton;
    private JButton buildButton;

    public JLabel playerNameLabel;

    private GameManager gameManager;

    private Player[] players;

    private GameDisplay gameDisplay;

    private DicePanel dicePanel;

    private Locale locale;
    private BuildManagerGUI buildGUI;
    private TradeManagerGUI tradeGUI;
    private boolean enableFlag = true;


    public PlayerTurnDisplay(GameManager gameManager, GameDisplay gameDisplay, Player[] players,
                             Locale locale) {
        initializeConstructorFields(gameManager, gameDisplay, players, locale);
        initializeRemainingFields(gameManager, locale);

        initialize();
    }

    private void initializeRemainingFields(GameManager gameManager, Locale locale) {
        this.playerName = gameManager.getPlayers()[0].getPlayerName();
        turnEnded = false;
        messages = ResourceBundle.getBundle("messages", new Locale(locale.getLanguage()));
    }

    private void initializeConstructorFields(GameManager gameManager,
                                             GameDisplay gameDisplay, Player[] players, Locale locale) {
        this.gameManager = gameManager;
        this.gameDisplay = gameDisplay;
        this.players = players;
        this.locale = locale;
    }


    public void initialize() {
        initializeFields();
        initializeSwingUI();
        attachActionListeners();


        frame.add(panel);
    }

    private void initializeFields() {
        frame = new JPanel();
        setFrameLayout();
        panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
    }

    private void setFrameLayout() {
        final int numRows = 3;
        final int numCols = 1;
        GridLayout wholeLayout = new GridLayout(numRows,numCols);
        frame.setLayout(wholeLayout);
    }

    private void attachActionListeners() {
        tradeButton.addActionListener(e -> tradeButtonAction());
        buildButton.addActionListener(e -> buildButtonAction());
        endTurnButton.addActionListener(e -> endTurnButtonAction());
    }

    public boolean isTurnOver() {
        return turnEnded;
    }

    public void updateUIForNewPlayer(Player player) {
        this.playerName = player.getPlayerName();
        turnEnded = false;
        resetContent();

        playerNameLabel.setText(getPlayerNameTurn(playerName));

    }

    public String getPlayerNameTurn(String playerName) {
        String message = messages.getString("playerTurnTitle");
        return MessageFormat.format(message, playerName);
    }

    private void resetButtons() {
        tradeButton.setEnabled(false);
        endTurnButton.setEnabled(false);
        buildButton.setEnabled(false);
    }

    public void resetContent() {
        resetButtons();
        resetDicePanel();
    }


    private void initializeSwingUI() {
        GridBagConstraints constraints = new GridBagConstraints();

        addPlayerLabel(constraints);

        addPlayerButtons(constraints);

        addDicePanel();

        resetButtons();

    }

    private void addPlayerLabel(GridBagConstraints constraints) {
        setGridBagConstraints(constraints,
                new GridBagConstraintHelper(0, 0, PLAYER_LABEL_WIDTH, PLAYER_LABEL_HEIGHT));
        setPlayerNameLabelConstraints(constraints);
        initializePlayerNameLabel();
        panel.add(playerNameLabel, constraints);
    }

    private void initializePlayerNameLabel() {
        playerNameLabel = new JLabel(getPlayerNameTurn(playerName));
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    private void addDicePanel() {
        dicePanel = new DicePanel(players, gameManager, locale);
        frame.add(dicePanel);

        JButton[] buttonsToEnable = new JButton[]{tradeButton,buildButton,endTurnButton};
        dicePanel.addButtonsToEnable(buttonsToEnable);

    }

    public void resetDicePanel() {
        dicePanel.resetButtons();
    }

    private void setPlayerNameLabelConstraints(GridBagConstraints constraints) {
        constraints.insets = new Insets(PLAYER_SMALL_INSET_PADDING, PLAYER_LARGE_INSET_PADDING,
                PLAYER_SMALL_INSET_PADDING, PLAYER_LARGE_INSET_PADDING);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
    }


    private void addPlayerButtons(GridBagConstraints constraints) {
        addTradeButton(constraints);

        addBuildButton(constraints);

        addEndTurnButton(constraints);
    }

    private void addEndTurnButton(GridBagConstraints constraints) {
        setGridBagConstraints(constraints,
                new GridBagConstraintHelper(0, BOTTOM_ROW_BUTTON_INDEX, END_TURN_BUTTON_WIDTH, 1));
        endTurnButton = new JButton(messages.getString("endTurnLabel"));
        setEndTurnButtonInsetsConstraints(constraints);
        panel.add(endTurnButton, constraints);
    }

    private void setEndTurnButtonInsetsConstraints(GridBagConstraints constraints) {
        constraints.insets =
                new Insets(SMALL_INSET_PADDING, SMALL_INSET_PADDING, SMALL_INSET_PADDING,
                        LARGE_INSET_PADDING);
    }


    private void addBuildButton(GridBagConstraints constraints) {
        setGridBagConstraints(constraints, new GridBagConstraintHelper(
                RIGHT_BUTTON_COLUMN, TOP_ROW_BUTTON, PLAYER_ACTION_BUTTON_WIDTH, 1));
        buildButton = new JButton(messages.getString("buildLabel"));
        setEndTurnButtonInsetsConstraints(constraints);
        panel.add(buildButton, constraints);
    }

    private void addTradeButton(GridBagConstraints constraints) {
        setGridBagConstraints(constraints, new GridBagConstraintHelper(
                LEFT_BUTTON_COLUMN, TOP_ROW_BUTTON, PLAYER_ACTION_BUTTON_WIDTH, 1));
        tradeButton = new JButton(messages.getString("tradeLabel"));
        setTradeButtonAdditionalConstraints(constraints);
        panel.add(tradeButton, constraints);
    }

    private void setTradeButtonAdditionalConstraints(GridBagConstraints constraints) {
        constraints.weightx = 0.5;
        constraints.insets =
                new Insets(SMALL_INSET_PADDING, LARGE_INSET_PADDING, SMALL_INSET_PADDING,
                        SMALL_INSET_PADDING);
        constraints.fill = GridBagConstraints.HORIZONTAL;
    }

    private void endTurnButtonAction() {
        gameDisplay.actionTaken = true;
        turnEnded = true;
        ifTradeOrBuildOpenKillThem();
    }

    private void buildButtonAction() {
        ifBuildOpenKillIt();
        buildGUI = new BuildManagerGUI(gameDisplay, locale);
    }


    private void tradeButtonAction() {
        ifTradeOpenKillIt();
        tradeGUI = new TradeManagerGUI(players, gameManager, locale);
    }

    private void ifTradeOrBuildOpenKillThem(){
        ifTradeOpenKillIt();
        ifBuildOpenKillIt();
    }

    private void ifBuildOpenKillIt() {
        if(buildGUI != null && buildGUI.isShowing()) buildGUI.disposeFrame();
    }

    private void ifTradeOpenKillIt() {
        if(tradeGUI != null && tradeGUI.isActive()) tradeGUI.disposeFrame();
    }

    public void enableButtons(boolean enable) {
        tradeButton.setEnabled(enable);
        buildButton.setEnabled(enable);
        endTurnButton.setEnabled(enable);
        enableFlag = enable;
    }

    public boolean isEnableFlag(){
        return enableFlag;
    }



    private void setGridBagConstraints(GridBagConstraints constraints,
                                       GridBagConstraintHelper helper) {
        constraints.gridheight = helper.height;
        constraints.gridwidth = helper.width;
        constraints.gridx = helper.offsetX;
        constraints.gridy = helper.offsetY;
    }

    private static class GridBagConstraintHelper {

        int offsetX;
        int offsetY;
        int width;
        int height;

        public GridBagConstraintHelper(int x, int y, int width, int height) {
            this.offsetX = x;
            this.offsetY = y;
            this.width = width;
            this.height = height;
        }
    }
}
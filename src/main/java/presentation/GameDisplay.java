package presentation;

import domain.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

public class GameDisplay implements ActionListener {


    private static final String RESOURCE_BUNDLE = "messages";
    private static final int MILLS_TO_WAIT = 10;
    public static final int TIMER_INTERVAL = 20000;
    public static final int TIMER_START_AFTER = 100;
    private static final int ROBBER_ROLL = 7;
    static final int[] QUICK_SETUP_ROADS = {0, 6, 29, 31, 10, 28, 5, 19, 42, 51, 48, 35, 38, 49, 46, 52, 1, 7, 22, 23, 13, 32, 15, 17};
    static final int[] QUICK_SETUP_SETTLEMENTS = {0, 29, 10, 5, 51, 48, 38, 46, 1, 22, 13, 17};
    private static ResourceBundle messages;
    private final Locale[] locales = {new Locale("en"), new Locale("es")};
    protected CardGUI cardDisplay;
    public JFrame boardFrame;
    public GameManager gameManager;
    public ColorPickerDisplay colorPickerDisplay;
    protected BoardManager boardManager;
    protected BoardDisplay boardDisplay;
    private BonusManager bonusManager;

    private DiceManager diceManager;

    private TradeManagerGUI discardGUI;
    protected PlayersStatsGUI playersStats;
    BarbarianRaidDisplay barbarianRaidDisplay;
    Locale gameLocale = locales[0];

    private String[] quickPlayerNames = new String[]{"Player1", "Player2", "Player3", "Player4", "Player5", "Player6"};
    private Color[] quickPlayerColors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.BLACK, Color.PINK};


    protected Player[] players;

    protected Road[] roads;

    private ArrayList<Integer> playerRolls = new ArrayList<>();

    private int playerRollIndex;

    private JFrame rollForPlayersFrame;

    private DicePanel dicePanel;

    protected int numPlayers;

    protected Player inTurn;

    private Timer timer;

    static PlayerTurnDisplay turnDisplay;

    boolean actionTaken = false;
    boolean buildSettlement = false;
    boolean buildRoad = false;

    boolean buildCity = false;
    private boolean playerRollsFinished = false;
    private ArrayList<ResourceType> quickPlayerResources = new ArrayList<>(
            Arrays.asList(ResourceType.BRICK, ResourceType.LUMBER, ResourceType.GRAIN,
                    ResourceType.WOOL, ResourceType.ORE, ResourceType.BRICK, ResourceType.LUMBER,
                    ResourceType.GRAIN, ResourceType.ORE, ResourceType.ORE, ResourceType.ORE));
    private JButton confirmButton;

    public GameDisplay(boolean quickSetup) {
        setup(quickSetup);
        setupForFirstTurn();
        boardFrame.pack();
        cardDisplay.frame.setVisible(false);
        setupTimer();
    }

    private void initAlmanacDisplay() {
        JPanel almanacPanel = new JPanel(new GridLayout(4, 1));
        almanacPanel.setBorder(BorderFactory.createTitledBorder(
                messages.getString("almanacTitle") // e.g. "Build Costs"
        ));
        // Road
        almanacPanel.add(new JLabel(
                messages.getString("roadLabel") + ": " + buildCostString(
                        ResourceType.BRICK, ResourceType.LUMBER
                )));
        // Settlement
        almanacPanel.add(new JLabel(
                messages.getString("settlementLabel") + ": " + buildCostString(
                        ResourceType.BRICK, ResourceType.LUMBER,
                        ResourceType.WOOL, ResourceType.GRAIN
                )));
        // City
        almanacPanel.add(new JLabel(
                messages.getString("cityLabel") + ": " + buildCostString(
                        ResourceType.GRAIN, ResourceType.GRAIN,
                        ResourceType.ORE, ResourceType.ORE, ResourceType.ORE
                )));
        // Development Card
        almanacPanel.add(new JLabel(
                messages.getString("devCardLabel") + ": " + buildCostString(
                        ResourceType.WOOL, ResourceType.GRAIN, ResourceType.ORE
                )));
        // Place at bottom of game display
        boardFrame.getContentPane().add(almanacPanel, BorderLayout.SOUTH);
    }

    /**
     * Helper to build a comma-separated cost string from resource types.
     */
    private String buildCostString(ResourceType... resources) {
        Map<ResourceType, Integer> count = new EnumMap<>(ResourceType.class);
        for (ResourceType r : resources) {
            count.merge(r, 1, Integer::sum);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<ResourceType, Integer> e : count.entrySet()) {
            sb.append(e.getValue())
                    .append(" ")
                    .append(messages.getString(e.getKey().toString().toLowerCase()))
                    .append(", ");
        }
        // remove trailing comma+space
        if (sb.length() > 2) sb.setLength(sb.length()-2);
        return sb.toString();
    }

    private void setupForFirstTurn() {
        gameManager.setInTurnPlayer(0);
        initAndDisplaySecondaryDisplays();
        turnDisplay.updateUIForNewPlayer(players[0]);
    }

    private void setup(boolean quickSetup) {
        doQuickOrNormalSetup(quickSetup);

        initBoardDisplay();

        initBoardFrame();

        addBoardDisplayToFrame();
        handlePlayerSetup(quickSetup);
    }

    private void doQuickOrNormalSetup(boolean quickSetup) {
        if (quickSetup) {
            quickSetup();
        } else {
            normalSetup();
        }
    }

    private void initAndDisplaySecondaryDisplays() {
        initializeSecondaryDisplays();

        addSecondaryDisplaysToFrame();
    }

//    private void initBoard() {
//        initBoardDisplay();
//
//        initBoardFrame();
//
//        addBoardDisplayToFrame();
//    }

    private void addSecondaryDisplaysToFrame() {
        JPanel verticalPanel = new JPanel(new GridLayout(2,1));
        verticalPanel.add(turnDisplay.frame);
        verticalPanel.add(cardDisplay.frame);
        boardFrame.add(verticalPanel);
        boardFrame.add(barbarianRaidDisplay.panel);
        boardFrame.add(playersStats.frame);
    }

    private void initializeSecondaryDisplays() {
        playersStats = new PlayersStatsGUI(gameManager.getPlayers(), gameLocale);
        turnDisplay = new PlayerTurnDisplay(gameManager, this, players, gameLocale);
        cardDisplay = new CardGUI(players, gameManager, this, gameLocale);
        barbarianRaidDisplay = new BarbarianRaidDisplay(gameManager);
        initAlmanacDisplay();
    }

    private void handlePlayerSetup(boolean isQuickSetup) {
        if(isQuickSetup)    placeQuickSetupStructures();
        else{
            boardFrame.pack();
            initialBoardSetup(gameManager.getPlayers());
        }
    }

    private void placeQuickSetupStructures() {    	
    	placeInitialSettlements(0);
    	placeInitialSettlements(6);
    	placeInitialRoads(0);
    	placeInitialRoads(12);
        repaintButtons();
        repaintBoardHexes();
    }
    
    private void placeInitialRoads(int index) {
    	int player = 0;
    	while (player < numPlayers) {
    		int index2 = index + 1;
    		gameManager.placeRoad(QUICK_SETUP_ROADS[index], QUICK_SETUP_ROADS[index2], 
    				gameManager.getPlayers()[player], true);
    		player++; 
    		index+=2;
    	}
    }
    
    private void placeInitialSettlements(int index) {
    	int player = 0;
    	while (player < numPlayers) {
    		gameManager.placeInitialSettlement(QUICK_SETUP_SETTLEMENTS[index], gameManager.getPlayers()[player]);
    		player++; 
    		index++;
    	}
    }

    private void quickSetup() {
        setupLanguage();
        int playerNum = getPlayerNum();
        initializeGameAndBoardManager(playerNum);
        quickSetupPlayers();
    }

    private void quickSetupPlayers() {
        numPlayers = gameManager.getNumPlayers();
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            quickSetupPlayer(i);
        }
    }

    private void quickSetupPlayer(int i) {
        players[i] = new Player(quickPlayerColors[i], quickPlayerNames[i],
                new ArrayList<>(quickPlayerResources));

        gameManager.decrementResourcesFromBank(quickPlayerResources);

        gameManager.setPlayer(i, players[i]);
    }

    private void normalSetup() {
        setupLanguage();
        int playerNum = getPlayerNum();
        initializeGameAndBoardManager(playerNum);
        setupWinCondition();
        setupPlayers();
        rollForPlayerOrder();
    }

    private void initializeGameAndBoardManager(int playerNum) {
        boardManager = new BoardManager();
        gameManager = new GameManager(playerNum, boardManager);
        diceManager = gameManager.diceManager;
    }

    private void rollForPlayerOrder() {
        playerRollIndex = 0;
        createRollForPlayerFrame();
        waitForPlayerRolls();
        ArrayList<Player> playersList = new ArrayList<>();
        setNewPlayerOrder(playersList);
    }

    private void setNewPlayerOrder(ArrayList<Player> playersList) {
        for (int i = 0; i < numPlayers; i++)    playersList.add(players[i]);
        Collections.sort(playersList, Comparator.comparing(item -> playerRolls.indexOf(item)));
        for (int i = 0; i < numPlayers; i++)    players[i] = playersList.get(i);
    }

    private void waitForPlayerRolls() {
        while (!playerRollsFinished) {
            sleepForInput();
        }
    }

    private void confirmRollButtonAction() {
        if (playerRollIndex >= numPlayers - 1)  setPlayerRollsFinished();
        else setupForNextPlayerRoll();
    }

    private void uniqueDiceRollButtonAction(){
        getUniquePlayerRoll();
        dicePanel.updateDiceImages();
        if(confirmButton != null) confirmButton.setEnabled(true);
        dicePanel.rollDiceButton.setEnabled(false);
    }

    private void setupForNextPlayerRoll() {
        playerRollIndex++;
        rollForPlayersFrame.dispose();
        createRollForPlayerFrame();
    }

    private void setPlayerRollsFinished() {
        rollForPlayersFrame.dispose();
        playerRollsFinished = true;
    }

    private void getUniquePlayerRoll() {
        int roll;
        do {
            roll = gameManager.diceManager.rollAllDice();
        } while (playerRolls.contains(roll));
    }

    private void createRollForPlayerFrame() {
        initializeRollForPlayersFrameAndDicePanel();
        addPlayerLabelAndDicePanelToFrame();

        addConfirmButtonToFrame();

        rollForPlayersFrame.pack();
        rollForPlayersFrame.setVisible(true);
    }

    private void addConfirmButtonToFrame() {
        confirmButton = new JButton(messages.getString("confirmRoll"));
        confirmButton.addActionListener(e -> confirmRollButtonAction());
        rollForPlayersFrame.add(confirmButton);
        confirmButton.setEnabled(false);
    }

    private void addPlayerLabelAndDicePanelToFrame() {
        JLabel playerLabel = new JLabel(players[playerRollIndex].getPlayerName());
        rollForPlayersFrame.add(playerLabel);
        rollForPlayersFrame.add(dicePanel);
        replaceDiceButtonActionListenerWithUniqueOne();
    }

    private void replaceDiceButtonActionListenerWithUniqueOne() {
        ActionListener[] actionListeners = dicePanel.rollDiceButton.getActionListeners();
        dicePanel.rollDiceButton.removeActionListener(actionListeners[0]);
        dicePanel.rollDiceButton.addActionListener(e -> uniqueDiceRollButtonAction());
    }

    private void initializeRollForPlayersFrameAndDicePanel() {
        rollForPlayersFrame = new JFrame();
        rollForPlayersFrame.setLayout(new FlowLayout());
        rollForPlayersFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dicePanel = new DicePanel(players, gameManager, gameLocale);
    }


    void setupTimer() {
        timer = new Timer(TIMER_INTERVAL, this);
        timer.setInitialDelay(TIMER_START_AFTER);
        timer.start();
    }

    public static boolean chooseQuickSetup() {
        String[] setupStyle = {"Normal", "Quick"};
        String style = ensureGetSetupStyle(setupStyle);
        if (style.equals("Quick"))  return true;
        return false;
    }

    private static String ensureGetSetupStyle(String[] setupStyles) {
        String style = null;
        while(style == null){
            style = getSetupStyle(setupStyles);
        }
        return style;
    }

    private static String getSetupStyle(String[] setupStyles) {
        String style =
                (String) JOptionPane.showInputDialog(null, "Quick or Normal Setup", "Setup Selection",
                        JOptionPane.QUESTION_MESSAGE, null, setupStyles, setupStyles[0]);
        return style;
    }

    private static void invalidRoadPlacementDisplay() {
        JOptionPane.showMessageDialog(null, messages.getString("invalidRoad"),
                messages.getString("invalidPlacementTitle"), JOptionPane.ERROR_MESSAGE);
    }

    private static void invalidSettlementPlacementDisplay() {
        JOptionPane.showMessageDialog(null, messages.getString("invalidSettlement"),
                messages.getString("invalidPlacementTitle"), JOptionPane.ERROR_MESSAGE);
    }

    private static String getFormattedPlayerMessage(String key, Object arg) {
        String messageTemplate = messages.getString(key);
        return MessageFormat.format(messageTemplate, arg);
    }

    private void setupLanguage() {
        //dropdown frame to select either english or spanish
        String[] languages = {"English", "Espanol"};
        String language = ensureGetLanguageString(languages);

        setLanguage(language);
    }

    private String ensureGetLanguageString(String[] languages) {
        String language = null;
        while(language == null){
            language = getLanguageString(languages);
        }
        return language;
    }

    private String getLanguageString(String[] languages) {
        String language =
                (String) JOptionPane.showInputDialog(null, "Choose a language", "Language Selection",
                        JOptionPane.QUESTION_MESSAGE, null, languages, languages[0]);
        return language;
    }

    private void setLanguage(String language) {
        if (language.equals("Espanol")) {
            setLocaleToSpanish();
            messages = ResourceBundle.getBundle(RESOURCE_BUNDLE, gameLocale);
        }else  messages = ResourceBundle.getBundle(RESOURCE_BUNDLE);
    }

    private void setLocaleToSpanish() {
        gameLocale = locales[1];
        Locale.setDefault(locales[1]);
        JComponent.setDefaultLocale(locales[1]);
    }
    
    private void setupWinCondition() {
    	int victoryPoints = ensureGetWinConditionInt();
    	gameManager.setScoreToWin(victoryPoints);
    }

    private int ensureGetWinConditionInt() {
    	int victoryPoints = 0;
    	while (victoryPoints <= 4 || victoryPoints >= 26) {
    		victoryPoints = getWinCondition();
    	}
    	return victoryPoints;
    }
    
    private int getWinCondition() {
    	int victoryPoints = -1;
    	String victoryPointsString = JOptionPane.showInputDialog(null, "Set Victory Points needed for Win (min 5)");
    	try {
    		victoryPoints = Integer.parseInt(victoryPointsString);
    	} catch (NumberFormatException e) {
    		victoryPoints = -1;
    	}
    	return victoryPoints;
    }

    private int getPlayerNum() {

        int playerNum = ensureStringIsInt(ensureGetPlayerNumMessage());
        while (playerNum < GameManager.MIN_PLAYERS || playerNum > GameManager.MAX_PLAYERS) {
            playerNum = ensureStringIsInt(ensureGetNumMessageAfterError());
        }
        return playerNum;
    }

    private int ensureStringIsInt(String string){
        try{
            return Integer.parseInt(string);
        } catch (NumberFormatException e){
            return -1;
        }
    }

    private String ensureGetNumMessageAfterError() {
        String playerNum = null;
        while(playerNum == null){
            playerNum = getPlayerNumMessageAfterError();
        }
        return playerNum;
    }

    private String getPlayerNumMessageAfterError() {
        return JOptionPane.showInputDialog(null, messages.getString("numPlayersError"),
                "2");
    }

    private String ensureGetPlayerNumMessage() {
        String numPlayers = null;
        while (numPlayers == null){
            numPlayers = getPlayerNumMessage();
        }
        return numPlayers;
    }

    private String getPlayerNumMessage() {
        return JOptionPane.showInputDialog(null, messages.getString("enterNumPlayers"), "2");
    }

    private void addBoardDisplayToFrame() {
        boardFrame.add(boardDisplay);
        boardFrame.repaint();
        boardFrame.setVisible(true);
    }

    private void initBoardFrame() {
        boardFrame = new JFrame();
        boardFrame.setLayout(new FlowLayout());
        boardFrame.pack();

        boardFrame.setDefaultCloseOperation(boardFrame.EXIT_ON_CLOSE);
    }

    private void initBoardDisplay() {
        int useRandomizedBoard =
                JOptionPane.showConfirmDialog(null, messages.getString("useRandomizedBoardMessage"),
                        messages.getString("useRandomizedBoardTitle"), JOptionPane.YES_NO_OPTION);

        boolean randomize = useRandomizedBoard == JOptionPane.YES_OPTION;
        boardDisplay = new BoardDisplay(boardManager, randomize, gameLocale);
    }

    void singleTurn(Player player) throws Exception {
        updatePlayerInfoForTurn(player);
        int roll = waitForDiceRoll(gameManager);
        if (roll == ROBBER_ROLL) {
            sevenRolled(player);
        }
        else {
            handleResourceDistributionOnRoll(roll);
        }


        waitForTurnOver(player);
    }

    private void updatePlayerInfoForTurn(Player player) {
        inTurn = player;
        inTurn.startTurn();
        turnDisplay.updateUIForNewPlayer(player);
        cardDisplay.drawMainPanel();
    }

    void runGame() {
        inTurn = players[gameManager.setNextPlayerInTurn()];

        try {
            mainGameLoop();
        } catch (Exception e) {
            System.err.println("Game ended due to exception: " + e.getMessage());
        }
        gameWonMessage(inTurn);

    }

//    private void tryMainGameLoop() {
//        try {
//            mainGameLoop();
//        } catch (GameOverException e) {
//        }
//    }

    private void mainGameLoop() throws Exception {
        boolean gameOver = false;
        while (!gameOver) {
            gameOver = doSingleTurnAndSetupForNextTurnIfGameNotOver();
        }
    }

    private boolean doSingleTurnAndSetupForNextTurnIfGameNotOver() throws Exception {
        singleTurn(inTurn);
        boolean gameOver = gameManager.isGameOver();
        if (!gameOver)  inTurn = players[gameManager.setNextPlayerInTurn()];
        return gameOver;
    }

    private static void gameWonMessage(Player player) {
        JOptionPane.showMessageDialog(null,
                player.getPlayerName() + messages.getString("hasWonLabel"),
                messages.getString("gameOverTitle"), JOptionPane.INFORMATION_MESSAGE);
    }


    private void sevenRolled(Player player) {
        enableButtonsAndCardGUI(false);
        sevenRolledMessage();
        handlePlayersRobberDiscard();
        handleRobberActions(player);
        ArrayList<Integer> downgradedIndexes = gameManager.handleBarbarianTrigger();
        for (Integer downgradedIndex : downgradedIndexes) {
            boardDisplay.downgradeToSettlementButton(downgradedIndex);
        }
        enableButtonsAndCardGUI(true);
    }

    public void enableButtonsAndCardGUI(boolean enable){
        turnDisplay.enableButtons(enable);
        cardDisplay.setAllEnableTo(enable);
    }

    private void handlePlayersRobberDiscard() {
        for (int j = 0; j < players.length; j++) {
            handlePlayerRobberDiscard(j);
        }
    }

    private void handleResourceDistributionOnRoll(int roll) {
        Map<Player, Map<ResourceType, Integer>> countsBefore = new HashMap<>();
        for (Player p : players) {
            if (p != null) {
                Map<ResourceType, Integer> playerCounts = new HashMap<>();
                for (ResourceType rt : ResourceType.values()) {
                    playerCounts.put(rt, p.getNumOwnedResource(rt));
                }
                countsBefore.put(p, playerCounts);
            }
        }
        int result = gameManager.distributeResourcesOnRoll(roll);

        List<Integer> affectedHexIndices = getHexIndicesFromRoll(roll);
        boardDisplay.highlightHexes(affectedHexIndices);

        for (Player p : players) {
            if (p != null) {
                boolean gained = false;
                Map<ResourceType, Integer> countsNow = new HashMap<>();
                for (ResourceType rt : ResourceType.values()) {
                    countsNow.put(rt, p.getNumOwnedResource(rt));
                }

                Map<ResourceType, Integer> previousCounts = countsBefore.getOrDefault(p, new HashMap<>());
                for(ResourceType rt : ResourceType.values()){
                    if(countsNow.getOrDefault(rt, 0) > previousCounts.getOrDefault(rt, 0)){
                        gained = true;
                        break;
                    }
                }

                if (gained) {
                    playersStats.triggerResourceGainIndicator(p);
                }
            }
        }

        if (result == 2) {
            JOptionPane.showMessageDialog(null, messages.getString("robberOnLocation"),
                    messages.getString("robberOnLocationTitle"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void waitForTurnOver(Player player) throws Exception {
        while (!turnDisplay.isTurnOver()) {
            waitForActionTaken();
            handleActionOptions(player);
            actionTaken = false;

        }
        boardDisplay.clearHighlights();
    }

    private List<Integer> getHexIndicesFromRoll(int roll) {
        List<Integer> indices = new ArrayList<>();
        Hexagon[] hexagons = boardManager.getHexagons();
        for (int i = 0; i < hexagons.length; i++) {
            if (hexagons[i].getValue() == roll && !hexagons[i].getHasRobber()) {
                indices.add(i);
            }
        }
        return indices;
    }

    private void killTradeAndBuildWindowsIfAlive(){

    }

    private void waitForActionTaken() {
        while (!actionTaken) {
            sleepForInput();
        }
    }

    private void handleActionOptions(Player player) throws Exception {
        if (buildSettlement)    handleSettlementAction(player);
        else if (buildRoad)     handleRoadAction(player);
        else if (buildCity)     handleCityAction(player);
    }

    private void handleSettlementAction(Player player) throws Exception {
        buildSettlement = false;
        tryBuildSettlement(player);
        repaintButtons();
        repaintBoardHexes();;
        hasInTurnWonTheGame();
    }

    private void hasInTurnWonTheGame() throws Exception {
        gameManager.calculateVictoryPointsForPlayer(inTurn);
        if (gameManager.isGameOver()) {
            throw new Exception();
        }
    }

    private void handleRoadAction(Player player) throws Exception {
        buildRoad = false;
        tryBuildRoad(player);
        bonusManager.findLongestRoad(players, roads);
        repaintButtons();
        repaintBoardHexes();;
        hasInTurnWonTheGame();
    }

    private void handleCityAction(Player player) throws Exception {
        buildCity = false;
        tryBuildCity(player);
        repaintButtons();
        repaintBoardHexes();;
        hasInTurnWonTheGame();
    }

    private void tryBuildCity(Player player) {
    	if (!checkValidResourcesCity(player)) {
    		return;
    	}
        buildCityMessage();
        int intersection1 = getIntersectionButtonSelection();
        if (!gameManager.buildCity(intersection1, player))  invalidCityPlacementMessage();
        else    boardDisplay.upgradeToCityButton(intersection1);
    }


    
    private boolean checkValidResourcesCity(Player player) {
    	if (!player.getEnoughForCity()) {
    		invalidResourcesMessage();
    		return false;
    	}
    	return true;
    }
    
    private void invalidResourcesMessage() {
    	JOptionPane.showMessageDialog(null, messages.getString("buildInvalidResources"),
                messages.getString("buildInvalidResourcesTitle"), JOptionPane.ERROR_MESSAGE);
    }

    private void invalidCityPlacementMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("invalidCityBuild"),
                messages.getString("invalidCityBuildTitle"), JOptionPane.ERROR_MESSAGE);
    }

    private void buildCityMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("buildCityMessage"),
                messages.getString("buildCityLabel"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void tryBuildSettlement(Player player) {
    	if (!checkValidResourcesSettlement(player)) {
    		return;
    	}
        buildSettlementMessage();
        int intersection1 = getIntersectionButtonSelection();
        if (!gameManager.buildSettlement(intersection1, player)) {
            invalidSettlementPlacementMessage();
        }
    }
    
    private boolean checkValidResourcesSettlement(Player player) {
    	if (!player.getEnoughForSettlement()) {
    		invalidResourcesMessage();
    		return false;
    	}
    	return true;
    }

    protected void tryBuildRoad(Player player) {
    	if (!checkValidResourcesRoad(player)) {
    		return;
    	}
        buildRoadMessage();
        int intersection1 = getIntersectionButtonSelection();
        int intersection2 = getIntersectionButtonSelection();
        buildRoad(player, intersection1, intersection2);
    }
    
    private boolean checkValidResourcesRoad(Player player) {
    	if (!player.getEnoughForRoad()) {
    		invalidResourcesMessage();
    		return false;
    	} 
    	return true;
    }

    private void buildRoad(Player player, int intersection1, int intersection2) {
        if (intersection1 == intersection2 || !gameManager.buildRoad(intersection1, intersection2,
                player)) {
            invalidRoadPlacementMessage();
        }
    }

    private static void invalidRoadPlacementMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("invalidRoadBuild"),
                messages.getString("invalidRoadBuildTitle"), JOptionPane.ERROR_MESSAGE);
    }

    protected static void buildRoadMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("buildRoadMessage"),
                messages.getString("buildRoadLabel"), JOptionPane.INFORMATION_MESSAGE);
    }

    private static void invalidSettlementPlacementMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("invalidSettlementBuild"),
                messages.getString("invalidSettlementBuildTitle"), JOptionPane.ERROR_MESSAGE);
    }

    private static void buildSettlementMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("buildSettlementMessage"),
                messages.getString("buildSettlementLabel"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void handlePlayerRobberDiscard(int j) {
        if (gameManager.getNumberCardsToDiscard(players[j]) != 0) {
            discardCardsMessage(j);

            startPlayerRobberDiscardAndWaitForSuccess(j);
        }
    }

    private void startPlayerRobberDiscardAndWaitForSuccess(int j) {
        discardGUI = new TradeManagerGUI(players[j], gameManager, gameLocale,
                gameManager.getNumberCardsToDiscard(players[j]));

        while (discardGUI.isShowing()) {
            sleepForInput();
        }
    }

    protected void discardCardsMessage(int j) {
        JOptionPane.showMessageDialog(null, getFormattedDiscardMessage(j),
                messages.getString("discardTitle"), JOptionPane.INFORMATION_MESSAGE);
    }

    private String getFormattedDiscardMessage(int j) {
        String messageTemplate = messages.getString("discardMessage");
        return MessageFormat.format(messageTemplate, players[j].getPlayerName(),
                gameManager.getNumberCardsToDiscard(players[j]));
    }

    private void handleRobberActions(Player currentPlayer) {
        handleMoveRobber();

        Player selectedPlayerToSteal = getPlayerToStealFrom(currentPlayer);

        tryStealResourceWithMessageToPlayers(currentPlayer, selectedPlayerToSteal);

    }

    public Player getPlayerToStealFrom(Player currentPlayer) {
        Map<String, Player> playerMap = setupPlayerMap(currentPlayer);
        if (playerMap.isEmpty()) {
            displayRobberNoPlayersToStealFrom();
            return null;
        } else return playerMap.get(getNameOfPlayerToSteal(playerMap));
    }

    private static void displayRobberNoPlayersToStealFrom() {
        JOptionPane.showMessageDialog(null, messages.getString("noPlayersToStealMessage"),
                messages.getString("noPlayersToStealTitle"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleMoveRobber() {
        toggleHexButtons(true);
        boolean robberMoved = false;
        while (!robberMoved)    robberMoved = getHexAndTryRobberMovement();
        toggleHexButtons(false);
    }


    private boolean getHexAndTryRobberMovement() {
        int hexSelection = waitForHexSelection();
        return tryRobberMovement(hexSelection);
    }

    @SuppressWarnings("methodlength")
    private boolean tryRobberMovement(int hexSelection) {
        boolean robberMoved = false;
        try {
            gameManager.moveRobber(hexSelection);
            robberMoved = true;
        } catch (IllegalArgumentException e) {
            displayRobberPlacementError();
        }
        return robberMoved;
    }

    public static void displayRobberPlacementError() {
        JOptionPane.showMessageDialog(null, messages.getString("invalidRobberPlacement"),
                messages.getString("invalidRobberPlacementTitle"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void tryStealResourceWithMessageToPlayers(Player currentPlayer,
                                                      Player selectedPlayerToSteal) {
        try {
            stealResourceWithMessageToPlayers(currentPlayer, selectedPlayerToSteal);
        } catch (IllegalArgumentException e) {
            // display already handled
        }

    }

    private void stealResourceWithMessageToPlayers(Player currentPlayer,
                                                   Player selectedPlayerToSteal) {
        String resourceStolen =
                gameManager.tryRobberSteal(currentPlayer, selectedPlayerToSteal).toString();
        displayRobberResourceStolenMessage(selectedPlayerToSteal.getPlayerName(),
                resourceStolen);
    }


    public static void displayRobberResourceStolenMessage(String playerToStealFrom,
                                                          String resourceStolen) {
        JOptionPane.showMessageDialog(null,
                formatResourceStolenMessage(resourceStolen, playerToStealFrom),
                messages.getString("resourceStolenTitle"), JOptionPane.INFORMATION_MESSAGE);
    }

    private static String formatResourceStolenMessage(String resourceStolen,
                                                      String playerToStealFrom) {
        String messageTemplate = messages.getString("resourceStolenFromPlayerMessage");
        String formattedResourceStolen = getFormattedResourceStolen(resourceStolen);
        return MessageFormat.format(messageTemplate, formattedResourceStolen, playerToStealFrom);
    }

    public static String getFormattedResourceStolen(String resourceStolen) {
    	return messages.getString(resourceStolen.toLowerCase());
    }

    private static String getNameOfPlayerToSteal(Map<String, Player> playerMap) {
        String[] playerNames = playerMap.keySet().toArray(new String[0]);

        String playerToStealFrom = getPlayerToStealEnsureNotNull(playerNames);
        return playerToStealFrom;
    }

    private static String getPlayerToStealEnsureNotNull(String[] playerNames) {
        String playerToStealFrom = null;
        while(playerToStealFrom == null){
            playerToStealFrom = getPlayerToStealFromMessage(playerNames);
        }
        return playerToStealFrom;
    }

    private static String getPlayerToStealFromMessage(String[] playerNames) {
        String playerToStealFrom =
                (String) JOptionPane.showInputDialog(null, messages.getString("chooseToStealMessage"),
                        messages.getString("chooseToStealTitle"), JOptionPane.QUESTION_MESSAGE, null,
                        playerNames, playerNames[0]);
        return playerToStealFrom;
    }

    private Map<String, Player> setupPlayerMap(Player currentPlayer) {
        ArrayList<Player> playersToStealFrom = gameManager.getHexagonPlayers();

        Map<String, Player> playerMap = new HashMap<>();
        addPlayersToStealFromToMap(currentPlayer, playersToStealFrom, playerMap);
        return playerMap;
    }

    private void addPlayersToStealFromToMap(Player currentPlayer,
                                            ArrayList<Player> playersToStealFrom, Map<String, Player> playerMap) {
        for (Player p : playersToStealFrom) {
            if (!p.equals(currentPlayer)) {
                playerMap.put(p.getPlayerName(), p);
            }
        }
    }

    private static void sevenRolledMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("sevenRolledMessage"),
                messages.getString("sevenRolledMessage"), JOptionPane.INFORMATION_MESSAGE);
    }

    public static void moveRobberMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("selectRobberHexMessage"),
                messages.getString("moveRobberTitle"), JOptionPane.INFORMATION_MESSAGE);
    }

    protected int waitForHexSelection() {
        while (boardManager.peekHexSelection() == -1) {
            sleepForInput();
        }
        return boardManager.getHexSelection();
    }

    protected void toggleHexButtons(boolean toggle) {
        boardDisplay.toggleHexButtons(toggle);
        repaintButtons();
        repaintBoardHexes();;
    }

    private int waitForDiceRoll(GameManager gameManager) {
        while (!diceManager.hasPlayerRolledDice()) {
            sleepForInput();
        }
        return gameManager.getCurrentDiceRoll();
    }

    public void initialBoardSetup(Player[] players) {
        initialBoardSetupFirstSettlementAndRoadPlacement(players);
        initialBoardSetupSecondSettlementAndRoadPlacement(players);
    }

    private void initialBoardSetupSecondSettlementAndRoadPlacement(Player[] players) {
        for (int i = players.length - 1; i > -1; i--) {
            trySecondRoadAndSettlementPlacement(players, i);
        }
    }

    private void initialBoardSetupFirstSettlementAndRoadPlacement(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            tryFirstSettlementAndRoadPlacement(players, i);
        }
    }

    private void tryFirstSettlementAndRoadPlacement(Player[] players, int i) {
        initialSettlementDisplay(players, i, messages.getString("settlementFirst"));
        waitForSettlementPlacement(players[i], false);
        initialRoadDisplay(players, i, messages.getString("settlementFirst"));
        waitForRoadPlacement(players[i]);
    }

    private void waitForSettlementPlacement(Player players, boolean giveResources) {

        boolean settlementPlaced = false;
        while (!settlementPlaced) {
            settlementPlaced = getAndTrySettlementPlacement(players, giveResources);
        }
    }

    private void trySecondRoadAndSettlementPlacement(Player[] players, int i) {
        initialSettlementDisplay(players, i, messages.getString("settlementSecond"));
        waitForSettlementPlacement(players[i], false);
        initialRoadDisplay(players, i, messages.getString("settlementSecond"));
        waitForRoadPlacement(players[i]);
    }

    protected void waitForRoadPlacement(Player players) {
        boolean roadPlaced = false;
        while (!roadPlaced) {
            roadPlaced = getAndTryRoadPlacement(players);
        }
    }

    private boolean getAndTryRoadPlacement(Player player) {
        int intersection1 = getIntersectionButtonSelection();
        int intersection2 = getIntersectionButtonSelection();
        boolean roadPlaced = tryRoadPlacement(player, intersection1, intersection2);
        return roadPlaced;
    }

    @SuppressWarnings("methodlength")
    private boolean tryRoadPlacement(Player player, int intersection1, int intersection2) {
        boolean roadPlaced = false;
        try {
            roadPlaced = placeRoad(player, intersection1, intersection2);
        } catch (IllegalArgumentException e) {
            invalidRoadPlacementDisplay();
        }
        return roadPlaced;
    }

    private boolean placeRoad(Player player, int intersection1, int intersection2) {
        gameManager.placeRoad(intersection1, intersection2, player, true);
        repaintButtons();
        repaintBoardHexes();;
        return true;
    }

    private void initialRoadDisplay(Player[] players, int i, String roadNumber) {
        JOptionPane.showMessageDialog(null,
                getFormattedStructureMessage("placeInitialRoadMessage", roadNumber),
                getFormattedPlayerMessage("playerTurnTitle", players[i].getPlayerName()), JOptionPane.INFORMATION_MESSAGE);
    }

    private String getFormattedStructureMessage(String key, String structureNumber) {
        String messageTemplate = messages.getString(key);
        return MessageFormat.format(messageTemplate, structureNumber);
    }

    private boolean getAndTrySettlementPlacement(Player player, boolean giveResources) {
        int intersection1 = getIntersectionButtonSelection();
        return trySettlementPlacement(player, giveResources, intersection1);
    }

    @SuppressWarnings("methodlength")
    private boolean trySettlementPlacement(Player player, boolean giveResources, int intersection) {
        boolean settlementPlaced = false;
        try {
            settlementPlaced = placeSettlement(player, giveResources, intersection);
        } catch (IllegalArgumentException e) {
            invalidSettlementPlacementDisplay();
        }
        return settlementPlaced;
    }

    private boolean placeSettlement(Player player, boolean giveResources, int intersection1) {
        gameManager.placeInitialSettlement(intersection1, player);
        if (giveResources)  gameManager.giveInitialResources(intersection1, player);
        repaintButtons();
        repaintBoardHexes();;
        return true;
    }

    private void initialSettlementDisplay(Player[] players, int playerIndex, String settlementNumber) {
        JOptionPane.showMessageDialog(null,
                getFormattedStructureMessage("placeInitialSettlementMessage", settlementNumber),
                getFormattedPlayerMessage("playerTurnTitle", players[playerIndex].getPlayerName()),
                JOptionPane.INFORMATION_MESSAGE);
    }

    protected int getIntersectionButtonSelection() {
        boardDisplay.toggleIntersectionButtons(true);
        waitForIntersectionButtonSelection();
        boardDisplay.toggleIntersectionButtons(false);

        return boardManager.getIntersectionSelection();
    }

    private void waitForIntersectionButtonSelection() {
        while (boardManager.peekIntersectionSelection() == -1) {
            sleepForInput();
        }
    }

    private static void sleepForInput() {
        try {
            Thread.sleep(MILLS_TO_WAIT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    protected void repaintBoard() {
//        repaintButtons();
//        repaintBoardHexes();
//    }

    private void repaintBoardHexes() {
        boardDisplay.repaint();
    }

    private void repaintButtons() {
        boardDisplay.redrawButtons(boardManager);
        boardFrame.revalidate();
        boardFrame.repaint();
    }

    private Player getPlayerByName(String name) {
        for (int i = 0; i < numPlayers; i++) {
            if (players[i] == null) return null;
            if (players[i].getPlayerName().equals(name))    return players[i];
        }
        return null;
    }

    //
    public void setupPlayers() {
        numPlayers = gameManager.getNumPlayers();
        players = new Player[numPlayers];
        colorPickerDisplay = new ColorPickerDisplay(numPlayers, gameLocale);

        for (int i = 0; i < numPlayers; i++)    setupPlayer(i);
    }

    private void setupPlayer(int i) {
        String curName = getPlayerName(i);
        Color chosenColor = colorPickerDisplay.chooseColor();

        players[i] = new Player(chosenColor, curName, new ArrayList<>());
        gameManager.setPlayer(i, players[i]);
    }

    private String getPlayerName(int i) {
        String curName = getPlayerNameMessage(i);
        while (curName == null || getPlayerByName(curName) != null) {
            curName = getNewPlayerNameAfterErrorMessage();
        }
        return curName;
    }

    private String getNewPlayerNameAfterErrorMessage() {
        String curName;
        invalidPlayerNameMessage();
        curName = ensureGetNewPlayerNameAfterErrorMessage();
        return curName;
    }

    private String ensureGetNewPlayerNameAfterErrorMessage() {
        String playerName = null;
        while (playerName == null){
            playerName = newPlayerNameAfterErrorMessage();
        }
        return playerName;
    }

    private String newPlayerNameAfterErrorMessage() {
        return JOptionPane.showInputDialog(null, messages.getString("enterPlayerName"), "");
    }

    private String getPlayerNameMessage(int i) {
        String curName = JOptionPane.showInputDialog(null,
                getFormattedPlayerMessage("playerSelectionMessage", i + 1),
                messages.getString("namePlaceholder"));
        return curName;
    }

    private void invalidPlayerNameMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("playerNameSelectionError"),
                messages.getString("invalidInput"), JOptionPane.ERROR_MESSAGE);
    }

    public void toggleIntersectionButtons(boolean toggle) {
        boardDisplay.toggleIntersectionButtons(toggle);
        repaintButtons();
        repaintBoardHexes();;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        playersStats.updatePlayersStats();
        barbarianRaidDisplay.updateDisplay();
        updateCardGUI();
        repaintButtons();
        repaintBoardHexes();;
    }

    private void updateCardGUI() {
        if (diceManager.hasPlayerRolledDice() && turnDisplay.isEnableFlag()
                && (discardGUI == null || !discardGUI.isShowing()))
            cardDisplay.setAllEnableTo(true);
        else
            cardDisplay.setAllEnableTo(false);
    }
}

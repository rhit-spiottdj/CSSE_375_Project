package presentation;

import domain.DevelopmentCards;
import domain.GameManager;
import domain.Player;
import domain.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import static domain.ResourceType.*;


public class CardGUI implements ActionListener {

    public static final int FRAME_WIDTH = 790;
    public static final int FRAME_HEIGHT = 400;
    public static final int MAIN_PANEL_WIDTH = 250;
    public static final int MAIN_PANEL_HEIGHT = 500;

    JPanel mainPanel;

    JButton buyButton;
    JPanel cardPanel;

    JPanel frame;
    GameManager gameManager;
    Player[] players;
    GameDisplay display;

    static ResourceBundle messages;


    public CardGUI(Player[] players, GameManager gameManager, GameDisplay display, Locale locale) {

        initializeFields(players, gameManager, display, locale);

        initializeFrameAndPanel();

        drawMainPanel();

        mainPanel.setMaximumSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
    }

    private void initializeFrameAndPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame = new JPanel();
        frame.add(mainPanel);
        frame.setLocation(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void drawMainPanel() {
        mainPanel.removeAll();
        initializeAndDisplayButtonsAndPanels();
        mainPanel.revalidate();
        mainPanel.repaint();
        updateFrame();
    }

    private void initializeAndDisplayButtonsAndPanels() {
        initializeBuyButton();
        initializeCardPanel();
        displayPlayableCardsPanel();
        displayUnplayableCardsPanel();
    }

    private void initializeFields(Player[] players, GameManager gameManager, GameDisplay display,
        Locale locale) {
        this.players = players;
        this.gameManager = gameManager;
        this.display = display;
        messages = ResourceBundle.getBundle("messages", new Locale(locale.getLanguage()));
    }

    private void initializeBuyButton() {
        buyButton = new JButton(messages.getString("buyACardLabel"));
        buyButton.addActionListener(e -> buyButtonAction());
        mainPanel.add(buyButton);
    }

    private void buyButtonAction() {
        if (gameManager.buyDevelopmentCard()) {
            displaySuccessfulDevelopmentCardPurchaseMessage();
        } else  purchaseFailedMessage();
        drawMainPanel();
    }

    private void displaySuccessfulDevelopmentCardPurchaseMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("buyDevCardSuccess"),
            messages.getString("successfulPurchaseTitle"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void purchaseFailedMessage() {
        if (gameManager.getDevelopmentCardsInBank().isEmpty()) {
            displayBankOutOfDevelopmentCardsMessage();
        } else {
            displayLackResourcesForDevelopmentCardMessage();
        }
    }

    private void displayLackResourcesForDevelopmentCardMessage() {
        JOptionPane.showMessageDialog(null,
            messages.getString("purchaseFailedMessage"), messages.getString("purchaseFailedTitle"),
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayBankOutOfDevelopmentCardsMessage() {
        JOptionPane.showMessageDialog(null,
            messages.getString("outOfDevCardsMessage"), messages.getString("purchaseFailedTitle"),
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void initializeCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        mainPanel.add(cardPanel);
    }


    private void updateFrame() {
        frame.setVisible(true);
    }


    private void displayPlayableCardsPanel() {
        cardPanel.add(new JLabel(messages.getString("playableCardsLabel")));
        boolean isEmpty = displayPlayableCards();
        if (isEmpty)    cardPanel.add(new JLabel(messages.getString("noPlayableCardsLabel")));
    }

    private boolean displayPlayableCards() {
        for (DevelopmentCards card : gameManager.getPlayableDevelopmentCards()) {
            addPlayableCardToPanel(card);
        }
        return gameManager.getPlayableDevelopmentCards().isEmpty();
    }

    private void addPlayableCardToPanel(DevelopmentCards card) {
        JComponent playableCard;
        if(getCurrentPlayer().isDevCardPlayed()) playableCard = createCardLabel(card);
        playableCard = createPlayableCardButton(card);
        cardPanel.add(playableCard);
    }

    private JButton createPlayableCardButton(DevelopmentCards card) {
        String cardTitle = getDevelopmentCardText(card);
        JButton playableCard = new CardButton(cardTitle, card);
        playableCard.addActionListener(this);
        return playableCard;
    }

    @SuppressWarnings("methodlength")
    private String getDevelopmentCardText(DevelopmentCards card) {
        switch (card) {
            case ROAD:
                return messages.getString("roadBuilding");
            case MONOPOLY:
                return messages.getString("monopoly");
            case PLENTY:
                return messages.getString("yearOfPlenty");
            case KNIGHT:
                return messages.getString("knight");
            case VICTORY:
                return messages.getString("victoryPoint");
            default:
                return messages.getString("unknown");
        }
    }



    private void displayUnplayableCardsPanel() {
        cardPanel.add(new JLabel(messages.getString("usedAndUnplayableCards")));
        boolean isEmpty = displayUnplayableCards();
        if (isEmpty)    cardPanel.add(new JLabel(messages.getString("noUsedAndUnplayableCards")));
    }

    private boolean displayUnplayableCards() {
        return displayActualUnplayableCards() && displayFutureDevelopmentCards();
    }

    private boolean displayActualUnplayableCards() {
        for (DevelopmentCards card : gameManager.getUnplayableDevelopmentCards()) {
            displayUnplayableDevelopmentCard(card);
        }
        return gameManager.getUnplayableDevelopmentCards().isEmpty();
    }

    private boolean displayFutureDevelopmentCards() {
        for (DevelopmentCards card : gameManager.getFutureDevelopmentCards()) {
            displayUnplayableDevelopmentCard(card);
        }
        return gameManager.getFutureDevelopmentCards().isEmpty();
    }

    private void displayUnplayableDevelopmentCard(DevelopmentCards card) {
        JLabel cardLabel = createCardLabel(card);
        cardPanel.add(cardLabel);
    }

    private JLabel createCardLabel(DevelopmentCards card) {
        String cardTitle = getDevelopmentCardText(card);
        JLabel cardLabel = new JLabel(cardTitle);
        return cardLabel;
    }


    @Override//Set Card to Play
    public void actionPerformed(ActionEvent e) {
        CardButton button = (CardButton) e.getSource();

        button.setEnabled(false);

        DevelopmentCards devCard = button.getCardType();

        playCard(devCard);
    }

    @SuppressWarnings("methodlength")
    private void playCard(DevelopmentCards devCard) {
        switch (devCard) {
            case ROAD:
                playRoadBuilding();
                return;
            case MONOPOLY:
                tryPlayMonopoly();
                return;
            case PLENTY:
                tryPlayYearOfPlenty();
                return;
            case KNIGHT:
                playKnight();
                return;
            default:
                return;
        }
    }

    private void tryPlayMonopoly(){
        try {
            playMonopoly();
        } catch(IllegalArgumentException e){
            System.err.println(e);
        }
    }

    private void playMonopoly() {
        display.enableButtonsAndCardGUI(false);
        prepareForAndTryPlayMonopoly();
        getCurrentPlayer().setDevelopmentCardAsPlayed(DevelopmentCards.MONOPOLY);
        successfulCardPlayed();
    }

    private void prepareForAndTryPlayMonopoly() {
        String message = messages.getString("monopolyMessage");
        String title = messages.getString("monopoly");
        ResourceType resourceOne = getResourceInput(message, title);
        gameManager.playMonopolyCard(resourceOne);
    }

    private void playRoadBuilding(){
        Thread t = new Thread(() -> playRoadBuildingThread());
        t.start();
    }

    private void playRoadBuildingThread() {
        display.enableButtonsAndCardGUI(false);
        prepareForAndPlayRoadBuilding();
        getCurrentPlayer().setDevelopmentCardAsPlayed(DevelopmentCards.ROAD);
        successfulCardPlayed();
    }

    private void successfulCardPlayed() {
        drawMainPanel();
        getCurrentPlayer().setDevCardPlayed();
        display.enableButtonsAndCardGUI(true);
    }

    private void prepareForAndPlayRoadBuilding() {
        displayFirstRoadBuildingMessage();
        int[] roadOne = toggleIntersectionsAndGetRoadInput();

        displaySecondRoadBuildingMessage();
        int[] roadTwo = toggleIntersectionsAndGetRoadInput();


        playRoadBuildingLoop(roadOne, roadTwo);
    }

    private void playRoadBuildingLoop(int[] roadOne, int[] roadTwo) {
        while(!gameManager.playRoadBuildingCard(combineRoads(roadOne, roadTwo))){
            displayInvalidRoadPlacementMessage();
            roadOne = toggleIntersectionsAndGetRoadInput();
            roadTwo = toggleIntersectionsAndGetRoadInput();
        }
    }

    private void displayInvalidRoadPlacementMessage() {
        JOptionPane.showMessageDialog(null, "Invalid road placement", "Road Building",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void displaySecondRoadBuildingMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("buildSecondRoad"),
            messages.getString("roadBuilding"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayFirstRoadBuildingMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("buildFirstRoad"),
            messages.getString("roadBuilding"), JOptionPane.INFORMATION_MESSAGE);
    }


    private int[][] combineRoads(int[] roadOne, int[] roadTwo) {
        int[][] roads = {{roadOne[0], roadOne[1]},{roadTwo[0],roadTwo[1]}};
        return roads;
    }

    private int[] toggleIntersectionsAndGetRoadInput() {
        GameDisplay.buildRoadMessage();
        display.toggleIntersectionButtons(true);
        int[] intersection = getRoadInput();
        display.toggleIntersectionButtons(false);
        return intersection;
    }

    private int[] getRoadInput() {
        int[] intersection = new int[2];
        intersection[0] = display.getIntersectionButtonSelection();
        intersection[1] = display.getIntersectionButtonSelection();
        return intersection;
    }

    private void playKnight() {
        Thread t = new Thread(() -> playKnightThreadAction());
        t.start();
    }

    private void playKnightThreadAction() {
        display.enableButtonsAndCardGUI(false);
        prepareForAndPlayKnight();
        successfulCardPlayed();
    }

    private void prepareForAndPlayKnight() {
        int oldHexIndex = gameManager.getRobberLocation();
        int hexIndex = toggleHexButtonsAndTemporarilyMoveRobber();
        Player toStealFrom = display.getPlayerToStealFrom(display.inTurn);
        gameManager.moveRobber(oldHexIndex);

        gameManager.playKnight(toStealFrom, hexIndex);
    }

    private int toggleHexButtonsAndTemporarilyMoveRobber() {
        display.toggleHexButtons(true);
        int hexIndex = temporarilyMoveRobber();
        display.toggleHexButtons(false);
        return hexIndex;
    }

    private int temporarilyMoveRobber() {
        GameDisplay.moveRobberMessage();
        int hexIndex = display.waitForHexSelection();
        waitAndEnsureValidRobberMove(hexIndex);
        return hexIndex;
    }

    private void waitAndEnsureValidRobberMove(int hexIndex) {
        boolean successfulRobberMovement = false;
        while (!successfulRobberMovement) {
            successfulRobberMovement = tryMoveRobber(hexIndex);
        }
    }

    @SuppressWarnings("methodlength")
    private boolean tryMoveRobber(int hexIndex) {
        boolean successfulRobberMovement = false;
        try {
            gameManager.moveRobber(hexIndex);
            successfulRobberMovement = true;
        } catch (IllegalArgumentException e) {
            GameDisplay.displayRobberPlacementError();
        }
        return successfulRobberMovement;
    }

    private void tryPlayYearOfPlenty(){
        try {
            disableButtonsAndPlayYearOfPlenty();
        } catch(IllegalArgumentException e){
            System.err.println(e);
        }
    }

    private void playYearOfPlenty() {
        String message = messages.getString("yearOfPlentyMessage");
        String title = messages.getString("yearOfPlenty");
        ResourceType resourceOne = getResourceInput(message, title);
        ResourceType resourceTwo = getResourceInput(message, title);
        activateYearOfPlenty(resourceOne, resourceTwo);
    }

    private void disableButtonsAndPlayYearOfPlenty(){
        display.enableButtonsAndCardGUI(false);
        playYearOfPlenty();
    }

    private void activateYearOfPlenty(ResourceType resourceOne, ResourceType resourceTwo) {
        gameManager.playYearOfPlenty(resourceOne, resourceTwo);
        getCurrentPlayer().setDevelopmentCardAsPlayed(DevelopmentCards.PLENTY);
        successfulCardPlayed();
    }

    private Player getCurrentPlayer(){
        for(Player player: players){
            if (gameManager.isInTurnPlayer(player)) return player;
        }
        return null;
    }

    static ResourceType getResourceInput(String message, String title) {
        int option = ensureGetResourceInputFromMessage(message, title);

        if(option != JOptionPane.CLOSED_OPTION){
            return convertOptionToResourceType(option);
        }
        throw new IllegalArgumentException("Card requires a selected resource");
    }

    private static int ensureGetResourceInputFromMessage(String message, String title) {
        int option = JOptionPane.CLOSED_OPTION;
        while(option == JOptionPane.CLOSED_OPTION){
            option = getResourceInputFromMessage(message, title);
        }
        return option;
    }

    private static int getResourceInputFromMessage(String message, String title) {
        String[] options = getResourceLabelsInLanguage();

        int option =  JOptionPane.showOptionDialog(null, message, title,
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        return option;
    }

    private static String[] getResourceLabelsInLanguage() {
        String[] options = new String[] {messages.getString("grain"), messages.getString("lumber"),
            messages.getString("brick"),
            messages.getString("ore"),
            messages.getString("wool")};
        return options;
    }

    @SuppressWarnings({"magicnumber","methodlength"})
    private static ResourceType convertOptionToResourceType(int option){
        switch (option){
            case 0: return GRAIN;
            case 1: return LUMBER;
            case 2: return BRICK;
            case 3: return ORE;
            case 4: return WOOL;
            default: return GRAIN;
        }
    }

    public void setAllEnableTo(boolean enabled){
        if(buyButton != null) buyButton.setEnabled(enabled);
        if(cardPanel != null) setCardPanelEnabledTo(enabled);
    }

    private void setCardPanelEnabledTo(boolean enabled){
        cardPanel.setEnabled(enabled);
        checkIfDevCardPlayed(enabled);
    }

    private void checkIfDevCardPlayed(boolean enabled) {
        if(getCurrentPlayer().isDevCardPlayed()){
            toggleComponents(false);
        } else {
            toggleComponents(enabled);
        }
    }

    private void toggleComponents(boolean b) {
        for (Component component : cardPanel.getComponents()) {
            component.setEnabled(b);
        }
    }

}




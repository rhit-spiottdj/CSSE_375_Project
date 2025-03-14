package presentation;

//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.*;

import static domain.ResourceType.GRAIN;

public class TradeManagerGUI {

    public static final int MAX_SINGLE_RESOURCE = 19;
    public static final int MULTIPLE_OF_FOUR = 4;
    public static final int MULTIPLE_OF_THREE = 3;
    private static ResourceBundle messages;
    ResourceType[] resourceOrder =
        new ResourceType[]{ResourceType.BRICK, GRAIN, ResourceType.LUMBER,
            ResourceType.WOOL, ResourceType.ORE};
    int numDiscard;
    private JFrame frame;
    private JPanel enterResources;
    private JPanel tradeOptions;
    private JLabel label;
    private JLabel brickLabel;
    private JLabel grainLabel;
    private JLabel lumberLabel;
    private JLabel woolLabel;
    private JLabel oreLabel;
    private JLabel tradeOptionsLabel;
    private JTextField brick;
    private JTextField grain;
    private JTextField lumber;
    private JTextField wool;
    private JTextField ore;
    private JPanel brickPanel;
    private JPanel grainPanel;
    private JPanel lumberPanel;
    private JPanel woolPanel;
    private JPanel orePanel;

    private TradeRatio brickTradeRatio;
    private JButton tradeAway;
    private JButton tradeBank;
    private JButton tradeFor;
    private JButton discard;
    private JButton[] yesButtons = new JButton[0]; //Prevents Arrays issues in actionPerformed
    private JButton[] noButtons = new JButton[0]; //Prevents Arrays issues in actionPerformed
    private boolean[] validTradees;
    private Player[] players;
    private Collection<ResourceType> resourcesToTradeAway;
    private Collection<ResourceType> resourcesDesired;
    private GameManager manager;
    private TradeRatio grainTradeRatio;
    private TradeRatio woolTradeRatio;
    private TradeRatio lumberTradeRatio;
    private TradeRatio oreTradeRatio;

    private boolean active = true;

    TradeManagerGUI(Player[] players, GameManager manager, Locale locale) {
        initializePassedInFields(manager, players, 0, locale);
        initializeMainFields();

        initializeGUI(false);

        frame.add(enterResources);
        displayFrame();
    }

    private void initializeGUI(boolean discardMode) {
        initializeLabels(discardMode);
        initializeTextFields();
        initializeOptionalGUI(discardMode);
        initializeTradeOutGUI(discardMode);
    }

    private void initializeOptionalGUI(boolean discardMode) {
        if(discardMode) initializeDiscardButton();
        else    initializeTradePanelsAndButtons();
    }

    private void initializeTradePanelsAndButtons() {
        initializeTextFieldPanels();
        initializeTradeAwayButton();
        initializeTradeBankAndPortButton();
    }

    private void initializePassedInFields(GameManager manager, Player[] players, int numDiscard,
        Locale locale) {
        this.manager = manager;
        this.players = players;
        this.numDiscard = numDiscard;
        messages = ResourceBundle.getBundle("messages", new Locale(locale.getLanguage()));
    }

    TradeManagerGUI(Player player, GameManager manager, Locale locale, int numDiscard) {
        initializeDiscardFields(player, manager, locale, numDiscard);

        initializeGUI(true);

        frame.setUndecorated(true);
        frame.add(enterResources);
        displayFrame();
    }

    private void initializeDiscardFields(Player player, GameManager manager, Locale locale,
        int numDiscard) {
        initializePassedInFields(manager,  new Player[]{player}, numDiscard, locale);
        initializeMainFields();
    }


    private static void noResourcesSelectedMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("noResourcesSelected"),
            messages.getString("noResourcesSelected"), JOptionPane.ERROR_MESSAGE);
    }

    private static void moreThanOneResourceTypeSelectedForBankMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("oneResourceTypeOnly"),
            messages.getString("bankPortFailTitle"), JOptionPane.ERROR_MESSAGE);
    }

    private static void notAMultipleOfTradeRatioMessage(){
        JOptionPane.showMessageDialog(null, messages.getString("tradeRatioMessages"),
            messages.getString("bankPortFailTitle"), JOptionPane.ERROR_MESSAGE);
    }

    private static void insufficientResourcesMessage(int numDiscard) {
        JOptionPane.showMessageDialog(null, getFormattedInsufficientResourcesMessage(numDiscard),
            messages.getString("invalidInput"), JOptionPane.ERROR_MESSAGE);
    }

    private static void discardFailMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("cannotDiscardResourcesMessage"),
            messages.getString("invalidInput"), JOptionPane.ERROR_MESSAGE);
    }

    private static String getFormattedInsufficientResourcesMessage(int numDiscard) {
        String insufficientResourcesMessage = messages.getString("insufficientResourcesMessage");
        return MessageFormat.format(insufficientResourcesMessage, numDiscard);
    }

    private static void discardSuccessMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("resourcesDiscardedMessage"),
            messages.getString("successMessage"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void initializeTextFieldPanels() {
        initializeBrickTextFieldPanel();
        initializeGrainTextFieldPanel();
        initializeLumberTextFieldPanel();
        initializeWoolTextFieldPanel();
        initializeOreTextFieldPanel();
    }

    private void initializeBrickTextFieldPanel() {
        brickPanel = new JPanel(new GridLayout());
        brickTradeRatio = getResourceTradeRatio(ResourceType.BRICK);
        JLabel brickRatioLabel = new JLabel(brickTradeRatio.toString());
        addContentsToResourcePanel(brickPanel, brickLabel, brickRatioLabel, brick);
    }

    private void addContentsToResourcePanel(JPanel resourcePanel, JLabel resourceLabel,
        JLabel resourceRatioLabel, JTextField resourceText) {
        resourcePanel.add(resourceLabel);
        resourcePanel.add(resourceRatioLabel);
        resourcePanel.add(resourceText);
    }

    private void initializeWoolTextFieldPanel() {
        woolPanel = new JPanel(new GridLayout());
        woolTradeRatio = getResourceTradeRatio(ResourceType.WOOL);
        JLabel woolRatioLabel = new JLabel(woolTradeRatio.toString());
        addContentsToResourcePanel(woolPanel, woolLabel, woolRatioLabel, wool);
    }

    private void initializeLumberTextFieldPanel() {
        lumberPanel = new JPanel(new GridLayout());
        lumberTradeRatio = getResourceTradeRatio(ResourceType.LUMBER);
        JLabel lumberRatioLabel = new JLabel(lumberTradeRatio.toString());
        addContentsToResourcePanel(lumberPanel, lumberLabel, lumberRatioLabel, lumber);
    }

    private void initializeOreTextFieldPanel() {
        orePanel = new JPanel(new GridLayout());
        oreTradeRatio = getResourceTradeRatio(ResourceType.ORE);
        JLabel oreRatioLabel = new JLabel(oreTradeRatio.toString());
        addContentsToResourcePanel(orePanel, oreLabel, oreRatioLabel, ore);
    }

    private void initializeGrainTextFieldPanel() {
        grainPanel = new JPanel(new GridLayout());
        grainTradeRatio = getResourceTradeRatio(GRAIN);
        JLabel grainRatioLabel = new JLabel(grainTradeRatio.toString());
        addContentsToResourcePanel(grainPanel, grainLabel, grainRatioLabel, grain);
    }

    private TradeRatio getResourceTradeRatio(ResourceType resource){
        Player player = getInTurnPlayer();
        if(getPortIfOwnedByPlayer(player, newTwoPort(resource)))  return TradeRatio.TWO_TO_ONE;
        else if (getPortIfOwnedByPlayer(player, newThreePort()))  return TradeRatio.THREE_TO_ONE;
        else   return TradeRatio.FOUR_TO_ONE;
    }

    private Port newTwoPort(ResourceType resource){
        return new Port(PortTradeRatio.TWO_TO_ONE, resource);
    }

    private Port newThreePort(){
        return new Port(PortTradeRatio.THREE_TO_ONE, GRAIN);
    }

    public boolean getPortIfOwnedByPlayer(Player player, Port toCompare) {
        BoardManager board = manager.boardManager;
        for (Intersection intersection : board.getIntersections()) {
            if (checkForStructureBeforePort(player, toCompare, intersection))   return true;
        }
        return false;
    }

    private boolean checkForStructureBeforePort(Player player, Port toCompare,
        Intersection intersection) {
        Structure structure = intersection.getStructure();
        if (checkStructureIsNullBeforePort(player, toCompare, intersection, structure)) return true;
        return false;
    }

    private boolean checkStructureIsNullBeforePort(Player player, Port toCompare,
        Intersection intersection, Structure structure) {
        if(structure != null && playerOwnsStructureAndCorrectPort(player,
            toCompare, intersection, structure)){
            return true;
        }
        return false;
    }

    private boolean playerOwnsStructureAndCorrectPort(Player player, Port toCompare,
        Intersection intersection, Structure structure) {
        Player owner = structure.getOwner();
        if (owner.equals(player) && samePortType(intersection.getPort(), toCompare)) {
            return true;
        }
        return false;
    }

    private boolean samePortType(Port port, Port toCompare) {
        if(port == null) return false;
        return (port.getPortTradeRatio() == PortTradeRatio.THREE_TO_ONE &&
               toCompare.getPortTradeRatio() == PortTradeRatio.THREE_TO_ONE) ||
               port.getResourceType() == toCompare.getResourceType() &&
               port.getPortTradeRatio() == toCompare.getPortTradeRatio();
    }

    private Player getInTurnPlayer() {
        for (Player player : players)   if (manager.isInTurnPlayer(player)) return player;
        return null;
    }


    private void initializeTradeBankAndPortButton() {
        tradeBank = new JButton(messages.getString("tradeWithBankOrPort"));
        tradeBank.addActionListener(this::actionPerformed);
    }

    private void displayFrame() {
        frame.pack();
        frame.setVisible(true);
    }

    private void initializeTradeAwayButton() {
        tradeAway = new JButton(messages.getString("tradeWithPlayerLabel"));
        tradeAway.addActionListener(this::actionPerformed);
    }

    private void initializeDiscardButton() {
        discard = new JButton(messages.getString("discardLabel"));
        discard.addActionListener(this::actionPerformed);
    }

    private void initializeMainFields() {
        resourcesToTradeAway = new ArrayList<>();
        frame = new JFrame();
        frame.getContentPane().setLayout(new GridLayout());
    }

    public void initializeLabels(boolean isDiscard) {
        if (isDiscard) label = new JLabel(messages.getString("discardWithColon"));
        else    label = new JLabel(messages.getString("tradeAwayWithColon"));
        initializeResourceLabels();
    }

    private void initializeResourceLabels() {
        brickLabel = new JLabel(messages.getString("brickAmountLabel"));
        grainLabel = new JLabel(messages.getString("grainAmountLabel"));
        lumberLabel = new JLabel(messages.getString("lumberAmountLabel"));
        woolLabel = new JLabel(messages.getString("woolAmountLabel"));
        oreLabel = new JLabel(messages.getString("oreAmountLabel"));
    }

    private void initializeTextFields() {
        brick = new JTextField();
        grain = new JTextField();
        lumber = new JTextField();
        wool = new JTextField();
        ore = new JTextField();
    }

    private void initializeTradeOutGUI(boolean discardMode) {
        enterResources = new JPanel();
        enterResources.setLayout(new BoxLayout(enterResources, BoxLayout.Y_AXIS));
        addComponents(discardMode);
    }

    private void addComponents(boolean discardMode) {
        if(discardMode){
            addDiscardComponents();
        }else{
            addTradeComponents();
        }
    }

    private void addDiscardComponents() {
        JComponent[] componentsToAdd =
            new JComponent[]{label, brickLabel, brick, grainLabel, grain, lumberLabel, lumber,
                woolLabel, wool, oreLabel, ore, discard};
        for (Component component : componentsToAdd) enterResources.add(component);
    }

    private void addTradeComponents() {
        JComponent[] componentsToAdd =
            new JComponent[]{label, brickPanel, grainPanel, lumberPanel, woolPanel, orePanel,
                tradeAway, tradeBank};
        for (Component component : componentsToAdd) enterResources.add(component);
    }

    public void resetTextFields() {
        brick.setText(null);
        grain.setText(null);
        lumber.setText(null);
        wool.setText(null);
        ore.setText(null);
    }

    public void initializeTradeInGUI() {
        label.setText(messages.getString("tradeForLabel"));
        removeTradeAwayAndBankButton();
        initializeTradeForButton();
        enterResources.add(tradeFor);
        refreshFrame();
    }

    private void removeTradeAwayAndBankButton() {
        enterResources.remove(tradeAway);
        enterResources.remove(tradeBank);
    }

    private void initializeTradeForButton() {
        tradeFor = new JButton(messages.getString("acceptLabel"));
        tradeFor.addActionListener(this::actionPerformed);
    }

    public void initializeTradeOptionsGUI() {
        int numPlayers = players.length;
        initializeJOptionsGUIComponents(numPlayers);
        addPlayersToTradePanel(numPlayers);
        frame.add(tradeOptions);
        refreshFrame();
    }

    private void addPlayersToTradePanel(int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            if (!manager.isInTurnPlayer(players[i])) {
                addPlayerToTradePanel(i);
            };
        }
    }

    private void refreshFrame() {
        frame.revalidate();
        frame.repaint();
    }

    private void initializeJOptionsGUIComponents(int numPlayers) {
        yesButtons = new JButton[numPlayers];
        noButtons = new JButton[numPlayers];
        tradeOptions = new JPanel();
        tradeOptionsLabel = new JLabel(messages.getString("tradeOptionsLabel"));
        tradeOptions.add(tradeOptionsLabel);
    }

    private void addPlayerToTradePanel(int i) {
        JPanel tempPanel = new JPanel();

        tempPanel.add(new JLabel(players[i].getPlayerName()));
        addPlayerYesButton(i, tempPanel);
        addPlayerNoButton(i, tempPanel);

        tradeOptions.add(tempPanel);
    }

    private void addPlayerNoButton(int i, JPanel tempPanel) {
        JButton noButton = new JButton(messages.getString("noLabel"));
        noButton.addActionListener(this::actionPerformed);
        noButtons[i] = noButton;
        tempPanel.add(noButton);
    }

    private void addPlayerYesButton(int i, JPanel tempPanel) {
        JButton yesButton = new JButton(messages.getString("yesLabel"));
        yesButton.addActionListener(this::actionPerformed);
        disableYesButtonForInvalidTradees(i, yesButton);
        yesButtons[i] = yesButton;
        tempPanel.add(yesButton);
    }

    private void disableYesButtonForInvalidTradees(int i, JButton yesButton) {
        if (!validTradees[i]) {
            yesButton.setEnabled(false);
        }
    }

    private Collection<ResourceType> convertResourceFieldsToCollection() {
        String[] resourceAmounts =
            {brick.getText(), grain.getText(), lumber.getText(), wool.getText(), ore.getText()};
        ArrayList<ResourceType> resources = new ArrayList<>();

        addResourceAmountsToResources(resourceAmounts, resources);
        return resources;
    }

    private void addResourceAmountsToResources(String[] resourceAmounts,
        ArrayList<ResourceType> resources) {
        for (int i = 0; i < resourceAmounts.length; i++) {
            addResourceFromTextFieldToCollection(resourceAmounts, resources, i);
        }
    }

    private void addResourceFromTextFieldToCollection(String[] resourceAmounts,
        ArrayList<ResourceType> resources, int i) {
        int resourceAmount = getResourceAmountFromTextField(resourceAmounts[i]);

        if (resourceAmount < 0 || resourceAmount > MAX_SINGLE_RESOURCE) {
            displayInvalidResourceAmountMessageAndDisposeFrame();
        }
        addAmountOfResourceToCollection(resources, resourceOrder[i], resourceAmount);
    }

    private void displayInvalidResourceAmountMessageAndDisposeFrame() {
        displayInvalidResourceAmountMessage();
        frame.dispose();
    }

    private void displayInvalidResourceAmountMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("invalidResourceAmount"),
            messages.getString("invalidInput"), JOptionPane.ERROR_MESSAGE);
    }

    private void addAmountOfResourceToCollection(ArrayList<ResourceType> resources,
        ResourceType resourceType, int resourceAmount) {
        for (int j = 0; j < resourceAmount; j++) {
            resources.add(resourceType);
        }
    }

    @SuppressWarnings("methodlength")
    private int getResourceAmountFromTextField(String resourceAmounts) {
        int resourceAmount;
        try {
            resourceAmount = Integer.parseInt(resourceAmounts);
        } catch (NumberFormatException e) {
            resourceAmount = 0;
        }
        return resourceAmount;
    }

    private Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getPlayerName().equals(name))    return player;
        }
        return players[0];
    }

    private boolean[] getValidPeopleToTradeWith(Collection<ResourceType> resourcesDesired) {
        boolean[] validPeopleToTradeWith = new boolean[players.length];
        for (int i = 0; i < players.length; i++) {
            setValidPersonToTradeWith(resourcesDesired, validPeopleToTradeWith, i);
        }
        return validPeopleToTradeWith;
    }

    private void setValidPersonToTradeWith(Collection<ResourceType> resourcesDesired,
        boolean[] validPeopleToTradeWith, int i) {
        validPeopleToTradeWith[i] =
            players[i].hasResources(resourcesDesired) && !manager.isInTurnPlayer(players[i]);
    }

    @SuppressWarnings("methodlength")
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tradeAway) tradeAwayActionPerformed();
        else if (e.getSource() == discard)  discardActionPerformed();
        else if (e.getSource() == tradeFor) tradeForActionPerformed();
        else if (isYesButtonSelected(e))    yesButtonActionPerformed(e);
        else if (isNoButtonSelected(e))     noButtonActionPerformed(e);
        else if (e.getSource() == tradeBank)    tradeBankActionPerformed();
    }

    private boolean isNoButtonSelected(ActionEvent e) {
        return Arrays.asList(noButtons).contains(e.getSource());
    }

    private boolean isYesButtonSelected(ActionEvent e) {
        return Arrays.stream(yesButtons).anyMatch(e.getSource()::equals);
    }

    private void tradeBankActionPerformed() {
        resourcesToTradeAway = convertResourceFieldsToCollection();
        if (noResourcesSelected() || insufficientResourcesToTrade()) return;
        ResourceType resourceToTrade = resourcesToTradeAway.iterator().next();
        int numResource = Collections.frequency(resourcesToTradeAway, resourceToTrade);

        validResourcesAndAttemptTradeWithPortOrBank(resourceToTrade, numResource);
    }

    private void validResourcesAndAttemptTradeWithPortOrBank(ResourceType resourceToTrade,
        int numResource) {
        TradeRatio ratio = getCorrespondingTradeRatio(resourceToTrade);
        if (ensureResourceRatioAndSingleType(numResource, ratio)) return;
        attemptTradeWithPortOrBank(resourceToTrade, numResource, ratio);
        frame.dispose();
    }

    private boolean ensureResourceRatioAndSingleType(int numResource, TradeRatio ratio) {
        return moreThanOneResourceType(numResource) || notAMultipleOfRatio(numResource, ratio);
    }

    private void attemptTradeWithPortOrBank(ResourceType resourceToTrade, int numResource,
        TradeRatio relevantRatio) {
        try {
            getResourceAndTradeWithBank(resourceToTrade, numResource, relevantRatio);
        } catch(IllegalArgumentException e){
            frame.dispose();
        }
    }

    private void getResourceAndTradeWithBank(ResourceType resourceToTrade, int numResource,
        TradeRatio relevantRatio) {
        ResourceType taking = CardGUI.getResourceInput(
            messages.getString("tradeWithBankMessage"), messages.getString("bankPortTradeTitle"));
        tradeWithPortOrBank(resourceToTrade, numResource, relevantRatio, taking);
    }

    @SuppressWarnings("methodlength")
    private void tradeWithPortOrBank(ResourceType resourceToTrade, int numResource,
        TradeRatio relevantRatio, ResourceType taking) {
        switch(relevantRatio){
            case FOUR_TO_ONE: tradeWithBank(resourceToTrade, numResource, taking);
            break;
            case THREE_TO_ONE: tradeThreeToOne(resourceToTrade, numResource, taking);
            break;
            case TWO_TO_ONE: tradeTwoToOne(resourceToTrade, numResource, taking);
            break;
            default: frame.dispose();
        }
    }

    private void tradeWithBank(ResourceType resourceToTrade, int numResource, ResourceType taking) {
        if (!manager.playerTradeWithBank(resourceToTrade, taking, numResource)) {
            displayInsufficientResourcesBankMessageAndDisposeFrame();
        };
    }

    private void tradeThreeToOne(ResourceType resourceToTrade, int numResource,
        ResourceType taking) {
        if (!manager.playerTradeWithPort(new Port(PortTradeRatio.THREE_TO_ONE,
            resourceToTrade), resourceToTrade, taking, numResource)) {
            displayInsufficientResourcesBankMessageAndDisposeFrame();
        }
    }

    private void tradeTwoToOne(ResourceType resourceToTrade, int numResource, ResourceType taking) {
        if (!manager.playerTradeWithPort(new Port(PortTradeRatio.TWO_TO_ONE,
            resourceToTrade), resourceToTrade, taking, numResource)) {
            displayInsufficientResourcesBankMessageAndDisposeFrame();
        }
    }

    private boolean notAMultipleOfRatio(int numResource, TradeRatio relevantRatio) {
        if(!isMultipleOfRatio(relevantRatio, numResource)){
            displayNotAMultipleOfTradeRatioMessageAndDisposeFrame();
            return true;
        }
        return false;
    }

    private void displayNotAMultipleOfTradeRatioMessageAndDisposeFrame() {
        notAMultipleOfTradeRatioMessage();
        frame.dispose();
    }

    private boolean moreThanOneResourceType(int numResource) {
        if (numResource != resourcesToTradeAway.size()) {
            displayNoMoreThanOneResourceTypeSelectedForBankMessageAndDisposeFrame();
            return true;
        }
        return false;
    }

    private void displayNoMoreThanOneResourceTypeSelectedForBankMessageAndDisposeFrame() {
        moreThanOneResourceTypeSelectedForBankMessage();
        frame.dispose();
    }

    private boolean noResourcesSelected() {
        if (resourcesToTradeAway.isEmpty()) {
            displayNoResourcesSelectedMessageAndDisposeFrame();
            return true;
        }
        return false;
    }

    private void displayNoResourcesSelectedMessageAndDisposeFrame() {
        noResourcesSelectedMessage();
        frame.dispose();
    }

    private boolean insufficientResourcesToTrade() {
        if (!manager.currentPlayerHasSufficientResources(resourcesToTradeAway)) {
            displayInsufficientResourcesMessageAndDisposeFrame();
            return true;
        }
        return false;
    }

    private void displayInsufficientResourcesMessageAndDisposeFrame() {
        displayInsufficientResourcesMessage();
        frame.dispose();
    }

    private void displayInsufficientResourcesBankMessageAndDisposeFrame() {
        displayInsufficientResourcesBankMessage();
        frame.dispose();
    }

    private void displayInsufficientResourcesMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("doNotOwnResourcesMessage"),
            messages.getString("invalidInput"), JOptionPane.ERROR_MESSAGE);
    }

    private void displayInsufficientResourcesBankMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("insufficientResourcesBankMessage"),
            messages.getString("invalidInput"), JOptionPane.ERROR_MESSAGE);
    }


    private boolean isMultipleOfRatio(TradeRatio ratio, int numResource){
        switch(ratio){
            case THREE_TO_ONE: return isMultipleOf(numResource, MULTIPLE_OF_THREE);
            case TWO_TO_ONE: return isMultipleOf(numResource,2);
            default: return isMultipleOf(numResource,MULTIPLE_OF_FOUR);
        }
    }

    private boolean isMultipleOf(int numResource, int baseNumber) {
        if(numResource == 0) return true;
        if( numResource < 0) return false;
        return isMultipleOf(numResource - baseNumber, baseNumber);
    }


    @SuppressWarnings("methodlength")
    private TradeRatio getCorrespondingTradeRatio(ResourceType resource){
        switch(resource){
            case BRICK: return brickTradeRatio;
            case LUMBER: return lumberTradeRatio;
            case WOOL: return woolTradeRatio;
            case ORE: return oreTradeRatio;
            case GRAIN: return grainTradeRatio;
            default: return brickTradeRatio;
        }
    }

    private void discardActionPerformed() {
        resourcesToTradeAway = convertResourceFieldsToCollection();
        if (discardCheckPlayerHasResources())   return;
        resetTextFields();
        if (!discardResources())  return;
        frame.dispose();
    }

    private boolean discardCheckPlayerHasResources() {
        if (!players[0].hasResources(resourcesToTradeAway)) {
            displayInsufficientResourcesMessageAndRefreshFrame();
            return true;
        }
        return false;
    }

    private void displayInsufficientResourcesMessageAndRefreshFrame() {
        insufficientResourcesMessage(numDiscard);
        refreshFrame();
    }

    private boolean discardResources() {
        if (incorrectAmountToDiscard()) return false;
        return attemptDiscardResources();
    }

    private boolean attemptDiscardResources() {
        if (manager.playerDiscardResources(players[0], resourcesToTradeAway)) {
            return displayDiscardSuccessMessageAndReturnTrue();
        } else {
            return displayDiscardFailMessageAndReturnFalse();
        }
    }

    private boolean displayDiscardFailMessageAndReturnFalse() {
        discardFailMessage();
        return false;
    }

    private boolean displayDiscardSuccessMessageAndReturnTrue() {
        discardSuccessMessage();
        return true;
    }

    private boolean incorrectAmountToDiscard() {
        if (resourcesToTradeAway.size() != players[0].getResources().size() / 2) {
            insufficientResourcesMessage(numDiscard);
            return true;
        }
        return false;
    }

    private void tradeAwayActionPerformed() {
        resourcesToTradeAway = convertResourceFieldsToCollection();
        if (insufficientResourcesToTrade()) return;
        resetTextFields();
        initializeTradeInGUI();
    }

    private void tradeForActionPerformed() {
        resourcesDesired = convertResourceFieldsToCollection();
        validTradees = getValidPeopleToTradeWith(resourcesDesired);
        frame.remove(enterResources);
        refreshFrame();
        initializeTradeOptionsGUI();
    }

    private void yesButtonActionPerformed(ActionEvent e) {
        JButton button = getSelectedYesButton(e);
        tradeWithPlayerFromButton(button);
        frame.dispose();
    }

    private void tradeWithPlayerFromButton(JButton button) {
        JLabel playerLabel = (JLabel) button.getParent().getComponent(0);
        String playerName = playerLabel.getText();
        Player toTradeWith = getPlayerByName(playerName);

        manager.playerTrade(toTradeWith, resourcesToTradeAway, resourcesDesired);
    }

    private JButton getSelectedYesButton(ActionEvent e) {
        int index = Arrays.asList(yesButtons).indexOf(e.getSource());
        JButton button = yesButtons[index];
        return button;
    }

    private void noButtonActionPerformed(ActionEvent e) {
        int index = Arrays.asList(noButtons).indexOf(e.getSource());
        JButton button = noButtons[index];
        tradeOptions.remove(button.getParent());
        if (tradeOptions.getComponents().length == 1)   nobodyWantsToTradeAndDisposeFrame();
        refreshFrame();
    }

    private void nobodyWantsToTradeAndDisposeFrame() {
        displayNobodyWantsToTradeMessage();
        frame.dispose();
    }

    private void displayNobodyWantsToTradeMessage() {
        JOptionPane.showMessageDialog(null, messages.getString("nobodyWantedToTradeMessage"),
            messages.getString("invalidMessage"), JOptionPane.ERROR_MESSAGE);
    }

    public boolean isShowing() {
        return frame.isShowing();
    }

    public void disposeFrame(){
        frame.dispose();
        active = false;
    }

    public boolean isActive(){
        return active;
    }

}

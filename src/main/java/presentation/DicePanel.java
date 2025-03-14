package presentation;

import domain.DiceManager;
import domain.GameManager;
import domain.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class DicePanel extends JPanel {

    private static final int SMALL_INSET_PADDING = 10;
    private static final int LARGE_INSET_PADDING = 40;
    private static final int DICE_SMALL_PADDING = 30;
    private static final int DICE_LARGE_PADDING = 50;

    private static final int DICE_IMAGE_WIDTH = 60;
    private static final int DICE_IMAGE_HEIGHT = 60;

    private static final int DICE_GRIDBAG_X_POSITION = 2;
    private static final int DICE_GRIDBAG_Y_POSITION = 2;

    private static final int DICE_BOTTOM_GRID_BAG_WIDTH = 3;

    private static final int PLAYER_SMALL_INSET_PADDING = 20;
    private static final int PLAYER_LARGE_INSET_PADDING = 60;
    private GameManager gameManager;
    Player[] players;
    ResourceBundle messages;
    ArrayList<JButton> buttonsToEnable  = new ArrayList<>();
    private JLabel[] diceLabels;
    public JButton rollDiceButton;

    private DiceManager diceManager;


    protected DicePanel(Player[] players, GameManager gameManager, Locale locale) {
        initializeSetFields(players, gameManager, locale);
        this.setLayout(new GridBagLayout());
        addDiceButtonAndLabels();
        resetButtons();
        this.setVisible(true);
    }

    private void addDiceButtonAndLabels() {
        addDiceButton();
        rollDiceButton.addActionListener(e -> rollDiceButtonAction());
        diceLabels = new JLabel[diceManager.getNumDice()];
    }

    private void initializeSetFields(Player[] players, GameManager gameManager, Locale locale) {
        this.players = players;
        messages = ResourceBundle.getBundle("messages", new Locale(locale.getLanguage()));
        this.gameManager = gameManager;
        this.diceManager = gameManager.diceManager;
    }

    private void addDiceButton() {
        GridBagConstraints constraints = new GridBagConstraints();
        setDiceButtonGridConstraints(constraints);
        rollDiceButton = new JButton(messages.getString("rollDiceLabel"));
        setDiceButtonWeightAndInsetsConstraints(constraints);
        this.add(rollDiceButton, constraints);
    }

    private void setDiceButtonWeightAndInsetsConstraints(GridBagConstraints constraints) {
        constraints.weightx = 1;
        constraints.insets = new Insets(PLAYER_SMALL_INSET_PADDING, PLAYER_LARGE_INSET_PADDING,
            PLAYER_SMALL_INSET_PADDING, PLAYER_LARGE_INSET_PADDING);
    }

    private void setDiceButtonGridConstraints(GridBagConstraints constraints) {
        constraints.gridx = DICE_GRIDBAG_X_POSITION;
        constraints.gridy = DICE_GRIDBAG_Y_POSITION;
        constraints.gridwidth = DICE_BOTTOM_GRID_BAG_WIDTH;
        constraints.gridheight = 1;
    }

    private void enableButtons() {
        rollDiceButton.setEnabled(false);
        rollDiceButton.setText(String.valueOf(gameManager.getCurrentDiceRoll()));
        for (JButton button : buttonsToEnable) {
            button.setEnabled(true);
        }
    }

    public void addButtonsToEnable(JButton[] buttons) {
        for (JButton button : buttons) {
            buttonsToEnable.add(button);
        }
    }

    protected void resetButtons() {
        for (int i = 0; i < diceLabels.length; i++) updateDiceImage(0, i);
        rollDiceButton.setText(messages.getString("rollDiceLabel"));
        rollDiceButton.setEnabled(true);
        diceManager.invalidateDice();
    }

    private void updateDiceImage(int diceRoll, int dicePosition) {
        if (diceRoll < DiceManager.DICE_MIN_ROLL || diceRoll > DiceManager.DICE_MAX_ROLL) {
            return;
        }
        if (dicePosition < 0 || dicePosition > 1)   return;
        scaleAndSetDiceImage(diceRoll, dicePosition);
    }

    private void scaleAndSetDiceImage(int diceRoll, int dicePosition) {
        ImageIcon resized = getDiceImageIcon(diceRoll);

        addDiceLabelsIfNull(dicePosition, resized);
        diceLabels[dicePosition].setIcon(resized);
    }

    private ImageIcon getDiceImageIcon(int diceRoll) {
        BufferedImage dice = loadDiceImage(diceRoll);
        Image image = new ImageIcon(dice).getImage();
        ImageIcon resized = new ImageIcon(
            image.getScaledInstance(DICE_IMAGE_WIDTH, DICE_IMAGE_HEIGHT, Image.SCALE_SMOOTH));
        return resized;
    }

    private void addDiceLabelsIfNull(int dicePosition, ImageIcon resized) {
        if (diceLabels[dicePosition] == null) {
            GridBagConstraints constraints = generateDiceConstraints(dicePosition);
            diceLabels[dicePosition] = new JLabel(resized);
            this.add(diceLabels[dicePosition], constraints);
        }
    }

    private BufferedImage loadDiceImage(int diceRoll) {
        BufferedImage dice;
        dice = tryLoadDiceImage(diceRoll);
        return dice;
    }

    @SuppressWarnings("methodlength")
    private BufferedImage tryLoadDiceImage(int diceRoll) {
        BufferedImage dice;
        try {
            dice = getBufferedImage(diceRoll);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dice;
    }

    private BufferedImage getBufferedImage(int diceRoll) throws IOException {
        BufferedImage dice;
        dice = ImageIO.read(
            Path.of("src", "main", "resources", "DiceFaces", "Dice" + diceRoll + ".png")
            .toFile());
        return dice;
    }

    private void rollDiceButtonAction() {

        diceManager.rollAllDice();
        updateDiceImages();
        enableButtons();
    }

    public void rollUniqueDiceButtonAction(){

    }

    public void updateDiceImages() {
        int[] rolls = diceManager.getIndividualDiceRolls();
        for (int i = 0; i < diceLabels.length; i++) {
            updateDiceImage(rolls[i], i);
        }
    }

    private GridBagConstraints generateDiceConstraints(int dicePosition) {
        GridBagConstraints constraints = new GridBagConstraints();
        setDiceConstraints(dicePosition, constraints);
        return constraints;
    }

    private void setDiceConstraints(int dicePosition, GridBagConstraints constraints) {
        constraints.weightx = 0.5;
        int left = adjustLeftDicePadding(dicePosition);
        setDiceGridConstraints(dicePosition, constraints);
        setDiceInsetAndAnchorConstraints(constraints, left);
    }

    private void setDiceInsetAndAnchorConstraints(GridBagConstraints constraints, int left) {
        constraints.insets =
            new Insets(SMALL_INSET_PADDING, left, SMALL_INSET_PADDING, DICE_SMALL_PADDING);
        constraints.anchor = GridBagConstraints.CENTER;
    }

    private void setDiceGridConstraints(int dicePosition, GridBagConstraints constraints) {
        constraints.gridx = 1 + dicePosition * 2;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
    }

    private int adjustLeftDicePadding(int dicePosition) {
        int left;
        if (dicePosition == 0)  left = DICE_LARGE_PADDING;
        else    left = DICE_SMALL_PADDING;
        return left;
    }
}

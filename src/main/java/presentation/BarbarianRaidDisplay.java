package presentation;

import domain.GameManager;

import javax.swing.*;
import java.awt.*;

public class BarbarianRaidDisplay {

    public JPanel panel;
    private JLabel progressLabel;
    public GameManager gameManager;
    private int threshold;

    public BarbarianRaidDisplay(GameManager manager) {
        this.gameManager = manager;
        this.threshold = gameManager.getBarbarianAttackThreshold();
        initializePanel();
        updateDisplay();
    }

    private void initializePanel() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progressLabel = new JLabel();
        panel.add(progressLabel);
    }

    public void updateDisplay() {
        int currentProgress = gameManager.getSevensRolledCounter();
        progressLabel.setText(String.format("Barbarian Progress: %d / %d", currentProgress, threshold));
        panel.revalidate();
        panel.repaint();
    }
}
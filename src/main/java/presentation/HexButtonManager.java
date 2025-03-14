package presentation;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static presentation.BoardDisplay.*;


//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission
public class HexButtonManager {

    private static final int BUTTON_SIZE = 81;
    private static final int THREE = 3;

    ArrayList<JButton> hexButtons;

    private int selectedHex;

    public HexButtonManager() {
        hexButtons = new ArrayList<>();
        selectedHex = -1;
    }

    public void enableHexButtons(boolean setTo) {
        for (JButton hexButton : hexButtons) {
            hexButton.setEnabled(setTo);
            hexButton.setVisible(setTo);
        }
    }

    public JButton createHexButton(Point2D point, int id) {
        JButton hexButton = new HexButton("");
        hexButtonSetBounds(point, hexButton);
        setButtonContents(hexButton);
        addButtonActionListener(id, hexButton);
        return hexButton;
    }

    private void hexButtonSetBounds(Point2D point, JButton hexButton) {
        int centerX = (int) (X_CENTER_MULT * point.getX() + X_CENTER_OFFSET);
        int centerY = (int) (Y_CENTER_MULT * point.getY() + Y_CENTER_OFFSET);
        // Calculate the size of the hexagon
        // Calculate the bounds for the JButton
        int width = BUTTON_SIZE * 2; // Twice the radius for the width
        int height = (int) (Math.sqrt(THREE) * BUTTON_SIZE);
        // Height of an equilateral triangle

        hexButton.setBounds(centerX - width / 2, centerY - height / 2, width, height);
    }

    private static void setButtonContents(JButton hexButton) {
        hexButton.setContentAreaFilled(false);
        hexButton.setFocusPainted(false);
        hexButton.setVisible(false);
    }

    private void addButtonActionListener(int id, JButton hexButton) {
        HexActionListener listener = new HexActionListener(id, this);
        hexButton.addActionListener(listener);
        hexButtons.add(hexButton);
    }


    public void setSelectedHex(int selected) {
        selectedHex = selected;
    }

    public int getSelectedHex() {
        int temp = selectedHex;
        selectedHex = -1;
        return temp;
    }


}

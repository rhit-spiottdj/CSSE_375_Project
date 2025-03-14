package presentation;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static presentation.BoardDisplay.*;

//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission
public class IntersectionButtonManager {

    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    protected ArrayList<JButton> intersectionButtons;

    private int selectedIntersection;

    public IntersectionButtonManager() {
        intersectionButtons = new ArrayList<>();
        selectedIntersection = -1;
    }

    public void enableIntersectionButtons(boolean setTo) {
        for (JButton intersectionButton : intersectionButtons) {
            intersectionButton.setEnabled(setTo);
        }
    }

    public JButton createIntersectionButton(Point2D point, int id) {
        JButton intersectionButton = new JButton("");
        intersectionButtonSetup(point, id, intersectionButton);
        return intersectionButton;
    }

    private void intersectionButtonSetup(Point2D point, int id, JButton intersectionButton) {
        intersectionButtonSetContent(point, intersectionButton);
        intersectionButtonSetupActionListener(id, intersectionButton);
        intersectionButtons.add(intersectionButton);
        intersectionButton.setEnabled(false);
    }

    private void intersectionButtonSetupActionListener(int id, JButton intersectionButton) {
        IntersectionActionListener listener = new IntersectionActionListener(id, this);
        intersectionButton.addActionListener(listener);
    }

    private void intersectionButtonSetContent(Point2D point, JButton intersectionButton) {
        intersectionButtonSetBounds(point, intersectionButton);
        intersectionButton.setVisible(true);
        intersectionButton.setContentAreaFilled(false);
    }

    private void intersectionButtonSetBounds(Point2D point, JButton intersectionButton) {
        intersectionButton.setBounds(
            (int) ((X_CENTER_MULT * point.getX() + X_CENTER_OFFSET - BUTTON_WIDTH / 2)),
            (int) ((Y_CENTER_MULT * point.getY() + Y_CENTER_OFFSET - BUTTON_HEIGHT / 2)),
            BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public void setSelectedIntersection(int selected) {
        selectedIntersection = selected;
    }

    public int getSelectedIntersection() {
        int temp = selectedIntersection;
        selectedIntersection = -1;
        return temp;
    }
}


package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission
public class IntersectionActionListener implements ActionListener {
    private int intersectionIndex;
    private IntersectionButtonManager manager;

    protected IntersectionActionListener(int intersectionIndex, IntersectionButtonManager manager) {
        this.intersectionIndex = intersectionIndex;
        this.manager = manager;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        manager.setSelectedIntersection(intersectionIndex);
    }
}

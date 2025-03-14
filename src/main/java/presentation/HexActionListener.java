package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission
public class HexActionListener implements ActionListener {


    private int hexIndex;
    private HexButtonManager manager;

    protected HexActionListener(int hexIndex, HexButtonManager manager){
        this.hexIndex = hexIndex;
        this.manager = manager;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        manager.setSelectedHex(hexIndex);
    }
}

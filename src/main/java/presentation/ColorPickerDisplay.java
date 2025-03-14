package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission
public class ColorPickerDisplay {

    public Color[] colorArray;
    private int colorsPicked;

    private final ResourceBundle messages;

    public ColorPickerDisplay(int numPlayers, Locale locale) {
        colorsPicked = 0;
        colorArray = new Color[numPlayers];
        messages = ResourceBundle.getBundle("messages", new Locale(locale.getLanguage()));
    }



    public boolean colorArrayContains(Color color){
        for(Color knownColor: colorArray){
            if(color.equals(knownColor))    return true;
        }
        return false;
    }



    public Color chooseColor(){
        Color selectedColor = getUserColor();
        colorArray[colorsPicked] = selectedColor;
        colorsPicked++;
        return selectedColor;
    }

    private Color getUserColor() {
        Color selectedColor = ensureColorSelectMessage();
        while(colorArrayContains(selectedColor)){
            selectedColor = ensureColorSelectionErrorMessage();
        }
        return selectedColor;
    }

    private Color ensureColorSelectionErrorMessage() {
        Color color = null;
        while(color == null){
            color =  colorSelectionErrorMessage();
        }
        return color;
    }

    private Color ensureColorSelectMessage() {
        Color color = null;
        while(color == null){
            color =  colorSelectMessage();
        }
        return color;
    }

    private Color colorSelectMessage() {
        Color selectedColor = JColorChooser.showDialog(null, messages.getString(
            "colorSelectionMessage"), Color.RED);
        return selectedColor;
    }

    private Color colorSelectionErrorMessage() {
        Color selectedColor;
        JOptionPane.showMessageDialog(null, messages.getString("colorSelectionError"));
        selectedColor = JColorChooser.showDialog(null, messages.getString(
            "colorSelectionMessage"), Color.RED);
        return selectedColor;
    }

}

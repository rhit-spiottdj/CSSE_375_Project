package presentation;

import domain.DevelopmentCards;

import javax.swing.*;

public class CardButton extends JButton {

    DevelopmentCards devCard;

    public CardButton(String name, DevelopmentCards devCard){
        super(name);
        this.devCard = devCard;
    }

    public DevelopmentCards getCardType(){
        return devCard;
    }
}

package presentation;


public class GameRunner {

    public static void main(String[] args) {
        System.out.println("The Start of Catan.");
        boolean isQuickSetup = GameDisplay.chooseQuickSetup();
        GameDisplay game = new GameDisplay(isQuickSetup);
        game.boardFrame.setVisible(true);
        game.runGame();
    }
}

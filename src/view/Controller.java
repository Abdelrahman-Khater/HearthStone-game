package view;

public class Controller {

	private  mainMenu game;
	
	public Controller() {
		game=new mainMenu();
	}
	
	public  mainMenu getGameView() {
		return game;
	}
	public static void main(String [] args) {
		new Controller();
	}
}

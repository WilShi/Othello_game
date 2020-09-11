package ca.utoronto.utm.othello.model;

public class GameModeSingleton {
	
	private static GameModeSingleton instance = null;
	
	private String gameMode = "Human VS Human";
	
	private GameModeSingleton() {
		super();
	}
	
	/**
	 *  returns the instance of the gameMode singleton
	 *  @returns instance
	 */
	public static synchronized GameModeSingleton getInstance() {
		if(instance == null) {
			instance = new GameModeSingleton();
		}
		return instance;
	}
	
	/**
	 *  returns the string representation of the gameMode
	 *  @returns this.gameMode
	 */
	public String getGameMode() {
		return this.gameMode;
	}
	
	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}
	
}

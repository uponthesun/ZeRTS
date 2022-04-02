package zerts;


import gamemechanics.Engine;
import gamemechanics.GameInstance;
import gamemechanics.GameLoader;
import gamemechanics.HumanPlayer;
import guishitz.*;
import libraries.ImageLibrary;



/*ZeRTS
 * Main Class
 * Created February 20, 2008 
 * Finished: (not yet)
 * Does:
 *	(nothing)
 */
public class ZeRTS {
	public static int TILE = 80;
	public static double rt2 = Math.sqrt(2);
	public static GameInstance currentGame;
	
	public static void main (String [] args){
		LoadScreenFrame loadScreen = new LoadScreenFrame();
		ImageLibrary.setComponent(loadScreen);
		currentGame = GameLoader.makeNewGame("test5.map", 0);
		Engine e = new Engine(currentGame);
		loadScreen.dispose();
		new guishitz.GameWindow(currentGame, (HumanPlayer)currentGame.players[0]);
		e.startEngine();
	}
}

package gamemechanics;


import ingameobjects.Terrain;
import ingameobjects.Thing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import libraries.ImageLibrary;
import libraries.UnitLibrary;
import mechanicsobjects.Tile;
import mechanicsobjects.Tileset;
import java.awt.Color;


public class GameLoader {

	static ArrayList sets;

	private static void initialize(){

		sets = new ArrayList();
		Tileset chickens = new Tileset();
		Tileset lostGarden = new Tileset();

		chickens.add(ImageLibrary.mud, 'm');
		chickens.add(ImageLibrary.dirt, 'd');
		chickens.add(ImageLibrary.grass, 'g');
		sets.add(chickens);
		lostGarden.add(ImageLibrary.grass2, 'g');
		lostGarden.add(ImageLibrary.leaves, 'l');
		lostGarden.add(ImageLibrary.bits, 'b');
		lostGarden.add(ImageLibrary.funky, 'f');
		lostGarden.add(ImageLibrary.marble, 'm');
		sets.add(lostGarden);
		
	}

	public static GameInstance makeNewGame(String file, int numComps){
		initialize();

		GameInstance newGame = new GameInstance();
		newGame.players = new Player[1 + numComps];
		HumanPlayer dude = new HumanPlayer(newGame);
		dude.color = Color.PINK;
		newGame.players[0] = dude;	
		for(int n = 1; n < newGame.players.length; n++){
			newGame.players[n] = new ComputerPlayer();
		}
		ImageLibrary.loadAll();
		newGame = loadMapFile(file, newGame);
	
		return newGame;
	}
	
	private static GameInstance loadMapFile(String file, GameInstance newGame){
		Tile[][] newMap = null;
		try{
			BufferedReader inputBuffer;
//			if (file.indexOf(".newMap")!=-1)
				inputBuffer = new BufferedReader (new FileReader (file));
//			else
//				inputBuffer = new BufferedReader (new FileReader (file+".newMap"));
			/*BufferedReader inputBuffer = new BufferedReader (new FileReader (file+".newMap"));*/
			String current;
			int x = 0;
			int y = 0;
			int p = 0;
			double ex, why;
			Tileset set = null;
			while (inputBuffer.ready()){
				current = inputBuffer.readLine();
				char first = current.charAt(0);
				switch (first){
				case '$':
					break;
				case 'x':
					x = Integer.parseInt(current.substring(current.indexOf(" ")+1));
					//System.out.println(x);
					break;
				case 'y':
					y = Integer.parseInt(current.substring(current.indexOf(" ")+1));
					//System.out.println(y);
					newMap = new Tile[x][y];
					//ZeRTS.TILE = 400/newMap.length;
					break;
				case 'T':
					set = (Tileset)sets.get(Integer.parseInt(current.substring(current.indexOf(" ")+1)));
					System.out.println(set);
					break;
				case '%':
					for (int l= 0; l < x; l++){
						current = current.substring(current.indexOf(" ")+1);
						newMap[l][p] = new Tile(set.get(current.charAt(0)));
					}
					p++;
					break;
				case 'b':
					if(-1!=current.indexOf("boulder")){
						current = current.substring(current.indexOf(" ")+1);
						ex = Double.parseDouble(current.substring(0, current.indexOf(" ")));
						current = current.substring(current.indexOf(" ")+1);
						why = Double.parseDouble(current);
						newGame.addThing(new Terrain("boulder", ex, why, ImageLibrary.boulder));
						}
					else if (-1!=current.indexOf("bullseye")){
						current = current.substring(current.indexOf(" ")+1);
						ex = Double.parseDouble(current.substring(0, current.indexOf(" ")));
						current = current.substring(current.indexOf(" ")+1);
						why = Double.parseDouble(current.substring(0, current.indexOf(" ")));
						current = current.substring(current.indexOf(" ")+1);
						int playerIndex = Integer.parseInt(current);
						Thing newThing = UnitLibrary.bullseye(newGame.players[playerIndex], ex, why);						
						newGame.addThing(newThing);
					}
					break;
				case 'c':
					current = current.substring(current.indexOf(" ")+1);
					ex = Double.parseDouble(current.substring(0, current.indexOf(" ")));
					current = current.substring(current.indexOf(" ")+1);
					why = Double.parseDouble(current.substring(0, current.indexOf(" ")));
					current = current.substring(current.indexOf(" ")+1);
					int playerIndex = Integer.parseInt(current);
					Thing newThing = UnitLibrary.chicken(newGame.players[playerIndex], ex, why);
					newGame.addThing(newThing);
					break;
				case 'a':
					current = current.substring(current.indexOf(" ")+1);
					ex = Double.parseDouble(current.substring(0, current.indexOf(" ")));
					current = current.substring(current.indexOf(" ")+1);
					why = Double.parseDouble(current.substring(0, current.indexOf(" ")));
					current = current.substring(current.indexOf(" ")+1);
					playerIndex = Integer.parseInt(current);
					newThing = UnitLibrary.ant(newGame.players[playerIndex], ex, why);
					newGame.addThing(newThing);
					break;
				case 'q':
					current = current.substring(current.indexOf(" ")+1);
					ex = Double.parseDouble(current.substring(0, current.indexOf(" ")));
					current = current.substring(current.indexOf(" ")+1);
					why = Double.parseDouble(current.substring(0, current.indexOf(" ")));
					current = current.substring(current.indexOf(" ")+1);
					playerIndex = Integer.parseInt(current);
					newThing = UnitLibrary.queen(newGame.players[playerIndex], ex, why);
					newGame.addThing(newThing);
					break;
				default:
					break;
				}
			}	
		}
		catch (FileNotFoundException h) {
			System.out.println("The file was not found");
			System.exit(0);
		}//end of FNFE Catcher
		catch (IOException i) {
			System.out.println("there was an IO Exception");
			System.exit(0);
		}//end of IOE Catcher
		newGame.map = newMap;
		return newGame;
	}
}

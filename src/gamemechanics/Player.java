package gamemechanics;


import java.util.ArrayList;
import java.awt.Color;


public abstract class Player {

	public GameInstance game;
	public ArrayList units = new ArrayList();
	public ArrayList buildings = new ArrayList();
	
	public String type = "";
	public int food = 0;
	public Color color;
	
	public abstract void act();
	
}

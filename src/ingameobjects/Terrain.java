package ingameobjects;


import java.awt.*;

import zerts.ZeRTS;


public class Terrain extends Thing{

	public Terrain (String nomen, double ex, double why, Image pic){
		initialize(ex, why, pic, nomen, ZeRTS.TILE, ZeRTS.TILE, false);
		type = "terrain";
		attackable = false;
	}
	
	public void initialize(double ex, double why,
			Image pic, String nomen,
			double wide, double high, boolean selectable){
		x = ex;
		y = why;
		picture = pic;
		name = nomen;
		width = wide;
		height = high;

		
	}
}

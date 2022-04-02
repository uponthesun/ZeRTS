package libraries;

import ingameobjects.Unit;
import gamemechanics.Player;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import mechanicsobjects.AnimationCycle;


public class UnitLibrary {
	
	public static Unit makeUnit(String name, Player controller, double x, double y){
		if(name.equals("ant"))
			return ant(controller, x, y);
		if(name.equals("bullseye"))
			return bullseye(controller, x, y);
		if(name.equals("queen"))
			return queen(controller, x, y);

		return null;
	}
	
	public static Unit chicken (Player controller, double x, double y){
		Unit u = new Unit("chicken", controller, x, y, 12, 12, ImageLibrary.chicken, ImageLibrary.chicken, 
				.1, 100, 10, 50);
		return u;
	}
	public static Unit ant (Player controller, double x, double y){
		Unit u = new Unit("ant", controller,x, y, 40, 40, ImageLibrary.antL, ImageLibrary.antR,
				.1, 100, 10, 50);
		HitBoxLibrary.assignMeleeHitBox(u);
		return u;
	}
	public static Unit queen (Player controller, double x, double y){
		Unit u = new Unit("queen", controller,x, y, 100, 100, ImageLibrary.queenL, ImageLibrary.queenR,
				.1, 100, 10, 50);
		HitBoxLibrary.assignMeleeHitBox(u);
		return u;
	}
	public static Unit bullseye (Player controller, double x, double y){
		Unit u = new Unit("bullseye", controller,x, y, 40, 40, ImageLibrary.bullseyeAntL, ImageLibrary.bullseyeAntR,
				.1, 100,  10, 50);
		HitBoxLibrary.assignMeleeHitBox(u);
		return u;
	}
	/*public static Unit unitName
	 * 
	 * Unit(String nomen, double ex, double why, 
			double wide, double high, Image lpic, Image rpic, double speed, int health, int dmg, int cdown)
	}*/
}

package mechanicsobjects;
import java.util.ArrayList;

import ingameobjects.*;
import gamemechanics.*;

public abstract class HitBox {

	public Unit unit;
	public GameInstance env;
	
	public HitBox(Unit u){
		unit = u;
		env = u.controller.game;
		initialize();
	}

	public abstract void initialize();
	public Thing hitSomething(){
		
		ArrayList things = env.allThings;
		for(int n = 0; n < things.size(); n++){
			Thing t = (Thing)things.get(n);
			if(canHit(t))
				return t;
		}
		return null;
	}
	
	public abstract boolean canHit(Thing t);
	
}

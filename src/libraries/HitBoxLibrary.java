package libraries;
import mechanicsobjects.*;
import ingameobjects.*;
import java.util.ArrayList;
import zerts.ZeRTS;

public class HitBoxLibrary {

	public static HitBox assignMeleeHitBox(Unit u){
		HitBox h = new HitBox(u){
			
			double radius;

			public void initialize(){
				radius = (unit.width/ZeRTS.TILE)/2;
			}
			
			public boolean canHit(Thing t){
				double angle = unit.direction*Math.PI/4;
				double hX = unit.x + unit.width/(2*ZeRTS.TILE)+ radius*Math.cos(angle);
				double hY = unit.y + unit.height/(2*ZeRTS.TILE) - radius*Math.sin(angle);
				if(t.attackable && t!=unit){
					double tRadius = (t.width+t.height)/(4*ZeRTS.TILE);
					double tX = t.x + t.width/(2*ZeRTS.TILE);
					double tY = t.y + t.height/(2*ZeRTS.TILE);
					double dX = tX - hX;
					double dY = tY - hY;
					double distance = Math.sqrt(dX*dX + dY*dY);
					if(distance <= radius + tRadius)
						return true;
				}									
				return false;
			}
			

		};
		u.hBox = h;
		return h;
	}
	
}

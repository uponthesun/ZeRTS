package mechanicsobjects;
/*ZeRTS
 * Tile Class
 * Created March 3, 2008 
 * Finished: (not yet)
 * Does:
 *	
 */

import java.awt.*;

public class Tile {
	private Image picture;
	public Tile (Image setP){
		picture = setP;
	}
	public Tile (Tileset tiles, int num){
		picture = tiles.get(num);
	}
	public Image getImage(){
		return picture;
	}
}

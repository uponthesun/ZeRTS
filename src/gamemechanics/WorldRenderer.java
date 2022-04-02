package gamemechanics;
/*ZeRTS
 * Renderer class
 * Created March 5, 2008 
 * Finished: (not yet)
 * Does:
 *	
 */

import ingameobjects.Thing;
import ingameobjects.Unit;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import mechanicsobjects.Tile;

import zerts.ZeRTS;

import guishitz.GameWindow;


import java.util.ArrayList;


public class WorldRenderer{

	private static Image bufferImage;

	public static Image createMapImage (Component imageSource, Tile[][] tiles){

		int width = tiles.length*ZeRTS.TILE;
		int height = tiles[0].length*ZeRTS.TILE;

		Image mapImage = imageSource.createImage(width, height);
		Graphics g = mapImage.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.pink);
		g2.fill(new Rectangle2D.Double(0, 0, tiles.length*ZeRTS.TILE,tiles[0].length*ZeRTS.TILE));
		for(int r = 0; r < tiles[0].length; r++){
			for(int c = 0; c < tiles.length; c++){
				//System.out.println(tiles[c][r].getImage().getWidth(this));
				g2.drawImage(tiles[c][r].getImage(), c*ZeRTS.TILE, r*ZeRTS.TILE, imageSource);
			}	
		}	
		
		return mapImage;
	}

	public static Image drawWorld(GameInstance game, Image drawToThis, int cameraX, int cameraY){
		Graphics g = drawToThis.getGraphics();
		g.translate(-cameraX, -cameraY);

		g.drawImage(game.mapImage, 0, 0,  null);
		drawThings(game.allThings, g);
	
/*				if (temp2.isSelected()){
					if (temp2.getCurrentAction()!=null){
						ArrayList path = temp2.getCurrentAction().path;
						for (int q = 0; (path!= null) && (q < path.size()); q++){
							g2.setColor(Color.yellow);
							g2.fillRect((int)Math.round(((PointD)path.get(q)).x*ZeRTS.TILE), (int)Math.round(((PointD)path.get(q)).y*ZeRTS.TILE), 5, 5);
						}
					}
				}
*/
			if (GameWindow.dragSelect){
				g.setColor(Color.yellow);
				int x1, y1, x2, y2;
				x1 = GameWindow.stored.getX();
				y1 = GameWindow.stored.getY();
				x2 = GameWindow.current.getX();
				y2 = GameWindow.current.getY();
				if (x1 > x2){
					int t = x1;
						x1 = x2;
					x2 = t;
				}
				if (y1 > y2){
					int t = y1;
					y1 = y2;
					y2 = t;
				}
					
				g.drawRect(x1, y1, x2-x1, y2-y1);
			}
			g.translate(cameraX, cameraY);
			return drawToThis;
		}

	private static void drawThings(ArrayList things, Graphics g){

		for (int i = 0; i < things.size(); i++){
			Thing temp = (Thing)things.get(i);
			temp.drawSelf(g, null);
		
			if (temp.isSelected){
				g.setColor(Color.yellow);
				if(temp.type.equals("unit")){
					Unit utemp = (Unit)temp;
					if(utemp.manualControl)
						g.setColor(Color.cyan);
				}
					
				g.drawRect((int)(temp.x*ZeRTS.TILE), (int)(temp.y*ZeRTS.TILE), (int)temp.width, (int)temp.width);
			}
			if (temp.type.equals("unit")){
				Unit temp2 = (Unit)temp;
				int re, gr, bl;
				double percent = (double)temp2.currentHP/(double)temp2.maxHP;
				if (percent >= .5){
					re = (int)((400)*(1-percent));
					gr = 200;
				}
				else{
					re = 200;
					gr = (int)((400)*(percent));
				}
				bl = 50;
				Color health = new Color(re, gr, bl);
				g.setColor(health);
				g.fillRect((int)(temp.x*ZeRTS.TILE)+(int)temp.width, (int)(temp.y*ZeRTS.TILE)+(int)Math.round(temp.width*(1.0-percent)), 5, (int)Math.round(temp.width*percent));
				g.setColor(Color.black);
				g.drawRect((int)(temp.x*ZeRTS.TILE)+(int)temp.width, (int)(temp.y*ZeRTS.TILE), 5, (int)(temp.width));
			}
		}
	}

}

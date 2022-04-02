package gamemechanics;


import ingameobjects.Thing;
import ingameobjects.Unit;

import java.util.ArrayList;


import java.awt.Image;

import mechanicsobjects.Tile;

import zerts.ZeRTS;

public class GameInstance {
	
	public Tile[][] map;	
	public Player[] players;
	public Image mapImage;	
	
	public ArrayList allThings = new ArrayList();
	public ArrayList allUnits = new ArrayList();
	public ArrayList animationObjects = new ArrayList();
	
	public void addThing(Thing t){

		allThings.add(t);

		if(t.type.equals("unit"))
			allUnits.add(t);

		
		if(t.controller != null)
			for(int n = 0; n < players.length; n++){
				if(t.controller == players[n]){
					if(t.type.equals("unit"))
						players[n].units.add(t);
					if(t.type.equals("building"))
						players[n].buildings.add(t);
				}
			}
		
	}
	public void removeThing(Thing t){
		allThings.remove(t);

		if(t.type.equals("unit"))
			allUnits.remove(t);

		
		if(t.controller != null)
			for(int n = 0; n < players.length; n++){
				if(t.controller == players[n]){
					if(t.type.equals("unit")){
						players[n].units.remove(t);
						if(players[n].type.equals("human"))
							((HumanPlayer)players[n]).selectedUnits.remove(t);
					}
					if(t.type.equals("building"))
						players[n].buildings.remove(t);
				}
			}
				
	}

	public void addAnimationObject(Thing t){
		animationObjects.add(t);
	}
	public void removeAnimationObject(Thing t){
		animationObjects.remove(t);
	}
	
	
	public Thing validTargetFor (Unit u) {
		Thing target = null;
		for (int i = 0; i < u.range && target == null; i = i + 5){
			target = thingAt((u.x*ZeRTS.TILE+u.width/2.0+Math.cos((Math.PI/4.0)*u.direction)*(i))/ZeRTS.TILE,
					(u.y*ZeRTS.TILE+u.height/2.0-Math.sin((Math.PI/4.0)*u.direction)*(i))/ZeRTS.TILE);
			if (target == u){
				target = null;
			}
		}
		if (target != null && target.attackable)
			return target;
		else
			return null;
			
	}
	
	public Thing thingAt(double x, double y){
		Thing at = null;
		ArrayList things = allThings;
		boolean end = false;
		//System.out.println("checking");
		for (int i = 0; !end && i < things.size(); i++){
			Thing temp = (Thing)things.get(i);
			//System.out.println("dork: "+dork.x + " " + dork.y+" temp: " + temp.x + " " + temp.y);
			if ((
					temp.x>=(x-temp.width/ZeRTS.TILE)&&
					temp.x<=(x)&&
					temp.y>=(y-temp.height/ZeRTS.TILE)&&
					temp.y<=(y))){

				at = temp;
				end = true;
			}
		}
		return at;
	}
	

	public boolean unitCanBeAt(Unit dork, double x, double y){
		boolean can = true;
		boolean end = false;
		//System.out.println("checking");
		ArrayList things = allThings;
		for (int i = 0; !end && i < things.size(); i++){
			Thing temp = (Thing)things.get(i);
			//System.out.println("dork: "+dork.x + " " + dork.y+" temp: " + temp.x + " " + temp.y);
			if (temp.x==dork.x && temp.y==dork.y){
				
			}
			else if (x<=0||
					y<=0||
					x+(dork.width/ZeRTS.TILE)>=map.length||
					y+(dork.height/ZeRTS.TILE)>=map[0].length||//false);
					(temp.x>=(x-temp.width/ZeRTS.TILE)&&
							temp.x<=(x+dork.width/ZeRTS.TILE)&&
							temp.y>=(y-temp.height/ZeRTS.TILE)&&
							temp.y<=(y+dork.height/ZeRTS.TILE))){
				
				can = false;
				end = true;
			}
		}
		return can;
	}
	
}

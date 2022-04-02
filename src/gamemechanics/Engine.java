package gamemechanics;


import ingameobjects.Thing;
import ingameobjects.Unit;

import java.util.ArrayList;


import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import zerts.ZeRTS;
import libraries.*;

public class Engine {
		
	GameInstance game;
	Thread gameEngineThread;
	int gameEngineTimestep = 20;

	public Engine(GameInstance g){
		game = g;
	}

	public void startEngine(){
		gameEngineThread = new Thread(){
			public void run(){
				long startTime = 0;
				while(true){
					startTime = System.currentTimeMillis();
					doTimestep();
					while(System.currentTimeMillis() - startTime < gameEngineTimestep){
					}					
				}
			}
		};

		gameEngineThread.start();
	}
	
	public void doTimestep(){
		updateStatus();
		playerActions();
		unitBehavior();
	}

	private void playerActions(){
		Player[] players = game.players;
		for(int n = 0; n < players.length; n++){
			players[n].act();
		}
	}
	
	private void unitBehavior(){
		ArrayList things = game.allThings;
		for(int i = 0; i < things.size(); i++){
			if (((Thing)things.get(i)).type.equals("unit")){
				Unit tempUnit = ((Unit)things.get(i));
				tempUnit.doTimestep();
			}
		}	
	}

	private void updateStatus(){
		for(int n = 0; n < game.allThings.size(); n++){
			Thing tempThing = (Thing)game.allThings.get(n);
			if(tempThing.attackable){
				if(tempThing.currentHP <= 0)
					game.removeThing(tempThing);
			}
			
		}
	}


	/****************************************************** 
	 *   (Utility Methods)
	 ******************************************************/

	public static boolean approxEq (double first, double second){
		double tolerance = 0.0001;
		return ((Math.abs(first-second))<=tolerance);
	}

	public static double distance(double x1, double y1, double x2, double y2){
		double s1 = Math.abs(x1-x2);
		double s2 = Math.abs(y1-y2);
		double longer = s1;
		double shorter = s2;
		if(s2 > s1){
			longer = s2;
			shorter = s1;
		}
		return (longer-shorter) + shorter*ZeRTS.rt2;
	}
	
	public static double realDistance(double x1, double y1, double x2, double y2){
		double dx = x1-x2;
		double dy = y1-y2;
		double distance =  Math.sqrt(dx*dx + dy*dy);
		return distance;
	}


}

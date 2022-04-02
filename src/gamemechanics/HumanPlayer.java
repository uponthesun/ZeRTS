package gamemechanics;


import ingameobjects.Thing;
import ingameobjects.Unit;

import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.event.*;

import zerts.ZeRTS;


import libraries.ActionLibrary;
import libraries.AnimationLibrary;
import mechanicsobjects.AnimationCycle;
import mechanicsobjects.UnitAction;
import gamemechanics.*;
import guishitz.GameWindow;

public class HumanPlayer extends Player{

	public ArrayList mouseClickEvents = new ArrayList();
	public ArrayList mouseDraggedEvents = new ArrayList();
	public ArrayList keysPressed = new ArrayList();

	public ArrayList selectedUnits = new ArrayList();
	
	public HumanPlayer(GameInstance g){
		game = g;
		type = "human";
	}
	
	public void act(){
		handleMouseEvents();
		keyboardCommands();
	}
	
	private void manualControl(Unit selectedUnit){
		if(selectedUnit.lagCounter != 0)
			return;
		
		AnimationCycle anim = selectedUnit.currentAnimation;
		
		boolean lefting = keysPressed.contains("left") || keysPressed.contains("←");
		boolean righting = keysPressed.contains("right") || keysPressed.contains("→");
		boolean upping = keysPressed.contains("up") || keysPressed.contains("↑");
		boolean downing = keysPressed.contains("down") || keysPressed.contains("↓");
		boolean attacking = keysPressed.contains("a");
		
		boolean commandEntered = false;
		
		int xComponent = 0;
		int yComponent = 0;
		
		if(lefting)
			xComponent--;
		if(righting)
			xComponent++;
		if(upping)
			yComponent--;
		if(downing)
			yComponent++;		
		if(!(xComponent==0 && yComponent==0)){
			selectedUnit.stepTowards(xComponent + selectedUnit.x, yComponent + selectedUnit.y);
			if(anim == null || !anim.type.equals("move"))
				selectedUnit.currentAnimation = AnimationLibrary.moveAnimation(selectedUnit);

			commandEntered = true;
		}
		else{
			if(selectedUnit.manualControl && anim != null && anim.type.equals("move"))
				anim.end = true;
		}

		
		if(attacking){
			selectedUnit.attack();
			commandEntered = true;
		}

		if(commandEntered){
			selectedUnit.manualControl = true;
			selectedUnit.currentAction = null;
		}
		
	}
	
	private void handleMouseEvents(){
		while(mouseClickEvents.size()!=0){
			MouseEvent e = (MouseEvent)mouseClickEvents.remove(0);
			if ((e.getButton() == MouseEvent.BUTTON1)&&keysPressed.contains("ctrl")|| e.getButton() == 3)
				rightClick(e);
			else if(e.getButton() == MouseEvent.BUTTON1)
				leftClick(e);			
		}
		while(mouseDraggedEvents.size()!=0){
			MouseEvent[] dual = (MouseEvent[])mouseDraggedEvents.remove(0);
			MouseEvent e = dual[0];
			MouseEvent f = dual[1];
			if ((e.getButton() == MouseEvent.BUTTON1)&&keysPressed.contains("ctrl")|| e.getButton() == 3){
				mouseClickEvents.add(e);
			}
			else{
				multiSelect(e, f);
			}
		}
	}
	
	public void multiSelect(MouseEvent e, MouseEvent f){
		int x1 = e.getX();
		int y1 = e.getY();
		int x2 = f.getX();
		int y2 = f.getY();
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
		ArrayList tempList = new ArrayList();
		for (int i = 0; i < units.size(); i++){
			Thing temp = (Thing)units.get(i);
			if ((x1<=(temp.x*ZeRTS.TILE+temp.width))&&
					(x2>(temp.x*ZeRTS.TILE))&&
					(y1<(temp.y*ZeRTS.TILE+temp.height))&&
					(y2>(temp.y*ZeRTS.TILE)))
			{	
				tempList.add(temp);
			}			
		}
		
		if(tempList.size() == 0)
			return;
			
		for (int j = 0; j < selectedUnits.size(); j++){
			Unit u = (Unit)(selectedUnits.get(j));
			u.isSelected = false;
			if(u.manualControl){
				u.manualControl = false;
				if(u.currentAnimation != null)
					u.currentAnimation.end = true;
			}
		}
		
		for(int n = 0; n < tempList.size(); n++)
			((Thing)tempList.get(n)).isSelected = true;
		
		selectedUnits = tempList;
	}
	
	private void leftClick(MouseEvent e){
		multiSelect(e,e);
	}
	
	private void rightClick(MouseEvent e){
		for (int i = 0; i < selectedUnits.size(); i ++){
			Unit u = (Unit)selectedUnits.get(i);
			if(u != null){
				u.manualControl = false;			
				double targetX = ((double)e.getX())/ZeRTS.TILE;
				double targetY = ((double)e.getY())/ZeRTS.TILE;
				
				UnitAction act = ActionLibrary.moveAction(u, targetX - (u.width/ZeRTS.TILE)/2 , targetY - (u.height/ZeRTS.TILE)/2);
				u.currentAction = act;
				
			}
		}
	}
	
	private void keyboardCommands(){	
		if( keysPressed.contains("escape")){
			System.exit(0);
		}
		if( keysPressed.contains("q")){
			System.exit(0);
		}
		
		for(int n = 0; n < selectedUnits.size(); n++){
				Unit tUnit = (Unit)selectedUnits.get(n);
				manualControl(tUnit);
		}
	}
	
	
}

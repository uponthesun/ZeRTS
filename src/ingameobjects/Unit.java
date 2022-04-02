package ingameobjects;
import gamemechanics.*;
import libraries.*;
import mechanicsobjects.AnimationCycle;
import mechanicsobjects.UnitAction;
import mechanicsobjects.HitBox;

import java.awt.*;
import java.awt.image.ImageObserver;

import zerts.ZeRTS;


public class Unit extends Thing{

	public int attack;
	public HitBox hBox;
	public int range = 100;
	public double movementSpeed;
	public int direction; //0=E, 2=N, 4=W, 6=S (*45 degrees!)
	public Image[] leftDirectionalPicture;
	public Image[] rightDirectionalPicture;
	public Image baseLeftPicture;
	public Image baseRightPicture;
	public Image currentPicture;
	public UnitAction currentAction;

	public int cooldown;
	
	public boolean manualControl = false;
	public double lastActionTime = 0;
	public int cooldownCounter = 0;
	public int lagCounter = 0;
	public int attackLag = 15;

	public Unit(String nomen, Player controller, double ex, double why, 
			double wide, double high, Image lpic, Image rpic, double speed, int health, int dmg, int cdown){
	
		initialize(nomen, controller, ex, why, wide, high, lpic, rpic, speed, health, dmg, cdown);
		type = "unit";
	}
	
	public void initialize(String nomen, Player controller, double ex, double why, 
			double wide, double high, Image lpic, Image rpic, double speed, int health, int dmg, int cdown){
		x = ex;
		y = why;
		
		baseLeftPicture = lpic;
		baseRightPicture = rpic;
		this.controller = controller;
		leftDirectionalPicture = ImageLibrary.directionalize(baseLeftPicture);
		rightDirectionalPicture = ImageLibrary.directionalize(baseRightPicture);
		name = nomen;
		width = wide;
		height = high;
		isSelected = false;
		movementSpeed = speed;
		maxHP = health;
		currentHP = health;
		attack = dmg;
		cooldown = cdown;

		
	}
	
	public void doTimestep(){

		if(cooldownCounter > 0)
			cooldownCounter--;
		if(lagCounter > 0)
			lagCounter--;
		else if(currentAction != null){
			currentAction.doAction();
		}

	}

	
	public void attack(){
		Thing targetThing = hBox.hitSomething();
			if(cooldownCounter == 0){
				if(targetThing != null)
					targetThing.currentHP = targetThing.currentHP-attack;
				cooldownCounter = cooldown;
				lagCounter = attackLag;
				currentAnimation = AnimationLibrary.attackAnimation(this);
			}
	}
	
	public void stepTowards(double goalX, double goalY){

		double dx = goalX - x;
		double dy = goalY - y;

		double angle = Math.atan2(dy, dx);
		double drawAngle = Math.atan2(-dy, dx);
		double piover4 = Math.PI/4;
		int newDirection = ((int)Math.round((drawAngle+2*Math.PI)/piover4))%8;
		direction = newDirection;
		
		GameInstance env = controller.game;
		
		if(Math.sqrt(dx*dx + dy*dy) <= movementSpeed){
			if(env.unitCanBeAt(this, goalX, goalY)){
				x = goalX;
				y = goalY;
			}
		}
		else{
			double newX = movementSpeed*Math.cos(angle) + x;
			double newY = movementSpeed*Math.sin(angle) + y;
			
			if(env.unitCanBeAt(this, newX, newY)){
				x = newX;
				y = newY;
			}			
			else if(env.unitCanBeAt(this, x, newY))
				y = newY;
			else if(env.unitCanBeAt(this, newX, y))
				x = newX;
		}
		
	}


	
	public void drawSelf(Graphics g, ImageObserver obs){
		if(currentAnimation != null){
			AnimationCycle a = currentAnimation;
			currentPicture = a.getCurrentFrame(direction);
		}
		else{
			currentPicture = leftDirectionalPicture[direction];
		}
		
		Image i = currentPicture;
		g.drawImage(i , (int)(x*ZeRTS.TILE), (int)(y*ZeRTS.TILE), (int)width, (int)height, obs);
	}
	
	//GETTERSTRALIA
	




	
}

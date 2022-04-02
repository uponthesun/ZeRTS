package ingameobjects;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import mechanicsobjects.AnimationCycle;

import zerts.ZeRTS;

import gamemechanics.Player;

public abstract class Thing{

	public double x, y;
	public Image picture;
	public String name;
	public String type;
	public double width, height;
	public boolean isSelectable;
	public boolean isSelected;
	public boolean attackable = true;
	public Image currentPicture;
	public AnimationCycle currentAnimation;
	public Player controller;
	
	public int maxHP = -1;
	public int currentHP = -1;

	
	public Thing (){
	}
	
	public void doAnimationStep(){
		if(currentAnimation != null){
			currentAnimation.timeStep();
			if(currentAnimation.end)
				currentAnimation = null;
		}
	}
	
	public void drawSelf(Graphics g, ImageObserver obs){
		if(currentAnimation != null){
			AnimationCycle a = currentAnimation;
			currentPicture = a.getCurrentFrame();
		}
		else{
			currentPicture = picture;
		}
		
		Image i = currentPicture;
		g.drawImage(i , (int)(x*ZeRTS.TILE), (int)(y*ZeRTS.TILE), (int)width, (int)height, obs);
	}
	
/*	public void drawSelf(Graphics g, ImageObserver obs){
		g.drawImage(picture, (int)(x*ZeRTS.TILE), (int)(y*ZeRTS.TILE), (int)width, (int)height, obs);
	}
*/	
}

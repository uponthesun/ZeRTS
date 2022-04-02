package libraries;

import ingameobjects.Unit;

import java.awt.Image;

import java.util.HashMap;

import mechanicsobjects.AnimationCycle;

public class AnimationLibrary {

	private static HashMap moveAnimations = new HashMap();
	private static HashMap attackAnimations = new HashMap();
	
	public static void loadUnitAnimations(){

		Image[] antMoveFrames = new Image[2];
		antMoveFrames[0] = ImageLibrary.antL;
		antMoveFrames[1] = ImageLibrary.antR;

		Image[] queenMoveFrames = new Image[2];
		queenMoveFrames[0] = ImageLibrary.queenL;
		queenMoveFrames[1] = ImageLibrary.queenR;
		
		Image[] bullseyeMoveFrames = new Image[2];
		bullseyeMoveFrames[0] = ImageLibrary.bullseyeAntL;
		bullseyeMoveFrames[1] = ImageLibrary.bullseyeAntR;
		
		moveAnimations.put("ant", directionalizeAnimation(antMoveFrames));
		moveAnimations.put("queen", directionalizeAnimation(queenMoveFrames));
		moveAnimations.put("bullseye", directionalizeAnimation(bullseyeMoveFrames));
		
		Image[] antAttackFrames = new Image[2];
		antAttackFrames[0] = ImageLibrary.antAttack1;
		antAttackFrames[1] = ImageLibrary.antAttack2;
		
		Image[] queenAttackFrames = new Image[2];
		queenAttackFrames[0] = ImageLibrary.chicken;
		queenAttackFrames[1] = ImageLibrary.chicken;
		
		Image[] bullseyeAttackFrames = new Image[2];
		bullseyeAttackFrames[0] = ImageLibrary.chicken;
		bullseyeAttackFrames[1] = ImageLibrary.chicken;
		
		attackAnimations.put("ant", directionalizeAnimation(antAttackFrames));
		attackAnimations.put("queen", directionalizeAnimation(queenAttackFrames));
		attackAnimations.put("bullseye", directionalizeAnimation(bullseyeAttackFrames));
	}
	
	private static Image[][] directionalizeAnimation(Image[] baseFrames){
		Image[][] frames = new Image[baseFrames.length][8];		
		for(int n = 0; n < baseFrames.length; n++)
			frames[n] = ImageLibrary.directionalize(baseFrames[n]);
		return frames;
	}
	
	public static AnimationCycle moveAnimation(Unit u){

		Image[][] frames = (Image[][])moveAnimations.get(u.name);	
				
		int[] frameTimes = new int[2];
		frameTimes[0] = 3;
		frameTimes[1] = 6;
		
		AnimationCycle anim = new AnimationCycle(frames, frameTimes);
		anim.type = "move";
		anim.repeat = true;
		
		return anim;
	}
	
	public static AnimationCycle attackAnimation(Unit u){

		Image[][] frames = (Image[][])attackAnimations.get(u.name);	
				
		int[] frameTimes = new int[2];
		frameTimes[0] = 5;
		frameTimes[1] = 10;
		
		AnimationCycle anim = new AnimationCycle(frames, frameTimes);
		anim.type = "attack";
 		
		return anim;
	}
	
	
}

package libraries;

import java.awt.*;
import java.awt.image.ImageObserver;

public class ImageLibrary {
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Component c;
	
	public static Image mud = tk.createImage("TEXTURES/Mud.jpg");
	public static Image dirt = tk.createImage("TEXTURES/Dirt.jpg");
	public static Image grass = tk.createImage("TEXTURES/Grass.jpg");
	public static Image grass2 = tk.createImage("TEXTURES/Grass2.jpg");
	public static Image leaves = tk.createImage("TEXTURES/Leaves.jpg");
	public static Image bits = tk.createImage("TEXTURES/Bits.jpg");
	public static Image funky = tk.createImage("TEXTURES/Funky.jpg");
	public static Image marble = tk.createImage("TEXTURES/Marble.jpg");
	public static Image boulder = tk.createImage("Boulder.png");
	public static Image chicken = tk.createImage("GenericChicken.png");
	public static Image antL = tk.createImage("basicAntLeft.png");
	public static Image antR = tk.createImage("basicAntRight.png");
	public static Image antAttack1 = tk.createImage("basicAntAttack.png");
	public static Image antAttack2 = tk.createImage("basicAntAttack2.png");
	public static Image bullseyeAntL = tk.createImage("bullseyeAntLeft.png");
	public static Image bullseyeAntR = tk.createImage("bullseyeAntRight.png");
	public static Image queenL = tk.createImage("queenLeft.png");
	public static Image queenR = tk.createImage("queenRight.png");
	
	public static void loadAll(){
		if(c==null){
			System.out.println("can't load images - no component given");
			return;
		}
		
		MediaTracker mt = new MediaTracker(c);
		mt.addImage(mud, 0);
		mt.addImage(dirt, 0);
		mt.addImage(grass, 0);
		mt.addImage(grass2, 0);
		mt.addImage(leaves, 0);
		mt.addImage(bits, 0);
		mt.addImage(funky, 0);
		mt.addImage(marble, 0);
		mt.addImage(boulder, 0);
		mt.addImage(chicken, 0);
		mt.addImage(antL, 0);
		mt.addImage(antR, 0);
		mt.addImage(antAttack1, 0);
		mt.addImage(antAttack2, 0);
		mt.addImage(bullseyeAntL, 0);
		mt.addImage(bullseyeAntR, 0);
		mt.addImage(queenL, 0);
		mt.addImage(queenR, 0);
		try{
			mt.waitForAll();
		}
		catch(Exception e){
			System.out.println(e);
		}
		AnimationLibrary.loadUnitAnimations();
	}
	
	public static void setComponent(Component see){
		c = see;
	}
	
	public static Image[] directionalize (Image base){
		
		if(c==null){
			System.out.println("can't load images - no component given");
			return null;
		}	
		
		Image[] dPicts = new Image[8];
		for (int i = 0; i < dPicts.length; i++){
			Image temp = c.createImage(base.getWidth(c), base.getHeight(c));
			Graphics2D g2 = (Graphics2D)temp.getGraphics();
			g2.setBackground(new Color(200, 0, 200,0));
			g2.clearRect(0, 0, base.getWidth(c), base.getHeight(c));
			g2.translate(temp.getWidth(c)/2.0, temp.getHeight(c)/2.0);
			g2.rotate(-i*Math.PI/4.0+Math.PI/2.0);
			g2.translate(-temp.getWidth(c)/2.0, -temp.getHeight(c)/2.0);
			g2.drawImage(base,0,0,c);

			dPicts[i]=temp;
		}
		return dPicts;
	}
	
}

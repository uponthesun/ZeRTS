package guishitz;
/*ZeRTS
 * Main Menu, GUI
 * Created February 28, 2008 
 * Finished: (not yet)
 * Does:
 *	(nothing)
 */
import gamemechanics.*;
import libraries.*;

import javax.swing.*;


import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import ingameobjects.*;
import zerts.*;


public class GameWindow extends JFrame{

	Thread drawingStuffThread;
	int redrawScreenTimestep = 40;
	
	GameInstance game;
	HumanPlayer player;
	
	public static boolean dragSelect = false;
	public static MouseEvent stored;
	public static MouseEvent current;
	public String clickMode = "";
	private Point mousePosition;
	
	Image bufferImage;
	int screenWidth = 800;
	int screenHeight = 500;
	public static int screenX, screenY = 0;
	private JPanel contentPane;
	int frameWidth = 800;
	int frameHeight = 600;
	int cameraX = 0;
	int cameraY = 0;
	int mapHeight, mapWidth;
	
	public GameWindow(GameInstance g, HumanPlayer h) {
		game = g;
		player = h;

		setResizable(false);
		setVisible(true);

		bufferImage = createImage(screenWidth, screenHeight);
		
		ImageLibrary.setComponent(this);
		game.mapImage = WorldRenderer.createMapImage(this, game.map);
		mapHeight = game.mapImage.getHeight(this);	
		mapWidth = game.mapImage.getWidth(this);
		

		setSize(frameWidth, frameHeight + getInsets().top);
		setLocationRelativeTo(null);
		screenX = getInsets().left;
		screenY = getInsets().top;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel(null);
		contentPane.setSize(getWidth(), getHeight());
		
		makeBottomPanel();
		
		setContentPane(contentPane);
		setFocusable(false);

/*		contentPane.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e){
				contentPane.requestFocusInWindow();
			}
			public void focusGained(FocusEvent e){
			}
		});
*/		
		userInputListeners();
		createUpdateThread();
		drawingStuffThread.start();
		contentPane.requestFocus();
	}

	private void makeBottomPanel(){
		Panel bottomPanel = new Panel(new GridLayout(1, 3));
		bottomPanel.setLocation(0, screenHeight);
		bottomPanel.setSize(screenWidth, frameHeight-screenHeight);
		bottomPanel.setBackground(Color.WHITE);
		
		Panel leftPanel = new Panel(null);
		Panel infoPanel = new Panel(null);
		Panel buttonPanel = new Panel(new GridLayout(3, 3));

		bottomPanel.add(leftPanel);
		bottomPanel.add(infoPanel);
		bottomPanel.add(buttonPanel);
		
		String[] buttonNames = {"Move", "Attack", "Stop"};
		for(int n = 0; n < 9; n++){
			if(n<buttonNames.length){
				Button tempButton = new Button(buttonNames[n]);
				tempButton.setName(buttonNames[n]);
				ActionListener al = new ActionListener(){
					public void actionPerformed(ActionEvent e){
						handleButtons(e);
						contentPane.requestFocus();
					}
				};
				tempButton.addActionListener(al);
//				buttonPanel.add(tempButton);
			}
			else{
//				buttonPanel.add(new Panel());
			}
		}
		
		bottomPanel.setFocusable(false);
		Component[] c = bottomPanel.getComponents();
		for(int n = 0; n < c.length; n++)
			c[n].setFocusable(false);
		
		contentPane.add(bottomPanel);
	}
	
	private void createUpdateThread(){
		drawingStuffThread = new Thread(){
			public void run(){
				long startTime = 0;
				while(true){
					startTime = System.currentTimeMillis();
					if(mousePosition != null && contentPane.hasFocus()){
						int x = mousePosition.x;
						int y = mousePosition.y;
						int zone = 50;
						int moveInc = 20;
						int dx = 0;
						int dy = 0;
						if(x < zone)
							dx = -moveInc;
						if(screenWidth - x < zone)
							dx = moveInc;
						if(y - screenY < zone)
							dy = -moveInc;
						if(screenHeight+screenY-y < zone)
							dy = moveInc;
						
						cameraX += dx;
						cameraY += dy;
						cameraBounds();

					}
					ArrayList things = game.allThings;
					for(int n = 0; n < things.size(); n++){
						Thing t = (Thing)things.get(n);
						if(t.currentAnimation != null)
							t.doAnimationStep();
					}
					
					repaint();
					while(System.currentTimeMillis() - startTime < redrawScreenTimestep){
					}
				}
			}
		};
	}
	
	private void userInputListeners(){
		addMouseListener(new MouseAdapter() {

			long clickTime = 0;

			public void mousePressed (MouseEvent e){
				clickTime = System.currentTimeMillis();
				stored = adjustMELocation(e);
				contentPane.requestFocus();
			}			
			public void mouseReleased (MouseEvent e) {
				if (dragSelect){
					dragSelect = false;
					MouseEvent [] dualEvent = {stored, adjustMELocation(e)}; 
					player.mouseDraggedEvents.add(dualEvent);
				}
				else if( System.currentTimeMillis() - clickTime < 250){
					player.mouseClickEvents.add(adjustMELocation(e));
				}
				
			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged (MouseEvent e) {
				if (!dragSelect)
					dragSelect = true;
				current = adjustMELocation(e);
				mousePosition = new Point(e.getX(), e.getY());
			}
			public void mouseMoved (MouseEvent e) {
				mousePosition = new Point(e.getX(), e.getY());
			}
		});

		contentPane.addKeyListener(new KeyListener(){//key listeners for shortcut keys
			public void keyPressed(KeyEvent e){
				String key = e.getKeyText(e.getKeyCode()).toLowerCase();
				if(!player.keysPressed.contains(key))
					player.keysPressed.add(key);
				if(key.equals("c"))
					if(player.selectedUnits.size()>0)
						centerOnUnit((Unit)player.selectedUnits.get(0));
			}
			public void keyTyped(KeyEvent e){
			}
			public void keyReleased(KeyEvent e){
				String key = e.getKeyText(e.getKeyCode()).toLowerCase();
				player.keysPressed.remove(key);
			}
		});
	}

	private void handleButtons(ActionEvent e){
		Component c = (Component)(e.getSource());
		String buttonName = c.getName();
		System.out.println("Button: " + buttonName);
		
		ArrayList selected = player.selectedUnits;
		
	}
	
	private void centerOnUnit(Unit u){
		int pixelX = (int)((u.x)*ZeRTS.TILE +u.width/2);
		int pixelY = (int)((u.y)*ZeRTS.TILE+u.height/2);
		cameraX = pixelX - screenWidth/2;
		cameraY = pixelY - screenHeight/2;
		cameraBounds();
	}
	
	public MouseEvent adjustMELocation(MouseEvent e){
		int xAdjust = cameraX-screenX;
		int yAdjust = cameraY-screenY;
		e.translatePoint(xAdjust, yAdjust);		
		return e;
	}
	
	public Point getAdjustedLocation(MouseEvent e){
		int xAdjust = cameraX-screenX;
		int yAdjust = cameraY-screenY;
		return new Point(e.getX()+xAdjust, e.getY()+yAdjust);
	}
	
	public void cameraBounds(){
//		int sideMargin = screenWidth/2;
//		int topMargin = screenHeight/2;
		int sideMargin = 0;
		int topMargin = 0;
		if(cameraX < -sideMargin)
			cameraX = -sideMargin;
		if(cameraX + screenWidth > mapWidth + sideMargin)
			cameraX = mapWidth - screenWidth + sideMargin;
		if(cameraY < -topMargin)
			cameraY = -topMargin;
		if(cameraY + screenHeight > mapHeight + topMargin)
			cameraY = mapHeight - screenHeight + topMargin;		
	}
	
	public void update (Graphics g) {
		paint(g);
	}

	public void paint(Graphics g){
		Graphics tempG = bufferImage.getGraphics();
		tempG.setColor(Color.BLACK);
		tempG.fillRect(0, 0, screenWidth, screenHeight);
		WorldRenderer.drawWorld(game, bufferImage, cameraX, cameraY);
		g.drawImage(bufferImage , screenX, screenY, this);
	}
	
}





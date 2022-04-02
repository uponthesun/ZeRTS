package guishitz;
/*ZeRTS
 * Main Menu, GUI
 * Created February 20, 2008 
 * Finished: (not yet)
 * Does:
 *	(nothing)
 */

import guishitz.Multiplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame{
/*	JButton exit, singleP, multiP, mapEdit;
	AbstractAction quit, sp, mp, map;
	Panel buttons;
	public MainMenu () {
		super("ZeRTS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200,200);
		setLocation(40,60);
		quit= new AbstractAction("<HTML><u>Q</u>uit</HTML>") {
			{
				putValue(Action.SHORT_DESCRIPTION, "Quits the program");
			}
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		};
		exit = new JButton(quit);
		exit.setFocusable(false);
		
		map= new AbstractAction("<HTML><u>M</u>ap Editor</HTML>") {
			{
				putValue(Action.SHORT_DESCRIPTION, "Opens the Map Editor");
			}
			public void actionPerformed(ActionEvent evt) {
				new MapEditor();
			}
		};
		mapEdit = new JButton(map);
		mapEdit.setFocusable(false);
		
		sp= new AbstractAction("<HTML><u>S</u>ingle Player</HTML>") {
			{
				putValue(Action.SHORT_DESCRIPTION, "Starts a single player game");
			}
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				new GameWindow();
			}
		};
		singleP = new JButton(sp);
		singleP.setFocusable(false);
		
		mp= new AbstractAction("<HTML><u>M</u>ultiplayer</HTML>") {
			{
				putValue(Action.SHORT_DESCRIPTION, "Starts a multiplayer game");
			}
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				new Multiplayer();
			}
		};
		multiP = new JButton(mp);
		multiP.setFocusable(false);
		
		buttons = new Panel();
		buttons.setLayout(new GridLayout(4, 1));
				
		buttons.add(singleP);
		buttons.add(multiP);
		buttons.add(mapEdit);
		buttons.add(exit);
		add(buttons);
		setVisible(true);
		
		
		
		addKeyListener(new KeyListener(){//key listeners for shortcut keys
			public void keyPressed(KeyEvent e){
				//try{
					String key = e.getKeyText(e.getKeyCode());
					System.out.println(key);
					if( key.equalsIgnoreCase("escape")){
						System.exit(0);
					}
					if( key.equalsIgnoreCase("Q")){
						System.exit(0);
					}
					if( key.equalsIgnoreCase("S")){
						setVisible(false);
						new GameWindow();
					}
					if( key.equalsIgnoreCase("M")){
						setVisible(false);
						new Multiplayer();
					}

				//}
				catch(NullPointerException n){
					System.out.println("NullPointer.../n"+n.getMessage());
				}
			}
			public void keyTyped(KeyEvent e){
			}
			public void keyReleased(KeyEvent e){

			}
		});

		
	}
	*/
}

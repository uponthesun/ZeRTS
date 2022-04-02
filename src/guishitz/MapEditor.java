package guishitz;
/*ZeRTS
 * Main Menu, GUI
 * Created February 28, 2008 
 * Finished: (not yet)
 * Does:
 *	(nothing)
 */
import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MapEditor extends JFrame implements MouseListener, MouseMotionListener{
	private int x,y;
	private char[][] map;
	private int width = 10;
	private int height = 20;
	private int tile = 30;
	private Image buffer;
	private JScrollPane mapPane = new JScrollPane();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("File");
	private Action saving;
	private JMenuItem save;// = new JMenuItem("Save");
	private JPanel panel = new JPanel();
	private char currentTile=' ';
	private char[] tiles = {'m','d','g'};
	//PrintWriter printer = new PrintWriter("lolzor.txt"); uncaught exception lolzor
	public MapEditor(){
		super("MapEditor");


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		saving= new AbstractAction("Save") {
			{
				putValue(Action.SHORT_DESCRIPTION, "Saves the map");
			}
			public void actionPerformed(ActionEvent evt) {
				try{
					PrintWriter printer = new PrintWriter(new File("lolzor.map"));
					printer.println("x " + Integer.toString(getWidth()/tile));
					printer.println("y " + Integer.toString(getHeight()/tile));
					printer.println("Tileset 1");
					for(int x = 0; x < map[0].length; x++){
						printer.print("% ");
						for(int y = 0; y < map.length; y++){
							printer.print(map[y][x] + " ");
						}
						printer.println();
					}
					printer.println("end");
					printer.flush();
					printer.close();
				}
				catch(Exception e){
					System.out.println("lawlzor");
				}
			}
		};
		save = new JMenuItem(saving);
		GUI();
		map = new char[width][height];
		for(int x = 0; x < width;x++){
			for(int y = 0; y<height;y++){
				map[x][y]='f';
			}
		}
		setSize(width*tile,height*tile);
		System.out.println(width*tile);

		JPanel contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		setVisible(true);
		JPanel panel = new MapEditorPanel(createImage(width*tile, height*tile),map);
		//panel.setSize(40,40);
		contentPane.add(panel,BorderLayout.CENTER);

	//	addMouseListener(this);
	//	addMouseMotionListener(this);

	}

	private void GUI(){
		menuBar.add(menu);
		menu.add(save);
		setJMenuBar(menuBar);
	}





	/*public void paint(Graphics e){
		Graphics g = getGraphics();
		Graphics g = buffer.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.red);
		g.drawRect(x-x%tile, y-y%tile, tile, tile);
		for(int x = 0; x < width; x++){
			for(int y = 0; y<height;y++){
				if(map[x][y]=='g')
					g.fillRect(x*tile,y*tile,tile,tile);
			}
		}
		e.drawImage(buffer, 0, 0, this);
	}*/

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {


	}

	public void mouseExited(MouseEvent e) {


	}

	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		map[x/tile][y/tile] = 'g';
		repaint();
	}

	public void mouseReleased(MouseEvent e) {


	}

	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		map[x/tile][y/tile] = 'g';
		repaint();

	}

	public void mouseMoved(MouseEvent e) {
		System.out.println(e.getX());
		System.out.println(e.getY());
		x = e.getX();
		y = e.getY();
		repaint();

	}

}

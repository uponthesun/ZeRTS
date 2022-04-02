package guishitz;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MapEditorPanel extends JPanel implements MouseListener,MouseMotionListener{
	private int x,y;
	private int width = 10;
	private int height = 20;
	private int tile = 30;
	private Image buffer;
	private char[][] map;
	public MapEditorPanel(Image b, char[][] m){
		super();
		buffer = b;
		map = m;
		//setVisible(true);
	//	buffer=createImage(width*tile,height*tile);
		setSize(width*tile, height*tile);
		addMouseListener(this);
		addMouseMotionListener(this);
//		g = buffer.getGraphics();
	}
	public void paint(Graphics e){

		Graphics g = buffer.getGraphics();

		g.setColor(Color.pink);
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
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {


	}

	public void mouseExited(MouseEvent e) {


	}

	public void mousePressed(MouseEvent e) {
		System.out.println("pressed");
		x = e.getX();
		y = e.getY();
		map[x/tile][y/tile] = 'g';
		repaint();
	}

	public void mouseReleased(MouseEvent e) {


	}

	public void mouseDragged(MouseEvent e) {
		System.out.println("dragging");
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
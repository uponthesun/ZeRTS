package guishitz;

import java.awt.*;
import javax.swing.*;

public class LoadScreenFrame extends JFrame{

	public LoadScreenFrame(){
		setSize(400, 300);
		setLocationRelativeTo(null);
	//	setUndecorated(true);
		setLayout(new BorderLayout());

		JLabel label = new JLabel("loading...");
		add(label, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
}

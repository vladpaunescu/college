package tutorial;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import jade.core.Agent;

public class GuiAgent extends Agent { 
	 protected void setup() {
		 	JFrame frame = new JFrame();
			frame.setSize(600, 600);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			JTextArea ta = new JTextArea();
			frame.add(ta);
			ta.setText("Hello World. My name is "+this.getLocalName());
	 System.out.println("Hello World. My name is "+this.getLocalName()); 
	 } 
	} 


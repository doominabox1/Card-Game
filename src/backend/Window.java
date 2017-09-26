package backend;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	JFrame frame;
	JPanel backgroundPanel;
	public Window() {
		frame = new JFrame();
		windowDefaults();
		drawGameBoard();
		//frame.pack();
		frame.setVisible(true);
	}
	public Window(String s) {
		frame = new JFrame(s);
		windowDefaults();
		drawGameBoard();
		frame.setVisible(true);
	}
	public void windowDefaults() {
		frame.setSize(1200, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void drawGameBoard() {
		backgroundPanel = new JPanel();
		backgroundPanel.setBackground(new Color(0,200,0,1));
		frame.add(backgroundPanel);
	}
	public static void main(String[] args) {
		Window w = new Window("Window");
	}
}

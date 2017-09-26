package backend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.prism.Graphics;

public class WindowTest {

	BufferedImage img = null;
	
	static Card c = new Card();
	public static void main(String[] args) {
		//c.setValue((Integer.parseInt(JOptionPane.showInputDialog("Input a position"))));
		WindowTest window = new WindowTest();
	}
	public WindowTest() {
		JFrame frame = new JFrame("Card Tester");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		ImageIcon icon = new ImageIcon("cards.png");
		JLabel iconLabel = new JLabel(icon);
		panel.setBackground(Color.white);
		frame.add(panel);
		frame.pack();
		frame.setSize(1600, 1000);
		frame.setVisible(true);
		
	}
	public void loadImage(){
		try {
			img = ImageIO.read(new File("cards.png"));
		}
		catch (IOException e){
		}
	}
	public void paint(Graphics g) {
		//super.paint(g);
		//g.drawImage(img, 0, 0, null);
	}
}

package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import backend.Card;
import backend.Client;

public class TestClientPanel extends JPanel{
	Client client;
	JTextField output;
	JComponent drawPanel;
	public TestClientPanel(Client client){
		this.client = client;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(900, 80));
		JTextField input = new JTextField();
		output = new JTextField("Default");
		
		output.setEditable(false);
		
		drawPanel = new JComponent() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.drawLine(0, 0, 100, 100);
				int cardPos = 10;
				if(client.player.hand.size() > 0){
					for(Card c : client.player.hand){
						g.drawImage(c.getFace(), cardPos, 10, null);
						cardPos += 20;
					}
				}
				
			}
		};
		
		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(input.getText().length() > 0){
					//client.sendCard(input.getText());
					input.setText("");
				}
			}
		});
		
		drawPanel.setPreferredSize(new Dimension(900, 200));
		
		add(input);
		add(output);
		add(drawPanel);
	}
	public void showText(String line) {
		output.setText(line);
	}
	public void updatePanel() {
		drawPanel.repaint();
	}
}

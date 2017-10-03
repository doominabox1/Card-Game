package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import backend.Client;

public class TestClientPanel extends JPanel{
	Client client;
	JTextField output;
	public TestClientPanel(Client client){
		this.client = client;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(900, 80));
		JTextField input = new JTextField();
		output = new JTextField("Default");
		
		output.setEditable(false);
		
		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(input.getText().length() > 0){
					client.sendCard(input.getText());
					input.setText("");
				}
			}
		});
		
		add(input);
		add(output);
	}
	public void showText(String line) {
		output.setText(line);
	}
}

package backend;

import gui.ServerPanel;

public class Server {
	ServerPanel panel;
	public Server(String port){
		panel = new ServerPanel(port);
	}
	public ServerPanel getPanel(){
		return panel;
	}
}

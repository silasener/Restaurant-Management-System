package org.example;

import javax.swing.*;
import java.awt.*;


public class CookPanel extends JPanel{
	private static JTextArea cookPanelMessages;
	CookPanel(){
		JFrame cookFrame =new JFrame();
		cookFrame.setTitle("AŞÇI");
		cookFrame.setSize(400,500);
		cookPanelMessages = new JTextArea("", 20, 40);
		cookPanelMessages.setLineWrap(false);
		cookPanelMessages.setFont(new Font("Arial",Font.PLAIN, 12));
		JScrollPane jsp1 = new JScrollPane(cookPanelMessages);
		cookFrame.add(jsp1);
		cookFrame.setVisible(true);
	}
	
	public void addCookMessage(String msg){
		String text = cookPanelMessages.getText();
		if (text == null || text.length() == 0) {
			cookPanelMessages.setText(msg);
		}
		else {
			cookPanelMessages.setText(cookPanelMessages.getText() + "\n" + msg);
		}
	}
}

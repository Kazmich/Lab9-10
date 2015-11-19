package com.clarity;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class LoginActionListener implements ActionListener {
	public void processAction(ActionEvent e) 
	  throws AbortProcessingException {
	  System.out.println("logging in ...........");
	}
}
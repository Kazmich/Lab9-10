package com.clarity

import javax.faces.context.FacesContext
import javax.faces.bean.ManagedBean
import javax.faces.bean.SessionScoped
import javax.faces.event.ActionEvent
import javax.faces.event.ComponentSystemEvent 
import javax.faces.event.ValueChangeEvent
import javax.faces.component.UIForm
import javax.faces.component.UIInput 
import javax.faces.application.FacesMessage
 
@ManagedBean()  
@SessionScoped  
  
public class User {	   
  private String name
  private String password, nameError 
  private String planet, file
  private boolean hasErrors = false
  
  public String getFile() { file } 
  public void setFile(String newValue) { file = newValue }
  
  public String getName() { name }
  public void setName(String newValue) { name = newValue }
  
  public void setNameError(String error) {nameError = error}
  public String getNameError() {nameError}

  public String getPlanet() { planet }
  public void setPlanet(String planet) { this.planet = planet }

  public boolean getHasErrors() {
    return hasErrors;
  }
  
  public void validateName(ValueChangeEvent e) {
    UIInput nameInput = e.getComponent();
    String name = nameInput.getValue();
    
    if (name.contains("_"))   nameError = "Name cannot contain underscores"
    else if (name.equals("")) nameError = "Name cannot be blank"
    else                      nameError = "" 
  }
  
  // PROPERTY: password 
  public String getPassword() { return password }
  public void setPassword(String newValue) { password = newValue }	
  
  public void validate(ComponentSystemEvent e) {
    UIForm form = e.getComponent() 
    UIInput nameInput = form.findComponent("name")
    UIInput pwdInput = form.findComponent("password")
    
    hasErrors = false
    
    if ( ! (nameInput.getValue().equals("William") &&
    		pwdInput.getValue().equals("jsf"))) {
    	
      hasErrors = true
      
      FacesContext fc = FacesContext.getCurrentInstance()
      fc.addMessage(form.getClientId(), 
      		      new FacesMessage("Your name and password were not recognized. Please try again."))
      fc.renderResponse()
    }
  }
  
  public String login() {
	"/pages/places"
  }

  public String goPlanet() {
	"/pages/" + planet
  }
  
  public String getPlanetText() {
	"/resources/textfiles/" + (planet == null ? "mercury" : planet) + ".txt"
  } 
}
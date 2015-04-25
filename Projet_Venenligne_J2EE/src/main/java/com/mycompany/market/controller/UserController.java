package com.mycompany.market.controller;

import com.mycompany.market.business.RoleEJB;
import com.mycompany.market.business.UserEJB;
import com.mycompany.market.model.Role;
import com.mycompany.market.model.User;
import com.mycompany.market.util.FileUploadUtils;
import java.io.IOException;

import javax.ejb.EJB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.Part;


@ManagedBean
@SessionScoped
public class UserController {

    // ======================================
    // =             Attributes             =
    // ======================================

    @EJB
    private UserEJB userEJB;
    @EJB
    private RoleEJB roleEJB;
    
    private List<User> userList;
    
    private User user = new User();
  
    public UserEJB getUserEJB() {
        return userEJB;
    }
    
    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }
    
    // ======================================
    // =           Public Methods           =
    // ======================================

    public String doNew() {
        user = new User();
        return "/sections/private/user/newUser.xhtml";
    }

    public String doCreate() {
        try {
            Part file = user.getFile();
            if (file != null){
                String filePath = new FileUploadUtils(file, FacesContext.getCurrentInstance().getExternalContext()).uploadFile();
                user.setAvatar(filePath);
            }
            user = userEJB.create(user);
            Role role = new Role();
            role.setRole(Role.SIMPLEUSERROLE);
            role.setUsername(user.getUsername());
            role = roleEJB.create(role);
            user = new User();
            return "/sections/private/user/success.xhtml";
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return "/sections/private/user/newUser.xhtml";
        }
    }
    
    public String doLogin(){
        return "/login.xhtml";
    }
    
    public void validatePassword(ComponentSystemEvent event) {
 
	  FacesContext fc = FacesContext.getCurrentInstance();
 
	  UIComponent components = event.getComponent();
          //check username exits
          UIInput uiInputUsername = (UIInput) components.findComponent("username");
	  String username = uiInputUsername.getLocalValue() == null ? ""
		: uiInputUsername.getLocalValue().toString();
          String userId = uiInputUsername.getClientId();
          User user = userEJB.findByName(username);
          if (user != null) {
                FacesMessage msg = new FacesMessage("Username a existé déjà");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(userId, msg);
		fc.renderResponse();
          }
          
	  // get password
	  UIInput uiInputPassword = (UIInput) components.findComponent("password");
	  String password = uiInputPassword.getLocalValue() == null ? ""
		: uiInputPassword.getLocalValue().toString();
	  String passwordId = uiInputPassword.getClientId();
 
	  // get confirm password
	  UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
	  String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
		: uiInputConfirmPassword.getLocalValue().toString();
 
	  // Let required="true" do its job.
	  if (password.isEmpty() || confirmPassword.isEmpty()) {
		return;
	  }
 
	  if (!password.equals(confirmPassword)) {
 
		FacesMessage msg = new FacesMessage("Password must match confirm password");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(passwordId, msg);
		fc.renderResponse();
	  }
    }
    
    public String doCancel() {
        return "/home.xhtml";
    }

    public String doDelete() {

        return "listUsers.xhtml";
    }

    public String doEdit() {

        return "editUser.xhtml";
    }

    public String doSave() {
        user = userEJB.update(user);
        return "listUsers.xhtml";
    }
    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

}
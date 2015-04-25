package com.mycompany.market.controller;

import com.mycompany.market.business.BookEJB;
import com.mycompany.market.business.UserEJB;
import com.mycompany.market.model.User;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


@ManagedBean
@SessionScoped
public class LoginController {
    public static String USERNAMESESSION = "UserName";
    // ======================================
    // =             Attributes             =
    // ======================================

    @EJB
    private UserEJB userEJB;

    private User user= new User();

    // ======================================
    // =           Public Methods           =
    // ======================================

    public String doLogin() {
        System.out.println("Path info: "+FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo());
        AuthenticationToken token= new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject currentUser= SecurityUtils.getSubject(); 
        try {
            currentUser.login( token );
            Session sess = currentUser.getSession();
            sess.setAttribute(USERNAMESESSION, user.getUsername());
            user = userEJB.findByName(user.getUsername());
            //if no exception, that's it, we're done!
        } catch (AuthenticationException e) {
            addWarnMessage("Connexion impossible : ", "vérifiez les paramètres saisis");
            return null;
        }
        return "home.xhtml?faces-redirect=true";
    }

    private void addWarnMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }

    public String doLogout() {
        SecurityUtils.getSubject().logout();
        return "home.xhtml?faces-redirect=true";
    }
    
    public boolean isAuthentified(){
        return SecurityUtils.getSubject().isAuthenticated();
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
    
}
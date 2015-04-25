/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.servlet.http.Part;

/**
 *
 * @author morgan
 */
@Entity
@Table(name="users")
@NamedQueries({
    @NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u"),
    @NamedQuery(name = User.FIND_USERBYNAME, query = "SELECT u FROM User u WHERE u.username LIKE :userName"),  
})
public class User implements Serializable {
    public final static String FIND_ALL = "User.findAll";
    public final static String FIND_USERBYNAME= "User.findUserByName";
    public final static String Del_SOM = "User.delete";
    
    @Id
    @GeneratedValue
    private long id;

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
    
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Transient
    private String repassword;
    
    private String fullname;
    private String avatar;
    private String adresse;
    private String telephone;

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    @Transient
    private Part file;
    
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

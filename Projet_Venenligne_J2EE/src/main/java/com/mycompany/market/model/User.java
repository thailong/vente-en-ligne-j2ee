/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.model;

import java.io.Serializable;
import javax.persistence.*;

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
    
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;

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

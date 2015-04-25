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
@Table(name="user_roles")
@NamedQueries({
    @NamedQuery(name = Role.FIND_ALL, query = "SELECT r FROM Role r"),
})
public class Role implements Serializable {
    public final static String FIND_ALL = "Role.findAll";
    public final static String ADMINROLE = "produitadmin";
    public final static String SIMPLEUSERROLE = "simpleUser";
    @Id
    @GeneratedValue
    private long id;
    
    @Column(name="username")
    private String username;
    @Column(name="role_name")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

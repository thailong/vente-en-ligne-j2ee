/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author Thais
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Ordre.FIND_ALL, query = "SELECT o FROM Ordre o"),
    @NamedQuery(name = Ordre.FIND_USERORDER, query = "SELECT o FROM Ordre o WHERE o.user.username LIKE :userName"),
    @NamedQuery(name = Ordre.FIND_PRODUCTID, query = "SELECT o FROM Ordre o WHERE o.produit.id LIKE :produitId")
})
public class Ordre implements Serializable {
    public final static String FIND_ALL = "Ordre.findAll";
    public final static String FIND_USERORDER = "Ordre.userOrder";
    public final static String FIND_PRODUCTID = "Ordre.findOrderByPID";
    public final static String Del_SOM = "Ordre.delete";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_fk", nullable = false)
    private User user;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_fk", nullable = false)
    private Produit produit;
           
    private int number;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordre)) {
            return false;
        }
        Ordre other = (Ordre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.market.model.Panier[ id=" + id + " ]";
    }
    
}

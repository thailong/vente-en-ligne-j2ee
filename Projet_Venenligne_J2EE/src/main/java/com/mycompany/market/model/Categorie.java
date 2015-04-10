/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.model;

import javax.persistence.*;
import java.io.Serializable;
import javax.servlet.http.Part;

/**
 *
 * @author pahima
 */
@Entity
@NamedQuery(name = Categorie.FIND_ALL, query = "SELECT c FROM Categorie c")

@Table(name="categories")
public class Categorie implements Serializable {
    public final static String FIND_ALL = "Categorie.findAll";
    public final static String Del_SOM = "Categorie.delete";
   
    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue
    private long id;
    @Column(name="libelle")
    private String libelle;
    @Column(name="imageLink")
    private String imageLink;
    @Transient
    private Boolean selected;
    @Transient
    private Part file;
    

    
    // ======================================
    // =             Constructors           =
    // ======================================

    public Categorie() {
    }

    public Categorie(String libelle, String imageLink) {
        this.libelle = libelle;
        this.imageLink = imageLink;
    }


    // ======================================
    // =          Getteurs & setteurs       =
    // ======================================
      
    public long getId() {
        return id;
    }
    
    public void setId(long id) {  
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getImageLink() {
        return imageLink;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    
}

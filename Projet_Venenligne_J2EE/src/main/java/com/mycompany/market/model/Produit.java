/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import javax.validation.constraints.Size;

/**
 *
 * @author Thais
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Produit.FIND_ALL, query = "SELECT p FROM Produit p"),
    @NamedQuery(name = Produit.FIND_PRODUCTBYCATEG, query = "SELECT p FROM Produit p WHERE p.categorie.id =:idCateg")
})
public class Produit implements Serializable{
    public final static String FIND_ALL = "Produit.findAll";
    public final static String Del_SOM = "Produit.delete";
    public final static String FIND_PRODUCTBYCATEG = "Produit.findProductByCateg";

    @Id
    @GeneratedValue
    private int id;
    private String libelle;
    private double prix;
    @Column(name = "description", length = 1024)
    private String description;
    private int nbArticles;
    private String imageLink;    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_fk", nullable = false)
    private Categorie categorie;
    @Transient
    private Boolean selected;
    @Transient
    private Part file;

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    public Produit() {
    }

    public Produit(int id, String libelle, double prix, String description, int nbArticles, String imageLink, Categorie categorie) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.description = description;
        this.nbArticles = nbArticles;
        this.imageLink = imageLink;
        this.categorie = categorie;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbArticles() {
        return nbArticles;
    }

    public void setNbArticles(int nbArticles) {
        this.nbArticles = nbArticles;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object obj) {
        // Basic checks.
        if (obj == this) return true;
        if (obj == null) return false;
        // Property checks.
        Produit other = (Produit) obj;

        return this.getId() == other.getId();
    }
    
}

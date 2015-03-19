/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.model;

/**
 *
 * @author Thais
 */
public class OrderVM {
    private Produit produit;
    
    private int quality;

    public OrderVM(Produit produit, int quality) {
        this.produit = produit;
        this.quality = quality;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
    
    
}

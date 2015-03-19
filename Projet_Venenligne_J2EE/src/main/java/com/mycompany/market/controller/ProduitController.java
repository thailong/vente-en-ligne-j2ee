package com.mycompany.market.controller;

import com.mycompany.market.business.ProduitEJB;
import com.mycompany.market.business.ProduitEJB;
import com.mycompany.market.model.Categorie;
import com.mycompany.market.model.Produit;
import com.mycompany.market.util.FileUploadUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;


@ManagedBean
@SessionScoped
public class ProduitController {

    // ======================================
    // =             Attributes             =
    // ======================================

    @EJB
    private ProduitEJB produitEJB;

    private HtmlDataTable dataTable;

    private Produit produit = new Produit();
    private ListDataModel produitList; // j'ai utilisé un ListDataModel et pas List parce que cela permet de retrouver l'élément sélectionné dans la liste (pour l'édition d'un livre)

    private void updateProduitList() {
        produitList = new ListDataModel(produitEJB.findAll());
    }

    private List<Produit> newProduits;
    // ======================================
    // =           Public Methods           =
    // ======================================

    public String doNew() {
        produit = new Produit();
        return "/sections/products/newProduit.xhtml";
    }

    public String doCreate() {
        try {
            Part file = produit.getFile();
            String filePath = new FileUploadUtils(file, FacesContext.getCurrentInstance().getExternalContext()).uploadFile();
            produit.setImageLink(filePath);
            produit = produitEJB.create(produit);
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/produits.xhtml";
    }
    
    public String doCancel() {
        return "/produits.xhtml";
    }

    public String doDelete() {
        List<Produit> produits = (List<Produit>)produitList.getWrappedData();
        produitEJB.delete(onlySelected(produits));
        updateProduitList();
        return "/produits.xhtml";
    }

    private List<Produit> onlySelected(List<Produit> list) {
        for (Iterator<Produit> it = list.iterator(); it.hasNext(); ) {
            if (!(it.next().isSelected()))
                it.remove();
        }
        return list;
    }

    public String doEdit() {
        produit = (Produit)produitList.getRowData(); // Voici comment on trouve le livre sélectionné
        return "editProduit.xhtml";
    }

    public String doSave() {
        produit = produitEJB.update(produit);
        return "/produits.xhtml";
    }
    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public ListDataModel getProduitList() {
        updateProduitList();
        return produitList;
    }

    public void setProduitList(ListDataModel produitList) {
        this.produitList = produitList;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<Produit> getNewProduits() {
        List<Produit> news = produitEJB.findAll();
        for (int i = 0; i < news.size(); i++) {
            Produit p = news.get(i);
            String des = p.getDescription();
            des = des.length() > 50 ? des.substring(0, 48) + "..." : des;
            p.setDescription(des);
        }
//        List<Produit> news = new ArrayList<>();
//        news.add(new Produit(1, "Produit A", 400.00, "descriptiondescription", 100, "/uploads/81ccLffmzxL._SL1500_.jpg", new Categorie("AAA", "AAAA")));
//        news.add(new Produit(1, "Produit A", 400.00, "descriptiondescription", 100, "/uploads/81ccLffmzxL._SL1500_.jpg", new Categorie("AAA", "AAAA")));
//        news.add(new Produit(1, "Produit A", 400.00, "descriptiondescription", 100, "/uploads/81ccLffmzxL._SL1500_.jpg", new Categorie("AAA", "AAAA")));
//        news.add(new Produit(1, "Produit A", 400.00, "descriptiondescription", 100, "/uploads/81ccLffmzxL._SL1500_.jpg", new Categorie("AAA", "AAAA")));
//        news.add(new Produit(1, "Produit A", 400.00, "descriptiondescription", 100, "/uploads/81ccLffmzxL._SL1500_.jpg", new Categorie("AAA", "AAAA")));
//        news.add(new Produit(1, "Produit A", 400.00, "descriptiondescription", 100, "/uploads/81ccLffmzxL._SL1500_.jpg", new Categorie("AAA", "AAAA")));
        return news;
    }
}
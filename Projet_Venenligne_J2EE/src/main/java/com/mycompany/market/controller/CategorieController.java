/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.controller;

/**
 *
 * @author pahima
 */
import com.mycompany.market.business.CategorieEJB;
import com.mycompany.market.model.Categorie;
import com.mycompany.market.util.FileUploadUtils;
import java.io.IOException;

import javax.ejb.EJB;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.servlet.http.Part;


@ManagedBean
@SessionScoped
public class CategorieController {

    // ======================================
    // =             Attributes             =
    // ======================================

    @EJB
    private CategorieEJB categEJB;
    
    private HtmlDataTable dataTable;

    private Categorie categorie = new Categorie();
    private ListDataModel categList; // j'ai utilisé un ListDataModel et pas List parce que cela permet de retrouver l'élément sélectionné dans la liste (pour l'édition d'un livre)
    
    private List<Categorie> listCategories;
    
    @PostConstruct
    public void initilisation(){
        listCategories = categEJB.findAll();
    }
    
    private void updateCategList() {
        categList = new ListDataModel(categEJB.findAll());
    }

    // ======================================
    // =           Public Methods           =
    // ======================================

    public String doNew() {
        categorie = new Categorie();
        return "/sections/private/categorie/newCategorie.xhtml";
    }

    public String doCreate() {
        try {
            Part file = categorie.getFile();
            System.out.println("File...: " + file);
            String filePath = new FileUploadUtils(file, FacesContext.getCurrentInstance().getExternalContext()).uploadFile();
            categorie.setImageLink(filePath);
            categorie = categEJB.create(categorie);
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listCategories = categEJB.findAll();
        return "/categories.xhtml";
    }
    
    public String doCancel() {
        return "/categories.xhtml";
    }

    public String doEdit() {
        categorie = (Categorie)categList.getRowData(); // Voici comment on trouve le livre sélectionné
        return "/sections/private/categorie/editCategorie.xhtml";
    }

    public String doSave() {
         try {
            try{
            Part file = categorie.getFile();
            String filePath = new FileUploadUtils(file, FacesContext.getCurrentInstance().getExternalContext()).uploadFile();
            categorie.setImageLink(filePath);
            }catch (NullPointerException ex) {
            }
            categorie = categEJB.update(categorie);
            } catch (IOException ex) {
            Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listCategories = categEJB.findAll();
        return "/categories.xhtml";  
    }
    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public CategorieEJB getCategEJB() {
        return categEJB;
    }

    public void setCategEJB(CategorieEJB categEJB) {
        this.categEJB = categEJB;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public ListDataModel getCategList() {
         updateCategList();
        return categList;
    }

    public void setCategList(ListDataModel categList) {
        this.categList = categList;
    }

    public List<Categorie> getListCategories() {
        
        return listCategories;
    }

    public void setListCategories(List<Categorie> listCategories) {
        this.listCategories = listCategories;
    }
    

    
}


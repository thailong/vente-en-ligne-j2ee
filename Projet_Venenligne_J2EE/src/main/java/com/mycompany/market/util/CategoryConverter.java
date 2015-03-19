/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.util;

import com.mycompany.market.business.CategorieEJB;
import com.mycompany.market.controller.CategorieController;
import com.mycompany.market.controller.ProduitController;
import com.mycompany.market.model.Categorie;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thais
 */
@FacesConverter(forClass = Categorie.class)
public class CategoryConverter implements Converter{

    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        long catID = Long.parseLong(value);
        CategorieController cController = (CategorieController) ((HttpSession)context.getExternalContext().getSession(true)).getAttribute("categorieController");
        List<Categorie> cats = cController.getListCategories();
        for (Categorie cat : cats) {
            if (cat.getId() == catID){
                return cat;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Categorie cat = (Categorie) value;
        return String.valueOf(cat.getId());
    }
    
}

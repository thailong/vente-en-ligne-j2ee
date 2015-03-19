/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.market.business;

/**
 *
 * @author pahima
 */
import com.mycompany.market.model.Categorie;
import java.util.List;
import javax.persistence.*;
import javax.ejb.Stateless;

@Stateless
public class CategorieEJB {
        // ======================================
    // =             Attributes             =
    // ======================================
    @PersistenceContext(unitName = "jsfVentenlignePU")
    private EntityManager em;

    // ======================================
    // =           Public Methods           =
    // ======================================
    public List<Categorie> findAll() {
        Query query = em.createNamedQuery(Categorie.FIND_ALL);
        return query.getResultList();
    }
    
    public Categorie create(Categorie categ) {
        em.persist(categ);
        return categ;
    }

    public Categorie update(Categorie categ) {
        return em.merge(categ);
    }

    public void delete(List<Categorie> list) {
        for (Categorie categ : list) {
            delete(categ);
        }
    }

    public void delete(Categorie categ) {
        em.remove(em.merge(categ));
    }
    
    public Categorie find(int id){
        return em.find(Categorie.class, id);
    }
}

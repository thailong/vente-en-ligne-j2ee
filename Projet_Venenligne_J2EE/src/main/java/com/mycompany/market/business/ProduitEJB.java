package com.mycompany.market.business;

import com.mycompany.market.model.Produit;
import java.util.List;
import javax.persistence.*;
import javax.ejb.Stateless;

@Stateless
public class ProduitEJB {

    // ======================================
    // =             Attributes             =
    // ======================================
    @PersistenceContext(unitName = "jsfVentenlignePU")
    private EntityManager em;

    // ======================================
    // =           Public Methods           =
    // ======================================
    public List<Produit> findAll() {
        Query query = em.createNamedQuery(Produit.FIND_ALL);
        return query.getResultList();
    }

    public Produit create(Produit produit) {
        em.persist(produit);
        return produit;
    }

    public Produit update(Produit produit) {
        return em.merge(produit);
    }

    public void delete(List<Produit> list) {
        for (Produit produit : list) {
            delete(produit);
        }
    }

    public void delete(Produit produit) {
        em.remove(em.merge(produit));
    }
    
    public Produit findById(int id){
        return em.find(Produit.class, id);
    }
}

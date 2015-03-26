package com.mycompany.market.business;


import com.mycompany.market.model.Ordre;
import java.util.List;
import javax.persistence.*;
import javax.ejb.Stateless;

@Stateless
public class OrdreEJB {

    // ======================================
    // =             Attributes             =
    // ======================================
    @PersistenceContext(unitName = "jsfVentenlignePU")
    private EntityManager em;

    // ======================================
    // =           Public Methods           =
    // ======================================
    public List<Ordre> findAll() {
        Query query = em.createNamedQuery(Ordre.FIND_ALL);
        return query.getResultList();
    }
    
    public List<Ordre> findOrderOfUser(String userName){
        Query query = em.createNamedQuery(Ordre.FIND_USERORDER).setParameter("userName", userName);
        return query.getResultList();
    }
    
    public Ordre create(Ordre ordre) {
        em.persist(ordre);
        return ordre;
    }

    public Ordre update(Ordre ordre) {
        return em.merge(ordre);
    }

    public void delete(List<Ordre> list) {
        for (Ordre ordre : list) {
            delete(ordre);
        }
    }

    public void delete(Ordre ordre) {
        em.remove(em.merge(ordre));
    }
    
    
}

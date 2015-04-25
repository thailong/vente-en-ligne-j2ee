package com.mycompany.market.business;

import com.mycompany.market.model.Role;
import java.util.List;
import javax.persistence.*;
import javax.ejb.Stateless;

@Stateless
public class RoleEJB {

    // ======================================
    // =             Attributes             =
    // ======================================
    @PersistenceContext(unitName = "jsfVentenlignePU")
    private EntityManager em;

    // ======================================
    // =           Public Methods           =
    // ======================================
    public List<Role> findAll() {
        Query query = em.createNamedQuery(Role.FIND_ALL);
        return query.getResultList();
    }

    public Role create(Role role) {
        em.persist(role);
        return role;
    }

    public Role update(Role role) {
        return em.merge(role);
    }

    public void delete(List<Role> list) {
        for (Role role : list) {
            delete(role);
        }
    }

    public void delete(Role role) {
        em.remove(em.merge(role));
    }
    
}

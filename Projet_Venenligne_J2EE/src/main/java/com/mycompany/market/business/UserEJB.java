package com.mycompany.market.business;

import com.mycompany.market.model.User;
import java.util.List;
import javax.persistence.*;
import javax.ejb.Stateless;

@Stateless
public class UserEJB {

    // ======================================
    // =             Attributes             =
    // ======================================
    @PersistenceContext(unitName = "jsfVentenlignePU")
    private EntityManager em;

    // ======================================
    // =           Public Methods           =
    // ======================================
    public List<User> findAll() {
        Query query = em.createNamedQuery(User.FIND_ALL);
        return query.getResultList();
    }

    public User create(User user) {
        em.persist(user);
        return user;
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void delete(List<User> list) {
        for (User user : list) {
            delete(user);
        }
    }

    public void delete(User user) {
        em.remove(em.merge(user));
    }
    
    public User findByName(String userName){
        Query query = em.createNamedQuery(User.FIND_USERBYNAME).setParameter("userName", userName);
        List<User> users = query.getResultList();
        return users.size() == 0 ? null : users.get(0);
    }
}

package com.mycompany.market.business;

import com.mycompany.market.model.Order;
import com.mycompany.market.model.Order;
import java.util.List;
import javax.persistence.*;
import javax.ejb.Stateless;

@Stateless
public class OrderEJB {

    // ======================================
    // =             Attributes             =
    // ======================================
    @PersistenceContext(unitName = "jsfVentenlignePU")
    private EntityManager em;

    // ======================================
    // =           Public Methods           =
    // ======================================
    public List<Order> findAll() {
        Query query = em.createNamedQuery(Order.FIND_ALL);
        return query.getResultList();
    }
    
    public List<Order> findOrderOfUser(String userName){
        String queryStr = "SELECT o FROM Order o WHERE o.user.username LIKE :userName";
        Query query = em.createNamedQuery(queryStr).setParameter("userId", userName);
        return query.getResultList();
    }
    
    public Order create(Order order) {
        em.persist(order);
        return order;
    }

    public Order update(Order order) {
        return em.merge(order);
    }

    public void delete(List<Order> list) {
        for (Order order : list) {
            delete(order);
        }
    }

    public void delete(Order order) {
        em.remove(em.merge(order));
    }
}

package com.mycompany.market.controller;

import com.mycompany.market.business.OrderEJB;
import com.mycompany.market.model.Order;
import com.mycompany.market.model.OrderVM;
import com.mycompany.market.model.Produit;
import com.mycompany.market.model.User;
import java.util.ArrayList;

import javax.ejb.EJB;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.model.ListDataModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


@ManagedBean
@SessionScoped
public class PanierController {
    private static String PANIERSESSION = "Panier";
    // ======================================
    // =             Attributes             =
    // ======================================

    @EJB
    private OrderEJB orderEJB;

    private HtmlDataTable dataTable;

    private ListDataModel orderList; // j'ai utilisé un ListDataModel et pas List parce que cela permet de retrouver l'élément sélectionné dans la liste (pour l'édition d'un livre)

    private User user = null;

    private void updateOrderList() {
        orderList = new ListDataModel(orderEJB.findAll());
    }

    private List<OrderVM> listOrders;
    // ======================================
    // =           Public Methods           =
    // ======================================

    public List<OrderVM> getOrders() {
        Subject currentUser = SecurityUtils.getSubject();
        Session sess = currentUser.getSession();
        if (currentUser.isAuthenticated()){
            String userName = (String)sess.getAttribute(LoginController.USERNAMESESSION);
            List<Order> orders = orderEJB.findOrderOfUser(userName);
            listOrders = new ArrayList<>();
            for (int i = 0; i < orders.size(); i++) {
                Order o = orders.get(i);
                Produit p = o.getProduit();
                String des = p.getDescription();
                des = des.length() > 50 ? des.substring(0, 48) + "..." : des;
                p.setDescription(des);
                listOrders.add(new OrderVM(p, o.getNumber()));
            }
        }
        else{
            listOrders = (List<OrderVM>)sess.getAttribute(PANIERSESSION);
            if (listOrders == null){
                listOrders = new ArrayList<>();
                sess.setAttribute(PANIERSESSION, listOrders);
            }
        }
        return listOrders;
    }

    public String doNew() {

        return "/sections/products/newProduit.xhtml";
    }

    public String doCreate() {

        return "/produits.xhtml";
    }
    
    public String doCancel() {
        return "/produits.xhtml";
    }

    public String doDelete() {

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

        return "editProduit.xhtml";
    }

    public String doSave() {

        return "/produits.xhtml";
    }
    // ======================================
    // =          Getters & Setters         =
    // ======================================


    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public ListDataModel getOrderList() {
        return orderList;
    }

    public void setOrderList(ListDataModel orderList) {
        this.orderList = orderList;
    }
    
}
package com.mycompany.market.controller;

import com.mycompany.market.business.OrdreEJB;
import com.mycompany.market.business.ProduitEJB;
import com.mycompany.market.business.UserEJB;
import com.mycompany.market.model.Ordre;
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
    private OrdreEJB ordreEJB;
    @EJB
    private ProduitEJB produitEJB;
    @EJB
    private UserEJB userEJB;
    
    private HtmlDataTable dataTable;

    private ListDataModel ordreList; // j'ai utilisé un ListDataModel et pas List parce que cela permet de retrouver l'élément sélectionné dans la liste (pour l'édition d'un livre)

    private User user = null;

    private void updateOrderList() {
        ordreList = new ListDataModel(ordreEJB.findAll());
    }
    
    private List<OrderVM> listOrders;
    
    private Ordre newOrder = new Ordre();
    
    private double prixTotal;
    
    
    // ======================================
    // =           Public Methods           =
    // ======================================

    public List<OrderVM> getOrders() {
        Subject currentUser = SecurityUtils.getSubject();
        Session sess = currentUser.getSession();
        if (currentUser.isAuthenticated()){
            String userName = (String)sess.getAttribute(LoginController.USERNAMESESSION);
            List<Ordre> ordres = ordreEJB.findOrderOfUser(userName);
            listOrders = new ArrayList<>();
            for (int i = 0; i < ordres.size(); i++) {
                Ordre o = ordres.get(i);
                listOrders.add(new OrderVM(o.getProduit(), o.getNumber()));
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
    
    public String addToCart(int produitId){
        Subject currentUser = SecurityUtils.getSubject();
        Session sess = currentUser.getSession();
        if (currentUser.isAuthenticated()){
            String userName = (String)sess.getAttribute(LoginController.USERNAMESESSION);
            user = userEJB.findByName(userName);
            List<Ordre> ordres = ordreEJB.findOrderOfUser(userName);
            Produit p = produitEJB.findById(produitId);
            if (p != null){
                Ordre order = null;
                for (int i = 0; i < ordres.size(); i++) {
                    Ordre od = ordres.get(i);
                    if (od.getProduit().getId() == produitId){
                        order = od;
                        break;
                    }
                }
                if (order != null){
                    order.setNumber(order.getNumber() + 1);
                    ordreEJB.update(order);
                }
                else{
                    newOrder.setNumber(1);
                    newOrder.setProduit(p);
                    newOrder.setUser(user);
                    ordreEJB.create(newOrder);
                    newOrder = new Ordre();
                }
            }
        }
        else{
            listOrders = (List<OrderVM>)sess.getAttribute(PANIERSESSION);
            if (listOrders == null){
                listOrders = new ArrayList<>();
                sess.setAttribute(PANIERSESSION, listOrders);
            }
            Produit p = produitEJB.findById(produitId);
            if (p != null){
                OrderVM orderVM = null;
                for (int i = 0; i < listOrders.size(); i++) {
                    OrderVM pr = listOrders.get(i);
                    if (pr.getProduit().getId() == p.getId()){
                        orderVM = pr;
                        break;
                    }
                }
                if (orderVM != null){
                    orderVM.setQuality(orderVM.getQuality() + 1);
                }
                else{
                    orderVM = new OrderVM(p, 1);
                    listOrders.add(orderVM);
                }
            }
            sess.setAttribute(PANIERSESSION, listOrders);

        }
        return "/panier.xhtml";
    }
    
    public String doUpdateQuantity(OrderVM order){
        Subject currentUser = SecurityUtils.getSubject();
        Session sess = currentUser.getSession();
        if (currentUser.isAuthenticated()){
            String userName = (String)sess.getAttribute(LoginController.USERNAMESESSION);
            List<Ordre> ordres = ordreEJB.findOrderOfUser(userName);
            Ordre oldOrder = null;
            for (int i = 0; i < ordres.size(); i++) {
                Ordre od = ordres.get(i);
                if (od.getProduit().getId() == order.getProduit().getId()){
                    oldOrder = od;
                    break;
                }
            }
            if (oldOrder != null){
                oldOrder.setNumber(order.getQuality());
                ordreEJB.update(oldOrder);
            }
        }
        else{
            listOrders = (List<OrderVM>)sess.getAttribute(PANIERSESSION);
            if (listOrders == null){
                listOrders = new ArrayList<>();
                sess.setAttribute(PANIERSESSION, listOrders);
            }
            Produit p = order.getProduit();
            if (p != null){
                OrderVM orderVM = null;
                for (int i = 0; i < listOrders.size(); i++) {
                    OrderVM pr = listOrders.get(i);
                    if (pr.getProduit().getId() == p.getId()){
                        orderVM = pr;
                        break;
                    }
                }
                if (orderVM != null){
                    orderVM.setQuality(order.getQuality());
                }
            }
            sess.setAttribute(PANIERSESSION, listOrders);
        }
        
        return "/panier.xhtml";
    }
    
    public String doDeleteOrder(OrderVM order){
        Subject currentUser = SecurityUtils.getSubject();
        Session sess = currentUser.getSession();
        if (currentUser.isAuthenticated()){
            String userName = (String)sess.getAttribute(LoginController.USERNAMESESSION);
            List<Ordre> ordres = ordreEJB.findOrderOfUser(userName);
            Ordre oldOrder = null;
            for (int i = 0; i < ordres.size(); i++) {
                Ordre pr = ordres.get(i);
                if (pr.getProduit().getId() == order.getProduit().getId()){
                    oldOrder = pr;
                    break;
                }
            }
            if (oldOrder != null){
                ordreEJB.delete(oldOrder);
            }    
        }
        else{
            listOrders = (List<OrderVM>)sess.getAttribute(PANIERSESSION);
            if (listOrders == null){
                listOrders = new ArrayList<>();
                sess.setAttribute(PANIERSESSION, listOrders);
            }
            Produit p = order.getProduit();
            if (p != null){
                OrderVM orderVM = null;
                for (int i = 0; i < listOrders.size(); i++) {
                    OrderVM pr = listOrders.get(i);
                    if (pr.getProduit().getId() == p.getId()){
                        orderVM = pr;
                        break;
                    }
                }
                if (orderVM != null){
                    listOrders.remove(orderVM);
                }
            }
            sess.setAttribute(PANIERSESSION, listOrders);
        }
        return "/panier.xhtml";
    }
    
    public String doContinue() {

        return "/produits.xhtml";
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

    public double getPrixTotal() {
        if (listOrders == null){
            listOrders = getOrders();
        }
        prixTotal = 0;
        for (OrderVM ordre : listOrders) {
            prixTotal += ordre.getQuality() * ordre.getProduit().getPrix();
        }

        return Math.floor(prixTotal);
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public ListDataModel getOrderList() {
        return ordreList;
    }

    public void setOrderList(ListDataModel ordreList) {
        this.ordreList = ordreList;
    }
    
    public List<Integer> getListQuantity(){
        List<Integer> l = new ArrayList<>();
        for (int i = 1; i <= 5; i++){
            l.add(i);
        }
        return l;
    }
}
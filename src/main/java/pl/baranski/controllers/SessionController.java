package pl.baranski.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import pl.baranski.model.Cat;
import pl.baranski.model.MenuItem;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController implements Serializable {
    
    
    private List<Cat> catsList;
    private List<MenuItem> menuItemList; 
    private Cat newCat = new Cat(); 
    private final String sessionVersion = "Wersja Sesji Kotów: 1.0.0";
    
    @ManagedProperty(value="#{applicationController}")
    private ApplicationController appController;

    public SessionController() {
        initializeData();
        initializeMenu();
    }
    
    //   Menu
    
    private void initializeMenu() {
        menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem("Strona glowna", "/views/mainWindow"));
        menuItemList.add(new MenuItem("Lista kotów (Standard)", "/views/tableView"));
        menuItemList.add(new MenuItem("Lista użytkowników (AJAX)", "/views/tableAJAXView"));
        menuItemList.add(new MenuItem("Wylogowanie", "/logoutWindow"));
    }
    
    private void initializeData() {
        catsList = new ArrayList<>();
        catsList.add(new Cat(1, "Mruczek", "Dachowiec", 3, false));
        catsList.add(new Cat(2, "Luna", "Pers", 5, false));
        catsList.add(new Cat(3, "Garfield", "Rudy", 8, false));
        catsList.add(new Cat(4, "Fafik", "Syjamski", 2, false));
    }

    public void refreshData() {
        catsList.clear();
        initializeData();
        for(Cat cat: catsList)
            cat.setEdited(false);
    }
    
    public String invalidateSessionAndRedirect() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        
        if (session != null) {
            session.invalidate();
        }
        
        // Zwracamy wynik nawigacji do strony głównej/logowania
        return "views/mainWindow.xhtml?faces-redirect=true";
    }
    
    //   CRUD 

    
    private long getNextId() {
        long maxId = 0;
        for (Cat cat : catsList) {
            if (cat.getId() > maxId) {
                maxId = cat.getId();
            }
        }
        return maxId + 1;
    }
    
    
    public String addRow(Cat document) {
        int indexObject = catsList.indexOf(document);
        long nextId = getNextId();
        
        // Wstawienie nowego, edytowalnego kota (puste pola)
        Cat newCat = new Cat((int)nextId, "", "", 0, true); 
        catsList.add(indexObject + 1, newCat);
        return "";
    }
    
    public String delRow(Cat document) {
        catsList.remove(document);
        return "";
    }
    
    public String modifyRow(Cat document) {
        document.setEdited(true);
        return "";
    }
    
    public String saveData() {
        // Zapis (w naszej wersji: wyłączenie edycji dla wszystkich)
        for(Cat cat: catsList)
            cat.setEdited(false);
        return "";
    }
    
    //  metody dla AJAX 
    
    
    
    //  Gettery/Settery

    
    public List<Cat> getCatsList() { return catsList; } 
    public List<MenuItem> getMenuItemList() { return menuItemList; }
    
    
    public String getSessionVersion() { return sessionVersion; }
    public String getAppVersionInfo() { 
        
        String appVariableModified = appController.getVersion(); 
        return appVariableModified + " - po wstrzyknieciu";
    }
    public String getNextViewAlias() { return null; }
    
    public void setAppController(ApplicationController appController) { this.appController = appController; }
    
    
    public Cat getNewCat() { return newCat; }
    public void setNewCat(Cat newCat) { this.newCat = newCat; }
}
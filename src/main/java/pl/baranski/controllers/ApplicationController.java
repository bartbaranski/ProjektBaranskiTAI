package pl.baranski.controllers;

import pl.baranski.dao.UzytkownikDao;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import pl.baranski.to.UzytkownikTo;

@ManagedBean(name = "applicationController", eager = true)
@ApplicationScoped
public class ApplicationController implements Serializable { 
    
    private final String version = "CatSystem v1.0 by Baranski"; 
    
    private List<UzytkownikTo> uzytkownikToList = new java.util.ArrayList<>();
    
    private UzytkownikTo newUzytkownik = new UzytkownikTo(); 

    public ApplicationController() {
        refreshData();
    }
    
    //  CRUD
    
    // Metoda pobierająca dane
    public final void refreshData() {
        UzytkownikDao daneDAO = new UzytkownikDao();
        List<UzytkownikTo> uzytkownikTOListLokal = daneDAO.findAll();
        if (uzytkownikTOListLokal != null) {
            uzytkownikToList.clear();
            uzytkownikToList.addAll(uzytkownikTOListLokal);
        }
    }
    
    // Metoda dodająca wiersz (CREATE)
    public String createRow() {
        UzytkownikDao daneDao = new UzytkownikDao();
        Long newId = daneDao.create(newUzytkownik);
        
        if (newId != null) {
            //  nowy obiekt do listy w pamięci
            newUzytkownik.setId(newId);
            newUzytkownik.setEdited(false);
            uzytkownikToList.add(newUzytkownik);
            
            // Resetujemy obiekt formularza
            newUzytkownik = new UzytkownikTo(); 
        }
        return null; // odświeżenie AJAX
    }
    
    // Metoda usuwająca wiersz (DELETE)
    public String deleteRow(UzytkownikTo uzytkownikTo) {
        UzytkownikDao daneDao = new UzytkownikDao();
        if (daneDao.delete(uzytkownikTo.getId()) != -1L) {
            // Usuwamy obiekt z listy w pamięci po pomyślnym usunięciu z BD
            uzytkownikToList.remove(uzytkownikTo);
        }
        return null;
    }
    
    // Metoda przełączająca tryb edycji/zapisu (UPDATE)
    public String toggleEdit(UzytkownikTo uzytkownikTo) {
        if (uzytkownikTo.isEdited()) {
            
            UzytkownikDao daneDao = new UzytkownikDao();
            if (daneDao.update(uzytkownikTo) != -1L) {
                uzytkownikTo.setEdited(false); 
            }
        } else {
            // Przejdź do trybu edycji
            uzytkownikTo.setEdited(true);
        }
        return null;
    }
    
    public void addRow(UzytkownikTo uzytkownikTo) {
        int indexObject = uzytkownikToList.indexOf(uzytkownikTo);
        int listSize = uzytkownikToList.size();
        UzytkownikTo newRow = new UzytkownikTo(-1l, "", "", true);
        UzytkownikDao daneDAO = new UzytkownikDao();
        Long id = daneDAO.create(newRow);
        if (id != null) {
            newRow.setId(id);
            uzytkownikToList.add(indexObject + 1, newRow);
        }
    }  
    public void visibleChange(UzytkownikTo uzytkownikTo) {
        int indexObject = uzytkownikToList.indexOf(uzytkownikTo);
        
        UzytkownikDao daneDao = new UzytkownikDao();
        if (daneDao.update(uzytkownikTo) != -1) {
            uzytkownikToList.set(indexObject, uzytkownikTo);
        }

    }

    

    public String getVersion() { return version; }
    public List<UzytkownikTo> getUzytkownikToList() { return this.uzytkownikToList; }
    public void setUzytkownikToList(List<UzytkownikTo> uzytkownikToList) { this.uzytkownikToList = uzytkownikToList; }
    
    public UzytkownikTo getNewUzytkownik() { return newUzytkownik; }
    public void setNewUzytkownik(UzytkownikTo newUzytkownik) { this.newUzytkownik = newUzytkownik; }
}
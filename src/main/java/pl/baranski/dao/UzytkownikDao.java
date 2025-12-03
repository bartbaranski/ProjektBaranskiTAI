package pl.baranski.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import pl.baranski.to.UzytkownikTo;


public class UzytkownikDao {

    protected static final String SQL_SELECT_ALL = "select id,imie,nazwisko from t_uzytkownik order by id";
    
    protected static final String SQL_UPDATE = "update t_uzytkownik set imie=?, nazwisko=? where id=?";
    protected static final String SQL_INSERT = "insert into t_uzytkownik (id,imie,nazwisko) values (?, ?, ?)";
    protected static final String SQL_DELETE = "delete from t_uzytkownik where id=?";
    
    
    private static final String JNDI_NAME = "java:comp/env/jdbc/bazaTestowaMSSQLLocal"; 

    
    private Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource datasource = (DataSource) ctx.lookup(JNDI_NAME);
        if (datasource == null) {
            throw new Exception("Brak zasobu JNDI: " + JNDI_NAME);
        }
        return datasource.getConnection();
    }
    
    
    private void logError(Exception ex, String message) {
         FacesContext context = FacesContext.getCurrentInstance();
         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ex.toString()));
         
    }

    
    public Long getNextId() {
        try (Connection conn = getConnection();
             ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                     .executeQuery("select max(id) from t_uzytkownik")) {

            if (rs.first()) {
                return rs.getLong(1) + 1;
            }
            return 1L;
        } catch (Exception ex) {
            logError(ex, "Błąd pobierania następnego ID.");
            return null;
        }
    }

    
    public Long create(UzytkownikTo uzytkownikTo) {
        Long uzytkownikTOId = null;
        try (Connection conn = getConnection();
             PreparedStatement zapytanie = conn.prepareStatement(SQL_INSERT)) {

            uzytkownikTOId = getNextId();
            if (uzytkownikTOId == null) return null; 

            zapytanie.setLong(1, uzytkownikTOId);
            zapytanie.setString(2, uzytkownikTo.getImie());
            zapytanie.setString(3, uzytkownikTo.getNazwisko());
            zapytanie.executeUpdate();
            return uzytkownikTOId;
        } catch (Exception ex) {
            logError(ex, "Błąd tworzenia użytkownika (CREATE).");
            return null;
        }
    }

    
    public Long delete(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement zapytanie = conn.prepareStatement(SQL_DELETE)) {

            zapytanie.setLong(1, id);
            int iLiczbaZmian = zapytanie.executeUpdate();
            if (iLiczbaZmian == 0) return -1L; 
            return id;
        } catch (Exception ex) {
            logError(ex, "Błąd usuwania użytkownika (DELETE).");
            return -1L;
        }
    }

    
    public Long update(UzytkownikTo uzytkownikTo) {
        try (Connection conn = getConnection();
             PreparedStatement zapytanie = conn.prepareStatement(SQL_UPDATE)) {

            zapytanie.setString(1, uzytkownikTo.getImie());
            zapytanie.setString(2, uzytkownikTo.getNazwisko());
            zapytanie.setLong(3, uzytkownikTo.getId()); 
            
            int iLiczbaZMian = zapytanie.executeUpdate();
            if (iLiczbaZMian == 0) return -1L; 
            return uzytkownikTo.getId();

        } catch (Exception ex) {
            logError(ex, "Błąd aktualizacji użytkownika (UPDATE).");
            return -1L;
        }
    }

    
    public List<UzytkownikTo> findAll() {
        List<UzytkownikTo> uzytkownikToList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement zapytanie = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet wynikZapytania = zapytanie.executeQuery()) {
            
            while (wynikZapytania.next()) {
                uzytkownikToList.add(new UzytkownikTo(
                    wynikZapytania.getLong("id"), 
                    wynikZapytania.getString("imie"), 
                    wynikZapytania.getString("nazwisko"), 
                    false 
                ));
            }
            return uzytkownikToList;
        } catch (Exception ex) {
            logError(ex, "Błąd odczytu danych (FindAll).");
            return new ArrayList<>();
        }
    }
    
}
package pl.baranski.to;

import java.io.Serializable;
import java.util.Objects;

public class UzytkownikTo implements Serializable {
    private Long id;
    private String imie;
    private String nazwisko;
    private boolean edited; 

    public UzytkownikTo() {}

    public UzytkownikTo(Long id, String imie, String nazwisko, boolean edited) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.edited = edited;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getImie() { return imie; }
    public void setImie(String imie) { this.imie = imie; }
    public String getNazwisko() { return nazwisko; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }
    public boolean isEdited() { return edited; }
    public void setEdited(boolean edited) { this.edited = edited; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UzytkownikTo that = (UzytkownikTo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
package pl.baranski.model;

import java.io.Serializable;

public class Cat implements Serializable {
    private long id;
    private String name; 
    private String breed; 
    private int age; 
    private boolean edited; 

    public Cat() {}

    public Cat(long id, String name, String breed, int age, boolean edited) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.edited = edited;
    }

    // Gettery i Settery
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public boolean isEdited() { return edited; }
    public void setEdited(boolean edited) { this.edited = edited; }
}
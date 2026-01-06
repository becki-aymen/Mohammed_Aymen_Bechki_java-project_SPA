
package model;

import interfaces.IData;
import enums.fieldType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * 
 */
public class Box implements IData, Serializable {

    private int id;
    private String type; // CHIEN ou CHAT
    private int capacite;

    // Collection (obligatoire AMS)
    private List<Integer> animalIds = new ArrayList<>();

    public Box(int id, String type, int capacite) {
        this.id = id;
        this.type = type;
        this.capacite = capacite;
    }

    public boolean estPlein() {
        return animalIds.size() >= capacite;
    }

    public void ajouterAnimal(int animalId) {
        animalIds.add(animalId);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public HashMap<String, fieldType> getStruct() {
        HashMap<String, fieldType> s = new HashMap<>();
        s.put("id", fieldType.INT);
        s.put("type", fieldType.STRING);
        s.put("capacite", fieldType.INT);
        return s;
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> v = new HashMap<>();
        v.put("id", id);
        v.put("type", type);
        v.put("capacite", capacite);
        return v;
    }

    @Override
    public String toString() {
        return "Box{id=" + id + ", type=" + type + ", capacite=" + capacite + "}";
    }
}


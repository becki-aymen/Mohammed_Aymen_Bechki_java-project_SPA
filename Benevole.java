package model;

import interfaces.IData;
import enums.fieldType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.HashMap;
/**
 * 
 */
public class Benevole implements IData, Serializable {

    private int id;
    private String nom;
    private String telephone;
    private boolean disponible;

    public Benevole(int id, String nom, String telephone, boolean disponible) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.disponible = disponible;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public HashMap<String, fieldType> getStruct() {
        HashMap<String, fieldType> s = new LinkedHashMap<>();
        s.put("id", fieldType.INT);
        s.put("nom", fieldType.STRING);
        s.put("telephone", fieldType.STRING);
        s.put("disponible", fieldType.BOOLEAN);
        return s;
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> v = new LinkedHashMap<>();
        v.put("id", id);
        v.put("nom", nom);
        v.put("telephone", telephone);
        v.put("disponible", disponible);
        return v;
    }

    @Override
    public String toString() {
        return "Benevole{id=" + id +
               ", nom='" + nom + '\'' +
               ", telephone='" + telephone + '\'' +
               ", disponible=" + disponible + '}';
    }
}


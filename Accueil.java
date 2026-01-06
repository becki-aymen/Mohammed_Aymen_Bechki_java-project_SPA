package model;

import interfaces.IData;
import enums.fieldType;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.HashMap;
/**
 * 
 */
public class Accueil implements IData, Serializable {

    private int id;
    private int animalId;
    private int benevoleId;
    private Date dateDebut;

    public Accueil(int id, int animalId, int benevoleId, Date dateDebut) {
        this.id = id;
        this.animalId = animalId;
        this.benevoleId = benevoleId;
        this.dateDebut = dateDebut;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public HashMap<String, fieldType> getStruct() {
        HashMap<String, fieldType> s = new LinkedHashMap<>();
        s.put("id", fieldType.INT);
        s.put("animal_id", fieldType.INT);
        s.put("benevole_id", fieldType.INT);
        s.put("date_debut", fieldType.DATE);
        return s;
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> v = new LinkedHashMap<>();
        v.put("id", id);
        v.put("animal_id", animalId);
        v.put("benevole_id", benevoleId);
        v.put("date_debut", dateDebut);
        return v;
    }

    @Override
    public String toString() {
        return "Accueil{id=" + id +
               ", animalId=" + animalId +
               ", benevoleId=" + benevoleId +
               ", dateDebut=" + dateDebut + '}';
    }
}


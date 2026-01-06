
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
public class Adoption implements IData, Serializable {

    private int id;
    private int animalId;
    private String adoptant;
    private Date dateAdoption;

    public Adoption(int id, int animalId, String adoptant, Date dateAdoption) {
        this.id = id;
        this.animalId = animalId;
        this.adoptant = adoptant;
        this.dateAdoption = dateAdoption;
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
        s.put("adoptant", fieldType.STRING);
        s.put("date_adoption", fieldType.DATE);
        return s;
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> v = new LinkedHashMap<>();
        v.put("id", id);
        v.put("animal_id", animalId);
        v.put("adoptant", adoptant);
        v.put("date_adoption", dateAdoption);
        return v;
    }

    @Override
    public String toString() {
        return "Adoption{id=" + id +
               ", animalId=" + animalId +
               ", adoptant='" + adoptant + '\'' +
               ", dateAdoption=" + dateAdoption + '}';
    }
}


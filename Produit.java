package model;

import interfaces.IData;
import enums.fieldType;

import java.io.Serializable;
import java.util.HashMap;
/**
 * 
 */
public class Produit implements IData, Serializable {
    private int id;
    private int lotAchat;
    private String nom;
    private String description;
    private String categorie;
    private double prix;

    public Produit(int id, int lotAchat, String nom, String description, String categorie, double prix) {
        this.id = id;
        this.lotAchat = lotAchat;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.prix = prix;
    }

    @Override
    public int getId() { return id; }

    @Override
    public HashMap<String, fieldType> getStruct() {
        HashMap<String, fieldType> s = new HashMap<>();
        s.put("id", fieldType.INT);
        s.put("lot_achat", fieldType.INT);
        s.put("nom", fieldType.STRING);
        s.put("description", fieldType.STRING);
        s.put("categorie", fieldType.STRING);
        s.put("prix", fieldType.DOUBLE);
        return s;
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> v = new HashMap<>();
        v.put("id", id);
        v.put("lot_achat", lotAchat);
        v.put("nom", nom);
        v.put("description", description);
        v.put("categorie", categorie);
        v.put("prix", prix);
        return v;
    }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", lotAchat=" + lotAchat + ", nom='" + nom + "', categorie='" + categorie + "', prix=" + prix + "}";
    }
}


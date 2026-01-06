

package model;

import interfaces.IData;
import enums.fieldType;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 * Représente un animal du refuge (classe abstraite).
 * <p>
 * Spécialisée en {@link model.Chien} et {@link model.Chat}.
 * Implémente {@link interfaces.IData} pour permettre la persistance JDBC.
 * </p>
 */

public abstract class Animal implements IData, Serializable {

    protected int id;
    protected String nom;
    protected String type; // CHIEN ou CHAT
    protected Date dateArrivee;
    protected String race;
    protected int anneeNaissance;
    protected String statut;

    protected boolean okHumains;
    protected boolean okChiens;
    protected boolean okChats;
    protected boolean okBebes;

    public Animal(int id, String nom, String type, Date dateArrivee,
                  String race, int anneeNaissance, String statut,
                  boolean okHumains, boolean okChiens,
                  boolean okChats, boolean okBebes) {

        this.id = id;
        this.nom = nom;
        this.type = type;
        this.dateArrivee = dateArrivee;
        this.race = race;
        this.anneeNaissance = anneeNaissance;
        this.statut = statut;
        this.okHumains = okHumains;
        this.okChiens = okChiens;
        this.okChats = okChats;
        this.okBebes = okBebes;
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
        s.put("type", fieldType.STRING);
        s.put("date_arrivee", fieldType.DATE);
        s.put("race", fieldType.STRING);
        s.put("annee_naissance", fieldType.INT);
        s.put("statut", fieldType.STRING);
        s.put("ok_humains", fieldType.BOOLEAN);
        s.put("ok_chiens", fieldType.BOOLEAN);
        s.put("ok_chats", fieldType.BOOLEAN);
        s.put("ok_bebes", fieldType.BOOLEAN);
        return s;
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> v = new HashMap<>();
        v.put("id", id);
        v.put("nom", nom);
        v.put("type", type);
        v.put("date_arrivee", dateArrivee);
        v.put("race", race);
        v.put("annee_naissance", anneeNaissance);
        v.put("statut", statut);
        v.put("ok_humains", okHumains);
        v.put("ok_chiens", okChiens);
        v.put("ok_chats", okChats);
        v.put("ok_bebes", okBebes);
        return v;
    }
}


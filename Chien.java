package model;

import java.sql.Date;
/**
 * 
 */
public class Chien extends Animal {

    public Chien(int id, String nom, Date dateArrivee,
                 String race, int anneeNaissance, String statut,
                 boolean okHumains, boolean okChiens,
                 boolean okChats, boolean okBebes) {

        super(id, nom, "CHIEN", dateArrivee, race,
              anneeNaissance, statut,
              okHumains, okChiens, okChats, okBebes);
    }
}



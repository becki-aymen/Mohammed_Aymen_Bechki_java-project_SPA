
package model;

import java.sql.Date;
/**
 * 
 */
public class Chat extends Animal {

    public Chat(int id, String nom, Date dateArrivee,
                String race, int anneeNaissance, String statut,
                boolean okHumains, boolean okChiens,
                boolean okChats, boolean okBebes) {

        super(id, nom, "CHAT", dateArrivee, race,
              anneeNaissance, statut,
              okHumains, okChiens, okChats, okBebes);
    }
}



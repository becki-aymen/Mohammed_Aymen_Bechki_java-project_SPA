
package app;

import db.Connexion;
/**
 * 
 */
public class TestConnexion {
    public static void main(String[] args) {
        try {
            Connexion c = new Connexion();
            c.open();
            System.out.println("Connexion réussie ✅");
            c.close();
        } catch (Exception e) {
            System.out.println("Erreur connexion ❌");
            e.printStackTrace();
        }
    }
}

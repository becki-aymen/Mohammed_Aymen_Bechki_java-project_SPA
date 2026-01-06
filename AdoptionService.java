
package services;

import db.Gestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * Logique métier pour l'adoption.
 * <p>
 * Empêche la double adoption du même animal et met à jour son statut.
 * </p>
 */

public class AdoptionService {

    private final Gestion gestion;

    public AdoptionService(Gestion gestion) {
        this.gestion = gestion;
    }
    /**
     * Enregistre une adoption et met à jour le statut de l'animal en 'ADOPTE'.
     *
     * @param adoptionId identifiant adoption
     * @param animalId identifiant animal
     * @param adoptant nom de l'adoptant
     * @throws Exception si animal inexistant ou déjà adopté, ou erreur SQL
     */

    public void adopter(int adoptionId, int animalId, String adoptant) throws Exception {

        // 1️⃣ vérifier que l'animal existe
        PreparedStatement psAnimal = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "SELECT statut FROM animal WHERE id=?"
                );
        psAnimal.setInt(1, animalId);
        ResultSet rsAnimal = psAnimal.executeQuery();

        if (!rsAnimal.next()) {
            throw new Exception("Animal inexistant");
        }

        // 2️⃣ vérifier que l'animal n'est pas déjà adopté
        PreparedStatement psCheck = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "SELECT * FROM adoption WHERE animal_id=?"
                );
        psCheck.setInt(1, animalId);
        ResultSet rsCheck = psCheck.executeQuery();

        if (rsCheck.next()) {
            throw new Exception("Animal déjà adopté");
        }

        // 3️⃣ insertion adoption
        PreparedStatement psInsert = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "INSERT INTO adoption(id, animal_id, adoptant, date_adoption) " +
                        "VALUES (?, ?, ?, CURRENT_DATE)"
                );
        psInsert.setInt(1, adoptionId);
        psInsert.setInt(2, animalId);
        psInsert.setString(3, adoptant);
        psInsert.executeUpdate();

        // 4️⃣ mise à jour statut animal
        PreparedStatement psUpdate = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "UPDATE animal SET statut='ADOPTE' WHERE id=?"
                );
        psUpdate.setInt(1, animalId);
        psUpdate.executeUpdate();
    }
}


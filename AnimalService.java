
package services;

import db.Gestion;
import exceptions.CapacityExceededException;
import exceptions.InvalidBoxTypeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * Logique métier liée aux animaux.
 * <p>
 * Exemple : affecter un animal à un box en respectant :
 * - la compatibilité des types (CHIEN/CHAT)
 * - la capacité du box
 * </p>
 */

public class AnimalService {

    private final Gestion gestion;

    public AnimalService(Gestion gestion) {
        this.gestion = gestion;
    }

    /**
     * Affecte un animal à un box si les règles métier sont respectées.
     *
     * @param animalId id de l'animal
     * @param boxId id du box
     * @throws exceptions.InvalidBoxTypeException si types incompatibles
     * @throws exceptions.CapacityExceededException si box plein
     * @throws Exception si animal ou box inexistants ou autre erreur
     */

    public void affecterBox(int animalId, int boxId) throws Exception {

        // 1️⃣ récupérer le type de l'animal
        PreparedStatement psAnimal = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "SELECT type FROM animal WHERE id=?"
                );
        psAnimal.setInt(1, animalId);
        ResultSet rsAnimal = psAnimal.executeQuery();

        if (!rsAnimal.next()) {
            throw new Exception("Animal inexistant");
        }

        String typeAnimal = rsAnimal.getString("type");

        // 2️⃣ récupérer le box
        PreparedStatement psBox = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "SELECT type, capacite FROM box WHERE id=?"
                );
        psBox.setInt(1, boxId);
        ResultSet rsBox = psBox.executeQuery();

        if (!rsBox.next()) {
            throw new Exception("Box inexistant");
        }

        String typeBox = rsBox.getString("type");
        int capacite = rsBox.getInt("capacite");

        // 3️⃣ vérifier compatibilité type
        if (!typeAnimal.trim().equalsIgnoreCase(typeBox.trim())) {
            throw new InvalidBoxTypeException(
                    "Type animal incompatible avec box"
            );
        }

        // 4️⃣ vérifier capacité du box
        PreparedStatement psCount = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "SELECT COUNT(*) FROM sejour_box WHERE box_id=?"
                );
        psCount.setInt(1, boxId);
        ResultSet rsCount = psCount.executeQuery();
        rsCount.next();

        if (rsCount.getInt(1) >= capacite) {
            throw new CapacityExceededException("Box pleine");
        }

        // 5️⃣ affectation
        PreparedStatement psInsert = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "INSERT INTO sejour_box(animal_id, box_id, date_entree) " +
                        "VALUES (?, ?, CURRENT_DATE)"
                );
        psInsert.setInt(1, animalId);
        psInsert.setInt(2, boxId);
        psInsert.executeUpdate();
    }
}


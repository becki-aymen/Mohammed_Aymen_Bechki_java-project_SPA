package services;

import db.Gestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * Logique métier pour la famille d'accueil (accueil temporaire).
 * <p>
 * Vérifie : existence, disponibilité du bénévole, non adoption, non accueil déjà existant,
 * puis met à jour les statuts.
 * </p>
 */

public class AccueilService {

    private final Gestion gestion;

    public AccueilService(Gestion gestion) {
        this.gestion = gestion;
    }
    /**
     * Place un animal en famille d'accueil chez un bénévole.
     *
     * @param accueilId identifiant accueil
     * @param animalId identifiant animal
     * @param benevoleId identifiant bénévole
     * @throws Exception si règles métier non respectées ou erreur SQL
     */

    public void accueillir(int accueilId, int animalId, int benevoleId) throws Exception {

        // 1️⃣ vérifier animal existe
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

        String statut = rsAnimal.getString("statut");
        if ("ADOPTE".equals(statut)) {
            throw new Exception("Animal déjà adopté");
        }

        // 2️⃣ vérifier animal pas déjà accueilli
        PreparedStatement psCheckAnimal = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "SELECT * FROM accueil WHERE animal_id=?"
                );
        psCheckAnimal.setInt(1, animalId);
        ResultSet rsCheckAnimal = psCheckAnimal.executeQuery();

        if (rsCheckAnimal.next()) {
            throw new Exception("Animal déjà en accueil");
        }

        // 3️⃣ vérifier bénévole existe et disponible
        PreparedStatement psBen = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "SELECT disponible FROM benevole WHERE id=?"
                );
        psBen.setInt(1, benevoleId);
        ResultSet rsBen = psBen.executeQuery();

        if (!rsBen.next()) {
            throw new Exception("Bénévole inexistant");
        }

        boolean dispo = rsBen.getBoolean("disponible");
        if (!dispo) {
            throw new Exception("Bénévole non disponible");
        }

        // 4️⃣ insertion accueil
        PreparedStatement psInsert = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "INSERT INTO accueil(id, animal_id, benevole_id, date_debut) " +
                        "VALUES (?, ?, ?, CURRENT_DATE)"
                );
        psInsert.setInt(1, accueilId);
        psInsert.setInt(2, animalId);
        psInsert.setInt(3, benevoleId);
        psInsert.executeUpdate();

        // 5️⃣ mise à jour statut animal
        PreparedStatement psUpdateAnimal = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "UPDATE animal SET statut='ACCUEIL' WHERE id=?"
                );
        psUpdateAnimal.setInt(1, animalId);
        psUpdateAnimal.executeUpdate();

        // 6️⃣ mise à jour disponibilité bénévole
        PreparedStatement psUpdateBen = gestion
                .getConnexion()
                .getConnection()
                .prepareStatement(
                        "UPDATE benevole SET disponible=false WHERE id=?"
                );
        psUpdateBen.setInt(1, benevoleId);
        psUpdateBen.executeUpdate();
    }
}

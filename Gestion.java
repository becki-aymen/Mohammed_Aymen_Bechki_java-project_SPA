package db;

import interfaces.IData;
import enums.fieldType;

import java.sql.*;
import java.util.*;
import model.Produit;
/**
 * Fo urnit des opérations JDBC génériques sur les tables.
 * <p>
 * Permet : récupérer la structure d'une table, afficher une table, exécuter une requête,
 * insérer des entités {@link interfaces.IData} (avec traitement spécial pour {@link model.Produit}),
 * et supprimer une ligne par id.
 * </p>
 */

public class Gestion {

    private final Connexion connexion;

    public Gestion(Connexion connexion) {
        this.connexion = connexion;
    }

    /* تحويل نوع SQL إلى fieldType */
    private fieldType sqlTypeToFieldType(int sqlType) {
        return switch (sqlType) {
            case Types.INTEGER, Types.BIGINT -> fieldType.INT;
            case Types.VARCHAR, Types.CHAR, Types.LONGVARCHAR -> fieldType.STRING;
            case Types.DOUBLE, Types.FLOAT, Types.REAL, Types.NUMERIC -> fieldType.DOUBLE;
            case Types.BOOLEAN -> fieldType.BOOLEAN;
            case Types.DATE -> fieldType.DATE;
            case Types.TIMESTAMP -> fieldType.TIMESTAMP;
            default -> fieldType.STRING;
        };
    }

    /* إرجاع بنية الجدول */
    public HashMap<String, fieldType> structTable(String table, boolean display) throws SQLException {
        String sql = "SELECT * FROM " + table + " LIMIT 0";
        PreparedStatement ps = connexion.getConnection().prepareStatement(sql);
        ResultSetMetaData meta = ps.getMetaData();

        HashMap<String, fieldType> struct = new LinkedHashMap<>();

        for (int i = 1; i <= meta.getColumnCount(); i++) {
            String col = meta.getColumnName(i).toLowerCase();
            fieldType type = sqlTypeToFieldType(meta.getColumnType(i));
            struct.put(col, type);
        }

        if (display) {
            System.out.println("STRUCTURE DE " + table + " :");
            struct.forEach((k, v) -> System.out.println(" - " + k + " : " + v));
        }
        return struct;
    }
    /**
     * Affiche le contenu d'une table dans la console.
     *
     * @param table nom de la table
     * @throws java.sql.SQLException en cas d'erreur SQL
     */

    public void displayTable(String table) throws SQLException {
        String sql = "SELECT * FROM " + table;
        PreparedStatement ps = connexion.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData meta = rs.getMetaData();

        int cols = meta.getColumnCount();

        for (int i = 1; i <= cols; i++)
            System.out.print(meta.getColumnName(i) + "\t");
        System.out.println("\n-------------------------------------");

        while (rs.next()) {
            for (int i = 1; i <= cols; i++)
                System.out.print(rs.getObject(i) + "\t");
            System.out.println();
        }
    }
    /**
     * Exécute une requête SQL (CREATE, DROP, UPDATE, DELETE... hors insertion générique).
     *
     * @param query requête SQL
     * @throws java.sql.SQLException en cas d'erreur SQL
     */

    public void execute(String query) throws SQLException {
        PreparedStatement ps = connexion.getConnection().prepareStatement(query);
        ps.execute();
    }

    /**
     * Insère une entité {@link interfaces.IData} dans une table.
     * <p>
     * Vérifie d'abord la compatibilité entre la structure de la table et la structure de l'objet.
     * Si l'id n'existe pas : insertion générique.
     * Si l'id existe : mise à jour spéciale uniquement pour {@link model.Produit} selon le TP JDBC.
     * </p>
     *
     * @param data entité à insérer
     * @param table nom de la table cible
     * @throws java.sql.SQLException si structure incompatible ou erreur SQL
     */

    public void insert(IData data, String table) throws SQLException {

        HashMap<String, fieldType> tableStruct = structTable(table, false);
        if (!data.check(tableStruct))
            throw new SQLException("Structure incompatible avec la table");

        // vérifier si l'id existe
        String checkSql = "SELECT * FROM " + table + " WHERE id=?";
        PreparedStatement checkPs = connexion.getConnection().prepareStatement(checkSql);
        checkPs.setInt(1, data.getId());
        ResultSet rs = checkPs.executeQuery();

        if (!rs.next()) {
            // INSERT générique (لكل الكيانات)
            HashMap<String, Object> values = data.getValues();
            String cols = String.join(",", values.keySet());
            String qMarks = String.join(",", Collections.nCopies(values.size(), "?"));

            String sql = "INSERT INTO " + table + " (" + cols + ") VALUES (" + qMarks + ")";
            PreparedStatement ps = connexion.getConnection().prepareStatement(sql);

            int i = 1;
            for (Object val : values.values())
                ps.setObject(i++, val);

            ps.executeUpdate();

        } else {
            // UPDATE uniquement pour PRODUIT
            if (data instanceof model.Produit) {

                String sql = """
                    UPDATE product
                    SET lot_achat = ?,
                        nom = ?,
                        categorie = ?,
                        prix = prix + ?,
                        description = description || ' ' || ?
                    WHERE id = ?
                """;

                PreparedStatement ps = connexion.getConnection().prepareStatement(sql);
                ps.setObject(1, data.getValues().get("lot_achat"));
                ps.setObject(2, data.getValues().get("nom"));
                ps.setObject(3, data.getValues().get("categorie"));
                ps.setObject(4, data.getValues().get("prix"));
                ps.setObject(5, data.getValues().get("description"));
                ps.setInt(6, data.getId());
                ps.executeUpdate();

            } else {
                // autres entités : on ne fait rien
                System.out.println("ID déjà existant, aucune mise à jour effectuée.");
            }
        }

    }
    /**
     * Supprime une ligne d'une table par identifiant.
     *
     * @param table nom de la table
     * @param id identifiant à supprimer
     * @throws java.sql.SQLException en cas d'erreur SQL
     */

    public void remove(String table, int id) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE id=?";
        PreparedStatement ps = connexion.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    public Connexion getConnexion() {
        return connexion;
    }


}


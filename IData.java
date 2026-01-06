package interfaces;

import enums.fieldType;
import java.util.HashMap;
/**
 * Contrat des entités persistables.
 * <p>
 * Toute entité doit fournir :
 * - un identifiant {@code id}
 * - une structure (colonnes + types)
 * - des valeurs correspondantes pour l'insertion JDBC
 * </p>
 */

public interface IData {
    int getId();
    HashMap<String, fieldType> getStruct();
    HashMap<String, Object> getValues();
    /**
     * Vérifie la compatibilité entre la structure d'une table et la structure de l'entité.
     *
     * @param tableStruct structure de la table
     * @return true si structures compatibles
     */

    default boolean check(HashMap<String, fieldType> tableStruct) {
        return tableStruct != null
            && tableStruct.keySet().equals(getStruct().keySet());
    }

}



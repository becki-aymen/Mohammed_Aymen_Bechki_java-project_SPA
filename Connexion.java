package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Gère la connexion JDBC à PostgreSQL.
 * <p>
 * Fournit des méthodes pour ouvrir, récupérer et fermer une connexion à la base.
 * Utilise des timeouts pour éviter le blocage en cas de réseau instable.
 * </p>
 */

public class Connexion {

    private static final String HOST = "pedago.univ-avignon.fr";
    private static final int PORT = 5432;
    private static final String DB = "etd";

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB;

    private static final String USER = "uapv2500271";
    private static final String PASSWORD = "cheboutimayas";

    private Connection connection;
    /**
     * Ouvre une connexion JDBC à PostgreSQL.
     *
     * @return l'objet {@link java.sql.Connection} ouvert
     * @throws java.sql.SQLException si le driver est introuvable ou si la connexion échoue
     */

    public Connection open() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL introuvable. Vérifie le jar.", e);
        }

        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("connectTimeout", "10"); // ثواني
        props.setProperty("socketTimeout", "10");

        connection = DriverManager.getConnection(URL, props);
        return connection;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) return open();
        return connection;
    }

    public void close() {
        if (connection != null) {
            try { connection.close(); } catch (SQLException ignored) {}
        }
    }
}


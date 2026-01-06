package app;

import db.Connexion;
import db.Gestion;
import model.Produit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import model.Box;
import services.AnimalService;
import services.AdoptionService;
import model.Benevole;
import services.AccueilService;
/**
 * 
 */
public class Test {


    public static void main(String[] args) {

        Connexion connexion = new Connexion();
        Gestion gestion = new Gestion(connexion);

        try {
            connexion.open();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("TP JDBC - Commandes : CREATE, INSERT, DISPLAY, STRUCT, REMOVE, DROP, EXIT");

            while (true) {
                System.out.print("> ");
                String line = br.readLine();
                if (line == null) break;

                String[] parts = line.trim().split("\\s+");
                String cmd = parts[0].toUpperCase();


                switch (cmd) {
                    case "CREATE" -> gestion.execute("""
                        CREATE TABLE IF NOT EXISTS product(
                            id INT PRIMARY KEY,
                            lot_achat INT,
                            nom VARCHAR(100),
                            description TEXT,
                            categorie VARCHAR(50),
                            prix DOUBLE PRECISION
                        )
                    """);
                    case "CREATE_ANIMAL" -> gestion.execute("""
                    	    CREATE TABLE IF NOT EXISTS animal (
                    	        id INT PRIMARY KEY,
                    	        nom VARCHAR(100),
                    	        type VARCHAR(10),
                    	        date_arrivee DATE,
                    	        race VARCHAR(50),
                    	        annee_naissance INT,
                    	        statut VARCHAR(30),
                    	        ok_humains BOOLEAN,
                    	        ok_chiens BOOLEAN,
                    	        ok_chats BOOLEAN,
                    	        ok_bebes BOOLEAN
                    	    )
                    	""");
                    case "DISPLAY_ANIMAL" -> gestion.displayTable("animal");
                    case "INSERT_ANIMAL" -> {

                        System.out.print("id: ");
                        int id = Integer.parseInt(br.readLine());

                        System.out.print("type (CHIEN/CHAT): ");
                        String type = br.readLine().toUpperCase();

                        System.out.print("nom: ");
                        String nom = br.readLine();

                        System.out.print("race: ");
                        String race = br.readLine();

                        System.out.print("annee naissance: ");
                        int annee = Integer.parseInt(br.readLine());

                        java.sql.Date dateArrivee = new java.sql.Date(System.currentTimeMillis());

                        System.out.print("statut: ");
                        String statut = br.readLine();

                        System.out.print("ok humains (true/false): ");
                        boolean okH = Boolean.parseBoolean(br.readLine());

                        System.out.print("ok chiens (true/false): ");
                        boolean okC = Boolean.parseBoolean(br.readLine());

                        System.out.print("ok chats (true/false): ");
                        boolean okCh = Boolean.parseBoolean(br.readLine());

                        System.out.print("ok bebes (true/false): ");
                        boolean okB = Boolean.parseBoolean(br.readLine());

                        model.Animal a;
                        if (type.equals("CHIEN")) {
                            a = new model.Chien(id, nom, dateArrivee, race, annee, statut, okH, okC, okCh, okB);
                        } else {
                            a = new model.Chat(id, nom, dateArrivee, race, annee, statut, okH, okC, okCh, okB);
                        }

                        gestion.insert(a, "animal");
                    }

                    case "INSERT" -> {
                        System.out.print("id: ");
                        int id = Integer.parseInt(br.readLine());
                        System.out.print("lot: ");
                        int lot = Integer.parseInt(br.readLine());
                        System.out.print("nom: ");
                        String nom = br.readLine();
                        System.out.print("description: ");
                        String desc = br.readLine();
                        System.out.print("categorie: ");
                        String cat = br.readLine();
                        System.out.print("prix: ");
                        double prix = Double.parseDouble(br.readLine());

                        Produit p = new Produit(id, lot, nom, desc, cat, prix);
                        gestion.insert(p, "product");
                    }

                    case "DISPLAY" -> gestion.displayTable("product");
                    case "STRUCT" -> gestion.structTable("product", true);

                    case "REMOVE" -> {
                        System.out.print("id: ");
                        int id = Integer.parseInt(br.readLine());
                        gestion.remove("product", id);
                    }

                    case "DROP" -> gestion.execute("DROP TABLE product");
                    case "EXIT" -> {
                        connexion.close();
                        return;
                    }
                    case "CREATE_BOX" -> gestion.execute("""
                    	    CREATE TABLE IF NOT EXISTS box (
                    	        id INT PRIMARY KEY,
                    	        type VARCHAR(10),
                    	        capacite INT
                    	    )
                    	""");
                    case "INSERT_BOX" -> {
                        System.out.print("id: ");
                        int id = Integer.parseInt(br.readLine());

                        System.out.print("type (CHIEN/CHAT): ");
                        String type = br.readLine().toUpperCase();

                        System.out.print("capacite: ");
                        int capacite = Integer.parseInt(br.readLine());

                        Box b = new Box(id, type, capacite);
                        gestion.insert(b, "box");
                    }
                    case "DISPLAY_BOX" -> gestion.displayTable("box");
                    case "CREATE_SEJOUR_BOX" -> gestion.execute("""
                    	    CREATE TABLE IF NOT EXISTS sejour_box (
                    	        animal_id INT PRIMARY KEY,
                    	        box_id INT,
                    	        date_entree DATE,
                    	        FOREIGN KEY(animal_id) REFERENCES animal(id),
                    	        FOREIGN KEY(box_id) REFERENCES box(id)
                    	    )
                    	""");
                    case "AFFECT_BOX" -> {
                        System.out.print("animal id: ");
                        int animalId = Integer.parseInt(br.readLine());

                        System.out.print("box id: ");
                        int boxId = Integer.parseInt(br.readLine());

                        AnimalService service = new AnimalService(gestion);
                        service.affecterBox(animalId, boxId);

                        System.out.println("Animal affecté au box avec succès");
                    }
                    case "CREATE_ADOPTION" -> gestion.execute("""
                    	    CREATE TABLE IF NOT EXISTS adoption (
                    	        id INT PRIMARY KEY,
                    	        animal_id INT UNIQUE,
                    	        adoptant VARCHAR(100),
                    	        date_adoption DATE,
                    	        FOREIGN KEY(animal_id) REFERENCES animal(id)
                    	    )
                    	""");
                    case "ADOPT" -> {
                        System.out.print("adoption id: ");
                        int adoptionId = Integer.parseInt(br.readLine());

                        System.out.print("animal id: ");
                        int animalId = Integer.parseInt(br.readLine());

                        System.out.print("adoptant (nom): ");
                        String adoptant = br.readLine();

                        AdoptionService service = new AdoptionService(gestion);
                        service.adopter(adoptionId, animalId, adoptant);

                        System.out.println("Animal adopté avec succès");
                    }
                    case "CREATE_BENEVOLE" -> gestion.execute("""
                    	    CREATE TABLE IF NOT EXISTS benevole (
                    	        id INT PRIMARY KEY,
                    	        nom VARCHAR(100),
                    	        telephone VARCHAR(30),
                    	        disponible BOOLEAN
                    	    )
                    	""");
                    case "INSERT_BENEVOLE" -> {

                        System.out.print("id: ");
                        int id = Integer.parseInt(br.readLine());

                        System.out.print("nom: ");
                        String nom = br.readLine();

                        System.out.print("telephone: ");
                        String tel = br.readLine();

                        System.out.print("disponible (true/false): ");
                        boolean dispo = Boolean.parseBoolean(br.readLine());

                        Benevole b = new Benevole(id, nom, tel, dispo);
                        gestion.insert(b, "benevole");

                        System.out.println("Bénévole ajouté avec succès");
                    }
                    case "DISPLAY_BENEVOLE" -> gestion.displayTable("benevole");
                    case "CREATE_ACCUEIL" -> gestion.execute("""
                    	    CREATE TABLE IF NOT EXISTS accueil (
                    	        id INT PRIMARY KEY,
                    	        animal_id INT UNIQUE,
                    	        benevole_id INT,
                    	        date_debut DATE,
                    	        FOREIGN KEY(animal_id) REFERENCES animal(id),
                    	        FOREIGN KEY(benevole_id) REFERENCES benevole(id)
                    	    )
                    	""");
                    case "ACCUEILLIR" -> {
                        System.out.print("accueil id: ");
                        int accueilId = Integer.parseInt(br.readLine());

                        System.out.print("animal id: ");
                        int animalId = Integer.parseInt(br.readLine());

                        System.out.print("benevole id: ");
                        int benevoleId = Integer.parseInt(br.readLine());

                        AccueilService service = new AccueilService(gestion);
                        service.accueillir(accueilId, animalId, benevoleId);

                        System.out.println("Animal placé en famille d’accueil avec succès");
                    }


                    default -> System.out.println("Commande inconnue");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



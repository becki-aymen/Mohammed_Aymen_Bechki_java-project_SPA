package utils;

import java.io.*;
/**
 * Utility class for saving and loading objects using serialization.
 */
public class Serializer {
	  /**
     * Saves an object to a file.
     *
     * @param obj  the object to save
     * @param file the file path
     * @throws IOException if an I/O error occurs
     */
    public static void save(Object obj, String file) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(obj);
        oos.close();
    }
    /**
     * Loads an object from a file.
     *
     * @param file the file path
     * @return the loaded object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if class not found
     */
    public static Object load(String file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}


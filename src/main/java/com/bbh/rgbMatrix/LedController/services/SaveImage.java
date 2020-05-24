package com.bbh.rgbMatrix.LedController.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class SaveImage {

    /**
     * Speichert das Bild als .png datei
     * <p>
     * Beispiel: Bild soll mit dem Namen "output.png" gespeicehrt werden.
     *
     * @param image      Das eingelesene Bild
     * @param outputFile Name und Dateiendung vom neuen Bild, welches gespeicehrt werden soll.
     */
    public void save(BufferedImage image, String outputFile) {
        // Die Dateierweiterung wird vom Dateiname und Pfad getrennt
        String extension = FilenameUtils.getExtension(outputFile);
        try {
            // Wenn die Dateierweiterung .png enthält, wird das Bild gespeichert
            if (extension.equals("png")) {
                // Der Pfad wird vom Dateinamen und Dateierweiterung getrennt, anschliesend wird der Ordner erstellt, wenn er nicht schon vorhanden ist.
                createDirectory(FilenameUtils.getPath(outputFile));
                // Das Bild wird im .png Format gespeichert
                ImageIO.write(image, "png", new File(outputFile));
                System.out.println("Image saved to: " + outputFile);
            } else {
                System.err.println("Image not saved! Please only use .png");
            }
        } catch (IOException e) {
            System.err.println("Image could not be saved");
        }
    }

    /**
     * Überprüft ob der angegebne Ordner bereits erstellt ist oder nicht.
     * Erstellt einen neuen Ordner falls er noch nicht vorhanden ist.
     * <p>
     * Beispiel: Bild soll mit dem Namen "output.png" im Ordner "test" gespeicehrt werden.
     *
     * @param filePath Pfad zu der Datei, welche gespeichert werden soll.
     */
    public void createDirectory (String filePath) {
        try {
            // Wenn ein Pfad vorhanden ist, wird überprüft, ob die Ordner bereits existieren, oder erstellt werden müssen
            if (filePath.length() != 0) {
                // Ein File wird anhand vom Pfad erstellt
                File dir = new File(filePath);
                //Wenn die Ordner noch nicht vorhanden sind, werden sie erstellt
                if (!dir.exists()) {
                    System.out.println("Creating Directory: " + filePath);
                    // Die ordner werden erstellt
                    dir.mkdirs();
                }
            }
        } catch (Exception e) {
            System.err.println("Cloud not create Directory: " + filePath);
            System.err.println("Image could not be saved");
            System.exit(1);
        }
    }
}
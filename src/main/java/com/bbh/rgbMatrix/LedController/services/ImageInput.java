package com.bbh.rgbMatrix.LedController.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Component
public class ImageInput {

    private final List<String> ALLOWED_FILE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "ico", "tiff", "gif");

    private static final Logger LOGGER = LogManager.getLogger(ImageInput.class);
    /**
     * Liest ein Pfad oder URL von einem Bild als BufferdImage ein.
     * <p>
     * Beispiel 1: Lokale Datei "img.png" soll eingelesen werden.
     * Beispiel 2: Bild von einer Webseite soll eingelesen werden.
     *
     * @param inputFile Der Pfad oder URL vom eingelesenen Bild
     * @return Das eingelesene Bild als BufferdImage
     */
    public BufferedImage getImage(String inputFile) {
        try {
            String extension = FilenameUtils.getExtension(inputFile);
            if (ALLOWED_FILE_EXTENSIONS.contains(extension)) {
                System.out.println("Input image: " + inputFile);
                LOGGER.info("Input image: " + inputFile);
                if (inputFile.contains("http")) {
                    return ImageFromUrl(inputFile);
                } else {
                    File image = ImageFromPath(inputFile);
                    return readImageFile(image);
                }
            } else {
                System.err.println("Fil extension: " + extension + " is not allowed");
                LOGGER.error("Fil extension: " + extension + " is not allowed");
                System.err.println("Allowed extensions: " + "jpg, jpeg, png, ico, tiff, gif");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Could not find image: " + inputFile);
            LOGGER.error("Could not find image: " + inputFile);
            System.exit(1);
        }
        return null;
    }

    /**
     * Ein lokal gespeichertes Bild soll vom Pfad eingelesen werden
     * <p>
     *
     * @param file Der Pfad von dem Bild
     * @return Neues File Objekt
     */
    public File ImageFromPath(String file) {
        return new File(file);
    }

    /**
     * Ein Bild von einer Webseite soll eingelesen werden
     * <p>
     *
     * @param url Url zum Bild
     * @return Das eingelesene Bild als BufferdImage
     */
    public BufferedImage ImageFromUrl(String url) throws IOException {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Aktiviert neuen Trust-manager für HTTPS verbindungen
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.err.println("SSL Certificate not valid");
            LOGGER.error("SSL Certificate not valid");
        }

        URL urlInput = new URL(url);
        return ImageIO.read(urlInput);
    }

    /**
     * Ein Bild wird eingelesen und zurückgegeben
     * <p>
     *
     * @param imageFile Eingelesenes ImageFile Bild
     * @return Das eingelesene Bild als BufferdImage
     */
    public BufferedImage readImageFile(File imageFile) throws IOException {
        BufferedImage image;
        image = ImageIO.read(imageFile);
        return image;
    }
}
